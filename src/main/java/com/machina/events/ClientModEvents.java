package com.machina.events;

import com.machina.Machina;
import com.machina.api.client.ClientTimer;
import com.machina.api.client.cinema.CinematicHandler;
import com.machina.api.client.cinema.effect.renderer.CinematicTextOverlay;
import com.machina.api.client.cinema.effect.renderer.CinematicTextureOverlay;
import com.machina.api.util.reflect.ClassHelper;
import com.machina.client.PlanetSpecialEffects;
import com.machina.client.screen.menu.BatteryScreen;
import com.machina.client.screen.menu.CreativeBatteryScreen;
import com.machina.client.screen.menu.FurnaceGeneratorScreen;
import com.machina.client.screen.menu.GrinderScreen;
import com.machina.client.screen.menu.MachineCaseScreen;
import com.machina.registration.init.BlockEntityInit;
import com.machina.registration.init.FluidInit;
import com.machina.registration.init.FluidInit.FluidObject;
import com.machina.registration.init.KeyBindingInit;
import com.machina.registration.init.MenuTypeInit;
import com.machina.world.PlanetFactory;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Machina.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event) {
		ClientTimer.setup();
		CinematicHandler.setup();

		FluidInit.setRenderLayers();

		event.enqueueWork(() -> {
			MenuScreens.register(MenuTypeInit.BATTERY.get(), BatteryScreen::new);
			MenuScreens.register(MenuTypeInit.CREATIVE_BATTERY.get(), CreativeBatteryScreen::new);
			MenuScreens.register(MenuTypeInit.MACHINE_CASE.get(), MachineCaseScreen::new);
			MenuScreens.register(MenuTypeInit.FURNACE_GENERATOR.get(), FurnaceGeneratorScreen::new);
			MenuScreens.register(MenuTypeInit.GRINDER.get(), GrinderScreen::new);
		});
	}

	@SubscribeEvent
	public static void registerKeys(RegisterKeyMappingsEvent event) {
		ClassHelper.<KeyMapping>doWithStatics(KeyBindingInit.class, (name, map) -> event.register(map));
	}

	@SubscribeEvent
	public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
		event.registerAboveAll("cinematic_overlay",
				(gui, graphics, partialTick, width, height) -> CinematicTextureOverlay.renderOverlay());
		event.registerAboveAll("cinematic_title", (gui, graphics, partialTick, width, height) -> CinematicTextOverlay
				.renderOverlay(graphics, graphics.pose(), width, height));
	}

	@SubscribeEvent
	public static void itemColors(RegisterColorHandlersEvent.Item event) {
		ItemColors colors = event.getItemColors();

		for (FluidObject obj : FluidInit.OBJS) {
			colors.register((stack, index) -> index == 1 ? obj.chem().getColor() : -1, obj.bucket());
		}
	}

	@SubscribeEvent
	public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
		event.register(PlanetFactory.TYPE_KEY.location(), new PlanetSpecialEffects());
	}

	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(BlockEntityInit.SIGN.get(), SignRenderer::new);
		event.registerBlockEntityRenderer(BlockEntityInit.HANGING_SIGN.get(), HangingSignRenderer::new);
	}
}