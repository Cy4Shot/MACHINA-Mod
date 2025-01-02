package com.machina.block.menu;

import com.machina.api.container.MachinaContainerMenu;
import com.machina.block.menu.slot.AcceptSlot;
import com.machina.block.tile.MachineCaseBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.MenuTypeInit;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class MachineCaseMenu extends MachinaContainerMenu<MachineCaseBlockEntity> {

	public MachineCaseMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, inv, ContainerLevelAccess.NULL);
	}

	public MachineCaseMenu(int id, Inventory inv, ContainerLevelAccess access) {
		super(MenuTypeInit.MACHINE_CASE.get(), id, access);

		this.addSlot(new AcceptSlot(be, 0, -2, 74, s -> s.getItem().equals(Items.DIAMOND)));

		invSlots(inv, 0);
	}

	@Override
	protected Block getBlock() {
		return BlockInit.BASIC_MACHINE_CASE.get();
	}

	@Override
	protected int getContainerSize() {
		return 1;
	}
}
