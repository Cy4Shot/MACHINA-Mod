package com.machina.world.feature;

import java.util.Arrays;
import java.util.List;

import org.joml.Vector3f;

import com.machina.api.starchart.PlanetType.Tree;
import com.machina.api.starchart.PlanetType.VegetationRules;
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
import com.machina.api.util.math.sdf.primitive.SDFSphere;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class PlanetTreeFeature extends Feature<PlanetTreeFeature.PlanetTreeFeatureConfig> {

	@FunctionalInterface
	public static interface TreeMaker {
		SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p);
	}

	public static enum TreeType implements StringRepresentable {
		RADIAL_BAOBAB(new TreeMaker() {
			private static List<Vector3f> BRANCH = Arrays.asList(new Vector3f(0, 0, 0), new Vector3f(0.1F, 0.3F, 0),
					new Vector3f(0.4F, 0.6F, 0), new Vector3f(0.8F, 0.8F, 0), new Vector3f(1, 1, 0));
			private static List<Vector3f> SIDE1 = Arrays.asList(new Vector3f(0.4F, 0.6F, 0),
					new Vector3f(0.8F, 0.8F, 0), new Vector3f(1, 1, 0));
			private static List<Vector3f> SIDE2 = SplineUtil.copySpline(SIDE1);

			private SDF leavesBall(PlanetTreeFeatureConfig config, float radius, RandomSource random,
					OpenSimplex2F noise) {
				SDF sphere = new SDFSphere(radius).setBlock(config.tree().leaves());
				SDF sub = new SDFScale(sphere, 5);
				sub = new SDFTranslate(sub, 0, -radius * 5, 0);
				sphere = new SDFSubtraction(sphere, sub);
				sphere = new SDFScale3D(sphere, 1, 0.5f, 1);
				sphere = new SDFDisplacement(sphere,
						v -> (float) noise.noise3_Classic(v.x() * 0.2, v.y() * 0.2, v.z() * 0.2) * 1.5F);
				return new SDFDisplacement(sphere, v -> random.nextFloat() * 3F - 1.5F);
			}

			private SDF makeCap(PlanetTreeFeatureConfig config, float radius, RandomSource random, OpenSimplex2F noise,
					SDF input) {
				int count = (int) radius;
				int offset = (int) (BRANCH.get(BRANCH.size() - 1).y() * radius);
				for (int i = 0; i < count; i++) {
					float angle = (float) i / (float) count * Mth.PI * 2;
					float scale = radius * MathUtil.randRange(random, 0.85F, 1.15F);

					List<Vector3f> branch = SplineUtil.copySpline(BRANCH);
					SplineUtil.rotateSpline(branch, angle);
					SplineUtil.scale(branch, scale);
					SDF b1 = SplineUtil.buildSDF(branch, 1, 1, config.tree().log());
					input = new SDFUnion(input, b1);

					branch = SplineUtil.copySpline(SIDE1);
					SplineUtil.rotateSpline(branch, angle);
					SplineUtil.scale(branch, scale);
					SDF b2 = SplineUtil.buildSDF(branch, 1, 1, config.tree().log());
					input = new SDFUnion(input, b2);

					branch = SplineUtil.copySpline(SIDE2);
					SplineUtil.rotateSpline(branch, angle);
					SplineUtil.scale(branch, scale);
					SDF b3 = SplineUtil.buildSDF(branch, 1, 1, config.tree().log());
					input = new SDFUnion(input, b3);
				}
				SDF leaves = leavesBall(config, radius * 1.15F + 2, random, noise);
				leaves = new SDFTranslate(leaves, 0, offset, 0);
				return new SDFUnion(input, leaves);
			}

			@Override
			public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
				float size = MathUtil.randRange(random, 6, 12);
				List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
				SplineUtil.offsetParts(spline, random, 1F, 0, 1F);

				OpenSimplex2F noise = new OpenSimplex2F(random.nextLong());
				float radius = size * MathUtil.randRange(random, 0.9F, 0.95F);

				SDF stem = SplineUtil.buildSDF(spline, 2.3F, 1.2F, config.tree().log());
				return makeCap(config, radius, random, noise, stem);

			}
		}),

		ARCH(new TreeMaker() {
			@Override
			public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
				float maxdist = 16;
				float mindist = 10;
				float thickness = MathUtil.randRange(random, 1.6f, 2.0f);
				int xoff = (int) MathUtil.randRangeSigned(random, mindist, maxdist);
				int zoff = (int) MathUtil.randRangeSigned(random, mindist, maxdist);
				int yoff = l.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, p.getX() + xoff, p.getZ() + zoff) - p.getY();
				float height = MathUtil.randRange(random, 10, 25);

				List<Vector3f> spline = SplineUtil.makeArch(0, 0, 0, xoff, yoff, zoff, height, 10);
				SplineUtil.offsetParts(spline, random, 0.5F, 0.5f, 0.5F);
				SDF stem = SplineUtil.buildSDF(spline, thickness, thickness, config.tree().log());
				SDF leaves = SplineUtil.buildSDF(spline, thickness + 0.3f, thickness + 0.3f,
						x -> config.tree().leaves());
				leaves = new SDFSubtraction(leaves, stem);
				SDF res = new SDFUnion(leaves, stem);
				res.addPostProcess(
						new SDFSelective(new SDFChanceFilter(random, 0.4f), config.tree().leaves().getBlock()));
				return res;
			}
		}),

		FIR(new TreeMaker() {
			@Override
			public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
				int segments = 10 + random.nextInt(5);
				float size = MathUtil.randRange(random, 40, 70);
				List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, segments);
				SplineUtil.offsetParts(spline, random, 1F, 0.3f, 1F);
				SDF stem = SplineUtil.buildSDF(spline, 2.0f, 0.8f, config.tree().log());
				
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
					disc = new SDFDisplacement(disc, v -> random.nextFloat() * 5F - 2.5F);
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
		}),

		LOLLIPOP(new TreeMaker() {
			public SDF build(PlanetTreeFeatureConfig config, RandomSource random, WorldGenLevel l, BlockPos p) {
				float size = MathUtil.randRange(random, 15, 35);
				List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
				SplineUtil.offsetParts(spline, random, 1.5F, 0.3f, 1.5F);
				SDF stem = SplineUtil.buildSDF(spline, 1.8f, 1.3f, config.tree().log());
				float rad = MathUtil.randRange(random, 5, 10);
				SDF leaves = new SDFSphere(rad).setBlock(config.tree().leaves());
				leaves = new SDFTranslate(leaves, 0, size, 0);
				leaves = new SDFDisplacement(leaves, v -> random.nextFloat() * 5F - 2.5F);
				return new SDFUnion(stem, leaves);
			};
		});

		public static final Codec<PlanetTreeFeature.TreeType> CODEC = StringRepresentable.fromEnum(TreeType::values);
		private TreeMaker tree;

		private TreeType(TreeMaker tree) {
			this.tree = tree;
		}

		public TreeMaker getTree() {
			return tree;
		}

		@Override
		public String getSerializedName() {
			return name();
		}
	}

	public static record PlanetTreeFeatureConfig(TreeType type, VegetationRules rules, Tree tree)
			implements FeatureConfiguration {
		public static final Codec<PlanetTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance
				.group(TreeType.CODEC.fieldOf("type").forGetter(PlanetTreeFeatureConfig::type),
						VegetationRules.CODEC.fieldOf("rules").forGetter(PlanetTreeFeatureConfig::rules),
						Tree.CODEC.fieldOf("tree").forGetter(PlanetTreeFeatureConfig::tree))
				.apply(instance, PlanetTreeFeatureConfig::new));
	}

	public PlanetTreeFeature() {
		super(PlanetTreeFeatureConfig.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<PlanetTreeFeatureConfig> ctx) {
		PlanetTreeFeatureConfig cfg = ctx.config();
		BlockPos origin = ctx.origin();
		if (!ctx.level().getBlockState(origin.below()).is(cfg.rules().growable())) {
			return false;
		}
		SDF tree = cfg.type().getTree().build(cfg, ctx.random(), ctx.level(), ctx.origin());
		tree.fillRecursive(ctx.level(), origin);
		return true;
	}
}