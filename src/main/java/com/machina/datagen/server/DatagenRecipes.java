package com.machina.datagen.server;

import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.ItemInit;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

public class DatagenRecipes extends RecipeProvider implements IConditionBuilder {

	public DatagenRecipes(PackOutput po) {
		super(po);
	}

	@Override
	protected void buildRecipes(@NotNull Consumer<FinishedRecipe> gen) {
		ore(gen, List.of(ItemInit.RAW_ALUMINUM.get(), BlockInit.ALUMINUM_ORE.get()), ItemInit.ALUMINUM_INGOT.get(),
				0.25f, 200, "aluminum");
		ore(gen, List.of(BlockInit.ANTHRACITE.get()), ItemInit.COAL_CHUNK.get(), 0.05f, 40, "anthracite");

		compact(gen, BlockInit.ALUMINUM_BLOCK.get(), ItemInit.ALUMINUM_INGOT.get());
		compact(gen, ItemInit.ALUMINUM_INGOT.get(), ItemInit.ALUMINUM_NUGGET.get());
		compact(gen, Items.COAL, ItemInit.COAL_CHUNK.get());

		stoneFamily(gen, BlockInit.ANTHRACITE.get(), BlockInit.ANTHRACITE_SLAB.get(), BlockInit.ANTHRACITE_STAIRS.get(),
				BlockInit.ANTHRACITE_WALL.get());
		stoneFamily(gen, BlockInit.FELDSPAR.get(), BlockInit.FELDSPAR_SLAB.get(), BlockInit.FELDSPAR_STAIRS.get(),
				BlockInit.FELDSPAR_WALL.get());

		woodFamily(gen, BlockInit.TROPICAL_LOG.get(), BlockInit.TROPICAL_WOOD.get(),
				BlockInit.STRIPPED_TROPICAL_LOG.get(), BlockInit.STRIPPED_TROPICAL_WOOD.get(),
				BlockInit.TROPICAL_PLANKS.get(), BlockInit.TROPICAL_STAIRS.get(), BlockInit.TROPICAL_SLAB.get(),
				BlockInit.TROPICAL_FENCE.get(), BlockInit.TROPICAL_FENCE_GATE.get(), BlockInit.TROPICAL_DOOR.get(),
				BlockInit.TROPICAL_TRAPDOOR.get(), BlockInit.TROPICAL_PRESSURE_PLATE.get(),
				BlockInit.TROPICAL_BUTTON.get(), ItemInit.TROPICAL_SIGN.get(), ItemInit.TROPICAL_HANGING_SIGN.get());
	}

