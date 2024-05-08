package com.machina.world.biome;

import java.util.Random;

import com.machina.api.starchart.obj.Planet;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class PlanetBiome extends Biome {

	public PlanetBiome(Planet planet, int id, long seed) {
		this(planet, new Random(planet.name().hashCode() + id + seed % (2 ^ 32)));
	}

	private PlanetBiome(Planet p, Random r) {
		super(createClimate(p, r), createEffects(p, r), createGeneration(p, r), createMobs(p, r));
	}

	private static ClimateSettings createClimate(Planet p, Random r) {
		return new ClimateSettings(false, 1.0f, TemperatureModifier.NONE, 0f);
	}

	private static BiomeSpecialEffects createEffects(Planet p, Random r) {
		return new BiomeSpecialEffects.Builder().fogColor(0).skyColor(0).waterColor(0).waterFogColor(0).build();
	}

	private static BiomeGenerationSettings createGeneration(Planet p, Random r) {
		return BiomeGenerationSettings.EMPTY;
	}

	private static MobSpawnSettings createMobs(Planet p, Random r) {
		return MobSpawnSettings.EMPTY;
	}
}