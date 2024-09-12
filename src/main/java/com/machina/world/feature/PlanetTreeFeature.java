package com.machina.world.feature;

import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeTree;
import com.machina.api.util.math.sdf.SDF;
import com.machina.registration.init.RegistryInit;
import com.machina.registration.init.TagInit.BlockTagInit;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class PlanetTreeFeature extends Feature<PlanetTreeFeature.PlanetTreeFeatureConfig> {

	@FunctionalInterface
	public interface TreeMaker {
		SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p);
	}

	public record PlanetTreeFeatureConfig(PlanetBiomeTree tree) implements FeatureConfiguration {
		public static final Codec<PlanetTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(PlanetBiomeTree.CODEC.fieldOf("tree").forGetter(PlanetTreeFeatureConfig::tree))
				.apply(instance, PlanetTreeFeatureConfig::new));

		public TreeMaker getTree() {
			return RegistryInit.TREE_REGISTRY.get().getValue(tree.tree());
		}
	}

	public PlanetTreeFeature() {
		super(PlanetTreeFeatureConfig.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<PlanetTreeFeatureConfig> ctx) {
		PlanetTreeFeatureConfig cfg = ctx.config();
		BlockPos origin = ctx.origin();
		if (!ctx.level().getBlockState(origin.below()).is(BlockTagInit.PLANET_GROWABLE)) {
			return false;
		}
		TreeMaker maker = cfg.getTree();
		SDF tree = maker.build(cfg, ctx.random(), ctx.level(), ctx.origin());
		if (tree == null)
			return false;
		tree.fillRecursive(ctx.level(), origin);
		return true;
	}
}