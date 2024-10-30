package builderb0y.bigglobe.versions;

import net.minecraft.item.ItemStack;

@SuppressWarnings("UnnecessaryFullyQualifiedName")
public class ActionResultVersions {

	#if MC_VERSION >= MC_1_21_2

		public static final net.minecraft.util.ActionResult
			SUCCESS = net.minecraft.util.ActionResult.SUCCESS,
			CONSUME = net.minecraft.util.ActionResult.CONSUME,
			PASS    = net.minecraft.util.ActionResult.PASS,
			FAIL    = net.minecraft.util.ActionResult.FAIL,

			ITEM_SUCCESS = net.minecraft.util.ActionResult.SUCCESS,
			ITEM_CONSUME = net.minecraft.util.ActionResult.CONSUME,
			ITEM_PASS    = net.minecraft.util.ActionResult.PASS,
			ITEM_FAIL    = net.minecraft.util.ActionResult.FAIL;

		public static net.minecraft.util.ActionResult typedSuccess(ItemStack stack) {
			return net.minecraft.util.ActionResult.SUCCESS.withNewHandStack(stack);
		}

		public static net.minecraft.util.ActionResult typedConsume(ItemStack stack) {
			return net.minecraft.util.ActionResult.CONSUME.withNewHandStack(stack);
		}

		public static net.minecraft.util.ActionResult typePass(ItemStack stack) {
			return net.minecraft.util.ActionResult.PASS;
		}

		public static net.minecraft.util.ActionResult typedFail(ItemStack stack) {
			return net.minecraft.util.ActionResult.FAIL;
		}

	#elif MC_VERSION >= MC_1_20_5

		public static final net.minecraft.util.ActionResult
			SUCCESS = net.minecraft.util.ActionResult.SUCCESS,
			CONSUME = net.minecraft.util.ActionResult.CONSUME,
			PASS    = net.minecraft.util.ActionResult.PASS,
			FAIL    = net.minecraft.util.ActionResult.FAIL;

		public static final net.minecraft.util.ItemActionResult
			ITEM_SUCCESS = net.minecraft.util.ItemActionResult.SUCCESS,
			ITEM_CONSUME = net.minecraft.util.ItemActionResult.CONSUME,
			ITEM_PASS    = net.minecraft.util.ItemActionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION,
			ITEM_FAIL    = net.minecraft.util.ItemActionResult.FAIL;

		public static net.minecraft.util.TypedActionResult<ItemStack> typedSuccess(ItemStack stack) {
			return net.minecraft.util.TypedActionResult.success(stack);
		}

		public static net.minecraft.util.TypedActionResult<ItemStack> typedConsume(ItemStack stack) {
			return net.minecraft.util.TypedActionResult.consume(stack);
		}

		public static net.minecraft.util.TypedActionResult<ItemStack> typePass(ItemStack stack) {
			return net.minecraft.util.TypedActionResult.pass(stack);
		}

		public static net.minecraft.util.TypedActionResult<ItemStack> typedFail(ItemStack stack) {
			return net.minecraft.util.TypedActionResult.fail(stack);
		}

	#else

		public static final net.minecraft.util.ActionResult
			SUCCESS = net.minecraft.util.ActionResult.SUCCESS,
			CONSUME = net.minecraft.util.ActionResult.CONSUME,
			PASS    = net.minecraft.util.ActionResult.PASS,
			FAIL    = net.minecraft.util.ActionResult.FAIL,

			ITEM_SUCCESS = net.minecraft.util.ActionResult.SUCCESS,
			ITEM_CONSUME = net.minecraft.util.ActionResult.CONSUME,
			ITEM_PASS    = net.minecraft.util.ActionResult.PASS,
			ITEM_FAIL    = net.minecraft.util.ActionResult.FAIL;

		public static net.minecraft.util.TypedActionResult<ItemStack> typedSuccess(ItemStack stack) {
			return net.minecraft.util.TypedActionResult.success(stack);
		}

		public static net.minecraft.util.TypedActionResult<ItemStack> typedConsume(ItemStack stack) {
			return net.minecraft.util.TypedActionResult.consume(stack);
		}

		public static net.minecraft.util.TypedActionResult<ItemStack> typePass(ItemStack stack) {
			return net.minecraft.util.TypedActionResult.pass(stack);
		}

		public static net.minecraft.util.TypedActionResult<ItemStack> typedFail(ItemStack stack) {
			return net.minecraft.util.TypedActionResult.fail(stack);
		}

	#endif
}