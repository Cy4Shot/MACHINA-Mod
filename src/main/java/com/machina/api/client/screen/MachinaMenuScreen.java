package com.machina.api.client.screen;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;

import com.machina.Machina;
import com.machina.api.block.menu.MachinaContainerMenu;
import com.machina.api.multiblock.ClientMultiblock;
import com.machina.api.multiblock.MultiblockLoader;
import com.machina.api.util.MachinaRL;
import com.machina.api.util.math.MathUtil;
import com.machina.api.util.math.VecUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;

public abstract class MachinaMenuScreen<T extends MachinaContainerMenu<?>> extends AbstractContainerScreen<T> {

	private static final Minecraft mc = Minecraft.getInstance();

	protected static final ResourceLocation COMMON_UI = new MachinaRL("textures/gui/common_ui.png");
	protected static final ResourceLocation BG_OVERLAY = new MachinaRL("textures/gui/bg_overlay.png");

	private long aliveTicks = 0;
	private Float lsx, lsy = null;
	private float rotX, rotY;

	public MachinaMenuScreen(T menu, Inventory inv, Component title) {
		super(menu, inv, title);

		this.imageWidth = 235;
		this.imageHeight = 100;
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
	protected void renderLabels(GuiGraphics gui, int x, int y) {
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

	protected void drawString(GuiGraphics gui, Component text, int x, int y, int color) {
		gui.drawString(font, text, x, y, color);
	}

	protected void drawStringVertical(GuiGraphics gui, Component text, int x, int y, int color) {
		gui.pose().pushPose();
		gui.pose().translate(x, y, 0);
		gui.pose().mulPose(VecUtil.rotationDegrees(VecUtil.ZP, 90));
		gui.drawString(font, text, 0, 0, color);
		gui.pose().popPose();
	}

	protected void drawInventory(GuiGraphics gui, int mx, int my) {
		int i = midWidth();
		int j = midHeight();
		boolean hovered = this.hoveredSlot != null && this.hoveredSlot.container == mc.player.getInventory();

		int sx, sy;
		if (hovered) {
			sx = i + this.hoveredSlot.x + 2;
			sy = j + this.hoveredSlot.y + 3;
		} else {
			sx = Math.min(i + 196, Math.max(i + 23, mx - 5));
			sy = Math.min(j + 126, Math.max(j + 78, my - 5));
		}

		if (this.lsx == null || this.lsy == null) {
			this.lsx = (float) sx;
			this.lsy = (float) sy;
		} else {
			this.lsx += (sx - this.lsx) / 50f;
			this.lsy += (sy - this.lsy) / 50f;
		}

		int k1 = (int) Math.max(4 - this.aliveTicks, 0);
		int k2 = 6 - lsx.intValue() % 6;

		// Backdrop
		blitCommon(gui, i + 23, j + 78, 179, 0, 187, 92);
		blitCommon(gui, i + 27, j + 82, 0, 84 * k1, 179, 84);

		// Active Slot
		if (hovered) {
			blitCommon(gui, i + this.hoveredSlot.x - 1, j + this.hoveredSlot.y - 1, 368, 2, 19, 19);
		}

		// Decorators
		blitCommon(gui, i + 18, lsy.intValue(), 366, 0, 2, 14);
		blitCommon(gui, lsx.intValue(), j + 173, 368, 0, 14, 2);
		blitCommon(gui, i + 27, j + 143, 179 + k2, 92, 179, 2);
		drawStringVertical(gui, Component.translatable("container.inventory"), i + 220, j + 78, 0x00FEFE);
	}

	protected void drawBackground(GuiGraphics gui) {
		int i = midWidth();
		int j = midHeight();

		blitCommon(gui, i, j - 73, 179, 94, 235, 151);
		drawStringVertical(gui, this.menu.getName(), i + 245, j - 71, 0x00FEFE);
	}

	private void drawBar(GuiGraphics gui, int x, int y, int o, float p) {
		int i = midWidth() + x - 68;
		int j = midHeight() + y - 9;

		blitCommon(gui, i, j, 366, 21, 135, 18);
		blitCommon(gui, i + 3, j + 3, 366, 39 + o * 14, (int) (131 * p), 14);
	}

	protected void drawEnergyBar(GuiGraphics gui, int x, int y) {
		drawBar(gui, x, y, 0, this.menu.getEnergyF());
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

	public void drawMultiblock(GuiGraphics gui, ResourceLocation mbloc, int xPos, int yPos, int s, float pt) {
		int x = midWidth() + xPos;
		int y = midHeight() + yPos;
		ClientMultiblock mb = new ClientMultiblock(MultiblockLoader.INSTANCE.get(mbloc));
		Vec3i size = mb.mb.size;
		int sizeX = size.getX();
		int sizeY = size.getY();
		int sizeZ = size.getZ();
		float maxX = 90;
		float maxY = 90;
		float diag = (float) Math.sqrt(sizeX * sizeX + sizeZ * sizeZ);
		float scaleX = maxX / diag;
		float scaleY = maxY / sizeY;
		float scale = -Math.min(scaleX, scaleY) * s;

		gui.pose().pushPose();
		gui.pose().translate(x, y, 100);
		gui.pose().scale(scale, scale, scale);
		gui.pose().translate(-(float) sizeX / 2, -(float) sizeY / 2, 0);
		Matrix4f rotMat = new Matrix4f();
		rotMat.identity();
		gui.pose().mulPose(VecUtil.rotationDegrees(VecUtil.XP, rotX - 30F));
		rotMat.rotate(VecUtil.rotationDegrees(VecUtil.XP, 30F - rotX));

		float offX = (float) -sizeX / 2;
		float offZ = (float) -sizeZ / 2 + 1;
		gui.pose().translate(-offX, 0, -offZ);
		gui.pose().mulPose(VecUtil.rotationDegrees(VecUtil.YP, 45F - rotY));
		rotMat.rotate(VecUtil.rotationDegrees(VecUtil.YP, rotY - 45F));
		gui.pose().translate(offX, 0, offZ);

		renderElements(gui.pose(), mb, size, pt, pos -> false, rotX < 30F);

		gui.pose().popPose();
	}

	private static BufferSource mbBuffers = null;

	private static void renderElements(PoseStack ms, ClientMultiblock mb, Vec3i dest, float par,
			Predicate<BlockPos> transparency, boolean flip) {
		if (mbBuffers == null) {
			mbBuffers = initBuffers(mc.renderBuffers().bufferSource(), 0.2f);
		}

		BufferSource buffers = mc.renderBuffers().bufferSource();

		ms.pushPose();
		RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
		ms.translate(0, 0, -1);

		doWorldRenderPass(ms, mbBuffers, buffers, mb, dest, transparency, flip);
		mbBuffers.endBatch();
		buffers.endBatch();

		ms.popPose();
	}

	private static void doWorldRenderPass(PoseStack ms, @Nonnull BufferSource tpBuffers,
			@Nonnull BufferSource nmBuffers, ClientMultiblock mb, Vec3i dest, Predicate<BlockPos> transparency,
			boolean flip) {
		boolean last = false;
		for (int y = 0; y < dest.getY(); y++) {
			for (int x = 0; x < dest.getX(); x++) {
				for (int z = 0; z < dest.getZ(); z++) {
					BlockPos pos = new BlockPos(x, flip ? y : dest.getY() - y - 1, z);
					boolean tp = !transparency.test(pos);
					if (last != tp) {
						(last ? nmBuffers : tpBuffers).endBatch();
					}
					mb = mb.restrict(has -> tp ? !transparency.test(has) : transparency.test(has));
					BlockState bs = mb.getBlockState(pos);

					ms.pushPose();
					ms.translate(pos.getX(), pos.getY(), pos.getZ());
					for (RenderType layer : RenderType.chunkBufferLayers()) {
						VertexConsumer buffer = (tp ? nmBuffers : tpBuffers).getBuffer(layer);
						Vec3 vector3d = bs.getOffset(mb, pos);
						ms.translate(vector3d.x, vector3d.y, vector3d.z);
						BakedModel model = mc.getBlockRenderer().getBlockModel(bs);
						ModelData modelData = model.getModelData(mb, pos, bs, ModelData.EMPTY);
						mc.getBlockRenderer().getModelRenderer().renderModel(ms.last(), buffer, bs, model, pos.getX(),
								pos.getY(), pos.getZ(), 255, OverlayTexture.NO_OVERLAY, modelData, layer);
					}
					ms.popPose();
					last = tp;
				}
			}
		}
	}

	private static BufferSource initBuffers(BufferSource original, float alpha) {
		Map<RenderType, BufferBuilder> remapped = new Object2ObjectLinkedOpenHashMap<>();
		for (Map.Entry<RenderType, BufferBuilder> e : original.fixedBuffers.entrySet()) {
			remapped.put(MultiblockRenderType.remap(e.getKey(), alpha), e.getValue());
		}
		return new MultiblockBuffers(original.builder, remapped, alpha);
	}

	private static class MultiblockBuffers extends BufferSource {

		private final float alpha;

		protected MultiblockBuffers(BufferBuilder fallback, Map<RenderType, BufferBuilder> layerBuffers, float alpha) {
			super(fallback, layerBuffers);
			this.alpha = alpha;
		}

		@Override
		public VertexConsumer getBuffer(RenderType type) {
			return super.getBuffer(MultiblockRenderType.remap(type, alpha));
		}
	}

	private static class MultiblockRenderType extends RenderType {
		private static Map<RenderType, RenderType> remappedTypes = new IdentityHashMap<>();

		private MultiblockRenderType(RenderType original, float alpha) {
			super(String.format("%s_%s_multiblock", original.toString(), Machina.MOD_ID), original.format(),
					original.mode(), original.bufferSize(), original.affectsCrumbling(), true, () -> {
						original.setupRenderState();

						RenderSystem.disableDepthTest();
						RenderSystem.enableBlend();
						RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA,
								GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
						RenderSystem.setShaderColor(1, 1, 1, alpha);
					}, () -> {
						RenderSystem.setShaderColor(1, 1, 1, 1);
						RenderSystem.defaultBlendFunc();
						RenderSystem.disableBlend();
						RenderSystem.enableDepthTest();

						original.clearRenderState();
					});
		}

		@Override
		public boolean equals(@Nullable Object other) {
			return this == other;
		}

		@Override
		public int hashCode() {
			return System.identityHashCode(this);
		}

		public static RenderType remap(RenderType in, float alpha) {
			if (in instanceof MultiblockRenderType) {
				return in;
			} else {
				return remappedTypes.computeIfAbsent(in, a -> new MultiblockRenderType(a, alpha));
			}
		}
	}

	@Override
	public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {

		// Rotate - Right Click
		if (pButton == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			this.rotX -= (float) pDragY / (float) height * 80f;
			this.rotY -= (float) pDragX / (float) width * 180f;
			this.rotX = MathUtil.clamp(this.rotX, 0f, 60f);
		}

		return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
	}

}
