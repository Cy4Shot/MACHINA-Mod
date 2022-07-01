package com.machina.registration.init;

import java.util.Random;
import java.util.function.Function;

import com.machina.planet.attribute.PlanetAttributeType;
import com.machina.planet.attribute.serializers.AttributeSerializer;
import com.machina.planet.attribute.serializers.ColorListSerializer;
import com.machina.planet.attribute.serializers.FloatSerializer;
import com.machina.planet.attribute.serializers.IntSerializer;
import com.machina.planet.attribute.serializers.StringSerializer;
import com.machina.registration.registry.PlanetAttributeRegistry;
import com.machina.util.MachinaRL;
import com.machina.util.color.Color;
import com.machina.util.reflection.ClassHelper;
import com.machina.world.gen.PlanetBlocksGenerator;
import com.machina.world.gen.PlanetNameGenerator;
import com.machina.world.gen.PlanetPaletteGenerator;

import net.minecraftforge.event.RegistryEvent;

public final class PlanetAttributeTypesInit {

	//@formatter:off
	public static final PlanetAttributeType<Float> DISTANCE = create("AU", new FloatSerializer(1.0f, random(0.5f, 10f)));
	public static final PlanetAttributeType<Float> GRAVITY = create("G", new FloatSerializer(1.0f, random(0.1f, 2.0f)));
	public static final PlanetAttributeType<String> PLANET_NAME = create("", new StringSerializer("Planet", PlanetNameGenerator::getName));
	public static final PlanetAttributeType<Float> FOG_DENSITY = create("", new FloatSerializer(0.5f, random(0.0f, 1.0f)));
	public static final PlanetAttributeType<Color[]> PALETTE = create("", new ColorListSerializer(Color.black, PlanetPaletteGenerator::genPlanetPalette, 5));
	public static final PlanetAttributeType<Float> ATMOSPHERIC_PRESSURE = create("atm", new FloatSerializer(1.0f, random(0.1f, 2.0f)));
	public static final PlanetAttributeType<Float> TEMPERATURE = create("K", new FloatSerializer(350.0f, random(100f, 1000f)));
	public static final PlanetAttributeType<Integer> BASE_BLOCKS = create("", new IntSerializer(0, PlanetBlocksGenerator::getRandomBase));
	public static final PlanetAttributeType<Integer> SURF_BLOCKS = create("", new IntSerializer(0, PlanetBlocksGenerator::getRandomSurf));
	public static final PlanetAttributeType<Integer> FLUID_BLOCKS = create("", new IntSerializer(0, PlanetBlocksGenerator::getRandomFluid));
	public static final PlanetAttributeType<Integer> CAVES_EXIST = create("", new IntSerializer(0, random(0, 1)));
	public static final PlanetAttributeType<Float> CAVE_CHANCE = create("", new FloatSerializer(0.01f, random(0f, 0.02f)));
	public static final PlanetAttributeType<Integer> CAVE_LENGTH = create("",new IntSerializer(3, random(1, 5)));
	public static final PlanetAttributeType<Float> CAVE_THICKNESS = create("", new FloatSerializer(0.03f, random(0.01f, 0.06f, 1.7f)));
	public static final PlanetAttributeType<Float> ISLAND_DENSITY = create("", new FloatSerializer(0.5f, random(0.3f, 0.7f)));
	//@formatter:on
	
	public static <T> PlanetAttributeType<T> create(String unit, AttributeSerializer<T> ser) {
		return new PlanetAttributeType<T>(unit, ser);
	}

	public static void register(final RegistryEvent.Register<PlanetAttributeType<?>> event) {
		ClassHelper.<PlanetAttributeType<?>>doWithStatics(PlanetAttributeTypesInit.class,
				(name, data) -> register(name.toLowerCase(), data));
	}

	private static <T> PlanetAttributeType<T> register(String name, PlanetAttributeType<T> attribute) {
		attribute.setRegistryName(new MachinaRL(name));
		PlanetAttributeRegistry.REGISTRY.register(attribute);
		return attribute;

	}

	// Random
	public static Function<Random, Float> random(float min, float max) {
		return r -> min + r.nextFloat() * (max - min);
	}

	public static Function<Random, Float> random(float min, float max, float exp) {
		return r -> min + (float) Math.pow(r.nextFloat(), exp) * (max - min);
	}

	public static Function<Random, Integer> random(int min, int max) {
		return r -> r.nextInt((max - min) + 1) + min;
	}
}
