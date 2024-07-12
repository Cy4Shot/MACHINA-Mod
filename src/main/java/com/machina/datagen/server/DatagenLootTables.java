package com.machina.datagen.server;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.machina.datagen.server.provider.BlockLootTableProvider;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.FluidInit;
import com.machina.registration.init.FluidInit.FluidObject;
import com.machina.registration.init.ItemInit;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

// TODO: Fix this class. Make a datagen for loot tables.
public class DatagenLootTables extends LootTableProvider {
	public DatagenLootTables(PackOutput output) {
		super(output, Set.of(), List.of(new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)));
	}

	public static class BlockLoot extends BlockLootTableProvider {

		@Override
		protected void generate() {
			dropSelf(BlockInit.ALUMINUM_BLOCK.get());
			dropSelf(BlockInit.ANTHRACITE.get());
			slab(BlockInit.ANTHRACITE_SLAB.get());
			dropSelf(BlockInit.ANTHRACITE_STAIRS.get());
			dropSelf(BlockInit.ANTHRACITE_WALL.get());
			dropSelf(BlockInit.FELDSPAR.get());
			slab(BlockInit.FELDSPAR_SLAB.get());
			dropSelf(BlockInit.FELDSPAR_STAIRS.get());
			dropSelf(BlockInit.FELDSPAR_WALL.get());
			
			dropWithSilk(BlockInit.TROPICAL_GRASS_BLOCK.get(), BlockInit.TROPICAL_DIRT.get());
			dropSelf(BlockInit.TROPICAL_DIRT.get());

			dropSelf(BlockInit.TROPICAL_BUTTON.get());
			dropSelf(BlockInit.TROPICAL_DOOR.get());
			dropSelf(BlockInit.TROPICAL_FENCE.get());
			dropSelf(BlockInit.TROPICAL_FENCE_GATE.get());
			dropSelf(BlockInit.TROPICAL_HANGING_SIGN.get());
			dropSelf(BlockInit.TROPICAL_LOG.get());
			dropSelf(BlockInit.TROPICAL_PLANKS.get());
			leaves(BlockInit.TROPICAL_LEAVES.get());
			dropSelf(BlockInit.TROPICAL_PRESSURE_PLATE.get());
			dropSelf(BlockInit.TROPICAL_SIGN.get());
			slab(BlockInit.TROPICAL_SLAB.get());
			dropSelf(BlockInit.TROPICAL_STAIRS.get());
			dropSelf(BlockInit.TROPICAL_TRAPDOOR.get());
			dropSelf(BlockInit.TROPICAL_WALL_HANGING_SIGN.get());
			dropSelf(BlockInit.TROPICAL_WALL_SIGN.get());
			dropSelf(BlockInit.TROPICAL_WOOD.get());
			dropSelf(BlockInit.STRIPPED_TROPICAL_LOG.get());
			dropSelf(BlockInit.STRIPPED_TROPICAL_WOOD.get());

			dropSelf(BlockInit.DRAGON_PEONY.get());
			pot(BlockInit.POTTED_DRAGON_PEONY.get());
			dropSelf(BlockInit.ORHPEUM.get());
			dropSelf(BlockInit.CLOVER.get());

			// Ore
			ore(BlockInit.ALUMINUM_ORE.get(), ItemInit.RAW_ALUMINUM.get());

			// Fluids
			for (FluidObject obj : FluidInit.OBJS) {
				dropNone(obj.block());
			}
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return BlockInit.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
		}
	}
}