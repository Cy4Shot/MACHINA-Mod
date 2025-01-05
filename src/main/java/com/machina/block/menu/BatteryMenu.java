package com.machina.block.menu;

import com.machina.api.menu.MachinaContainerMenu;
import com.machina.api.menu.slot.AcceptSlot;
import com.machina.block.tile.machine.BatteryBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.ItemInit;
import com.machina.registration.init.MenuTypeInit;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.Block;

public class BatteryMenu extends MachinaContainerMenu<BatteryBlockEntity> {

	public BatteryMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, inv, ContainerLevelAccess.NULL);
	}

	public BatteryMenu(int id, Inventory inv, ContainerLevelAccess access) {
		super(MenuTypeInit.BATTERY.get(), id, access);

		this.addSlot(new AcceptSlot(be, 0, -2, 74, s -> s.getItem().equals(ItemInit.BLUEPRINT.get())));

		invSlots(inv, 0);
	}

	@Override
	protected Block getBlock() {
		return BlockInit.BATTERY.get();
	}

	@Override
	protected int getContainerSize() {
		return 1;
	}
}
