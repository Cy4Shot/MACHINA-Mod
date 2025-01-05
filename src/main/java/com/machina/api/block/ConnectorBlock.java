package com.machina.api.block;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.machina.api.tile.ConnectorBlockEntity;
import com.machina.api.util.block.BlockHelper;
import com.machina.api.util.math.MathUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class ConnectorBlock extends Block implements EntityBlock {

	public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
	public static final BooleanProperty EAST = BlockStateProperties.EAST;
	public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
	public static final BooleanProperty WEST = BlockStateProperties.WEST;
	public static final BooleanProperty UP = BlockStateProperties.UP;
	public static final BooleanProperty DOWN = BlockStateProperties.DOWN;
	public static final BooleanProperty MIDDLE = BooleanProperty.create("middle");
	public static final BooleanProperty TILE = BooleanProperty.create("tile");

	private static final VoxelShape PART_C = Block.box(6, 6, 6, 10, 10, 10);
	private static final VoxelShape PART_M = Block.box(6.5, 6.5, 6.5, 9.5, 9.5, 9.5);
	private static final VoxelShape PART_N = Block.box(6.5, 6.5, 0, 9.5, 9.5, 7);
	private static final VoxelShape PART_E = Block.box(9.5, 6.5, 6.5, 16, 9.5, 9.5);
	private static final VoxelShape PART_S = Block.box(6.5, 6.5, 9.5, 9.5, 9.5, 16);
	private static final VoxelShape PART_W = Block.box(0, 6.5, 6.5, 6.5, 9.5, 9.5);
	private static final VoxelShape PART_U = Block.box(6.5, 9.5, 6.5, 9.5, 16, 9.5);
	private static final VoxelShape PART_D = Block.box(6.5, 0, 6.5, 9.5, 7, 9.5);

	public ConnectorBlock(Properties props) {
		super(props.noOcclusion());

		this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false)
				.setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false)
				.setValue(MIDDLE, false).setValue(TILE, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext pContext) {
		VoxelShape shape = state.getValue(MIDDLE) ? PART_M : PART_C;
		if (state.getValue(NORTH) || isConnectable(level, pos, Direction.NORTH))
			shape = Shapes.or(shape, PART_N);
		if (state.getValue(EAST) || isConnectable(level, pos, Direction.EAST))
			shape = Shapes.or(shape, PART_E);
		if (state.getValue(SOUTH) || isConnectable(level, pos, Direction.SOUTH))
			shape = Shapes.or(shape, PART_S);
		if (state.getValue(WEST) || isConnectable(level, pos, Direction.WEST))
			shape = Shapes.or(shape, PART_W);
		if (state.getValue(UP) || isConnectable(level, pos, Direction.UP))
			shape = Shapes.or(shape, PART_U);
		if (state.getValue(DOWN) || isConnectable(level, pos, Direction.DOWN))
			shape = Shapes.or(shape, PART_D);
		return shape;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level,
			BlockPos pos, BlockPos facingPos) {
		BlockHelper.doWithTe(level, pos, ConnectorBlockEntity.class, cable -> {
			if (!level.isClientSide()) {
				cable.dirs.clear();
				for (Direction dir : Direction.values()) {
					if (isConnectable(level, pos, dir))
						cable.dirs.add(dir);
				}
				cable.sync();
			}
		});
		return createState(level, pos);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return createState(ctx.getLevel(), ctx.getClickedPos());
	}

	public abstract boolean canConnect(BlockEntity be, Direction dir);

	private boolean isConnectable(BlockGetter level, BlockPos pos, Direction dir) {
		BlockEntity be = level.getBlockEntity(pos.relative(dir));
		return !(be instanceof ConnectorBlockEntity) && canConnect(be, dir);
	}

	private boolean[] canAttach(BlockGetter level, BlockPos pos, Direction dir) {
		boolean connectable = isConnectable(level, pos, dir);
		return new boolean[] { level.getBlockState(pos.relative(dir)).getBlock() == this || connectable, connectable };
	}

	private BlockState createState(BlockGetter level, BlockPos pos) {
		final BlockState state = defaultBlockState();
		boolean[] north = canAttach(level, pos, Direction.NORTH);
		boolean[] south = canAttach(level, pos, Direction.SOUTH);
		boolean[] west = canAttach(level, pos, Direction.WEST);
		boolean[] east = canAttach(level, pos, Direction.EAST);
		boolean[] up = canAttach(level, pos, Direction.UP);
		boolean[] down = canAttach(level, pos, Direction.DOWN);

		boolean tile = north[1] || south[1] || west[1] || east[1] || up[1] || down[1];

		if (!tile)
			BlockHelper.doWithTe(level, pos, ConnectorBlockEntity.class, be -> be.setRemoved());

		boolean middle = false;
		if (MathUtil.numTrue(north[0], south[0], west[0], east[0], up[0], down[0]) == 2) {
			for (Direction dir : Direction.values()) {
				if (canAttach(level, pos, dir)[0]) {
					if (canAttach(level, pos, dir.getOpposite())[0])
						middle = true;
					break;
				}
			}
		}

		//@formatter:off
		return state
				.setValue(NORTH, north[0])
				.setValue(SOUTH, south[0])
				.setValue(WEST, west[0])
				.setValue(EAST, east[0])
				.setValue(UP, up[0])
				.setValue(DOWN, down[0])
				.setValue(MIDDLE, middle)
				.setValue(TILE, tile);
		//@formatter:on
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> b) {
		b.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, MIDDLE, TILE);
		super.createBlockStateDefinition(b);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);
		if (level.isClientSide())
			return;

		if (!BlockHelper.doWithTe(level, pos, ConnectorBlockEntity.class, be -> be.enqueueSearch())) {
			findConnectors(level, pos, pos);
		}
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState old, boolean moving) {
		if (level.isClientSide())
			return;

		BlockHelper.doWithTe(level, pos, ConnectorBlockEntity.class, be -> {
			be.dirs.clear();
			for (Direction dir : Direction.values()) {
				if (isConnectable(level, pos, dir))
					be.dirs.add(dir);

			}
			be.sync();
		});

		super.onPlace(state, level, pos, old, moving);
	}

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean moving) {
		findConnectors(level, pos, pos);
		super.onRemove(state, level, pos, newState, moving);
	}

	protected abstract Map<BlockPos, Set<BlockPos>> getCache();

	public void findConnectors(BlockGetter world, BlockPos poss, BlockPos pos) {
		Set<BlockPos> ss = getCache().get(poss);
		if (ss == null)
			ss = new HashSet<>();

		if (!ss.contains(pos)) {
			for (Direction direction : Direction.values()) {
				BlockPos blockPos = pos.relative(direction);
				Block block = world.getBlockState(blockPos).getBlock();
				if (block == this) {
					BlockHelper.doWithTe(world, blockPos, ConnectorBlockEntity.class, be -> be.enqueueSearch());
					ss.add(pos);
					getCache().put(poss, ss);
					((ConnectorBlock) block).findConnectors(world, poss, blockPos);
				}
			}
		}
		getCache().clear();
	}

	public void searchConnectors(BlockGetter world, BlockPos pos, ConnectorBlockEntity first, int dist) {
		int newdist = dist + 1;
		for (Direction dir : Direction.values()) {
			BlockPos blockPos = pos.relative(dir);

			if (!first.isInCache(blockPos)) {
				if (!blockPos.equals(first.getBlockPos())) {

					Block block = world.getBlockState(blockPos).getBlock();
					if (block == this) {
						BlockHelper.doWithTe(world, blockPos, ConnectorBlockEntity.class, be -> be.dirs.forEach(
								d -> first.connectors.add(new ConnectorBlockEntity.Connection(blockPos, d, newdist))));
						first.addToCache(blockPos);
						((ConnectorBlock) block).searchConnectors(world, blockPos, first, newdist);
					}
				}
			}
		}
	}

	protected abstract BlockEntityType<? extends ConnectorBlockEntity> getBlockEntityType();

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return getBlockEntityType().create(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
			BlockEntityType<T> type) {
		return type == getBlockEntityType() ? ConnectorBlockEntity::tick : null;
	}

}
