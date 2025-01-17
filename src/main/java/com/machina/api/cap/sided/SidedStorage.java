package com.machina.api.cap.sided;

import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.api.network.PacketSender;
import com.machina.api.network.c2s.C2SSideConfig;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

public abstract class SidedStorage {

	public Side[] modes;
	protected String tag;
	protected MachinaBlockEntity e;

	public SidedStorage(String tag, MachinaBlockEntity e, Side[] d) {
		this.tag = tag;
		this.e = e;
		this.modes = d;
	}

	public boolean isNonNullMode(Direction dir) {
		return this.modes[dir.ordinal()] != Side.NONE;
	}

	public boolean isInput(Direction from) {
		return this.modes[from.ordinal()] == Side.INPUT;
	}

	public void cycleMode(Direction dir) {
		this.modes[dir.ordinal()] = Side.values()[(this.modes[dir.ordinal()].ordinal() + 1) % Side.values().length];
		this.update();
	}
	
	protected void update() {
		if (this.e.getLevel().isClientSide()) {
			PacketSender.sendToServer(new C2SSideConfig(tag, this.e.getBlockPos(), getRawSideData()));
		} else {
			this.e.setChanged();
		}
	}

	public abstract void invalidate();

	protected abstract void save(CompoundTag tag);

	protected abstract void load(CompoundTag tag);

	public void saveAdditional(CompoundTag tag) {
		CompoundTag storageTag = new CompoundTag();
		this.save(storageTag);
		tag.put(this.tag, storageTag);
		tag.putByteArray("sides_" + this.tag, getRawSideData());
	}

	public void loadAdditional(CompoundTag tag2) {
		if (tag2.contains(this.tag)) {
			CompoundTag storageTag = tag2.getCompound(this.tag);
			this.load(storageTag);
		}
		if (tag2.contains("sides_" + this.tag)) {
			setRawSideData(tag2.getByteArray("sides_" + this.tag));
		}
	}

	public void setRawSideData(byte[] side_data) {
		if (side_data.length == 6) {
			for (int i = 0; i < 6; i++) {
				modes[i] = Side.values()[side_data[i]];
			}
		}
	}

	public byte[] getRawSideData() {
		byte[] raw = new byte[6];
		for (int i = 0; i < 6; i++) {
			raw[i] = (byte) modes[i].ordinal();
		}
		return raw;
	}
}
