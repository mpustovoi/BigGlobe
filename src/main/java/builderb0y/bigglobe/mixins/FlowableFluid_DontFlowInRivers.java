package builderb0y.bigglobe.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import builderb0y.bigglobe.blocks.RiverWaterBlock;

@Mixin(FlowableFluid.class)
public class FlowableFluid_DontFlowInRivers {

	@Inject(method = "onScheduledTick", at = @At("HEAD"), cancellable = true)
	private void bigglobe_dontFlowInRivers(
		#if MC_VERSION >= MC_1_21_2
			ServerWorld world,
			BlockPos pos,
			BlockState blockState,
			FluidState fluidState,
			CallbackInfo callback
		#else
			World world,
			BlockPos pos,
			FluidState state,
			CallbackInfo callback
		#endif
	) {
		if (world.getBlockState(pos).getBlock() instanceof RiverWaterBlock) {
			callback.cancel();
		}
	}
}