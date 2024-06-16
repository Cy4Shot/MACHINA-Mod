package com.machina.world.functions;

import java.util.stream.Stream;

import com.machina.api.starchart.obj.Planet;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.NoiseRouter;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.OreVeinifier;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class PlanetDensityFunction {

	// TODO: Just AT into NoiseRouterData
	private static final ResourceKey<DensityFunction> Y = createKey("y");
	private static final ResourceKey<DensityFunction> SHIFT_X = createKey("shift_x");
	private static final ResourceKey<DensityFunction> SHIFT_Z = createKey("shift_z");
	private static final ResourceKey<DensityFunction> CONTINENTS = createKey("overworld/continents");
	private static final ResourceKey<DensityFunction> EROSION = createKey("overworld/erosion");
	private static final ResourceKey<DensityFunction> RIDGES = createKey("overworld/ridges");
	private static final ResourceKey<DensityFunction> FACTOR = createKey("overworld/factor");
	private static final ResourceKey<DensityFunction> DEPTH = createKey("overworld/depth");
	private static final ResourceKey<DensityFunction> SLOPED_CHEESE = createKey("overworld/sloped_cheese");
	private static final ResourceKey<DensityFunction> ENTRANCES = createKey("overworld/caves/entrances");
	private static final ResourceKey<DensityFunction> NOODLE = createKey("overworld/caves/noodle");
	private static final ResourceKey<DensityFunction> PILLARS = createKey("overworld/caves/pillars");
	private static final ResourceKey<DensityFunction> SPAGHETTI_ROUGHNESS = createKey(
			"overworld/caves/spaghetti_roughness_function");
	private static final ResourceKey<DensityFunction> SPAGHETTI_2D = createKey("overworld/caves/spaghetti_2d");

	public static NoiseRouter planet(Planet p, HolderGetter<DensityFunction> densities,
			HolderGetter<NormalNoise.NoiseParameters> noises) {
		DensityFunction df = DensityFunctions.noise(noises.getOrThrow(Noises.AQUIFER_BARRIER), 0.5D);
		DensityFunction df1 = DensityFunctions.noise(noises.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_FLOODEDNESS), 0.67D);
		DensityFunction df2 = DensityFunctions.noise(noises.getOrThrow(Noises.AQUIFER_FLUID_LEVEL_SPREAD),
				0.7142857142857143D);
		DensityFunction df3 = DensityFunctions.noise(noises.getOrThrow(Noises.AQUIFER_LAVA));
		DensityFunction df4 = getFunction(densities, SHIFT_X);
		DensityFunction df5 = getFunction(densities, SHIFT_Z);
		DensityFunction df6 = DensityFunctions.shiftedNoise2d(df4, df5, 0.25D, noises.getOrThrow(Noises.TEMPERATURE));
		DensityFunction df7 = DensityFunctions.shiftedNoise2d(df4, df5, 0.25D, noises.getOrThrow(Noises.VEGETATION));
		DensityFunction df8 = getFunction(densities, FACTOR);
		DensityFunction df9 = getFunction(densities, DEPTH);
		DensityFunction df10 = noiseGradientDensity(DensityFunctions.cache2d(df8), df9);
		DensityFunction df11 = getFunction(densities, SLOPED_CHEESE);
		DensityFunction df12 = DensityFunctions.min(df11,
				DensityFunctions.mul(DensityFunctions.constant(5.0D), getFunction(densities, ENTRANCES)));
		DensityFunction df13 = DensityFunctions.rangeChoice(df11, -1000000.0D, 1.5625D, df12,
				underground(densities, noises, df11));
		DensityFunction df14 = DensityFunctions.min(postProcess(slideOverworld(df13)), getFunction(densities, NOODLE));
		DensityFunction df15 = getFunction(densities, Y);
		int i = Stream.of(OreVeinifier.VeinType.values()).mapToInt((p_224495_) -> {
			return p_224495_.minY;
		}).min().orElse(-DimensionType.MIN_Y * 2);
		int j = Stream.of(OreVeinifier.VeinType.values()).mapToInt((p_224457_) -> {
			return p_224457_.maxY;
		}).max().orElse(-DimensionType.MIN_Y * 2);
		DensityFunction df16 = yLimitedInterpolatable(df15,
				DensityFunctions.noise(noises.getOrThrow(Noises.ORE_VEININESS), 1.5D, 1.5D), i, j, 0);
		DensityFunction df17 = yLimitedInterpolatable(df15,
				DensityFunctions.noise(noises.getOrThrow(Noises.ORE_VEIN_A), 4.0D, 4.0D), i, j, 0).abs();
		DensityFunction df18 = yLimitedInterpolatable(df15,
				DensityFunctions.noise(noises.getOrThrow(Noises.ORE_VEIN_B), 4.0D, 4.0D), i, j, 0).abs();
		DensityFunction df19 = DensityFunctions.add(DensityFunctions.constant((double) -0.08F),
				DensityFunctions.max(df17, df18));
		DensityFunction df20 = DensityFunctions.noise(noises.getOrThrow(Noises.ORE_GAP));
		return new NoiseRouter(df, df1, df2, df3, df6, df7, getFunction(densities, CONTINENTS),
				getFunction(densities, EROSION), df9, getFunction(densities, RIDGES),
				slideOverworld(DensityFunctions.add(df10, DensityFunctions.constant(-0.703125D)).clamp(-64.0D, 64.0D)),
				df14, df16, df19, df20);
	}

	private static DensityFunction underground(HolderGetter<DensityFunction> densities,
			HolderGetter<NormalNoise.NoiseParameters> noises, DensityFunction pre) {
		DensityFunction df = getFunction(densities, SPAGHETTI_2D);
		DensityFunction df1 = getFunction(densities, SPAGHETTI_ROUGHNESS);
		DensityFunction df2 = DensityFunctions.noise(noises.getOrThrow(Noises.CAVE_LAYER), 8.0D);
		DensityFunction df3 = DensityFunctions.mul(DensityFunctions.constant(4.0D), df2.square());
		DensityFunction df4 = DensityFunctions.noise(noises.getOrThrow(Noises.CAVE_CHEESE), 0.6666666666666666D);
		DensityFunction df5 = DensityFunctions
				.add(DensityFunctions.add(DensityFunctions.constant(0.27D), df4).clamp(-1.0D, 1.0D),
						DensityFunctions
								.add(DensityFunctions.constant(1.5D),
										DensityFunctions.mul(DensityFunctions.constant(-0.64D), pre))
								.clamp(0.0D, 0.5D));
		DensityFunction df6 = DensityFunctions.add(df3, df5);
		DensityFunction df7 = DensityFunctions.min(DensityFunctions.min(df6, getFunction(densities, ENTRANCES)),
				DensityFunctions.add(df, df1));
		DensityFunction df8 = getFunction(densities, PILLARS);
		DensityFunction df9 = DensityFunctions.rangeChoice(df8, -1000000.0D, 0.03D,
				DensityFunctions.constant(-1000000.0D), df8);
		return DensityFunctions.max(df7, df9);
	}

	private static ResourceKey<DensityFunction> createKey(String key) {
		return ResourceKey.create(Registries.DENSITY_FUNCTION, new ResourceLocation(key));
	}

	private static DensityFunction getFunction(HolderGetter<DensityFunction> densities,
			ResourceKey<DensityFunction> density) {
		return new DensityFunctions.HolderHolder(densities.getOrThrow(density));
	}

	private static DensityFunction noiseGradientDensity(DensityFunction f1, DensityFunction f2) {
		DensityFunction df = DensityFunctions.mul(f2, f1);
		return DensityFunctions.mul(DensityFunctions.constant(4.0D), df.quarterNegative());
	}

	private static DensityFunction slideOverworld(DensityFunction df) {
		return slide(df, -64, 384, 80, 64, -0.078125D, 0, 24, 0.1171875D);
	}

	private static DensityFunction slide(DensityFunction p_224444_, int p_224445_, int p_224446_, int p_224447_,
			int p_224448_, double p_224449_, int p_224450_, int p_224451_, double p_224452_) {
		DensityFunction df1 = DensityFunctions.yClampedGradient(p_224445_ + p_224446_ - p_224447_,
				p_224445_ + p_224446_ - p_224448_, 1.0D, 0.0D);
		DensityFunction df3 = DensityFunctions.lerp(df1, p_224449_, p_224444_);
		DensityFunction df2 = DensityFunctions.yClampedGradient(p_224445_ + p_224450_, p_224445_ + p_224451_, 0.0D,
				1.0D);
		return DensityFunctions.lerp(df2, p_224452_, df3);
	}

	private static DensityFunction yLimitedInterpolatable(DensityFunction f1, DensityFunction f2, int p_209474_,
			int p_209475_, int p_209476_) {
		return DensityFunctions.interpolated(DensityFunctions.rangeChoice(f1, (double) p_209474_,
				(double) (p_209475_ + 1), f2, DensityFunctions.constant((double) p_209476_)));
	}

	private static DensityFunction postProcess(DensityFunction f) {
		DensityFunction df = DensityFunctions.blendDensity(f);
		return DensityFunctions.mul(DensityFunctions.interpolated(df), DensityFunctions.constant(0.64D)).squeeze();
	}
}
