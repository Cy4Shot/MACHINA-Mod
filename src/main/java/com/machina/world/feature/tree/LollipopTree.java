package com.machina.world.feature.tree;

import java.util.List;

import org.joml.Vector3f;

import com.machina.api.starchart.planet_biome.TreeMaker;
import com.machina.api.util.math.MathUtil;
import com.machina.api.util.math.sdf.SDF;
import com.machina.api.util.math.sdf.SplineUtil;
import com.machina.api.util.math.sdf.operator.SDFDisplacement;
import com.machina.api.util.math.sdf.operator.SDFTranslate;
import com.machina.api.util.math.sdf.operator.SDFUnion;
import com.machina.api.util.math.sdf.primitive.SDFSphere;
import com.machina.world.feature.PlanetTreeFeature.PlanetTreeFeatureConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;

public class LollipopTree implements TreeMaker {

	@Override
	public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
		float size = MathUtil.randRange(random, 15, 35);
		List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
		SplineUtil.offsetParts(spline, random, 1.5F, 0.3f, 1.5F);
		SDF stem = SplineUtil.buildSDF(spline, 1.8f, 1.3f, config.tree().wood());
		float rad = MathUtil.randRange(random, 5, 10);
		SDF leaves = new SDFSphere(rad).setBlock(config.tree().leaves());
		leaves = new SDFTranslate(leaves, 0, size, 0);
		leaves = new SDFDisplacement(leaves, random, 5f);
		return new SDFUnion(stem, leaves);
	}
}
