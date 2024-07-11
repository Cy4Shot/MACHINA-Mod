package com.machina.world.biome;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import com.google.common.hash.Hashing;
import com.machina.api.starchart.PlanetType;
import com.machina.api.starchart.PlanetType.Grass;
import com.machina.api.starchart.PlanetType.Lakes;
import com.machina.api.starchart.PlanetType.OreVein;
import com.machina.api.starchart.PlanetType.Tree;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.math.MathUtil;
import com.machina.registration.init.SoundInit;
import com.machina.world.feature.PlanetBushFeature;
import com.machina.world.feature.PlanetBushFeature.PlanetBushFeatureConfig;
import com.machina.world.feature.PlanetCaveSlopeFeature;
import com.machina.world.feature.PlanetCaveSlopeFeature.PlanetCaveSlopeFeatureConfig;
import com.machina.world.feature.PlanetGrassFeature;
import com.machina.world.feature.PlanetGrassFeature.PlanetGrassFeatureConfig;
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
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
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
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
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

		// Cave Slope
		for (CaveSurface surf : CaveSurface.values())
			add(builder, Decoration.UNDERGROUND_DECORATION, new PlanetCaveSlopeFeature(),
					new PlanetCaveSlopeFeatureConfig(surf, type.underground().rock(), type.rules().cave()), count(256),
					spread(), range(0, 256), EnvironmentScanPlacement.scanningFor(surf.getDirection(),
							BlockPredicate.solid(), BlockPredicate.matchesBlocks(Blocks.AIR), 12),
					biome());

		if (c.isUnderground()) {

			// Ores
			for (OreVein ore : type.underground().ores()) {
				add(builder, Decoration.UNDERGROUND_ORES, Feature.ORE,
						new OreConfiguration(new TagMatchTest(BlockTags.OVERWORLD_CARVER_REPLACEABLES), ore.ore(),
								ore.size(), ore.exposure_removal_chance()),
						count(ore.per_chunk()), spread(), range(0, 256), biome());
			}
		}
	}

	private static void addVegetationFeatures(BiomeGenerationSettings.PlainBuilder builder, Planet p, BiomeCategory c,
			Random r) {
		PlanetType type = p.type();

		if (c.equals(BiomeCategory.MIDDLE)) {

			if (type.vegetation().trees().size() > 0) {
				// Tree A
				PlanetTreeFeature.TreeType tree = MathUtil.randomInList(type.vegetation().trees().keySet(), r);
				Tree t1 = type.vegetation().trees().get(tree);
				add(builder, Decoration.SURFACE_STRUCTURES, new PlanetTreeFeature(),
						new PlanetTreeFeature.PlanetTreeFeatureConfig(tree, type.rules().veg(), t1), every(t1.every()),
						spread(), onFloor());

				// Tree B
				PlanetTreeFeature.TreeType tree2 = MathUtil.randomInList(type.vegetation().trees().keySet(), r);
				Tree t2 = type.vegetation().trees().get(tree);
				add(builder, Decoration.SURFACE_STRUCTURES, new PlanetTreeFeature(),
						new PlanetTreeFeature.PlanetTreeFeatureConfig(tree2, type.rules().veg(), t2), every(t2.every()),
						spread(), onFloor());

			}

			// Bushes
			for (PlanetBushFeatureConfig bush : type.vegetation().bushes())
				add(builder, Decoration.VEGETAL_DECORATION, new PlanetBushFeature(), bush, count(bush.perchunk()),
						spread(), onFloor());
		}

		if (!c.isUnderground()) {

			// Grass
			if (!type.vegetation().grass().isEmpty()) {
				Grass grass = MathUtil.randomInList(type.vegetation().grass(), r);
				add(builder, Decoration.VEGETAL_DECORATION, new PlanetGrassFeature(),
						new PlanetGrassFeatureConfig(grass.provider()),
						NoiseThresholdCountPlacement.of(-0.8f, grass.min(), grass.max()), spread(), onSurface(),
						biome());
			}

			// Lakes
			if (type.surface().lakes().isPresent()) {
				Lakes lakes = type.surface().lakes().get();
				BlockState fluid = p.getDominantLiquidBodyBlock();
				if (fluid != null && r.nextFloat() < lakes.chance()) {
					add(builder, Decoration.LAKES, Feature.LAKE,
							new LakeFeature.Configuration(BlockStateProvider.simple(fluid),
									BlockStateProvider.simple(type.underground().rock().base())),
							every(lakes.rarity()), spread(), onSurface(), biome());
				}
			}
		}
	}

	private static <FC extends FeatureConfiguration, F extends Feature<FC>> void add(
			BiomeGenerationSettings.PlainBuilder builder, Decoration decorationLevel, F feature, FC config,
			PlacementModifier... placements) {
		builder.addFeature(decorationLevel, Holder.direct(
				new PlacedFeature(Holder.direct(new ConfiguredFeature<>(feature, config)), Arrays.asList(placements))));
	}

	private static RarityFilter every(int every) {
		return RarityFilter.onAverageOnceEvery(every);
	}

	private static CountPlacement count(int count) {
		return CountPlacement.of(count);
	}

	private static InSquarePlacement spread() {
		return InSquarePlacement.spread();
	}

	private static HeightmapPlacement onFloor() {
		return HeightmapPlacement.onHeightmap(Types.OCEAN_FLOOR_WG);
	}

	private static HeightmapPlacement onSurface() {
		return HeightmapPlacement.onHeightmap(Types.WORLD_SURFACE_WG);
	}

	private static BiomeFilter biome() {
		return BiomeFilter.biome();
	}

	private static HeightRangePlacement range(int min, int max) {
		return HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(min), VerticalAnchor.belowTop(max));
	}
}