package com.machina.world.biome;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import com.google.common.hash.Hashing;
import com.machina.api.starchart.PlanetType;
import com.machina.api.starchart.PlanetType.OreVein;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.math.MathUtil;
import com.machina.registration.init.SoundInit;
import com.machina.world.feature.PlanetCaveSlopeFeature;
import com.machina.world.feature.PlanetCaveSlopeFeature.CaveSlopeFeatureConfig;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.BlockPileFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
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
			add(builder, Decoration.UNDERGROUND_DECORATION, new PlanetCaveSlopeFeature(),
					new CaveSlopeFeatureConfig(surf, type.underground().rock(), type.rules().cave()),
					CountPlacement.of(256), InSquarePlacement.spread(),
					HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(256)),
					EnvironmentScanPlacement.scanningFor(surf.getDirection(), BlockPredicate.solid(),
							BlockPredicate.matchesBlocks(Blocks.AIR), 12),
					BiomeFilter.biome());

		if (c.isUnderground()) {
			for (OreVein ore : type.underground().ores()) {
				add(builder, Decoration.UNDERGROUND_ORES, Feature.ORE,
						new OreConfiguration(new TagMatchTest(BlockTags.OVERWORLD_CARVER_REPLACEABLES), ore.ore(),
								ore.size(), ore.exposure_removal_chance()),
						CountPlacement.of(ore.per_chunk()), InSquarePlacement.spread(),
						HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(256)),
						BiomeFilter.biome());
			}
		}
	}

	private static void addVegetationFeatures(BiomeGenerationSettings.PlainBuilder builder, Planet p, BiomeCategory c,
			Random r) {
		PlanetType type = p.type();

		if (c.equals(BiomeCategory.MIDDLE)) {

			PlanetTreeFeature.TreeType tree = MathUtil.randomInList(type.vegetation().trees().keySet(), r);
			add(builder, Decoration.SURFACE_STRUCTURES, new PlanetTreeFeature(),
					new PlanetTreeFeature.PlanetTreeFeatureConfig(tree, type.rules().veg(),
							type.vegetation().trees().get(tree)),
					RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
					HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR));

			PlanetTreeFeature.TreeType tree2 = MathUtil.randomInList(type.vegetation().trees().keySet(), r);
			add(builder, Decoration.SURFACE_STRUCTURES, new PlanetTreeFeature(),
					new PlanetTreeFeature.PlanetTreeFeatureConfig(tree2, type.rules().veg(),
							type.vegetation().trees().get(tree2)),
					RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
					HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR));

			for (BlockState state : type.vegetation().bushes())
				add(builder, Decoration.VEGETAL_DECORATION, new BlockPileFeature(BlockPileConfiguration.CODEC),
						new BlockPileConfiguration(BlockStateProvider.simple(state)), CountPlacement.of(2),
						InSquarePlacement.spread(), HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR));
		}
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void add(
			BiomeGenerationSettings.PlainBuilder builder, Decoration decorationLevel, F feature, FC config,
			PlacementModifier... placements) {
		builder.addFeature(decorationLevel, Holder.direct(
				new PlacedFeature(Holder.direct(new ConfiguredFeature<>(feature, config)), Arrays.asList(placements))));
	}
}