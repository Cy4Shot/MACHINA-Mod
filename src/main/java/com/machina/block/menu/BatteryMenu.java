package com.machina.block.menu;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.menu.MachinaContainerMenu;
import com.machina.api.block.menu.slot.AcceptSlot;
import com.machina.block.tile.machine.BatteryBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.ItemInit;
import com.machina.registration.init.MenuTypeInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;

public class BatteryMenu extends MachinaContainerMenu<BatteryBlockEntity> {
	public BatteryMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, clientLevel(), buf.readBlockPos(), inv, new SimpleContainer(1));
	}

	public BatteryMenu(int id, Level level, BlockPos pos, Inventory inv, Container access) {
		super(MenuTypeInit.BATTERY.get(), level, pos, id, access);

		this.addSlot(new AcceptSlot(container, 0, -2, 74, s -> s.getItem().equals(ItemInit.BLUEPRINT.get())));

		invSlots(inv, 0);
	}

	@Override
	protected MachineBlock getBlock() {
		return BlockInit.BATTERY.get();
	}
}
