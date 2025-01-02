package com.machina.api.menu;

import com.machina.api.menu.slot.InvSlot;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class MachinaContainerMenu<T extends BlockEntity> extends AbstractContainerMenu {

	private final ContainerLevelAccess access;
	protected final Container be;

	public MachinaContainerMenu(MenuType<?> type, int id, ContainerLevelAccess access) {
		super(type, id);

		this.access = access;
		this.be = new SimpleContainer(getContainerSize());
	}

	protected abstract Block getBlock();

	protected abstract int getContainerSize();

	@Override
	public boolean stillValid(Player player) {
		return stillValid(this.access, player, getBlock());
	}

	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack stack1 = slot.getItem();
			stack = stack1.copy();
			if (index < getContainerSize()
					&& !this.moveItemStackTo(stack1, getContainerSize(), this.slots.size(), true)) {
				return ItemStack.EMPTY;
			}
			if (!this.moveItemStackTo(stack1, 0, getContainerSize(), false)) {
				return ItemStack.EMPTY;
			}

			if (stack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		return stack;
	}

	public void invSlots(Inventory inv, int offset) {
		for (int l = 0; l < 3; ++l) {
			for (int j1 = 0; j1 < 9; ++j1) {
				this.addSlot(new InvSlot(inv, j1 + l * 9 + 9, 8 + j1 * 20, 103 + l * 20 + offset));
			}
		}

		for (int i1 = 0; i1 < 9; ++i1) {
			this.addSlot(new InvSlot(inv, i1, 8 + i1 * 20, 168 + offset));
		}
	}

}