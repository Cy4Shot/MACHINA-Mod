package com.machina.util.helper;

import java.util.concurrent.atomic.AtomicInteger;

import com.machina.capability.energy.MachinaEnergyStorage;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;

public class EnergyHelper {

	public static void sendOutPower(MachinaEnergyStorage storage, World level, BlockPos pos, Runnable onChanged) {
		AtomicInteger capacity = new AtomicInteger(storage.getEnergyStored());
		if (capacity.get() > 0) {
			for (Direction direction : Direction.values()) {
				TileEntity te = level.getBlockEntity(pos.relative(direction));
				if (te != null) {
					boolean doContinue = te.getCapability(CapabilityEnergy.ENERGY, direction).map(handler -> {
						if (handler.canReceive()) {
							int received = handler.receiveEnergy(Math.min(capacity.get(), 1000), false);
							capacity.addAndGet(-received);
							storage.consumeEnergy(received);
							onChanged.run();
							return capacity.get() > 0;
						} else {
							return true;
						}
					}).orElse(true);
					if (!doContinue) {
						return;
					}
				}
			}
		}
	}
}