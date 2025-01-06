package com.machina.block.machine;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.block.tile.machine.BatteryBlockEntity;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class BatteryBlock extends MachineBlock {

	public static final BooleanProperty LIT = BooleanProperty.create("lit");

	public BatteryBlock(Properties props) {
		super(props);

		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
	}

	@Override
	public BlockEntityType<?> getBlockEntityType() {
		return BlockEntityInit.BATTERY.get();
	}

	@Override
	public Class<? extends MachinaBlockEntity> getBlockEntityClass() {
		return BatteryBlockEntity.class;
	}

	@Override
	protected boolean isTickable() {
		return true;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(LIT);
		super.createBlockStateDefinition(builder);
	}
}