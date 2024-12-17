package com.machina.registration.init;

import java.util.function.Supplier;

import com.machina.Machina;
import com.machina.api.starchart.planet_biome.RockMaker;
import com.machina.api.starchart.planet_biome.TreeMaker;
import com.machina.api.util.MachinaRL;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public class RegistryInit {
	public static final DeferredRegister<TreeMaker> TREES = DeferredRegister.create(new MachinaRL("tree"),
			Machina.MOD_ID);
	public static final DeferredRegister<RockMaker> ROCKS = DeferredRegister.create(new MachinaRL("rock"),
			Machina.MOD_ID);

	public static final Supplier<IForgeRegistry<TreeMaker>> TREE_REGISTRY = TREES.makeRegistry(RegistryBuilder::new);
	public static final Supplier<IForgeRegistry<RockMaker>> ROCK_REGISTRY = ROCKS.makeRegistry(RegistryBuilder::new);
}
