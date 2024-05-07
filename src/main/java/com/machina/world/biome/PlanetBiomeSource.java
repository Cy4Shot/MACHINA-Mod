package com.machina.world.biome;

import java.util.ArrayList;
import java.util.List;

import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.PlanetHelper;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.LevelStem;

public class PlanetBiomeSource {

	private static final Climate.Parameter ZERO = Climate.Parameter.point(0.0F);
	private static final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
	private static final Climate.Parameter mushroomFieldsContinentalness = Climate.Parameter.span(-1.2F, -1.05F);
	private static final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
	private static final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
	private static final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
	private static final Climate.Parameter inlandContinentalness = Climate.Parameter.span(-0.11F, 0.55F);
	private static final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
	private static final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
	private static final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);

	private final List<Pair<Climate.ParameterPoint, Holder<Biome>>> biomes;
	private final Planet planet;
	private RegistryLookup<Biome> reg;

	public PlanetBiomeSource(ResourceKey<LevelStem> key, MinecraftServer server, long seed) {
		this.biomes = new ArrayList<>();
		this.reg = server.registries().compositeAccess().lookup(Registries.BIOME).get();
		this.planet = Starchart.system(seed).planets().get(PlanetHelper.getIdDim(key));
		generate();
	}

	public static List<Climate.ParameterPoint> spawnTarget() {
		return List.of(
				new Climate.ParameterPoint(FULL_RANGE, FULL_RANGE,
						Climate.Parameter.span(inlandContinentalness, FULL_RANGE), FULL_RANGE, ZERO,
						Climate.Parameter.span(-1.0F, -0.16F), 0L),
				new Climate.ParameterPoint(FULL_RANGE, FULL_RANGE,
						Climate.Parameter.span(inlandContinentalness, FULL_RANGE), FULL_RANGE, ZERO,
						Climate.Parameter.span(0.16F, 1.0F), 0L));
	}

	private Holder<Biome> getBiome(ResourceKey<Biome> biome) {
		return reg.get(biome).get();
	}

	private void generate() {
		this.addSurfaceBiome(0, 0.51f, 0, 0, 0, getBiome(Biomes.PLAINS));
		this.addSurfaceBiome(0.05f, 0.5f, 0.2f, 0.1f, 0, getBiome(Biomes.TAIGA));
//		this.addUndergroundBiome(FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, 0, getBiome(Biomes.LUSH_CAVES));
//		this.addBottomBiome(FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, 0, getBiome(Biomes.DEEP_DARK));
//		this.addOceanBiomes();
//		this.addLandBiomes();
//		this.addUndergroundBiomes();
	}

	private void addOceanBiomes() {

		// Water Lakes
		if (planet.isWaterPossible() && planet.waterState().equals(Planet.FluidState.LIQUID)) {
			addOcean(getBiome(Biomes.OCEAN), getBiome(Biomes.DEEP_OCEAN));
		}
	}

	private void addOcean(Holder<Biome> ocean, Holder<Biome> deepOcean) {
		this.addSurfaceBiome(FULL_RANGE, deepOceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, deepOcean);
		this.addSurfaceBiome(FULL_RANGE, oceanContinentalness, FULL_RANGE, FULL_RANGE, 0.0F, ocean);
	}

	private void addLandBiomes() {
		// *~* mAgIc ~*~
		this.addMidSlice(Climate.Parameter.span(-1.0F, -0.93333334F));
		this.addHighSlice(Climate.Parameter.span(-0.93333334F, -0.7666667F));
		this.addPeaks(Climate.Parameter.span(-0.7666667F, -0.56666666F));
		this.addHighSlice(Climate.Parameter.span(-0.56666666F, -0.4F));
		this.addMidSlice(Climate.Parameter.span(-0.4F, -0.26666668F));
		this.addLowSlice(Climate.Parameter.span(-0.26666668F, -0.05F));
		this.addValleys(Climate.Parameter.span(-0.05F, 0.05F));
		this.addLowSlice(Climate.Parameter.span(0.05F, 0.26666668F));
		this.addMidSlice(Climate.Parameter.span(0.26666668F, 0.4F));
		this.addHighSlice(Climate.Parameter.span(0.4F, 0.56666666F));
		this.addPeaks(Climate.Parameter.span(0.56666666F, 0.7666667F));
		this.addHighSlice(Climate.Parameter.span(0.7666667F, 0.93333334F));
		this.addMidSlice(Climate.Parameter.span(0.93333334F, 1.0F));
	}

	private void addMidSlice(Climate.Parameter weirdness) {

	}

	private void addHighSlice(Climate.Parameter weirdness) {

	}

	private void addLowSlice(Climate.Parameter weirdness) {

	}

	private void addPeaks(Climate.Parameter weirdness) {

	}

	private void addValleys(Climate.Parameter weirdness) {

	}

	private void addUndergroundBiomes() {

	}

	private void addSurfaceBiome(float humidity, float continentalness, float erosion, float weirdness, float offset,
			Holder<Biome> biome) {
		addBiome(Climate.Parameter.point(humidity), Climate.Parameter.point(continentalness),
				Climate.Parameter.point(erosion), Climate.Parameter.point(0F), Climate.Parameter.point(weirdness),
				offset, biome);
//		addBiome(humidity, continentalness, erosion, Climate.Parameter.point(1F), weirdness, offset, biome);
	}

	private void addSurfaceBiome(Climate.Parameter humidity, Climate.Parameter continentalness,
			Climate.Parameter erosion, Climate.Parameter weirdness, float offset, Holder<Biome> biome) {
		addBiome(humidity, continentalness, erosion, Climate.Parameter.point(0F), weirdness, offset, biome);
//		addBiome(humidity, continentalness, erosion, Climate.Parameter.point(1F), weirdness, offset, biome);
	}

	private void addUndergroundBiome(Climate.Parameter humidity, Climate.Parameter continentalness,
			Climate.Parameter erosion, Climate.Parameter weirdness, float offset, Holder<Biome> biome) {
		addBiome(humidity, continentalness, erosion, Climate.Parameter.span(0.2F, 0.9F), weirdness, offset, biome);
	}

	private void addBottomBiome(Climate.Parameter humidity, Climate.Parameter continentalness,
			Climate.Parameter erosion, Climate.Parameter weirdness, float offset, Holder<Biome> biome) {
		addBiome(humidity, continentalness, erosion, Climate.Parameter.point(1.1F), weirdness, offset, biome);
	}

	private void addBiome(Climate.Parameter humidity, Climate.Parameter continentalness, Climate.Parameter erosion,
			Climate.Parameter depth, Climate.Parameter weirdness, float offset, Holder<Biome> biome) {
		biomes.add(Pair.of(Climate.parameters(FULL_RANGE, humidity, continentalness, erosion, depth, weirdness, offset),
				biome));
	}

	public MultiNoiseBiomeSource build() {
		return MultiNoiseBiomeSource.createFromList(new Climate.ParameterList<Holder<Biome>>(biomes));
	}
}