package com.machina.datagen.server;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.machina.Machina;
import com.machina.registration.init.BlockFamiliesInit;
import com.machina.registration.init.BlockFamiliesInit.StoneFamily;
import com.machina.registration.init.BlockFamiliesInit.WoodFamily;
import com.machina.registration.init.BlockInit;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class DatagenItemTags extends ItemTagsProvider {
	public DatagenItemTags(PackOutput po, CompletableFuture<HolderLookup.Provider> p_275729_,
			CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
		super(po, p_275729_, p_275322_, Machina.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(@NotNull Provider pProvider) {

		tag(ItemTags.DIRT).add(BlockInit.TROPICAL_GRASS_BLOCK.get().asItem(), BlockInit.TROPICAL_DIRT.get().asItem(),
				BlockInit.FOREST_GRASS_BLOCK.get().asItem(), BlockInit.FOREST_DIRT.get().asItem(),
				BlockInit.PEAT.get().asItem(), BlockInit.SILT.get().asItem());

		flower(BlockInit.CLOVER);
		smallFlower(BlockInit.PURPLE_GLOWSHROOM);
		smallFlower(BlockInit.PINK_GLOWSHROOM);
		smallFlower(BlockInit.RED_GLOWSHROOM);
		smallFlower(BlockInit.ORANGE_GLOWSHROOM);
		smallFlower(BlockInit.YELLOW_GLOWSHROOM);
		smallFlower(BlockInit.GREEN_GLOWSHROOM);
		smallFlower(BlockInit.TURQUOISE_GLOWSHROOM);
		smallFlower(BlockInit.BLUE_GLOWSHROOM);
		smallFlower(BlockInit.DRAGON_PEONY);
		tallFlower(BlockInit.ORPHEUM);

		BlockFamiliesInit.STONES.forEach(this::stoneFamily);
		BlockFamiliesInit.WOODS.forEach(this::woodFamily);
	}

	private void smallFlower(RegistryObject<FlowerBlock> flower) {
		flower(flower);
		tag(ItemTags.SMALL_FLOWERS).add(flower.get().asItem());
	}

	private void tallFlower(RegistryObject<TallFlowerBlock> flower) {
		flower(flower);
		tag(ItemTags.TALL_FLOWERS).add(flower.get().asItem());
	}

	private void flower(RegistryObject<? extends BushBlock> flower) {
		tag(ItemTags.FLOWERS).add(flower.get().asItem());
	}

	private void stoneFamily(StoneFamily family) {
		tag(ItemTags.SLABS).add(family.slab().asItem());
		tag(ItemTags.STAIRS).add(family.stairs().asItem());
		tag(ItemTags.WALLS).add(family.wall().asItem());

		tag(ItemTags.BUTTONS).add(family.button().asItem());
		tag(ItemTags.STONE_BUTTONS).add(family.button().asItem());

		tag(ItemTags.STONE_CRAFTING_MATERIALS).add(family.base().asItem());
	}

	private void woodFamily(WoodFamily family) {
		tag(ItemTags.SLABS).add(family.slab().asItem());
		tag(ItemTags.STAIRS).add(family.stairs().asItem());

		tag(ItemTags.BUTTONS).add(family.button().asItem());
		tag(ItemTags.WOODEN_BUTTONS).add(family.button().asItem());
		tag(ItemTags.DOORS).add(family.door().asItem());
		tag(ItemTags.WOODEN_DOORS).add(family.door().asItem());
		tag(ItemTags.TRAPDOORS).add(family.trapdoor().asItem());
		tag(ItemTags.WOODEN_TRAPDOORS).add(family.trapdoor().asItem());
		tag(ItemTags.FENCES).add(family.fence().asItem());
		tag(ItemTags.WOODEN_FENCES).add(family.fence().asItem());
		tag(ItemTags.FENCE_GATES).add(family.fencegate().asItem());
		tag(ItemTags.WOODEN_PRESSURE_PLATES).add(family.pressure_plate().asItem());

		tag(ItemTags.SIGNS).add(family.sign());
		tag(ItemTags.HANGING_SIGNS).add(family.hangingsign());

		tag(ItemTags.LOGS).add(family.log().asItem(), family.stripped_log().asItem(), family.wood().asItem(),
				family.stripped_wood().asItem());
		tag(ItemTags.PLANKS).add(family.planks().asItem());
		tag(ItemTags.LEAVES).add(family.leaves().asItem());
	}
}