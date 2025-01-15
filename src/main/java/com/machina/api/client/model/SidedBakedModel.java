package com.machina.api.client.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.ChunkRenderTypeSet;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.IDynamicBakedModel;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;

// From CoFH Core
public class SidedBakedModel extends BakedModelWrapper<BakedModel> implements IDynamicBakedModel {

	public static final ModelProperty<byte[]> SIDES = new ModelProperty<>();

	private static final Int2ObjectMap<BakedQuad[]> SIDE_QUAD_CACHE = new Int2ObjectOpenHashMap<>();

	public static void clearCache() {
		SIDE_QUAD_CACHE.clear();
	}

	public SidedBakedModel(BakedModel original) {
		super(original);
	}

	@Override
	public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side,
			@NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType) {
		LinkedList<BakedQuad> quads = new LinkedList<>(
				originalModel.getQuads(state, side, rand, extraData, renderType));
		if (side == null || quads.isEmpty()) {
			return quads;
		}

		BakedQuad quad = quads.get(0);
//		quads.clear();
//		quads.add(new BakedQuad(quad.getVertices(), quad.getTintIndex(), side, ModelLoader.MACHINE_FACE_INPUT,
//				quad.isShade(), quad.hasAmbientOcclusion()));
		
		
		int sideIndex = side.get3DDataValue();

		byte[] sideConfigRaw = extraData.get(SIDES);
		if (sideConfigRaw == null) {
			return quads;
		}

		int configHash = Arrays.hashCode(sideConfigRaw);
		BakedQuad[] cachedSideQuads = SIDE_QUAD_CACHE.get(configHash);
		if (cachedSideQuads == null || cachedSideQuads.length < 6) {
			cachedSideQuads = new BakedQuad[6];
		}
		if (cachedSideQuads[sideIndex] == null) {
			cachedSideQuads[sideIndex] = new RetexturedBakedQuad(quad, side,
					getConfigTexture(sideConfigRaw[sideIndex]));
			SIDE_QUAD_CACHE.put(configHash, cachedSideQuads);
		}
//		quads.add(cachedSideQuads[sideIndex]);

		return quads;
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
		return List.of();
	}

	private TextureAtlasSprite getConfigTexture(byte side) {
		return ModelLoader.MACHINE_FACE_INPUT;
//		switch (side) {
//		case 1:
//			return ModelLoader.MACHINE_FACE_OUTPUT;
//		case 2:
//			return ModelLoader.MACHINE_FACE_INPUT;
//		default:
//			return ModelLoader.MACHINE_FACE_NONE;
//		}
	}
}
