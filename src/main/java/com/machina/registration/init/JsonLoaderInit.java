package com.machina.registration.init;

import java.util.ArrayList;
import java.util.List;

import com.machina.api.multiblock.MultiblockLoader;
import com.machina.api.starchart.planet_biome.PlanetBiomeLoader;
import com.machina.api.starchart.planet_type.PlanetTypeLoader;
import com.machina.api.util.loader.JsonLoader;

import net.minecraftforge.event.AddReloadListenerEvent;

public class JsonLoaderInit {

	private static List<JsonLoader<?>> LOADERS = new ArrayList<>();

	static {
		LOADERS.add(MultiblockLoader.INSTANCE);
		LOADERS.add(PlanetTypeLoader.INSTANCE);
		LOADERS.add(PlanetBiomeLoader.INSTANCE);
	}

	public static void registerAll(final AddReloadListenerEvent e) {
		LOADERS.forEach(e::addListener);
	}
}