package com.machina.api.util.math.sdf;

import java.util.Map;
import java.util.Optional;

import com.mojang.datafixers.util.Pair;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PosInfo implements Comparable<PosInfo> {
	private static final BlockState AIR = Blocks.AIR.defaultBlockState();
	private final Map<BlockPos, PosInfo> blocks;
	private final Map<BlockPos, PosInfo> add;
	private final BlockPos pos;
	private Optional<Pair<BlockPos, BlockState>> extra = Optional.empty();
	private BlockState state;

	public static PosInfo create(Map<BlockPos, PosInfo> blocks, Map<BlockPos, PosInfo> add, BlockPos pos) {
		return new PosInfo(blocks, add, pos);
	}
	
	public static PosInfo create(Map<BlockPos, PosInfo> blocks, Map<BlockPos, PosInfo> add, BlockPos pos, BlockState state) {
		PosInfo info = create(blocks, add, pos);
		info.setState(state);
		return info;
	}

	private PosInfo(Map<BlockPos, PosInfo> blocks, Map<BlockPos, PosInfo> add, BlockPos pos) {
		this.blocks = blocks;
		this.add = add;
		this.pos = pos;
		blocks.put(pos, this);
	}
	
	public PosInfo(PosInfo old, BlockState state) {
		this.blocks = old.blocks;
		this.add = old.add;
		this.pos = old.pos;
		this.state = state;
	}

	public BlockState getState() {
		return state;
	}

	public BlockState getState(BlockPos pos) {
		PosInfo info = blocks.get(pos);
		if (info == null) {
			info = add.get(pos);
			return info == null ? AIR : info.getState();
		}
		return info.getState();
	}

	public void setState(BlockState state) {
		this.state = state;
	}

	public void setState(BlockPos pos, BlockState state) {
		PosInfo info = blocks.get(pos);
		if (info != null) {
			info.setState(state);
		}
	}

	public BlockState getState(Direction dir) {
		PosInfo info = blocks.get(pos.relative(dir));
		if (info == null) {
			info = add.get(pos.relative(dir));
			return info == null ? AIR : info.getState();
		}
		return info.getState();
	}

	public BlockState getState(Direction dir, int distance) {
		PosInfo info = blocks.get(pos.relative(dir, distance));
		if (info == null) {
			return AIR;
		}
		return info.getState();
	}

	public BlockState getStateUp() {
		return getState(Direction.UP);
	}

	public BlockState getStateDown() {
		return getState(Direction.DOWN);
	}

	@Override
	public int hashCode() {
		return pos.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PosInfo)) {
			return false;
		}
		return pos.equals(((PosInfo) obj).pos);
	}

	@Override
	public int compareTo(PosInfo info) {
		return this.pos.getY() - info.pos.getY();
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setBlockPos(BlockPos pos, BlockState state) {
		PosInfo info = new PosInfo(blocks, add, pos);
		info.state = state;
		add.put(pos, info);
	}
	
	public void setExtra(Optional<Pair<BlockPos, BlockState>> extra) {
		this.extra = extra;
	}
	
	public Optional<Pair<BlockPos, BlockState>> getExtra() {
		return extra;
	}
}
