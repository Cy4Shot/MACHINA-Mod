package com.machina.block.menu;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.menu.MachinaContainerMenu;
import com.machina.api.block.menu.slot.AcceptSlot;
import com.machina.block.tile.machine.BatteryBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.MenuTypeInit;
import com.machina.registration.init.TagInit.ItemTagInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;

public class BatteryMenu extends MachinaContainerMenu<BatteryBlockEntity> {
	public BatteryMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, clientLevel(), buf.readBlockPos(), inv);
	}

	public BatteryMenu(int id, Level level, BlockPos pos, Inventory inv) {
		super(MenuTypeInit.BATTERY.get(), level, pos, id);

		this.addSlot(new AcceptSlot(be, 0, 107, -57, s -> s.is(ItemTagInit.CAPACITOR)));

		invSlots(inv, 0);
	}

	@Override
	protected MachineBlock getBlock() {
		return BlockInit.BATTERY.get();
	}
}