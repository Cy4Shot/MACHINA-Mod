package com.machina.block.tile.machine;

import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.api.util.reflect.QuadFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GrinderBlockEntity extends MachinaBlockEntity {

	public GrinderBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void createStorages() {
		// TODO Auto-generated method stub

	}

	@Override
	public int getMaxEnergy() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getExtraDataSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected QuadFunction<Integer, Inventory, Container, ContainerData, AbstractContainerMenu> createMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean activeModel() {
		// TODO Auto-generated method stub
		return false;
	}

}
