package com.machina.api.util;

import java.util.function.Consumer;

import com.machina.Machina;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class BlockHelper {
	@SuppressWarnings("unchecked")
	public static <T extends BlockEntity> void doWithTe(Level world, BlockPos pos, Class<T> clazz, Consumer<T> todo) {
		BlockEntity e = world.getBlockEntity(pos);
		if (e == null || !(clazz.isAssignableFrom(e.getClass()))) {
			Machina.LOGGER.error(String.format("BE at %s is null.", pos.toShortString()));
		}

		todo.accept((T) e);
	}

	public static <C> LazyOptional<C> getCapability(BlockGetter world, BlockPos pos, Direction side,
			Capability<C> capability) {
		BlockEntity be = world.getBlockEntity(pos);
		if (be != null) {
			return be.getCapability(capability, side);
		}
		return LazyOptional.empty();
	}
	
	public static BlockState waterlog(BlockState state, BlockGetter world, BlockPos pos) {
		FluidState fluidState = world.getFluidState(pos);
		return state.setValue(BlockStateProperties.WATERLOGGED,
				fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8);
	}
}
