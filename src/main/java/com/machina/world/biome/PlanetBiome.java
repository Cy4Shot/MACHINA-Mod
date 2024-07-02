package com.machina.world.biome;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import com.google.common.hash.Hashing;
import com.machina.api.starchart.PlanetType;
import com.machina.api.starchart.PlanetType.OreVein;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.math.MathUtil;
import com.machina.registration.init.SoundInit;
import com.machina.world.feature.CaveSlopeFeature;
import com.machina.world.feature.CaveSlopeFeature.CaveSlopeFeatureConfig;
import com.machina.world.feature.PlanetTreeFeature;
import com.mojang.serialization.Codec;

import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class PlanetBiome extends Biome {

	private static final NavigableMap<Integer, BiomeCategory> categories = new TreeMap<>();

	public static enum BiomeCategory implements StringRepresentable {
		MIDDLE(0),
		BEACH(5),
		RIVER(6),
		PLATEAU(7),
		PEAK(8),
		SLOPE(9),
		OCEAN(10),
		DEEP_OCEAN(11),
		CAVE(12);

		public static final Codec<BiomeCategory> CODEC = StringRepresentable.fromEnum(BiomeCategory::values);

		public int starting_id;

		BiomeCategory(int starting_id) {
			this.starting_id = starting_id;
		}

		@Override
		public String getSerializedName() {
			return name();
		}

		public boolean isUnderground() {
			return this.starting_id == CAVE.starting_id;
		}
	}

	static {
		for (BiomeCategory cat : BiomeCategory.values()) {
			categories.put(cat.starting_id, cat);
		}
	}

	public final BiomeCategory cat;

	public PlanetBiome(Planet planet, int id, long seed) {
		this(planet, generateRandom(planet.name(), id, seed), id, categories.get(categories.floorKey(id)));
	}

	private static Random generateRandom(String planetName, int id, long seed) {
		String combinedString = planetName + id + seed;
		long hash = Hashing.murmur3_128().hashString(combinedString, StandardCharsets.UTF_8).asLong();
		return new Random(hash);
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
		// TODO: Add particles & grass color & effects
		return new BiomeSpecialEffects.Builder().fogColor(0).skyColor(0).waterColor(0).waterFogColor(0)
				.backgroundMusic(SoundInit.asMusic(SoundInit.MUSIC)).build();
	}

	private static BiomeGenerationSettings createGeneration(Planet p, Random r, int i, BiomeCategory c) {
		BiomeGenerationSettings.PlainBuilder builder = new BiomeGenerationSettings.PlainBuilder();

		addUndergroundFeatures(builder, p, c, r);
		addVegetationFeatures(builder, p, c, r);

		return builder.build();
	}

	private static void addUndergroundFeatures(BiomeGenerationSettings.PlainBuilder builder, Planet p, BiomeCategory c,
			Random r) {
		PlanetType type = p.type();

		for (CaveSurface surf : CaveSurface.values())
			builder.addFeature(Decoration.UNDERGROUND_DECORATION,
					Holder.direct(
							new PlacedFeature(
									Holder.direct(new ConfiguredFeature<>(new CaveSlopeFeature(),
											new CaveSlopeFeatureConfig(surf, type.underground().rock(),
													type.rules().cave()))),
									List.of(CountPlacement.of(256), InSquarePlacement.spread(),
											HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0),
													VerticalAnchor.belowTop(256)),
											EnvironmentScanPlacement.scanningFor(surf.getDirection(),
													BlockPredicate.solid(), BlockPredicate.matchesBlocks(Blocks.AIR),
													12),
											BiomeFilter.biome()))));

		if (c.isUnderground()) {
			for (OreVein ore : type.underground().ores()) {
				builder.addFeature(Decoration.UNDERGROUND_ORES,
						Holder.direct(new PlacedFeature(
								Holder.direct(new ConfiguredFeature<>(Feature.ORE,
										new OreConfiguration(new TagMatchTest(BlockTags.OVERWORLD_CARVER_REPLACEABLES),
												ore.ore(), ore.size(), ore.exposure_removal_chance()))),
								List.of(CountPlacement.of(ore.per_chunk()), InSquarePlacement.spread(),
										HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0),
												VerticalAnchor.belowTop(256)),
										BiomeFilter.biome()))));
			}
		}
	}

	private static void addVegetationFeatures(BiomeGenerationSettings.PlainBuilder builder, Planet p, BiomeCategory c,
			Random r) {
		PlanetType type = p.type();

		if (c.equals(BiomeCategory.MIDDLE)) {

			PlanetTreeFeature.TreeType tree = MathUtil.randomInList(type.vegetation().trees().keySet(), r);

			builder.addFeature(Decoration.SURFACE_STRUCTURES,
					Holder.direct(new PlacedFeature(
							Holder.direct(new ConfiguredFeature<>(new PlanetTreeFeature(),
									new PlanetTreeFeature.PlanetTreeFeatureConfig(tree, type.rules().veg(),
											type.vegetation().trees().get(tree)))),
							List.of(RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
									HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR)))));
		}

	}
}