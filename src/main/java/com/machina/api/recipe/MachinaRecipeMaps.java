package com.machina.api.recipe;

import java.util.ArrayList;
import java.util.List;

import com.machina.registration.init.RecipeInit.RecipeRegistryObject;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

public abstract class MachinaRecipeMaps<C extends Container> {

	private final List<Recipe<C>> recipes = new ArrayList<>();

	public abstract boolean isValid(C entity, Recipe<C> recipe);

	protected abstract RecipeRegistryObject<C> getRegistryObject();

	protected abstract void addExtraRecipes();

	public void refresh(RecipeManager man) {
		recipes.clear();
		recipes.addAll(man.getAllRecipesFor(getRegistryObject().type().get()));
		addExtraRecipes();
	}

	public void addRecipe(Recipe<C> recipe) {
		recipes.add(recipe);
	}
}
