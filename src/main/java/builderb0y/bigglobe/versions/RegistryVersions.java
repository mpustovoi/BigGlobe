package builderb0y.bigglobe.versions;

import java.util.stream.Stream;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.loot.entry.LootPoolEntryType;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.particle.ParticleType;
import net.minecraft.potion.Potion;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.structure.StructureType;

import builderb0y.bigglobe.dynamicRegistries.BetterRegistry;

public class RegistryVersions {

	@Deprecated public static Registry         <Block                             > block                 () { return Registries.BLOCK               ; }
	@Deprecated public static Registry         <BlockEntityType<?>                > blockEntityType       () { return Registries.BLOCK_ENTITY_TYPE   ; }
	@Deprecated public static Registry         <Item                              > item                  () { return Registries.ITEM                ; }
	@Deprecated public static Registry         <Fluid                             > fluid                 () { return Registries.FLUID               ; }
	@Deprecated public static Registry         <EntityType<?>                     > entityType            () { return Registries.ENTITY_TYPE         ; }
	@Deprecated public static Registry         <Potion                            > potion                () { return Registries.POTION              ; }
	@Deprecated public static Registry         <Feature<?>                        > feature               () { return Registries.FEATURE             ; }
	@Deprecated public static Registry         <LootPoolEntryType                 > lootPoolEntryType     () { return Registries.LOOT_POOL_ENTRY_TYPE; }
	@Deprecated public static Registry         <ParticleType<?>                   > particleType          () { return Registries.PARTICLE_TYPE       ; }
	@Deprecated public static Registry         <RecipeSerializer<?>               > recipeSerializer      () { return Registries.RECIPE_SERIALIZER   ; }
	@Deprecated public static Registry         <StructureType<?>                  > structureType         () { return Registries.STRUCTURE_TYPE      ; }
	@Deprecated public static Registry         <StructurePieceType                > structurePieceType    () { return Registries.STRUCTURE_PIECE     ; }
	@Deprecated public static Registry         <StructurePlacementType<?>         > structurePlacementType() { return Registries.STRUCTURE_PLACEMENT ; }
	@Deprecated public static Registry         <SoundEvent                        > soundEvent            () { return Registries.SOUND_EVENT         ; }

	#if MC_VERSION >= MC_1_20_5
		//public static Registry     <ArmorMaterial                     > armorMaterial         () { return Registries.ARMOR_MATERIAL      ; }
		@Deprecated public static Registry     <MapCodec<? extends ChunkGenerator>> chunkGenerator        () { return Registries.CHUNK_GENERATOR     ; }
		@Deprecated public static Registry     <MapCodec<? extends BiomeSource   >> biomeSource           () { return Registries.BIOME_SOURCE        ; }
		@Deprecated public static Registry     <LootFunctionType<?>               > lootFunctionType      () { return Registries.LOOT_FUNCTION_TYPE  ; }
	#else
		@Deprecated public static Registry     <Codec<? extends ChunkGenerator>   > chunkGenerator        () { return Registries.CHUNK_GENERATOR     ; }
		@Deprecated public static Registry     <Codec<? extends BiomeSource   >   > biomeSource           () { return Registries.BIOME_SOURCE        ; }
		@Deprecated public static Registry     <LootFunctionType                  > lootFunctionType      () { return Registries.LOOT_FUNCTION_TYPE  ; }
	#endif

	#if MC_VERSION >= MC_1_20_3
		@Deprecated public static Registry     <StatusEffect                      > statusEffect          () { return Registries.STATUS_EFFECT       ; }
	#endif

	@SuppressWarnings("unchecked")
	public static <T> RegistryKey<Registry<T>> getRegistryKey(Registry<T> registry) {
		return (RegistryKey<Registry<T>>)(registry.getKey());
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
			return manager.getOrThrow(key.getRegistryRef()).get(key);
		#else
			return manager.get(key.getRegistryRef()).get(key);
		#endif
	}

	public static <T> RegistryEntry<T> getEntry(DynamicRegistryManager manager, RegistryKey<T> key) {
		#if MC_VERSION >= MC_1_21_2
			return manager.getOrThrow(key.getRegistryRef()).getOrThrow(key);
		#else
			return manager.get(key.getRegistryRef()).entryOf(key);
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