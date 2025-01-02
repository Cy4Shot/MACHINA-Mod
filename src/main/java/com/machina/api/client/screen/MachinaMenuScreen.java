package com.machina.api.client.screen;

import com.machina.api.util.MachinaRL;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public abstract class MachinaMenuScreen<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

	protected static final ResourceLocation COMMON_UI = new MachinaRL("textures/gui/common_ui.png");
	protected static final ResourceLocation BG_OVERLAY = new MachinaRL("textures/gui/bg_overlay.png");

	protected long aliveTicks = 0;

	public MachinaMenuScreen(T menu, Inventory inv, Component title) {
		super(menu, inv, title);
	}

	public void render(GuiGraphics gui, int mx, int my, float pt) {
		this.renderBackground(gui);
		if (this.aliveTicks > 9 || this.aliveTicks == 5 || this.aliveTicks == 7 || this.aliveTicks == 8)
			super.render(gui, mx, my, pt);
		else
			this.renderBg(gui, pt, mx, my);
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

	protected void blitCommon(GuiGraphics gui, int x, int y, int u, int v, int w, int h) {
		gui.blit(COMMON_UI, x, y, u, v, w, h, 512, 512);
	}
	
	protected void blitOverlay(GuiGraphics gui, int x, int y, int w, int h) {
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1f, 1f, 1f, 0.1f);
		gui.blit(BG_OVERLAY, x, y, 0, 0, w, h, 512, 512);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
	}

	protected void drawInventory(GuiGraphics gui) {
		int i = midWidth();
		int j = midHeight();

		int s1 = (int) (this.aliveTicks * 1.7f);
		int k1 = (int) Math.max(4 - this.aliveTicks, 0);
		int k2 = s1 % 78;
		int k3 = s1 % 156 >= 78 ? k2 : 78 - k2;
		int k4 = (s1 + 39) % 78;
		int k5 = (s1 + 39) % 156 >= 78 ? k4 : 78 - k4;
		int k6 = s1 % 173;
		int k7 = s1 % 346 >= 173 ? k6 : 173 - k6;
		int k8 = 6 - (int) (this.aliveTicks % 6);

		// Backdrop
		blitCommon(gui, i + 3, j + 98, 179, 0, 187, 92);
		blitCommon(gui, i + 7, j + 102, 0, 84 * k1, 179, 84);

		// Active Slot
		if (this.hoveredSlot != null) {
			blitCommon(gui, i + this.hoveredSlot.x - 1, j + this.hoveredSlot.y - 1, 368, 2, 19, 19);
		}

		// Decorators
		blitCommon(gui, i + 193, j + 98 + k3, 366, 0, 2, 14);
		blitCommon(gui, i, j + 98 + k5, 366, 0, 2, 14);
		blitCommon(gui, i + k7, j + 193, 368, 0, 14, 2);
		blitCommon(gui, i + 7, j + 163, 179 + k8, 92, 179, 2);
	}
	
	protected void drawOverlay(GuiGraphics gui) {
		int height = this.height + 4;
		int width = this.width;
		
		int k = (int) (this.aliveTicks / 3 % 4);
		int cw = width / 512;
		int ch = height / 512;
		int tw = cw * 512;
		int th = ch * 512;
		
		for (int i = 0; i < cw; i++) {
            for (int j = 0; j < ch; j++) {
                blitOverlay(gui, i * 512, j * 512 + k, 512, 512);
            }
		}
		
		for (int i = 0; i < cw; i++) {
			blitOverlay(gui, i * 512, th + k, 512, height - th);
		}
		
		for (int j = 0; j < ch; j++) {
			blitOverlay(gui, tw, j * 512 + k, width - tw, 512);
		}
		
		blitOverlay(gui, tw, th + k, width - tw, height - th);
	}
}