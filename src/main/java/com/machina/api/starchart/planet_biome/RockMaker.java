package com.machina.api.starchart.planet_biome;

import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeBigRock;
import com.machina.api.util.math.sdf.SDF;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;

public interface RockMaker {

	SDF build(PlanetBiomeBigRock config, RandomSource random, WorldGenLevel l, BlockPos p);
}