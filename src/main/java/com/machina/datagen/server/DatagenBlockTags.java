package com.machina.datagen.server;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.machina.Machina;
import com.machina.registration.init.BlockFamiliesInit;
import com.machina.registration.init.BlockFamiliesInit.StoneFamily;
import com.machina.registration.init.BlockFamiliesInit.WoodFamily;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.TagInit.BlockTagInit;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class DatagenBlockTags extends BlockTagsProvider {
	public DatagenBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Machina.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(@NotNull Provider pProvider) {
		tag(BlockTags.DIRT).add(BlockInit.TROPICAL_GRASS_BLOCK.get(), BlockInit.TROPICAL_DIRT.get(),
				BlockInit.FOREST_GRASS_BLOCK.get(), BlockInit.FOREST_DIRT.get(), BlockInit.PEAT.get(),
				BlockInit.SILT.get());

		smallFlower(BlockInit.PURPLE_GLOWSHROOM, BlockInit.POTTED_PURPLE_GLOWSHROOM);
		smallFlower(BlockInit.PINK_GLOWSHROOM, BlockInit.POTTED_PINK_GLOWSHROOM);
		smallFlower(BlockInit.RED_GLOWSHROOM, BlockInit.POTTED_RED_GLOWSHROOM);
		smallFlower(BlockInit.ORANGE_GLOWSHROOM, BlockInit.POTTED_ORANGE_GLOWSHROOM);
		smallFlower(BlockInit.YELLOW_GLOWSHROOM, BlockInit.POTTED_YELLOW_GLOWSHROOM);
		smallFlower(BlockInit.GREEN_GLOWSHROOM, BlockInit.POTTED_GREEN_GLOWSHROOM);
		smallFlower(BlockInit.TURQUOISE_GLOWSHROOM, BlockInit.POTTED_TURQUOISE_GLOWSHROOM);
		smallFlower(BlockInit.BLUE_GLOWSHROOM, BlockInit.POTTED_BLUE_GLOWSHROOM);

		smallFlower(BlockInit.DRAGON_PEONY, BlockInit.POTTED_DRAGON_PEONY);
		tallFlower(BlockInit.ORPHEUM);
		tag(BlockTags.FLOWERS).add(BlockInit.CLOVER.get());

		tag(BlockTagInit.PLANET_GROWABLE).add(BlockInit.TROPICAL_GRASS_BLOCK.get());

		tag(BlockTagInit.PLANET_CARVABLE).add(Blocks.STONE, BlockInit.TROPICAL_DIRT.get(),
				BlockInit.TROPICAL_GRASS_BLOCK.get(), BlockInit.FOREST_GRASS_BLOCK.get(), BlockInit.FOREST_DIRT.get(),
				BlockInit.PEAT.get(), Blocks.GRAVEL, Blocks.WATER);

		BlockFamiliesInit.STONES.forEach(this::stoneFamily);
		BlockFamiliesInit.WOODS.forEach(this::woodFamily);
	}

	private void smallFlower(RegistryObject<FlowerBlock> flower, RegistryObject<FlowerPotBlock> potted) {
		tag(BlockTags.FLOWERS).add(flower.get());
		tag(BlockTags.SMALL_FLOWERS).add(flower.get());
		tag(BlockTags.FLOWER_POTS).add(potted.get());
	}

	private void tallFlower(RegistryObject<TallFlowerBlock> flower) {
		tag(BlockTags.FLOWERS).add(flower.get());
		tag(BlockTags.TALL_FLOWERS).add(flower.get());
	}

	private void stoneFamily(StoneFamily family) {
		tag(BlockTags.SLABS).add(family.slab());
		tag(BlockTags.STAIRS).add(family.stairs());
		tag(BlockTags.WALLS).add(family.wall());

		tag(BlockTags.BUTTONS).add(family.button());
		tag(BlockTags.STONE_BUTTONS).add(family.button());
		tag(BlockTags.PRESSURE_PLATES).add(family.pressure_plate());
		tag(BlockTags.STONE_PRESSURE_PLATES).add(family.pressure_plate());

		tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(family.base());
	}

	private void woodFamily(WoodFamily family) {
		tag(BlockTags.SLABS).add(family.slab());
		tag(BlockTags.STAIRS).add(family.stairs());

		tag(BlockTags.BUTTONS).add(family.button());
		tag(BlockTags.WOODEN_BUTTONS).add(family.button());
		tag(BlockTags.DOORS).add(family.door());
		tag(BlockTags.WOODEN_DOORS).add(family.door());
		tag(BlockTags.TRAPDOORS).add(family.trapdoor());
		tag(BlockTags.WOODEN_TRAPDOORS).add(family.trapdoor());
		tag(BlockTags.FENCES).add(family.fence());
		tag(BlockTags.WOODEN_FENCES).add(family.fence());
		tag(BlockTags.FENCE_GATES).add(family.fencegate());
		tag(BlockTags.PRESSURE_PLATES).add(family.pressure_plate());
		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(family.pressure_plate());

		tag(BlockTags.ALL_SIGNS).add(family.signblock(), family.wallsignblock());
		tag(BlockTags.SIGNS).add(family.signblock());
		tag(BlockTags.WALL_SIGNS).add(family.wallsignblock());
		tag(BlockTags.ALL_HANGING_SIGNS).add(family.hangingsignblock(), family.hangingwallsignblock());
		tag(BlockTags.CEILING_HANGING_SIGNS).add(family.hangingsignblock());
		tag(BlockTags.WALL_HANGING_SIGNS).add(family.hangingwallsignblock());
		tag(BlockTags.LOGS).add(family.log(), family.stripped_log(), family.wood(), family.stripped_wood());

		tag(BlockTags.PLANKS).add(family.planks());
		tag(BlockTags.LEAVES).add(family.leaves());
	}
}