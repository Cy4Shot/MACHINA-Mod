package com.machina.api.util.math.sdf.post;

import java.util.Optional;

import com.machina.api.util.math.sdf.PosInfo;
import com.machina.api.util.math.sdf.SDF.SDFPostProcessor;
import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

public record SDFFruitPlacer(RandomSource random, float chance, BlockState fruit, BlockState leaves)
		implements SDFPostProcessor {

	public boolean isState(PosInfo pos, BlockPos p, BlockState state) {
		return pos.getState(p).equals(state);
	}

	public boolean isAir(PosInfo pos, BlockPos p) {
		return pos.getState(p).isAir();
	}

	@Override
	public Optional<Pair<BlockPos, BlockState>> extra(PosInfo posInfo) {
		BlockPos p = posInfo.getPos();
		if (isState(posInfo, p, leaves) && random.nextFloat() < chance) {
			if (isAir(posInfo, p.below()) && isAir(posInfo, p.below(2))) {
				return Optional.of(Pair.of(p.below(), fruit));
			}
		}
		return Optional.empty();
	}
}
