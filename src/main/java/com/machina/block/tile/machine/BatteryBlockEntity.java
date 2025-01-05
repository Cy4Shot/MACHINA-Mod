package com.machina.block.tile.machine;

import com.machina.api.cap.energy.IEnergyBlockEntity;
import com.machina.api.cap.energy.MachinaEnergyStorage;
import com.machina.api.cap.item.MachinaItemStorage;
import com.machina.api.tile.MachinaBlockEntity;
import com.machina.block.menu.BatteryMenu;
import com.machina.registration.init.BlockEntityInit;
import com.machina.registration.init.ItemInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryBlockEntity extends MachinaBlockEntity implements IEnergyBlockEntity {

	public BatteryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public BatteryBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityInit.BATTERY.get(), pos, state);
	}

	@Override
	public void createStorages() {
		add(new MachinaEnergyStorage(this, 1_000_000, 1000));
		add(new MachinaItemStorage(1, (i, s) -> s.getItem().equals(ItemInit.BLUEPRINT.get())));
	}

	@Override
	public boolean isGenerator() {
		return false;
	}

	@Override
	protected AbstractContainerMenu createMenu(int id, Inventory inv) {
		return new BatteryMenu(id, inv, ContainerLevelAccess.create(level, worldPosition));
	}
}
