package com.machina.api.util.math.sdf.post;

import java.util.function.Predicate;

import com.machina.api.util.math.sdf.PosInfo;
import com.machina.api.util.math.sdf.SDF.SDFPostProcessor;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class SDFSelective implements SDFPostProcessor {

	Predicate<BlockState> test;
	SDFPostProcessor parent;
	
	public SDFSelective(SDFPostProcessor parent, Predicate<BlockState> test) {
		this.parent = parent;
		this.test = test;
	}
	
	public SDFSelective(SDFPostProcessor parent, Block block) {
		this(parent, state -> state.getBlock().equals(block));
	}
	
	@Override
	public BlockState apply(PosInfo info) {
		BlockState state = info.getState();
		if (test.test(state)) {
			return parent.apply(info);
		}
		return state;
	}
}