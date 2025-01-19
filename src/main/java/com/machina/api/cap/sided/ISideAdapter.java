package com.machina.api.cap.sided;

import net.minecraft.core.Direction;

public interface ISideAdapter {
	public abstract Side get(Direction d);
	public abstract void cycle(Direction d);
}
