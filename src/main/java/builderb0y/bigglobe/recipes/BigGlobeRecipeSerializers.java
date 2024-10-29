package builderb0y.bigglobe.recipes;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import builderb0y.bigglobe.BigGlobeMod;

public class BigGlobeRecipeSerializers {

	public static final RecipeSerializer<ScriptedRecipe> SCRIPTED = register("scripted", new ScriptedRecipeSerializer());

	public static void init() {}

	public static <T extends RecipeSerializer<?>> T register(String name, T serializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER, BigGlobeMod.modID(name), serializer);
	}
}