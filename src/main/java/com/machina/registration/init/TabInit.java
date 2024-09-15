package com.machina.registration.init;

import java.util.List;
import java.util.function.Consumer;

import com.machina.Machina;
import com.machina.registration.init.FluidInit.FluidObject;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabInit {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, Machina.MOD_ID);

	public static final RegistryObject<CreativeModeTab> MACHINA_RESOURCES = create("machina_resources",
			ItemInit.AMMONIUM_NITRATE, a -> {
				add(a, ItemInit.ALUMINUM_NUGGET);
				add(a, ItemInit.ALUMINUM_INGOT);
				add(a, BlockInit.ALUMINUM_BLOCK);
				add(a, ItemInit.RAW_ALUMINUM);

				add(a, ItemInit.COAL_CHUNK);

				add(a, ItemInit.SILICON);
				add(a, ItemInit.RAW_SILICON_BLEND);
				add(a, ItemInit.SILICON_BOLUS);
				add(a, ItemInit.HIGH_PURITY_SILICON);

				add(a, ItemInit.LDPE);
				add(a, ItemInit.HDPE);
				add(a, ItemInit.UHMWPE);

				add(a, ItemInit.AMMONIUM_NITRATE);
				add(a, ItemInit.CALCIUM_SULPHATE);
				add(a, ItemInit.HEXAMINE);
				add(a, ItemInit.NITRONIUM_TETRAFLUOROBORATE);
				add(a, ItemInit.PALLADIUM_CHLORIDE);
				add(a, ItemInit.PALLADIUM_ON_CARBON);
				add(a, ItemInit.SODIUM_CARBONATE);
				add(a, ItemInit.SODIUM_HYDROXIDE);

				for (FluidObject e : FluidInit.OBJS) {
					add(a, e.bucket());
				}
			});

	public static final RegistryObject<CreativeModeTab> MACHINA_WORLDGEN = create("machina_worldgen",
			BlockInit.TROPICAL_GRASS_BLOCK, a -> {
				add(a, BlockFamiliesInit.DIRTS);

				add(a, BlockInit.ALUMINUM_ORE);
				add(a, BlockFamiliesInit.STONES);

				add(a, BlockFamiliesInit.WOODS);

				add(a, BlockInit.CLOVER);
				add(a, BlockInit.ORPHEUM);
				add(a, BlockInit.DRAGON_PEONY);
				add(a, BlockInit.PURPLE_GLOWSHROOM);
				add(a, BlockInit.PINK_GLOWSHROOM);
				add(a, BlockInit.RED_GLOWSHROOM);
				add(a, BlockInit.ORANGE_GLOWSHROOM);
				add(a, BlockInit.YELLOW_GLOWSHROOM);
				add(a, BlockInit.GREEN_GLOWSHROOM);
				add(a, BlockInit.TURQUOISE_GLOWSHROOM);
				add(a, BlockInit.BLUE_GLOWSHROOM);

				add(a, ItemInit.TAMA_SPORE);
				add(a, ItemInit.STRAPPLE);
				add(a, ItemInit.ARGO_BERRY);
				add(a, ItemInit.Y2_NANA);
				add(a, ItemInit.AVA_FRUIT);
				add(a, ItemInit.GRELP_BERRY);
				add(a, ItemInit.SPARR_BALL);
				add(a, ItemInit.ERBI_POD);
			});

	public static final RegistryObject<CreativeModeTab> MACHINA_MISCELLANEOUS = create("machina_misc",
			ItemInit.LOGIC_UNIT, a -> {
				add(a, ItemInit.COPPER_COIL);
				add(a, ItemInit.LOGIC_UNIT);
				add(a, ItemInit.PROCESSOR);
				add(a, ItemInit.PROCESSOR_CORE);
				add(a, ItemInit.TRANSISTOR);
			});

	public static final void add(CreativeModeTab.Output adder, ItemLike item) {
		adder.accept(item);
	}

	public static final void add(CreativeModeTab.Output adder, RegistryObject<? extends ItemLike> item) {
		add(adder, item.get());
	}

	public static final void add(CreativeModeTab.Output adder, List<? extends BlockFamiliesInit.BlockFamily> family) {
		family.forEach(f -> f.tab().forEach(i -> add(adder, i)));
	}

	public static final RegistryObject<CreativeModeTab> create(String name, RegistryObject<? extends ItemLike> item,
			Consumer<CreativeModeTab.Output> gen) {
		return CREATIVE_MODE_TABS.register(name,
				() -> CreativeModeTab.builder().icon(() -> new ItemStack(item.get()))
						.title(Component.translatable(Machina.MOD_ID + ".creativemodetab." + name))
						.displayItems((p, a) -> gen.accept(a)).build());
	}
}
