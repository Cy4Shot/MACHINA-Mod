package com.machina.api.util.math.sdf.operator;

import com.machina.api.util.math.MathUtil;
import com.machina.api.util.math.sdf.SDF;

public class SDFSubtraction extends SDFBinary {
	public SDFSubtraction(SDF sourceA, SDF sourceB) {
		super(sourceA, sourceB);
	}

	@Override
	public float getDistance(float x, float y, float z) {
		float a = this.sourceA.getDistance(x, y, z);
		float b = this.sourceB.getDistance(x, y, z);
		this.selectValue(a, b);
		return MathUtil.max(a, -b);
	}
}
