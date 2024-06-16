package com.machina.registration.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.machina.api.starchart.PlanetType;
import com.machina.api.starchart.PlanetType.OreVein;
import com.machina.api.starchart.PlanetType.RockType;
import com.machina.api.starchart.PlanetType.Shape;
import com.machina.api.starchart.PlanetType.Surface;
import com.machina.api.starchart.PlanetType.Underground;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class PlanetTypeInit {

	public static final List<PlanetType> PLANET_TYPES = new ArrayList<>();

	public static final PlanetType EARTHLIKE = create(new PlanetType(new Shape(60, new NoiseSettings(-64, 384, 1, 2)),
			new Surface(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.DIRT.defaultBlockState()),
			new Underground(
					new RockType(Blocks.STONE.defaultBlockState(), Blocks.STONE_STAIRS.defaultBlockState(),
							Blocks.STONE_SLAB.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()),
					List.of(new OreVein(Blocks.ANDESITE.defaultBlockState(), 32, 0f, 8)))));

	public static final PlanetType ANTHRACITE = create(new PlanetType(new Shape(60, new NoiseSettings(-64, 384, 1, 2)),
			new Surface(Blocks.COAL_ORE.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()),
			new Underground(
					new RockType(BlockInit.ANTHRACITE.get().defaultBlockState(),
							BlockInit.ANTHRACITE_STAIRS.get().defaultBlockState(),
							BlockInit.ANTHRACITE_SLAB.get().defaultBlockState(),
							Blocks.BLACK_CONCRETE_POWDER.defaultBlockState()),
					List.of(new OreVein(Blocks.COAL_BLOCK.defaultBlockState(), 32, 0f, 8)))));

	public static PlanetType create(PlanetType name) {
		PLANET_TYPES.add(name);
		return name;
	}

	public static PlanetType pickRandom(Random rand) {
		int index = rand.nextInt(PLANET_TYPES.size());
		return PLANET_TYPES.get(index);
	}
}