package builderb0y.bigglobe.mixins;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import builderb0y.bigglobe.blocks.BigGlobeBlocks.VanillaBlocks;
import builderb0y.bigglobe.versions.IdentifierVersions;

@Mixin(Items.class)
public class Items_PlaceableFlint {

	#if MC_VERSION >= MC_1_21_2

		@Redirect(
			method = "<clinit>",
			at     = @At(
				value  = "INVOKE",
				target = "Lnet/minecraft/item/Items;register(Ljava/lang/String;)Lnet/minecraft/item/Item;"
			),
			slice = @Slice(
				from = @At(value = "CONSTANT", args   = "stringValue=flint"),
				to   = @At(value = "FIELD",    target = "Lnet/minecraft/item/Items;FLINT:Lnet/minecraft/item/Item;", opcode = Opcodes.PUTSTATIC)
			)
		)
		private static Item bigglobe_makeSticksPlaceable(String name) {
			return Items.register(
				RegistryKey.of(RegistryKeys.ITEM, IdentifierVersions.vanilla(name)),
				(Item.Settings settings) -> new BlockItem(VanillaBlocks.FLINT, settings)
			);
		}

	#else

		@Redirect(
			method = "<clinit>",
			at     = @At(
				value  = "NEW",
				target = "(Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;"
			),
			slice = @Slice(
				from = @At(value = "CONSTANT", args   = "stringValue=flint"),
				to   = @At(value = "FIELD",    target = "Lnet/minecraft/item/Items;FLINT:Lnet/minecraft/item/Item;", opcode = Opcodes.PUTSTATIC)
			)
		)
		private static Item bigglobe_makeSticksPlaceable(Item.Settings settings) {
			return new BlockItem(VanillaBlocks.FLINT, settings);
		}

	#endif
}