package com.machina.datagen.client;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.FluidInit;
import com.machina.registration.init.FluidInit.FluidObject;
import com.machina.registration.init.FruitInit;
import com.machina.registration.init.FruitInit.Fruit;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
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

		cube(BlockInit.ALUMINUM_BLOCK);
		cube(BlockInit.ALUMINUM_ORE);

		cube(BlockInit.ANTHRACITE);
		slab(BlockInit.ANTHRACITE_SLAB, BlockInit.ANTHRACITE);
		stairs(BlockInit.ANTHRACITE_STAIRS, BlockInit.ANTHRACITE);
		wall(BlockInit.ANTHRACITE_WALL, BlockInit.ANTHRACITE);
		button(BlockInit.ANTHRACITE_BUTTON, BlockInit.ANTHRACITE);
		pressure_plate(BlockInit.ANTHRACITE_PRESSURE_PLATE, BlockInit.ANTHRACITE);

		cube(BlockInit.FELDSPAR);
		slab(BlockInit.FELDSPAR_SLAB, BlockInit.FELDSPAR);
		stairs(BlockInit.FELDSPAR_STAIRS, BlockInit.FELDSPAR);
		wall(BlockInit.FELDSPAR_WALL, BlockInit.FELDSPAR);
		button(BlockInit.FELDSPAR_BUTTON, BlockInit.FELDSPAR);
		pressure_plate(BlockInit.FELDSPAR_PRESSURE_PLATE, BlockInit.FELDSPAR);

		cube(BlockInit.GRAY_SOAPSTONE);
		slab(BlockInit.GRAY_SOAPSTONE_SLAB, BlockInit.GRAY_SOAPSTONE);
		stairs(BlockInit.GRAY_SOAPSTONE_STAIRS, BlockInit.GRAY_SOAPSTONE);
		wall(BlockInit.GRAY_SOAPSTONE_WALL, BlockInit.GRAY_SOAPSTONE);
		button(BlockInit.GRAY_SOAPSTONE_BUTTON, BlockInit.GRAY_SOAPSTONE);
		pressure_plate(BlockInit.GRAY_SOAPSTONE_PRESSURE_PLATE, BlockInit.GRAY_SOAPSTONE);

		cube(BlockInit.GREEN_SOAPSTONE);
		slab(BlockInit.GREEN_SOAPSTONE_SLAB, BlockInit.GREEN_SOAPSTONE);
		stairs(BlockInit.GREEN_SOAPSTONE_STAIRS, BlockInit.GREEN_SOAPSTONE);
		wall(BlockInit.GREEN_SOAPSTONE_WALL, BlockInit.GREEN_SOAPSTONE);
		button(BlockInit.GREEN_SOAPSTONE_BUTTON, BlockInit.GREEN_SOAPSTONE);
		pressure_plate(BlockInit.GREEN_SOAPSTONE_PRESSURE_PLATE, BlockInit.GREEN_SOAPSTONE);

		cube(BlockInit.WHITE_SOAPSTONE);
		slab(BlockInit.WHITE_SOAPSTONE_SLAB, BlockInit.WHITE_SOAPSTONE);
		stairs(BlockInit.WHITE_SOAPSTONE_STAIRS, BlockInit.WHITE_SOAPSTONE);
		wall(BlockInit.WHITE_SOAPSTONE_WALL, BlockInit.WHITE_SOAPSTONE);
		button(BlockInit.WHITE_SOAPSTONE_BUTTON, BlockInit.WHITE_SOAPSTONE);
		pressure_plate(BlockInit.WHITE_SOAPSTONE_PRESSURE_PLATE, BlockInit.WHITE_SOAPSTONE);

		cube(BlockInit.SHALE);
		slab(BlockInit.SHALE_SLAB, BlockInit.SHALE);
		stairs(BlockInit.SHALE_STAIRS, BlockInit.SHALE);
		wall(BlockInit.SHALE_WALL, BlockInit.SHALE);
		button(BlockInit.SHALE_BUTTON, BlockInit.SHALE);
		pressure_plate(BlockInit.SHALE_PRESSURE_PLATE, BlockInit.SHALE);

		cube(BlockInit.TECTONITE);
		slab(BlockInit.TECTONITE_SLAB, BlockInit.TECTONITE);
		stairs(BlockInit.TECTONITE_STAIRS, BlockInit.TECTONITE);
		wall(BlockInit.TECTONITE_WALL, BlockInit.TECTONITE);
		button(BlockInit.TECTONITE_BUTTON, BlockInit.TECTONITE);
		pressure_plate(BlockInit.TECTONITE_PRESSURE_PLATE, BlockInit.TECTONITE);

		cube(BlockInit.MARBLE);
		slab(BlockInit.MARBLE_SLAB, BlockInit.MARBLE);
		stairs(BlockInit.MARBLE_STAIRS, BlockInit.MARBLE);
		wall(BlockInit.MARBLE_WALL, BlockInit.MARBLE);
		button(BlockInit.MARBLE_BUTTON, BlockInit.MARBLE);
		pressure_plate(BlockInit.MARBLE_PRESSURE_PLATE, BlockInit.MARBLE);

		cube(BlockInit.CHALK);
		slab(BlockInit.CHALK_SLAB, BlockInit.CHALK);
		stairs(BlockInit.CHALK_STAIRS, BlockInit.CHALK);
		wall(BlockInit.CHALK_WALL, BlockInit.CHALK);
		button(BlockInit.CHALK_BUTTON, BlockInit.CHALK);
		pressure_plate(BlockInit.CHALK_PRESSURE_PLATE, BlockInit.CHALK);

		cube(BlockInit.LIMESTONE);
		slab(BlockInit.LIMESTONE_SLAB, BlockInit.LIMESTONE);
		stairs(BlockInit.LIMESTONE_STAIRS, BlockInit.LIMESTONE);
		wall(BlockInit.LIMESTONE_WALL, BlockInit.LIMESTONE);
		button(BlockInit.LIMESTONE_BUTTON, BlockInit.LIMESTONE);
		pressure_plate(BlockInit.LIMESTONE_PRESSURE_PLATE, BlockInit.LIMESTONE);

		cube(BlockInit.MIGMATITE);
		slab(BlockInit.MIGMATITE_SLAB, BlockInit.MIGMATITE);
		stairs(BlockInit.MIGMATITE_STAIRS, BlockInit.MIGMATITE);
		wall(BlockInit.MIGMATITE_WALL, BlockInit.MIGMATITE);
		button(BlockInit.MIGMATITE_BUTTON, BlockInit.MIGMATITE);
		pressure_plate(BlockInit.MIGMATITE_PRESSURE_PLATE, BlockInit.MIGMATITE);

		cube(BlockInit.GNEISS);
		slab(BlockInit.GNEISS_SLAB, BlockInit.GNEISS);
		stairs(BlockInit.GNEISS_STAIRS, BlockInit.GNEISS);
		wall(BlockInit.GNEISS_WALL, BlockInit.GNEISS);
		button(BlockInit.GNEISS_BUTTON, BlockInit.GNEISS);
		pressure_plate(BlockInit.GNEISS_PRESSURE_PLATE, BlockInit.GNEISS);

		cubeBottomTopRandomRotation(BlockInit.TROPICAL_GRASS_BLOCK);
		cubeBottomTopRandomRotation(BlockInit.FOREST_GRASS_BLOCK);

		cubeRandomRotation(BlockInit.TROPICAL_DIRT);
		slab(BlockInit.TROPICAL_DIRT_SLAB, BlockInit.TROPICAL_DIRT);
		stairs(BlockInit.TROPICAL_DIRT_STAIRS, BlockInit.TROPICAL_DIRT);

		cubeRandomRotation(BlockInit.FOREST_DIRT);
		slab(BlockInit.FOREST_DIRT_SLAB, BlockInit.FOREST_DIRT);
		stairs(BlockInit.FOREST_DIRT_STAIRS, BlockInit.FOREST_DIRT);

		cube(BlockInit.PEAT);
		slab(BlockInit.PEAT_SLAB, BlockInit.PEAT);
		stairs(BlockInit.PEAT_STAIRS, BlockInit.PEAT);

		cubeRandomRotation(BlockInit.SILT);
		slab(BlockInit.SILT_SLAB, BlockInit.SILT);
		stairs(BlockInit.SILT_STAIRS, BlockInit.SILT);

		cube(BlockInit.TROPICAL_PLANKS);
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
		
		cube(BlockInit.DEAD_TROPICAL_PLANKS);
		leaves(BlockInit.DEAD_TROPICAL_LEAVES);
		log(BlockInit.DEAD_TROPICAL_LOG);
		log(BlockInit.DEAD_TROPICAL_WOOD);
		log(BlockInit.STRIPPED_DEAD_TROPICAL_LOG);
		log(BlockInit.STRIPPED_DEAD_TROPICAL_WOOD);
		slab(BlockInit.DEAD_TROPICAL_SLAB, BlockInit.DEAD_TROPICAL_PLANKS);
		stairs(BlockInit.DEAD_TROPICAL_STAIRS, BlockInit.DEAD_TROPICAL_PLANKS);
		button(BlockInit.DEAD_TROPICAL_BUTTON, BlockInit.DEAD_TROPICAL_PLANKS);
		pressure_plate(BlockInit.DEAD_TROPICAL_PRESSURE_PLATE, BlockInit.DEAD_TROPICAL_PLANKS);
		fence(BlockInit.DEAD_TROPICAL_FENCE, BlockInit.DEAD_TROPICAL_PLANKS);
		fence_gate(BlockInit.DEAD_TROPICAL_FENCE_GATE, BlockInit.DEAD_TROPICAL_PLANKS);
		sign(BlockInit.DEAD_TROPICAL_SIGN, BlockInit.DEAD_TROPICAL_WALL_SIGN, BlockInit.DEAD_TROPICAL_PLANKS);
		sign(BlockInit.DEAD_TROPICAL_HANGING_SIGN, BlockInit.DEAD_TROPICAL_WALL_HANGING_SIGN, BlockInit.DEAD_TROPICAL_PLANKS);
		trapdoor(BlockInit.DEAD_TROPICAL_TRAPDOOR);
		door(BlockInit.DEAD_TROPICAL_DOOR);

		cube(BlockInit.PINE_PLANKS);
		leaves(BlockInit.PINE_LEAVES);
		log(BlockInit.PINE_LOG);
		log(BlockInit.PINE_WOOD);
		log(BlockInit.STRIPPED_PINE_LOG);
		log(BlockInit.STRIPPED_PINE_WOOD);
		slab(BlockInit.PINE_SLAB, BlockInit.PINE_PLANKS);
		stairs(BlockInit.PINE_STAIRS, BlockInit.PINE_PLANKS);
		button(BlockInit.PINE_BUTTON, BlockInit.PINE_PLANKS);
		pressure_plate(BlockInit.PINE_PRESSURE_PLATE, BlockInit.PINE_PLANKS);
		fence(BlockInit.PINE_FENCE, BlockInit.PINE_PLANKS);
		fence_gate(BlockInit.PINE_FENCE_GATE, BlockInit.PINE_PLANKS);
		sign(BlockInit.PINE_SIGN, BlockInit.PINE_WALL_SIGN, BlockInit.PINE_PLANKS);
		sign(BlockInit.PINE_HANGING_SIGN, BlockInit.PINE_WALL_HANGING_SIGN, BlockInit.PINE_PLANKS);
		trapdoor(BlockInit.PINE_TRAPDOOR);
		door(BlockInit.PINE_DOOR);
		
		flower(BlockInit.TWISTED_GRASS);

		tall_flower(BlockInit.SPINDLEGRASS);
		tall_flower(BlockInit.ORPHEUM);
		flower(BlockInit.SPINDLESPROUT);
		flower(BlockInit.SMALL_FERN);
		flower(BlockInit.DEAD_SMALL_FERN);
		flower(BlockInit.DRAGON_PEONY);
		flower_pot(BlockInit.POTTED_DRAGON_PEONY);
		flower_pot(BlockInit.POTTED_SPINDLESPROUT);
		flower_pot(BlockInit.POTTED_SMALL_FERN);
		flower_pot(BlockInit.POTTED_DEAD_SMALL_FERN);
		
		flower(BlockInit.PURPLE_GLOWSHROOM);
		flower_pot(BlockInit.POTTED_PURPLE_GLOWSHROOM);
		flower(BlockInit.PINK_GLOWSHROOM);
		flower_pot(BlockInit.POTTED_PINK_GLOWSHROOM);
		flower(BlockInit.RED_GLOWSHROOM);
		flower_pot(BlockInit.POTTED_RED_GLOWSHROOM);
		flower(BlockInit.ORANGE_GLOWSHROOM);
		flower_pot(BlockInit.POTTED_ORANGE_GLOWSHROOM);
		flower(BlockInit.YELLOW_GLOWSHROOM);
		flower_pot(BlockInit.POTTED_YELLOW_GLOWSHROOM);
		flower(BlockInit.GREEN_GLOWSHROOM);
		flower_pot(BlockInit.POTTED_GREEN_GLOWSHROOM);
		flower(BlockInit.TURQUOISE_GLOWSHROOM);
		flower_pot(BlockInit.POTTED_TURQUOISE_GLOWSHROOM);
		flower(BlockInit.BLUE_GLOWSHROOM);
		flower_pot(BlockInit.POTTED_BLUE_GLOWSHROOM);

		// Dynamic
		FruitInit.FRUITS.forEach(this::fruit);
		FluidInit.OBJS.forEach(this::fluid);
	}

	private void cubeRandomRotation(RegistryObject<Block> block) {
		Block b = block.get();
		ModelFile model = cubeAll(b);
		simpleBlock(b, randomRotation(model));
		simpleBlockItem(b, model);
	}

	private void cubeBottomTopRandomRotation(RegistryObject<Block> block) {
		ResourceLocation t = blockTexture(block.get());
		Block b = block.get();
		ModelFile model = models().cubeBottomTop(name(b), extend(t, "_side"), extend(t, "_bottom"), extend(t, "_top"));
		simpleBlock(b, randomRotation(model));
		simpleBlockItem(b, model);
	}

	private ConfiguredModel[] randomRotation(ModelFile model) {
		return ConfiguredModel.builder().modelFile(model).nextModel().rotationY(270).modelFile(model).nextModel()
				.rotationY(180).modelFile(model).nextModel().rotationY(90).modelFile(model).build();
	}

	private void cube(RegistryObject<Block> blockRegistryObject) {
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

	private void flower(RegistryObject<? extends Block> flower) {
		Block f = flower.get();
		ResourceLocation tex = blockTexture(f);
		simpleBlock(f, models().cross(name(f), tex).renderType("cutout"));
		simpleFlatItem(f, tex);
	}

	private void fruit(Fruit fruit) {
		Block f = fruit.block().get();
		ResourceLocation tex = blockTexture(f);
		simpleBlock(f, models().cross(name(f), tex).renderType("cutout"));
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