package com.machina.api.client.planet;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import com.machina.api.client.RenderTypes;
import com.machina.api.starchart.obj.Orbit;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;
import team.lodestar.lodestone.systems.rendering.VFXBuilders.WorldVFXBuilder;
import team.lodestar.lodestone.systems.rendering.trail.TrailPoint;

public class CelestialRenderer extends WorldVFXBuilder {

	public WorldVFXBuilder drawCelestial(MultiBufferSource mbs, PoseStack stack, int detail, CelestialRenderInfo info,
			double time, ScreenParticleHolder p_target) {

		stack.pushPose();
		List<TrailPoint> orbit = generateOrbitPoints(info.orbit(), 50).stream().map(TrailPoint::new).toList();
		renderTrail(mbs.getBuffer(RenderTypes.ORBIT.get()), stack, orbit, 1f);
		stack.popPose();

		Vec3 pos = info.getOrbitalCoords(time);
		float rad = (float) info.radius();
		stack.pushPose();
		stack.translate(pos.x, pos.y, pos.z);

		Vec2 sp = asScreenPos(stack, info.width(), info.height());
		info.particle().accept(sp, p_target);

		stack.scale(rad, rad, rad);

		sphere(mbs, RenderTypes.getOrCreateCelestial(info.bg()), stack, 1f, detail);
		// TODO: Based on atm density
		sphere(mbs, RenderTypes.getOrCreateCelestial(info.fg()), stack, 0.5f, detail);

		stack.popPose();
		return this;
	}

	public static Vec2 asScreenPos(PoseStack stack, int w, int h) {
		Vector4f spos = new Vector4f(0, 0, 0, 1);
		Matrix4f stm = new Matrix4f(stack.last().pose());
		Matrix4f mvp = new Matrix4f(RenderSystem.getProjectionMatrix()).mul(RenderSystem.getModelViewMatrix());

		stm.transform(spos);
		mvp.transform(spos);

		Vector4f norm = spos.div(spos.w);
		float x = (1.0f + norm.x) * 0.5f * w;
		float y = (1.0f - norm.y) * 0.5f * h;

		return new Vec2(x, y);
	}

	public static List<Vec3> generateOrbitPoints(Orbit o, int numPoints) {
		List<Vec3> orbitPoints = new ArrayList<>();
		double a = o.a();
		double e = o.e();

		for (int i = 0; i < numPoints; i++) {
			double trueAnomaly = 2 * Math.PI * i / numPoints;
			double radius = a * (1 - e * e) / (1 + e * Math.cos(trueAnomaly));

			double x = radius * Math.cos(trueAnomaly);
			double y = radius * Math.sin(trueAnomaly);

			orbitPoints.add(new Vec3((float) x, 0, (float) y));
		}

		return orbitPoints;
	}

	private WorldVFXBuilder sphere(MultiBufferSource m, RenderType t, PoseStack s, float a, int d) {
		return renderSphere(m.getBuffer(t), s, d, d, a);
	}

	// Thanks rat man :)
	public CelestialRenderer renderSphere(VertexConsumer c, PoseStack stack, int longs, int lats, float a) {
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
		return this;
	}

	public static void v(VertexConsumer c, Matrix4f m, float x, float y, float z, float r, float g, float b, float a,
			float u, float v, int l) {
		c.vertex(m, x, y, z).color(r, g, b, a).uv(u, v).uv2(l).endVertex();
	}
}