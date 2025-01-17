package com.machina.api.util.math.sdf.operator;

import java.util.function.Consumer;

import org.joml.Vector3f;

import com.machina.api.util.math.sdf.SDF;

public class SDFCoordModify extends SDFUnary {
	private final Vector3f pos = new Vector3f();
	private final Consumer<Vector3f> function;
	
	public SDFCoordModify(SDF source, Consumer<Vector3f> function) {
		super(source);
		this.function = function;
	}

	@Override
	public float getDistance(float x, float y, float z) {
		pos.set(x, y, z);
		function.accept(pos);
		return this.source.getDistance(pos.x(), pos.y(), pos.z());
	}
}
