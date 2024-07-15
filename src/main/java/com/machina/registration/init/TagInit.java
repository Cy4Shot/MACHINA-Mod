package com.machina.registration.init;

import java.util.ArrayList;
import java.util.List;

import com.machina.api.util.MachinaRL;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagInit {

	public static class BlockTagInit {
		
		public static final List<TagKey<Block>> GROWABLES = new ArrayList<>();
		public static final List<TagKey<Block>> CARVABLES = new ArrayList<>();

		public static final TagKey<Block> EARTHLIKE_GROWABLE = growable("earthlike");
		public static final TagKey<Block> ANTHRACITE_GROWABLE = growable("anthracite");
		public static final TagKey<Block> MARTIAN_GROWABLE = growable("martian");
		
		public static final TagKey<Block> EARTHLIKE_CARVABLE = carvable("earthlike");
		public static final TagKey<Block> ANTHRACITE_CARVABLE = carvable("anthracite");
		public static final TagKey<Block> MARTIAN_CARVABLE = carvable("martian");

		private static TagKey<Block> growable(String name) {
			TagKey<Block> t = create("planet_growable_" + name);
			GROWABLES.add(t);
			return t;
		}
		
		private static TagKey<Block> carvable(String name) {
			TagKey<Block> t = create("planet_carvable_" + name);
			CARVABLES.add(t);
			return t;
		}
		
		private static TagKey<Block> create(String name) {
			return TagKey.create(Registries.BLOCK, new MachinaRL(name));
		}
	}
}