package com.machina.block.menu;

import org.checkerframework.checker.nullness.qual.NonNull;

import com.machina.api.block.MachineBlock;
import com.machina.api.block.menu.MachinaContainerMenu;
import com.machina.api.block.menu.slot.AcceptSlot;
import com.machina.block.tile.machine.FurnaceGeneratorBlockEntity;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.MenuTypeInit;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;

public class FurnaceGeneratorMenu extends MachinaContainerMenu<FurnaceGeneratorBlockEntity> {

	private static final int EXTRA_DATA_SIZE = 1;

	public FurnaceGeneratorMenu(int id, Inventory inv, @NonNull FriendlyByteBuf buf) {
		this(id, clientLevel(), buf.readBlockPos(), inv, new SimpleContainer(1), defaultData(EXTRA_DATA_SIZE));
	}

	public FurnaceGeneratorMenu(int id, Level level, BlockPos pos, Inventory inv, Container access, ContainerData data) {
		super(MenuTypeInit.FURNACE_GENERATOR.get(), level, pos, id, access, data);

		this.addSlot(new AcceptSlot(container, 0, -2, 74, s -> ForgeHooks.getBurnTime(s, RecipeType.SMELTING) > 0));

		invSlots(inv, 0);
	}

	@Override
	protected MachineBlock getBlock() {
		return BlockInit.FURNACE_GENERATOR.get();
	}

	@Override
	protected int getExtraDataSize() {
		return EXTRA_DATA_SIZE;
	}
}
