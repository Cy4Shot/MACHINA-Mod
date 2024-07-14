package com.machina.registration.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockFamiliesInit {

	public static List<WoodFamily> WOODS = new ArrayList<>();
	public static List<StoneFamily> STONES = new ArrayList<>();

	static {
		WOODS.add(new WoodFamily(BlockInit.TROPICAL_LOG.get(), BlockInit.TROPICAL_WOOD.get(),
				BlockInit.STRIPPED_TROPICAL_LOG.get(), BlockInit.STRIPPED_TROPICAL_WOOD.get(),
				BlockInit.TROPICAL_PLANKS.get(), BlockInit.TROPICAL_STAIRS.get(), BlockInit.TROPICAL_SLAB.get(),
				BlockInit.TROPICAL_FENCE.get(), BlockInit.TROPICAL_FENCE_GATE.get(), BlockInit.TROPICAL_DOOR.get(),
				BlockInit.TROPICAL_TRAPDOOR.get(), BlockInit.TROPICAL_PRESSURE_PLATE.get(),
				BlockInit.TROPICAL_BUTTON.get(), ItemInit.TROPICAL_SIGN.get(), ItemInit.TROPICAL_HANGING_SIGN.get(),
				BlockInit.TROPICAL_SIGN.get(), BlockInit.TROPICAL_WALL_SIGN.get(),
				BlockInit.TROPICAL_HANGING_SIGN.get(), BlockInit.TROPICAL_WALL_HANGING_SIGN.get(), BlockInit.TROPICAL_LEAVES.get()));
		WOODS.add(new WoodFamily(BlockInit.PINE_LOG.get(), BlockInit.PINE_WOOD.get(), BlockInit.STRIPPED_PINE_LOG.get(),
				BlockInit.STRIPPED_PINE_WOOD.get(), BlockInit.PINE_PLANKS.get(), BlockInit.PINE_STAIRS.get(),
				BlockInit.PINE_SLAB.get(), BlockInit.PINE_FENCE.get(), BlockInit.PINE_FENCE_GATE.get(),
				BlockInit.PINE_DOOR.get(), BlockInit.PINE_TRAPDOOR.get(), BlockInit.PINE_PRESSURE_PLATE.get(),
				BlockInit.PINE_BUTTON.get(), ItemInit.PINE_SIGN.get(), ItemInit.PINE_HANGING_SIGN.get(),
				BlockInit.PINE_SIGN.get(), BlockInit.PINE_WALL_SIGN.get(), BlockInit.PINE_HANGING_SIGN.get(),
				BlockInit.PINE_WALL_HANGING_SIGN.get(), BlockInit.PINE_LEAVES.get()));
		
		STONES.add(new StoneFamily(BlockInit.ANTHRACITE.get(), BlockInit.ANTHRACITE_SLAB.get(), BlockInit.ANTHRACITE_STAIRS.get(),
				BlockInit.ANTHRACITE_WALL.get()));
		STONES.add(new StoneFamily(BlockInit.FELDSPAR.get(), BlockInit.FELDSPAR_SLAB.get(), BlockInit.FELDSPAR_STAIRS.get(),
				BlockInit.FELDSPAR_WALL.get()));
	}

	public static final record WoodFamily(Block log, Block wood, Block stripped_log, Block stripped_wood, Block planks,
			Block stairs, Block slab, Block fence, Block fencegate, Block door, Block trapdoor, Block pressure_plate,
			Block button, Item sign, Item hangingsign, Block signblock, Block wallsignblock, Block hangingsignblock,
			Block hangingwallsignblock, Block leaves) {
	}
	
	public static final record StoneFamily(Block base, Block slab, Block stairs, Block wall) {}
}
