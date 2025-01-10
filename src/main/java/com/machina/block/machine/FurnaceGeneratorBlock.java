package com.machina.block.machine;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.block.tile.machine.FurnaceGeneratorBlockEntity;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class FurnaceGeneratorBlock extends MachineBlock {
	
	public static final BooleanProperty LIT = BooleanProperty.create("lit");

	public FurnaceGeneratorBlock(Properties props) {
		super(props);
		
		this.registerDefaultState(this.stateDefinition.any().setValue(LIT, false));
	}

	@Override
	public BlockEntityType<?> getBlockEntityType() {
		return BlockEntityInit.FURNACE_GENERATOR.get();
	}

	@Override
	public Class<? extends MachinaBlockEntity> getBlockEntityClass() {
		return FurnaceGeneratorBlockEntity.class;
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