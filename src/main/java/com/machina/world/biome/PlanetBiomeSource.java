package com.machina.world.biome;

import java.util.ArrayList;
import java.util.List;

import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.PlanetHelper;
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
	private static final Parameter FULL_RANGE = Parameter.span(-1.0F, 1.0F);
	private static final Parameter deepOceanContinentalness = Parameter.span(-1.05F, -0.455F);
	private static final Parameter oceanContinentalness = Parameter.span(-0.455F, -0.19F);
	private static final Parameter coastContinentalness = Parameter.span(-0.19F, -0.11F);
	private static final Parameter inlandContinentalness = Parameter.span(-0.11F, 0.55F);
	private static final Parameter midInlandContinentalness = Parameter.span(-0.11F, 0.1F);

	private final List<Pair<ParameterPoint, Holder<Biome>>> biomes;
	private final Planet planet;
	private final ResourceKey<LevelStem> key;

	public PlanetBiomeSource(ResourceKey<LevelStem> key, long seed) {
		this.biomes = new ArrayList<>();
		this.planet = Starchart.system(seed).planets().get(PlanetHelper.getIdDim(key));
		this.key = key;
		this.addLandBiomes(seed);
		this.addOceanBiomes(seed);
		this.addUndergroundBiomes(seed);
	}

	public static List<ParameterPoint> spawnTarget() {
		return List.of(
				new ParameterPoint(FULL_RANGE, FULL_RANGE, Parameter.span(inlandContinentalness, FULL_RANGE),
						FULL_RANGE, ZERO, Parameter.span(-1.0F, -0.16F), 0L),
				new ParameterPoint(FULL_RANGE, FULL_RANGE, Parameter.span(inlandContinentalness, FULL_RANGE),
						FULL_RANGE, ZERO, Parameter.span(0.16F, 1.0F), 0L));
	}

	private Holder<Biome> getBiome(int id, long seed) {
		return PlanetBiomeRegistrationHandler.getOrCreate(ServerLifecycleHooks.getCurrentServer(),
				() -> new PlanetBiome(planet, id, seed), key, id);
	}

	private void addLandBiomes(long seed) {
		// Plains

		// Forests

		// Craters

		// Lakes

		// Mountains
	}

	private void addOceanBiomes(long seed) {
		if (planet.hasGenLiquid()) {
			// Oceans
			addSurfaceBiome(deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, getBiome(40, seed));
			addSurfaceBiome(oceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, getBiome(45, seed));

			// Coast
			addSurfaceBiome(coastContinentalness, 1f, 0f, 0f, getBiome(60, seed));
		}
	}

	private void addUndergroundBiomes(long seed) {

	}

	// Continentalness - Far from sea (high is further)
	// Flatness - (higher is flatter)
	// Weirdness - for biome variants
	// Offset - scale of biome (0 is normal, 1 is do not generate)
	private void addSurfaceBiome(Parameter continentalness, float flatness, float weirdness, float scale,
			Holder<Biome> biome) {
		addBiome(FULL_RANGE, continentalness, Parameter.point(flatness), ZERO, Parameter.point(weirdness), scale,
				biome);
	}

	private void addSurfaceBiome(Parameter continentalness, Parameter erosion, Parameter weirdness, float offset,
			Holder<Biome> biome) {
		addBiome(FULL_RANGE, continentalness, erosion, ZERO, weirdness, offset, biome);
	}

	private void addUndergroundBiome(Parameter humidity, Parameter continentalness, Parameter erosion,
			Parameter weirdness, float offset, Holder<Biome> biome) {
		addBiome(humidity, continentalness, erosion, Parameter.span(0.2F, 0.9F), weirdness, offset, biome);
	}

	private void addBottomBiome(Parameter humidity, Parameter continentalness, Parameter erosion, Parameter weirdness,
			float offset, Holder<Biome> biome) {
		addBiome(humidity, continentalness, erosion, Parameter.point(1.1F), weirdness, offset, biome);
	}

	private void addBiome(Parameter humidity, Parameter continentalness, Parameter erosion, Parameter depth,
			Parameter weirdness, float offset, Holder<Biome> biome) {
		biomes.add(Pair.of(Climate.parameters(FULL_RANGE, humidity, continentalness, erosion, depth, weirdness, offset),
				biome));
	}

	public MultiNoiseBiomeSource build() {
		return MultiNoiseBiomeSource.createFromList(new ParameterList<Holder<Biome>>(biomes));
	}
}