package com.machina.registration.init;

import com.machina.api.starchart.planet_biome.TreeMaker;
import com.machina.world.feature.tree.AcaciaTree;
import com.machina.world.feature.tree.ArchTree;
import com.machina.world.feature.tree.ConeTree;
import com.machina.world.feature.tree.DeadRadialBaobabTree;
import com.machina.world.feature.tree.FirTree;
import com.machina.world.feature.tree.InvertedMushroomTree;
import com.machina.world.feature.tree.LollipopTree;
import com.machina.world.feature.tree.RadialBaobabTree;
import com.machina.world.feature.tree.SmallFirTree;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class PlanetTreeInit {
	public static final DeferredRegister<TreeMaker> TREES = RegistryInit.TREES;

	//@formatter:off
	public static final RegistryObject<RadialBaobabTree> RADIAL_BAOBAB = TREES.register("radial_baobab", RadialBaobabTree::new);
	public static final RegistryObject<DeadRadialBaobabTree> DEAD_RADIAL_BAOBAB = TREES.register("dead_radial_baobab", DeadRadialBaobabTree::new);
	public static final RegistryObject<ArchTree> ARCH = TREES.register("arch", ArchTree::new);
	public static final RegistryObject<FirTree> FIR = TREES.register("fir", FirTree::new);
	public static final RegistryObject<SmallFirTree> SMALL_FIR = TREES.register("small_fir", SmallFirTree::new);
	public static final RegistryObject<ConeTree> CONE = TREES.register("cone", ConeTree::new);
	public static final RegistryObject<LollipopTree> LOLLIPOP = TREES.register("lollipop", LollipopTree::new);
	public static final RegistryObject<AcaciaTree> ACACIA = TREES.register("acacia", AcaciaTree::new);
	public static final RegistryObject<InvertedMushroomTree> INVERTED_MUSHROOM = TREES.register("inverted_mushroom", InvertedMushroomTree::new);
	//@formatter:on
}
