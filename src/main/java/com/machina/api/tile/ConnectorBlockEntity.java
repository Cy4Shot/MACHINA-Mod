package com.machina.api.tile;

import java.util.ArrayList;
import java.util.List;

import com.machina.api.block.ConnectorBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ConnectorBlockEntity extends BaseBlockEntity {

	private int recursionDepth;
	private boolean search = false;

	public List<Connection> connectors = new ArrayList<>();
	public List<BlockPos> cache = new ArrayList<>();
	public List<Direction> dirs = new ArrayList<>();

	public ConnectorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T be) {
		if (level.isClientSide())
			return;

		ConnectorBlockEntity cbe = (ConnectorBlockEntity) be;

		if (cbe.search) {
			cbe.search();
			cbe.search = false;
			cbe.sync();
		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		ListTag cons = new ListTag();
		this.connectors.forEach(pos -> {
			CompoundTag postag = new CompoundTag();
			postag.put("connector", pos.save());
			cons.add(postag);
		});
		tag.put("connectors", cons);

		ListTag sides = new ListTag();
		this.dirs.forEach(dir -> {
			CompoundTag postag = new CompoundTag();
			postag.putInt("dir", dir.get3DDataValue());
			sides.add(postag);
		});
		tag.put("dirs", sides);
		super.saveAdditional(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		connectors = new ArrayList<>();
		dirs = new ArrayList<>();

		ListTag cons = tag.getList("connectors", Tag.TAG_COMPOUND);
		for (int j = 0; j < cons.size(); j++) {
			connectors.add(Connection.load(cons.getCompound(j).getCompound("connector")));
		}

		ListTag sides = tag.getList("dirs", Tag.TAG_COMPOUND);
		for (int j = 0; j < sides.size(); j++) {
			dirs.add(Direction.from3DDataValue(sides.getCompound(j).getInt("dir")));
		}
		super.load(tag);
	}

	public void enqueueSearch() {
		this.connectors.clear();
		this.search = true;
		this.sync();
	}

	public void search() {
		if (this.level != null) {
			addToCache(this.worldPosition);

			dirs.forEach(dir -> {
				connectors.add(new Connection(this.worldPosition, dir, 0));
			});

			Block b = this.getBlockState().getBlock();
			if (b instanceof ConnectorBlock)
				((ConnectorBlock) b).searchConnectors(this.level, this.worldPosition, this, 0);
		}
		this.cache.clear();
	}

	public void addToCache(BlockPos pos) {
		this.cache.add(pos);
	}

	public boolean isInCache(BlockPos pos) {
		return this.cache.contains(pos);
	}

	public boolean pushRecursion() {
		if (recursionDepth >= 1) {
			return true;
		}
		recursionDepth++;
		return false;
	}

	public void popRecursion() {
		recursionDepth--;
	}

	public static class Connection {
		private final BlockPos pos;
		private final Direction direction;
		private final int distance;

		public Connection(BlockPos pos, Direction direction, int distance) {
			this.pos = pos;
			this.direction = direction;
			this.distance = distance;
		}

		public int getDistance() {
			return distance;
		}

		public BlockPos getPos() {
			return pos;
		}

		public BlockPos getDest() {
			return pos.relative(direction.getOpposite());
		}

		public Direction getDirection() {
			return direction;
		}

		@Override
		public String toString() {
			return "Connection{" + "pos=" + pos + ", direction=" + direction + ", distance=" + distance + '}';
		}

		public CompoundTag save() {
			CompoundTag tag = new CompoundTag();
			tag.put("CPos", NbtUtils.writeBlockPos(pos));
			tag.putInt("CDir", direction.get3DDataValue());
			tag.putInt("CDis", distance);
			return tag;
		}

		public static Connection load(CompoundTag nbt) {
			BlockPos p = NbtUtils.readBlockPos(nbt.getCompound("CPos"));
			Direction d = Direction.from3DDataValue(nbt.getInt("CDir"));
			int dist = nbt.getInt("CDis");
			return new Connection(p, d, dist);
		}
	}

}
