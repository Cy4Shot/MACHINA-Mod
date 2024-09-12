package com.machina.world.feature;

import com.machina.api.starchart.obj.Planet;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings;
import com.machina.api.util.PlanetHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class PlanetLakeFeature extends Feature<PlanetLakeFeature.PlanetLakeFeatureConfig> {

	private static final BlockState AIR = Blocks.CAVE_AIR.defaultBlockState();

	public record PlanetLakeFeatureConfig(PlanetBiomeSettings settings) implements FeatureConfiguration {
		public static final Codec<PlanetLakeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(PlanetBiomeSettings.CODEC.fieldOf("settings").forGetter(PlanetLakeFeatureConfig::settings))
				.apply(instance, PlanetLakeFeatureConfig::new));
	}

	public PlanetLakeFeature() {
		super(PlanetLakeFeatureConfig.CODEC);
	}

	public boolean place(FeaturePlaceContext<PlanetLakeFeature.PlanetLakeFeatureConfig> ctx) {
		BlockPos blockpos = ctx.origin();
		WorldGenLevel worldgenlevel = ctx.level();
		RandomSource randomsource = ctx.random();
		PlanetLakeFeature.PlanetLakeFeatureConfig cfg = ctx.config();

		Planet p = PlanetHelper.getPlanetFor(worldgenlevel.getLevel());
		BlockState fluid = p.getDominantLiquidBodyBlock();
		if (fluid == null || fluid.getFluidState().isEmpty()) {
			return false;
		}

		if (blockpos.getY() <= worldgenlevel.getMinBuildHeight() + 4) {
			return false;
		} else {
			blockpos = blockpos.below(4);
			boolean[] aboolean = new boolean[2048];
			int i = randomsource.nextInt(4) + 4;

			for (int j = 0; j < i; ++j) {
				double d0 = randomsource.nextDouble() * 6.0D + 3.0D;
				double d1 = randomsource.nextDouble() * 4.0D + 2.0D;
				double d2 = randomsource.nextDouble() * 6.0D + 3.0D;
				double d3 = randomsource.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
				double d4 = randomsource.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
				double d5 = randomsource.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

				for (int l = 1; l < 15; ++l) {
					for (int i1 = 1; i1 < 15; ++i1) {
						for (int j1 = 1; j1 < 7; ++j1) {
							double d6 = ((double) l - d3) / (d0 / 2.0D);
							double d7 = ((double) j1 - d4) / (d1 / 2.0D);
							double d8 = ((double) i1 - d5) / (d2 / 2.0D);
							double d9 = d6 * d6 + d7 * d7 + d8 * d8;
							if (d9 < 1.0D) {
								aboolean[(l * 16 + i1) * 8 + j1] = true;
							}
						}
					}
				}
			}

			for (int k1 = 0; k1 < 16; ++k1) {
				for (int k = 0; k < 16; ++k) {
					for (int l2 = 0; l2 < 8; ++l2) {
						boolean flag = !aboolean[(k1 * 16 + k) * 8 + l2]
								&& (k1 < 15 && aboolean[((k1 + 1) * 16 + k) * 8 + l2]
										|| k1 > 0 && aboolean[((k1 - 1) * 16 + k) * 8 + l2]
										|| k < 15 && aboolean[(k1 * 16 + k + 1) * 8 + l2]
										|| k > 0 && aboolean[(k1 * 16 + (k - 1)) * 8 + l2]
										|| l2 < 7 && aboolean[(k1 * 16 + k) * 8 + l2 + 1]
										|| l2 > 0 && aboolean[(k1 * 16 + k) * 8 + (l2 - 1)]);
						if (flag) {
							BlockState blockstate3 = worldgenlevel.getBlockState(blockpos.offset(k1, l2, k));
							if (l2 >= 4 && blockstate3.liquid()) {
								return false;
							}

							if (l2 < 4 && !blockstate3.isSolid()
									&& worldgenlevel.getBlockState(blockpos.offset(k1, l2, k)) != fluid) {
								return false;
							}
						}
					}
				}
			}

			for (int l1 = 0; l1 < 16; ++l1) {
				for (int i2 = 0; i2 < 16; ++i2) {
					for (int i3 = 0; i3 < 8; ++i3) {
						if (aboolean[(l1 * 16 + i2) * 8 + i3]) {
							BlockPos blockpos1 = blockpos.offset(l1, i3, i2);
							if (this.canReplaceBlock(worldgenlevel.getBlockState(blockpos1))) {
								boolean flag1 = i3 >= 4;
								worldgenlevel.setBlock(blockpos1, flag1 ? AIR : fluid, 2);
								if (flag1) {
									worldgenlevel.scheduleTick(blockpos1, AIR.getBlock(), 0);
									this.markAboveForPostProcessing(worldgenlevel, blockpos1);
								}
							}
						}
					}
				}
			}

			BlockState blockstate2 = cfg.settings().base();
			if (!blockstate2.isAir()) {
				for (int j2 = 0; j2 < 16; ++j2) {
					for (int j3 = 0; j3 < 16; ++j3) {
						for (int l3 = 0; l3 < 8; ++l3) {
							boolean flag2 = !aboolean[(j2 * 16 + j3) * 8 + l3]
									&& (j2 < 15 && aboolean[((j2 + 1) * 16 + j3) * 8 + l3]
											|| j2 > 0 && aboolean[((j2 - 1) * 16 + j3) * 8 + l3]
											|| j3 < 15 && aboolean[(j2 * 16 + j3 + 1) * 8 + l3]
											|| j3 > 0 && aboolean[(j2 * 16 + (j3 - 1)) * 8 + l3]
											|| l3 < 7 && aboolean[(j2 * 16 + j3) * 8 + l3 + 1]
											|| l3 > 0 && aboolean[(j2 * 16 + j3) * 8 + (l3 - 1)]);
							if (flag2 && (l3 < 4 || randomsource.nextInt(2) != 0)) {
								BlockState blockstate = worldgenlevel.getBlockState(blockpos.offset(j2, l3, j3));
								if (blockstate.isSolid() && !blockstate.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE)) {
									BlockPos blockpos3 = blockpos.offset(j2, l3, j3);
									worldgenlevel.setBlock(blockpos3, blockstate2, 2);
									this.markAboveForPostProcessing(worldgenlevel, blockpos3);
								}
							}
						}
					}
				}
			}

			return true;
		}
	}

	protected void markAboveForPostProcessing(WorldGenLevel p_159740_, BlockPos p_159741_) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159741_.mutable();

		for (int i = 0; i < 2; ++i) {
			blockpos$mutableblockpos.move(Direction.UP);
			if (p_159740_.getBlockState(blockpos$mutableblockpos).isAir()) {
				return;
			}

			p_159740_.getChunk(blockpos$mutableblockpos).markPosForPostprocessing(blockpos$mutableblockpos);
		}

	}

	private boolean canReplaceBlock(BlockState p_190952_) {
		return !p_190952_.is(BlockTags.FEATURES_CANNOT_REPLACE);
	}

}
