package com.machina.api.item;

import org.jetbrains.annotations.Nullable;

import com.machina.api.cap.energy.EnergyItemWrapper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public abstract class EnergyItem extends Item {

	private static final float BAR = (float) Item.MAX_BAR_WIDTH;

	public EnergyItem(Properties props) {
		super(props);
	}

	public abstract int getMaxEnergy();

	public static int getEnergy(ItemStack stack) {
		if (stack.getItem() instanceof EnergyItem) {
			CompoundTag nbt = stack.getTagElement("energy");
			if (nbt != null && nbt.contains("energy")) {
				return nbt.getInt("energy");
			}
		}
		return 0;
	}

	public static boolean setEnergy(ItemStack stack, int energy) {
		if (stack.getItem() instanceof EnergyItem) {
			CompoundTag nbt = new CompoundTag();
			nbt.putInt("energy", energy);
			stack.addTagElement("energy", nbt);
		}
		return false;
	}

	private float getEnergyProp(ItemStack stack) {
		float max = (float) getMaxEnergy();
		float stored = (float) getEnergy(stack);
		return stored / max;
	}

	@Override
	public int getMaxStackSize(ItemStack stack) {
		return 1;
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
		return new EnergyItemWrapper(stack);
	}

	@Override
	public ItemStack getDefaultInstance() {
		ItemStack stack = new ItemStack(this);
		setEnergy(stack, 0);
		return stack;
	}

	@Override
	public boolean isBarVisible(ItemStack stack) {
		return true;
	}

	@Override
	public int getBarWidth(ItemStack stack) {
		return Math.round(BAR - BAR * (1f - getEnergyProp(stack)));
	}

	@Override
	public int getBarColor(ItemStack stack) {
		return 0x00fefe;
	}
}
