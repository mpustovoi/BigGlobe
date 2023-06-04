package builderb0y.bigglobe.recipes;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import builderb0y.bigglobe.items.BallOfStringItem;
import builderb0y.bigglobe.items.BigGlobeItems;

public class BallOfStringAddRecipe extends SpecialCraftingRecipe {

	public static final TagKey<Item> STRING = TagKey.of(Registry.ITEM_KEY, new Identifier("c", "string"));

	public BallOfStringAddRecipe(Identifier id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingInventory inventory, World world) {
		boolean haveBall = false, haveString = false;
		for (int slot = 0, size = inventory.size(); slot < size; slot++) {
			ItemStack stack = inventory.getStack(slot);
			if (stack.isEmpty()) {
				continue;
			}
			else if (stack.isIn(STRING)) {
				haveString = true;
			}
			else if (stack.isOf(BigGlobeItems.BALL_OF_STRING)) {
				if (haveBall) return false;
				else haveBall = true;
			}
			else {
				return false;
			}
		}
		return haveBall & haveString;
	}

	@Override
	public ItemStack craft(CraftingInventory inventory) {
		ItemStack ball = ItemStack.EMPTY;
		int string = 0;
		for (int slot = 0, size = inventory.size(); slot < size; slot++) {
			ItemStack stack = inventory.getStack(slot);
			if (stack.isEmpty()) {
				continue;
			}
			else if (stack.isIn(STRING)) {
				string++;
			}
			else if (stack.isOf(BigGlobeItems.BALL_OF_STRING)) {
				if (ball.isEmpty()) ball = stack;
				else return ItemStack.EMPTY;
			}
			else {
				return ItemStack.EMPTY;
			}
		}
		ball = ball.copy();
		if (string > 0) {
			BallOfStringItem.addString(ball, string);
		}
		return ball;
	}

	@Override
	public boolean fits(int width, int height) {
		return width * height > 1;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return BigGlobeRecipeSerializers.BALL_OF_STRING_ADD;
	}
}