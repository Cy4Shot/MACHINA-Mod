package com.machina.api.util.math.sdf.operator;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import com.machina.api.util.math.VecUtil;

public class SDFRotation extends SDFUnary {
	private final Vector3f pos = new Vector3f();
	private Quaternionf rotation;

	public SDFRotation setRotation(Vector3f axis, float rotationAngle) {
		rotation = VecUtil.rotationDegrees(axis, rotationAngle);
		return this;
	}

	@Override
	public float getDistance(float x, float y, float z) {
		pos.set(x, y, z);
		pos.rotate(rotation);
		return source.getDistance(pos.x(), pos.y(), pos.z());
	}
}
