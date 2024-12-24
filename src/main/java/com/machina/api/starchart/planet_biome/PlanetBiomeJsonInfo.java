package com.machina.api.starchart.planet_biome;

import java.util.List;
import java.util.stream.Collectors;

import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeBigRock;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeBush;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeEffects;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeGrass;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeLakes;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeOre;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeRock;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeTree;
import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBlockWeight;
import com.machina.api.util.block.BlockHelper;
import com.machina.api.util.loader.JsonInfo;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public record PlanetBiomeJsonInfo(PlanetBiomeEffects effects, String base, String top, String second, String stair,
		String slab, String extra, List<PlanetBiomeTreeJsonInfo> trees, List<PlanetBiomeBushJsonInfo> bushes,
		PlanetBiomeGrassJsonInfo grass, PlanetBiomeLakesJsonInfo lakes, List<PlanetBiomeRockJsonInfo> rocks,
		List<PlanetBiomeBigRockJsonInfo> big_rocks, List<PlanetBiomeOreJsonInfo> ores)
		implements JsonInfo<PlanetBiomeSettings> {

	public static BlockState getBlock(String block) {
		return BlockHelper.parseState(BlockHelper.blockHolderLookup(), block);
	}

	public record PlanetBiomeTreeJsonInfo(String type, List<String> blocks, float chance, List<String> fruits,
			List<String> fruit_dirs, float fruit_chance, float tree_fruit_chance) implements JsonInfo<PlanetBiomeTree> {

		@Override
		public PlanetBiomeTree cast() {
			List<BlockState> blocks = blocks().stream().map(PlanetBiomeJsonInfo::getBlock).collect(Collectors.toList());
			List<BlockState> fruits = fruits().stream().map(PlanetBiomeJsonInfo::getBlock).collect(Collectors.toList());
			List<Direction> fruit_dirs = fruit_dirs().stream().map(Direction::byName).collect(Collectors.toList());
			return new PlanetBiomeTree(new ResourceLocation(type), blocks, chance, fruits, fruit_dirs, fruit_chance,
					tree_fruit_chance);
		}
	}

	public record PlanetBiomeBushJsonInfo(String block, float radius, int perchunk)
			implements JsonInfo<PlanetBiomeBush> {
		@Override
		public PlanetBiomeBush cast() {
			return new PlanetBiomeBush(getBlock(block), radius, perchunk);
		}
	}

	public record PlanetBiomeGrassJsonInfo(boolean enabled, int min, int max, List<PlanetBlockWeightJsonInfo> grasses)
			implements JsonInfo<PlanetBiomeGrass> {
		@Override
		public PlanetBiomeGrass cast() {
			List<PlanetBlockWeight> grasses = grasses().stream().map(PlanetBlockWeightJsonInfo::cast)
					.collect(Collectors.toList());
			return new PlanetBiomeGrass(enabled, min, max, grasses);
		}
	}

	public record PlanetBiomeLakesJsonInfo(String block, boolean enabled, float chance, float decorator_chance,
			List<PlanetBlockWeightJsonInfo> decorators) implements JsonInfo<PlanetBiomeLakes> {
		@Override
		public PlanetBiomeLakes cast() {
			List<PlanetBlockWeight> decorators = decorators().stream().map(PlanetBlockWeightJsonInfo::cast)
					.collect(Collectors.toList());
			return new PlanetBiomeLakes(getBlock(block), enabled, chance, decorator_chance, decorators);
		}
	}

	public record PlanetBiomeRockJsonInfo(String base, String stair, String slab, String wall, float chance,
			float radius, float deform) implements JsonInfo<PlanetBiomeRock> {
		@Override
		public PlanetBiomeRock cast() {
			return new PlanetBiomeRock(getBlock(base), getBlock(stair), getBlock(slab), getBlock(wall), chance, radius,
					deform);
		}
	}

	public record PlanetBiomeBigRockJsonInfo(String type, String block, String extra, float chance,
			float up_extra_chance, float down_extra_chance, float side_extra_chance)
			implements JsonInfo<PlanetBiomeBigRock> {

		@Override
		public PlanetBiomeBigRock cast() {
			return new PlanetBiomeBigRock(new ResourceLocation(type), getBlock(block), getBlock(extra), chance,
					up_extra_chance, down_extra_chance, side_extra_chance);
		}
	}

	public record PlanetBiomeOreJsonInfo(String block, int size, float exposure_removal_chance, float chance, int min_y,
			int max_y) implements JsonInfo<PlanetBiomeOre> {
		@Override
		public PlanetBiomeOre cast() {
			return new PlanetBiomeOre(getBlock(block), size, exposure_removal_chance, chance, min_y, max_y);
		}
	}

	public record PlanetBlockWeightJsonInfo(String block, int weight) implements JsonInfo<PlanetBlockWeight> {
		@Override
		public PlanetBlockWeight cast() {
			return new PlanetBlockWeight(getBlock(block), weight);
		}
	}

	@Override
	public PlanetBiomeSettings cast() {
		List<PlanetBiomeTree> trees = trees().stream().map(PlanetBiomeTreeJsonInfo::cast).collect(Collectors.toList());
		List<PlanetBiomeBush> bushes = bushes().stream().map(PlanetBiomeBushJsonInfo::cast)
				.collect(Collectors.toList());
		PlanetBiomeGrass grass = grass().cast();
		PlanetBiomeLakes lakes = lakes().cast();
		List<PlanetBiomeRock> rocks = rocks().stream().map(PlanetBiomeRockJsonInfo::cast).collect(Collectors.toList());
		List<PlanetBiomeBigRock> big_rocks = big_rocks().stream().map(PlanetBiomeBigRockJsonInfo::cast)
				.collect(Collectors.toList());
		List<PlanetBiomeOre> ores = ores().stream().map(PlanetBiomeOreJsonInfo::cast).collect(Collectors.toList());

		return new PlanetBiomeSettings(effects, getBlock(base), getBlock(top), getBlock(second), getBlock(stair),
				getBlock(slab), getBlock(extra), trees, bushes, grass, lakes, rocks, big_rocks, ores);
	}
}