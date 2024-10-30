package builderb0y.bigglobe.versions;

import java.util.stream.Stream;

import com.mojang.datafixers.util.Pair;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;

import builderb0y.bigglobe.dynamicRegistries.BetterRegistry;

public class RegistryVersions {

	@SuppressWarnings("unchecked")
	public static <T> RegistryKey<Registry<T>> getRegistryKey(Registry<T> registry) {
		return (RegistryKey<Registry<T>>)(registry.getKey());
	}

	public static <T> RegistryKey<Registry<T>> getRegistryKey(RegistryKey<T> key) {
		#if MC_VERSION >= MC_1_20_5
			return key.getRegistryRef();
		#else
			return RegistryKey.ofRegistry(key.getRegistry());
		#endif
	}

	@SuppressWarnings("unchecked")
	public static <T> RegistryKey<Registry<T>> getRegistryKey(TagKey<T> key) {
		#if MC_VERSION >= MC_1_21_2
			return (RegistryKey<Registry<T>>)(key.registryRef());
		#else
			return (RegistryKey<Registry<T>>)(key.registry());
		#endif
	}

	@SuppressWarnings("unchecked")
	public static <T> RegistryKey<Registry<T>> getRegistryKey(RegistryWrapper.Impl<T> registry) {
		#if MC_VERSION >= MC_1_21_2
			return (RegistryKey<Registry<T>>)(registry.getKey());
		#else
			return (RegistryKey<Registry<T>>)(registry.getRegistryKey());
		#endif
	}

	public static <T> T getObject(DynamicRegistryManager manager, RegistryKey<T> key) {
		#if MC_VERSION >= MC_1_21_2
			return manager.getOrThrow(getRegistryKey(key)).get(key);
		#else
			return manager.get(getRegistryKey(key)).get(key);
		#endif
	}

	public static <T> RegistryEntry<T> getEntry(DynamicRegistryManager manager, RegistryKey<T> key) {
		#if MC_VERSION >= MC_1_21_2
			return manager.getOrThrow(getRegistryKey(key)).getOrThrow(key);
		#else
			return manager.get(getRegistryKey(key)).entryOf(key);
		#endif
	}

	public static <T> RegistryEntry<T> getEntry(Registry<T> registry, RegistryKey<T> key) {
		#if MC_VERSION >= MC_1_21_2
			return registry.getOrThrow(key);
		#else
			return registry.entryOf(key);
		#endif
	}

	public static <T> RegistryEntry<T> getEntry(Registry<T> registry, T object) {
		return registry.getEntry(object);
	}

	public static <T> Registry<T> getRegistry(DynamicRegistryManager manager, RegistryKey<Registry<T>> key) {
		#if MC_VERSION >= MC_1_21_2
			return manager.getOrThrow(key);
		#else
			return manager.get(key);
		#endif
	}

	public static <T> RegistryEntryList<T> getTagNullable(Registry<T> registry, TagKey<T> key) {
		#if MC_VERSION >= MC_1_21_2
			return registry.getOptional(key).orElse(null);
		#else
			return registry.getEntryList(key).orElse(null);
		#endif
	}

	public static <T> RegistryEntryList<T> getTagNullable(DynamicRegistryManager manager, TagKey<T> key) {
		#if MC_VERSION >= MC_1_21_2
			return manager.getOrThrow(key.registryRef()).getOptional(key).orElse(null);
		#else
			return manager.get(key.registry()).getEntryList(key).orElse(null);
		#endif
	}

	public static <T> Stream<RegistryEntryList<T>> streamTags(Registry<T> registry) {
		#if MC_VERSION >= MC_1_21_2
			return BetterRegistry.castStream(registry.streamTags());
		#else
			return registry.streamTagsAndEntries().map(Pair::getSecond);
		#endif
	}
}