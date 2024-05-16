package com.machina.world.carver;

import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.lang3.mutable.MutableBoolean;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

public class PlanetCarver extends WorldCarver<PlanetCarverConfig> {
	public static Supplier<ConfiguredWorldCarver<?>> create(final float chance, final int length,
			final float thickness) {
		return () -> new PlanetCarver(PlanetCarverConfig.CODEC)
				.configured(new PlanetCarverConfig(chance, length, thickness));
	}

	public PlanetCarver(Codec<PlanetCarverConfig> config) {
		super(config);
	}

	public int getRange(PlanetCarverConfig c) {
		return c.length;
	}

	protected int getCaveBound() {
		return 15;
	}

	protected float getThickness(RandomSource rand, PlanetCarverConfig c) {
		float f = rand.nextFloat() * c.thickness + rand.nextFloat();
		if (rand.nextInt(10) == 0) {
			f *= rand.nextFloat() * rand.nextFloat() * 3.0F + 1.0F;
		}

		return f * 4; // Global thickness multiplier :)
	}

	protected double getYScale() {
		return 1.0D;
	}

	protected int getCaveY(RandomSource rand) {
		return rand.nextInt(rand.nextInt(120) + 8);
	}

	protected void genRoom(CarvingContext ctx, PlanetCarverConfig config, ChunkAccess chunk,
			Function<BlockPos, Holder<Biome>> biome, Aquifer aqua, CarvingMask mask, CarveSkipChecker skip, double rX,
			double rY, double rZ, float size, double yScale) {
		double d0 = 1.5D + (double) (Mth.sin(((float) Math.PI / 2F)) * size);
		this.carveEllipsoid(ctx, config, chunk, biome, aqua, rX + 1.0D, rY, rZ, d0, d0 * yScale, mask, skip);
	}

	protected void genTunnel(CarvingContext context, PlanetCarverConfig config, ChunkAccess chunk,
			Function<BlockPos, Holder<Biome>> biome, RandomSource rand, Aquifer aqua, CarvingMask mask,
			CarveSkipChecker skip, double rX, double rY, double rZ, float thickness, float rA, float rB, int start,
			int end, double yScale) {
		int i = rand.nextInt(end / 2) + end / 4;
		boolean flag = rand.nextInt(6) == 0;
		float f = 0.0F;
		float f1 = 0.0F;

		for (int j = start; j < end; ++j) {
			double d0 = 1.5D + (double) (Mth.sin((float) Math.PI * (float) j / (float) end) * thickness);
			float f2 = Mth.cos(rB);
			rX += (double) (Mth.cos(rA) * f2);
			rY += (double) Mth.sin(rB);
			rZ += (double) (Mth.sin(rA) * f2);
			rB *= (flag ? 0.92F : 0.7F);
			rB += f1 * 0.1F;
			rA += f * 0.1F;
			f1 *= 0.9F;
			f *= 0.75F;
			f1 += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 2.0F;
			f += (rand.nextFloat() - rand.nextFloat()) * rand.nextFloat() * 4.0F;
			if (j == i && thickness > 1.0F) {
				this.genTunnel(context, config, chunk, biome, rand, aqua, mask, skip, rX, rY, rZ,
						rand.nextFloat() * 0.5F + 0.5F, rA - ((float) Math.PI / 2F), rB / 3.0F, j, end, 1.0D);
				this.genTunnel(context, config, chunk, biome, rand, aqua, mask, skip, rX, rY, rZ,
						rand.nextFloat() * 0.5F + 0.5F, rA + ((float) Math.PI / 2F), rB / 3.0F, j, end, 1.0D);
				return;
			}

			if (rand.nextInt(4) != 0) {
				if (!canReach(chunk.getPos(), rX, rZ, j, end, thickness)) {
					return;
				}

				this.carveEllipsoid(context, config, chunk, biome, aqua, rX, rY, rZ, d0, d0 * yScale, mask, skip);
			}
		}

	}

	@Override
	protected boolean carveBlock(CarvingContext p_190744_, PlanetCarverConfig config, ChunkAccess chunk,
			Function<BlockPos, Holder<Biome>> p_190747_, CarvingMask p_190748_, MutableBlockPos pos,
			MutableBlockPos pos1, Aquifer p_190751_, MutableBoolean p_190752_) {
		BlockState blockstate = chunk.getBlockState(pos);
		BlockState blockstate2 = chunk.getBlockState(pos.above());

		if (!canReplaceBlock(config, blockstate, blockstate2)) {
			return false;
		} else {
			chunk.setBlockState(chunk.getPos().getBlockAt(pos.getX(), pos.getY(), pos.getZ()),
					Blocks.AIR.defaultBlockState(), false);
			pos1.setWithOffset(pos, Direction.DOWN);

			return true;
		}
	}

	protected boolean canReplaceBlock(PlanetCarverConfig config, BlockState state, BlockState aboveState) {
		return canReplaceBlock(config, state) || !aboveState.getFluidState().is(FluidTags.WATER);
	}

	@Override
	public boolean carve(CarvingContext context, PlanetCarverConfig config, ChunkAccess chunk,
			Function<BlockPos, Holder<Biome>> biome, RandomSource rand, Aquifer aqua, ChunkPos pos, CarvingMask mask) {
		int size = (this.getRange(config) * 2 - 1) * 16;
		int maxcaves = rand.nextInt(rand.nextInt(rand.nextInt(this.getCaveBound()) + 1) + 1);

		for (int k = 0; k < maxcaves; ++k) {
			double randX = (double) pos.x * 16 + rand.nextInt(16);
			double randY = (double) this.getCaveY(rand);
			double randZ = (double) pos.z * 16 + rand.nextInt(16);
			int l = 1;
			if (rand.nextInt(4) == 0) {
				float caveSize = 1.0F + rand.nextFloat() * 6.0F;
				this.genRoom(context, config, chunk, biome, aqua, mask, (x1, x2, x3, x4, x5) -> false, randX, randY,
						randZ, caveSize, 0.5D);
				l += rand.nextInt(4);
			}

			for (int k1 = 0; k1 < l; ++k1) {
				float f = rand.nextFloat() * ((float) Math.PI * 2F);
				float f3 = (rand.nextFloat() - 0.5F) / 4.0F;
				float f2 = this.getThickness(rand, config);
				int i1 = size - rand.nextInt(size / 4);
				this.genTunnel(context, config, chunk, biome, rand, aqua, mask, (x1, x2, x3, x4, x5) -> false, randX,
						randY, randZ, f2, f, f3, 0, i1, this.getYScale());
			}
		}

		return true;
	}

	@Override
	public boolean isStartChunk(PlanetCarverConfig config, RandomSource rand) {
		return rand.nextFloat() <= config.probability;
	}
}
