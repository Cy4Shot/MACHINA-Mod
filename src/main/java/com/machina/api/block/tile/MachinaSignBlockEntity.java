package com.machina.api.block.tile;

import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MachinaSignBlockEntity extends SignBlockEntity {

	public MachinaSignBlockEntity(BlockPos pos, BlockState state) {
		super(pos, state);
	}

	@Override
	public BlockEntityType<?> getType() {
		return BlockEntityInit.SIGN.get();
	}
}