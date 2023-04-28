package com.machina.block.tile;

import com.machina.block.container.TankContainer;
import com.machina.block.container.base.IMachinaContainerProvider;
import com.machina.block.tile.base.CustomTE;
import com.machina.capability.MachinaTank;
import com.machina.capability.CustomFluidStorage;
import com.machina.capability.CustomItemStorage;
import com.machina.registration.init.TileEntityInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class TankTileEntity extends CustomTE implements ITickableTileEntity, IMachinaContainerProvider {

	public TankTileEntity() {
		super(TileEntityInit.TANK.get());
	}

	CustomItemStorage items;
	CustomFluidStorage fluid;

	@Override
	public void createStorages() {
		this.items = add(new CustomItemStorage(2));
		this.fluid = add(new MachinaTank(this, 10000, p -> true, true, 0));
	}

	@Override
	public void tick() {
		if (this.level.isClientSide())
			return;

		// Update Slots
		FluidActionResult res0 = FluidUtil.tryEmptyContainerAndStow(this.items.getStackInSlot(0), fluid, null,
				Integer.MAX_VALUE, null, true);
		if (res0.isSuccess()) {
			this.items.setStackInSlot(0, res0.getResult());
		}

		FluidActionResult res1 = FluidUtil.tryFillContainerAndStow(this.items.getStackInSlot(1), fluid, null,
				Integer.MAX_VALUE, null, true);
		if (res1.isSuccess()) {
			this.items.setStackInSlot(1, res1.getResult());
		}
	}

	@Override
	public Container createMenu(int id, PlayerInventory p, PlayerEntity e) {
		return new TankContainer(id, p, this);
	}

	public FluidStack getFluid() {
		return fluid.getFluidInTank(0);
	}

	public int stored() {
		return getFluid().getAmount();
	}

	public int capacity() {
		return fluid.getTankCapacity(0);
	}
	
	public float propFull() {
		return (float) stored() / (float) capacity();
	}
}