package com.machina.world.feature.tree;

import java.util.List;

import org.joml.Vector3f;

import com.machina.api.starchart.planet_biome.PlanetBiomeSettings.PlanetBiomeTree;
import com.machina.api.starchart.planet_biome.TreeMaker;
import com.machina.api.util.math.MathUtil;
import com.machina.api.util.math.OpenSimplex2F;
import com.machina.api.util.math.sdf.SDF;
import com.machina.api.util.math.sdf.SplineUtil;
import com.machina.api.util.math.sdf.operator.SDFDisplacement;
import com.machina.api.util.math.sdf.operator.SDFScale;
import com.machina.api.util.math.sdf.operator.SDFSmoothUnion;
import com.machina.api.util.math.sdf.operator.SDFSubtraction;
import com.machina.api.util.math.sdf.operator.SDFTranslate;
import com.machina.api.util.math.sdf.operator.SDFUnion;
import com.machina.api.util.math.sdf.primitive.SDFSphere;
import com.machina.api.util.math.sdf.primitive.SDFTorus;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;

public class BellMushroomTree implements TreeMaker {

	@Override
	public SDF build(PlanetBiomeTree config, RandomSource random, WorldGenLevel l, BlockPos p) {

		OpenSimplex2F noise = new OpenSimplex2F(random.nextLong());
		float size = MathUtil.randRange(random, 10, 15);

		float r = size * MathUtil.randRange(random, 0.9F, 0.95F) / 3f * 1.15F;
		float r2 = (r + 4) / 2f;

		List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
		SplineUtil.offsetParts(spline, random, 1F, 0.1f, 1F);
		SDF stem = SplineUtil.buildSDF(spline, 2f, 3.5f, config.wood());
		
		SDF sphere = new SDFSphere(r).setBlock(config.leaves());
		SDF sub = new SDFScale(sphere, 5);
		sub = new SDFTranslate(sub, 0, -r * 5, 0);
		sphere = new SDFSubtraction(sphere, sub);
		sphere = new SDFDisplacement(sphere, noise, 0.2f, 1.5f);
		sphere = new SDFTranslate(sphere, 0, r2 + size, 0);

		SDF torus = new SDFTorus(r2 + 1, r2).setBlock(config.leaves());
		torus = new SDFDisplacement(torus, noise, 0.2f, 1.5f);
		torus = new SDFTranslate(torus, 0, size - 2, 0);
		torus = new SDFSmoothUnion(torus, sphere, 4f);

		return new SDFUnion(torus, stem);
	}

	@Override
	public BlockState getLeafAttachment(PlanetBiomeTree config, RandomSource random) {
		return config.wood();
	}
}
