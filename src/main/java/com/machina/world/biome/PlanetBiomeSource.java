package com.machina.world.biome;

import java.util.ArrayList;
import java.util.List;

import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.PlanetHelper;
import com.machina.world.biome.PlanetBiome.BiomeCategory;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraftforge.server.ServerLifecycleHooks;

public class PlanetBiomeSource {

	private static final Parameter ZERO = Parameter.point(0.0F);
	private static final Parameter ONE = Parameter.point(1.0F);
	private static final Parameter FULL = Parameter.span(-1.0F, 1.0F);
	private static final Parameter[] variants = new Parameter[] { Parameter.span(-1.0F, -0.45F),
			Parameter.span(-0.45F, -0.15F), Parameter.span(-0.15F, 0.2F), Parameter.span(0.2F, 0.55F),
			Parameter.span(0.55F, 1.0F) };
	private static final Parameter[] erosions = new Parameter[] { Parameter.span(-1.0F, -0.78F),
			Parameter.span(-0.78F, -0.375F), Parameter.span(-0.375F, -0.2225F), Parameter.span(-0.2225F, 0.05F),
			Parameter.span(0.05F, 0.45F), Parameter.span(0.45F, 0.55F), Parameter.span(0.55F, 1.0F) };
	private static final Parameter deepOceanContinentalness = Parameter.span(-1.05F, -0.455F);
	private static final Parameter oceanContinentalness = Parameter.span(-0.455F, -0.19F);
	private static final Parameter coastContinentalness = Parameter.span(-0.19F, -0.11F);
	private static final Parameter inlandContinentalness = Parameter.span(-0.11F, 0.55F);
	private static final Parameter nearInlandContinentalness = Parameter.span(-0.11F, 0.03F);
	private static final Parameter midInlandContinentalness = Parameter.span(0.03F, 0.3F);
	private static final Parameter farInlandContinentalness = Parameter.span(0.3F, 1.0F);
	private final List<Pair<ParameterPoint, Holder<Biome>>> biomes;
	private final Planet planet;
	private final ResourceKey<LevelStem> key;

	public PlanetBiomeSource(ResourceKey<LevelStem> key, long seed) {
		biomes = new ArrayList<>();
		planet = Starchart.system(seed).planets().get(PlanetHelper.getIdDim(key));
		this.key = key;
		addLandBiomes(seed);
		addOceanBiomes(seed);
		addUndergroundBiomes(seed);
	}

	public static List<ParameterPoint> spawnTarget() {
		return List.of(
				new ParameterPoint(FULL, FULL, Parameter.span(inlandContinentalness, FULL), FULL, ZERO,
						Parameter.span(-1.0F, -0.16F), 0L),
				new ParameterPoint(FULL, FULL, Parameter.span(inlandContinentalness, FULL), FULL, ZERO,
						Parameter.span(0.16F, 1.0F), 0L));
	}

	private Holder<Biome> getBiome(BiomeCategory cat, long seed) {
		return getBiome(cat, 0, seed);
	}

	private Holder<Biome> getBiome(BiomeCategory cat, int variant, long seed) {
		int id = cat.starting_id + variant;
		return PlanetBiomeRegistrationHandler.getOrCreate(ServerLifecycleHooks.getCurrentServer(),
				() -> new PlanetBiome(planet, id, seed), key, id);
	}

	private void addOceanBiomes(long seed) {
		if (planet.hasGenLiquid()) {
			// Oceans
			addOceanBiome(deepOceanContinentalness, FULL, FULL, 0.0F, getBiome(BiomeCategory.DEEP_OCEAN, seed));
			addOceanBiome(oceanContinentalness, FULL, FULL, 0.0F, getBiome(BiomeCategory.OCEAN, seed));
		}
	}

	private void addUndergroundBiomes(long seed) {
		for (int i = 0; i < 5; i++) {
			addUndergroundBiome(FULL, FULL, Climate.Parameter.span(0.8F, 1.0F), FULL, 0.0F, getBiome(BiomeCategory.CAVE, 5, seed));
		}
	}

	private void addLandBiomes(long seed) {
		addMidSlice(seed, Parameter.span(-1.0F, -0.93333334F));
		addHighSlice(seed, Parameter.span(-0.93333334F, -0.7666667F));
		addPeaks(seed, Parameter.span(-0.7666667F, -0.56666666F));
		addHighSlice(seed, Parameter.span(-0.56666666F, -0.4F));
		addMidSlice(seed, Parameter.span(-0.4F, -0.26666668F));
		addLowSlice(seed, Parameter.span(-0.26666668F, -0.05F));
		addValleys(seed, Parameter.span(-0.05F, 0.05F));
		addLowSlice(seed, Parameter.span(0.05F, 0.26666668F));
		addMidSlice(seed, Parameter.span(0.26666668F, 0.4F));
		addHighSlice(seed, Parameter.span(0.4F, 0.56666666F));
		addPeaks(seed, Parameter.span(0.56666666F, 0.7666667F));
		addHighSlice(seed, Parameter.span(0.7666667F, 0.93333334F));
		addMidSlice(seed, Parameter.span(0.93333334F, 1.0F));
	}

