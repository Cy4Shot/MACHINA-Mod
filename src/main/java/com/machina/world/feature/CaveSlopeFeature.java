package com.machina.world.feature;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import com.machina.world.carver.PlanetSlopeGenerator;
import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class CaveSlopeFeature extends Feature<CaveSlopeFeature.CaveSlopeFeatureConfig> {

	public static record CaveSlopeFeatureConfig(CaveSurface surface) implements FeatureConfiguration {
		public static final Codec<CaveSlopeFeatureConfig> CODEC = CaveSurface.CODEC.fieldOf("surface")
				.xmap(CaveSlopeFeatureConfig::new, CaveSlopeFeatureConfig::surface).codec();
	}

	public CaveSlopeFeature() {
		super(CaveSlopeFeatureConfig.CODEC);
	}

	public boolean place(FeaturePlaceContext<CaveSlopeFeatureConfig> conf) {
		WorldGenLevel worldgenlevel = conf.level();
		RandomSource randomsource = conf.random();
		BlockPos blockpos = conf.origin();
		Predicate<BlockState> predicate = (p_204782_) -> {
			return PlanetSlopeGenerator.carvable(p_204782_);
		};
		int radius = 4;
		Set<BlockPos> set = this.placeGroundPatch(worldgenlevel, randomsource, blockpos, predicate, radius, radius,
				conf.config().surface());
		this.distributeVegetation(conf, worldgenlevel, randomsource, set, radius, radius, conf.config().surface());
		return !set.isEmpty();
	}

	protected Set<BlockPos> placeGroundPatch(WorldGenLevel p_225311_, RandomSource p_225313_, BlockPos p_225314_,
			Predicate<BlockState> p_225315_, int p_225316_, int p_225317_, CaveSurface surf) {
		BlockPos.MutableBlockPos blockpos$mutableblockpos = p_225314_.mutable();
		BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos$mutableblockpos.mutable();
		Direction direction = surf.getDirection();
		Direction direction1 = direction.getOpposite();
		Set<BlockPos> set = new HashSet<>();

		for (int i = -p_225316_; i <= p_225316_; ++i) {
			boolean flag = i == -p_225316_ || i == p_225316_;

			for (int j = -p_225317_; j <= p_225317_; ++j) {
				boolean flag1 = j == -p_225317_ || j == p_225317_;
				boolean flag2 = flag || flag1;
				boolean flag3 = flag && flag1;
				boolean flag4 = flag2 && !flag3;
				if (!flag3 && (!flag4 || 0.3f != 0.0F && !(p_225313_.nextFloat() > 0.3f))) {
					blockpos$mutableblockpos.setWithOffset(p_225314_, i, 0, j);

					for (int k = 0; p_225311_.isStateAtPosition(blockpos$mutableblockpos,
							BlockBehaviour.BlockStateBase::isAir) && k < 5; ++k) {
						blockpos$mutableblockpos.move(direction);
					}

					for (int i1 = 0; p_225311_.isStateAtPosition(blockpos$mutableblockpos, (p_284926_) -> {
						return !p_284926_.isAir();
					}) && i1 < 5; ++i1) {
						blockpos$mutableblockpos.move(direction1);
					}

					blockpos$mutableblockpos1.setWithOffset(blockpos$mutableblockpos, surf.getDirection());
					BlockState blockstate = p_225311_.getBlockState(blockpos$mutableblockpos1);
					if (p_225311_.isEmptyBlock(blockpos$mutableblockpos) && blockstate.isFaceSturdy(p_225311_,
							blockpos$mutableblockpos1, surf.getDirection().getOpposite())) {
						set.add(blockpos$mutableblockpos1.immutable());
					}
				}
			}
		}

		return set;
	}

	protected void distributeVegetation(FeaturePlaceContext<CaveSlopeFeatureConfig> p_225331_, WorldGenLevel p_225332_,
			RandomSource p_225334_, Set<BlockPos> p_225335_, int p_225336_, int p_225337_, CaveSurface surf) {
		for (BlockPos blockpos : p_225335_) {
			if (p_225334_.nextFloat() < 0.8) {
				this.placeVegetation(p_225332_, p_225331_.chunkGenerator(), p_225334_, blockpos, surf);
			}
		}

	}

	protected boolean placeVegetation(WorldGenLevel p_225318_, ChunkGenerator p_225320_, RandomSource p_225321_,
			BlockPos p_225322_, CaveSurface surf) {
		return PlanetSlopeGenerator.decorateAt(p_225318_, p_225322_.relative(surf.getDirection().getOpposite()),
				p_225321_, NormalNoise.create(p_225321_, -8, 0.5, 1, 2, 1, 2, 1, 0, 2, 0), true);
	}

}
