package com.machina.api.starchart.planet_biome;

import com.machina.api.util.math.sdf.SDF;
import com.machina.world.feature.PlanetTreeFeature.PlanetTreeFeatureConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;

@FunctionalInterface
public interface TreeMaker {
	SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p);
}