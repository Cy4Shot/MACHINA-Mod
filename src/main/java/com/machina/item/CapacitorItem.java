package com.machina.item;

import java.util.List;
import java.util.function.Supplier;

import com.machina.api.item.EnergyItem;
import com.machina.api.util.StringUtils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

public class CapacitorItem extends EnergyItem {

	private final Supplier<ForgeConfigSpec.IntValue> capacity;

	public CapacitorItem(Properties props, Supplier<ForgeConfigSpec.IntValue> capacity) {
		super(props);
		this.capacity = capacity;
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> tooltip, TooltipFlag flag) {
		tooltip.add(
				Component.literal(StringUtils.formatPower(getMaxEnergy())).setStyle(Style.EMPTY.withColor(0x00FEFE)));
		super.appendHoverText(stack, level, tooltip, flag);
	}

	@Override
	public int getMaxEnergy() {
		return this.capacity.get().get();
	}
}