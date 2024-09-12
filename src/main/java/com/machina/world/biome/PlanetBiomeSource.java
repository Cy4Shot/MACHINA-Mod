package com.machina.world.biome;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.starchart.planet_type.PlanetType;
import com.machina.api.starchart.planet_type.PlanetType.BiomePlacement;
import com.machina.api.util.PlanetHelper;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.dimension.LevelStem;

public class PlanetBiomeSource {

	private static final Parameter ZERO = Parameter.point(0.0F);
	private static final Parameter ONE = Parameter.point(1.0F);
	private static final Parameter FULL = Parameter.span(-1.0F, 1.0F);
	private static final Parameter[] erosions = new Parameter[] { Parameter.span(-1.0F, -0.78F),
			Parameter.span(-0.78F, -0.375F), Parameter.span(-0.375F, -0.2225F), Parameter.span(-0.2225F, 0.05F),
			Parameter.span(0.05F, 0.45F), Parameter.span(0.45F, 0.55F), Parameter.span(0.55F, 1.0F) };
	private static final Parameter[] depths = new Parameter[] { Climate.Parameter.span(0.8F, 1.0F),
			Climate.Parameter.span(1.0F, 1.2F), Climate.Parameter.span(1.2F, 1.4F) };
	private static final Parameter deepOceanCont = Parameter.span(-1.05F, -0.455F);
	private static final Parameter oceanCont = Parameter.span(-0.455F, -0.19F);
	private static final Parameter coastCont = Parameter.span(-0.19F, -0.11F);
	private static final Parameter inlandCont = Parameter.span(-0.11F, 0.55F);
	private static final Parameter nearInlandCont = Parameter.span(-0.11F, 0.03F);
	private static final Parameter midInlandCont = Parameter.span(0.03F, 0.3F);
	private static final Parameter farInlandCont = Parameter.span(0.3F, 1.0F);
	private final List<Pair<ParameterPoint, Holder<Biome>>> biomes;
	private final Planet planet;
	private final PlanetType type;
	private final RegistryLookup<Biome> biomeReg;

	public PlanetBiomeSource(ResourceKey<LevelStem> key, long seed, RegistryAccess lookup) {
		this.biomes = new ArrayList<>();
		this.planet = Starchart.system(seed).planets().get(PlanetHelper.getIdDim(key));
		this.type = planet.type();
		this.biomeReg = lookup.lookup(Registries.BIOME).get();

		for (ResourceKey<Biome> a : biomeReg.listElementIds().collect(Collectors.toList())) {
			System.out.println(a.toString());
		}

		addLandBiomes();
		addOceanBiomes();
		addUndergroundBiomes();
	}

	public static List<ParameterPoint> spawnTarget() {
		return List.of(
				new ParameterPoint(FULL, FULL, Parameter.span(inlandCont, FULL), FULL, ZERO,
						Parameter.span(-1.0F, -0.16F), 0L),
				new ParameterPoint(FULL, FULL, Parameter.span(inlandCont, FULL), FULL, ZERO,
						Parameter.span(0.16F, 1.0F), 0L));
	}

	private void forall(String category, Consumer<Holder<Biome>> action) {
		boolean found = false;
		for (BiomePlacement placement : type.biomes()) {
			if (placement.placements().contains(category)) {
				found = true;
				ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, placement.biome());
				action.accept(this.biomeReg.get(key).get());
			}
		}

