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
		tag(BlockTags.SLABS).add(BlockInit.ANTHRACITE_SLAB.get(), BlockInit.FELDSPAR_SLAB.get(), BlockInit.TROPICAL_SLAB.get());
		tag(BlockTags.STAIRS).add(BlockInit.ANTHRACITE_STAIRS.get(), BlockInit.FELDSPAR_STAIRS.get(), BlockInit.TROPICAL_STAIRS.get());
		tag(BlockTags.WALLS).add(BlockInit.ANTHRACITE_WALL.get(), BlockInit.FELDSPAR_WALL.get());
		
		tag(BlockTags.BUTTONS).add(BlockInit.TROPICAL_BUTTON.get());
		tag(BlockTags.WOODEN_BUTTONS).add(BlockInit.TROPICAL_BUTTON.get());
		tag(BlockTags.DOORS).add(BlockInit.TROPICAL_DOOR.get());
		tag(BlockTags.WOODEN_DOORS).add(BlockInit.TROPICAL_DOOR.get());
		tag(BlockTags.TRAPDOORS).add(BlockInit.TROPICAL_TRAPDOOR.get());
		tag(BlockTags.WOODEN_TRAPDOORS).add(BlockInit.TROPICAL_TRAPDOOR.get());
		tag(BlockTags.FENCES).add(BlockInit.TROPICAL_FENCE.get());
		tag(BlockTags.WOODEN_FENCES).add(BlockInit.TROPICAL_FENCE.get());
		tag(BlockTags.FENCE_GATES).add(BlockInit.TROPICAL_FENCE_GATE.get());
		tag(BlockTags.PRESSURE_PLATES).add(BlockInit.TROPICAL_PRESSURE_PLATE.get());
		tag(BlockTags.WOODEN_PRESSURE_PLATES).add(BlockInit.TROPICAL_PRESSURE_PLATE.get());
		
		tag(BlockTags.ALL_SIGNS).add(BlockInit.TROPICAL_SIGN.get(), BlockInit.TROPICAL_WALL_SIGN.get());
		tag(BlockTags.SIGNS).add(BlockInit.TROPICAL_SIGN.get());
		tag(BlockTags.WALL_SIGNS).add(BlockInit.TROPICAL_WALL_SIGN.get());
		tag(BlockTags.ALL_HANGING_SIGNS).add(BlockInit.TROPICAL_HANGING_SIGN.get(), BlockInit.TROPICAL_WALL_HANGING_SIGN.get());
		tag(BlockTags.CEILING_HANGING_SIGNS).add(BlockInit.TROPICAL_HANGING_SIGN.get());
		tag(BlockTags.WALL_HANGING_SIGNS).add(BlockInit.TROPICAL_WALL_HANGING_SIGN.get());
		
		tag(BlockTags.LOGS).add(BlockInit.TROPICAL_LOG.get(), BlockInit.STRIPPED_TROPICAL_LOG.get(),
				BlockInit.TROPICAL_WOOD.get(), BlockInit.STRIPPED_TROPICAL_WOOD.get());
		tag(BlockTags.PLANKS).add(BlockInit.TROPICAL_PLANKS.get());
		tag(BlockTags.LEAVES).add(BlockInit.TROPICAL_LEAVES.get());

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