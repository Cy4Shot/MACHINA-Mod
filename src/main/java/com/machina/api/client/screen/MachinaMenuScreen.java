package com.machina.api.client.screen;

import com.machina.api.util.MachinaRL;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class MachinaMenuScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

	protected static final ResourceLocation COMMON_UI = new MachinaRL("textures/gui/common_ui.png");

	protected long aliveTicks = 0;

	public MachinaMenuScreen(T menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	public void render(GuiGraphics gui, int mx, int my, float pt) {
		this.renderBackground(gui);
		super.render(gui, mx, my, pt);
		this.renderTooltip(gui, mx, my);
	}

	@Override
	protected void containerTick() {
		super.containerTick();
		this.aliveTicks++;
	}

	protected int midWidth() {
		return (this.width - this.imageWidth) / 2;
	}

	protected int midHeight() {
		return (this.height - this.imageHeight) / 2;
	}
	
	protected void drawInventory(GuiGraphics gui) {
		int i = midWidth();
		int j = midHeight();

		long k = Math.max(4 - this.aliveTicks, 0);
		gui.blit(COMMON_UI, i + 3, j + 98, 179, 0, 187, 92, 512, 512);
		gui.blit(COMMON_UI, i + 7, j + 102, 0, 84 * k, 179, 84, 512, 512);
	}

}
