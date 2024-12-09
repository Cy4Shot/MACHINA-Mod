package com.machina.world.feature.tree;

import java.util.Arrays;
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
import com.machina.api.util.math.sdf.operator.SDFScale3D;
import com.machina.api.util.math.sdf.operator.SDFSubtraction;
import com.machina.api.util.math.sdf.operator.SDFTranslate;
import com.machina.api.util.math.sdf.operator.SDFUnion;
import com.machina.api.util.math.sdf.primitive.SDFSphere;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;

public class RandomRadialBaobabTree implements TreeMaker {
	private List<Vector3f> BRANCH = Arrays.asList(new Vector3f(0, 0, 0), new Vector3f(0.1F, 0.3F, 0),
			new Vector3f(0.4F, 0.6F, 0), new Vector3f(0.8F, 0.8F, 0), new Vector3f(1, 1, 0));

	@Override
	public SDF build(PlanetBiomeTree config, RandomSource random, WorldGenLevel l, BlockPos p) {
		float size = MathUtil.randRange(random, 6, 12);
		List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
		SplineUtil.offsetParts(spline, random, 1F, 0, 1F);

		OpenSimplex2F noise = new OpenSimplex2F(random.nextLong());
		float radius = size * MathUtil.randRange(random, 0.9F, 0.95F);

		SDF stem = SplineUtil.buildSDF(spline, 2.3F, 1.2F, config.wood());

		int count = (int) radius;
		int offset = (int) (BRANCH.get(BRANCH.size() - 1).y() * radius);
		for (int i = 0; i < count; i++) {
			if (random.nextInt(3) != 0)
				continue;

			float angle = (float) i / (float) count * Mth.PI * 2;
			float scale = radius * MathUtil.randRange(random, 0.85F, 1.15F);

			List<Vector3f> branch = SplineUtil.copySpline(BRANCH);
			SplineUtil.rotateSpline(branch, angle);
			SplineUtil.scale(branch, scale);
			SDF b1 = SplineUtil.buildSDF(branch, 1, 1, config.wood());
			stem = new SDFUnion(stem, b1);
		}

		if (config.leaves().isAir())
			return stem;

		SDF sphere = new SDFSphere(radius * 1.15F + 2).setBlock(config.leaves());
		SDF sub = new SDFScale(sphere, 5);
		sub = new SDFTranslate(sub, 0, -(radius * 1.15F + 2) * 5, 0);
		sphere = new SDFSubtraction(sphere, sub);
		sphere = new SDFScale3D(sphere, 1, 0.5f, 1);
		sphere = new SDFDisplacement(sphere, noise, 0.2f, 1.5f);
		SDF leaves = new SDFDisplacement(sphere, random, 3);
		leaves = new SDFTranslate(leaves, 0, offset, 0);
		return new SDFUnion(stem, leaves);
	}

	@Override
	public BlockState getLeafAttachment(PlanetBiomeTree config, RandomSource random) {
		return config.leaves();
	}
}
