package builderb0y.bigglobe.scripting.wrappers.entries;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.Map;
import java.util.random.RandomGenerator;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;

import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.dynamicRegistries.BigGlobeDynamicRegistries;
import builderb0y.bigglobe.dynamicRegistries.WoodPalette;
import builderb0y.bigglobe.dynamicRegistries.WoodPalette.WoodPaletteType;
import builderb0y.bigglobe.randomLists.IRandomList;
import builderb0y.bigglobe.randomLists.MappingRandomList;
import builderb0y.bigglobe.scripting.wrappers.tags.WoodPaletteTag;
import builderb0y.bigglobe.util.UnregisteredObjectException;
import builderb0y.scripting.bytecode.ConstantFactory;
import builderb0y.scripting.bytecode.TypeInfo;

public class WoodPaletteEntry extends EntryWrapper<WoodPalette, WoodPaletteTag> {

	public static final TypeInfo TYPE = TypeInfo.of(WoodPaletteEntry.class);
	public static final ConstantFactory CONSTANT_FACTORY = ConstantFactory.autoOfString();

	public WoodPaletteEntry(RegistryEntry<WoodPalette> entry) {
		super(entry);
	}

	public static WoodPaletteEntry of(MethodHandles.Lookup caller, String name, Class<?> type, String id) {
		return of(id);
	}

	public static WoodPaletteEntry of(String id) {
		if (id == null) return null;
		return new WoodPaletteEntry(BigGlobeMod.getRegistry(BigGlobeDynamicRegistries.WOOD_PALETTE_REGISTRY_KEY).getByName(id));
	}

	public Map<String, ConfiguredFeatureEntry> features() {
		return Collections.unmodifiableMap(Maps.transformValues(this.entry.value().features, ConfiguredFeatureEntry::new));
	}

	public IRandomList<Block> getBlocks(WoodPaletteType type) {
		IRandomList<RegistryEntry<Block>> blocks = this.entry.value().blocks.get(type);
		if (blocks != null) return MappingRandomList.create(blocks, RegistryEntry<Block>::value);
		else throw new IllegalStateException("WoodPaletteType " + type + " not present on WoodPalette " + UnregisteredObjectException.getID(this.entry));
	}

	public Block getBlock(RandomGenerator random, WoodPaletteType type) {
		return this.getBlocks(type).getRandomElement(random);
	}

	public BlockState getState(RandomGenerator random, WoodPaletteType type) {
		return this.getBlock(random, type).getDefaultState();
	}

	@Override
	public boolean isIn(WoodPaletteTag entries) {
		return super.isIn(entries);
	}
}