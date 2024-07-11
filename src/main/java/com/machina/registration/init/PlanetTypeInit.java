package com.machina.registration.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import com.machina.api.starchart.PlanetType;
import com.machina.api.starchart.PlanetType.ExtraRules;
import com.machina.api.starchart.PlanetType.Grass;
import com.machina.api.starchart.PlanetType.OreVein;
import com.machina.api.starchart.PlanetType.RockType;
import com.machina.api.starchart.PlanetType.Shape;
import com.machina.api.starchart.PlanetType.Surface;
import com.machina.api.starchart.PlanetType.Tree;
import com.machina.api.starchart.PlanetType.Underground;
import com.machina.api.starchart.PlanetType.UndergroundRules;
import com.machina.api.starchart.PlanetType.Vegetation;
import com.machina.api.starchart.PlanetType.VegetationRules;
import com.machina.api.util.block.WeightedStateProviderProvider;
import com.machina.registration.init.TagInit.BlockTagInit;
import com.machina.world.feature.PlanetBushFeature.PlanetBushFeatureConfig;
import com.machina.world.feature.PlanetTreeFeature.TreeType;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class PlanetTypeInit {
//	@formatter:off
	private static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.defaultBlockState();
	private static final BlockState DIRT = Blocks.DIRT.defaultBlockState();
	private static final BlockState OAK_WOOD = Blocks.OAK_WOOD.defaultBlockState();
	private static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true);
	private static final BlockState STONE = Blocks.STONE.defaultBlockState();
	private static final BlockState STONE_STAIRS = Blocks.STONE_STAIRS.defaultBlockState();
	private static final BlockState STONE_SLAB = Blocks.STONE_SLAB.defaultBlockState();
	private static final BlockState GRAVEL = Blocks.GRAVEL.defaultBlockState();
	private static final BlockState ANDESITE = Blocks.ANDESITE.defaultBlockState();
	private static final BlockState RED_SAND = Blocks.RED_SAND.defaultBlockState();
	private static final BlockState RED_SANDSTONE = Blocks.RED_SANDSTONE.defaultBlockState();
	private static final BlockState SMOOTH_RED_SANDSTONE = Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState();
	private static final BlockState FELDSPAR = BlockInit.FELDSPAR.get().defaultBlockState();
	private static final BlockState FELDSPAR_STAIRS = BlockInit.FELDSPAR_STAIRS.get().defaultBlockState();
	private static final BlockState FELDSPAR_SLAB = BlockInit.FELDSPAR_SLAB.get().defaultBlockState();
	private static final BlockState FELDSPAR_WALL = BlockInit.FELDSPAR_WALL.get().defaultBlockState();

	public static final Map<String, PlanetType> PLANET_TYPES = new HashMap<>();

	public static final String EARTHLIKE = create("earthlike", new PlanetType(
			new Shape(60, new NoiseSettings(-64, 384, 1, 2)),
			new Surface(GRASS_BLOCK, DIRT),
			new Vegetation(
					List.of(new Grass(5, 10, WeightedStateProviderProvider.builder()
							.add(Blocks.GRASS.defaultBlockState(), 10)
							.add(Blocks.FERN.defaultBlockState(), 2)
							.addHorizontal(BlockInit.CLOVER.get().defaultBlockState(), 1))),
					Map.of(
							TreeType.RADIAL_BAOBAB, new Tree(OAK_WOOD, OAK_LEAVES, OAK_LEAVES, 4)),
					List.of(new PlanetBushFeatureConfig(OAK_LEAVES, 2.5f, 3))),
			new Underground(
					new RockType(STONE, STONE_STAIRS, STONE_SLAB, GRAVEL),
					List.of(
							new OreVein(ANDESITE, 32, 0f, 8))
					),
			new ExtraRules(
					new VegetationRules(BlockTagInit.EARTHLIKE_GROWABLE),
					new UndergroundRules(BlockTagInit.EARTHLIKE_CARVABLE)
			), 0));
	

	
//	public static final String MARTIAN = create("martian", new PlanetType(
//			new Shape(50, new NoiseSettings(-64, 384, 1, 4)),
//			new Surface(RED_SAND, RED_SANDSTONE),
//			new Vegetation(Map.of(
//					TreeType.ARCH, new Tree(FELDSPAR, FELDSPAR_WALL, FELDSPAR_WALL, 24)),
//					List.of(new PlanetBushFeatureConfig(RED_SAND, 3f, 2))),
//			new Underground(
//					new RockType(FELDSPAR, FELDSPAR_STAIRS, FELDSPAR_SLAB, RED_SAND),
//					List.of(
//							new OreVein(RED_SANDSTONE, 32, 0f, 6),
//							new OreVein(SMOOTH_RED_SANDSTONE, 32, 0f, 6))
//					),
//			new ExtraRules(
//					new VegetationRules(BlockTagInit.MARTIAN_GROWABLE),
//					new UndergroundRules(BlockTagInit.MARTIAN_CARVABLE)
//			), 4));
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