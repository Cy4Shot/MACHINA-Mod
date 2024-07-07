package com.machina.events;

import com.machina.Machina;
import com.machina.api.starchart.Starchart;
import com.machina.registration.Registration;
import com.machina.registration.init.ItemInit;
import com.machina.world.PlanetRegistrationHandler;
import com.machina.world.data.PlanetDimensionData;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Machina.MOD_ID, bus = Bus.FORGE)
public class CommonForgeEvents {

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		event.addListener(Registration.MULTIBLOCK_LOADER);
	}

	@SubscribeEvent
	public static void onPlayerLogin(final PlayerLoggedInEvent e) {
		if (e.getEntity().level().isClientSide())
			return;

		Starchart.syncClient((ServerPlayer) e.getEntity());
	}

	@SubscribeEvent
	public static void onDebug(final ItemTossEvent event) {
		int id = 0;
		if (!event.getPlayer().level().isClientSide()) {
			ServerLevel planet = PlanetRegistrationHandler.createPlanet(event.getPlayer().getServer(), id);
			event.getPlayer().sendSystemMessage(
					Component.literal("Sending to: " + Starchart.system(planet).planets().get(id).name()));
			PlanetRegistrationHandler.sendPlayerToDimension((ServerPlayer) event.getPlayer(), planet,
					new BlockPos(0, 100, 0));
		}
	}

	@SubscribeEvent
	public static void getBurnTime(final FurnaceFuelBurnTimeEvent event) {
		if (event.getItemStack().getItem().equals(ItemInit.COAL_CHUNK.get())) {
			event.setBurnTime(ForgeHooks.getBurnTime(new ItemStack(Items.COAL), event.getRecipeType()) / 9);
		}
	}

	@SubscribeEvent
	public static void serverStart(final LevelEvent.Load event) {
		if (event.getLevel().isClientSide()) {
			return;
		}
		MinecraftServer server = event.getLevel().getServer();
		if (server.levelKeys().size() > 1) {
			return;
		}
		PlanetDimensionData.getDefaultInstance(server).updateSeed(((ServerLevel) event.getLevel()).getSeed());
	}
}
