package com.machina.api.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeType;

public class MachinaRecipeType<R extends Container> implements RecipeType<MachinaRecipe<R>> {
	private final ResourceLocation name;
	private final short flags;

	public MachinaRecipeType(ResourceLocation name, short flags) {
		this.name = name;
		this.flags = flags;
	}

	public short getFlags() {
		return flags;
	}

	@Override
	public String toString() {
		return name.toString();
	}
}
