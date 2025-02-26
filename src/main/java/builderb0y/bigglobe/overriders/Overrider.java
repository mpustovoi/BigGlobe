package builderb0y.bigglobe.overriders;

import java.util.*;
import java.util.stream.Collectors;

import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import builderb0y.autocodec.annotations.MemberUsage;
import builderb0y.autocodec.annotations.UseCoder;
import builderb0y.autocodec.coders.AutoCoder;
import builderb0y.autocodec.coders.KeyDispatchCoder;
import builderb0y.autocodec.reflection.reification.ReifiedType;
import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.chunkgen.BigGlobeScriptedChunkGenerator;
import builderb0y.bigglobe.codecs.BigGlobeAutoCodec;
import builderb0y.bigglobe.columns.scripted.dependencies.DependencyView;
import builderb0y.bigglobe.columns.scripted.dependencies.IndirectDependencyCollector;
import builderb0y.bigglobe.columns.scripted.entries.ColumnEntry;
import builderb0y.bigglobe.dynamicRegistries.BigGlobeDynamicRegistries;
import builderb0y.bigglobe.util.UnregisteredObjectException;
import builderb0y.bigglobe.versions.RegistryVersions;

@UseCoder(name = "CODER", in = Overrider.class, usage = MemberUsage.FIELD_CONTAINS_HANDLER)
public sealed interface Overrider permits ColumnValueOverrider.Entry, StructureOverrider.Entry {

	public static final AutoCoder<Overrider> CODER = new KeyDispatchCoder<>(ReifiedType.from(Overrider.class), BigGlobeAutoCodec.AUTO_CODEC.createCoder(Type.class)) {

		@Override
		public @Nullable Type getKey(@NotNull Overrider object) {
			return object.getOverriderType();
		}

		@Override
		public @Nullable AutoCoder<? extends Overrider> getCoder(@NotNull Type type) {
			return type.coder;
		}
	};
	public static Object SETUP = new Object() {{
		CommonLifecycleEvents.TAGS_LOADED.register((DynamicRegistryManager registries, boolean client) -> {
			if (!client) {
				RegistryVersions
				.getRegistry(registries, BigGlobeDynamicRegistries.OVERRIDER_REGISTRY_KEY)
				.streamEntries()
				.filter((RegistryEntry<Overrider> entry) -> entry.streamTags().findAny().isEmpty())
				.forEach((RegistryEntry<Overrider> entry) -> {
					BigGlobeMod.LOGGER.warn(UnregisteredObjectException.getKey(entry) + " is not in any tags. It will not be able to function unless you add it to a tag which the chunk generator uses.");
				});
			}
		});
	}};

	public abstract Type getOverriderType();

	public static enum Type {
		STRUCTURE(StructureOverrider.Entry.class),
		COLUMN_VALUE(ColumnValueOverrider.Entry.class);

		public final Class<? extends Overrider> overriderClass;
		public final AutoCoder<? extends Overrider> coder;

		Type(Class<? extends Overrider> overriderClass) {
			this.overriderClass = overriderClass;
			this.coder = BigGlobeAutoCodec.AUTO_CODEC.createCoder(overriderClass);
		}
	}

	public static class SortedOverriders {

		public final StructureOverrider.Holder[] structures;
		public final ColumnValueOverrider.Holder[] rawColumnValues, featureColumnValues;
		public final String[] rawColumnValueDependencies, featureColumnValueDependencies;

		public SortedOverriders(BigGlobeScriptedChunkGenerator generator) {
			Map<Type, List<Overrider>> map = generator.overriders.objectStream().collect(Collectors.groupingBy(Overrider::getOverriderType));
			this.structures = map.getOrDefault(Type.STRUCTURE, Collections.emptyList()).stream().map(StructureOverrider.Entry.class::cast).map(StructureOverrider.Entry::script).toArray(StructureOverrider.Holder[]::new);
			this.rawColumnValues = map.getOrDefault(Type.COLUMN_VALUE, Collections.emptyList()).stream().map(ColumnValueOverrider.Entry.class::cast).filter(ColumnValueOverrider.Entry::raw_generation).map(ColumnValueOverrider.Entry::script).toArray(ColumnValueOverrider.Holder[]::new);
			this.featureColumnValues = map.getOrDefault(Type.COLUMN_VALUE, Collections.emptyList()).stream().map(ColumnValueOverrider.Entry.class::cast).filter(ColumnValueOverrider.Entry::feature_generation).map(ColumnValueOverrider.Entry::script).toArray(ColumnValueOverrider.Holder[]::new);
			this.rawColumnValueDependencies = this.extractDependencies(this.rawColumnValues, generator);
			this.featureColumnValueDependencies = this.extractDependencies(this.featureColumnValues, generator);
		}

		public String[] extractDependencies(ColumnValueOverrider.Holder[] holders, BigGlobeScriptedChunkGenerator generator) {
			IndirectDependencyCollector collector = new IndirectDependencyCollector(generator);
			for (ColumnValueOverrider.Holder holder : holders) {
				holder.streamDirectDependencies().forEach(collector);
			}
			return (
				collector
				.stream()
				.filter((RegistryEntry<? extends DependencyView> registryEntry) -> {
					return (
						registryEntry.value() instanceof ColumnEntry columnEntry &&
						generator.columnEntryRegistry.voronoiManager.getEnablingSettings(columnEntry).isEmpty() &&
						columnEntry.hasField()
					);
				})
				.map(UnregisteredObjectException::getID)
				.map(Identifier::toString)
				.map(String::intern)
				.toArray(String[]::new)
			);
		}
	}
}