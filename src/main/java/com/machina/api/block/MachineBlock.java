package com.machina.api.block;

import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.api.util.block.BlockHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public abstract class MachineBlock extends HorizontalDirectionalBlock implements EntityBlock {
	protected MachineBlock(Properties props) {
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
			BlockHelper.doWithTe(world, pos, getBlockEntityClass(),
					te -> NetworkHooks.openScreen((ServerPlayer) player, te, te.getBlockPos()));
			return InteractionResult.CONSUME;
		}
	}

	public abstract Class<? extends MachinaBlockEntity> getBlockEntityClass();

	public abstract BlockEntityType<?> getBlockEntityType();

	protected boolean isTickable() {
		return false;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return getBlockEntityType().create(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		if (isTickable() && getBlockEntityType() == type) {
			return (level1, pos, state1, blockEntity) -> {
				((MachinaBlockEntity) blockEntity).tick();
			};
		}
		return EntityBlock.super.getTicker(level, state, type);
	}
}
