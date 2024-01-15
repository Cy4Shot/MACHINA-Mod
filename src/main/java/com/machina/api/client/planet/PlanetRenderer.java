package com.machina.api.client.planet;

import org.joml.Matrix4f;

import com.machina.api.util.MachinaRL;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.util.Mth;
import team.lodestar.lodestone.helpers.RenderHelper;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.registry.client.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.VFXBuilders.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;

// Thanks rat man :)
public class PlanetRenderer extends VFXBuilders.WorldVFXBuilder {

	public static final RenderTypeProvider TEXTURE_ACTUAL_TRIANGLE_ADDITIVE;

	static {
		TEXTURE_ACTUAL_TRIANGLE_ADDITIVE = new RenderTypeProvider(
				texture -> LodestoneRenderTypeRegistry.createGenericRenderType(texture.getNamespace(),
						"texture_actual_triangle_additive", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP,
						VertexFormat.Mode.TRIANGLES, LodestoneShaderRegistry.LODESTONE_TEXTURE.getShard(),
						StateShards.NORMAL_TRANSPARENCY, new TextureStateShard(texture, false, false),
						RenderStateShard.CULL));
	}

	public static void drawCelestial(MultiBufferSource consumer, PoseStack stack, int detail, String tex) {
		PlanetRenderer renderer = (PlanetRenderer) (new PlanetRenderer().setPosColorTexLightmapDefaultFormat());

		stack.pushPose();
		renderer.setAlpha(1.0F).renderSphere(consumer.getBuffer(
				TEXTURE_ACTUAL_TRIANGLE_ADDITIVE.apply(new MachinaRL("textures/gui/starchart/" + tex + ".png"))), stack,
				1.0F, detail, detail);
		stack.popPose();
	}

	@Override
	public WorldVFXBuilder renderSphere(VertexConsumer c, PoseStack stack, float radius, int longs, int lats) {
		Matrix4f last = stack.last().pose();
		float startU = 0.0F;
		float startV = 0.0F;
		float endU = 6.2831855F;
		float endV = 3.1415927F;
		float stepU = (endU - startU) / longs;
		float stepV = (endV - startV) / lats;
		for (int i = 0; i < longs; i++) {
			for (int j = 0; j < lats; j++) {
				float u = i * stepU + startU;
				float v = j * stepV + startV;
				float un = (i + 1 == longs) ? endU : ((i + 1) * stepU + startU);
				float vn = (j + 1 == lats) ? endV : ((j + 1) * stepV + startV);
				float p0x = Mth.cos(u) * Mth.sin(v) * radius;
				float p0y = Mth.cos(v) * radius;
				float p0z = Mth.sin(u) * Mth.sin(v) * radius;
				float p1x = Mth.cos(u) * Mth.sin(vn) * radius;
				float p1y = Mth.cos(vn) * radius;
				float p1z = Mth.sin(u) * Mth.sin(vn) * radius;
				float p2x = Mth.cos(un) * Mth.sin(v) * radius;
				float p2y = Mth.cos(v) * radius;
				float p2z = Mth.sin(un) * Mth.sin(v) * radius;
				float p3x = Mth.cos(un) * Mth.sin(vn) * radius;
				float p3y = Mth.cos(vn) * radius;
				float p3z = Mth.sin(un) * Mth.sin(vn) * radius;
				float textureU = u / endU * radius;
				float textureV = v / endV * radius;
				float textureUN = un / endU * radius;
				float textureVN = vn / endV * radius;
				RenderHelper.vertexPosColorUVLight(c, last, p0x, p0y, p0z, r, g, b, a, textureU, textureV, light);
				RenderHelper.vertexPosColorUVLight(c, last, p2x, p2y, p2z, r, g, b, a, textureUN, textureV, light);
				RenderHelper.vertexPosColorUVLight(c, last, p1x, p1y, p1z, r, g, b, a, textureU, textureVN, light);
				RenderHelper.vertexPosColorUVLight(c, last, p3x, p3y, p3z, r, g, b, a, textureUN, textureVN, light);
				RenderHelper.vertexPosColorUVLight(c, last, p1x, p1y, p1z, r, g, b, a, textureU, textureVN, light);
				RenderHelper.vertexPosColorUVLight(c, last, p2x, p2y, p2z, r, g, b, a, textureUN, textureV, light);
			}
		}
		return this;
	}
}