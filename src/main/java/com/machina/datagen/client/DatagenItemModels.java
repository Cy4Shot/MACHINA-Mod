package com.machina.datagen.client;

import java.util.LinkedHashMap;

import com.machina.Machina;
import com.machina.api.util.MachinaRL;
import com.machina.registration.init.FamiliesInit;
import com.machina.registration.init.FamiliesInit.OreFamily;
import com.machina.registration.init.FluidInit;
import com.machina.registration.init.FluidInit.FluidObject;
import com.machina.registration.init.FruitInit;
import com.machina.registration.init.FruitInit.Fruit;
import com.machina.registration.init.ItemInit;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DatagenItemModels extends ItemModelProvider {
	private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
	static {
		trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
		trimMaterials.put(TrimMaterials.IRON, 0.2F);
		trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
		trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
		trimMaterials.put(TrimMaterials.COPPER, 0.5F);
		trimMaterials.put(TrimMaterials.GOLD, 0.6F);
		trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
		trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
		trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
		trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
	}

	public DatagenItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, Machina.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		simpleItem(ItemInit.BLUEPRINT);
		
		simpleItem(ItemInit.BASIC_CAPACITOR);
		simpleItem(ItemInit.ADVANCED_CAPACITOR);
		simpleItem(ItemInit.SUPREME_CAPACITOR);

		simpleItem(ItemInit.ALUMINUM_INGOT);
		simpleItem(ItemInit.ALUMINUM_NUGGET);
		simpleItem(ItemInit.COAL_CHUNK);

		simpleItem(ItemInit.TROPICAL_SIGN);
		simpleItem(ItemInit.TROPICAL_HANGING_SIGN);

		simpleItem(ItemInit.DEAD_TROPICAL_SIGN);
		simpleItem(ItemInit.DEAD_TROPICAL_HANGING_SIGN);

		simpleItem(ItemInit.PINE_SIGN);
		simpleItem(ItemInit.PINE_HANGING_SIGN);

		simpleItem(ItemInit.CONIFEROUS_SIGN);
		simpleItem(ItemInit.CONIFEROUS_HANGING_SIGN);

		simpleItem(ItemInit.CYCAD_SIGN);
		simpleItem(ItemInit.CYCAD_HANGING_SIGN);

		// Dynamic
		FruitInit.FRUITS.forEach(this::fruit);
		FluidInit.OBJS.forEach(this::bucket);
		FamiliesInit.ORES.forEach(this::oreFamily);
	}

	private String name(Item item) {
		return BuiltInRegistries.ITEM.getKey(item).getPath();
	}

	private void oreFamily(OreFamily fam) {
		simpleItem(fam.dust());
		fam.getIngot().ifPresent(this::simpleItem);
		fam.getNugget().ifPresent(this::simpleItem);
		fam.plate().ifPresent(this::simpleItem);
		fam.rod().ifPresent(this::simpleItem);
		fam.wire().ifPresent(this::simpleItem);
	}

	protected void bucket(FluidObject obj) {
		DynamicFluidContainerModelBuilder<ItemModelBuilder> builder = withExistingParent(name(obj.fluid().getBucket()),
				new ResourceLocation("forge", "item/bucket")).customLoader(DynamicFluidContainerModelBuilder::begin);
		if (obj.fluid().getFluidType().getDensity() < 0) {
			builder.flipGas(true);
		}
		builder.fluid(obj.fluid());
	}

	@SuppressWarnings("unused")
	private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
		if (itemRegistryObject.get() instanceof ArmorItem armorItem) {
			trimMaterials.forEach((trimMaterial, value) -> {

				float trimValue = value;

				String armorType = switch (armorItem.getEquipmentSlot()) {
				case HEAD -> "helmet";
				case CHEST -> "chestplate";
				case LEGS -> "leggings";
				case FEET -> "boots";
				default -> "";
				};

				String armorItemPath = "item/" + armorItem;
				String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
				String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
				ResourceLocation armorItemResLoc = new MachinaRL(armorItemPath);
				ResourceLocation trimResLoc = new ResourceLocation(trimPath);
				ResourceLocation trimNameResLoc = new MachinaRL(currentTrimName);

				existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

				getBuilder(currentTrimName).parent(new ModelFile.UncheckedModelFile("item/generated"))
						.texture("layer0", armorItemResLoc).texture("layer1", trimResLoc);

				this.withExistingParent(itemRegistryObject.getId().getPath(), mcLoc("item/generated")).override()
						.model(new ModelFile.UncheckedModelFile(trimNameResLoc))
						.predicate(mcLoc("trim_type"), trimValue).end()
						.texture("layer0", new MachinaRL("item/" + itemRegistryObject.getId().getPath()));
			});
		}
	}

	private ItemModelBuilder simpleItem(Item item) {
		String name = name(item);
		return withExistingParent(name, new ResourceLocation("item/generated")).texture("layer0",
				new MachinaRL("item/" + name));
	}

	private ItemModelBuilder simpleItem(RegistryObject<? extends Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new MachinaRL("item/" + item.getId().getPath()));
	}

	public void evenSimplerBlockItem(RegistryObject<? extends Block> block) {
		this.withExistingParent(Machina.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
	}

	public void trapdoorItem(RegistryObject<Block> block) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
				modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
	}

	public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
				.texture("texture", new ResourceLocation(Machina.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
				.texture("texture", new ResourceLocation(Machina.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
		this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
				.texture("wall", new ResourceLocation(Machina.MOD_ID,
						"block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
	}

	private void fruit(Fruit fruit) {
		simpleItem(fruit.item());
	}

	@SuppressWarnings("unused")
	private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/handheld")).texture("layer0",
				new MachinaRL("item/" + item.getId().getPath()));
	}

	@SuppressWarnings("unused")
	private ItemModelBuilder simpleBlockItem(RegistryObject<? extends Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new MachinaRL("item/" + item.getId().getPath()));
	}

	@SuppressWarnings("unused")
	private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<? extends Block> item) {
		return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated")).texture("layer0",
				new MachinaRL("block/" + item.getId().getPath()));
	}
}