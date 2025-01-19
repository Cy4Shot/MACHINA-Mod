package com.machina.registration.init;

import com.machina.Machina;
import com.machina.block.menu.BatteryMenu;
import com.machina.block.menu.CreativeBatteryMenu;
import com.machina.block.menu.FurnaceGeneratorMenu;
import com.machina.block.menu.GrinderMenu;
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
	public static final RegistryObject<MenuType<BatteryMenu>> BATTERY = register("battery",
            BatteryMenu::new);
	public static final RegistryObject<MenuType<CreativeBatteryMenu>> CREATIVE_BATTERY = register("creative_battery",
            CreativeBatteryMenu::new);
	public static final RegistryObject<MenuType<MachineCaseMenu>> MACHINE_CASE = register("machine_case",
			MachineCaseMenu::new);
	public static final RegistryObject<MenuType<FurnaceGeneratorMenu>> FURNACE_GENERATOR = register("furnace_generator",
            FurnaceGeneratorMenu::new);
	public static final RegistryObject<MenuType<GrinderMenu>> GRINDER = register("grinder",
            GrinderMenu::new);
	//@formatter:on

	private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String n,
			IContainerFactory<T> sup) {
		return MENU_TYPES.register(n, () -> IForgeMenuType.create(sup));
	}
}
