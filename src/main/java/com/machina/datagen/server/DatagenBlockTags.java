package com.machina.datagen.server;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.BlockFamiliesInit;
import com.machina.registration.init.BlockFamiliesInit.StoneFamily;
import com.machina.registration.init.BlockFamiliesInit.WoodFamily;
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
		tag(BlockTags.DIRT).add(BlockInit.TROPICAL_GRASS_BLOCK.get(), BlockInit.TROPICAL_DIRT.get());

		tag(BlockTags.FLOWERS).add(BlockInit.DRAGON_PEONY.get(), BlockInit.ORHPEUM.get());
		tag(BlockTags.SMALL_FLOWERS).add(BlockInit.DRAGON_PEONY.get());
		tag(BlockTags.TALL_FLOWERS).add(BlockInit.ORHPEUM.get());
		tag(BlockTags.FLOWER_POTS).add(BlockInit.POTTED_DRAGON_PEONY.get());

		tag(BlockTagInit.EARTHLIKE_GROWABLE).add(BlockInit.TROPICAL_GRASS_BLOCK.get());
		tag(BlockTagInit.ANTHRACITE_GROWABLE).add(Blocks.COAL_ORE);
		tag(BlockTagInit.MARTIAN_GROWABLE).add(Blocks.RED_SAND);

		tag(BlockTagInit.EARTHLIKE_CARVABLE).add(Blocks.STONE, BlockInit.TROPICAL_DIRT.get(),
				BlockInit.TROPICAL_GRASS_BLOCK.get(), Blocks.GRAVEL, Blocks.ANDESITE, Blocks.WATER);
		tag(BlockTagInit.ANTHRACITE_CARVABLE).add(Blocks.COAL_ORE, Blocks.STONE, Blocks.GRAVEL,
				BlockInit.ANTHRACITE.get(), Blocks.WATER);
		tag(BlockTagInit.MARTIAN_CARVABLE).add(Blocks.RED_SAND, Blocks.RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE,
				BlockInit.FELDSPAR.get(), Blocks.WATER);
		
		BlockFamiliesInit.STONES.forEach(this::stoneFamily);
		BlockFamiliesInit.WOODS.forEach(this::woodFamily);
	}
	
	private void stoneFamily(StoneFamily family) {
		tag(BlockTags.SLABS).add(family.slab());
		tag(BlockTags.STAIRS).add(family.stairs());
		tag(BlockTags.WALLS).add(family.wall());

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