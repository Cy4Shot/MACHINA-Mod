package com.machina.registration.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import com.machina.world.feature.PlanetTreeFeature.TreeType;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.NoiseSettings;

public class PlanetTypeInit {

	public static final List<PlanetType> PLANET_TYPES = new ArrayList<>();

	//@formatter:off
	public static final PlanetType EARTHLIKE = create(new PlanetType(
			new Shape(60, new NoiseSettings(-64, 384, 1, 2)),
			new Surface(Blocks.GRASS_BLOCK.defaultBlockState(), Blocks.DIRT.defaultBlockState()),
			new Vegetation(Map.of(
					TreeType.BASIC2, new Tree(Blocks.OAK_WOOD.defaultBlockState(),
							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true),
							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true)),
					TreeType.BASIC3, new Tree(Blocks.OAK_WOOD.defaultBlockState(),
							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true),
							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true)))),
			new Underground(
					new RockType(Blocks.STONE.defaultBlockState(),
							Blocks.STONE_STAIRS.defaultBlockState(),
							Blocks.STONE_SLAB.defaultBlockState(),
							Blocks.GRAVEL.defaultBlockState()),
					List.of(
							new OreVein(Blocks.ANDESITE.defaultBlockState(), 32, 0f, 8))
					),
			new ExtraRules(
					new VegetationRules(BlockTagInit.EARTHLIKE_GROWABLE),
					new UndergroundRules(BlockTagInit.EARTHLIKE_CARVABLE)
			)));

	public static final PlanetType ANTHRACITE = create(new PlanetType(
			new Shape(60, new NoiseSettings(-64, 384, 1, 2)),
			new Surface(Blocks.COAL_ORE.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()),
			new Vegetation(Map.of(
					TreeType.BASIC2, new Tree(Blocks.OAK_WOOD.defaultBlockState(),
							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true),
							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true)),
					TreeType.BASIC3, new Tree(Blocks.OAK_WOOD.defaultBlockState(),
							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true),
							Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.PERSISTENT, true)))),
			new Underground(
					new RockType(BlockInit.ANTHRACITE.get().defaultBlockState(),
							BlockInit.ANTHRACITE_STAIRS.get().defaultBlockState(),
							BlockInit.ANTHRACITE_SLAB.get().defaultBlockState(),
							Blocks.BLACK_CONCRETE_POWDER.defaultBlockState()),
					List.of(
							new OreVein(Blocks.COAL_BLOCK.defaultBlockState(), 32, 0f, 8))
					),
			new ExtraRules(
					new VegetationRules(BlockTagInit.ANTHRACITE_GROWABLE),
					new UndergroundRules(BlockTagInit.ANTHRACITE_CARVABLE)
			)));
	//@formatter:on

	public static PlanetType create(PlanetType name) {
		PLANET_TYPES.add(name);
		return name;
	}

	public static PlanetType pickRandom(Random rand) {
		int index = rand.nextInt(PLANET_TYPES.size());
		return PLANET_TYPES.get(index);
	}
}