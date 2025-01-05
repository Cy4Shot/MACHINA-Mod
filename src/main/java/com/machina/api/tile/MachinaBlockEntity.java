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
import com.machina.api.cap.sided.MultiSidedStorage;
import com.machina.api.cap.sided.Side;
import com.machina.api.cap.sided.SidedStorage;
import com.machina.api.cap.sided.SingleSidedStorage;
import com.machina.api.menu.IMachinaMenuProvider;
import com.machina.api.util.reflect.QuadFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.LockCode;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

/**
 * Abstract class to allow Machina BlockEntities to store items, fluids and
 * energy.
 * 
 * @author Cy4Shot
 * @since Machina v0.1.0
 */
public abstract class MachinaBlockEntity extends BaseBlockEntity implements WorldlyContainer, IMachinaMenuProvider {

	public static final int DEFAULT_DATA_SIZE = 2;
	private LockCode lockKey = LockCode.NO_LOCK;
	private MultiSidedStorage<MachinaEnergyStorage> energyCap;
	private List<SingleSidedStorage<MachinaFluidStorage>> fluidsCap = new ArrayList<>();
	private NonNullList<ItemStack> items = NonNullList.create();
	private NonNullList<Side[]> itemSides = NonNullList.create();

	private long energy;

	protected ContainerData data = new SimpleContainerData(getExtraDataSize() + DEFAULT_DATA_SIZE);

	public abstract void createStorages();

	public void itemStorage(Side[] sides) {
		this.items.add(ItemStack.EMPTY);
		this.itemSides.add(sides);
	}

	public void energyStorage(Side[] sides) {
		this.energyCap = new MultiSidedStorage<MachinaEnergyStorage>("energy", this, MachinaEnergyStorage::new, sides);
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
		ContainerHelper.loadAllItems(tag, this.items);
		this.itemSides = NonNullList.create();
		ListTag sides = tag.getList("sides", Tag.TAG_COMPOUND);
		for (int i = 0; i < sides.size(); i++) {
			this.itemSides.add(Side.deserialize(sides.getCompound(i)));
		}
		this.setChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		forEachStorage(s -> s.saveAdditional(tag));
		tag.putLong("energy", energy);
		ContainerHelper.saveAllItems(tag, this.items);
		ListTag sides = new ListTag();
		for (int i = 0; i < this.itemSides.size(); i++) {
			sides.add(Side.serialize(this.itemSides.get(i)));
		}
		tag.put("sides", sides);
		this.lockKey.addToTag(tag);
		super.saveAdditional(tag);
	}

	LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (side != null) {
			if (cap == ForgeCapabilities.ENERGY) {
				if (energyCap.isNonNullMode(side)) {
					return energyCap.get(side).cast();
				}
			} else if (cap == ForgeCapabilities.ITEM_HANDLER && !this.remove && side != null) {
				return handlers[side.ordinal()].cast();
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
	public void invalidateCaps() {
		forEachStorage(SidedStorage::invalidate);
		for (LazyOptional<? extends IItemHandler> handler : handlers) {
			handler.invalidate();
		}
		super.invalidateCaps();
	}

	@Override
	public void reviveCaps() {
		handlers = SidedInvWrapper.create(this, Direction.values());
		this.items.clear();
		this.itemSides.clear();
		this.fluidsCap.clear();
		this.createStorages();
		super.reviveCaps();
	}

	@Override
	public int getContainerSize() {
		return items.size();
	}

	@Override
	public boolean isEmpty() {
		return items.stream().allMatch(p -> p.isEmpty());
	}

	@Override
	public @NotNull ItemStack getItem(int pIndex) {
		return items.get(pIndex);
	}

	@Override
	public @NotNull ItemStack removeItem(int pIndex, int pCount) {
		ItemStack stack = ContainerHelper.removeItem(items, pIndex, pCount);
		if (!stack.isEmpty()) {
			this.setChanged();
		}
		return stack;
	}

	@Override
	public @NotNull ItemStack removeItemNoUpdate(int pIndex) {
		return ContainerHelper.takeItem(items, pIndex);
	}

	@Override
	public void setItem(int pIndex, ItemStack pStack) {
//		ItemStack itemstack = this.items.get(pIndex);
//		boolean flag = !pStack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, pStack);
		this.items.set(pIndex, pStack);
		if (pStack.getCount() > this.getMaxStackSize()) {
			pStack.setCount(this.getMaxStackSize());
		}
		this.setChanged();
	}

	@Override
	public int[] getSlotsForFace(Direction slots) {
		List<Integer> acceptable = new ArrayList<>();
		for (int i = 0; i < itemSides.size(); i++) {
			if (itemSides.get(i)[slots.ordinal()] != Side.NONE) {
				acceptable.add(i);
			}
		}
		return acceptable.stream().mapToInt(Integer::intValue).toArray();
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, Direction face) {
		return itemSides.get(slot)[face.ordinal()] == Side.INPUT;
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction face) {
		return itemSides.get(slot)[face.ordinal()] == Side.OUTPUT;
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
		this.items.clear();
		this.itemSides.clear();
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
		return this.canOpen(player) ? createMenu().apply(id, inv, this, data) : null;
	}

	protected abstract int getExtraDataSize();

	protected abstract QuadFunction<Integer, Inventory, Container, ContainerData, AbstractContainerMenu> createMenu();

	public void tick() {
	}

}