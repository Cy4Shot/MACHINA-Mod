package com.machina.recipe.maps;

import com.machina.api.recipe.MachinaRecipeMaps;
import com.machina.block.tile.machine.GrinderBlockEntity;
import com.machina.registration.init.RecipeInit;
import com.machina.registration.init.RecipeInit.RecipeRegistryObject;

import net.minecraft.world.item.crafting.Recipe;

public class GrinderRecipeMaps extends MachinaRecipeMaps<GrinderBlockEntity> {

	public static final GrinderRecipeMaps INSTANCE = new GrinderRecipeMaps();

	@Override
	public boolean isValid(GrinderBlockEntity entity, Recipe<GrinderBlockEntity> recipe) {
		return true;
	}

	@Override
	protected RecipeRegistryObject<GrinderBlockEntity> getRegistryObject() {
		return RecipeInit.GRINDER;
	}

	@Override
	protected void addExtraRecipes() {
	}
}