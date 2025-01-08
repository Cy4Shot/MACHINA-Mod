package com.machina.api.recipe;

import com.machina.registration.init.RecipeInit;

import net.minecraft.world.item.crafting.RecipeManager;

public class RecipeRefreshManager {

	public static final RecipeRefreshManager INSTANCE = new RecipeRefreshManager();

	private RecipeManager serverRecipeManager;
	private RecipeManager clientRecipeManager;

	public void setServerRecipeManager(RecipeManager manager) {
		this.serverRecipeManager = manager;
	}

	public void setClientRecipeManager(RecipeManager manager) {
		this.clientRecipeManager = manager;
	}

	public void refreshServer() {
		refresh(serverRecipeManager);
	}

	public void refreshClient() {
		refresh(clientRecipeManager);
	}

	private void refresh(RecipeManager manager) {
		if (manager == null)
			return;
		RecipeInit.MAPS.forEach(map -> map.refresh(manager));
	}
}
