package com.machina.api.menu;

import com.machina.api.block.MachineBlock;
import com.machina.api.menu.slot.InvSlot;
import com.machina.api.tile.MachinaBlockEntity;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public abstract class MachinaContainerMenu<T extends MachinaBlockEntity> extends AbstractContainerMenu {

	private final ContainerLevelAccess access;
	private final ContainerData data;
	protected final Container be;

	public MachinaContainerMenu(MenuType<?> type, int id, ContainerLevelAccess access, ContainerData data) {
		super(type, id);

		checkContainerDataCount(data, getExtraDataSize() + MachinaBlockEntity.DEFAULT_DATA_SIZE);

		this.access = access;
		this.data = data;
		this.be = new SimpleContainer(getContainerSize());
	}

	protected static ContainerData defaultData(int extra) {
		return new SimpleContainerData(extra + MachinaBlockEntity.DEFAULT_DATA_SIZE);
	}

	protected abstract MachineBlock getBlock();

	protected abstract int getContainerSize();

	protected abstract int getExtraDataSize();

	public int getEnergy() {
		return this.data.get(0);
	}

	public int getMaxEnergy() {
		return this.data.get(1);
	}

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