		if (!found) {
			for (BiomePlacement placement : type.biomes()) {
				if (placement.placements().contains("FALLBACK")) {
					found = true;
					ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, placement.biome());
					action.accept(this.biomeReg.get(key).get());
				}
			}
		}
	}

	private void addOceanBiomes() {
		if (planet.hasGenLiquid()) {
			forall("DEEP_OCEAN", ocean -> {
				addOceanBiome(deepOceanCont, FULL, FULL, 0.0F, ocean);
			});
			forall("OCEAN", ocean -> {
				addOceanBiome(oceanCont, FULL, FULL, 0.0F, ocean);
			});
		}
	}

	private void addUndergroundBiomes() {
		forall("CAVE", cave -> {
			for (int i = 0; i < 3; i++) {
				addUndergroundBiome(FULL, FULL, depths[i], FULL, 0.0F, cave);
			}
		});
	}

	private void addLandBiomes() {
		addMidSlice(Parameter.span(-1.0F, -0.93333334F));
		addHighSlice(Parameter.span(-0.93333334F, -0.7666667F));
		addPeaks(Parameter.span(-0.7666667F, -0.56666666F));
		addHighSlice(Parameter.span(-0.56666666F, -0.4F));
		addMidSlice(Parameter.span(-0.4F, -0.26666668F));
		addLowSlice(Parameter.span(-0.26666668F, -0.05F));
		addValleys(Parameter.span(-0.05F, 0.05F));
		addLowSlice(Parameter.span(0.05F, 0.26666668F));
		addMidSlice(Parameter.span(0.26666668F, 0.4F));
		addHighSlice(Parameter.span(0.4F, 0.56666666F));
		addPeaks(Parameter.span(0.56666666F, 0.7666667F));
		addHighSlice(Parameter.span(0.7666667F, 0.93333334F));
		addMidSlice(Parameter.span(0.93333334F, 1.0F));
	}

	private void addPeaks(Parameter depth) {
		forall("PLATEAU", biome -> {
			addSurfaceBiome(Parameter.span(midInlandCont, farInlandCont), erosions[2], depth, 0.0F, biome);
			addSurfaceBiome(farInlandCont, erosions[3], depth, 0.0F, biome);
		});

		forall("PEAK", biome -> {
			addSurfaceBiome(Parameter.span(coastCont, farInlandCont), erosions[0], depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(midInlandCont, farInlandCont), erosions[1], depth, 0.0F, biome);
		});
		forall("MIDDLE", biome -> {
			addSurfaceBiome(Parameter.span(coastCont, nearInlandCont), Parameter.span(erosions[2], erosions[3]), depth,
					0.0F, biome);
			addSurfaceBiome(Parameter.span(coastCont, farInlandCont), erosions[4], depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(coastCont, farInlandCont), erosions[6], depth, 0.0F, biome);
		});

	}

	private void addHighSlice(Parameter depth) {
		forall("PLATEAU", biome -> {
			addSurfaceBiome(Parameter.span(midInlandCont, farInlandCont), erosions[2], depth, 0.0F, biome);
			addSurfaceBiome(farInlandCont, erosions[3], depth, 0.0F, biome);
		});
		forall("SLOPE", biome -> {
			addSurfaceBiome(nearInlandCont, erosions[0], depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(midInlandCont, farInlandCont), erosions[1], depth, 0.0F, biome);
		});
		forall("PEAK", biome -> {
			addSurfaceBiome(Parameter.span(midInlandCont, farInlandCont), erosions[0], depth, 0.0F, biome);
		});
		forall("MIDDLE", biome -> {
			addSurfaceBiome(coastCont, Parameter.span(erosions[0], erosions[1]), depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(coastCont, nearInlandCont), Parameter.span(erosions[2], erosions[3]), depth,
					0.0F, biome);
			addSurfaceBiome(Parameter.span(coastCont, farInlandCont), erosions[4], depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(coastCont, farInlandCont), erosions[6], depth, 0.0F, biome);
		});
	}

	private void addMidSlice(Parameter depth) {
		forall("PLATEAU", biome -> {
			addSurfaceBiome(farInlandCont, erosions[1], depth, 0.0F, biome);
			addSurfaceBiome(farInlandCont, erosions[2], depth, 0.0F, biome);
		});
		forall("BEACH", biome -> {
			if (depth.max() < 0L) {
				addSurfaceBiome(coastCont, erosions[4], depth, 0.0F, biome);
			}
			if (depth.max() < 0L) {
				addSurfaceBiome(coastCont, erosions[6], depth, 0.0F, biome);
			}
		});
		forall("SLOPE", biome -> {
			addSurfaceBiome(Parameter.span(nearInlandCont, farInlandCont), erosions[0], depth, 0.0F, biome);
		});
		forall("MIDDLE", biome -> {
			addSurfaceBiome(nearInlandCont, erosions[2], depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(coastCont, nearInlandCont), erosions[3], depth, 0.0F, biome);
			if (depth.max() < 0L) {
				addSurfaceBiome(Parameter.span(nearInlandCont, farInlandCont), erosions[4], depth, 0.0F, biome);
			} else {
				addSurfaceBiome(Parameter.span(coastCont, farInlandCont), erosions[4], depth, 0.0F, biome);
			}
			if (depth.max() >= 0L) {
				addSurfaceBiome(coastCont, erosions[6], depth, 0.0F, biome);
			}
			addSurfaceBiome(Parameter.span(nearInlandCont, farInlandCont), erosions[6], depth, 0.0F, biome);
		});
	}

	private void addLowSlice(Parameter depth) {
		forall("BEACH", biome -> {
			addSurfaceBiome(coastCont, Parameter.span(erosions[3], erosions[4]), depth, 0.0F, biome);
			addSurfaceBiome(coastCont, erosions[6], depth, 0.0F, biome);
		});
		forall("MIDDLE", biome -> {
			addSurfaceBiome(nearInlandCont, Parameter.span(erosions[2], erosions[3]), depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(nearInlandCont, farInlandCont), erosions[4], depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(midInlandCont, farInlandCont), erosions[5], depth, 0.0F, biome);

			addSurfaceBiome(Parameter.span(nearInlandCont, farInlandCont), erosions[6], depth, 0.0F, biome);
		});
	}

	private void addValleys(Parameter depth) {
		forall("BEACH", biome -> {
			if (depth.max() < 0L) {
				addSurfaceBiome(coastCont, Parameter.span(erosions[0], erosions[1]), depth, 0.0F, biome);
			}
		});
		forall("RIVER", biome -> {
			if (depth.max() >= 0L) {
				addSurfaceBiome(coastCont, Parameter.span(erosions[0], erosions[1]), depth, 0.0F, biome);
			}
			addSurfaceBiome(nearInlandCont, Parameter.span(erosions[0], erosions[1]), depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(coastCont, farInlandCont), Parameter.span(erosions[2], erosions[5]), depth,
					0.0F, biome);
			addSurfaceBiome(coastCont, erosions[6], depth, 0.0F, biome);
			addSurfaceBiome(Parameter.span(inlandCont, farInlandCont), erosions[6], depth, 0.0F, biome);
		});
	}

	private void addSurfaceBiome(Parameter continentalness, Parameter erosion, Parameter weirdness, float offset,
			Holder<Biome> biome) {
		if (biome == null)
			return;
		addBiome(FULL, continentalness, erosion, ZERO, weirdness, offset, biome);
		addBiome(FULL, continentalness, erosion, ONE, weirdness, offset, biome);
	}

	private void addOceanBiome(Parameter continentalness, Parameter erosion, Parameter weirdness, float offset,
			Holder<Biome> biome) {
		addBiome(FULL, continentalness, erosion, ZERO, weirdness, offset, biome);
	}

	private void addUndergroundBiome(Parameter continentalness, Parameter erosion, Parameter depth, Parameter weirdness,
			float offset, Holder<Biome> biome) {
		addBiome(FULL, continentalness, erosion, depth, weirdness, offset, biome);
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