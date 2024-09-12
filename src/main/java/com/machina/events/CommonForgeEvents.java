package com.machina.events;

import com.machina.Machina;
import com.machina.api.starchart.Starchart;
import com.machina.api.starchart.planet_biome.PlanetBiomeLoader;
import com.machina.registration.init.BlockFamiliesInit;
import com.machina.registration.init.BlockFamiliesInit.WoodFamily;
import com.machina.registration.init.ItemInit;
import com.machina.registration.init.JsonLoaderInit;
import com.machina.world.PlanetRegistrationHandler;
import com.machina.world.biome.PlanetBiome;
import com.machina.world.data.PlanetDimensionData;
import com.mojang.serialization.Lifecycle;

import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Machina.MOD_ID, bus = Bus.FORGE)
public class CommonForgeEvents {

	@SubscribeEvent
	public static void addReloadListeners(AddReloadListenerEvent event) {
		JsonLoaderInit.registerAll(event);
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

	@SubscribeEvent
	public static void serverAboutToStart(final ServerAboutToStartEvent event) {
		MinecraftServer server = event.getServer();
		PlanetBiomeLoader.INSTANCE.getEntrySet().forEach(e -> {
			registerBiome(server, e.getKey(), new PlanetBiome(e.getValue()));
		});
	}

	private static void registerBiome(MinecraftServer server, ResourceLocation loc, PlanetBiome biome) {
		ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, loc);
		Registry<Biome> dimRegFrozen = server.registryAccess().registryOrThrow(Registries.BIOME);
		if (dimRegFrozen.containsKey(key)) {
			return;
		}
		if (dimRegFrozen instanceof MappedRegistry<Biome> biomeReg) {
			biomeReg.unfreeze();
			biomeReg.register(key, biome, Lifecycle.stable());
		} else {
			throw new IllegalStateException(
					String.format("Unable to register dimension %s -- dimension registry not writable", loc));
		}
	}

	@SubscribeEvent
	public static void blockToolModification(BlockEvent.BlockToolModificationEvent event) {
		ToolAction action = event.getToolAction();
		BlockState state = event.getState();
		if (!event.isSimulated()) {
			if (action == ToolActions.AXE_STRIP) {
				for (WoodFamily family : BlockFamiliesInit.WOODS) {
					if (state.is(family.log())) {
						event.setFinalState(family.stripped_log().withPropertiesOf(state));
						return;
					}

					if (state.is(family.wood())) {
						event.setFinalState(family.stripped_wood().withPropertiesOf(state));
						return;
					}
				}
			}
		}
	}
}
