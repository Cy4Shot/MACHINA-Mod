package com.machina.registration.init;

import com.machina.api.starchart.planet_biome.RockMaker;
import com.machina.world.feature.rock.BulbRock;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PlanetRockInit {
	public static final DeferredRegister<RockMaker> ROCKS = RegistryInit.ROCKS;

	//@formatter:off
	public static final RegistryObject<BulbRock> BULB = ROCKS.register("bulb", BulbRock::new);
	//@formatter:on
}
