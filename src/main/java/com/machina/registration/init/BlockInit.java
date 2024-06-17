package com.machina.registration.init;

import java.util.function.Function;
import java.util.function.Supplier;

import com.machina.Machina;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			Machina.MOD_ID);

	//@formatter:off
	public static final RegistryObject<Block> ALUMINUM_BLOCK = block("aluminum_block", Blocks.IRON_BLOCK);
	public static final RegistryObject<Block> ALUMINUM_ORE = block("aluminum_ore", Blocks.IRON_ORE);

	public static final RegistryObject<Block> ANTHRACITE = block("anthracite", Blocks.ANDESITE);
	public static final RegistryObject<SlabBlock> ANTHRACITE_SLAB = slab("anthracite_slab", Blocks.ANDESITE_SLAB);
	public static final RegistryObject<StairBlock> ANTHRACITE_STAIRS = stairs("anthracite_stairs", ANTHRACITE, Blocks.ANDESITE_SLAB);
	public static final RegistryObject<WallBlock> ANTHRACITE_WALL = wall("anthracite_wall", Blocks.ANDESITE_WALL);
	
	public static final RegistryObject<FlowerBlock> DRAGON_PEONY = flower("dragon_peony", () -> MobEffects.LEVITATION, 5, Blocks.DANDELION);
	public static final RegistryObject<TallFlowerBlock> ORHPEUM = tall_flower("orpheum", Blocks.PEONY);
	public static final RegistryObject<FlowerPotBlock> POTTED_DRAGON_PEONY = flower_pot("potted_dragon_peony", DRAGON_PEONY);
	//@formatter:on

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
		return register(name, prop, a -> a, WallBlock::new);
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

	public static <T extends Block> RegistryObject<T> register(String name, Block prop,
			Function<Block.Properties, Block.Properties> extra, Function<Block.Properties, T> constructor) {
		RegistryObject<T> ro = register(name, BlockInit.<T>of(prop, extra, constructor));
		registerBlockItem(name, ro);
		return ro;
	}

	private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
		return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
	}
}
