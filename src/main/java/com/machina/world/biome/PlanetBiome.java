package com.machina.world.biome;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import com.machina.api.starchart.obj.Planet;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class PlanetBiome extends Biome {

	private static final NavigableMap<Integer, BiomeCategory> categories = new TreeMap<>();

	static enum BiomeCategory {
		PLAINS(0, false),
		FOREST(10, false),
		CREATER(20, false),
		LAKE(30, true),
		OCEAN(40, true),
		DEEP_OCEAN(45, true),
		COAST(50, false),
		MOUNTAIN(60, false),
		CAVE(70, false);

		private boolean canBeFrozen;

		BiomeCategory(int starting_id, boolean canBeFrozen) {
			categories.put(starting_id, this);
			this.canBeFrozen = canBeFrozen;
		}

		public boolean canBeFrozen() {
			return canBeFrozen;
		}
	}

	public PlanetBiome(Planet planet, int id, long seed) {
		this(planet, new Random(planet.name().hashCode() + id + seed % (2 ^ 32)), id,
				categories.get(categories.floorKey(id)));
	}

	private PlanetBiome(Planet p, Random r, int i, BiomeCategory c) {
		super(createClimate(p, r, i, c), createEffects(p, r, i, c), createGeneration(p, r, i, c),
				MobSpawnSettings.EMPTY);
	}

	private static ClimateSettings createClimate(Planet p, Random r, int i, BiomeCategory c) {
		TemperatureModifier mod = c.canBeFrozen() && p.isFluidFrozen() ? TemperatureModifier.FROZEN
				: TemperatureModifier.NONE;
		boolean rains = p.doesRain();
		return new ClimateSettings(rains, 1.0f, mod, rains ? 0.5f : 0f);
	}

	private static BiomeSpecialEffects createEffects(Planet p, Random r, int i, BiomeCategory c) {
		// TODO: Add particles & grass color.
		return new BiomeSpecialEffects.Builder().fogColor(0).skyColor(0).waterColor(0).waterFogColor(0).build();
	}

	private static BiomeGenerationSettings createGeneration(Planet p, Random r, int i, BiomeCategory c) {
		// Features and carvers
		return BiomeGenerationSettings.EMPTY;
	}
}