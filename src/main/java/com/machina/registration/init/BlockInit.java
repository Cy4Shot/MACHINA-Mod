package com.machina.registration.init;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.machina.Machina;
import com.machina.api.block.MachinaHangingSignBlock;
import com.machina.api.block.MachinaHangingWallSignBlock;
import com.machina.api.block.MachinaSignBlock;
import com.machina.api.block.MachinaWallSignBlock;
import com.machina.api.block.SmallFlowerBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final List<RegistryObject<? extends Block>> SIGNS = new ArrayList<>();
	public static final List<RegistryObject<? extends Block>> HANGING_SIGNS = new ArrayList<>();

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Machina.MOD_ID);

	//@formatter:off
	public static final RegistryObject<Block> ALUMINUM_BLOCK = block("aluminum_block", Blocks.IRON_BLOCK);
	public static final RegistryObject<Block> ALUMINUM_ORE = block("aluminum_ore", Blocks.IRON_ORE);

	public static final RegistryObject<Block> ANTHRACITE = block("anthracite", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> ANTHRACITE_SLAB = slab("anthracite_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> ANTHRACITE_STAIRS = stairs("anthracite_stairs", ANTHRACITE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> ANTHRACITE_WALL = wall("anthracite_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> ANTHRACITE_PRESSURE_PLATE = stone_pressure_plate("anthracite_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> ANTHRACITE_BUTTON = stone_button("anthracite_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> FELDSPAR = block("feldspar", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> FELDSPAR_SLAB = slab("feldspar_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> FELDSPAR_STAIRS = stairs("feldspar_stairs", FELDSPAR, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> FELDSPAR_WALL = wall("feldspar_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> FELDSPAR_PRESSURE_PLATE = stone_pressure_plate("feldspar_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> FELDSPAR_BUTTON = stone_button("feldspar_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> GRAY_SOAPSTONE = block("gray_soapstone", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> GRAY_SOAPSTONE_SLAB = slab("gray_soapstone_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> GRAY_SOAPSTONE_STAIRS = stairs("gray_soapstone_stairs", GRAY_SOAPSTONE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> GRAY_SOAPSTONE_WALL = wall("gray_soapstone_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> GRAY_SOAPSTONE_PRESSURE_PLATE = stone_pressure_plate("gray_soapstone_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> GRAY_SOAPSTONE_BUTTON = stone_button("gray_soapstone_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> GREEN_SOAPSTONE = block("green_soapstone", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> GREEN_SOAPSTONE_SLAB = slab("green_soapstone_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> GREEN_SOAPSTONE_STAIRS = stairs("green_soapstone_stairs", GREEN_SOAPSTONE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> GREEN_SOAPSTONE_WALL = wall("green_soapstone_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> GREEN_SOAPSTONE_PRESSURE_PLATE = stone_pressure_plate("green_soapstone_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> GREEN_SOAPSTONE_BUTTON = stone_button("green_soapstone_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> WHITE_SOAPSTONE = block("white_soapstone", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> WHITE_SOAPSTONE_SLAB = slab("white_soapstone_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> WHITE_SOAPSTONE_STAIRS = stairs("white_soapstone_stairs", WHITE_SOAPSTONE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> WHITE_SOAPSTONE_WALL = wall("white_soapstone_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> WHITE_SOAPSTONE_PRESSURE_PLATE = stone_pressure_plate("white_soapstone_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> WHITE_SOAPSTONE_BUTTON = stone_button("white_soapstone_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> SHALE = block("shale", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> SHALE_SLAB = slab("shale_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> SHALE_STAIRS = stairs("shale_stairs", SHALE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> SHALE_WALL = wall("shale_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> SHALE_PRESSURE_PLATE = stone_pressure_plate("shale_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> SHALE_BUTTON = stone_button("shale_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> TECTONITE = block("tectonite", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> TECTONITE_SLAB = slab("tectonite_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> TECTONITE_STAIRS = stairs("tectonite_stairs", TECTONITE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> TECTONITE_WALL = wall("tectonite_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> TECTONITE_PRESSURE_PLATE = stone_pressure_plate("tectonite_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> TECTONITE_BUTTON = stone_button("tectonite_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> MARBLE = block("marble", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> MARBLE_SLAB = slab("marble_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> MARBLE_STAIRS = stairs("marble_stairs", MARBLE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> MARBLE_WALL = wall("marble_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> MARBLE_PRESSURE_PLATE = stone_pressure_plate("marble_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> MARBLE_BUTTON = stone_button("marble_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> CHALK = block("chalk", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> CHALK_SLAB = slab("chalk_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> CHALK_STAIRS = stairs("chalk_stairs", CHALK, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> CHALK_WALL = wall("chalk_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> CHALK_PRESSURE_PLATE = stone_pressure_plate("chalk_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> CHALK_BUTTON = stone_button("chalk_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> LIMESTONE = block("limestone", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> LIMESTONE_SLAB = slab("limestone_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> LIMESTONE_STAIRS = stairs("limestone_stairs", LIMESTONE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> LIMESTONE_WALL = wall("limestone_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> LIMESTONE_PRESSURE_PLATE = stone_pressure_plate("limestone_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> LIMESTONE_BUTTON = stone_button("limestone_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> MIGMATITE = block("migmatite", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> MIGMATITE_SLAB = slab("migmatite_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> MIGMATITE_STAIRS = stairs("migmatite_stairs", MIGMATITE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> MIGMATITE_WALL = wall("migmatite_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> MIGMATITE_PRESSURE_PLATE = stone_pressure_plate("migmatite_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> MIGMATITE_BUTTON = stone_button("migmatite_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> GNEISS = block("gneiss", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> GNEISS_SLAB = slab("gneiss_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> GNEISS_STAIRS = stairs("gneiss_stairs", GNEISS, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> GNEISS_WALL = wall("gneiss_wall", Blocks.ANDESITE_WALL);
	public static final RegistryObject<PressurePlateBlock> GNEISS_PRESSURE_PLATE = stone_pressure_plate("gneiss_pressure_plate", Blocks.STONE_PRESSURE_PLATE);
	public static final RegistryObject<ButtonBlock> GNEISS_BUTTON = stone_button("gneiss_button", Blocks.STONE_BUTTON);
	
	public static final RegistryObject<Block> TROPICAL_GRASS_BLOCK = block("tropical_grass_block", Blocks.GRASS_BLOCK);
	public static final RegistryObject<Block> FOREST_GRASS_BLOCK = block("forest_grass_block", Blocks.GRASS_BLOCK);
	
	public static final RegistryObject<Block> TROPICAL_DIRT = block("tropical_dirt", Blocks.DIRT);
	public static final RegistryObject<StairBlock> TROPICAL_DIRT_STAIRS = stairs("tropical_dirt_stairs", TROPICAL_DIRT, Blocks.DIRT);
	public static final RegistryObject<SlabBlock> TROPICAL_DIRT_SLAB = slab("tropical_dirt_slab", Blocks.DIRT);
	
	public static final RegistryObject<Block> FOREST_DIRT = block("forest_dirt", Blocks.DIRT);
	public static final RegistryObject<StairBlock> FOREST_DIRT_STAIRS = stairs("forest_dirt_stairs", FOREST_DIRT, Blocks.DIRT);
	public static final RegistryObject<SlabBlock> FOREST_DIRT_SLAB = slab("forest_dirt_slab", Blocks.DIRT);
	
	public static final RegistryObject<Block> SILT = block("silt", Blocks.MUD);
	public static final RegistryObject<StairBlock> SILT_STAIRS = stairs("silt_stairs", SILT, Blocks.MUD);
	public static final RegistryObject<SlabBlock> SILT_SLAB = slab("silt_slab", Blocks.MUD);
	
	public static final RegistryObject<Block> PEAT = block("peat", Blocks.MUD);
	public static final RegistryObject<StairBlock> PEAT_STAIRS = stairs("peat_stairs", PEAT, Blocks.MUD);
	public static final RegistryObject<SlabBlock> PEAT_SLAB = slab("peat_slab", Blocks.MUD);
	
	public static final WoodType TROPICAL = registerWoodType("tropical");
	public static final WoodType PINE = registerWoodType("pine");
	
	public static final RegistryObject<RotatedPillarBlock> TROPICAL_LOG = log("tropical_log", Blocks.OAK_LOG);
	public static final RegistryObject<RotatedPillarBlock> TROPICAL_WOOD = log("tropical_wood", Blocks.OAK_WOOD);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_TROPICAL_LOG = log("stripped_tropical_log", Blocks.STRIPPED_OAK_LOG);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_TROPICAL_WOOD = log("stripped_tropical_wood", Blocks.STRIPPED_OAK_WOOD);
	public static final RegistryObject<Block> TROPICAL_PLANKS = block("tropical_planks", Blocks.OAK_PLANKS);
	public static final RegistryObject<LeavesBlock> TROPICAL_LEAVES = leaves("tropical_leaves", Blocks.OAK_LEAVES);
	public static final RegistryObject<SlabBlock> TROPICAL_SLAB = slab("tropical_slab", Blocks.OAK_SLAB);
	public static final RegistryObject<StairBlock> TROPICAL_STAIRS = stairs("tropical_stairs", TROPICAL_PLANKS, Blocks.OAK_STAIRS);
	public static final RegistryObject<MachinaSignBlock> TROPICAL_SIGN = sign("tropical_sign", Blocks.OAK_SIGN, TROPICAL);
	public static final RegistryObject<MachinaWallSignBlock> TROPICAL_WALL_SIGN = wall_sign("tropical_wall_sign", Blocks.OAK_WALL_SIGN, TROPICAL);
	public static final RegistryObject<MachinaHangingSignBlock> TROPICAL_HANGING_SIGN = hanging_sign("tropical_hanging_sign", Blocks.OAK_HANGING_SIGN, TROPICAL);
	public static final RegistryObject<MachinaHangingWallSignBlock> TROPICAL_WALL_HANGING_SIGN = wall_hanging_sign("tropical_wall_hanging_sign", Blocks.OAK_WALL_HANGING_SIGN, TROPICAL);
	public static final RegistryObject<ButtonBlock> TROPICAL_BUTTON = wood_button("tropical_button", Blocks.OAK_BUTTON, TROPICAL);
	public static final RegistryObject<DoorBlock> TROPICAL_DOOR = wood_door("tropical_door", Blocks.OAK_DOOR, TROPICAL);
	public static final RegistryObject<TrapDoorBlock> TROPICAL_TRAPDOOR = wood_trapdoor("tropical_trapdoor", Blocks.OAK_TRAPDOOR, TROPICAL);
	public static final RegistryObject<FenceBlock> TROPICAL_FENCE = fence("tropical_fence", Blocks.OAK_FENCE);
	public static final RegistryObject<FenceGateBlock> TROPICAL_FENCE_GATE = fence_gate("tropical_fence_gate", Blocks.OAK_FENCE_GATE, TROPICAL);
	public static final RegistryObject<PressurePlateBlock> TROPICAL_PRESSURE_PLATE = wood_pressure_plate("tropical_pressure_plate", Blocks.OAK_PRESSURE_PLATE, TROPICAL);
	
	public static final RegistryObject<RotatedPillarBlock> PINE_LOG = log("pine_log", Blocks.OAK_LOG);
	public static final RegistryObject<RotatedPillarBlock> PINE_WOOD = log("pine_wood", Blocks.OAK_WOOD);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PINE_LOG = log("stripped_pine_log", Blocks.STRIPPED_OAK_LOG);
	public static final RegistryObject<RotatedPillarBlock> STRIPPED_PINE_WOOD = log("stripped_pine_wood", Blocks.STRIPPED_OAK_WOOD);
	public static final RegistryObject<Block> PINE_PLANKS = block("pine_planks", Blocks.OAK_PLANKS);
	public static final RegistryObject<LeavesBlock> PINE_LEAVES = leaves("pine_leaves", Blocks.OAK_LEAVES);
	public static final RegistryObject<SlabBlock> PINE_SLAB = slab("pine_slab", Blocks.OAK_SLAB);
	public static final RegistryObject<StairBlock> PINE_STAIRS = stairs("pine_stairs", PINE_PLANKS, Blocks.OAK_STAIRS);
	public static final RegistryObject<MachinaSignBlock> PINE_SIGN = sign("pine_sign", Blocks.OAK_SIGN, PINE);
	public static final RegistryObject<MachinaWallSignBlock> PINE_WALL_SIGN = wall_sign("pine_wall_sign", Blocks.OAK_WALL_SIGN, PINE);
	public static final RegistryObject<MachinaHangingSignBlock> PINE_HANGING_SIGN = hanging_sign("pine_hanging_sign", Blocks.OAK_HANGING_SIGN, PINE);
	public static final RegistryObject<MachinaHangingWallSignBlock> PINE_WALL_HANGING_SIGN = wall_hanging_sign("pine_wall_hanging_sign", Blocks.OAK_WALL_HANGING_SIGN, PINE);
	public static final RegistryObject<ButtonBlock> PINE_BUTTON = wood_button("pine_button", Blocks.OAK_BUTTON, PINE);
	public static final RegistryObject<DoorBlock> PINE_DOOR = wood_door("pine_door", Blocks.OAK_DOOR, PINE);
	public static final RegistryObject<TrapDoorBlock> PINE_TRAPDOOR = wood_trapdoor("pine_trapdoor", Blocks.OAK_TRAPDOOR, PINE);
	public static final RegistryObject<FenceBlock> PINE_FENCE = fence("pine_fence", Blocks.OAK_FENCE);
	public static final RegistryObject<FenceGateBlock> PINE_FENCE_GATE = fence_gate("pine_fence_gate", Blocks.OAK_FENCE_GATE, PINE);
	public static final RegistryObject<PressurePlateBlock> PINE_PRESSURE_PLATE = wood_pressure_plate("pine_pressure_plate", Blocks.OAK_PRESSURE_PLATE, PINE);
	
	public static final RegistryObject<BushBlock> TWISTED_GRASS = register("twisted_grass", Blocks.GRASS, BushBlock::new);
	public static final RegistryObject<SmallFlowerBlock> GROUND_LILLIES = register("ground_lillies", Blocks.PINK_PETALS, SmallFlowerBlock::new);
	public static final RegistryObject<SmallFlowerBlock> CLOVER = register("clover", Blocks.PINK_PETALS, SmallFlowerBlock::new);
	public static final RegistryObject<PinkPetalsBlock> PURPLE_PETALS = register("purple_petals", Blocks.PINK_PETALS, PinkPetalsBlock::new);
	
	public static final RegistryObject<SmallFlowerBlock> SPINDLESPROUT = register("spindlesprout", Blocks.FERN, SmallFlowerBlock::new);
	public static final RegistryObject<SmallFlowerBlock> SMALL_FERN = register("small_fern", Blocks.FERN, SmallFlowerBlock::new);
	public static final RegistryObject<SmallFlowerBlock> DEAD_SMALL_FERN = register("dead_small_fern", Blocks.FERN, SmallFlowerBlock::new);
	
	public static final RegistryObject<TallFlowerBlock> SPINDLEGRASS = tall_flower("spindlegrass", Blocks.SUNFLOWER);
	public static final RegistryObject<TallFlowerBlock> ORPHEUM = tall_flower("orpheum", Blocks.PEONY);

	public static final RegistryObject<FlowerBlock> DRAGON_PEONY = flower("dragon_peony", () -> MobEffects.LEVITATION, 5, Blocks.DANDELION);
	public static final RegistryObject<FlowerPotBlock> POTTED_DRAGON_PEONY = flower_pot("potted_dragon_peony", DRAGON_PEONY);
	public static final RegistryObject<FlowerPotBlock> POTTED_SPINDLESPROUT = flower_pot("potted_spindlesprout", SPINDLESPROUT);
	public static final RegistryObject<FlowerPotBlock> POTTED_SMALL_FERN = flower_pot("potted_small_fern", SMALL_FERN);
	public static final RegistryObject<FlowerPotBlock> POTTED_DEAD_SMALL_FERN = flower_pot("potted_dead_small_fern", DEAD_SMALL_FERN);

	public static final RegistryObject<FlowerBlock> PURPLE_GLOWSHROOM = flower("purple_glowshroom", () -> MobEffects.GLOWING, 30, Blocks.BROWN_MUSHROOM, light(7));
	public static final RegistryObject<FlowerBlock> PINK_GLOWSHROOM = flower("pink_glowshroom", () -> MobEffects.GLOWING, 30, Blocks.BROWN_MUSHROOM, light(7));
	public static final RegistryObject<FlowerBlock> RED_GLOWSHROOM = flower("red_glowshroom", () -> MobEffects.GLOWING, 30, Blocks.BROWN_MUSHROOM, light(7));
	public static final RegistryObject<FlowerBlock> ORANGE_GLOWSHROOM = flower("orange_glowshroom", () -> MobEffects.GLOWING, 30, Blocks.BROWN_MUSHROOM, light(7));
	public static final RegistryObject<FlowerBlock> YELLOW_GLOWSHROOM = flower("yellow_glowshroom", () -> MobEffects.GLOWING, 30, Blocks.BROWN_MUSHROOM, light(7));
	public static final RegistryObject<FlowerBlock> GREEN_GLOWSHROOM = flower("green_glowshroom", () -> MobEffects.GLOWING, 30, Blocks.BROWN_MUSHROOM, light(7));
	public static final RegistryObject<FlowerBlock> TURQUOISE_GLOWSHROOM = flower("turquoise_glowshroom", () -> MobEffects.GLOWING, 30, Blocks.BROWN_MUSHROOM, light(7));
	public static final RegistryObject<FlowerBlock> BLUE_GLOWSHROOM = flower("blue_glowshroom", () -> MobEffects.GLOWING, 30, Blocks.BROWN_MUSHROOM, light(7));
	public static final RegistryObject<FlowerPotBlock> POTTED_PURPLE_GLOWSHROOM = flower_pot("potted_purple_glowshroom", PURPLE_GLOWSHROOM, light(7));
	public static final RegistryObject<FlowerPotBlock> POTTED_PINK_GLOWSHROOM = flower_pot("potted_pink_glowshroom", PINK_GLOWSHROOM, light(7));
	public static final RegistryObject<FlowerPotBlock> POTTED_RED_GLOWSHROOM = flower_pot("potted_red_glowshroom", RED_GLOWSHROOM, light(7));
	public static final RegistryObject<FlowerPotBlock> POTTED_ORANGE_GLOWSHROOM = flower_pot("potted_orange_glowshroom", ORANGE_GLOWSHROOM, light(7));
	public static final RegistryObject<FlowerPotBlock> POTTED_YELLOW_GLOWSHROOM = flower_pot("potted_yellow_glowshroom", YELLOW_GLOWSHROOM, light(7));
	public static final RegistryObject<FlowerPotBlock> POTTED_GREEN_GLOWSHROOM = flower_pot("potted_green_glowshroom", GREEN_GLOWSHROOM, light(7));
	public static final RegistryObject<FlowerPotBlock> POTTED_TURQUOISE_GLOWSHROOM = flower_pot("potted_turquoise_glowshroom", TURQUOISE_GLOWSHROOM, light(7));
	public static final RegistryObject<FlowerPotBlock> POTTED_BLUE_GLOWSHROOM = flower_pot("potted_blue_glowshroom", BLUE_GLOWSHROOM, light(7));
	//@formatter:on

	private static WoodType registerWoodType(String name) {
		String id = Machina.MOD_ID + ":" + name;
		return WoodType.register(new WoodType(id, new BlockSetType(id)));
	}

	private static Function<Block.Properties, Block.Properties> light(int light) {
		return p -> p.lightLevel(s -> light);
	}

	private static <T extends Block> Supplier<T> of(Block block, Function<Block.Properties, Block.Properties> extra,
			Function<Block.Properties, T> constructor) {
		return () -> constructor.apply((extra.apply(Block.Properties.copy(block))));
	}

	public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
		return BLOCKS.<T>register(name, block);
	}

	public static RegistryObject<Block> block(String name, Block prop) {
		return register(name, prop, a -> a, Block::new);
	}

	public static RegistryObject<SlabBlock> slab(String name, Block prop) {
		return register(name, prop, a -> a, SlabBlock::new);
	}

	public static RegistryObject<StairBlock> stairs(String name, RegistryObject<Block> block, Block prop) {
		return register(name, prop, a -> a, p -> new StairBlock(() -> block.get().defaultBlockState(), p));
	}

	public static RegistryObject<WallBlock> wall(String name, Block prop) {
		return register(name, prop, a -> a.hasPostProcess(BlockInit::always), WallBlock::new);
	}

	public static RegistryObject<RotatedPillarBlock> log(String name, Block prop) {
		return register(name, prop, a -> a, RotatedPillarBlock::new);
	}

	public static RegistryObject<LeavesBlock> leaves(String name, Block prop) {
		return register(name, prop, a -> a, LeavesBlock::new);
	}

	public static RegistryObject<MachinaSignBlock> sign(String name, Block prop, WoodType wood) {
		RegistryObject<MachinaSignBlock> s = registerNI(name, prop, a -> a, p -> new MachinaSignBlock(p, wood));
		SIGNS.add(s);
		return s;
	}

	public static RegistryObject<MachinaWallSignBlock> wall_sign(String name, Block prop, WoodType wood) {
		RegistryObject<MachinaWallSignBlock> s = registerNI(name, prop, a -> a, p -> new MachinaWallSignBlock(p, wood));
		SIGNS.add(s);
		return s;
	}

	public static RegistryObject<MachinaHangingSignBlock> hanging_sign(String name, Block prop, WoodType wood) {
		RegistryObject<MachinaHangingSignBlock> s = registerNI(name, prop, a -> a,
				p -> new MachinaHangingSignBlock(p, wood));
		HANGING_SIGNS.add(s);
		return s;
	}

	public static RegistryObject<MachinaHangingWallSignBlock> wall_hanging_sign(String name, Block prop,
			WoodType wood) {
		RegistryObject<MachinaHangingWallSignBlock> s = registerNI(name, prop, a -> a,
				p -> new MachinaHangingWallSignBlock(p, wood));
		HANGING_SIGNS.add(s);
		return s;
	}

	public static RegistryObject<ButtonBlock> wood_button(String name, Block prop, WoodType wood) {
		return register(name, prop, a -> a, p -> new ButtonBlock(p, wood.setType(), 30, true));
	}

	public static RegistryObject<DoorBlock> wood_door(String name, Block prop, WoodType wood) {
		return register(name, prop, a -> a, p -> new DoorBlock(p, wood.setType()));
	}

	public static RegistryObject<TrapDoorBlock> wood_trapdoor(String name, Block prop, WoodType wood) {
		return register(name, prop, a -> a, p -> new TrapDoorBlock(p, wood.setType()));
	}

	public static RegistryObject<FenceBlock> fence(String name, Block prop) {
		return register(name, prop, a -> a, FenceBlock::new);
	}

	public static RegistryObject<FenceGateBlock> fence_gate(String name, Block prop, WoodType wood) {
		return register(name, prop, a -> a, p -> new FenceGateBlock(p, wood));
	}

	public static RegistryObject<PressurePlateBlock> wood_pressure_plate(String name, Block prop, WoodType wood) {
		return register(name, prop, a -> a,
				p -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, p, wood.setType()));
	}

	public static RegistryObject<ButtonBlock> stone_button(String name, Block prop) {
		return register(name, prop, a -> a, p -> new ButtonBlock(p, BlockSetType.STONE, 20, false));
	}

	public static RegistryObject<PressurePlateBlock> stone_pressure_plate(String name, Block prop) {
		return register(name, prop, a -> a,
				p -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, p, BlockSetType.STONE));
	}

	public static RegistryObject<FlowerBlock> flower(String name, Supplier<MobEffect> effect, int duration, Block prop,
			Function<Block.Properties, Block.Properties> extra) {
		return register(name, prop, extra, p -> new FlowerBlock(effect, duration, p));
	}

	public static RegistryObject<FlowerBlock> flower(String name, Supplier<MobEffect> effect, int duration,
			Block prop) {
		return register(name, prop, a -> a, p -> new FlowerBlock(effect, duration, p));
	}

	public static RegistryObject<TallFlowerBlock> tall_flower(String name, Block prop) {
		return register(name, prop, a -> a, TallFlowerBlock::new);
	}

	public static RegistryObject<FlowerPotBlock> flower_pot(String name, RegistryObject<? extends Block> flower) {
		return register(name, BlockInit.of(Blocks.FLOWER_POT, a -> a, p -> new FlowerPotBlock(flower.get(), p)));
	}

	public static RegistryObject<FlowerPotBlock> flower_pot(String name, RegistryObject<FlowerBlock> flower,
			Function<Block.Properties, Block.Properties> extra) {
		return register(name, BlockInit.of(Blocks.FLOWER_POT, extra, p -> new FlowerPotBlock(flower.get(), p)));
	}

	public static <T extends Block> RegistryObject<T> register(String name, Block prop,
			Function<Block.Properties, T> constructor) {
		return register(name, prop, a -> a, constructor);
	}

	public static <T extends Block> RegistryObject<T> registerNI(String name, Block prop,
			Function<Block.Properties, Block.Properties> extra, Function<Block.Properties, T> constructor) {
		return register(name, BlockInit.<T>of(prop, extra, constructor));
	}

	public static <T extends Block> RegistryObject<T> register(String name, Block prop,
			Function<Block.Properties, Block.Properties> extra, Function<Block.Properties, T> constructor) {
		RegistryObject<T> ro = register(name, BlockInit.<T>of(prop, extra, constructor));
		registerBlockItem(name, ro);
		return ro;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}

	private static boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
		return true;
	}
}
