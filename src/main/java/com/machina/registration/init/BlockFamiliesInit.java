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
				BlockInit.TROPICAL_HANGING_SIGN.get(), BlockInit.TROPICAL_WALL_HANGING_SIGN.get(),
				BlockInit.TROPICAL_LEAVES.get()));
		WOODS.add(new WoodFamily(BlockInit.PINE_LOG.get(), BlockInit.PINE_WOOD.get(), BlockInit.STRIPPED_PINE_LOG.get(),
				BlockInit.STRIPPED_PINE_WOOD.get(), BlockInit.PINE_PLANKS.get(), BlockInit.PINE_STAIRS.get(),
				BlockInit.PINE_SLAB.get(), BlockInit.PINE_FENCE.get(), BlockInit.PINE_FENCE_GATE.get(),
				BlockInit.PINE_DOOR.get(), BlockInit.PINE_TRAPDOOR.get(), BlockInit.PINE_PRESSURE_PLATE.get(),
				BlockInit.PINE_BUTTON.get(), ItemInit.PINE_SIGN.get(), ItemInit.PINE_HANGING_SIGN.get(),
				BlockInit.PINE_SIGN.get(), BlockInit.PINE_WALL_SIGN.get(), BlockInit.PINE_HANGING_SIGN.get(),
				BlockInit.PINE_WALL_HANGING_SIGN.get(), BlockInit.PINE_LEAVES.get()));

		STONES.add(new StoneFamily(BlockInit.ANTHRACITE.get(), BlockInit.ANTHRACITE_SLAB.get(),
				BlockInit.ANTHRACITE_STAIRS.get(), BlockInit.ANTHRACITE_WALL.get(),
				BlockInit.ANTHRACITE_PRESSURE_PLATE.get(), BlockInit.ANTHRACITE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.FELDSPAR.get(), BlockInit.FELDSPAR_SLAB.get(),
				BlockInit.FELDSPAR_STAIRS.get(), BlockInit.FELDSPAR_WALL.get(), BlockInit.FELDSPAR_PRESSURE_PLATE.get(),
				BlockInit.FELDSPAR_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.GRAY_SOAPSTONE.get(), BlockInit.GRAY_SOAPSTONE_SLAB.get(),
				BlockInit.GRAY_SOAPSTONE_STAIRS.get(), BlockInit.GRAY_SOAPSTONE_WALL.get(),
				BlockInit.GRAY_SOAPSTONE_PRESSURE_PLATE.get(), BlockInit.GRAY_SOAPSTONE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.GREEN_SOAPSTONE.get(), BlockInit.GREEN_SOAPSTONE_SLAB.get(),
				BlockInit.GREEN_SOAPSTONE_STAIRS.get(), BlockInit.GREEN_SOAPSTONE_WALL.get(),
				BlockInit.GREEN_SOAPSTONE_PRESSURE_PLATE.get(), BlockInit.GREEN_SOAPSTONE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.WHITE_SOAPSTONE.get(), BlockInit.WHITE_SOAPSTONE_SLAB.get(),
				BlockInit.WHITE_SOAPSTONE_STAIRS.get(), BlockInit.WHITE_SOAPSTONE_WALL.get(),
				BlockInit.WHITE_SOAPSTONE_PRESSURE_PLATE.get(), BlockInit.WHITE_SOAPSTONE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.SHALE.get(), BlockInit.SHALE_SLAB.get(),
				BlockInit.SHALE_STAIRS.get(), BlockInit.SHALE_WALL.get(), BlockInit.SHALE_PRESSURE_PLATE.get(),
				BlockInit.SHALE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.TECTONITE.get(), BlockInit.TECTONITE_SLAB.get(),
				BlockInit.TECTONITE_STAIRS.get(), BlockInit.TECTONITE_WALL.get(), BlockInit.TECTONITE_PRESSURE_PLATE.get(),
				BlockInit.TECTONITE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.MARBLE.get(), BlockInit.MARBLE_SLAB.get(),
				BlockInit.MARBLE_STAIRS.get(), BlockInit.MARBLE_WALL.get(), BlockInit.MARBLE_PRESSURE_PLATE.get(),
				BlockInit.MARBLE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.CHALK.get(), BlockInit.CHALK_SLAB.get(),
				BlockInit.CHALK_STAIRS.get(), BlockInit.CHALK_WALL.get(), BlockInit.CHALK_PRESSURE_PLATE.get(),
				BlockInit.CHALK_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.LIMESTONE.get(), BlockInit.LIMESTONE_SLAB.get(),
				BlockInit.LIMESTONE_STAIRS.get(), BlockInit.LIMESTONE_WALL.get(), BlockInit.LIMESTONE_PRESSURE_PLATE.get(),
				BlockInit.LIMESTONE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.MIGMATITE.get(), BlockInit.MIGMATITE_SLAB.get(),
				BlockInit.MIGMATITE_STAIRS.get(), BlockInit.MIGMATITE_WALL.get(), BlockInit.MIGMATITE_PRESSURE_PLATE.get(),
				BlockInit.MIGMATITE_BUTTON.get()));
		STONES.add(new StoneFamily(BlockInit.GNEISS.get(), BlockInit.GNEISS_SLAB.get(),
				BlockInit.GNEISS_STAIRS.get(), BlockInit.GNEISS_WALL.get(), BlockInit.GNEISS_PRESSURE_PLATE.get(),
				BlockInit.GNEISS_BUTTON.get()));
	}

	public static final record WoodFamily(Block log, Block wood, Block stripped_log, Block stripped_wood, Block planks,
			Block stairs, Block slab, Block fence, Block fencegate, Block door, Block trapdoor, Block pressure_plate,
			Block button, Item sign, Item hangingsign, Block signblock, Block wallsignblock, Block hangingsignblock,
			Block hangingwallsignblock, Block leaves) {
	}

	public static final record StoneFamily(Block base, Block slab, Block stairs, Block wall, Block pressure_plate,
			Block button) {
	}
}
