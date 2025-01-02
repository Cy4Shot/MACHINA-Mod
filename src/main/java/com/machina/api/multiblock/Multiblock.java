package com.machina.api.multiblock;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.machina.api.util.loader.JsonInfo;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.RegistryLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Multiblock {
	public Vec3i size;
	public Vec3i controller_pos;
	public Map<String, List<BlockState>> map;
	public Map<String, BlockState> renderMap;
	public List<BlockState> allowed;
	public Set<Block> allowedBlock;
	public String[][][] structure;

	public class MultiblockJsonInfo implements JsonInfo<Multiblock> {
		public List<Integer> size;
		public Map<String, List<String>> blocks;
		public List<List<String>> structure;

		public Multiblock cast() {
			Multiblock mb = new Multiblock();
			mb.size = new Vec3i(size.get(0), size.get(1), size.get(2));
			mb.map = blocks.entrySet().stream()
					.collect(Collectors.toMap(Entry::getKey, s -> s.getValue().stream().map(b -> {
						try {
							return parse(b);
						} catch (CommandSyntaxException e) {
							e.printStackTrace();
							return Blocks.AIR.defaultBlockState();
						}
					}).collect(Collectors.toList())));

			mb.structure = structure.stream()
					.map(l1 -> l1.stream().map(l2 -> l2.split("(?!^)")).toArray(String[][]::new))
					.toArray(String[][][]::new);

			mb.controller_pos = null;
			for (int x = 0; x < mb.size.getX(); x++) {
				for (int y = 0; y < mb.size.getY(); y++) {
					for (int z = 0; z < mb.size.getZ(); z++) {
						if (mb.structure[x][y][z].equals("!")) {
							mb.controller_pos = new Vec3i(x, y, z);
							break;
						}
					}
				}
			}

			if (mb.controller_pos == null) {
				throw new IllegalArgumentException("No controller found in structure");
			}

			mb.allowed = mb.map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
			mb.allowedBlock = mb.allowed.stream().map(b -> b.getBlock()).collect(Collectors.toSet());
			return mb;
		}
	}

	private static BlockState parse(String value) throws CommandSyntaxException {
		return BlockStateParser
				.parseForBlock(RegistryLayer.createRegistryAccess().compositeAccess().lookup(Registries.BLOCK).get(),
						value, true)
				.blockState();
	}

	public BlockState getRenderAtPos(Vec3i pos) {
		try {
			BlockState bs = renderMap.get(structure[pos.getX()][pos.getY()][pos.getZ()]);
			return bs == null ? Blocks.AIR.defaultBlockState() : bs;
		} catch (IndexOutOfBoundsException e) {
			return Blocks.AIR.defaultBlockState();
		}
	}

	@Override
	public String toString() {
		return "Multiblock {" + "\n\t size = " + size.toString() + "\n\t map = "
				+ Joiner.on(",").withKeyValueSeparator("=")
						.join(map.entrySet().stream().collect(Collectors.toMap(Entry::getKey,
								s -> s.getValue().stream().map(b -> b.toString()).collect(Collectors.toList()))))
				+ "\n\t structure = " + Arrays.deepToString(structure) + '}';
	}
}