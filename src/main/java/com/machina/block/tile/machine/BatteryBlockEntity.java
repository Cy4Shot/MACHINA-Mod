package com.machina.block.tile.machine;

import java.util.function.BiFunction;

import org.jetbrains.annotations.NotNull;

import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.api.cap.sided.Side;
import com.machina.api.client.model.SidedBakedModel;
import com.machina.api.item.EnergyItem;
import com.machina.api.util.block.BlockHelper;
import com.machina.api.util.reflect.QuadFunction;
import com.machina.block.machine.BatteryBlock;
import com.machina.block.menu.BatteryMenu;
import com.machina.item.CapacitorItem;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public class BatteryBlockEntity extends MachinaBlockEntity {
	int prev = -1;

	public BatteryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public BatteryBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityInit.BATTERY.get(), pos, state);
	}

	@Override
	public void createStorages() {
		energyStorage(Side.INPUTS);
		itemStorage(Side.NONES);
		itemStorage(Side.NONES);
		itemStorage(Side.NONES);
	}

	@Override
	public void tick() {		
		int energy = getEnergy();
		if (energy != prev) {
			this.prev = energy;
			this.setChanged();
		}

		BlockHelper.sendEnergy(level, worldPosition, energy, 1_000, this);

		this.level.setBlock(worldPosition, getBlockState().setValue(BatteryBlock.LIT, energy > 0), 3);

		super.tick();
	}

	@Override
	protected QuadFunction<Integer, Level, BlockPos, Inventory, AbstractContainerMenu> createMenu() {
		return BatteryMenu::new;
	}

	private <T> T doWithCapacitor(BiFunction<ItemStack, CapacitorItem, T> func, T def) {
		ItemStack cap = getItem(0);
		if (cap.isEmpty() || !(cap.getItem() instanceof CapacitorItem))
			return def;

		return func.apply(cap, (CapacitorItem) cap.getItem());
	}

	@Override
	public int getMaxEnergy() {
		return doWithCapacitor((s, i) -> i.getMaxEnergy(), 0);
	}

	@Override
	public int getEnergy() {
		return doWithCapacitor((s, i) -> EnergyItem.getEnergy(s), 0);
	}

	@Override
	protected void setEnergy(int n) {
		doWithCapacitor((s, i) -> EnergyItem.setEnergy(s, n), false);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (side != null) {
			if (cap == ForgeCapabilities.ENERGY) {
				if (energyCap.isNonNullMode(side)) {
					return doWithCapacitor((s, i) -> s.getCapability(cap, side), super.getCapability(cap, side));
				}
			}
		}

		return super.getCapability(cap, side);
	}

	@Override
	public @NotNull ModelData getModelData() {
		return ModelData.builder().with(SidedBakedModel.SIDES, getSideConfig(energyCap)).build();
	}

	@Override
	public boolean activeModel() {
		return true;
	}
}
