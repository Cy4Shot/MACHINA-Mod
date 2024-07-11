package com.machina.api.starchart;

import java.util.List;
import java.util.Map;

import com.machina.api.util.block.WeightedStateProviderProvider;
import com.machina.world.feature.PlanetBushFeature.PlanetBushFeatureConfig;
import com.machina.world.feature.PlanetTreeFeature.TreeType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseSettings;

public record PlanetType(Shape shape, Surface surface, Vegetation vegetation, Underground underground, ExtraRules rules,
		int texOffset) {

	public static final Codec<PlanetType> CODEC = RecordCodecBuilder
			.create(instance -> instance
					.group(Shape.CODEC.fieldOf("shape").forGetter(PlanetType::shape),
							Surface.CODEC.fieldOf("surface").forGetter(PlanetType::surface),
							Vegetation.CODEC.fieldOf("vegetation").forGetter(PlanetType::vegetation),
							Underground.CODEC.fieldOf("underground").forGetter(PlanetType::underground),
							ExtraRules.CODEC.fieldOf("rules").forGetter(PlanetType::rules),
							Codec.INT.fieldOf("texOffset").forGetter(PlanetType::texOffset))
					.apply(instance, PlanetType::new));

	public static record Shape(int sea_level, NoiseSettings noise_settings) {
		public static final Codec<Shape> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(Codec.INT.fieldOf("sea_level").forGetter(Shape::sea_level),
						NoiseSettings.CODEC.fieldOf("noise_settings").forGetter(Shape::noise_settings))
				.apply(instance, Shape::new));
	}

	public record Surface(BlockState top, BlockState second) {
		public static final Codec<Surface> CODEC = RecordCodecBuilder
				.create(instance -> instance
						.group(BlockState.CODEC.fieldOf("top").forGetter(Surface::top),
								BlockState.CODEC.fieldOf("second").forGetter(Surface::second))
						.apply(instance, Surface::new));
	}

	public record Vegetation(List<Grass> grass, Map<TreeType, Tree> trees, List<PlanetBushFeatureConfig> bushes) {
		public static final Codec<Vegetation> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(Codec.list(Grass.CODEC).fieldOf("grass").forGetter(Vegetation::grass),
						Codec.unboundedMap(TreeType.CODEC, Tree.CODEC).fieldOf("trees").forGetter(Vegetation::trees),
						Codec.list(PlanetBushFeatureConfig.CODEC).fieldOf("bushes").forGetter(Vegetation::bushes))
				.apply(instance, Vegetation::new));
	}

	public record Grass(int min, int max, WeightedStateProviderProvider provider) {
		public Grass(int min, int max, WeightedStateProviderProvider.Builder builder) {
			this(min, max, builder.build());
		}

		public static final Codec<Grass> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(Codec.INT.fieldOf("min").forGetter(Grass::min), Codec.INT.fieldOf("max").forGetter(Grass::max),
						WeightedStateProviderProvider.CODEC.fieldOf("provider").forGetter(Grass::provider))
				.apply(instance, Grass::new));
	}

	public record Tree(BlockState log, BlockState leaves, BlockState leavesextra, int every) {
		public static final Codec<Tree> CODEC = RecordCodecBuilder
				.create(instance -> instance.group(BlockState.CODEC.fieldOf("log").forGetter(Tree::log),
						BlockState.CODEC.fieldOf("leaves").forGetter(Tree::leaves),
						BlockState.CODEC.fieldOf("leavesextra").forGetter(Tree::leavesextra),
						Codec.INT.fieldOf("every").forGetter(Tree::every)).apply(instance, Tree::new));
	}

	public record Underground(RockType rock, List<OreVein> ores) {
		public static final Codec<Underground> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(RockType.CODEC.fieldOf("rock").forGetter(Underground::rock),
						Codec.list(OreVein.CODEC).fieldOf("ores").forGetter(Underground::ores))
				.apply(instance, Underground::new));
	}

	public record RockType(BlockState base, BlockState stair, BlockState slab, BlockState extra) {
		public static final Codec<RockType> CODEC = RecordCodecBuilder
				.create(instance -> instance
						.group(BlockState.CODEC.fieldOf("base").forGetter(RockType::base),
								BlockState.CODEC.fieldOf("stair").forGetter(RockType::stair),
								BlockState.CODEC.fieldOf("slab").forGetter(RockType::slab),
								BlockState.CODEC.fieldOf("extra").forGetter(RockType::extra))
						.apply(instance, RockType::new));
	}

	public record OreVein(BlockState ore, int size, float exposure_removal_chance, int per_chunk) {
		public static final Codec<OreVein> CODEC = RecordCodecBuilder
				.create(instance -> instance
						.group(BlockState.CODEC.fieldOf("ore").forGetter(OreVein::ore),
								Codec.INT.fieldOf("size").forGetter(OreVein::size),
								Codec.FLOAT.fieldOf("exposure_removal_chance")
										.forGetter(OreVein::exposure_removal_chance),
								Codec.INT.fieldOf("per_chunk").forGetter(OreVein::per_chunk))
						.apply(instance, OreVein::new));
	}

	public record ExtraRules(VegetationRules veg, UndergroundRules cave) {
		public static final Codec<ExtraRules> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(VegetationRules.CODEC.fieldOf("veg").forGetter(ExtraRules::veg),
						UndergroundRules.CODEC.fieldOf("cave").forGetter(ExtraRules::cave))
				.apply(instance, ExtraRules::new));
	}

	public record VegetationRules(TagKey<Block> growable) {
		public static final Codec<VegetationRules> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(TagKey.codec(Registries.BLOCK).fieldOf("growable").forGetter(VegetationRules::growable))
				.apply(instance, VegetationRules::new));
	}

	public record UndergroundRules(TagKey<Block> carvable) {
		public static final Codec<UndergroundRules> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(TagKey.codec(Registries.BLOCK).fieldOf("carvable").forGetter(UndergroundRules::carvable))
				.apply(instance, UndergroundRules::new));
	}
}
