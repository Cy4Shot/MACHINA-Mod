package com.machina.block.menu;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.menu.MachinaContainerMenu;
import com.machina.api.block.menu.slot.AcceptSlot;
import com.machina.block.tile.machine.MachineCaseBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.ItemInit;
import com.machina.registration.init.MenuTypeInit;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;

public class MachineCaseMenu extends MachinaContainerMenu<MachineCaseBlockEntity> {

	private static final int EXTRA_DATA_SIZE = 1;

	public MachineCaseMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, inv, new SimpleContainer(1), defaultData(EXTRA_DATA_SIZE));
	}

	public MachineCaseMenu(int id, Inventory inv, Container access, ContainerData data) {
		super(MenuTypeInit.MACHINE_CASE.get(), id, access, data);

		this.addSlot(new AcceptSlot(container, 0, -2, 74, s -> s.getItem().equals(ItemInit.BLUEPRINT.get())));

		invSlots(inv, 0);
	}

	@Override
	protected MachineBlock getBlock() {
		return BlockInit.BASIC_MACHINE_CASE.get();
	}

	@Override
	protected int getExtraDataSize() {
		return EXTRA_DATA_SIZE;
	}
}
