package com.machina.block.menu;

import com.machina.api.block.MachineBlock;
import com.machina.api.menu.MachinaContainerMenu;
import com.machina.api.menu.slot.AcceptSlot;
import com.machina.block.tile.machine.FurnaceGeneratorBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.MenuTypeInit;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;

public class FurnaceGeneratorMenu extends MachinaContainerMenu<FurnaceGeneratorBlockEntity> {

	private static final int EXTRA_DATA_SIZE = 1;

	public FurnaceGeneratorMenu(int id, Inventory inv, FriendlyByteBuf buf) {
		this(id, inv, ContainerLevelAccess.NULL, defaultData(EXTRA_DATA_SIZE));
	}

	public FurnaceGeneratorMenu(int id, Inventory inv, ContainerLevelAccess access, ContainerData data) {
		super(MenuTypeInit.FURNACE_GENERATOR.get(), id, access, data);

		this.addSlot(new AcceptSlot(be, 0, -2, 74, s -> ForgeHooks.getBurnTime(s, RecipeType.SMELTING) > 0));

		invSlots(inv, 0);
	}

	@Override
	protected MachineBlock getBlock() {
		return BlockInit.FURNACE_GENERATOR.get();
	}

	@Override
	protected int getContainerSize() {
		return 1;
	}

	@Override
	protected int getExtraDataSize() {
		return EXTRA_DATA_SIZE;
	}
}
