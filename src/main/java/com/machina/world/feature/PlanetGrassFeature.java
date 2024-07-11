package com.machina.world.feature;

import com.machina.api.util.block.WeightedStateProviderProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class PlanetGrassFeature extends Feature<PlanetGrassFeature.PlanetGrassFeatureConfig> {

	private static int XZ_SPREAD = 7;
	private static int Y_SPREAD = 3;
	private static int TRIES = 32;

	public static record PlanetGrassFeatureConfig(BlockStateProvider provider) implements FeatureConfiguration {

		public PlanetGrassFeatureConfig(WeightedStateProviderProvider.Builder builder) {
			this(builder.build());
		}

		public static final Codec<PlanetGrassFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(BlockStateProvider.CODEC.fieldOf("provider").forGetter(PlanetGrassFeatureConfig::provider))
				.apply(instance, PlanetGrassFeatureConfig::new));
	}

	public PlanetGrassFeature() {
		super(PlanetGrassFeatureConfig.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<PlanetGrassFeatureConfig> ctx) {
		RandomSource rand = ctx.random();
		BlockPos pos = ctx.origin();
		WorldGenLevel level = ctx.level();
		int i = 0;
		BlockPos.MutableBlockPos mbp = new BlockPos.MutableBlockPos();
		int j = XZ_SPREAD + 1;
		int k = Y_SPREAD + 1;

		for (int l = 0; l < TRIES; ++l) {
			mbp.setWithOffset(pos, rand.nextInt(j) - rand.nextInt(j), rand.nextInt(k) - rand.nextInt(k),
					rand.nextInt(j) - rand.nextInt(j));
			if (level.getBlockState(mbp).is(Blocks.AIR)) {
				if (placeBlock(ctx.config(), level, mbp, rand)) {
					++i;
				}
			}
		}

		return i > 0;
	}

	public boolean placeBlock(PlanetGrassFeatureConfig cfg, WorldGenLevel level, BlockPos pos, RandomSource random) {
		BlockState blockstate = cfg.provider().getState(random, pos);
		if (blockstate.canSurvive(level, pos)) {
			if (blockstate.getBlock() instanceof DoublePlantBlock) {
				if (!level.isEmptyBlock(pos.above())) {
					return false;
				}

				DoublePlantBlock.placeAt(level, blockstate, pos, 2);
			} else {
				level.setBlock(pos, blockstate, 2);
			}

			return true;
		}
		return false;
	}

}
