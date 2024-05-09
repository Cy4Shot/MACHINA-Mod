package com.machina.world;

import com.machina.Machina;
import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.MachinaRL;
import com.machina.api.util.PlanetHelper;
import com.machina.world.biome.PlanetBiomeSource;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseRouterData;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class PlanetFactory {

	public static final ResourceKey<DimensionType> TYPE_KEY = ResourceKey.create(Registries.DIMENSION_TYPE,
			new MachinaRL(Machina.MOD_ID));

	public static LevelStem createDimension(MinecraftServer server, ResourceKey<LevelStem> key) {
		long seed = server.overworld().getSeed();
		Planet planet = Starchart.system(seed).planets().get(PlanetHelper.getIdDim(key));
		BlockState fluid = planet.getDominantLiquidBodyBlock();

		MultiNoiseBiomeSource bs = new PlanetBiomeSource(key, seed).build();

		RegistryAccess lookup = server.registries().compositeAccess();
		NoiseGeneratorSettings settings = new NoiseGeneratorSettings(new NoiseSettings(-64, 384, 1, 2),
				Blocks.STONE.defaultBlockState(), fluid,
				NoiseRouterData.overworld(lookup.lookup(Registries.DENSITY_FUNCTION).get(),
						lookup.lookup(Registries.NOISE).get(), false, false),
				SurfaceRuleData.overworld(), PlanetBiomeSource.spawnTarget(), 60, false, true, true, false);

		return new LevelStem(getDimensionType(server),
				new PlanetChunkGenerator(bs, Holder.direct(settings), key, seed));
	}

	public static Holder<DimensionType> getDimensionType(MinecraftServer server) {
		return server.registryAccess().registryOrThrow(Registries.DIMENSION_TYPE).getHolderOrThrow(TYPE_KEY);
	}
}
