package builderb0y.bigglobe.scripting.wrappers.tags;

import java.lang.invoke.MethodHandles;
import java.util.random.RandomGenerator;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.structure.Structure;

import builderb0y.bigglobe.scripting.wrappers.entries.StructureEntry;
import builderb0y.bigglobe.util.DelayedEntryList;
import builderb0y.scripting.bytecode.ConstantFactory;
import builderb0y.scripting.bytecode.TypeInfo;

import static builderb0y.scripting.bytecode.InsnTrees.*;

public class StructureTag extends TagWrapper<Structure, StructureEntry> {

	public static final TypeInfo TYPE = type(StructureTag.class);
	public static final ConstantFactory CONSTANT_FACTORY = ConstantFactory.autoOfString();

	public StructureTag(DelayedEntryList<Structure> list) {
		super(list);
	}

	public static StructureTag of(MethodHandles.Lookup caller, String name, Class<?> type, String id) {
		return of(id);
	}

	public static StructureTag of(String id) {
		if (id == null) return null;
		return new StructureTag(DelayedEntryList.constant(RegistryKeys.STRUCTURE, id));
	}

	@Override
	public StructureEntry wrap(RegistryEntry<Structure> entry) {
		return new StructureEntry(entry);
	}

	@Override
	public RegistryEntry<Structure> unwrap(StructureEntry entry) {
		return entry.entry;
	}

	@Override
	public boolean contains(StructureEntry entry) {
		return super.contains(entry);
	}

	@Override
	public StructureEntry random(RandomGenerator random) {
		return super.random(random);
	}

	@Override
	public StructureEntry random(long seed) {
		return super.random(seed);
	}
}