package com.machina.block;

import com.machina.api.util.block.BlockHelper;
import com.machina.block.tile.MachineCaseBlockEntity;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;

public class MachineCaseBlock extends HorizontalDirectionalBlock implements EntityBlock {

	public MachineCaseBlock(Properties props) {
		super(props);
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
			BlockHitResult res) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			BlockHelper.doWithTe(world, pos, MachineCaseBlockEntity.class, player::openMenu);
			return InteractionResult.CONSUME;
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return BlockEntityInit.MACHINE_CASE.get().create(pos, state);
	}
}
