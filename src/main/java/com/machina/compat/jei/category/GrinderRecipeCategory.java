package com.machina.compat.jei.category;

import com.machina.block.entity.machine.GrinderBlockEntity;
import com.machina.compat.jei.base.MachinaRecipeCategory;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.RecipeInit;

import mezz.jei.api.helpers.IGuiHelper;

public class GrinderRecipeCategory extends MachinaRecipeCategory<GrinderBlockEntity> {

	public GrinderRecipeCategory(IGuiHelper gui) {
		super(gui, RecipeInit.GRINDER, BlockInit.GRINDER);
	}
}
