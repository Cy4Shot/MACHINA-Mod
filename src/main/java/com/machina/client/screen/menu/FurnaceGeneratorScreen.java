package com.machina.client.screen.menu;

import com.machina.api.client.screen.MachinaMenuScreen;
import com.machina.block.menu.FurnaceGeneratorMenu;
import com.machina.block.tile.machine.FurnaceGeneratorBlockEntity;

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
		drawEnergyBar(gui, 117, 0, true, true);
		drawItemSideConfig(gui, 0, 0, mx, my, 0, SpecialSlot.CROSS);
		drawOverlay(gui);
	}
}
