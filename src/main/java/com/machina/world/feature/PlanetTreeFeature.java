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
import com.machina.api.util.math.sdf.primitive.SDFSphere;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class PlanetTreeFeature extends Feature<PlanetTreeFeature.PlanetTreeFeatureConfig> {

	@FunctionalInterface
	public static interface TreeMaker {
		SDF build(PlanetTreeFeatureConfig config, RandomSource random);
	}

	public static enum TreeType implements StringRepresentable {
		BASIC(new TreeMaker() {
			@Override
			public SDF build(PlanetTreeFeatureConfig config, RandomSource random) {
				float height = MathUtil.randRange(random, 10, 25);
				int count = Mth.floor(height / 4);
				List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, height, 0, count);
				SplineUtil.offsetParts(spline, random, 1F, 0, 1F);
				return SplineUtil.buildSDF(spline, 2.1F, 1.5F, x -> config.tree().log());
			}
		}),

		BASIC2(new TreeMaker() {

			private static List<Vector3f> BRANCH = Arrays.asList(new Vector3f(0, 0, 0), new Vector3f(0.1F, 0.3F, 0),
					new Vector3f(0.4F, 0.6F, 0), new Vector3f(0.8F, 0.8F, 0), new Vector3f(1, 1, 0));
			private static List<Vector3f> SIDE1 = Arrays.asList(new Vector3f(0.4F, 0.6F, 0),
					new Vector3f(0.8F, 0.8F, 0), new Vector3f(1, 1, 0));
			private static List<Vector3f> SIDE2 = SplineUtil.copySpline(SIDE1);

			private SDF leavesBall(PlanetTreeFeatureConfig config, float radius, RandomSource random,
					OpenSimplex2F noise) {
				SDF sphere = new SDFSphere().setRadius(radius).setBlock(config.tree().leaves());
				SDF sub = new SDFScale().setScale(5).setSource(sphere);
				sub = new SDFTranslate().setTranslate(0, -radius * 5, 0).setSource(sub);
				sphere = new SDFSubtraction().setSourceA(sphere).setSourceB(sub);
				sphere = new SDFScale3D().setScale(1, 0.5F, 1).setSource(sphere);
				sphere = new SDFDisplacement().setFunction(
						(vec) -> (float) noise.noise3_Classic(vec.x() * 0.2, vec.y() * 0.2, vec.z() * 0.2) * 1.5F)
						.setSource(sphere);
				return new SDFDisplacement().setFunction((vec) -> random.nextFloat() * 3F - 1.5F).setSource(sphere);
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
					SDF b1 = SplineUtil.buildSDF(branch, 1, 1, x -> config.tree().log());
					input = new SDFUnion().setSourceA(input).setSourceB(b1);

					branch = SplineUtil.copySpline(SIDE1);
					SplineUtil.rotateSpline(branch, angle);
					SplineUtil.scale(branch, scale);
					SDF b2 = SplineUtil.buildSDF(branch, 1, 1, x -> config.tree().log());
					input = new SDFUnion().setSourceA(input).setSourceB(b2);

					branch = SplineUtil.copySpline(SIDE2);
					SplineUtil.rotateSpline(branch, angle);
					SplineUtil.scale(branch, scale);
					SDF b3 = SplineUtil.buildSDF(branch, 1, 1, x -> config.tree().log());
					input = new SDFUnion().setSourceA(input).setSourceB(b3);
				}
				SDF leaves = leavesBall(config, radius * 1.15F + 2, random, noise);
				SDF upperleaves = new SDFTranslate().setTranslate(0, offset, 0).setSource(leaves);
				SDF output = new SDFUnion().setSourceA(input).setSourceB(upperleaves);
				return output;
			}

			@Override
			public SDF build(PlanetTreeFeatureConfig config, RandomSource random) {
				float size = MathUtil.randRange(random, 6, 12);
				List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
				SplineUtil.offsetParts(spline, random, 1F, 0, 1F);

				OpenSimplex2F noise = new OpenSimplex2F(random.nextLong());
				float radius = size * MathUtil.randRange(random, 0.9F, 0.95F);

				SDF stem = SplineUtil.buildSDF(spline, 2.3F, 1.2F, x -> config.tree().log());
				return makeCap(config, radius, random, noise, stem);

			}
		}),

		BASIC3(new TreeMaker() {
			public SDF build(PlanetTreeFeatureConfig config, RandomSource random) {
				float size = MathUtil.randRange(random, 15, 35);
				List<Vector3f> spline = SplineUtil.makeSpline(0, 0, 0, 0, size, 0, 6);
				SplineUtil.offsetParts(spline, random, 1.5F, 0.3f, 1.5F);
				SDF stem = SplineUtil.buildSDF(spline, 1.8f, 1.3f, x -> config.tree().log());
				float rad = MathUtil.randRange(random, 5, 10);
				SDF leaves = new SDFSphere().setRadius(rad).setBlock(config.tree().leaves());
				leaves = new SDFTranslate().setTranslate(0, size, 0).setSource(leaves);
				leaves = new SDFDisplacement().setFunction((vec) -> random.nextFloat() * 5F - 2.5F).setSource(leaves);
				return new SDFUnion().setSourceA(stem).setSourceB(leaves);
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
		SDF tree = cfg.type().getTree().build(cfg, ctx.random());
		tree.fillRecursive(ctx.level(), origin);
		return true;
	}
}