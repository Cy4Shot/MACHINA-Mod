package com.machina.datagen.server;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.TagInit.BlockTagInit;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DatagenBlockTags extends BlockTagsProvider {
	public DatagenBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			@Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, Machina.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(@NotNull Provider pProvider) {
		tag(BlockTags.SLABS).add(BlockInit.ANTHRACITE_SLAB.get(), BlockInit.FELDSPAR_SLAB.get());
		tag(BlockTags.STAIRS).add(BlockInit.ANTHRACITE_STAIRS.get(), BlockInit.FELDSPAR_STAIRS.get());
		tag(BlockTags.WALLS).add(BlockInit.ANTHRACITE_WALL.get(), BlockInit.FELDSPAR_WALL.get());

		tag(BlockTags.FLOWERS).add(BlockInit.DRAGON_PEONY.get(), BlockInit.ORHPEUM.get());
		tag(BlockTags.SMALL_FLOWERS).add(BlockInit.DRAGON_PEONY.get());
		tag(BlockTags.TALL_FLOWERS).add(BlockInit.ORHPEUM.get());
		tag(BlockTags.FLOWER_POTS).add(BlockInit.POTTED_DRAGON_PEONY.get());

		tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(BlockInit.ANTHRACITE.get(), BlockInit.FELDSPAR.get());

		tag(BlockTagInit.EARTHLIKE_GROWABLE).add(Blocks.GRASS_BLOCK);
		tag(BlockTagInit.ANTHRACITE_GROWABLE).add(Blocks.COAL_ORE);
		tag(BlockTagInit.MARTIAN_GROWABLE).add(Blocks.RED_SAND);

		tag(BlockTagInit.EARTHLIKE_CARVABLE).add(Blocks.STONE, Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.GRAVEL,
				Blocks.ANDESITE, Blocks.WATER);
		tag(BlockTagInit.ANTHRACITE_CARVABLE).add(Blocks.COAL_ORE, Blocks.STONE, Blocks.GRAVEL,
				BlockInit.ANTHRACITE.get(), Blocks.WATER);
		tag(BlockTagInit.MARTIAN_CARVABLE).add(Blocks.RED_SAND, Blocks.RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE,
				BlockInit.FELDSPAR.get(), Blocks.WATER);
	}
}