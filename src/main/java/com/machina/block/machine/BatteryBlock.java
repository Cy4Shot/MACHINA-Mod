package com.machina.block.machine;

import com.machina.api.block.MachineBlock;
import com.machina.api.tile.MachinaBlockEntity;
import com.machina.block.tile.machine.BatteryBlockEntity;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.world.level.block.entity.BlockEntityType;

public class BatteryBlock extends MachineBlock {

	public BatteryBlock(Properties props) {
		super(props);
	}

	@Override
	public BlockEntityType<?> getBlockEntityType() {
		return BlockEntityInit.BATTERY.get();
	}

	@Override
	public Class<? extends MachinaBlockEntity> getBlockEntityClass() {
		return BatteryBlockEntity.class;
	}
}