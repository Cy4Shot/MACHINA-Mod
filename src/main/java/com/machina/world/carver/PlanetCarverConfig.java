package com.machina.world.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;

public class PlanetCarverConfig extends CarverConfiguration {

	public static final Codec<PlanetCarverConfig> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((config) -> {
			return config.probability;
		})).and(Codec.intRange(0, 25).fieldOf("length").forGetter((config) -> {
			return config.length;
		})).and(Codec.floatRange(0F, 20F).fieldOf("thickness").forGetter((config) -> {
			return config.thickness;
		})).apply(builder, PlanetCarverConfig::new);
	});

	public final float probability;
	public final int length;
	public final float thickness;

	public PlanetCarverConfig(float p, int l, float t) {
		super(0, ConstantHeight.of(VerticalAnchor.TOP), ConstantFloat.ZERO, VerticalAnchor.BOTTOM,
				CarverDebugSettings.DEFAULT, HolderSet.direct());
		this.probability = p;
		this.length = l;
		this.thickness = t;
	}

}