package com.machina.client.screen.menu;

import com.machina.api.client.screen.MachinaMenuScreen;
import com.machina.block.menu.BatteryMenu;
import com.machina.block.tile.machine.BatteryBlockEntity;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class BatteryScreen extends MachinaMenuScreen<BatteryBlockEntity, BatteryMenu> {

	public BatteryScreen(BatteryMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	@Override
	protected void renderBg(GuiGraphics gui, float pt, int mx, int my) {
		drawInventory(gui, mx, my);
		drawBackground(gui);
		drawEnergyBar(gui, 117, 0, true, true);
		drawUpFacingSlot(gui, 1, mx, my, 20, 30, SpecialSlot.PLUS, "battery.input");
		drawUpFacingSlot(gui, 2, mx, my, 197, 30, SpecialSlot.MINUS, "battery.output");
		drawDownFacingSlot(gui, 0, mx, my, 106, -60, SpecialSlot.BOLT, "battery.capacitor");

		drawSideConfig(gui, 0, 0, mx, my, "energy", SpecialSlot.BOLT);

		int i = midWidth();
		int j = midHeight();
		blitCommon(gui, i + 113, j - 40, 508, 0, 4, 23);
		blitCommon(gui, i + 113, j - 17, 508, 0, 4, 8);

		blitCommon(gui, i + 27, j + 7, 508, 0, 4, 21);
		blitCommon(gui, i + 27, j - 1, 369, 80, 17, 8);

		blitCommon(gui, i + 204, j + 7, 508, 0, 4, 21);
		blitCommon(gui, i + 191, j - 1, 390, 80, 17, 8);

		drawOverlay(gui);
	}
}
