package com.machina.api.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import com.machina.api.cap.energy.MachinaEnergyStorage;
import com.machina.api.cap.fluid.MachinaFluidStorage;
import com.machina.api.cap.item.MachinaItemStorage;
import com.machina.api.cap.sided.MultiSidedStorage;
import com.machina.api.cap.sided.Side;
import com.machina.api.cap.sided.SidedStorage;
import com.machina.api.cap.sided.SingleSidedStorage;
import com.machina.api.menu.IMachinaMenuProvider;
import com.machina.api.util.reflect.QuadFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.LockCode;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

/**
 * Abstract class to allow Machina BlockEntities to store items, fluids and
 * energy.
 * 
 * @author Cy4Shot
 * @since Machina v0.1.0
 */
public abstract class MachinaBlockEntity extends BaseBlockEntity implements Container, IMachinaMenuProvider {

	public static final int DEFAULT_DATA_SIZE = 2;
	private LockCode lockKey = LockCode.NO_LOCK;
	private MultiSidedStorage<MachinaEnergyStorage> energyCap;
	private List<SingleSidedStorage<MachinaItemStorage>> itemsCap = new ArrayList<>();
	private List<SingleSidedStorage<MachinaFluidStorage>> fluidsCap = new ArrayList<>();

	private long energy;

	protected ContainerData data = new SimpleContainerData(getExtraDataSize() + DEFAULT_DATA_SIZE);

	public abstract void createStorages();

	public void energyStorage(Side[] sides) {
		this.energyCap = new MultiSidedStorage<MachinaEnergyStorage>("energy", this, MachinaEnergyStorage::new, sides);
	}

	public void itemStorage(@NonNull MachinaItemStorage store, Side[] sides) {
		this.itemsCap.add(new SingleSidedStorage<MachinaItemStorage>("item_" + this.itemsCap.size(), store, sides));
	}

	public void fluidStorage(@NonNull MachinaFluidStorage store, Side[] sides) {
		this.fluidsCap.add(new SingleSidedStorage<MachinaFluidStorage>("fluid_" + this.fluidsCap.size(), store, sides));
	}

