package builderb0y.bigglobe.mixins;

import com.llamalad7.mixinextras.sugar.Local;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;

import builderb0y.bigglobe.chunkgen.BigGlobeScriptedChunkGenerator;

@Mixin(ServerWorld.class)
public abstract class ServerWorld_SpawnEnderDragonInBigGlobeWorlds extends World {

	@Shadow private @Nullable EnderDragonFight enderDragonFight;

	public ServerWorld_SpawnEnderDragonInBigGlobeWorlds() {
		#if MC_VERSION >= MC_1_21_2
			super(null, null, null, null, false, false, 0L, 0);
		#else
			super(null, null, null, null, null, false, false, 0L, 0);
		#endif
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void bigglobe_createEnderDragonFight(
		CallbackInfo callback,
		@Local(argsOnly = true) MinecraftServer server,
		@Local(argsOnly = true) DimensionOptions dimensionOptions
	) {
		if (this.enderDragonFight == null && dimensionOptions.chunkGenerator() instanceof BigGlobeScriptedChunkGenerator generator && generator.end_overrides != null) {
			this.enderDragonFight = new EnderDragonFight(
				(ServerWorld)(Object)(this),
				server.getSaveProperties().getGeneratorOptions().getSeed(),
				server.getSaveProperties().getDragonFight()
			);
		}
	}
}