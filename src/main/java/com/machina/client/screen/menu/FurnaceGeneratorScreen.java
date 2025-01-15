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
		this.imageWidth = 230;
		this.imageHeight = 219;
	}

	@Override
	protected void renderBg(GuiGraphics gui, float pt, int mx, int my) {
		drawInventory(gui, mx, my);
		drawOverlay(gui);
//		gui.drawString(font, Component.literal(String.valueOf(menu.getBlockEntity().isLit())), 0, 20, 0xFFFFFF);
		gui.drawString(font, Component.literal(String.valueOf(menu.getEnergy())), 0, 0, 0xFFFFFF);
	}
}
