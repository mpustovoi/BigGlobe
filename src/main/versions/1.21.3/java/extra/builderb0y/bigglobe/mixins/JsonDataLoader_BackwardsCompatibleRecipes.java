package builderb0y.bigglobe.mixins;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.recipe.Recipe;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.util.Identifier;

import builderb0y.bigglobe.BigGlobeMod;

@Mixin(JsonDataLoader.class)
public class JsonDataLoader_BackwardsCompatibleRecipes {

	@ModifyExpressionValue(method = "load", at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonParser;parseReader(Ljava/io/Reader;)Lcom/google/gson/JsonElement;"))
	private static JsonElement bigglobe_portRecipes(
		JsonElement original,
		@Local(argsOnly = true) Codec<?> codec,
		@Local(index = 9) Identifier id
	) {
		if (
			codec == Recipe.CODEC &&
			id.getNamespace().equals(BigGlobeMod.MODID) &&
			original instanceof JsonObject root &&
			root.get("type") instanceof JsonPrimitive type &&
			type.isString()
		) {
			switch (type.getAsString()) {
				case
					"crafting_shaped",
					"crafting_shapeless",
					"smithing_transform",
					"minecraft:crafting_shaped",
					"minecraft:crafting_shapeless",
					"minecraft:smithing_transform"
				-> {
					if (
						root.get("result") instanceof JsonObject result &&
						result.get("item") instanceof JsonPrimitive item &&
						item.isString()
					) {
						result.add("id", item);
						result.remove("item");
					}
					else {
						BigGlobeMod.LOGGER.warn("Unexpected format in crafting or smithing recipe " + id);
					}
				}
				case
					"stonecutting",
					"smelting",
					"blasting",
					"minecraft:stonecutting",
					"minecraft:smelting",
					"minecraft:blasting"
				-> {
					if (root.get("result") instanceof JsonPrimitive result && result.isString()) {
						JsonObject newResult = new JsonObject();
						newResult.add("id", result);
						if (root.get("count") instanceof JsonPrimitive count && count.isNumber()) {
							newResult.add("count", count);
							root.remove("count");
						}
						root.remove("result");
						root.add("result", newResult);
					}
					else {
						BigGlobeMod.LOGGER.warn("Unexpected format in stonecutting or smelting recipe " + id);
					}
				}
				case "bigglobe:scripted" -> {}
				default -> {
					BigGlobeMod.LOGGER.warn("Unknown recipe type: " + type.getAsString());
				}
			}
		}
		return original;
	}
}