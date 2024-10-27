package builderb0y.bigglobe.hyperspace;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;

import builderb0y.bigglobe.BigGlobeMod;

public class HyperspaceConstants {

	public static final RegistryKey<World>            WORLD_KEY             = RegistryKey.of(RegistryKeys.WORLD,     BigGlobeMod.modID("hyperspace"));
	public static final RegistryKey<DimensionOptions> DIMENSION_OPTIONS_KEY = RegistryKey.of(RegistryKeys.DIMENSION, BigGlobeMod.modID("hyperspace"));
	public static final RegistryKey<Biome>            BIOME_KEY             = RegistryKey.of(RegistryKeys.BIOME,     BigGlobeMod.modID("hyperspace"));
}