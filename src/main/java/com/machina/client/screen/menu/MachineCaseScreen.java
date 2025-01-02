package com.machina.client.screen.menu;

import com.machina.block.menu.MachineCaseMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class MachineCaseScreen extends AbstractContainerScreen<MachineCaseMenu> {

	public MachineCaseScreen(MachineCaseMenu menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	@Override
	protected void renderBg(GuiGraphics gui, float pT, int mX, int mY) {

	}

}
