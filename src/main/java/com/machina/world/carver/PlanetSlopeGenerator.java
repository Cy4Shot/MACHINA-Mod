package com.machina.world.carver;

import com.machina.api.util.BlockHelper;
import com.machina.world.PlanetChunkGenerator;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class PlanetSlopeGenerator {
	public static void decorateAt(ChunkAccess chunk, BlockPos pos, PlanetChunkGenerator gen, RandomState rand,
			NormalNoise noise, boolean allowVerticalConnections) {
		for (Direction dir : Direction.values()) {

			BlockPos adjecent = pos.relative(dir);

			if (canGenSide(chunk, chunk.getBlockState(adjecent), dir)) {
				switch (dir) {
				case UP:
					// Gen Ceil

					break;
				case DOWN:
					// Gen Floor

					final double d = getNoise(noise, adjecent, 0.125d);
					if (d < -0.3d)
						chunk.setBlockState(adjecent, Blocks.ANDESITE.defaultBlockState(), false);
					break;
				default:
					// Gen Wall

					break;
				}
			}
		}

		for (Direction dir : Direction.values()) {
			BlockPos adjecent = pos.relative(dir);
			if (canGenExtra(chunk.getBlockState(pos), chunk.getBlockState(adjecent))) {
				switch (dir) {
				case UP:
					// Gen Ceil Extra

					break;
				case DOWN:
					// Gen Floor Extra

					break;
				default:

					if (dir == Direction.EAST && adjecent.getX() % 16 == 0)
						break;
					if (dir == Direction.SOUTH && adjecent.getZ() % 16 == 0)
						break;
					if (dir == Direction.WEST && (adjecent.getX() + 1) % 16 == 0)
						break;
					if (dir == Direction.NORTH && (adjecent.getZ() + 1) % 16 == 0)
						break;

					genSlope(chunk, pos, dir, gen, rand.oreRandom().at(pos), allowVerticalConnections);
					break;
				}
			}
		}
	}

	public static void genSlope(ChunkAccess world, BlockPos pos, Direction wallDir, PlanetChunkGenerator gen,
			RandomSource randomSource, boolean allowVerticalConnections) {

		BlockPos.MutableBlockPos mutPos = new BlockPos.MutableBlockPos().set(pos);

		mutPos.set(pos).move(0, -1, 0);
		final boolean isDown = carvable(world.getBlockState(mutPos));
		mutPos.set(pos).move(0, 1, 0);
		final boolean isUp = carvable(world.getBlockState(mutPos));
		if (!isDown && !isUp)
			return;

		if (!allowVerticalConnections) {
			if (world.getBlockState(pos.north()).isAir() && world.getBlockState(pos.east()).isAir()
					&& world.getBlockState(pos.south()).isAir() && world.getBlockState(pos.west()).isAir())
				return;
		}

		mutPos.set(pos);
		int air = 0;
		Direction oppDir = wallDir.getOpposite();
		while (air < 16 && !world.getBlockState(mutPos.move(oppDir)).isFaceSturdy(world, mutPos, wallDir))
			++air;

		int chance = 4;
		if (air <= 3)
			chance = 2;
		if (randomSource.nextInt(10) >= chance)
			return;
		if (randomSource.nextInt(5) <= 2)
			genBlock(world, pos,
					BlockHelper.waterlog(Blocks.STONE_STAIRS.defaultBlockState()
							.setValue(BlockStateProperties.HORIZONTAL_FACING, wallDir)
							.setValue(BlockStateProperties.HALF, isDown ? Half.BOTTOM : Half.TOP), world, pos));
		else
			genBlock(world, pos, BlockHelper.waterlog(Blocks.STONE_SLAB.defaultBlockState()
					.setValue(BlockStateProperties.SLAB_TYPE, isDown ? SlabType.BOTTOM : SlabType.TOP), world, pos));
	}

	public static boolean genBlock(ChunkAccess world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state, false);
		return true;
	}

	public static double getNoise(NormalNoise noise, BlockPos pos, double frequency) {
		return noise.getValue((double) pos.getX() * frequency, (double) pos.getY() * frequency,
				(double) pos.getZ() * frequency);
	}

	public static boolean canGenSide(ChunkAccess chunk, BlockState state, Direction dir) {
		return carvable(state);
	}

	public static boolean canGenExtra(BlockState state, BlockState sState) {
		return state.isAir() && carvable(sState);
	}

	public static boolean carvable(BlockState state) {
		return state.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES);
	}
}
