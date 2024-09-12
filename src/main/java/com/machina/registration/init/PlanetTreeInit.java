package com.machina.registration.init;

import java.util.Arrays;
import java.util.List;

import org.joml.Vector3f;

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
import com.machina.api.util.math.sdf.post.SDFChanceFilter;
import com.machina.api.util.math.sdf.post.SDFSelective;
import com.machina.api.util.math.sdf.primitive.SDFCappedCone;
import com.machina.api.util.math.sdf.primitive.SDFSphere;
import com.machina.world.feature.PlanetTreeFeature.PlanetTreeFeatureConfig;
import com.machina.world.feature.PlanetTreeFeature.TreeMaker;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PlanetTreeInit {

	public static final DeferredRegister<TreeMaker> TREES = RegistryInit.TREES;

	public static final RegistryObject<TreeMaker> RADIAL_BAOBAB = TREES.register("radial_baobab",
			() -> new TreeMaker() {
				private List<Vector3f> BRANCH = Arrays.asList(new Vector3f(0, 0, 0), new Vector3f(0.1F, 0.3F, 0),
						new Vector3f(0.4F, 0.6F, 0), new Vector3f(0.8F, 0.8F, 0), new Vector3f(1, 1, 0));
				private List<Vector3f> SIDE1 = Arrays.asList(new Vector3f(0.4F, 0.6F, 0), new Vector3f(0.8F, 0.8F, 0),
						new Vector3f(1, 1, 0));
				private List<Vector3f> SIDE2 = SplineUtil.copySpline(SIDE1);

				@Override
				public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
					float size = MathUtil.randRange(random, 6, 12);
					List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
					SplineUtil.offsetParts(spline, random, 1F, 0, 1F);

					OpenSimplex2F noise = new OpenSimplex2F(random.nextLong());
					float radius = size * MathUtil.randRange(random, 0.9F, 0.95F);

					SDF stem = SplineUtil.buildSDF(spline, 2.3F, 1.2F, config.tree().wood());

					int count = (int) radius;
					int offset = (int) (BRANCH.get(BRANCH.size() - 1).y() * radius);
					for (int i = 0; i < count; i++) {
						float angle = (float) i / (float) count * Mth.PI * 2;
						float scale = radius * MathUtil.randRange(random, 0.85F, 1.15F);

						List<Vector3f> branch = SplineUtil.copySpline(BRANCH);
						SplineUtil.rotateSpline(branch, angle);
						SplineUtil.scale(branch, scale);
						SDF b1 = SplineUtil.buildSDF(branch, 1, 1, config.tree().wood());
						stem = new SDFUnion(stem, b1);

						branch = SplineUtil.copySpline(SIDE1);
						SplineUtil.rotateSpline(branch, angle);
						SplineUtil.scale(branch, scale);
						SDF b2 = SplineUtil.buildSDF(branch, 1, 1, config.tree().wood());
						stem = new SDFUnion(stem, b2);

						branch = SplineUtil.copySpline(SIDE2);
						SplineUtil.rotateSpline(branch, angle);
						SplineUtil.scale(branch, scale);
						SDF b3 = SplineUtil.buildSDF(branch, 1, 1, config.tree().wood());
						stem = new SDFUnion(stem, b3);
					}

					SDF sphere = new SDFSphere(radius * 1.15F + 2).setBlock(config.tree().leaves());
					SDF sub = new SDFScale(sphere, 5);
					sub = new SDFTranslate(sub, 0, -(radius * 1.15F + 2) * 5, 0);
					sphere = new SDFSubtraction(sphere, sub);
					sphere = new SDFScale3D(sphere, 1, 0.5f, 1);
					sphere = new SDFDisplacement(sphere, noise, 0.2f, 1.5f);
					SDF leaves = new SDFDisplacement(sphere, random, 3);
					leaves = new SDFTranslate(leaves, 0, offset, 0);
					return new SDFUnion(stem, leaves);

				}
			});

	public static final RegistryObject<TreeMaker> ARCH = TREES.register("arch", () -> new TreeMaker() {
		@Override
		public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
			float maxdist = 16;
			float mindist = 10;
			float thickness = MathUtil.randRange(random, 1.6f, 2.0f);
			int xoff = (int) MathUtil.randRangeSigned(random, mindist, maxdist);
			int zoff = (int) MathUtil.randRangeSigned(random, mindist, maxdist);
			int yoff = l.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, p.getX() + xoff, p.getZ() + zoff) - p.getY();
			if (Math.abs(yoff) > 10)
				return null;
			float height = MathUtil.randRange(random, 10, 25);

			List<Vector3f> spline = SplineUtil.makeArch(0, 0, 0, xoff, yoff, zoff, height, 10);
			SplineUtil.offsetParts(spline, random, 0.5F, 0.5f, 0.5F);
			SDF stem = SplineUtil.buildSDF(spline, thickness, thickness, config.tree().wood());
			SDF leaves = SplineUtil.buildSDF(spline, thickness + 0.3f, thickness + 0.3f, config.tree().leaves());
			leaves = new SDFSubtraction(leaves, stem);
			SDF res = new SDFUnion(leaves, stem);
			res.addPostProcess(new SDFSelective(new SDFChanceFilter(random, 0.4f), config.tree().leaves().getBlock()));
			return res;
		}
	});
	public static final RegistryObject<TreeMaker> FIR = TREES.register("fir", () -> new TreeMaker() {
		@Override
		public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
			int segments = 10 + random.nextInt(5);
			float size = MathUtil.randRange(random, 40, 70);
			List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, segments);
			SplineUtil.offsetParts(spline, random, 1F, 0.3f, 1F);
			SDF stem = SplineUtil.buildSDF(spline, 2.0f, 0.8f, config.tree().wood());

			SDF discs = null;

			float bottomrad = MathUtil.randRange(random, 5f, 8f);
			float toprad = MathUtil.randRange(random, 0f, 2f);
			float sep = size / (segments + 1);
			float height = sep;
			for (int i = 0; i < segments; i++) {
				height += sep;
				Vector3f seg = spline.get(i);
				float rad = ((float) i / (float) segments) * (toprad - bottomrad) + bottomrad;
				SDF disc = new SDFSphere(rad).setBlock(config.tree().leaves());
				disc = new SDFScale3D(disc, 1, 1.5f / rad, 1);
				disc = new SDFDisplacement(disc, random, 5f);
				disc = new SDFTranslate(disc, seg.x, height, seg.z);
				disc = new SDFSubtraction(disc, stem);
				if (i == 0) {
					discs = disc;
				} else {
					discs = new SDFUnion(disc, discs);
				}
			}

			return new SDFUnion(discs, stem);
		}
	});

	public static final RegistryObject<TreeMaker> CONE = TREES.register("cone", () -> new TreeMaker() {
		public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
			float size = MathUtil.randRange(random, 8, 15);
			List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
			SplineUtil.offsetParts(spline, random, 0.6F, 0.1f, 0.6F);
			SDF stem = SplineUtil.buildSDF(spline, 1.2f, 0.9f, config.tree().wood());

			float radius = MathUtil.randRange(random, 1f, 3f);
			float width = MathUtil.randRange(random, 1f, 2f);
			float height = MathUtil.randRange(random, 2f, 6f);
			SDF leaves = new SDFCappedCone(radius, radius + width, height).setBlock(config.tree().leaves());
			leaves = new SDFTranslate(leaves, 0, size + height / 2 - 1, 0);
			leaves = new SDFDisplacement(leaves, random, 4f);

			return new SDFUnion(stem, leaves);

		};
	});

	public static final RegistryObject<TreeMaker> LOLLIPOP = TREES.register("lollipop", () -> new TreeMaker() {
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
		};
	});

}
