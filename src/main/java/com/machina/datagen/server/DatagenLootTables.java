package com.machina.datagen.server;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.machina.datagen.server.provider.BlockLootTableProvider;
import com.machina.registration.init.BlockFamiliesInit;
import com.machina.registration.init.BlockFamiliesInit.StoneFamily;
import com.machina.registration.init.BlockFamiliesInit.WoodFamily;
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

			dropSelf(BlockInit.PURPLE_GLOWSHROOM.get());
			pot(BlockInit.POTTED_PURPLE_GLOWSHROOM.get());
			dropSelf(BlockInit.PINK_GLOWSHROOM.get());
			pot(BlockInit.POTTED_PINK_GLOWSHROOM.get());
			dropSelf(BlockInit.RED_GLOWSHROOM.get());
			pot(BlockInit.POTTED_RED_GLOWSHROOM.get());
			dropSelf(BlockInit.ORANGE_GLOWSHROOM.get());
			pot(BlockInit.POTTED_ORANGE_GLOWSHROOM.get());
			dropSelf(BlockInit.YELLOW_GLOWSHROOM.get());
			pot(BlockInit.POTTED_YELLOW_GLOWSHROOM.get());
			dropSelf(BlockInit.GREEN_GLOWSHROOM.get());
			pot(BlockInit.POTTED_GREEN_GLOWSHROOM.get());
			dropSelf(BlockInit.TURQUOISE_GLOWSHROOM.get());
			pot(BlockInit.POTTED_TURQUOISE_GLOWSHROOM.get());
			dropSelf(BlockInit.BLUE_GLOWSHROOM.get());
			pot(BlockInit.POTTED_BLUE_GLOWSHROOM.get());

			dropSelf(BlockInit.DRAGON_PEONY.get());
			pot(BlockInit.POTTED_DRAGON_PEONY.get());
			dropSelf(BlockInit.ORPHEUM.get());
			dropSelf(BlockInit.CLOVER.get());

			ore(BlockInit.ALUMINUM_ORE.get(), ItemInit.RAW_ALUMINUM.get());

			// Fluids
			for (FluidObject obj : FluidInit.OBJS) {
				dropNone(obj.block());
			}

			BlockFamiliesInit.DIRTS.forEach(this::dirtFamily);
			BlockFamiliesInit.STONES.forEach(this::stoneFamily);
			BlockFamiliesInit.WOODS.forEach(this::woodFamily);
		}

		private void dirtFamily(BlockFamiliesInit.DirtFamily family) {
			dropSelf(family.dirt());
			dropSelf(family.stairs());
			dropSelf(family.slab());
			family.grass().ifPresent(grass -> dropWithSilk(grass, family.dirt()));
		}

		private void stoneFamily(StoneFamily family) {
			dropSelf(family.base());
			dropSelf(family.stairs());
			dropSelf(family.wall());
			dropSelf(family.button());
			dropSelf(family.pressure_plate());
			slab(family.slab());
		}

		private void woodFamily(WoodFamily family) {
			dropSelf(family.button());
			dropSelf(family.door());
			dropSelf(family.fence());
			dropSelf(family.fencegate());
			dropSelf(family.hangingsignblock());
			dropSelf(family.log());
			dropSelf(family.planks());
			dropSelf(family.pressure_plate());
			dropSelf(family.signblock());
			dropSelf(family.stairs());
			dropSelf(family.trapdoor());
			dropSelf(family.wallsignblock());
			dropSelf(family.hangingwallsignblock());
			dropSelf(family.wood());
			dropSelf(family.stripped_log());
			dropSelf(family.stripped_wood());
			leaves(family.leaves());
			slab(family.slab());
		}

		@Override
		protected Iterable<Block> getKnownBlocks() {
			return BlockInit.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
		}
	}
}