package com.machina.api.starchart.planet_biome;

import java.util.List;
import java.util.stream.Collectors;

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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public record PlanetBiomeJsonInfo(PlanetBiomeEffects effects, String base, String top, String second, String stair,
		String slab, String extra, List<PlanetBiomeTreeJsonInfo> trees, List<PlanetBiomeBushJsonInfo> bushes,
		PlanetBiomeGrassJsonInfo grass, PlanetBiomeLakesJsonInfo lakes, List<PlanetBiomeRockJsonInfo> rocks,
		List<PlanetBiomeOreJsonInfo> ores) implements JsonInfo<PlanetBiomeSettings> {

	public static BlockState getBlock(String block) {
		return BlockHelper.parseState(BlockHelper.blockHolderLookup(), block);
	}

	public record PlanetBiomeTreeJsonInfo(String type, String wood, String leaves, float chance, List<String> fruits,
			float fruit_chance, float tree_fruit_chance) implements JsonInfo<PlanetBiomeTree> {

		@Override
		public PlanetBiomeTree cast() {
			List<BlockState> fruits = fruits().stream().map(PlanetBiomeJsonInfo::getBlock).collect(Collectors.toList());
			return new PlanetBiomeTree(new ResourceLocation(type), getBlock(wood), getBlock(leaves), chance, fruits,
					fruit_chance, tree_fruit_chance);
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
		List<PlanetBiomeOre> ores = ores().stream().map(PlanetBiomeOreJsonInfo::cast).collect(Collectors.toList());

		return new PlanetBiomeSettings(effects, getBlock(base), getBlock(top), getBlock(second), getBlock(stair),
				getBlock(slab), getBlock(extra), trees, bushes, grass, lakes, rocks, ores);
	}
}