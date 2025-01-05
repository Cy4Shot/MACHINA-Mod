package com.machina.block.tile.machine;

import com.machina.api.cap.sided.Side;
import com.machina.api.tile.MachinaBlockEntity;
import com.machina.api.util.block.BlockHelper;
import com.machina.api.util.reflect.QuadFunction;
import com.machina.block.menu.BatteryMenu;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryBlockEntity extends MachinaBlockEntity {

	public BatteryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public BatteryBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityInit.BATTERY.get(), pos, state);
	}

	@Override
	public void createStorages() {
		energyStorage(Side.INPUTS);
		itemStorage(Side.NONES);
	}

	@Override
	public void tick() {
		BlockHelper.sendEnergy(level, worldPosition, getEnergy(), 1_000, this);
	}

	@Override
	protected QuadFunction<Integer, Inventory, Container, ContainerData, AbstractContainerMenu> createMenu() {
		return BatteryMenu::new;
	}

	@Override
	public int getMaxEnergy() {
		return 1_000_000;
	}

	@Override
	protected int getExtraDataSize() {
		return 0;
	}
}
