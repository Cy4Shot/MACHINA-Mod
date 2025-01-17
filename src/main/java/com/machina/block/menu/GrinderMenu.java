package com.machina.block.menu;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.menu.MachinaContainerMenu;
import com.machina.api.block.menu.slot.InvSlot;
import com.machina.api.block.menu.slot.ResultSlot;
import com.machina.block.tile.machine.GrinderBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.MenuTypeInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;

public class GrinderMenu extends MachinaContainerMenu<GrinderBlockEntity> {

	public GrinderMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, clientLevel(), buf.readBlockPos(), inv, new SimpleContainer(2));
	}

	public GrinderMenu(int id, Level level, BlockPos pos, Inventory inv, Container access) {
		super(MenuTypeInit.GRINDER.get(), level, pos, id, access);

		this.addSlot(new InvSlot(container, 0, -2, 74));
		this.addSlot(new ResultSlot(container, 1, 134, 74));

		invSlots(inv, 0);
	}

	@Override
	protected MachineBlock getBlock() {
		return BlockInit.GRINDER.get();
	}
}
