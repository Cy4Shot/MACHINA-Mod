package com.machina.datagen.server;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;

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
		tag(ItemTags.SLABS).add(BlockInit.ANTHRACITE_SLAB.get().asItem(), BlockInit.FELDSPAR_SLAB.get().asItem());
		tag(ItemTags.STAIRS).add(BlockInit.ANTHRACITE_STAIRS.get().asItem(), BlockInit.FELDSPAR_STAIRS.get().asItem());
		tag(ItemTags.WALLS).add(BlockInit.ANTHRACITE_WALL.get().asItem(), BlockInit.FELDSPAR_WALL.get().asItem());

		tag(ItemTags.FLOWERS).add(BlockInit.DRAGON_PEONY.get().asItem(), BlockInit.ORHPEUM.get().asItem());
		tag(ItemTags.SMALL_FLOWERS).add(BlockInit.DRAGON_PEONY.get().asItem());
		tag(ItemTags.TALL_FLOWERS).add(BlockInit.ORHPEUM.get().asItem());

		tag(ItemTags.STONE_CRAFTING_MATERIALS).add(BlockInit.ANTHRACITE.get().asItem(),
				BlockInit.FELDSPAR.get().asItem());
	}
}