package com.machina.block.connector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.machina.api.block.ConnectorBlock;
import com.machina.api.cap.energy.MachinaEnergyStorage;
import com.machina.api.tile.ConnectorBlockEntity;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.MapColor;

public class EnergyCableBlock extends ConnectorBlock {

	private static final Map<BlockPos, Set<BlockPos>> CACHE = new HashMap<>();

	public EnergyCableBlock() {
		super(Block.Properties.of().mapColor(MapColor.COLOR_GRAY).strength(1f).sound(SoundType.METAL).noOcclusion());
	}

	@Override
	public boolean canConnect(BlockEntity be, Direction dir) {
		return MachinaEnergyStorage.hasEnergy(be, dir);
	}

	@Override
	protected Map<BlockPos, Set<BlockPos>> getCache() {
		return CACHE;
	}

	@Override
	protected BlockEntityType<? extends ConnectorBlockEntity> getBlockEntityType() {
		return BlockEntityInit.ENERGY_CABLE.get();
	}

}
