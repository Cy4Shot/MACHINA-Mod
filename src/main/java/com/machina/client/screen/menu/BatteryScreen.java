package com.machina.client.screen.menu;

import com.machina.api.client.screen.MachinaMenuScreen;
import com.machina.block.menu.BatteryMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BatteryScreen extends MachinaMenuScreen<BatteryMenu> {

	public BatteryScreen(BatteryMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	@Override
	protected void renderBg(GuiGraphics gui, float pt, int mx, int my) {
		drawInventory(gui, mx, my);
		drawBackground(gui);
		drawEnergyBar(gui, 114, 0, true, true);
		drawDownFacingSlot(gui, 50, -60, SpecialSlot.PLUS);
		drawDownFacingSlot(gui, 161, -60, SpecialSlot.MINUS);
		drawOverlay(gui);
	}
}
