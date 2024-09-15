package com.machina.registration.init;

import com.machina.api.starchart.planet_biome.TreeMaker;
import com.machina.world.feature.tree.ArchTree;
import com.machina.world.feature.tree.ConeTree;
import com.machina.world.feature.tree.FirTree;
import com.machina.world.feature.tree.LollipopTree;
import com.machina.world.feature.tree.RadialBaobabTree;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PlanetTreeInit {
	public static final DeferredRegister<TreeMaker> TREES = RegistryInit.TREES;

	//@formatter:off
	public static final RegistryObject<RadialBaobabTree> RADIAL_BAOBAB = TREES.register("radial_baobab", RadialBaobabTree::new);
	public static final RegistryObject<ArchTree> ARCH = TREES.register("arch", ArchTree::new);
	public static final RegistryObject<FirTree> FIR = TREES.register("fir", FirTree::new);
	public static final RegistryObject<ConeTree> CONE = TREES.register("cone", ConeTree::new);
	public static final RegistryObject<LollipopTree> LOLLIPOP = TREES.register("lollipop", LollipopTree::new);
	//@formatter:on
}
