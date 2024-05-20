package com.machina.api.util.math.sdf.primitive;

import com.machina.api.util.math.MathUtil;

import net.minecraft.util.Mth;

public class SDFCapsule extends SDFPrimitive {
	private float radius;
	private float height;

	public SDFCapsule setRadius(float radius) {
		this.radius = radius;
		return this;
	}

	public SDFCapsule setHeight(float height) {
		this.height = height;
		return this;
	}

	@Override
	public float getDistance(float x, float y, float z) {
		return MathUtil.length(x, y - Mth.clamp(y, 0, height), z) - radius;
	}
}
