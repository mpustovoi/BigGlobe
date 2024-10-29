package builderb0y.bigglobe.scripting.wrappers.entries;

import java.lang.invoke.MethodHandles;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.structure.StructureType;

import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.scripting.wrappers.tags.StructureTypeTag;
import builderb0y.scripting.bytecode.ConstantFactory;
import builderb0y.scripting.bytecode.TypeInfo;

public class StructureTypeEntry extends EntryWrapper<StructureType<?>, StructureTypeTag> {

	public static final TypeInfo TYPE = TypeInfo.of(StructureTypeEntry.class);
	public static final ConstantFactory CONSTANT_FACTORY = ConstantFactory.autoOfString();

	public StructureTypeEntry(RegistryEntry<StructureType<?>> entry) {
		super(entry);
	}

	public static StructureTypeEntry of(MethodHandles.Lookup caller, String name, Class<?> type, String id) {
		return of(id);
	}

	public static StructureTypeEntry of(String id) {
		if (id == null) return null;
		return new StructureTypeEntry(BigGlobeMod.getRegistry(RegistryKeys.STRUCTURE_TYPE).getByName(id));
	}

	@Override
	public boolean isIn(StructureTypeTag entries) {
		return super.isIn(entries);
	}
}