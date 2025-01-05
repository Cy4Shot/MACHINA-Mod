package com.machina.block.tile.machine;

import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.api.cap.sided.Side;
import com.machina.api.util.block.BlockHelper;
import com.machina.api.util.reflect.QuadFunction;
import com.machina.block.menu.FurnaceGeneratorMenu;
import com.machina.config.CommonConfig;
import com.machina.registration.init.BlockEntityInit;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public class FurnaceGeneratorBlockEntity extends MachinaBlockEntity {

	private int litTime;

	public FurnaceGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	public FurnaceGeneratorBlockEntity(BlockPos pos, BlockState state) {
		this(BlockEntityInit.FURNACE_GENERATOR.get(), pos, state);
	}

	@Override
	public void createStorages() {
		energyStorage(Side.OUTPUTS);
		itemStorage(Side.INPUTS);
	}

	public boolean isLit() {
		return this.litTime > 0;
	}

	@Override
	public void tick() {
		if (this.level.isClientSide())
			return;

		boolean flag = this.isLit();
		boolean flag1 = false;
		if (this.isLit())
			--this.litTime;
		ItemStack itemstack = getItem(0);
		if ((this.isLit() || !itemstack.isEmpty()) && !this.isEnergyFull()) {
			if (this.isLit()) {
				receiveEnergy(CommonConfig.furnaceGeneratorRate.get(), false);
				flag1 = true;
			} else {
				this.litTime = ForgeHooks.getBurnTime(itemstack, RecipeType.SMELTING);
				if (this.isLit()) {
					flag1 = true;
					if (itemstack.hasCraftingRemainingItem())
						this.setItem(0, itemstack.getCraftingRemainingItem());
					else if (!itemstack.isEmpty()) {
						itemstack.shrink(1);
						if (itemstack.isEmpty())
							this.setItem(0, itemstack.getCraftingRemainingItem());
					}
				}
			}
		}

		if (flag != this.isLit()) {
			flag1 = true;
		}

		BlockHelper.sendEnergy(level, worldPosition, getEnergy(), CommonConfig.furnaceGeneratorTransferRate.get(),
				this);

		if (flag1) {
			sync();

		}
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.putInt("litTime", litTime);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		this.litTime = tag.getInt("litTime");
	}

	@Override
	public int getMaxEnergy() {
		return CommonConfig.furnaceGeneratorCapacity.get();
	}

	@Override
	protected int getExtraDataSize() {
		return 1;
	}

	@Override
	protected QuadFunction<Integer, Inventory, Container, ContainerData, AbstractContainerMenu> createMenu() {
		return FurnaceGeneratorMenu::new;
	}
}
