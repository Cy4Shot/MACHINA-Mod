package com.machina.api.cap.sided;

import com.machina.api.block.tile.MachinaBlockEntity;
import com.machina.api.network.PacketSender;
import com.machina.api.network.c2s.C2SSideConfig;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;

public enum Side {
	NONE(false, false, 407, 81), OUTPUT(false, true, 423, 81), INPUT(true, false, 415, 81);

	public static final Side[] NONES = new Side[] { Side.NONE, Side.NONE, Side.NONE, Side.NONE, Side.NONE, Side.NONE };
	public static final Side[] INPUTS = new Side[] { Side.INPUT, Side.INPUT, Side.INPUT, Side.INPUT, Side.INPUT,
			Side.INPUT };
	public static final Side[] OUTPUTS = new Side[] { Side.OUTPUT, Side.OUTPUT, Side.OUTPUT, Side.OUTPUT, Side.OUTPUT,
			Side.OUTPUT };

	private final boolean input;
	private final boolean output;
	private final int tx;
	private final int ty;

	Side(boolean input, boolean output, int tx, int ty) {
		this.input = input;
		this.output = output;
		this.tx = tx;
		this.ty = ty;
	}

	public boolean isInput() {
		return input;
	}

	public boolean isOutput() {
		return output;
	}

	public int x() {
		return this.tx;
	}

	public int y() {
		return this.ty;
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

	public static void cycle(Side[] input, Direction d, MachinaBlockEntity e, String tag) {
		input[d.ordinal()] = values()[(input[d.ordinal()].ordinal() + 1) % values().length];

		if (e.getLevel().isClientSide()) {
			PacketSender.sendToServer(new C2SSideConfig(tag, e.getBlockPos(), getRaw(input)));
		} else {
			e.setChanged();
		}
	}

	public static byte[] getRaw(Side[] input) {
		byte[] raw = new byte[6];
		for (int i = 0; i < 6; i++) {
			raw[i] = (byte) input[i].ordinal();
		}
		return raw;
	}

	public static void fromRaw(Side[] input, byte[] raw) {
		if (raw.length == 6) {
			for (int i = 0; i < 6; i++) {
				input[i] = Side.values()[raw[i]];
			}
		}
	}
}