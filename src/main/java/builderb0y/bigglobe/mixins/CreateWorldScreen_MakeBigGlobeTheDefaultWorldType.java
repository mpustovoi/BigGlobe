package builderb0y.bigglobe.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.gen.WorldPreset;

import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.config.BigGlobeConfig;

@Environment(EnvType.CLIENT)
@Mixin(CreateWorldScreen.class)
public class CreateWorldScreen_MakeBigGlobeTheDefaultWorldType {

	#if MC_VERSION >= MC_1_21_2
		@ModifyExpressionValue(method = "show(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/gui/screen/Screen;Lnet/minecraft/client/gui/screen/world/CreateWorldCallback;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/gen/WorldPresets;DEFAULT:Lnet/minecraft/registry/RegistryKey;"))
	#else
		@ModifyExpressionValue(method = "create(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/gui/screen/Screen;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/gen/WorldPresets;DEFAULT:Lnet/minecraft/registry/RegistryKey;"))
	#endif
	private static RegistryKey<WorldPreset> bigglobe_getDefaultWorldPreset(RegistryKey<WorldPreset> original) {
		return BigGlobeConfig.INSTANCE.get().makeBigGlobeDefaultWorldType ? BigGlobeMod.BIG_GLOBE_WORLD_PRESET_KEY : original;
	}
}