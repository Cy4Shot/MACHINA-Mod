package com.machina.block.tile.machine;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.machina.api.cap.fluid.MachinaTank;
import com.machina.api.cap.item.MachinaItemStorage;
import com.machina.api.multiblock.Multiblock;
import com.machina.api.tile.MachinaBlockEntity;
import com.machina.block.menu.MachineCaseMenu;
import com.machina.registration.init.BlockEntityInit;
import com.machina.registration.init.ItemInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class MachineCaseBlockEntity extends MachinaBlockEntity {

	public Multiblock mb;
	public boolean formed = false;

	public MachineCaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);

		this.mb = null;
	}

	public MachineCaseBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityInit.MACHINE_CASE.get(), pos, state);
	}

	@Override
	public void createStorages() {
		add(new MachinaItemStorage(1, (i, s) -> s.getItem().equals(ItemInit.BLUEPRINT.get())));
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inv) {
		return new MachineCaseMenu(id, inv, ContainerLevelAccess.create(level, worldPosition));
	}

	public void update() {
		this.formed = this.mb != null && valid();
		this.setChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.putBoolean("formed", formed);
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		this.formed = tag.getBoolean("formed");
		super.load(tag);
	}

	private boolean valid() {
		Vec3i cp = this.getBlockPos().subtract(mb.controller_pos);
		Vec3i size = mb.size;

		for (int x = 0; x < size.getX(); x++) {
			for (int y = 0; y < size.getY(); y++) {
				for (int z = 0; z < size.getZ(); z++) {
					String v = mb.structure[x][y][z];
					if (v != " ") {
						List<BlockState> allowed = List.of(mb.map.get(v));
						BlockPos pos = new BlockPos(cp.getX() + x, cp.getY() + y, cp.getZ() + z);
						BlockState state = this.level.getBlockState(pos);
						if (!allowed.contains(state))
							return false;
					}
				}
			}
		}

		return true;
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (!this.formed)
			return LazyOptional.empty();
		return super.getCapability(cap, side);
	}

	@Override
	public int getContainerSize() {
		if (!this.formed)
			return 0;
		return super.getContainerSize();
	}

	@Override
	public boolean isEmpty() {
		if (!this.formed)
			return true;
		return super.isEmpty();
	}

	@Override
	public @NotNull ItemStack getItem(int pIndex) {
		if (!this.formed)
			return ItemStack.EMPTY;
		return super.getItem(pIndex);
	}

	@Override
	public @NotNull ItemStack removeItem(int pIndex, int pCount) {
		if (!this.formed)
			return ItemStack.EMPTY;
		return super.removeItem(pIndex, pCount);
	}

	@Override
	public @NotNull ItemStack removeItemNoUpdate(int pIndex) {
		if (!this.formed)
			return ItemStack.EMPTY;
		return super.removeItemNoUpdate(pIndex);
	}

	@Override
	public void setItem(int pIndex, ItemStack pStack) {
		if (!this.formed)
			return;
		super.setItem(pIndex, pStack);
	}

	@Override
	public void clearContent() {
		if (!this.formed)
			return;
		super.clearContent();
	}

	@Override
	public int getEnergy() {
		if (!this.formed)
			return 0;
		return super.getEnergy();
	}

	@Override
	public int getMaxEnergy() {
		if (!this.formed)
			return 0;
		return super.getMaxEnergy();
	}

	@Override
	public float getEnergyProp() {
		if (!this.formed)
			return 0;
		return super.getEnergyProp();
	}

	@Override
	public MachinaTank getTank(int id) {
		if (!this.formed)
			return null;
		return super.getTank(id);
	}
}
