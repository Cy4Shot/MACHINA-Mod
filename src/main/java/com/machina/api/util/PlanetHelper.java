package com.machina.api.util;

import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.obj.Planet;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;

public class PlanetHelper {
	public static int getIdDim(ResourceKey<LevelStem> dim) {
		return Integer.valueOf(dim.location().getPath());
	}

	public static int getIdLevel(ResourceKey<Level> dim) {
		return Integer.valueOf(dim.location().getPath());
	}

	public static boolean isPlanetDim(ResourceKey<LevelStem> dim) {
		try {
			getIdDim(dim);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isPlanetLevel(ResourceKey<Level> dim) {
		try {
			getIdLevel(dim);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static Planet getPlanetFor(ServerLevel level) {
		ResourceKey<Level> dim = level.dimension();
		if (isPlanetLevel(dim)) {
			return Starchart.system(level).planets().get(getIdLevel(dim));
		}
		return null;
	}
}
