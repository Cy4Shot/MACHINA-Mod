package com.machina.api.cap.sided;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public enum Side {
	NONE(false, false),
	OUTPUT(false, true),
	INPUT(true, false);

	public static final Side[] NONES = new Side[] { Side.NONE, Side.NONE, Side.NONE, Side.NONE, Side.NONE, Side.NONE };
	public static final Side[] INPUTS = new Side[] { Side.INPUT, Side.INPUT, Side.INPUT, Side.INPUT, Side.INPUT,
			Side.INPUT };
	public static final Side[] OUTPUTS = new Side[] { Side.OUTPUT, Side.OUTPUT, Side.OUTPUT, Side.OUTPUT, Side.OUTPUT,
			Side.OUTPUT };

	private final boolean input;
	private final boolean output;

	Side(boolean input, boolean output) {
		this.input = input;
		this.output = output;
	}

	public boolean isInput() {
		return input;
	}

	public boolean isOutput() {
		return output;
	}

	public String getSerializedName() {
		return name().toLowerCase();
	}

	public static CompoundTag serialize(Side[] sides) {
		CompoundTag tag = new CompoundTag();
		ListTag list = new ListTag();
		for (Side side : sides) {
			list.add(StringTag.valueOf(side.getSerializedName()));
		}
		tag.put("sides", list);
		return tag;
	}

	public static Side[] deserialize(CompoundTag tag) {
		ListTag list = tag.getList("sides", 8);
		Side[] sides = new Side[list.size()];
		for (int i = 0; i < list.size(); i++) {
			sides[i] = Side.valueOf(list.getString(i).toUpperCase());
		}
		return sides;
	}
}