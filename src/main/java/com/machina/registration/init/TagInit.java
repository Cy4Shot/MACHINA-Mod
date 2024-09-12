package com.machina.registration.init;

import com.machina.api.util.MachinaRL;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagInit {

	public static class BlockTagInit {

		public static final TagKey<Block> PLANET_GROWABLE = create("planet_growable");
		public static final TagKey<Block> PLANET_CARVABLE = create("planet_carvable");

		private static TagKey<Block> create(String name) {
			return TagKey.create(Registries.BLOCK, new MachinaRL(name));
		}
	}
}