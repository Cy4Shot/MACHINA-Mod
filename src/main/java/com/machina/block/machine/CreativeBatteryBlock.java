package com.machina.block.machine;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.block.tile.machine.CreativeBatteryBlockEntity;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.world.level.block.entity.BlockEntityType;

public class CreativeBatteryBlock extends MachineBlock {

	public CreativeBatteryBlock(Properties props) {
		super(props);
	}

	@Override
	public BlockEntityType<?> getBlockEntityType() {
		return BlockEntityInit.CREATIVE_BATTERY.get();
	}

	@Override
	public Class<? extends MachinaBlockEntity> getBlockEntityClass() {
		return CreativeBatteryBlockEntity.class;
	}

	@Override
	protected boolean isTickable() {
		return true;
	}
}