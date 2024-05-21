package com.machina.world.biome;

import java.util.function.Supplier;

import com.machina.api.util.MachinaRL;
import com.machina.api.util.PlanetHelper;
import com.machina.world.data.PlanetDimensionData;
import com.mojang.serialization.Lifecycle;

import net.minecraft.core.Holder.Reference;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.LevelStem;

public class PlanetBiomeRegistrationHandler {

	public static Reference<Biome> getOrCreate(MinecraftServer server, Supplier<Biome> biome,
			ResourceKey<LevelStem> dim, int id) {
		int pid = PlanetHelper.getIdDim(dim);
		String bid = String.format("%d_%d", pid, id);
		PlanetDimensionData.getDefaultInstance(server).addBiome(pid, id);
		ResourceKey<Biome> key = ResourceKey.create(Registries.BIOME, new MachinaRL(bid));
		return server.registryAccess().registryOrThrow(Registries.BIOME).getHolder(key)
				.orElseGet(() -> register(server, key, biome));
	}

	private static Reference<Biome> register(MinecraftServer server, ResourceKey<Biome> key, Supplier<Biome> biome) {
		Registry<Biome> dimRegFrozen = server.registryAccess().registryOrThrow(Registries.BIOME);
		return register(key, biome, dimRegFrozen);
	}

	public static Reference<Biome> register(ResourceKey<Biome> key, Supplier<Biome> biome, Registry<Biome> bRegFrozen) {
		if (bRegFrozen instanceof MappedRegistry<Biome> biomeReg) {
			biomeReg.unfreeze();
			return biomeReg.register(key, biome.get(), Lifecycle.stable());
		} else {
			throw new IllegalStateException(String
					.format("Unable to register dimension %s -- dimension registry not writable", key.location()));
		}
	}
}
