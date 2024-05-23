package com.machina.datagen.server;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DatagenBlockTags extends BlockTagsProvider {
	public DatagenBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Machina.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(@NotNull Provider pProvider) {
		tag(BlockTags.SLABS).add(BlockInit.ANTHRACITE_SLAB.get());
		tag(BlockTags.STAIRS).add(BlockInit.ANTHRACITE_STAIRS.get());
		tag(BlockTags.WALLS).add(BlockInit.ANTHRACITE_WALL.get());
		
		tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(BlockInit.ANTHRACITE.get());
	}
}