package builderb0y.bigglobe.structures;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.structure.Structure;

import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.scripting.wrappers.ArrayWrapper;
import builderb0y.bigglobe.scripting.wrappers.StructureStartWrapper;
import builderb0y.bigglobe.util.UnregisteredObjectException;
import builderb0y.bigglobe.versions.RegistryVersions;

public class ScriptStructures extends ArrayWrapper<StructureStartWrapper> {

	public static final StructureStartWrapper[] EMPTY_STRUCTURE_START_ARRAY = {};
	public static final ScriptStructures EMPTY_SCRIPT_STRUCTURES = new ScriptStructures(EMPTY_STRUCTURE_START_ARRAY);

	public ScriptStructures(StructureStartWrapper[] starts) {
		super(starts);
	}

	public static ScriptStructures getStructures(StructureAccessor structureAccessor, ChunkPos chunkPos, boolean distantHorizons) {
		if (distantHorizons) {
			return EMPTY_SCRIPT_STRUCTURES;
		}
		List<StructureStart> starts = structureAccessor.getStructureStarts(chunkPos, Predicates.alwaysTrue());
		if (starts.isEmpty()) {
			return EMPTY_SCRIPT_STRUCTURES;
		}
		//note: need an actual Registry instance here due to its
		//ability to lookup the key associated with a given object.
		Registry<Structure> structureRegistry = RegistryVersions.getRegistry(
			BigGlobeMod.getCurrentServer().getRegistryManager(),
			RegistryKeys.STRUCTURE
		);
		return new ScriptStructures(
			starts
			.stream()
			.map((StructureStart start) -> StructureStartWrapper.of(
				RegistryVersions.getEntry(
					structureRegistry,
					start.getStructure()
				),
				start
			))
			.toArray(StructureStartWrapper[]::new)
		);
	}
}