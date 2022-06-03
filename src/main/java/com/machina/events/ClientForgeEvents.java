package com.machina.events;

import com.machina.Machina;
import com.machina.client.ClientStarchart;
import com.machina.client.screen.ComponentAnalyzerScreen;
import com.machina.client.screen.DevPlanetCreationScreen;
import com.machina.client.screen.ShipConsoleScreen;
import com.machina.client.screen.StarchartScreen;
import com.machina.registration.init.KeyBindingsInit;
import com.machina.registration.init.PlanetAttributeTypesInit;
import com.machina.util.color.Color;
import com.machina.util.server.PlanetUtils;
import com.machina.world.data.PlanetData;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Machina.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void fogSetup(FogColors event) {
		RegistryKey<World> dim = Minecraft.getInstance().level.dimension();
		if (PlanetUtils.isDimensionPlanet(dim)) {
			PlanetData data = ClientStarchart.getPlanetData(PlanetUtils.getId(dim));
			Color color = data.getAttribute(PlanetAttributeTypesInit.PALETTE)[4];
			Float density = data.getAttribute(PlanetAttributeTypesInit.FOG_DENSITY);
			event.setRed(color.getRed() / 255f * density);
			event.setGreen(color.getGreen() / 255f * density);
			event.setBlue(color.getBlue() / 255f * density);
		}
	}
	
	@SubscribeEvent
	public static void clientTick(ClientTickEvent event) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.screen != null || mc.level == null) { return; }

		if (KeyBindingsInit.isKeyDown(KeyBindingsInit.DEV_PLANET_CREATION_SCREEN)) {
			mc.setScreen(new DevPlanetCreationScreen());
		}

		if (KeyBindingsInit.isKeyDown(KeyBindingsInit.STARCHART)) {
			mc.setScreen(new StarchartScreen());
		}
	}
	
	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void drawTooltipEvent(RenderTooltipEvent.Color event) {
		Screen s = Minecraft.getInstance().screen;
		if (s instanceof ShipConsoleScreen || s instanceof ComponentAnalyzerScreen) {
			event.setBackground(0xFF_232323);
			event.setBorderEnd(0xFF_1bcccc);
			event.setBorderStart(0xFF_00fefe);
		}
	}
}
