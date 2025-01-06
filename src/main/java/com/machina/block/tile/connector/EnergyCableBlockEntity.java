package com.machina.block.tile.connector;

import com.machina.api.block.tile.ConnectorBlockEntity;
import com.machina.api.cap.energy.CableEnergyStorage;
import com.machina.config.CommonConfig;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class EnergyCableBlockEntity extends ConnectorBlockEntity<CableEnergyStorage> {

	public EnergyCableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public EnergyCableBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityInit.ENERGY_CABLE.get(), pos, state);
	}

	@Override
	public CableEnergyStorage createStorage(Direction side) {
		return new CableEnergyStorage(this, side);
	}

	@Override
	public int getRate() {
		return CommonConfig.cableTransferRate.get();
	}

	@Override
	public Capability<?> getCapability() {
		return ForgeCapabilities.ENERGY;
	}

	@Override
	public boolean activeModel() {
		return false;
	}

}