	private void addPeaks(long seed, Parameter depth) {
		Holder<Biome> biome3 = getBiome(BiomeCategory.PLATEAU, seed);
		Holder<Biome> biome6 = getBiome(BiomeCategory.PEAK, seed);
		addSurfaceBiome(-1, Parameter.span(coastContinentalness, farInlandContinentalness), erosions[0], depth, 0.0F,
				biome6);
		addSurfaceBiome(-1, Parameter.span(midInlandContinentalness, farInlandContinentalness), erosions[1], depth,
				0.0F, biome6);
		addSurfaceBiome(-1, Parameter.span(midInlandContinentalness, farInlandContinentalness), erosions[2], depth,
				0.0F, biome3);
		addSurfaceBiome(-1, farInlandContinentalness, erosions[3], depth, 0.0F, biome3);

		for (int i = 0; i < 5; i++) {
			Holder<Biome> biome = getBiome(BiomeCategory.MIDDLE, i, seed);
			addSurfaceBiome(i, Parameter.span(coastContinentalness, nearInlandContinentalness),
					Parameter.span(erosions[2], erosions[3]), depth, 0.0F, biome);
			addSurfaceBiome(i, Parameter.span(coastContinentalness, farInlandContinentalness), erosions[4], depth, 0.0F,
					biome);
			addSurfaceBiome(i, Parameter.span(coastContinentalness, farInlandContinentalness), erosions[6], depth, 0.0F,
					biome);
		}

	}

	private void addHighSlice(long seed, Parameter depth) {
		Holder<Biome> biome3 = getBiome(BiomeCategory.PLATEAU, seed);
		Holder<Biome> biome6 = getBiome(BiomeCategory.SLOPE, seed);
		Holder<Biome> biome7 = getBiome(BiomeCategory.PEAK, seed);
		addSurfaceBiome(-1, nearInlandContinentalness, erosions[0], depth, 0.0F, biome6);
		addSurfaceBiome(-1, Parameter.span(midInlandContinentalness, farInlandContinentalness), erosions[0], depth,
				0.0F, biome7);
		addSurfaceBiome(-1, Parameter.span(midInlandContinentalness, farInlandContinentalness), erosions[1], depth,
				0.0F, biome6);
		addSurfaceBiome(-1, Parameter.span(midInlandContinentalness, farInlandContinentalness), erosions[2], depth,
				0.0F, biome3);
		addSurfaceBiome(-1, farInlandContinentalness, erosions[3], depth, 0.0F, biome3);

		for (int i = 0; i < 5; i++) {
			Holder<Biome> biome = getBiome(BiomeCategory.MIDDLE, i, seed);
			addSurfaceBiome(i, coastContinentalness, Parameter.span(erosions[0], erosions[1]), depth, 0.0F, biome);
			addSurfaceBiome(i, Parameter.span(coastContinentalness, nearInlandContinentalness),
					Parameter.span(erosions[2], erosions[3]), depth, 0.0F, biome);
			addSurfaceBiome(i, Parameter.span(coastContinentalness, farInlandContinentalness), erosions[4], depth, 0.0F,
					biome);
			addSurfaceBiome(i, Parameter.span(coastContinentalness, farInlandContinentalness), erosions[6], depth, 0.0F,
					biome);
		}

	}

	private void addMidSlice(long seed, Parameter depth) {
		Holder<Biome> biome4 = getBiome(BiomeCategory.PLATEAU, seed);
		Holder<Biome> biome5 = getBiome(BiomeCategory.BEACH, seed);
		Holder<Biome> biome8 = getBiome(BiomeCategory.SLOPE, seed);
		addSurfaceBiome(-1, Parameter.span(nearInlandContinentalness, farInlandContinentalness), erosions[0], depth,
				0.0F, biome8);
		addSurfaceBiome(-1, farInlandContinentalness, erosions[1], depth, 0.0F, biome4);
		addSurfaceBiome(-1, farInlandContinentalness, erosions[2], depth, 0.0F, biome4);
		if (depth.max() < 0L) {
			addSurfaceBiome(-1, coastContinentalness, erosions[4], depth, 0.0F, biome5);
		}
		if (depth.max() < 0L) {
			addSurfaceBiome(-1, coastContinentalness, erosions[6], depth, 0.0F, biome5);
		}

		for (int i = 0; i < 5; i++) {
			Holder<Biome> biome = getBiome(BiomeCategory.MIDDLE, i, seed);
			addSurfaceBiome(i, nearInlandContinentalness, erosions[2], depth, 0.0F, biome);
			addSurfaceBiome(i, Parameter.span(coastContinentalness, nearInlandContinentalness), erosions[3], depth,
					0.0F, biome);
			if (depth.max() < 0L) {
				addSurfaceBiome(i, Parameter.span(nearInlandContinentalness, farInlandContinentalness), erosions[4],
						depth, 0.0F, biome);
			} else {
				addSurfaceBiome(i, Parameter.span(coastContinentalness, farInlandContinentalness), erosions[4], depth,
						0.0F, biome);
			}
			if (depth.max() >= 0L) {
				addSurfaceBiome(i, coastContinentalness, erosions[6], depth, 0.0F, biome);
			}
			addSurfaceBiome(i, Parameter.span(nearInlandContinentalness, farInlandContinentalness), erosions[6], depth,
					0.0F, biome);
		}

	}

