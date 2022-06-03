package com.machina.item.container;

import com.machina.registration.init.ContainerTypesInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class ScannerContainer extends Container {
	
	private RegistryKey<World> dim;

	public ScannerContainer(int windowId, RegistryKey<World> key) {
		super(ContainerTypesInit.SCANNER_CONTAINER.get(), windowId);
		this.dim = key;
	}

	@Override
	public boolean stillValid(PlayerEntity pPlayer) {
		return true;
	}

	public RegistryKey<World> getDim() {
		return dim;
	}

}