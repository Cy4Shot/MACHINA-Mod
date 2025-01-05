package com.machina.block.machine;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.block.tile.machine.FurnaceGeneratorBlockEntity;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.world.level.block.entity.BlockEntityType;

public class FurnaceGeneratorBlock extends MachineBlock {

	public FurnaceGeneratorBlock(Properties props) {
		super(props);
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
}