package com.machina.block.tile.connector;

import com.machina.api.tile.ConnectorBlockEntity;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyCableBlockEntity extends ConnectorBlockEntity {

	public EnergyCableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	public EnergyCableBlockEntity(BlockPos pos, BlockState state) {
		super(BlockEntityInit.ENERGY_CABLE.get(), pos, state);
	}

}
