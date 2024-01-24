package com.machina.api.client.planet;

import java.util.function.Function;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.machina.api.client.RenderTypes;
import com.machina.api.util.MachinaRL;
import com.machina.client.particle.GUIParticles;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

public class CelestialRenderer {

	private static final float r = 1, g = 1, b = 1;
	private static final int light = 15728880;

	public static void drawCelestial(MultiBufferSource mbs, PoseStack stack, int detail, CelestialRenderInfo info,
			double time, ScreenParticleHolder p_target, Matrix4f modelView) {
		// TODO: .... absolutely not. Get rid of these silly constants.
		Vec3 pos = info.getOrbitalCoords(time).multiply(0.00000000001D, 0, 0.00000000001D);
		float rad = (float) (info.radius() / 1000000000D);

		stack.pushPose();
		stack.scale(rad, rad, rad);
		stack.translate(pos.x, pos.y, pos.z);
		
		Matrix4f proj = stack.last().pose();
		Vector4f view = modelView.transform(new Vector4f(0, 0, 0, 1));
		Vector4f cast = proj.transform(view);
		Vector4f norm = cast.div(cast.w);
		float screenX = (norm.x + 1.0f) * 0.5f * info.width();
		float screenY = (1.0f - norm.y) * 0.5f * info.height();
		
		GUIParticles.planetGlow(screenX, screenY, p_target);

		sphere(mbs, RenderTypes.CELESTIAL, info.bg(), stack, 1f, detail);
		// TODO: Based on atm density
		sphere(mbs, RenderTypes.CELESTIAL, info.fg(), stack, 0.5f, detail);

		stack.popPose();
	}

	private static void sphere(MultiBufferSource m, Function<ResourceLocation, RenderType> p, String t, PoseStack s,
			float a, int d) {
		renderSphere(m.getBuffer(p.apply(new MachinaRL("textures/gui/starchart/" + t + ".png"))), s, d, d, a);
	}

	// Thanks rat man :)
	public static void renderSphere(VertexConsumer c, PoseStack stack, int longs, int lats, float a) {
		Matrix4f last = stack.last().pose();
		float startU = 0.0F;
		float startV = 0.0F;
		float radius = 1.0F;
		float endU = Mth.TWO_PI;
		float endV = Mth.PI;
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
				v(c, last, p0x, p0y, p0z, r, g, b, a, textureU, textureV, light);
				v(c, last, p2x, p2y, p2z, r, g, b, a, textureUN, textureV, light);
				v(c, last, p1x, p1y, p1z, r, g, b, a, textureU, textureVN, light);
				v(c, last, p3x, p3y, p3z, r, g, b, a, textureUN, textureVN, light);
				v(c, last, p1x, p1y, p1z, r, g, b, a, textureU, textureVN, light);
				v(c, last, p2x, p2y, p2z, r, g, b, a, textureUN, textureV, light);
			}
		}
	}

	public static void v(VertexConsumer c, Matrix4f m, float x, float y, float z, float r, float g, float b, float a,
			float u, float v, int l) {
		c.vertex(m, x, y, z).color(r, g, b, a).uv(u, v).uv2(l).endVertex();
	}
}