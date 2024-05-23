package com.machina.datagen.client;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.FluidInit;
import com.machina.registration.init.FluidInit.FluidObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class DatagenBlockStates extends BlockStateProvider {
	public DatagenBlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
		super(output, Machina.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {

		blockWithItem(BlockInit.ALUMINUM_BLOCK);
		blockWithItem(BlockInit.ALUMINUM_ORE);

		blockWithItem(BlockInit.ANTHRACITE);
		slab(BlockInit.ANTHRACITE_SLAB, BlockInit.ANTHRACITE);
		stairs(BlockInit.ANTHRACITE_STAIRS, BlockInit.ANTHRACITE);
		wall(BlockInit.ANTHRACITE_WALL, BlockInit.ANTHRACITE);

		// Fluids
		for (FluidObject obj : FluidInit.OBJS) {
			fluid(obj);
		}
	}

	private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
		simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
	}

	private void slab(RegistryObject<SlabBlock> slab, RegistryObject<Block> material) {
		ResourceLocation texture = blockTexture(material.get());
		SlabBlock b = slab.get();
		slabBlock(b, texture, texture);
		simpleBlockItem(b, models().slab(name(b), texture, texture, texture));
	}

	private void wall(RegistryObject<WallBlock> wall, RegistryObject<Block> material) {
		ResourceLocation texture = blockTexture(material.get());
		WallBlock b = wall.get();
		wallBlock(b, texture);
		simpleBlockItem(b, models().wallInventory(name(b) + "_inventory", texture));
	}

	private void stairs(RegistryObject<StairBlock> stair, RegistryObject<Block> material) {
		ResourceLocation texture = blockTexture(material.get());
		StairBlock b = stair.get();
		stairsBlock(b, texture);
		simpleBlockItem(b, models().stairs(name(b), texture, texture, texture));
	}

	private void fluid(FluidObject obj) {
		getVariantBuilder(obj.block()).partialState().modelForState()
				.modelFile(models().cubeAll(name(obj.block()), new ResourceLocation("block/water_still"))).addModel();
	}

	private String name(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block).getPath();
	}
}