package builderb0y.bigglobe.scripting.wrappers;

import java.util.Iterator;
import java.util.Optional;
import java.util.random.RandomGenerator;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;

import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.noise.Permuter;
import builderb0y.bigglobe.versions.TagKeyVersions;
import builderb0y.scripting.bytecode.TypeInfo;

public interface TagWrapper<T_Raw, T_Entry> extends Iterable<T_Entry> {

	public static final TypeInfo TYPE = TypeInfo.of(TagWrapper.class);

	public abstract TagKey<T_Raw> key();

	public default String id() {
		return this.key().id().toString();
	}

	public abstract T_Entry wrap(RegistryEntry<T_Raw> entry);

	/**
	implementations should delegate to {@link #randomImpl(RandomGenerator)}.
	the reason why we have 2 methods here instead of just making random()
	default is to ensure that on subclasses, a method which returns
	the actual type of T_Entry instead of the generic type exists.
	the actual type is used by scripting, so the bytecode is a bit picky.
	*/
	public abstract T_Entry random(RandomGenerator random);

	public default T_Entry randomImpl(RandomGenerator random) {
		TagKey<T_Raw> key = this.key();
		RegistryKey<Registry<T_Raw>> registryKey = TagKeyVersions.registry(key);
		RegistryEntryList<T_Raw> list = BigGlobeMod.getRegistry(registryKey).getOrCreateTag(key);
		if (list == null) throw new RuntimeException("#" + registryKey.getValue() + " / " + key.id() + " does not exist.");
		if (list.size() == 0) throw new RuntimeException("#" + registryKey.getValue() + " / " + key.id() + " is empty.");
		RegistryEntry<T_Raw> element = list.get(random.nextInt(list.size()));
		return this.wrap(element);
	}

	public abstract T_Entry random(long seed);

	public default T_Entry randomImpl(long seed) {
		TagKey<T_Raw> key = this.key();
		RegistryKey<Registry<T_Raw>> registryKey = TagKeyVersions.registry(key);
		RegistryEntryList<T_Raw> list = BigGlobeMod.getRegistry(registryKey).getOrCreateTag(key);
		if (list == null) throw new RuntimeException("#" + registryKey.getValue() + " / " + key.id() + " does not exist.");
		if (list.size() == 0) throw new RuntimeException("#" + registryKey.getValue() + " / " + key.id() + " is empty.");
		RegistryEntry<T_Raw> element = list.get(Permuter.nextBoundedInt(seed, list.size()));
		return this.wrap(element);
	}

	@Override
	public default Iterator<T_Entry> iterator() {
		TagKey<T_Raw> key = this.key();
		RegistryKey<Registry<T_Raw>> registryKey = TagKeyVersions.registry(key);
		RegistryEntryList<T_Raw> list = BigGlobeMod.getRegistry(registryKey).getOrCreateTag(key);
		if (list == null || list.size() == 0) throw new RuntimeException("#" + registryKey.getValue() + " / " + key.id() + " does not exist");
		return list.stream().map(this::wrap).iterator();
	}
}