package com.machina.world;

import com.google.common.base.Suppliers;
import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.PlanetHelper;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Aquifer.FluidStatus;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class PlanetChunkGenerator extends NoiseBasedChunkGenerator {

	public static Codec<PlanetChunkGenerator> makeCodec() {
		//@formatter:off
		return RecordCodecBuilder.create(instance -> instance.group(
				BiomeSource.CODEC.fieldOf("biome_source").forGetter(c -> c.biomeSource),
				NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter(c -> c.settings),
				Codec.INT.fieldOf("id").forGetter(c -> c.id),
				Codec.LONG.fieldOf("seed").forGetter(c -> c.seed))
			.apply(instance, PlanetChunkGenerator::new));
		//@formatter:on
	}

	int id;
	Planet planet;
	long seed;

	public PlanetChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings,
			ResourceKey<LevelStem> dim, long seed) {
		this(biomeSource, settings, PlanetHelper.getIdDim(dim), seed);
	}

	public PlanetChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings, int id,
			long seed) {
		super(biomeSource, settings);
		this.id = id;
		this.seed = seed;
		this.planet = Starchart.system(seed).planets().get(id);

		this.globalFluidPicker = Suppliers.memoize(() -> (x, y, z) -> {
			NoiseGeneratorSettings s = this.settings.value();
			return new FluidStatus(s.seaLevel(), s.defaultFluid());
		});
	}
}