package builderb0y.bigglobe.scripting.wrappers.entries;

import java.lang.invoke.MethodHandles;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.dynamicRegistries.BigGlobeDynamicRegistries;
import builderb0y.bigglobe.scripting.wrappers.tags.StructurePlacementScriptTag;
import builderb0y.bigglobe.structures.scripted.ScriptedStructure.CombinedStructureScripts;
import builderb0y.scripting.bytecode.ConstantFactory;
import builderb0y.scripting.bytecode.TypeInfo;

public class StructurePlacementScriptEntry extends EntryWrapper<CombinedStructureScripts, StructurePlacementScriptTag> {

	public static final TypeInfo TYPE = TypeInfo.of(StructurePlacementScriptEntry.class);
	public static final ConstantFactory CONSTANT_FACTORY = ConstantFactory.autoOfString();

	public StructurePlacementScriptEntry(RegistryEntry<CombinedStructureScripts> entry) {
		super(entry);
	}

	public static StructurePlacementScriptEntry of(MethodHandles.Lookup caller, String name, Class<?> type, String id) {
		return of(id);
	}

	public static StructurePlacementScriptEntry of(String id) {
		if (id == null) return null;
		return new StructurePlacementScriptEntry(BigGlobeMod.getRegistry(BigGlobeDynamicRegistries.SCRIPT_STRUCTURE_PLACEMENT_REGISTRY_KEY).getByName(id));
	}

	@Override
	public boolean isIn(StructurePlacementScriptTag entries) {
		return super.isIn(entries);
	}
}