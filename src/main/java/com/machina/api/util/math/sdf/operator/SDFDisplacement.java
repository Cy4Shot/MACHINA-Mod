package com.machina.api.util.math.sdf.operator;

import java.util.function.Function;

import org.joml.Vector3f;

import com.machina.api.util.math.sdf.SDF;

public class SDFDisplacement extends SDFUnary {

	private final Vector3f pos = new Vector3f();
	private final Function<Vector3f, Float> displace;
	
	public SDFDisplacement(SDF source, Function<Vector3f, Float> displace) {
		super(source);
		this.displace = displace;
	}

	@Override
	public float getDistance(float x, float y, float z) {
		pos.set(x, y, z);
		return this.source.getDistance(x, y, z) + displace.apply(pos);
	}
}
