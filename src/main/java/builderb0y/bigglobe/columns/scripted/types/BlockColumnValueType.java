package builderb0y.bigglobe.columns.scripted.types;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

import builderb0y.autocodec.annotations.RecordLike;
import builderb0y.bigglobe.columns.scripted.compile.ColumnCompileContext;
import builderb0y.bigglobe.versions.IdentifierVersions;
import builderb0y.scripting.bytecode.TypeInfo;
import builderb0y.scripting.bytecode.tree.InsnTree;

import static builderb0y.scripting.bytecode.InsnTrees.*;

@RecordLike({})
public class BlockColumnValueType extends AbstractColumnValueType {

	@Override
	public TypeInfo getTypeInfo() {
		return type(Block.class);
	}

	@Override
	public InsnTree createConstant(Object object, ColumnCompileContext context) {
		String string = (String)(object);
		Identifier identifier = IdentifierVersions.create(string);
		RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, identifier);
		RegistryEntry<Block> blockEntry = context.registry.registries.getRegistry(RegistryKeys.BLOCK).getOrCreateEntry(key);
		return ldc(blockEntry.value(), type(Block.class));
	}

	@Override
	public String toString() {
		return "block";
	}
}