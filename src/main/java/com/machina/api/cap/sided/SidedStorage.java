package com.machina.api.cap.sided;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

public abstract class SidedStorage {

	public Side[] modes;
	protected String tag;

	public SidedStorage(String tag, Side[] d) {
		this.tag = tag;
		this.modes = d;
	}

	public boolean isNonNullMode(Direction dir) {
		return this.modes[dir.ordinal()] != Side.NONE;
	}

	public boolean isInput(Direction from) {
		return this.modes[from.ordinal()] == Side.INPUT;
	}

	public abstract void invalidate();

	protected abstract void save(CompoundTag tag);

	protected abstract void load(CompoundTag tag);

	public void saveAdditional(CompoundTag tag) {
		CompoundTag storageTag = new CompoundTag();
		this.save(storageTag);
		tag.put(this.tag, storageTag);
	}

	public void loadAdditional(CompoundTag tag2) {
		if (tag2.contains(this.tag)) {
			CompoundTag storageTag = tag2.getCompound(this.tag);
			this.load(storageTag);
		}
	}

	public byte[] getRawSideData() {
		byte[] raw = new byte[6];
		for (int i = 0; i < 6; ++i) {
			raw[i] = (byte) modes[i].ordinal();
		}
		return raw;
	}
}
