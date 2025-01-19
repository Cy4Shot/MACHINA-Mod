package com.machina.client.screen.menu;

import com.machina.api.client.screen.MachinaMenuScreen;
import com.machina.block.entity.machine.FurnaceGeneratorBlockEntity;
import com.machina.block.menu.FurnaceGeneratorMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class FurnaceGeneratorScreen extends MachinaMenuScreen<FurnaceGeneratorBlockEntity, FurnaceGeneratorMenu> {

	public FurnaceGeneratorScreen(FurnaceGeneratorMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	@Override
	protected void renderBg(GuiGraphics gui, float pt, int mx, int my) {
		drawInventory(gui, mx, my);
		drawBackground(gui);
		drawDownFacingSlot(gui, 0, mx, my, 107, -40, SpecialSlot.CROSS, "furnace_generator.input");
		drawEnergyBar(gui, 117, 8, true, true, "");
		
		int i = midWidth();
		int j = midHeight();
		blitCommon(gui, i + 81, j - 30, 369, 80, 17, 8);
		blitCommon(gui, i + 81, j - 22, 508, 0, 4, 21);

		blitCommon(gui, i + 134, j - 30, 390, 80, 17, 8);
		blitCommon(gui, i + 147, j - 22, 508, 0, 4, 21);
		
		drawOverlay(gui);
	}
}
