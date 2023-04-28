package com.machina.block.tile;

import com.machina.block.container.BlueprinterContainer;
import com.machina.block.container.base.IMachinaContainerProvider;
import com.machina.block.tile.base.CustomTE;
import com.machina.blueprint.Blueprint;
import com.machina.capability.CustomItemStorage;
import com.machina.item.BlueprintItem;
import com.machina.registration.init.BlueprintInit;
import com.machina.registration.init.ItemInit;
import com.machina.registration.init.TileEntityInit;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;

public class BlueprinterTileEntity extends CustomTE implements IMachinaContainerProvider {

	public BlueprinterTileEntity(TileEntityType<?> type) {
		super(type);
	}

	public BlueprinterTileEntity() {
		this(TileEntityInit.BLUEPRINTER.get());
	}
	
	CustomItemStorage items;

	@Override
	public void createStorages() {
		items = add(new CustomItemStorage(2));
	}

	public void etch(String id) {
		items.getStackInSlot(0).shrink(1);
		ItemStack stack = ItemInit.BLUEPRINT.get().getDefaultInstance();
		BlueprintItem.set(stack, BlueprintInit.BLUEPRINTS.getOrDefault(id, Blueprint.EMPTY));
		items.setStackInSlot(1, stack);
	}

	@Override
	public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
		return new BlueprinterContainer(id, inventory, this);
	}
}