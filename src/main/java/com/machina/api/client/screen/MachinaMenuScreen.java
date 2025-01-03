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

	private Float lsx, lsy = null;

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

	protected void drawInventory(GuiGraphics gui, int mx, int my) {
		int i = midWidth();
		int j = midHeight();

		int k1 = (int) Math.max(4 - this.aliveTicks, 0);
		int k2 = 6 - (int) (this.aliveTicks % 6);

		int sx, sy;
		if (this.hoveredSlot != null) {
			sx = i + this.hoveredSlot.x + 2;
			sy = j + this.hoveredSlot.y + 3;
		} else {
			sx = Math.min(i + 176, Math.max(i + 3, mx - 5));
			sy = Math.min(j + 176, Math.max(j + 98, my - 5));
		}
		
		if (this.lsx == null || this.lsy == null) {
			this.lsx = (float) sx;
			this.lsy = (float) sy;
		} else {
			this.lsx += (sx - this.lsx) / 50f;
			this.lsy += (sy - this.lsy) / 50f;
		}

		// Backdrop
		blitCommon(gui, i + 3, j + 98, 179, 0, 187, 92);
		blitCommon(gui, i + 7, j + 102, 0, 84 * k1, 179, 84);

		// Active Slot
		if (this.hoveredSlot != null) {
			blitCommon(gui, i + this.hoveredSlot.x - 1, j + this.hoveredSlot.y - 1, 368, 2, 19, 19);
		}

		// Decorators
//		blitCommon(gui, i + 193, lsy.intValue(), 366, 0, 2, 14);
		blitCommon(gui, i - 2, lsy.intValue(), 366, 0, 2, 14);
		blitCommon(gui, lsx.intValue(), j + 193, 368, 0, 14, 2);

		blitCommon(gui, i + 7, j + 163, 179 + k2, 92, 179, 2);
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
