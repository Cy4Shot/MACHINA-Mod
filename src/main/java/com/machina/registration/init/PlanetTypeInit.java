package com.machina.registration.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.machina.api.starchart.PlanetType;
import com.machina.api.starchart.PlanetType.ExtraRules;
import com.machina.api.starchart.PlanetType.OreVein;
import com.machina.api.starchart.PlanetType.RockType;
import com.machina.api.starchart.PlanetType.Shape;
import com.machina.api.starchart.PlanetType.Surface;
import com.machina.api.starchart.PlanetType.Tree;
import com.machina.api.starchart.PlanetType.Underground;
import com.machina.api.starchart.PlanetType.UndergroundRules;
import com.machina.api.starchart.PlanetType.Vegetation;
import com.machina.api.starchart.PlanetType.VegetationRules;
import com.machina.registration.init.TagInit.BlockTagInit;
import com.machina.world.feature.PlanetBushFeature.PlanetBushFeatureConfig;
import com.machina.world.feature.PlanetTreeFeature.TreeType;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class PlanetTypeInit {

	public static final Map<String, PlanetType> PLANET_TYPES = new HashMap<>();

	//@formatter:off
//	public static final String EARTHLIKE = create("earthlike", new PlanetType(
//			new Shape(60, new NoiseSettings(-64, 384, 1, 2)),
//			new Surface(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.DIRT.defaultBlockState()),
//			new Vegetation(Map.of(
////					TreeType.ARCH, new Tree(Blocks.OAK_WOOD.defaultBlockState(),
////							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true),
////							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 4),
//					TreeType.RADIAL_BAOBAB, new Tree(Blocks.OAK_WOOD.defaultBlockState(),
//							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true),
//							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 4)),
//					List.of(new PlanetBushFeatureConfig(Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true), 2.5f, 3))),
//			new Underground(
//					new RockType(Blocks.STONE.defaultBlockState(),
//							Blocks.STONE_STAIRS.defaultBlockState(),
//							Blocks.STONE_SLAB.defaultBlockState(),
//							Blocks.GRAVEL.defaultBlockState()),
//					List.of(
//							new OreVein(Blocks.ANDESITE.defaultBlockState(), 32, 0f, 8))
//					),
//			new ExtraRules(
//					new VegetationRules(BlockTagInit.EARTHLIKE_GROWABLE),
//					new UndergroundRules(BlockTagInit.EARTHLIKE_CARVABLE)
//			), 0));
	

	
	public static final String MARTIAN = create("martian", new PlanetType(
			new Shape(50, new NoiseSettings(-64, 384, 1, 4)),
			new Surface(Blocks.RED_SAND.defaultBlockState(), Blocks.RED_SANDSTONE.defaultBlockState()),
			new Vegetation(Map.of(
					TreeType.ARCH, new Tree(BlockInit.FELDSPAR.get().defaultBlockState(),
							BlockInit.FELDSPAR_WALL.get().defaultBlockState(), BlockInit.FELDSPAR_WALL.get().defaultBlockState(), 24)),
					List.of(new PlanetBushFeatureConfig(Blocks.RED_SAND.defaultBlockState(), 3f, 2))),
			new Underground(
					new RockType(BlockInit.FELDSPAR.get().defaultBlockState(),
							BlockInit.FELDSPAR_STAIRS.get().defaultBlockState(),
							BlockInit.FELDSPAR_SLAB.get().defaultBlockState(),
							Blocks.RED_SAND.defaultBlockState()),
					List.of(
							new OreVein(Blocks.RED_SANDSTONE.defaultBlockState(), 32, 0f, 6),
							new OreVein(Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState(), 32, 0f, 6))
					),
			new ExtraRules(
					new VegetationRules(BlockTagInit.MARTIAN_GROWABLE),
					new UndergroundRules(BlockTagInit.MARTIAN_CARVABLE)
			), 4));
	//@formatter:on

	public static String create(String name, PlanetType type) {
		PLANET_TYPES.put(name, type);
		return name;
	}

	public static PlanetType get(String string) {
		return PLANET_TYPES.get(string);
	}

	public static String pickRandom(Random rand) {
		List<String> types = PLANET_TYPES.keySet().stream().collect(Collectors.toList());
		int index = rand.nextInt(types.size());
		return types.get(index);
	}
}