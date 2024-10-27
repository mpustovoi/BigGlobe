package builderb0y.bigglobe.scripting.wrappers;

import java.lang.invoke.MethodHandles;

import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

import builderb0y.bigglobe.versions.IdentifierVersions;
import builderb0y.scripting.bytecode.ConstantFactory;
import builderb0y.bigglobe.versions.RegistryVersions;
import builderb0y.scripting.bytecode.TypeInfo;

import static builderb0y.scripting.bytecode.InsnTrees.*;

public record StructureTypeEntry(RegistryEntry<StructureType<?>> entry) implements EntryWrapper<StructureType<?>, StructureTypeTagKey> {

	public static final TypeInfo TYPE = type(StructureTypeEntry.class);
	public static final ConstantFactory CONSTANT_FACTORY = ConstantFactory.autoOfString();

	public static StructureTypeEntry of(MethodHandles.Lookup caller, String name, Class<?> type, String id) {
		return of(id);
	}

	public static StructureTypeEntry of(String id) {
		if (id == null) return null;
		return new StructureTypeEntry(
			RegistryVersions.getEntry(
				Registries.STRUCTURE_TYPE,
				RegistryKey.of(
					RegistryKeys.STRUCTURE_TYPE,
					IdentifierVersions.create(id)
				)
			)
		);
	}

	@Override
	public boolean isIn(StructureTypeTagKey key) {
		return this.isInImpl(key);
	}
}