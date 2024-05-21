package com.machina.mixin;

import java.io.File;
import java.util.Map.Entry;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.machina.api.client.ClientStarchart;
import com.machina.api.starchart.obj.Planet;
import com.machina.api.starchart.obj.SolarSystem;
import com.machina.api.util.MachinaRL;
import com.machina.world.biome.PlanetBiome;
import com.machina.world.biome.PlanetBiomeRegistrationHandler;
import com.machina.world.data.PlanetDimensionData;
import com.mojang.serialization.DynamicOps;

import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.WorldLoader.DataLoadContext;
import net.minecraft.server.WorldLoader.DataLoadOutput;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;

@Mixin(WorldOpenFlows.class)
public class WorldOpenFlowsMixin {

	// Highly dodgy code!
	@Inject(method = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;lambda$loadWorldStem$1(Lnet/minecraft/world/level/storage/LevelStorageSource$LevelStorageAccess;Lnet/minecraft/server/WorldLoader$DataLoadContext;)Lnet/minecraft/server/WorldLoader$DataLoadOutput;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/WorldLoader$DataLoadContext;datapackDimensions()Lnet/minecraft/core/RegistryAccess$Frozen;"))
	private static <D> void loadWorldStem$1(LevelStorageSource.LevelStorageAccess access, DataLoadContext ctx,
			CallbackInfoReturnable<DataLoadOutput<D>> ci, @Local LocalRef<DynamicOps<Tag>> ref) {
		RegistryAccess.Frozen worldgen = ctx.datapackWorldgen();
		Registry<Biome> biomes = worldgen.registryOrThrow(Registries.BIOME);
		File file1 = access.getDimensionPath(Level.OVERWORLD).resolve("data").toFile();
		file1.mkdirs();
		DimensionDataStorage storage = new DimensionDataStorage(file1, DataFixers.getDataFixer());
		PlanetDimensionData data = PlanetDimensionData.getDefaultInstance(storage);
		long seed = data.lastKnownSeed;
		ClientStarchart.sync(seed);
		SolarSystem system = ClientStarchart.system;
		if (biomes instanceof MappedRegistry<Biome> biomeReg) {
			biomeReg.unfreeze();
			for (Entry<Integer, Set<Integer>> e : data.ids.entrySet()) {
				Planet p = system.planets().get(e.getKey());
				for (Integer b : e.getValue()) {
					String bid = String.format("%d_%d", e.getKey(), b);
					ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, new MachinaRL(bid));
					PlanetBiomeRegistrationHandler.register(key, () -> new PlanetBiome(p, b, seed), biomeReg);
				}
			}
		} else {
			throw new IllegalStateException("Unable to register biome -- biome registry not writable");
		}
		ref.set(RegistryOps.create(NbtOps.INSTANCE, worldgen));
	}
}
