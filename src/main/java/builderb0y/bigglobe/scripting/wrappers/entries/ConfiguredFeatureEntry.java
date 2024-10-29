package builderb0y.bigglobe.scripting.wrappers.entries;

import java.lang.invoke.MethodHandles;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.scripting.wrappers.tags.ConfiguredFeatureTag;
import builderb0y.scripting.bytecode.ConstantFactory;
import builderb0y.scripting.bytecode.TypeInfo;

public class ConfiguredFeatureEntry extends EntryWrapper<ConfiguredFeature<?, ?>, ConfiguredFeatureTag> {

	public static final TypeInfo TYPE = TypeInfo.of(ConfiguredFeatureEntry.class);
	public static final ConstantFactory CONSTANT_FACTORY = ConstantFactory.autoOfString();

	public ConfiguredFeatureEntry(RegistryEntry<ConfiguredFeature<?, ?>> entry) {
		super(entry);
	}

	public static ConfiguredFeatureEntry of(MethodHandles.Lookup caller, String name, Class<?> type, String id) {
		return of(id);
	}

	public static ConfiguredFeatureEntry of(String id) {
		if (id == null) return null;
		return new ConfiguredFeatureEntry(BigGlobeMod.getRegistry(RegistryKeys.CONFIGURED_FEATURE).getByName(id));
	}

	@Override
	public boolean isIn(ConfiguredFeatureTag entries) {
		return super.isIn(entries);
	}
}