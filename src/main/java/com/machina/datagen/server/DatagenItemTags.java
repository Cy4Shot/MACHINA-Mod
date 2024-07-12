package com.machina.datagen.server;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.ItemInit;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DatagenItemTags extends ItemTagsProvider {
	public DatagenItemTags(PackOutput po, CompletableFuture<HolderLookup.Provider> p_275729_,
			CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
		super(po, p_275729_, p_275322_, Machina.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(@NotNull Provider pProvider) {
		tag(ItemTags.SLABS).add(BlockInit.ANTHRACITE_SLAB.get().asItem(), BlockInit.FELDSPAR_SLAB.get().asItem(),
				BlockInit.TROPICAL_SLAB.get().asItem());
		tag(ItemTags.STAIRS).add(BlockInit.ANTHRACITE_STAIRS.get().asItem(), BlockInit.FELDSPAR_STAIRS.get().asItem(),
				BlockInit.TROPICAL_STAIRS.get().asItem());
		tag(ItemTags.WALLS).add(BlockInit.ANTHRACITE_WALL.get().asItem(), BlockInit.FELDSPAR_WALL.get().asItem());

		tag(ItemTags.BUTTONS).add(BlockInit.TROPICAL_BUTTON.get().asItem());
		tag(ItemTags.WOODEN_BUTTONS).add(BlockInit.TROPICAL_BUTTON.get().asItem());
		tag(ItemTags.DOORS).add(BlockInit.TROPICAL_DOOR.get().asItem());
		tag(ItemTags.WOODEN_DOORS).add(BlockInit.TROPICAL_DOOR.get().asItem());
		tag(ItemTags.TRAPDOORS).add(BlockInit.TROPICAL_TRAPDOOR.get().asItem());
		tag(ItemTags.WOODEN_TRAPDOORS).add(BlockInit.TROPICAL_TRAPDOOR.get().asItem());
		tag(ItemTags.FENCES).add(BlockInit.TROPICAL_FENCE.get().asItem());
		tag(ItemTags.WOODEN_FENCES).add(BlockInit.TROPICAL_FENCE.get().asItem());
		tag(ItemTags.FENCE_GATES).add(BlockInit.TROPICAL_FENCE_GATE.get().asItem());
		tag(ItemTags.WOODEN_PRESSURE_PLATES).add(BlockInit.TROPICAL_PRESSURE_PLATE.get().asItem());

		tag(ItemTags.SIGNS).add(ItemInit.TROPICAL_SIGN.get());
		tag(ItemTags.HANGING_SIGNS).add(ItemInit.TROPICAL_HANGING_SIGN.get());

		tag(ItemTags.DIRT).add(BlockInit.TROPICAL_GRASS_BLOCK.get().asItem(), BlockInit.TROPICAL_DIRT.get().asItem());
		tag(ItemTags.LOGS).add(BlockInit.TROPICAL_LOG.get().asItem(), BlockInit.STRIPPED_TROPICAL_LOG.get().asItem(),
				BlockInit.TROPICAL_WOOD.get().asItem(), BlockInit.STRIPPED_TROPICAL_WOOD.get().asItem());
		tag(ItemTags.PLANKS).add(BlockInit.TROPICAL_PLANKS.get().asItem());
		tag(ItemTags.LEAVES).add(BlockInit.TROPICAL_LEAVES.get().asItem());

		tag(ItemTags.FLOWERS).add(BlockInit.DRAGON_PEONY.get().asItem(), BlockInit.ORHPEUM.get().asItem());
		tag(ItemTags.SMALL_FLOWERS).add(BlockInit.DRAGON_PEONY.get().asItem());
		tag(ItemTags.TALL_FLOWERS).add(BlockInit.ORHPEUM.get().asItem());

		tag(ItemTags.STONE_CRAFTING_MATERIALS).add(BlockInit.ANTHRACITE.get().asItem(),
				BlockInit.FELDSPAR.get().asItem());
	}
}