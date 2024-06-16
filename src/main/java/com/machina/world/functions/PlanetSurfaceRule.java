package com.machina.world.functions;

import java.util.Arrays;
import java.util.stream.LongStream;

import com.google.common.collect.ImmutableList;
import com.machina.api.starchart.PlanetType;
import com.machina.api.starchart.obj.Planet;
import com.machina.world.biome.PlanetBiome;
import com.machina.world.biome.PlanetBiome.BiomeCategory;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;

import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.Context;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class PlanetSurfaceRule {

	private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK.defaultBlockState());

	private static SurfaceRules.RuleSource makeStateRule(BlockState state) {
		return SurfaceRules.state(state);
	}

	public static SurfaceRules.RuleSource planet(Planet p) {
		return planetLike(p, false, true);
	}

	public static SurfaceRules.RuleSource planetLike(Planet p, boolean top, boolean bottom) {

		PlanetType type = p.type();
		SurfaceRules.RuleSource top_block = makeStateRule(type.surface().top());
		SurfaceRules.RuleSource second_top_block = makeStateRule(type.surface().second());
		SurfaceRules.RuleSource rock = makeStateRule(type.underground().rock().base());

		SurfaceRules.ConditionSource cs7 = SurfaceRules.waterBlockCheck(-1, 0);
		SurfaceRules.ConditionSource cs8 = SurfaceRules.waterBlockCheck(0, 0);
		SurfaceRules.ConditionSource cs9 = SurfaceRules.waterStartCheck(-6, -1);
		SurfaceRules.RuleSource rs = SurfaceRules.sequence(SurfaceRules.ifTrue(cs8, top_block), second_top_block);
		SurfaceRules.RuleSource rs2 = SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, rock);
		SurfaceRules.RuleSource rs7 = SurfaceRules.sequence(rs);
		SurfaceRules.RuleSource rs8 = SurfaceRules.sequence(
				SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(cs7, rs7)),
				SurfaceRules.ifTrue(cs9,
						SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, second_top_block))),
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
		builder.add(SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), rs8));
		return SurfaceRules.sequence(builder.build().toArray((x) -> {
			return new SurfaceRules.RuleSource[x];
		}));
	}

	private static PlanetBiomeConditionSource isPlanetBiome(BiomeCategory cat) {
		return new PlanetBiomeConditionSource(cat);
	}

	@SuppressWarnings("unused")
	private static AndConditionSource and(SurfaceRules.ConditionSource... ts) {
		return new AndConditionSource(ts);
	}

	@SuppressWarnings("unused")
	private static AndConditionSource or(SurfaceRules.ConditionSource... ts) {
		return new AndConditionSource(ts);
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

	static record AndConditionSource(SurfaceRules.ConditionSource... ts) implements SurfaceRules.ConditionSource {
		@Override
		public SurfaceRules.Condition apply(Context t) {
			return new SurfaceRules.Condition() {
				@Override
				public boolean test() {
					return Arrays.asList(ts).stream().allMatch(x -> x.apply(t).test());
				}
			};
		}

		@Override
		public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
			return KeyDispatchDataCodec
					.of(Codec
							.compoundList(Codec.LONG,
									SurfaceRules.ConditionSource.CODEC)
							.xmap(l -> new AndConditionSource(
									(SurfaceRules.ConditionSource[]) l.stream().map(Pair::getSecond).toArray()),
									x -> LongStream.range(0, x.ts().length)
											.mapToObj(t -> new Pair<Long, SurfaceRules.ConditionSource>(t,
													(SurfaceRules.ConditionSource) x.ts[(int) t]))
											.toList()));
		}
	}

	static record OrConditionSource(SurfaceRules.ConditionSource... ts) implements SurfaceRules.ConditionSource {
		@Override
		public SurfaceRules.Condition apply(Context t) {
			return new SurfaceRules.Condition() {
				@Override
				public boolean test() {
					return Arrays.asList(ts).stream().anyMatch(x -> x.apply(t).test());
				}
			};
		}

		@Override
		public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
			return KeyDispatchDataCodec
					.of(Codec
							.compoundList(Codec.LONG,
									SurfaceRules.ConditionSource.CODEC)
							.xmap(l -> new OrConditionSource(
									(SurfaceRules.ConditionSource[]) l.stream().map(Pair::getSecond).toArray()),
									x -> LongStream.range(0, x.ts().length)
											.mapToObj(t -> new Pair<Long, SurfaceRules.ConditionSource>(t,
													(SurfaceRules.ConditionSource) x.ts[(int) t]))
											.toList()));
		}
	}

}
