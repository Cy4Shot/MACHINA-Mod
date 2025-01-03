package com.machina.client.screen.menu;

import com.machina.api.client.screen.MachinaMenuScreen;
import com.machina.block.menu.MachineCaseMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MachineCaseScreen extends MachinaMenuScreen<MachineCaseMenu> {

	public MachineCaseScreen(MachineCaseMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
		this.imageWidth = 230;
		this.imageHeight = 219;
	}

	@Override
	protected void renderBg(GuiGraphics gui, float pt, int mx, int my) {
		drawInventory(gui, mx, my);
		drawOverlay(gui);
	}

}
