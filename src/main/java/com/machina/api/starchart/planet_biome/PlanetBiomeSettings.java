package com.machina.api.starchart.planet_biome;

import java.util.List;

import com.machina.api.util.block.WeightedStateProviderProvider;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.block.state.BlockState;

public record PlanetBiomeSettings(PlanetBiomeEffects effects, BlockState base, BlockState top, BlockState second,
		BlockState stair, BlockState slab, BlockState extra, List<PlanetBiomeTree> trees, List<PlanetBiomeBush> bushes,
		PlanetBiomeGrassSettings grass, PlanetBiomeLakes lakes, List<PlanetBiomeOre> ores) {

	public static final Codec<PlanetBiomeSettings> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(PlanetBiomeEffects.CODEC.fieldOf("effects").forGetter(PlanetBiomeSettings::effects),
					BlockState.CODEC.fieldOf("base").forGetter(PlanetBiomeSettings::base),
					BlockState.CODEC.fieldOf("top").forGetter(PlanetBiomeSettings::top),
					BlockState.CODEC.fieldOf("second").forGetter(PlanetBiomeSettings::second),
					BlockState.CODEC.fieldOf("stair").forGetter(PlanetBiomeSettings::stair),
					BlockState.CODEC.fieldOf("slab").forGetter(PlanetBiomeSettings::slab),
					BlockState.CODEC.fieldOf("extra").forGetter(PlanetBiomeSettings::extra),
					Codec.list(PlanetBiomeTree.CODEC).fieldOf("trees").forGetter(PlanetBiomeSettings::trees),
					Codec.list(PlanetBiomeBush.CODEC).fieldOf("bushes").forGetter(PlanetBiomeSettings::bushes),
					PlanetBiomeGrassSettings.CODEC.fieldOf("grass").forGetter(PlanetBiomeSettings::grass),
					PlanetBiomeLakes.CODEC.fieldOf("lakes").forGetter(PlanetBiomeSettings::lakes),
					Codec.list(PlanetBiomeOre.CODEC).fieldOf("ores").forGetter(PlanetBiomeSettings::ores))
			.apply(instance, PlanetBiomeSettings::new));

	public record PlanetBiomeEffects(int fog_color, int sky_color, int water_color, int water_fog_color, int grass_color) {

		public static final Codec<PlanetBiomeEffects> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(Codec.INT.fieldOf("fog_color").forGetter(PlanetBiomeEffects::fog_color),
						Codec.INT.fieldOf("sky_color").forGetter(PlanetBiomeEffects::sky_color),
						Codec.INT.fieldOf("water_color").forGetter(PlanetBiomeEffects::water_color),
						Codec.INT.fieldOf("water_fog_color").forGetter(PlanetBiomeEffects::water_fog_color),
						Codec.INT.fieldOf("grass_color").forGetter(PlanetBiomeEffects::grass_color))
				.apply(instance, PlanetBiomeEffects::new));

		public BiomeSpecialEffects.Builder build() {
			return new BiomeSpecialEffects.Builder().fogColor(fog_color).skyColor(sky_color).waterColor(water_color)
					.waterFogColor(water_fog_color).grassColorOverride(grass_color);
		}
	}

	public record PlanetBiomeTree(ResourceLocation tree, BlockState wood, BlockState leaves, int every) {

		public static final Codec<PlanetBiomeTree> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(ResourceLocation.CODEC.fieldOf("tree").forGetter(PlanetBiomeTree::tree),
						BlockState.CODEC.fieldOf("wood").forGetter(PlanetBiomeTree::wood),
						BlockState.CODEC.fieldOf("leaves").forGetter(PlanetBiomeTree::leaves),
						Codec.INT.fieldOf("every").forGetter(PlanetBiomeTree::every))
				.apply(instance, PlanetBiomeTree::new));
	}

	public record PlanetBiomeBush(BlockState block, float radius, int perchunk) {

		public static final Codec<PlanetBiomeBush> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(BlockState.CODEC.fieldOf("bush").forGetter(PlanetBiomeBush::block),
						Codec.FLOAT.fieldOf("radius").forGetter(PlanetBiomeBush::radius),
						Codec.INT.fieldOf("perchunk").forGetter(PlanetBiomeBush::perchunk))
				.apply(instance, PlanetBiomeBush::new));
	}

	public record PlanetBiomeGrassSettings(boolean enabled, int min, int max, List<PlanetBiomeGrass> grasses,
			WeightedStateProviderProvider provider) {

		public PlanetBiomeGrassSettings(boolean enabled, int min, int max, List<PlanetBiomeGrass> grasses) {
			this(enabled, min, max, grasses, build(grasses).build());
		}

		public static final Codec<PlanetBiomeGrassSettings> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(Codec.BOOL.fieldOf("enabled").forGetter(PlanetBiomeGrassSettings::enabled),
						Codec.INT.fieldOf("min").forGetter(PlanetBiomeGrassSettings::min),
						Codec.INT.fieldOf("max").forGetter(PlanetBiomeGrassSettings::max),
						Codec.list(PlanetBiomeGrass.CODEC).fieldOf("grasses")
								.forGetter(PlanetBiomeGrassSettings::grasses),
						WeightedStateProviderProvider.CODEC.fieldOf("provider")
								.forGetter(PlanetBiomeGrassSettings::provider))
				.apply(instance, PlanetBiomeGrassSettings::new));

		public static WeightedStateProviderProvider.Builder build(List<PlanetBiomeGrass> gs) {
			WeightedStateProviderProvider.Builder builder = WeightedStateProviderProvider.builder();
			for (PlanetBiomeGrass g : gs) {
				builder.add(g.block(), g.weight());
			}
			return builder;
		}
	}

	public record PlanetBiomeGrass(BlockState block, int weight) {
		public static final Codec<PlanetBiomeGrass> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(BlockState.CODEC.fieldOf("block").forGetter(PlanetBiomeGrass::block),
						Codec.INT.fieldOf("weight").forGetter(PlanetBiomeGrass::weight))
				.apply(instance, PlanetBiomeGrass::new));
	}

	public record PlanetBiomeLakes(boolean enabled, int rarity) {
		public static final Codec<PlanetBiomeLakes> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(Codec.BOOL.fieldOf("enabled").forGetter(PlanetBiomeLakes::enabled),
						Codec.INT.fieldOf("rarity").forGetter(PlanetBiomeLakes::rarity))
				.apply(instance, PlanetBiomeLakes::new));
	}

	public record PlanetBiomeOre(BlockState block, int size, float exposure_removal_chance, int per_chunk, int min_y,
			int max_y) {

		public static final Codec<PlanetBiomeOre> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(BlockState.CODEC.fieldOf("block").forGetter(PlanetBiomeOre::block),
						Codec.INT.fieldOf("size").forGetter(PlanetBiomeOre::size),
						Codec.FLOAT.fieldOf("exposure_removal_chance")
								.forGetter(PlanetBiomeOre::exposure_removal_chance),
						Codec.INT.fieldOf("per_chunk").forGetter(PlanetBiomeOre::per_chunk),
						Codec.INT.fieldOf("min_y").forGetter(PlanetBiomeOre::min_y),
						Codec.INT.fieldOf("max_y").forGetter(PlanetBiomeOre::max_y))
				.apply(instance, PlanetBiomeOre::new));
	}

}
