package com.machina.block.tile;

import com.machina.block.container.AtmosphericSeperatorContainer;
import com.machina.block.container.base.IMachinaContainerProvider;
import com.machina.block.tile.base.BaseTileEntity;
import com.machina.registration.init.AttributeInit;
import com.machina.registration.init.TileEntityInit;
import com.machina.util.server.ServerHelper;
import com.machina.world.data.PlanetData;
import com.machina.world.data.StarchartData;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;

public class AtmosphericSeperatorTileEntity extends BaseTileEntity implements IMachinaContainerProvider {

	public int selected = 0;
	public float rate = 0;

	protected final IIntArray data = new IIntArray() {
		public int get(int index) {
			switch (index) {
			case 0:
				return selected;
			default:
				return 0;
			}
		}

		public void set(int index, int value) {
			switch (index) {
			case 0:
				selected = value;
				break;
			}
		}

		@Override
		public int getCount() {
			return 1;
		}
	};

	public AtmosphericSeperatorTileEntity() {
		super(TileEntityInit.ATMOSPHERIC_SEPERATOR.get());
	}

	public void setId(int id) {
		this.selected = id;
		PlanetData data = StarchartData.getDataOrNone(ServerHelper.server(), this.level.dimension());
		Double atm = data.getAttribute(AttributeInit.ATMOSPHERE)[id];
		this.rate = 0.05f * (float) atm.doubleValue();
		this.sync();
	}

	@Override
	public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
		return new AtmosphericSeperatorContainer(id, this);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		compound.putInt("selected", this.selected);
		compound.putFloat("rate", this.rate);
		return super.save(compound);
	}

	@Override
	public void load(BlockState state, CompoundNBT compound) {
		this.selected = compound.getInt("selected");
		this.rate = compound.getFloat("rate");
		super.load(state, compound);
	}

	public IIntArray getData() {
		return this.data;
	}

}
