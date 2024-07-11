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
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
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
	
	public static final RegistryObject<Block> FELDSPAR = block("feldspar", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> FELDSPAR_SLAB = slab("feldspar_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> FELDSPAR_STAIRS = stairs("feldspar_stairs", FELDSPAR, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> FELDSPAR_WALL = wall("feldspar_wall", Blocks.ANDESITE_WALL);
	
	
	public static final WoodType TROPICAL = registerWoodType("tropical");
	
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
	
	public static final RegistryObject<SmallFlowerBlock> CLOVER = register("clover", Blocks.PINK_PETALS, SmallFlowerBlock::new);
	public static final RegistryObject<FlowerBlock> DRAGON_PEONY = flower("dragon_peony", () -> MobEffects.LEVITATION, 5, Blocks.DANDELION);
	public static final RegistryObject<TallFlowerBlock> ORHPEUM = tall_flower	("orpheum", Blocks.PEONY);
	public static final RegistryObject<FlowerPotBlock> POTTED_DRAGON_PEONY = flower_pot("potted_dragon_peony", DRAGON_PEONY);
	//@formatter:on

	private static WoodType registerWoodType(String name) {
		String id = Machina.MOD_ID + ":" + name;
		return WoodType.register(new WoodType(id, new BlockSetType(id)));
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

	public static RegistryObject<FlowerBlock> flower(String name, Supplier<MobEffect> effect, int duration,
			Block prop) {
		return register(name, prop, a -> a, p -> new FlowerBlock(effect, duration, p));
	}

	public static RegistryObject<TallFlowerBlock> tall_flower(String name, Block prop) {
		return register(name, prop, a -> a, TallFlowerBlock::new);
	}

	public static RegistryObject<FlowerPotBlock> flower_pot(String name, RegistryObject<FlowerBlock> flower) {
		return register(name, BlockInit.of(Blocks.FLOWER_POT, a -> a, p -> new FlowerPotBlock(flower.get(), p)));
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
