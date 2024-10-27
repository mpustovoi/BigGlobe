package builderb0y.bigglobe.blocks;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import builderb0y.bigglobe.codecs.BigGlobeAutoCodec;

/**
this block exists to be generated in the nether, surrounded by magma blocks.
as soon as one of those magma blocks is removed, this block turns into actual lava.
the reason why we don't generate actual lava initially is because lava doesn't cull adjacent faces,
which results in a lot of unnecessary geometry being rendered.
*/
public class HiddenLavaBlock extends Block {

	#if MC_VERSION >= MC_1_20_3
		public static final MapCodec<HiddenLavaBlock> CODEC = BigGlobeAutoCodec.AUTO_CODEC.createDFUMapCodec(HiddenLavaBlock.class);

		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public MapCodec getCodec() {
			return CODEC;
		}
	#endif

	public HiddenLavaBlock(Settings settings) {
		super(settings);
	}

	@Override
	@Deprecated
	@SuppressWarnings("deprecation")
	public BlockState getStateForNeighborUpdate(
		#if MC_VERSION >= MC_1_21_2
			BlockState state,
			WorldView world,
			net.minecraft.world.tick.ScheduledTickView tickView,
			BlockPos pos,
			Direction direction,
			BlockPos neighborPos,
			BlockState neighborState,
			Random random
		#else
			BlockState state,
			Direction direction,
			BlockState neighborState,
			WorldAccess world,
			BlockPos pos,
			BlockPos neighborPos
		#endif
	) {
		return Block.isShapeFullCube(neighborState.getCullingShape(#if MC_VERSION < MC_1_21_2 world, pos #endif)) ? state : BlockStates.LAVA;
	}
}