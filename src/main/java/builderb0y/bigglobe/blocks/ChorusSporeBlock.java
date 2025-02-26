package builderb0y.bigglobe.blocks;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import builderb0y.bigglobe.features.SingleBlockFeature;
import builderb0y.bigglobe.versions.BlockStateVersions;

public abstract class ChorusSporeBlock extends PlantBlock implements Fertilizable {

	#if MC_VERSION >= MC_1_20_3

		@Override
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public abstract MapCodec getCodec();
	#endif

	public final RegistryEntry<Block> grow_into;

	public ChorusSporeBlock(Settings settings, RegistryEntry<Block> grow_into) {
		super(settings);
		this.grow_into = grow_into;
	}

	@Override
	@Deprecated
	@SuppressWarnings("deprecation")
	public abstract VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context);

	@Override
	public boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return BlockStateVersions.isOpaqueFullCube(floor, world, pos);
	}

	@Override
	public boolean isFertilizable(
		WorldView world,
		BlockPos pos,
		BlockState state
		#if MC_VERSION < MC_1_20_2 , boolean isClient #endif
	) {
		return true;
	}

	@Override
	public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		SingleBlockFeature.place(world, pos, this.grow_into.value().getDefaultState(), SingleBlockFeature.IS_REPLACEABLE);
	}
}