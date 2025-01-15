package com.machina.client.screen.menu;

import com.machina.api.client.screen.MachinaMenuScreen;
import com.machina.block.menu.GrinderMenu;
import com.machina.block.tile.machine.GrinderBlockEntity;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GrinderScreen extends MachinaMenuScreen<GrinderBlockEntity, GrinderMenu> {

	public GrinderScreen(GrinderMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	@Override
	protected void renderBg(GuiGraphics gui, float pt, int mx, int my) {
		drawInventory(gui, mx, my);
		drawBackground(gui);
		drawEnergyBar(gui, 110, 0, this.menu.getEnergy() > 0, false);
		drawOverlay(gui);
	}
}