	public MachinaBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
		this.createStorages();
	}

	public void forEachStorage(Consumer<SidedStorage> consumer) {
		consumer.accept(energyCap);
		this.itemsCap.forEach(consumer);
		this.fluidsCap.forEach(consumer);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		forEachStorage(SidedStorage::invalidate);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		forEachStorage(s -> s.loadAdditional(tag));
		this.energy = tag.getLong("energy");
		this.lockKey = LockCode.fromTag(tag);
		this.setChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		forEachStorage(s -> s.saveAdditional(tag));
		tag.putLong("energy", energy);
		this.lockKey.addToTag(tag);
		super.saveAdditional(tag);
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (side != null) {
			if (cap == ForgeCapabilities.ENERGY) {
				if (energyCap.isNonNullMode(side)) {
					return energyCap.get(side).cast();
				}
			} else if (cap == ForgeCapabilities.ITEM_HANDLER) {
				for (SingleSidedStorage<MachinaItemStorage> storage : itemsCap) {
					if (storage.isNonNullMode(side)) {
						return storage.get().cast();
					}
				}
			} else if (cap == ForgeCapabilities.FLUID_HANDLER) {
				for (SingleSidedStorage<MachinaFluidStorage> storage : fluidsCap) {
					if (storage.isNonNullMode(side)) {
						return storage.get().cast();
					}
				}
			}
		}
		return super.getCapability(cap, side);
	}

	@Override
	public int getContainerSize() {
		int total = 0;
		for (SingleSidedStorage<MachinaItemStorage> storage : itemsCap) {
			total += storage.get().map(s -> s.getSlots()).orElse(0);
		}
		return total;
	}

	@Override
	public boolean isEmpty() {
		for (SingleSidedStorage<MachinaItemStorage> storage : itemsCap) {
			if (storage.get().map(s -> s.items().stream().allMatch(ItemStack::isEmpty)).orElse(false)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public @NotNull ItemStack getItem(int pIndex) {
		int i = pIndex;
		for (SingleSidedStorage<MachinaItemStorage> storage : itemsCap) {
			int size = storage.get().map(s -> s.getSlots()).orElse(0);
			if (i < size) {
				MachinaItemStorage itemStore = storage.get()
						.orElseThrow(() -> new IllegalStateException("Item storage is null"));
				return itemStore.getStackInSlot(i);
			}
			i -= size;
		}

		return ItemStack.EMPTY;
	}

	@Override
	public @NotNull ItemStack removeItem(int pIndex, int pCount) {
		int i = pIndex;
		for (SingleSidedStorage<MachinaItemStorage> storage : itemsCap) {
			int size = storage.get().map(s -> s.getSlots()).orElse(0);
			if (i < size) {
				MachinaItemStorage itemStore = storage.get()
						.orElseThrow(() -> new IllegalStateException("Item storage is null"));
				ItemStack stack = itemStore.extractItem(i, pCount, false);
				if (!stack.isEmpty()) {
					this.setChanged();
				}
				return stack;
			}
			i -= size;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public @NotNull ItemStack removeItemNoUpdate(int pIndex) {
		int i = pIndex;
		for (SingleSidedStorage<MachinaItemStorage> storage : itemsCap) {
			int size = storage.get().map(s -> s.getSlots()).orElse(0);
			if (i < size) {
				MachinaItemStorage itemStore = storage.get()
						.orElseThrow(() -> new IllegalStateException("Item storage is null"));
				return itemStore.extractItem(i, itemStore.getStackInSlot(i).getCount(), false);
			}
			i -= size;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setItem(int pIndex, ItemStack pStack) {
		System.out.println("Setting");
		int i = pIndex;
		for (SingleSidedStorage<MachinaItemStorage> storage : itemsCap) {
			int size = storage.get().map(s -> s.getSlots()).orElse(0);
			if (i < size) {
				MachinaItemStorage itemStore = storage.get()
						.orElseThrow(() -> new IllegalStateException("Item storage is null"));
				itemStore.setStackInSlot(i, pStack);
				if (pStack.getCount() > this.getMaxStackSize()) {
					pStack.setCount(this.getMaxStackSize());
				}
				this.setChanged();
				return;
			}
			i -= size;
		}
	}

	public long getEnergy() {
		return energy;
	}

	public abstract int getMaxEnergy();

	public long receiveEnergy(Direction from, long maxReceive, boolean simulate) {
		if (!energyCap.isInput(from)) {
			return 0;
		}
		return receiveEnergy(maxReceive, simulate);
	}

	protected long receiveEnergy(long maxReceive, boolean simulate) {
		long received = Math.min(getMaxEnergy() - energy, maxReceive);
		if (received > 0) {
			if (!simulate) {
				energy += received;
				this.setChanged();
			}
		}
		if (received > 0) {
			maxReceive -= received;
		}
		return received;
	}

	public boolean isEnergyFull() {
		return energy >= getMaxEnergy();
	}

	public void consumeEnergy(long amount) {
		this.energy -= amount;
		if (this.energy < 0) {
			this.energy = 0;
		} else if (this.energy > getMaxEnergy()) {
			this.energy = getMaxEnergy();
		}
		this.setChanged();
	}

	@Override
	public boolean stillValid(@NotNull Player pPlayer) {
		if ((this.level != null ? this.level.getBlockEntity(this.worldPosition) : null) != this) {
			return false;
		} else {
			return !(pPlayer.distanceToSqr((double) this.worldPosition.getX() + 0.5D,
					(double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) > 64.0D);
		}
	}

	@Override
	public void clearContent() {
		this.itemsCap.forEach(s -> s.get().ifPresent(MachinaItemStorage::clear));
	}

	@Override
	public void setChanged() {
		if (this.level instanceof ServerLevel) {
			final BlockState state = getBlockState();
			this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
			this.level.blockEntityChanged(this.worldPosition);
		}
		this.data.set(0, (int) this.energy);
		this.data.set(1, this.getMaxEnergy());
		super.setChanged();
	}

	public boolean canOpen(Player player) {
		return canUnlock(player, this.lockKey, this.getDisplayName());
	}

	public static boolean canUnlock(Player player, LockCode lock, Component name) {
		if (!player.isSpectator() && !lock.unlocksWith(player.getMainHandItem())) {
			player.displayClientMessage(Component.translatable("container.isLocked", name), true);
			player.playNotifySound(SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0F, 1.0F);
			return false;
		} else {
			return true;
		}
	}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
		return this.canOpen(player)
				? createMenu().apply(id, inv, ContainerLevelAccess.create(level, worldPosition), data)
				: null;
	}

	protected abstract int getExtraDataSize();

	protected abstract QuadFunction<Integer, Inventory, ContainerLevelAccess, ContainerData, AbstractContainerMenu> createMenu();

	public void tick() {
	}

}