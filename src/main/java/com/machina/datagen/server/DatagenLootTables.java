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
import com.machina.registration.init.FruitInit;
import com.machina.registration.init.FruitInit.Fruit;
import com.machina.registration.init.ItemInit;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

public class DatagenLootTables extends LootTableProvider {
	public DatagenLootTables(PackOutput output) {
		super(output, Set.of(), List.of(new SubProviderEntry(BlockLoot::new, LootContextParamSets.BLOCK)));
	}

	public static class BlockLoot extends BlockLootTableProvider {

		@Override
		protected void generate() {
			dropSelf(BlockInit.BASIC_MACHINE_CASE.get());
			
			dropSelf(BlockInit.ALUMINUM_BLOCK.get());

			dropSelf(BlockInit.BROWN_MUSHROOM_STALK.get());
			dropSelf(BlockInit.GREEN_MUSHROOM_STALK.get());
			dropSelf(BlockInit.PURPLE_MUSHROOM_STALK.get());
			dropSelf(BlockInit.YELLOW_MUSHROOM_STALK.get());
			dropSelf(BlockInit.BROWN_MUSHROOM_CAP.get());
			dropSelf(BlockInit.RED_MUSHROOM_CAP.get());
			dropSelf(BlockInit.PURPLE_MUSHROOM_CAP.get());
			dropSelf(BlockInit.YELLOW_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.BROWN_MUSHROOM_GILLS.get(), BlockInit.BROWN_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.RED_MUSHROOM_GILLS.get(), BlockInit.RED_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.PURPLE_MUSHROOM_GILLS.get(), BlockInit.PURPLE_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.YELLOW_MUSHROOM_GILLS.get(), BlockInit.YELLOW_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.SPECKLED_BROWN_MUSHROOM_CAP.get(), BlockInit.BROWN_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.SPECKLED_RED_MUSHROOM_CAP.get(), BlockInit.RED_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.SPECKLED_PURPLE_MUSHROOM_CAP.get(), BlockInit.PURPLE_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.SPECKLED_YELLOW_MUSHROOM_CAP.get(), BlockInit.YELLOW_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.IMBUED_BROWN_MUSHROOM_CAP.get(), BlockInit.BROWN_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.IMBUED_RED_MUSHROOM_CAP.get(), BlockInit.RED_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.IMBUED_PURPLE_MUSHROOM_CAP.get(), BlockInit.PURPLE_MUSHROOM_CAP.get());
			dropAsSilkOr(BlockInit.IMBUED_YELLOW_MUSHROOM_CAP.get(), BlockInit.YELLOW_MUSHROOM_CAP.get());

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

			dropSelf(BlockInit.SPRUCE_CUP.get());
			pot(BlockInit.POTTED_SPRUCE_CUP.get());
			dropSelf(BlockInit.DRAGON_PEONY.get());
			pot(BlockInit.POTTED_DRAGON_PEONY.get());
			dropAsSilk(BlockInit.SPINDLESPROUT.get());
			pot(BlockInit.POTTED_SPINDLESPROUT.get());
			dropAsSilk(BlockInit.SMALL_FERN.get());
			pot(BlockInit.POTTED_SMALL_FERN.get());
			dropAsSilk(BlockInit.DEAD_SMALL_FERN.get());
			pot(BlockInit.POTTED_DEAD_SMALL_FERN.get());
			dropAsSilk(BlockInit.NEEDLEGRASS.get());
			pot(BlockInit.POTTED_NEEDLEGRASS.get());

			dropAsSilk(BlockInit.SPINDLEGRASS.get());
			dropAsSilk(BlockInit.NEEDLETHATCH.get());
			dropSelf(BlockInit.ORPHEUM.get());
			dropAsSilk(BlockInit.CLOVER.get());
			dropAsSilk(BlockInit.TROPICAL_GRASS.get());
			dropAsSilk(BlockInit.TWISTED_GRASS.get());
			dropAsSilk(BlockInit.CONIFEROUS_GRASS.get());
			dropAsSilk(BlockInit.SHORT_CONIFEROUS_GRASS.get());
			dropAsSilk(BlockInit.WINDSWEPT_GRASS.get());
			dropAsSilk(BlockInit.MYCELIAL_GRASS.get());
			dropAsSilk(BlockInit.PURPLE_PETALS.get());
			dropAsSilk(BlockInit.RED_PETALS.get());
			dropAsSilk(BlockInit.ORANGE_PETALS.get());
			dropAsSilk(BlockInit.YELLOW_PETALS.get());
			dropAsSilk(BlockInit.GREEN_PETALS.get());
			dropAsSilk(BlockInit.TURQUOISE_PETALS.get());
			dropAsSilk(BlockInit.BLUE_PETALS.get());

			dropAsSilk(BlockInit.PURPLE_GROUNDLILY.get());
			dropAsSilk(BlockInit.PINK_GROUNDLILY.get());
			dropAsSilk(BlockInit.RED_GROUNDLILY.get());
			dropAsSilk(BlockInit.ORANGE_GROUNDLILY.get());
			dropAsSilk(BlockInit.YELLOW_GROUNDLILY.get());
			dropAsSilk(BlockInit.GREEN_GROUNDLILY.get());
			dropAsSilk(BlockInit.TURQUOISE_GROUNDLILY.get());
			dropAsSilk(BlockInit.BLUE_GROUNDLILY.get());
			dropAsSilk(BlockInit.PURPLE_WATERLILY.get());
			dropAsSilk(BlockInit.PINK_WATERLILY.get());
			dropAsSilk(BlockInit.RED_WATERLILY.get());
			dropAsSilk(BlockInit.ORANGE_WATERLILY.get());
			dropAsSilk(BlockInit.YELLOW_WATERLILY.get());
			dropAsSilk(BlockInit.GREEN_WATERLILY.get());
			dropAsSilk(BlockInit.TURQUOISE_WATERLILY.get());
			dropAsSilk(BlockInit.BLUE_WATERLILY.get());

			ore(BlockInit.ALUMINUM_ORE.get(), ItemInit.RAW_ALUMINUM.get());

			// Fruit
			for (Fruit fruit : FruitInit.FRUITS) {
				dropSelf(fruit.block().get());
			}

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
			dropSelf(family.pebbles());
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
			slab(family.slab());
			for (Block leaf : family.leaves()) {
				leaves(leaf);
			}
		}

		@Override
		protected @NotNull Iterable<Block> getKnownBlocks() {
			return BlockInit.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
		}
	}
}