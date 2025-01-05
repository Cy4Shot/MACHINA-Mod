package com.machina.block.menu;

import com.machina.api.block.MachineBlock;
import com.machina.api.menu.MachinaContainerMenu;
import com.machina.api.menu.slot.AcceptSlot;
import com.machina.block.tile.machine.BatteryBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.ItemInit;
import com.machina.registration.init.MenuTypeInit;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class BatteryMenu extends MachinaContainerMenu<BatteryBlockEntity> {

	private static final int EXTRA_DATA_SIZE = 0;

	public BatteryMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, inv, new SimpleContainer(1), defaultData(EXTRA_DATA_SIZE));
	}

	public BatteryMenu(int id, Inventory inv, Container access, ContainerData data) {
		super(MenuTypeInit.BATTERY.get(), id, access, data);

		this.addSlot(new AcceptSlot(container, 0, -2, 74, s -> s.getItem().equals(ItemInit.BLUEPRINT.get())));

		invSlots(inv, 0);
	}

	@Override
	protected MachineBlock getBlock() {
		return BlockInit.BATTERY.get();
	}

	@Override
	protected int getExtraDataSize() {
		return EXTRA_DATA_SIZE;
	}
}
