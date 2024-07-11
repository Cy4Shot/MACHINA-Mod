package com.machina.api.block;

import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class MachinaSignBlock extends StandingSignBlock {
	public MachinaSignBlock(Properties props, WoodType type) {
		super(props, type);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityInit.SIGN.get().create(pos, state);
	}
}
