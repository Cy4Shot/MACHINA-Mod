package com.machina.block;

import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class MachinaHangingSignBlock extends CeilingHangingSignBlock {
	public MachinaHangingSignBlock(Properties props, WoodType type) {
		super(props, type);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityInit.HANGING_SIGN.get().create(pos, state);
	}
}