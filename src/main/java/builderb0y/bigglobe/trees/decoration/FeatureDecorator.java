package builderb0y.bigglobe.trees.decoration;

import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import builderb0y.bigglobe.trees.TreeGenerator;

public class FeatureDecorator implements BlockDecorator {

	public final RegistryEntry<ConfiguredFeature<?, ?>> feature;

	public FeatureDecorator(RegistryEntry<ConfiguredFeature<?, ?>> feature) {
		this.feature = feature;
	}

	@Override
	public void decorate(TreeGenerator generator, BlockPos pos, BlockState state) {
		this.feature.value().generate(
			generator.worldQueue,
			((ServerChunkManager)(generator.worldQueue.getChunkManager())).getChunkGenerator(),
			generator.random.mojang(),
			pos
		);
	}
}