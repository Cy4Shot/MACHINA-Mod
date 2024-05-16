package com.machina.api.util.math.sdf.primitive;

import com.machina.api.util.math.MathUtil;

public class SDFSphere extends SDFPrimitive {
	private float radius;

	public SDFSphere setRadius(float radius) {
		this.radius = radius;
		return this;
	}

	@Override
	public float getDistance(float x, float y, float z) {
		return MathUtil.length(x, y, z) - radius;
	}
}
