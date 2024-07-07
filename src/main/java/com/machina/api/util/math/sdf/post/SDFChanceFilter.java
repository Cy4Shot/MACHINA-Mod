package com.machina.api.util.math.sdf.post;

import com.machina.api.util.math.sdf.PosInfo;
import com.machina.api.util.math.sdf.SDF.SDFPostProcessor;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SDFChanceFilter implements SDFPostProcessor {

	float chance;
	RandomSource random;
	
	public SDFChanceFilter(RandomSource random, float chance) {
		this.random = random;
		this.chance = chance;
	}

	@Override
	public BlockState apply(PosInfo info) {
		return this.random.nextFloat() < this.chance ? info.getState() : Blocks.AIR.defaultBlockState();
	}
}