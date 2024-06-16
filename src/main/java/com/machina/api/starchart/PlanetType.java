package com.machina.api.starchart;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseSettings;

public record PlanetType(Shape shape, Surface surface, Underground underground) {

	public static final Codec<PlanetType> CODEC = RecordCodecBuilder.create(instance -> instance
			.group(Shape.CODEC.fieldOf("shape").forGetter(PlanetType::shape),
					Surface.CODEC.fieldOf("surface").forGetter(PlanetType::surface),
					Underground.CODEC.fieldOf("underground").forGetter(PlanetType::underground))
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
}
