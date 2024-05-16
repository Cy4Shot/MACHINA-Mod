package com.machina.world;

import java.util.BitSet;

import com.google.common.base.Suppliers;
import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.util.PlanetHelper;
import com.machina.world.carver.PlanetCarver;
import com.machina.world.carver.PlanetSlopeGenerator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.Aquifer.FluidStatus;
import net.minecraft.world.level.levelgen.GenerationStep.Carving;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

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

	public PlanetChunkGenerator(BiomeSource biomeSource, Holder<NoiseGeneratorSettings> settings, int id, long seed) {
		super(biomeSource, settings);
		this.id = id;
		this.seed = seed;
		this.planet = Starchart.system(seed).planets().get(id);

		this.globalFluidPicker = Suppliers.memoize(() -> (x, y, z) -> {
			NoiseGeneratorSettings s = settings.value();
			return new FluidStatus(s.seaLevel(), s.defaultFluid());
		});
	}

	public void placeCaves(WorldGenRegion region, RandomState rand, StructureManager structure, ChunkAccess chunk,
			Carving carving) {
		WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));

		ConfiguredWorldCarver<?> carver = PlanetCarver.create(0.02f, 3, 0.03f).get();
		BiomeManager biomemanager = region.getBiomeManager().withDifferentSource((p_255581_, p_255582_, p_255583_) -> {
			return this.biomeSource.getNoiseBiome(p_255581_, p_255582_, p_255583_, rand.sampler());
		});
		ChunkPos chunkpos = chunk.getPos();
		NoiseChunk noisechunk = chunk.getOrCreateNoiseChunk((p_224250_) -> {
			return this.createNoiseChunk(p_224250_, structure, Blender.of(region), rand);
		});
		Aquifer aquifer = noisechunk.aquifer();
		CarvingContext carvingcontext = new CarvingContext(this, region.registryAccess(),
				chunk.getHeightAccessorForGeneration(), noisechunk, rand, this.settings.value().surfaceRule());
		CarvingMask carvingmask = ((ProtoChunk) chunk).getOrCreateCarvingMask(carving);

		for (int j = -8; j <= 8; ++j) {
			for (int k = -8; k <= 8; ++k) {
				ChunkPos chunkpos1 = new ChunkPos(chunkpos.x + j, chunkpos.z + k);

				worldgenrandom.setLargeFeatureSeed(seed, chunkpos1.x, chunkpos1.z);
				if (carver.isStartChunk(worldgenrandom)) {
					carver.carve(carvingcontext, chunk, biomemanager::getBiome, worldgenrandom, aquifer, chunkpos1,
							carvingmask);
				}
			}
		}

		BitSet bitset = BitSet.valueOf(carvingmask.toArray());

		for (int a = 0; a < bitset.length(); ++a) {
			if (bitset.get(a)) {
				BlockPos pos = new BlockPos(chunkpos.getMinBlockX() + (a & 15), (a >> 8),
						chunkpos.getMinBlockZ() + (a >> 4 & 15));
				PlanetSlopeGenerator.decorateAt(chunk, pos.below(64), this, rand,
						NormalNoise.create(worldgenrandom, -8, 0.5, 1, 2, 1, 2, 1, 0, 2, 0), true);
			}
		}
	}

	@Override
	public void applyCarvers(WorldGenRegion reg, long p_224225_, RandomState rand, BiomeManager p_224227_,
			StructureManager struct, ChunkAccess chunk, Carving carve) {
		placeCaves(reg, rand, struct, chunk, carve);
	}

}