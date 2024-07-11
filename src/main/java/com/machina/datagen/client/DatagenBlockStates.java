package com.machina.datagen.client;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.FluidInit;
import com.machina.registration.init.FluidInit.FluidObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
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

		blockWithItem(BlockInit.FELDSPAR);
		slab(BlockInit.FELDSPAR_SLAB, BlockInit.FELDSPAR);
		stairs(BlockInit.FELDSPAR_STAIRS, BlockInit.FELDSPAR);
		wall(BlockInit.FELDSPAR_WALL, BlockInit.FELDSPAR);

		blockWithItem(BlockInit.TROPICAL_PLANKS);
		leaves(BlockInit.TROPICAL_LEAVES);
		log(BlockInit.TROPICAL_LOG);
		log(BlockInit.TROPICAL_WOOD);
		log(BlockInit.STRIPPED_TROPICAL_LOG);
		log(BlockInit.STRIPPED_TROPICAL_WOOD);
		slab(BlockInit.TROPICAL_SLAB, BlockInit.TROPICAL_PLANKS);
		stairs(BlockInit.TROPICAL_STAIRS, BlockInit.TROPICAL_PLANKS);
		button(BlockInit.TROPICAL_BUTTON, BlockInit.TROPICAL_PLANKS);
		pressure_plate(BlockInit.TROPICAL_PRESSURE_PLATE, BlockInit.TROPICAL_PLANKS);
		fence(BlockInit.TROPICAL_FENCE, BlockInit.TROPICAL_PLANKS);
		fence_gate(BlockInit.TROPICAL_FENCE_GATE, BlockInit.TROPICAL_PLANKS);
		sign(BlockInit.TROPICAL_SIGN, BlockInit.TROPICAL_WALL_SIGN, BlockInit.TROPICAL_PLANKS);
		sign(BlockInit.TROPICAL_HANGING_SIGN, BlockInit.TROPICAL_WALL_HANGING_SIGN, BlockInit.TROPICAL_PLANKS);
		trapdoor(BlockInit.TROPICAL_TRAPDOOR);
		door(BlockInit.TROPICAL_DOOR);

		flower(BlockInit.DRAGON_PEONY);
		tall_flower(BlockInit.ORHPEUM);
		flower_pot(BlockInit.POTTED_DRAGON_PEONY);

		// Fluids
		for (FluidObject obj : FluidInit.OBJS) {
			fluid(obj);
		}
	}

	private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
		simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
	}

	private void leaves(RegistryObject<LeavesBlock> leaves) {
		LeavesBlock b = leaves.get();
		simpleBlockWithItem(b, models().cubeAll(name(b), blockTexture(b)).renderType("translucent"));
	}

	private void slab(RegistryObject<SlabBlock> slab, RegistryObject<Block> material) {
		ResourceLocation texture = blockTexture(material.get());
		SlabBlock b = slab.get();
		slabBlock(b, texture, texture);
		simpleBlockItem(b, models().slab(name(b), texture, texture, texture));
	}

	private void log(RegistryObject<RotatedPillarBlock> log) {
		ResourceLocation texture = blockTexture(log.get());
		RotatedPillarBlock b = log.get();
		logBlock(b);
		simpleBlockItem(b, models().cubeColumn(name(b), texture, extend(texture, "_top")));
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

	private void button(RegistryObject<ButtonBlock> button, RegistryObject<Block> material) {
		ResourceLocation texture = blockTexture(material.get());
		ButtonBlock b = button.get();
		buttonBlock(b, texture);
		simpleBlockItem(b, models().buttonInventory(name(b) + "_inventory", texture));
	}

	private void pressure_plate(RegistryObject<PressurePlateBlock> plate, RegistryObject<Block> material) {
		ResourceLocation texture = blockTexture(material.get());
		PressurePlateBlock b = plate.get();
		pressurePlateBlock(b, texture);
		simpleBlockItem(b, models().pressurePlate(name(b), texture));
	}

	private void fence(RegistryObject<FenceBlock> fence, RegistryObject<Block> material) {
		ResourceLocation texture = blockTexture(material.get());
		FenceBlock b = fence.get();
		fenceBlock(b, texture);
		simpleBlockItem(b, models().fenceInventory(name(b) + "_inventory", texture));
	}

	private void fence_gate(RegistryObject<FenceGateBlock> fence, RegistryObject<Block> material) {
		ResourceLocation texture = blockTexture(material.get());
		FenceGateBlock b = fence.get();
		fenceGateBlock(b, texture);
		simpleBlockItem(b, models().fenceGate(name(b), texture));
	}

	private void sign(RegistryObject<? extends SignBlock> sign, RegistryObject<? extends SignBlock> wall,
			RegistryObject<Block> material) {
		ModelFile mod = models().sign(name(sign.get()), blockTexture(material.get()));
		simpleBlock(sign.get(), mod);
		simpleBlock(wall.get(), mod);
	}

	private void trapdoor(RegistryObject<TrapDoorBlock> door) {
		ResourceLocation texture = blockTexture(door.get());
		TrapDoorBlock b = door.get();
		trapdoorBlockWithRenderType(b, texture, true, "cutout");
		simpleBlockItem(b, models().trapdoorBottom(name(b), texture));
	}

	private void door(RegistryObject<DoorBlock> door) {
		ResourceLocation texture = blockTexture(door.get());
		DoorBlock b = door.get();
		doorBlockWithRenderType(b, extend(texture, "_bottom"), extend(texture, "_top"), "cutout");
		simpleFlatItem(b, itemTexture(b));
	}

	private void fluid(FluidObject obj) {
		getVariantBuilder(obj.block()).partialState().modelForState()
				.modelFile(models().cubeAll(name(obj.block()), new ResourceLocation("block/water_still"))).addModel();
	}

	private void flower(RegistryObject<FlowerBlock> flower) {
		FlowerBlock f = flower.get();
		ResourceLocation tex = blockTexture(f);
		simpleBlock(f, models().cross(name(f), tex).renderType("cutout"));
		simpleFlatItem(f, tex);
	}

	private void tall_flower(RegistryObject<TallFlowerBlock> flower) {
		TallFlowerBlock f = flower.get();
		ResourceLocation tex = blockTexture(f);
		getVariantBuilder(flower.get()).forAllStates(state -> {
			boolean top = state.getValue(TallFlowerBlock.HALF).equals(DoubleBlockHalf.UPPER);
			String name = top ? "_top" : "_bottom";
			ModelFile file = models().cross(name(f) + name, extend(tex, name)).renderType("cutout");
			return ConfiguredModel.builder().modelFile(file).build();
		});
		simpleFlatItem(f, extend(tex, "_top"));
	}

	private void flower_pot(RegistryObject<FlowerPotBlock> pot) {
		FlowerPotBlock p = pot.get();
		simpleBlock(p, models().withExistingParent(name(p), ModelProvider.BLOCK_FOLDER + "/flower_pot_cross")
				.texture("plant", blockTexture(p.getContent())).renderType("cutout"));
	}

	private void simpleFlatItem(Block block, ResourceLocation tex) {
		itemModels().getBuilder(key(block).getPath()).parent(new ModelFile.UncheckedModelFile("item/generated"))
				.texture("layer0", tex);
	}

	private String name(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block).getPath();
	}

	private ResourceLocation extend(ResourceLocation rl, String suffix) {
		return new ResourceLocation(rl.getNamespace(), rl.getPath() + suffix);
	}

	private ResourceLocation key(Block block) {
		return ForgeRegistries.BLOCKS.getKey(block);
	}

	public ResourceLocation itemTexture(Block block) {
		ResourceLocation name = key(block);
		return new ResourceLocation(name.getNamespace(), ModelProvider.ITEM_FOLDER + "/" + name.getPath());
	}
}