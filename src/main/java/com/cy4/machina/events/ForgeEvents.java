package com.cy4.machina.events;

import com.cy4.machina.Machina;
import com.cy4.machina.api.capability.trait.CapabilityPlanetTrait;
import com.cy4.machina.api.events.LivingEntityAddEffectEvent;
import com.cy4.machina.api.planet.PlanetUtils;
import com.cy4.machina.init.PlanetTraitInit;
import com.cy4.machina.world.data.StarchartData;

import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = Machina.MOD_ID, bus = Bus.FORGE)
public class ForgeEvents {

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(Machina.traitPoolManager);
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void debug(ItemTossEvent event) {
		if (event.getPlayer().level instanceof ServerWorld) {
			StarchartData.getDefaultInstance(ServerLifecycleHooks.getCurrentServer()).starchart.debugStarchart();
		}
	}

	@SubscribeEvent
	public static void onWorldLoaded(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getPlayer().level.isClientSide()) {
			CapabilityPlanetTrait.syncCapabilityWithClients(event.getPlayer().level);
		}
	}

	@SubscribeEvent
	public static void handleEffectBan(LivingEntityAddEffectEvent event) {
		World level = event.getEntity().level;
		if (PlanetUtils.isDimensionPlanet(level.dimension())) {
			if (CapabilityPlanetTrait.worldHasTrait(level, PlanetTraitInit.SUPERHOT)
					&& event.getEffect().getEffect() == Effects.FIRE_RESISTANCE) {
				event.setCanceled(true);
			}
		}
	}
}
