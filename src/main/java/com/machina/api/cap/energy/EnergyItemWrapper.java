package com.machina.api.cap.energy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.machina.api.item.EnergyItem;

import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class EnergyItemWrapper implements IEnergyStorage, ICapabilityProvider {

	private final LazyOptional<IEnergyStorage> holder = LazyOptional.of(() -> this);

	@NotNull
	protected ItemStack container;

	public EnergyItemWrapper(@NotNull ItemStack container) {
		this.container = container;
	}

	@Override
	@NotNull
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction facing) {
		return ForgeCapabilities.ENERGY.orEmpty(cap, holder);
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int energy = getEnergyStored();
		int received = Math.min(getMaxEnergyStored() - getEnergyStored(), maxReceive);
		if (received > 0 && !simulate) {
			if (!setEnergyStored(energy + received))
				return 0;
		}
		if (received > 0) {
			maxReceive -= received;
		}
		return received;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
		return EnergyItem.getEnergy(this.container);
	}

	public boolean setEnergyStored(int energy) {
		return EnergyItem.setEnergy(this.container, energy);
	}

	@Override
	public int getMaxEnergyStored() {
		Item item = this.container.getItem();
		if (item instanceof EnergyItem) {
			return ((EnergyItem) item).getMaxEnergy();
		}
		return 0;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return true;
	}
}
