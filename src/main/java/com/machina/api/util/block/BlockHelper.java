package com.machina.api.util.block;

import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.machina.Machina;
import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.api.util.math.MathUtil;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.RegistryLayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public class BlockHelper {
	@SuppressWarnings("unchecked")
	public static <T extends BlockEntity> boolean doWithTe(BlockGetter world, BlockPos pos, Class<T> clazz,
			Consumer<T> todo) {
		BlockEntity e = world.getBlockEntity(pos);
		if (e == null || !(clazz.isAssignableFrom(e.getClass()))) {
			Machina.LOGGER.error(String.format("BE at %s is null.", pos.toShortString()));
			return false;
		}

		todo.accept((T) e);
		return true;
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

	public static HolderLookup<Block> blockHolderLookup() {
		return RegistryLayer.createRegistryAccess().compositeAccess().lookup(Registries.BLOCK).get();
	}

	public static BlockState parseState(HolderLookup<Block> block, String state) {
		try {
			return BlockStateParser.parseForBlock(block, state, true).blockState();
		} catch (CommandSyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean hasEnergy(BlockEntity be, @Nullable Direction side) {
		if (be == null) {
			return false;
		}
		return be.getCapability(ForgeCapabilities.ENERGY, side).isPresent();
	}

	// McJty
	public static long receiveEnergy(BlockEntity tileEntity, Direction from, long maxReceive) {
		if (tileEntity != null) {
			return tileEntity.getCapability(ForgeCapabilities.ENERGY, from)
					.map(handler -> handler.receiveEnergy(MathUtil.unsignedClampToInt(maxReceive), false)).orElse(0);
		}
		return 0;
	}

	public static void sendEnergy(Level world, BlockPos pos, long storedPower, long sendPerTick,
			MachinaBlockEntity storage) {
		for (Direction facing : Direction.values()) {
			if (!storage.canConsumeEnergy(facing))
				continue;
			BlockPos p = pos.relative(facing);
			BlockEntity te = world.getBlockEntity(p);
			Direction opposite = facing.getOpposite();
			if (hasEnergy(te, opposite)) {
				long rfToGive = Math.min(sendPerTick, storedPower);
				long received = receiveEnergy(te, opposite, rfToGive);
				storage.consumeEnergy(received);
				storedPower -= received;
				if (storedPower <= 0) {
					break;
				}
			}
		}
	}

}
