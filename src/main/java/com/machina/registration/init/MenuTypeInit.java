package com.machina.registration.init;

import com.machina.Machina;
import com.machina.block.menu.MachineCaseMenu;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuTypeInit {
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES,
			Machina.MOD_ID);

	//@formatter:off
	public static final RegistryObject<MenuType<MachineCaseMenu>> MACHINE_CASE = register("machine_case",
			MachineCaseMenu::new);
	//@formatter:on

	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String n,
			IContainerFactory<T> sup) {
		return MENU_TYPES.register(n, () -> IForgeMenuType.create(sup));
	}
}
