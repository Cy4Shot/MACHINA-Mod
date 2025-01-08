package com.machina.api.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.machina.registration.init.RecipeInit.RecipeRegistryObject;
import com.mojang.datafixers.util.Pair;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;

public abstract class MachinaRecipeMaps<C extends Container> {

	private final Map<ResourceLocation, Recipe<C>> recipes = new HashMap<>();

	public abstract boolean isValid(C entity, Recipe<C> recipe);

	protected abstract RecipeRegistryObject<C> getRegistryObject();

	protected abstract void addExtraRecipes(RecipeManager man);

	protected abstract Class<? extends Recipe<C>> getRecipeClass();

	@SuppressWarnings("unchecked")
	public <T extends Recipe<C>> T getRecipe(ResourceLocation id) {
		Class<T> clazz = (Class<T>) getRecipeClass();
		return clazz.cast(recipes.get(id));
	}

	public void refresh(RecipeManager man) {
		recipes.clear();
		for (Recipe<C> r : man.getAllRecipesFor(getRegistryObject().type().get())) {
			recipes.put(r.getId(), r);
		}
		addExtraRecipes(man);
	}

	public void add(Pair<ResourceLocation, Recipe<C>> pair) {
		add(pair.getFirst(), pair.getSecond());
	}

	public void add(ResourceLocation id, Recipe<C> recipe) {
		recipes.put(id, recipe);
	}

	@SuppressWarnings("unchecked")
	public <T extends Recipe<C>> Optional<T> findRecipe(C entity) {
		Class<T> clazz = (Class<T>) getRecipeClass();
		return recipes.values().stream().filter(r -> isValid(entity, r)).findFirst().map(clazz::cast);
	}
}