	private void addLowSlice(long seed, Parameter depth) {
		Holder<Biome> biome3 = getBiome(BiomeCategory.BEACH, seed);
		addSurfaceBiome(-1, coastContinentalness, Parameter.span(erosions[3], erosions[4]), depth, 0.0F, biome3);
		addSurfaceBiome(-1, coastContinentalness, erosions[6], depth, 0.0F, biome3);

		for (int i = 0; i < 5; i++) {
			Holder<Biome> biome = getBiome(BiomeCategory.MIDDLE, i, seed);
			addSurfaceBiome(i, nearInlandContinentalness, Parameter.span(erosions[2], erosions[3]), depth, 0.0F, biome);
			addSurfaceBiome(i, Parameter.span(nearInlandContinentalness, farInlandContinentalness), erosions[4], depth,
					0.0F, biome);
			addSurfaceBiome(i, Parameter.span(midInlandContinentalness, farInlandContinentalness), erosions[5], depth,
					0.0F, biome);

			addSurfaceBiome(i, Parameter.span(nearInlandContinentalness, farInlandContinentalness), erosions[6], depth,
					0.0F, biome);
		}

	}

	private void addValleys(long seed, Parameter depth) {

		Holder<Biome> biome = getBiome(BiomeCategory.RIVER, seed);
		Holder<Biome> biome1 = getBiome(BiomeCategory.BEACH, seed);

		addSurfaceBiome(-1, coastContinentalness, Parameter.span(erosions[0], erosions[1]), depth, 0.0F,
				depth.max() < 0L ? biome1 : biome);
		addSurfaceBiome(-1, nearInlandContinentalness, Parameter.span(erosions[0], erosions[1]), depth, 0.0F, biome);
		addSurfaceBiome(-1, Parameter.span(coastContinentalness, farInlandContinentalness),
				Parameter.span(erosions[2], erosions[5]), depth, 0.0F, biome);
		addSurfaceBiome(-1, coastContinentalness, erosions[6], depth, 0.0F, biome);
		addSurfaceBiome(-1, Parameter.span(inlandContinentalness, farInlandContinentalness), erosions[6], depth, 0.0F,
				biome);

	}

	private void addSurfaceBiome(int variant, Parameter continentalness, Parameter erosion, Parameter weirdness,
			float offset, Holder<Biome> biome) {
		if (biome == null)
			return;
		Parameter v = variant < 0 ? FULL : variants[variant];
		addBiome(v, continentalness, erosion, ZERO, weirdness, offset, biome);
		addBiome(v, continentalness, erosion, ONE, weirdness, offset, biome);
	}

	private void addOceanBiome(Parameter continentalness, Parameter erosion, Parameter weirdness, float offset,
			Holder<Biome> biome) {
		addBiome(FULL, continentalness, erosion, ZERO, weirdness, offset, biome);
	}

	private void addUndergroundBiome(Parameter continentalness, Parameter erosion, Parameter depth, Parameter weirdness, float offset,
			Holder<Biome> biome) {
		addBiome(FULL, continentalness, erosion, depth, weirdness, offset, biome);
	}

	private void addBottomBiome(Parameter continentalness, Parameter erosion, Parameter weirdness, float offset,
			Holder<Biome> biome) {
		addBiome(FULL, continentalness, erosion, Parameter.point(1.1F), weirdness, offset, biome);
	}

	private void addBiome(Parameter variant, Parameter continentalness, Parameter erosion, Parameter depth,
			Parameter weirdness, float offset, Holder<Biome> biome) {
		biomes.add(
				Pair.of(Climate.parameters(variant, FULL, continentalness, erosion, depth, weirdness, offset), biome));
	}

	public MultiNoiseBiomeSource build() {
		return MultiNoiseBiomeSource.createFromList(new ParameterList<Holder<Biome>>(biomes));
	}
}