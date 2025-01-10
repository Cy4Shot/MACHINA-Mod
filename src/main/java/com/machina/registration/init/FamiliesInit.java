package com.machina.registration.init;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.machina.Machina;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class FamiliesInit {

	public static List<OreFamily> ORES = new ArrayList<>();
	public static List<DirtFamily> DIRTS = new ArrayList<>();
	public static List<WoodFamily> WOODS = new ArrayList<>();
	public static List<StoneFamily> STONES = new ArrayList<>();

	static {
		ORES.add(OreFamily.gemLike("coal", Blocks.COAL_ORE, Blocks.COAL_BLOCK, Items.COAL, ItemInit.COAL_DUST.get()));
		ORES.add(OreFamily.gemLike("lapis", Blocks.LAPIS_ORE, Blocks.LAPIS_BLOCK, Items.LAPIS_LAZULI,
				ItemInit.LAPIS_DUST.get()));
		ORES.add(OreFamily.gemLike("quartz", Blocks.NETHER_QUARTZ_ORE, Blocks.QUARTZ_BLOCK, Items.QUARTZ,
				ItemInit.QUARTZ_DUST.get()));
		ORES.add(OreFamily.gemLike("emerald", Blocks.EMERALD_ORE, Blocks.EMERALD_BLOCK, Items.EMERALD,
				ItemInit.EMERALD_DUST.get()));
		ORES.add(OreFamily.ingotLike("iron", Blocks.IRON_ORE, Blocks.IRON_BLOCK, Items.IRON_NUGGET, Items.IRON_INGOT,
				ItemInit.IRON_DUST.get(), ItemInit.IRON_PLATE.get(), ItemInit.IRON_ROD.get(),
				ItemInit.IRON_WIRE.get()));
		ORES.add(OreFamily.ingotLike("gold", Blocks.GOLD_ORE, Blocks.GOLD_BLOCK, Items.GOLD_NUGGET, Items.GOLD_INGOT,
				ItemInit.GOLD_DUST.get(), ItemInit.GOLD_PLATE.get(), ItemInit.GOLD_ROD.get(),
				ItemInit.GOLD_WIRE.get()));
		ORES.add(OreFamily.ingotLike("copper", Blocks.COPPER_ORE, Blocks.COPPER_BLOCK, ItemInit.COPPER_NUGGET.get(),
				Items.COPPER_INGOT, ItemInit.COPPER_DUST.get(), ItemInit.COPPER_PLATE.get(), ItemInit.COPPER_ROD.get(),
				ItemInit.COPPER_WIRE.get()));
		ORES.add(OreFamily.ingotLike("diamond", Blocks.DIAMOND_ORE, Blocks.DIAMOND_BLOCK, ItemInit.DIAMOND_NUGGET.get(),
				Items.DIAMOND, ItemInit.DIAMOND_DUST.get(), ItemInit.DIAMOND_PLATE.get(), ItemInit.DIAMOND_ROD.get(),
				ItemInit.DIAMOND_WIRE.get()));

		DIRTS.add(new DirtFamily("tropical", BlockInit.TROPICAL_DIRT.get(), BlockInit.TROPICAL_DIRT_STAIRS.get(),
				BlockInit.TROPICAL_DIRT_SLAB.get(), Optional.of(BlockInit.TROPICAL_GRASS_BLOCK.get())));
		DIRTS.add(new DirtFamily("forest", BlockInit.FOREST_DIRT.get(), BlockInit.FOREST_DIRT_STAIRS.get(),
				BlockInit.FOREST_DIRT_SLAB.get(), Optional.of(BlockInit.FOREST_GRASS_BLOCK.get())));
		DIRTS.add(new DirtFamily("coniferous", BlockInit.CONIFEROUS_DIRT.get(), BlockInit.CONIFEROUS_DIRT_STAIRS.get(),
				BlockInit.CONIFEROUS_DIRT_SLAB.get(), Optional.of(BlockInit.CONIFEROUS_GRASS_BLOCK.get())));
		DIRTS.add(new DirtFamily("windswept", BlockInit.WINDSWEPT_DIRT.get(), BlockInit.WINDSWEPT_DIRT_STAIRS.get(),
				BlockInit.WINDSWEPT_DIRT_SLAB.get(), Optional.of(BlockInit.WINDSWEPT_GRASS_BLOCK.get())));
		DIRTS.add(new DirtFamily("mycelial", BlockInit.MYCELIAL_DIRT.get(), BlockInit.MYCELIAL_DIRT_STAIRS.get(),
				BlockInit.MYCELIAL_DIRT_SLAB.get(), Optional.of(BlockInit.MYCELIAL_GRASS_BLOCK.get())));
		DIRTS.add(new DirtFamily("peat", BlockInit.PEAT.get(), BlockInit.PEAT_STAIRS.get(), BlockInit.PEAT_SLAB.get()));
		DIRTS.add(new DirtFamily("silt", BlockInit.SILT.get(), BlockInit.SILT_STAIRS.get(), BlockInit.SILT_SLAB.get()));

		WOODS.add(new WoodFamily("tropical", BlockInit.TROPICAL_LOG.get(), BlockInit.TROPICAL_WOOD.get(),
				BlockInit.STRIPPED_TROPICAL_LOG.get(), BlockInit.STRIPPED_TROPICAL_WOOD.get(),
				BlockInit.TROPICAL_PLANKS.get(), BlockInit.TROPICAL_STAIRS.get(), BlockInit.TROPICAL_SLAB.get(),
				BlockInit.TROPICAL_FENCE.get(), BlockInit.TROPICAL_FENCE_GATE.get(), BlockInit.TROPICAL_DOOR.get(),
				BlockInit.TROPICAL_TRAPDOOR.get(), BlockInit.TROPICAL_PRESSURE_PLATE.get(),
				BlockInit.TROPICAL_BUTTON.get(), ItemInit.TROPICAL_SIGN.get(), ItemInit.TROPICAL_HANGING_SIGN.get(),
				BlockInit.TROPICAL_SIGN.get(), BlockInit.TROPICAL_WALL_SIGN.get(),
				BlockInit.TROPICAL_HANGING_SIGN.get(), BlockInit.TROPICAL_WALL_HANGING_SIGN.get(),
				new Block[] { BlockInit.TROPICAL_LEAVES.get() }));
		WOODS.add(new WoodFamily("dead_tropical", BlockInit.DEAD_TROPICAL_LOG.get(), BlockInit.DEAD_TROPICAL_WOOD.get(),
				BlockInit.STRIPPED_DEAD_TROPICAL_LOG.get(), BlockInit.STRIPPED_DEAD_TROPICAL_WOOD.get(),
				BlockInit.DEAD_TROPICAL_PLANKS.get(), BlockInit.DEAD_TROPICAL_STAIRS.get(),
				BlockInit.DEAD_TROPICAL_SLAB.get(), BlockInit.DEAD_TROPICAL_FENCE.get(),
				BlockInit.DEAD_TROPICAL_FENCE_GATE.get(), BlockInit.DEAD_TROPICAL_DOOR.get(),
				BlockInit.DEAD_TROPICAL_TRAPDOOR.get(), BlockInit.DEAD_TROPICAL_PRESSURE_PLATE.get(),
				BlockInit.DEAD_TROPICAL_BUTTON.get(), ItemInit.DEAD_TROPICAL_SIGN.get(),
				ItemInit.DEAD_TROPICAL_HANGING_SIGN.get(), BlockInit.DEAD_TROPICAL_SIGN.get(),
				BlockInit.DEAD_TROPICAL_WALL_SIGN.get(), BlockInit.DEAD_TROPICAL_HANGING_SIGN.get(),
				BlockInit.DEAD_TROPICAL_WALL_HANGING_SIGN.get(), new Block[] { BlockInit.DEAD_TROPICAL_LEAVES.get() }));
		WOODS.add(new WoodFamily("pine", BlockInit.PINE_LOG.get(), BlockInit.PINE_WOOD.get(),
				BlockInit.STRIPPED_PINE_LOG.get(), BlockInit.STRIPPED_PINE_WOOD.get(), BlockInit.PINE_PLANKS.get(),
				BlockInit.PINE_STAIRS.get(), BlockInit.PINE_SLAB.get(), BlockInit.PINE_FENCE.get(),
				BlockInit.PINE_FENCE_GATE.get(), BlockInit.PINE_DOOR.get(), BlockInit.PINE_TRAPDOOR.get(),
				BlockInit.PINE_PRESSURE_PLATE.get(), BlockInit.PINE_BUTTON.get(), ItemInit.PINE_SIGN.get(),
				ItemInit.PINE_HANGING_SIGN.get(), BlockInit.PINE_SIGN.get(), BlockInit.PINE_WALL_SIGN.get(),
				BlockInit.PINE_HANGING_SIGN.get(), BlockInit.PINE_WALL_HANGING_SIGN.get(),
				new Block[] { BlockInit.PINE_LEAVES.get() }));
		WOODS.add(new WoodFamily("coniferous", BlockInit.CONIFEROUS_LOG.get(), BlockInit.CONIFEROUS_WOOD.get(),
				BlockInit.STRIPPED_CONIFEROUS_LOG.get(), BlockInit.STRIPPED_CONIFEROUS_WOOD.get(),
				BlockInit.CONIFEROUS_PLANKS.get(), BlockInit.CONIFEROUS_STAIRS.get(), BlockInit.CONIFEROUS_SLAB.get(),
				BlockInit.CONIFEROUS_FENCE.get(), BlockInit.CONIFEROUS_FENCE_GATE.get(),
				BlockInit.CONIFEROUS_DOOR.get(), BlockInit.CONIFEROUS_TRAPDOOR.get(),
				BlockInit.CONIFEROUS_PRESSURE_PLATE.get(), BlockInit.CONIFEROUS_BUTTON.get(),
				ItemInit.CONIFEROUS_SIGN.get(), ItemInit.CONIFEROUS_HANGING_SIGN.get(), BlockInit.CONIFEROUS_SIGN.get(),
				BlockInit.CONIFEROUS_WALL_SIGN.get(), BlockInit.CONIFEROUS_HANGING_SIGN.get(),
				BlockInit.CONIFEROUS_WALL_HANGING_SIGN.get(),
				new Block[] { BlockInit.GREEN_CONIFEROUS_LEAVES.get(), BlockInit.YELLOW_CONIFEROUS_LEAVES.get(),
						BlockInit.ORANGE_CONIFEROUS_LEAVES.get(), BlockInit.RED_CONIFEROUS_LEAVES.get() }));
		WOODS.add(new WoodFamily("cycad", BlockInit.CYCAD_LOG.get(), BlockInit.CYCAD_WOOD.get(),
				BlockInit.STRIPPED_CYCAD_LOG.get(), BlockInit.STRIPPED_CYCAD_WOOD.get(), BlockInit.CYCAD_PLANKS.get(),
				BlockInit.CYCAD_STAIRS.get(), BlockInit.CYCAD_SLAB.get(), BlockInit.CYCAD_FENCE.get(),
				BlockInit.CYCAD_FENCE_GATE.get(), BlockInit.CYCAD_DOOR.get(), BlockInit.CYCAD_TRAPDOOR.get(),
				BlockInit.CYCAD_PRESSURE_PLATE.get(), BlockInit.CYCAD_BUTTON.get(), ItemInit.CYCAD_SIGN.get(),
				ItemInit.CYCAD_HANGING_SIGN.get(), BlockInit.CYCAD_SIGN.get(), BlockInit.CYCAD_WALL_SIGN.get(),
				BlockInit.CYCAD_HANGING_SIGN.get(), BlockInit.CYCAD_WALL_HANGING_SIGN.get(),
				new Block[] { BlockInit.CYCAD_LEAVES.get() }));

		STONES.add(new StoneFamily("anthracite", BlockInit.ANTHRACITE.get(), BlockInit.ANTHRACITE_SLAB.get(),
				BlockInit.ANTHRACITE_STAIRS.get(), BlockInit.ANTHRACITE_WALL.get(),
				BlockInit.ANTHRACITE_PRESSURE_PLATE.get(), BlockInit.ANTHRACITE_BUTTON.get(),
				BlockInit.ANTHRACITE_PEBBLES.get()));
		STONES.add(new StoneFamily("feldspar", BlockInit.FELDSPAR.get(), BlockInit.FELDSPAR_SLAB.get(),
				BlockInit.FELDSPAR_STAIRS.get(), BlockInit.FELDSPAR_WALL.get(), BlockInit.FELDSPAR_PRESSURE_PLATE.get(),
				BlockInit.FELDSPAR_BUTTON.get(), BlockInit.FELDSPAR_PEBBLES.get()));
		STONES.add(new StoneFamily("gray_soapstone", BlockInit.GRAY_SOAPSTONE.get(),
				BlockInit.GRAY_SOAPSTONE_SLAB.get(), BlockInit.GRAY_SOAPSTONE_STAIRS.get(),
				BlockInit.GRAY_SOAPSTONE_WALL.get(), BlockInit.GRAY_SOAPSTONE_PRESSURE_PLATE.get(),
				BlockInit.GRAY_SOAPSTONE_BUTTON.get(), BlockInit.GRAY_SOAPSTONE_PEBBLES.get()));
		STONES.add(new StoneFamily("green_soapstone", BlockInit.GREEN_SOAPSTONE.get(),
				BlockInit.GREEN_SOAPSTONE_SLAB.get(), BlockInit.GREEN_SOAPSTONE_STAIRS.get(),
				BlockInit.GREEN_SOAPSTONE_WALL.get(), BlockInit.GREEN_SOAPSTONE_PRESSURE_PLATE.get(),
				BlockInit.GREEN_SOAPSTONE_BUTTON.get(), BlockInit.GREEN_SOAPSTONE_PEBBLES.get()));
		STONES.add(new StoneFamily("white_soapstone", BlockInit.WHITE_SOAPSTONE.get(),
				BlockInit.WHITE_SOAPSTONE_SLAB.get(), BlockInit.WHITE_SOAPSTONE_STAIRS.get(),
				BlockInit.WHITE_SOAPSTONE_WALL.get(), BlockInit.WHITE_SOAPSTONE_PRESSURE_PLATE.get(),
				BlockInit.WHITE_SOAPSTONE_BUTTON.get(), BlockInit.WHITE_SOAPSTONE_PEBBLES.get()));
		STONES.add(new StoneFamily("shale", BlockInit.SHALE.get(), BlockInit.SHALE_SLAB.get(),
				BlockInit.SHALE_STAIRS.get(), BlockInit.SHALE_WALL.get(), BlockInit.SHALE_PRESSURE_PLATE.get(),
				BlockInit.SHALE_BUTTON.get(), BlockInit.SHALE_PEBBLES.get()));
		STONES.add(new StoneFamily("tectonite", BlockInit.TECTONITE.get(), BlockInit.TECTONITE_SLAB.get(),
				BlockInit.TECTONITE_STAIRS.get(), BlockInit.TECTONITE_WALL.get(),
				BlockInit.TECTONITE_PRESSURE_PLATE.get(), BlockInit.TECTONITE_BUTTON.get(),
				BlockInit.TECTONITE_PEBBLES.get()));
		STONES.add(new StoneFamily("marble", BlockInit.MARBLE.get(), BlockInit.MARBLE_SLAB.get(),
				BlockInit.MARBLE_STAIRS.get(), BlockInit.MARBLE_WALL.get(), BlockInit.MARBLE_PRESSURE_PLATE.get(),
				BlockInit.MARBLE_BUTTON.get(), BlockInit.MARBLE_PEBBLES.get()));
		STONES.add(new StoneFamily("chalk", BlockInit.CHALK.get(), BlockInit.CHALK_SLAB.get(),
				BlockInit.CHALK_STAIRS.get(), BlockInit.CHALK_WALL.get(), BlockInit.CHALK_PRESSURE_PLATE.get(),
				BlockInit.CHALK_BUTTON.get(), BlockInit.CHALK_PEBBLES.get()));
		STONES.add(new StoneFamily("limestone", BlockInit.LIMESTONE.get(), BlockInit.LIMESTONE_SLAB.get(),
				BlockInit.LIMESTONE_STAIRS.get(), BlockInit.LIMESTONE_WALL.get(),
				BlockInit.LIMESTONE_PRESSURE_PLATE.get(), BlockInit.LIMESTONE_BUTTON.get(),
				BlockInit.LIMESTONE_PEBBLES.get()));
		STONES.add(new StoneFamily("migmatite", BlockInit.MIGMATITE.get(), BlockInit.MIGMATITE_SLAB.get(),
				BlockInit.MIGMATITE_STAIRS.get(), BlockInit.MIGMATITE_WALL.get(),
				BlockInit.MIGMATITE_PRESSURE_PLATE.get(), BlockInit.MIGMATITE_BUTTON.get(),
				BlockInit.MIGMATITE_PEBBLES.get()));
		STONES.add(new StoneFamily("gneiss", BlockInit.GNEISS.get(), BlockInit.GNEISS_SLAB.get(),
				BlockInit.GNEISS_STAIRS.get(), BlockInit.GNEISS_WALL.get(), BlockInit.GNEISS_PRESSURE_PLATE.get(),
				BlockInit.GNEISS_BUTTON.get(), BlockInit.GNEISS_PEBBLES.get()));
	}

	public static interface ItemLikeFamily {
		public List<ItemLike> tab();
	}

	public static final record OreFamily(String name, Optional<Block> ore, Optional<Block> block, Optional<Item> nugget,
			Optional<Item> ingot, Item dust, Optional<Item> plate, Optional<Item> rod, Optional<Item> wire)
			implements ItemLikeFamily {

		public static OreFamily gemLike(String name, Block ore, Block block, Item ingot, Item dust) {
			return new OreFamily(name, Optional.of(ore), Optional.of(block), Optional.empty(), Optional.of(ingot), dust,
					Optional.empty(), Optional.empty(), Optional.empty());
		}

		public static OreFamily ingotLike(String name, Block ore, Block block, Item nugget, Item ingot, Item dust,
				Item plate, Item rod, Item wire) {
			return new OreFamily(name, Optional.of(ore), Optional.of(block), Optional.of(nugget), Optional.of(ingot),
					dust, Optional.of(plate), Optional.of(rod), Optional.of(wire));
		}
		
		private static boolean isOurItem(ItemLike item) {
			return BuiltInRegistries.ITEM.getKey(item.asItem()).getNamespace().equals(Machina.MOD_ID);
		}
		
		public Optional<Block> getOre() {
			return ore.filter(OreFamily::isOurItem);
		}
		
		public Optional<Block> getBlock() {
			return block.filter(OreFamily::isOurItem);
		}
		
		public Optional<Item> getNugget() {
			return nugget.filter(OreFamily::isOurItem);
		}
		
		public Optional<Item> getIngot() {
			return ingot.filter(OreFamily::isOurItem);
		}

		@Override
		public List<ItemLike> tab() {
			List<ItemLike> builder = new ArrayList<>();

			Consumer<ItemLike> add = i -> {
				if (BuiltInRegistries.ITEM.getKey(i.asItem()).getNamespace().equals(Machina.MOD_ID)) {
					builder.add(i);
				}
			};

			ore.ifPresent(add);
			ingot.ifPresent(add);
			block.ifPresent(add);
			builder.add(dust);
			plate.ifPresent(add);
			rod.ifPresent(add);
			wire.ifPresent(add);
			return builder;
		}
	}

	public static final record DirtFamily(String name, Block dirt, Block stairs, Block slab, Optional<Block> grass)
			implements ItemLikeFamily {

		public DirtFamily(String name, Block dirt, Block stairs, Block slab) {
			this(name, dirt, stairs, slab, Optional.empty());
		}

		@Override
		public List<ItemLike> tab() {
			if (grass.isPresent()) {
				return List.of(grass.get(), dirt, stairs, slab);
			}
			return List.of(dirt, stairs, slab);
		}
	}

	public static final record WoodFamily(String name, Block log, Block wood, Block stripped_log, Block stripped_wood,
			Block planks, Block stairs, Block slab, Block fence, Block fencegate, Block door, Block trapdoor,
			Block pressure_plate, Block button, Item sign, Item hangingsign, Block signblock, Block wallsignblock,
			Block hangingsignblock, Block hangingwallsignblock, Block[] leaves) implements ItemLikeFamily {

		@Override
		public List<ItemLike> tab() {
			return Stream.concat(Stream.<ItemLike>of(log, wood, stripped_log, stripped_wood, planks, stairs, slab,
					fence, fencegate, door, trapdoor, pressure_plate, button, sign, hangingsign), Arrays.stream(leaves))
					.toList();
		}
	}

	public static final record StoneFamily(String name, Block base, Block slab, Block stairs, Block wall,
			Block pressure_plate, Block button, Block pebbles) implements ItemLikeFamily {

		@Override
		public List<ItemLike> tab() {
			return List.of(base, stairs, slab, wall, pressure_plate, button, pebbles);
		}
	}
}
