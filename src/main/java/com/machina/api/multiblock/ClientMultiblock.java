package com.machina.api.multiblock;

import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class ClientMultiblock implements BlockAndTintGetter {

	public Multiblock mb;

	public ClientMultiblock(Multiblock mb) {
		this.mb = mb;
	}

	@Override
	public BlockEntity getBlockEntity(BlockPos pos) {
		return null;
	}

	@Override
	public BlockState getBlockState(BlockPos pos) {
		return mb.getRenderAtPos(pos);
	}

	@Override
	public FluidState getFluidState(BlockPos pPos) {
		return Fluids.EMPTY.defaultFluidState();
	}

	public ClientMultiblockRestricted restrict(Predicate<BlockPos> has) {
		return new ClientMultiblockRestricted(mb, has);
	}

	public class ClientMultiblockRestricted extends ClientMultiblock {

		private final Predicate<BlockPos> has;

		public ClientMultiblockRestricted(Multiblock mb, Predicate<BlockPos> has) {
			super(mb);
			this.has = has;
		}

		@Override
		public BlockState getBlockState(BlockPos pPos) {
			if (!has.test(pPos)) {
				return Blocks.AIR.defaultBlockState();
			}
			return super.getBlockState(pPos);
		}
	}

	@Override
	public int getHeight() {
		return 256;
	}

	@Override
	public int getMinBuildHeight() {
		return -128;
	}

	@Override
	public float getShade(Direction p_45522_, boolean p_45523_) {
		return 0;
	}

	@Override
	public LevelLightEngine getLightEngine() {
		return null;
	}

	@Override
	public int getBlockTint(BlockPos p_45520_, ColorResolver p_45521_) {
		return 0;
	}
}