	protected static void woodFamily(Consumer<FinishedRecipe> gen, ItemLike log, ItemLike wood, ItemLike stripped_log,
			ItemLike stripped_wood, ItemLike planks, ItemLike stairs, ItemLike slab, ItemLike fence, ItemLike fencegate,
			ItemLike door, ItemLike trapdoor, ItemLike pressure_plate, ItemLike button, ItemLike sign,
			ItemLike hangingsign) {
		//@formatter:off
		log_and_plank(gen, log, wood, planks);
		log_and_plank(gen, stripped_log, stripped_wood, planks);
		slab(gen, planks, slab);
		stair(gen, planks, stairs);
		door(gen, planks, door);
		trapdoor(gen, planks, trapdoor);
		pressure_plate(gen, planks, pressure_plate);
		button(gen, planks, button);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, fence, 3)
			.pattern("BSB")
			.pattern("BSB")
			.define('B', planks)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(planks), has(planks))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(fence));
		
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, fencegate)
			.pattern("SBS")
			.pattern("SBS")
			.define('B', planks)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(planks), has(planks))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(fencegate));
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, sign, 3)
			.pattern("BBB")
			.pattern("BBB")
			.pattern(" S ")
			.define('B', planks)
			.define('S', Items.STICK)
			.unlockedBy(getHasName(planks), has(planks))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(sign));
		
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, hangingsign, 6)
			.pattern("C C")
			.pattern("BBB")
			.pattern("BBB")
			.define('B', stripped_log)
			.define('C', Items.CHAIN)
			.unlockedBy(getHasName(stripped_log), has(stripped_log))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(hangingsign));
		//@formatter:on
	}

	protected static void log_and_plank(Consumer<FinishedRecipe> gen, ItemLike log, ItemLike wood, ItemLike planks) {
		//@formatter:off
		// LOG -> WOOD
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wood, 3)
			.pattern("BB")
			.pattern("BB")
			.define('B', log)
			.unlockedBy(getHasName(log), has(log))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(wood));
		
		// LOG -> PLANKS
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
			.requires(log)
			.unlockedBy(getHasName(log), has(log))
			.save(gen, Machina.MOD_ID + ":" + getItemName(planks) + "_from_" + getItemName(log));
		
		// WOOD -> PLANKS
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
			.requires(wood)
			.unlockedBy(getHasName(wood), has(wood))
			.save(gen, Machina.MOD_ID + ":" + getItemName(planks) + "_from_" + getItemName(wood));
		//@formatter:on
	}

	protected static void stoneFamily(Consumer<FinishedRecipe> gen, ItemLike base, ItemLike slab, ItemLike stair,
			ItemLike wall) {
		//@formatter:off
		slab(gen, base, slab);
		stair(gen, base, stair);
		
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
			.pattern("BBB")
			.pattern("BBB")
			.define('B', base)
			.unlockedBy(getHasName(base), has(base))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(wall));
		
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, slab, 2)
			.unlockedBy(getHasName(base), has(base))
			.save(gen, Machina.MOD_ID + ":stonecutting_" + getItemName(slab));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, stair)
			.unlockedBy(getHasName(base), has(base))
			.save(gen, Machina.MOD_ID + ":stonecutting_" + getItemName(stair));
		SingleItemRecipeBuilder.stonecutting(Ingredient.of(base), RecipeCategory.BUILDING_BLOCKS, wall)
			.unlockedBy(getHasName(base), has(base))
			.save(gen, Machina.MOD_ID + ":stonecutting_" + getItemName(wall));
		//@formatter:on
	}

	protected static void stair(Consumer<FinishedRecipe> gen, ItemLike base, ItemLike stair) {
		//@formatter:off
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, stair, 4)
			.pattern("B  ")
			.pattern("BB ")
			.pattern("BBB")
			.define('B', base)
			.unlockedBy(getHasName(base), has(base))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(stair));
		//@formatter:on
	}

	protected static void slab(Consumer<FinishedRecipe> gen, ItemLike base, ItemLike slab) {
		//@formatter:off
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
			.pattern("BBB")
			.define('B', base)
			.unlockedBy(getHasName(base), has(base))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(slab));
		//@formatter:on
	}

	protected static void door(Consumer<FinishedRecipe> gen, ItemLike base, ItemLike door) {
		//@formatter:off
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, door, 3)
			.pattern("BB")
			.pattern("BB")
			.pattern("BB")
			.define('B', base)
			.unlockedBy(getHasName(base), has(base))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(door));
		//@formatter:on
	}

	protected static void trapdoor(Consumer<FinishedRecipe> gen, ItemLike base, ItemLike door) {
		//@formatter:off
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, door, 2)
			.pattern("BBB")
			.pattern("BBB")
			.define('B', base)
			.unlockedBy(getHasName(base), has(base))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(door));
		//@formatter:on
	}

	protected static void pressure_plate(Consumer<FinishedRecipe> gen, ItemLike base, ItemLike plate) {
		//@formatter:off
		ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, plate)
			.pattern("BB")
			.define('B', base)
			.unlockedBy(getHasName(base), has(base))
			.showNotification(false)
			.save(gen, Machina.MOD_ID + ":crafting_" + getItemName(plate));
		//@formatter:on
	}

	protected static void button(Consumer<FinishedRecipe> gen, ItemLike base, ItemLike button) {
		//@formatter:off
		ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button, 1)
			.requires(base)
			.unlockedBy(getHasName(base), has(base))
			.save(gen, Machina.MOD_ID + ":" + getItemName(button) + "_from_" + getItemName(base));
		//@formatter:on
	}

	protected static void compact(Consumer<FinishedRecipe> gen, ItemLike big, ItemLike small) {
		//@formatter:off
		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, big)
	        .pattern("SSS")
	        .pattern("SSS")
	        .pattern("SSS")
	        .define('S', small)
	        .unlockedBy(getHasName(small), has(small))
	        .showNotification(false)
	        .save(gen, Machina.MOD_ID + ":" + getItemName(big) + "_from_" + getItemName(small));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, small, 9)
	        .requires(big)
	        .unlockedBy(getHasName(big), has(big))
	        .save(gen, Machina.MOD_ID + ":" + getItemName(small) + "_from_" + getItemName(big));
		//@formatter:on
	}

	protected static void ore(Consumer<FinishedRecipe> gen, List<ItemLike> ing, ItemLike res, float exp, int duration,
			String group) {
		oreSmelting(gen, ing, RecipeCategory.MISC, res, exp, duration, group);
		oreBlasting(gen, ing, RecipeCategory.MISC, res, exp, duration / 2, group);
	}

	protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> gen, List<ItemLike> ing,
			@NotNull RecipeCategory cat, @NotNull ItemLike res, float exp, int pCookingTIme, @NotNull String group) {
		oreCooking(gen, RecipeSerializer.SMELTING_RECIPE, ing, cat, res, exp, pCookingTIme, group, "_from_smelting");
	}

	protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> gen, List<ItemLike> ing,
			@NotNull RecipeCategory cat, @NotNull ItemLike res, float exp, int time, @NotNull String group) {
		oreCooking(gen, RecipeSerializer.BLASTING_RECIPE, ing, cat, res, exp, time, group, "_from_blasting");
	}

	protected static void oreCooking(@NotNull Consumer<FinishedRecipe> gen,
			@NotNull RecipeSerializer<? extends AbstractCookingRecipe> ser, List<ItemLike> ing,
			@NotNull RecipeCategory cat, @NotNull ItemLike res, float exp, int time, @NotNull String group,
			String name) {
		for (ItemLike itemlike : ing) {
			SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), cat, res, exp, time, ser).group(group)
					.unlockedBy(getHasName(itemlike), has(itemlike))
					.save(gen, Machina.MOD_ID + ":" + getItemName(res) + name + "_" + getItemName(itemlike));
		}
	}
}
