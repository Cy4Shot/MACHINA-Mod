package com.machina.world.biome;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import com.machina.api.starchart.obj.Planet;
import com.machina.registration.init.SoundInit;
import com.mojang.serialization.Codec;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class PlanetBiome extends Biome {

	private static final NavigableMap<Integer, BiomeCategory> categories = new TreeMap<>();

	public static enum BiomeCategory {
		MIDDLE(0),
		BEACH(5),
		RIVER(6),
		PLATEAU(7),
		PEAK(8),
		SLOPE(9),
		OCEAN(10),
		DEEP_OCEAN(11),
		CAVE(12);

		public static final Codec<BiomeCategory> CODEC = Codec.STRING.xmap(val -> {
			return BiomeCategory.valueOf(BiomeCategory.class, val);
		}, BiomeCategory::name);

		public int starting_id;

		BiomeCategory(int starting_id) {
			this.starting_id = starting_id;
		}

	}

	static {
		for (BiomeCategory cat : BiomeCategory.values()) {
			categories.put(cat.starting_id, cat);
		}
	}
	
	public final BiomeCategory cat;
	

	public PlanetBiome(Planet planet, int id, long seed) {
		this(planet, new Random(planet.name().hashCode() + id + seed % (2 ^ 32)), id,
				categories.get(categories.floorKey(id)));
	}

	private PlanetBiome(Planet p, Random r, int i, BiomeCategory c) {
		super(createClimate(p, r, i, c), createEffects(p, r, i, c), createGeneration(p, r, i, c),
				MobSpawnSettings.EMPTY);
		this.cat = c;
	}

	private static ClimateSettings createClimate(Planet p, Random r, int i, BiomeCategory c) {
		boolean rains = p.doesRain();
		return new ClimateSettings(rains, 1.0f, TemperatureModifier.NONE, rains ? 0.5f : 0f);
	}

	private static BiomeSpecialEffects createEffects(Planet p, Random r, int i, BiomeCategory c) {
		// TODO: Add particles & grass color, music & effects
		int water_color = 0;
		if (c.equals(BiomeCategory.OCEAN) || c.equals(BiomeCategory.DEEP_OCEAN)) {
			water_color = 0x00FF00;
		}
		if (c.equals(BiomeCategory.RIVER)) {
			water_color = 0x0000FF;
		}
		if (c.equals(BiomeCategory.BEACH)) {
			water_color = 0xFF0000;
		}
		return new BiomeSpecialEffects.Builder().fogColor(0).skyColor(0).waterColor(water_color).waterFogColor(0)
				.backgroundMusic(SoundInit.asMusic(SoundInit.MUSIC)).build();
	}

	private static BiomeGenerationSettings createGeneration(Planet p, Random r, int i, BiomeCategory c) {
		BiomeGenerationSettings.PlainBuilder builder = new BiomeGenerationSettings.PlainBuilder();

//		Features:
//		builder.addFeature
		return builder.build();
	}
}