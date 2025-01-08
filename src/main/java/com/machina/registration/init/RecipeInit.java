package com.machina.registration.init;

import java.util.ArrayList;
import java.util.List;

import com.machina.Machina;
import com.machina.api.recipe.MachinaRecipe;
import com.machina.api.recipe.MachinaRecipe.MachinaRecipeSerializer;
import com.machina.api.recipe.MachinaRecipe.RecipeFactory;
import com.machina.api.recipe.MachinaRecipeMaps;
import com.machina.api.recipe.MachinaRecipeType;
import com.machina.api.util.MachinaRL;
import com.machina.block.tile.machine.GrinderBlockEntity;
import com.machina.recipe.GrinderRecipe;
import com.machina.recipe.maps.GrinderRecipeMaps;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeInit {
	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister
			.create(ForgeRegistries.RECIPE_TYPES, Machina.MOD_ID);
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
			.create(ForgeRegistries.RECIPE_SERIALIZERS, Machina.MOD_ID);
	public static final List<MachinaRecipeMaps<?>> MAPS = new ArrayList<>();

	public static final RecipeRegistryObject<GrinderBlockEntity> GRINDER = register("grinder", MachinaRecipe.HAS_ENERGY,
			GrinderRecipe::new, GrinderRecipeMaps.INSTANCE);

	public static record RecipeRegistryObject<C extends Container>(ResourceLocation id,
			RegistryObject<MachinaRecipeType<C>> type, RegistryObject<RecipeSerializer<MachinaRecipe<C>>> serializer) {
	}

	private static <C extends Container> RecipeRegistryObject<C> register(String name, short flags,
			RecipeFactory<MachinaRecipe<C>> factory, MachinaRecipeMaps<?> map) {
		ResourceLocation id = new MachinaRL(name);
		RegistryObject<MachinaRecipeType<C>> type = RECIPE_TYPES.register(name,
				() -> new MachinaRecipeType<>(id, flags));
		RegistryObject<RecipeSerializer<MachinaRecipe<C>>> serializer = RECIPE_SERIALIZERS.register(name,
				() -> new MachinaRecipeSerializer<C>(type::get, factory));
		MAPS.add(map);
		return new RecipeRegistryObject<C>(id, type, serializer);
	}
}
