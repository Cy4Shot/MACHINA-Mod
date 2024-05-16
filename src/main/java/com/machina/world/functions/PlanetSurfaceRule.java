package com.machina.world.functions;

import com.google.common.collect.ImmutableList;
import com.machina.world.biome.PlanetBiome;
import com.machina.world.biome.PlanetBiome.BiomeCategory;

import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class PlanetSurfaceRule {

	private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
	private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
	private static final SurfaceRules.RuleSource DIRT = makeStateRule(Blocks.DIRT);
	private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.STONE);
	private static final SurfaceRules.RuleSource GRAVEL = makeStateRule(Blocks.GRAVEL);

	private static SurfaceRules.RuleSource makeStateRule(Block p_194811_) {
		return SurfaceRules.state(p_194811_.defaultBlockState());
	}

	public static SurfaceRules.RuleSource planet() {
		return planetLike(false, true);
	}

	public static SurfaceRules.RuleSource planetLike(boolean top, boolean bottom) {
		SurfaceRules.ConditionSource cs7 = SurfaceRules.waterBlockCheck(-1, 0);
		SurfaceRules.ConditionSource cs8 = SurfaceRules.waterBlockCheck(0, 0);
		SurfaceRules.ConditionSource cs9 = SurfaceRules.waterStartCheck(-6, -1);
		SurfaceRules.RuleSource rs = SurfaceRules.sequence(SurfaceRules.ifTrue(cs8, GRASS_BLOCK), DIRT);
		SurfaceRules.RuleSource rs2 = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE),
				GRAVEL);
		SurfaceRules.RuleSource rs7 = SurfaceRules.sequence(rs);
		SurfaceRules.RuleSource rs8 = SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(cs7, rs7)),
				SurfaceRules.ifTrue(cs9, SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, DIRT))),
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, rs2));
		ImmutableList.Builder<SurfaceRules.RuleSource> builder = ImmutableList.builder();
		if (top) {
			builder.add(SurfaceRules.ifTrue(SurfaceRules.not(
					SurfaceRules.verticalGradient("bedrock_roof", VerticalAnchor.belowTop(5), VerticalAnchor.top())),
					BEDROCK));
		}
		if (bottom) {
			builder.add(SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(),
					VerticalAnchor.aboveBottom(5)), BEDROCK));
		}

		SurfaceRules.RuleSource rs9 = SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), rs8);
		builder.add(rs9);
		return SurfaceRules.sequence(builder.build().toArray((p_198379_) -> {
			return new SurfaceRules.RuleSource[p_198379_];
		}));
	}

	private static PlanetBiomeConditionSource isPlanetBiome(BiomeCategory cat) {
		return new PlanetBiomeConditionSource(cat);
	}

	static final class PlanetBiomeConditionSource implements SurfaceRules.ConditionSource {
		static final KeyDispatchDataCodec<PlanetBiomeConditionSource> CODEC = KeyDispatchDataCodec
				.of(BiomeCategory.CODEC.fieldOf("planet_biome_is").xmap(PlanetSurfaceRule::isPlanetBiome, c -> c.cat));

		private final BiomeCategory cat;

		PlanetBiomeConditionSource(BiomeCategory cat) {
			this.cat = cat;
		}

		public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
			return CODEC;
		}

		public SurfaceRules.Condition apply(final SurfaceRules.Context ctx) {
			class PlanetBiomeCondition extends SurfaceRules.LazyYCondition {
				PlanetBiomeCondition() {
					super(ctx);
				}

				protected boolean compute() {
					Biome b = this.context.biome.get().get();
					if (b instanceof PlanetBiome) {
						if (PlanetBiomeConditionSource.this.cat.equals(((PlanetBiome) b).cat)) {
							return true;
						}
					}
					return false;
				}
			}

			return new PlanetBiomeCondition();
		}

		public boolean equals(Object p_209694_) {
			if (this == p_209694_) {
				return true;
			} else if (p_209694_ instanceof PlanetBiomeConditionSource) {
				PlanetBiomeConditionSource condsrc = (PlanetBiomeConditionSource) p_209694_;
				return this.cat.equals(condsrc.cat);
			} else {
				return false;
			}
		}

		public int hashCode() {
			return this.cat.hashCode();
		}

		public String toString() {
			return "PlanetBiomeConditionSource[cat=" + this.cat.name() + "]";
		}
	}

}
