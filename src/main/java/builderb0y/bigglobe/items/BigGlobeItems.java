package builderb0y.bigglobe.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.*;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;

import builderb0y.bigglobe.BigGlobeMod;
import builderb0y.bigglobe.blocks.BigGlobeBlockTags;
import builderb0y.bigglobe.blocks.BigGlobeBlocks;
import builderb0y.bigglobe.fluids.BigGlobeFluids;

public class BigGlobeItems {

	static { BigGlobeMod.LOGGER.debug("Registering items..."); }

	/*
	@TestOnly
	@Deprecated
	public static final TestItem
		TEST_ITEM                = register("test_item", new TestItem());
	//*/
	@SuppressWarnings("unused")
	public static final BlockItem
		OVERGROWN_SAND           = registerBlockPlacer(BigGlobeBlocks.OVERGROWN_SAND),
		OVERGROWN_PODZOL         = registerBlockPlacer(BigGlobeBlocks.OVERGROWN_PODZOL),
		ROSE                     = registerDecoPlacer(BigGlobeBlocks.ROSE),
		SHORT_GRASS              = registerDecoPlacer(BigGlobeBlocks.SHORT_GRASS),
		MUSHROOM_SPORES          = registerDecoPlacer(BigGlobeBlocks.MUSHROOM_SPORES),
		ROPE_ANCHOR              = registerDecoPlacer(BigGlobeBlocks.ROPE_ANCHOR),
		SPELUNKING_ROPE          = registerDecoPlacer(BigGlobeBlocks.SPELUNKING_ROPE),
		CRYSTALLINE_PRISMARINE   = registerBlockPlacer(BigGlobeBlocks.CRYSTALLINE_PRISMARINE),
		SLATED_PRISMARINE        = registerBlockPlacer(BigGlobeBlocks.SLATED_PRISMARINE),
		SLATED_PRISMARINE_SLAB   = registerBlockPlacer(BigGlobeBlocks.SLATED_PRISMARINE_SLAB),
		SLATED_PRISMARINE_STAIRS = registerBlockPlacer(BigGlobeBlocks.SLATED_PRISMARINE_STAIRS),
		FLOATSTONE               = registerBlockPlacer(BigGlobeBlocks.FLOATSTONE),
		FLOATSTONE_SLAB          = registerBlockPlacer(BigGlobeBlocks.FLOATSTONE_SLAB),
		FLOATSTONE_STAIRS        = registerBlockPlacer(BigGlobeBlocks.FLOATSTONE_STAIRS),
		ROCK                     = register("rock", new RockItem(BigGlobeBlocks.ROCK, new Item.Settings().group(ItemGroup.MISC))),
		ASHEN_NETHERRACK         = registerBlockPlacer(BigGlobeBlocks.ASHEN_NETHERRACK),
		SULFUR_ORE               = registerBlockPlacer(BigGlobeBlocks.SULFUR_ORE),
		SULFUR_BLOCK             = registerBlockPlacer(BigGlobeBlocks.SULFUR_BLOCK),
		WART_WEED                = registerDecoPlacer(BigGlobeBlocks.WART_WEED),
		CHARRED_GRASS            = registerDecoPlacer(BigGlobeBlocks.CHARRED_GRASS),
		BLAZING_BLOSSOM          = registerDecoPlacer(BigGlobeBlocks.BLAZING_BLOSSOM),
		GLOWING_GOLDENROD        = registerDecoPlacer(BigGlobeBlocks.GLOWING_GOLDENROD),
		CHARRED_PLANKS           = registerBlockPlacer(BigGlobeBlocks.CHARRED_PLANKS),
		CHARRED_SAPLING          = registerDecoPlacer(BigGlobeBlocks.CHARRED_SAPLING),
		CHARRED_LOG              = registerBlockPlacer(BigGlobeBlocks.CHARRED_LOG),
		STRIPPED_CHARRED_LOG     = registerBlockPlacer(BigGlobeBlocks.STRIPPED_CHARRED_LOG),
		CHARRED_WOOD             = registerBlockPlacer(BigGlobeBlocks.CHARRED_WOOD),
		STRIPPED_CHARRED_WOOD    = registerBlockPlacer(BigGlobeBlocks.STRIPPED_CHARRED_WOOD),
		CHARRED_LEAVES           = registerDecoPlacer(BigGlobeBlocks.CHARRED_LEAVES),
		CHARRED_SIGN             = register      ("charred_sign", new ColoredSignItem(new Item.Settings().maxCount(16).group(ItemGroup.DECORATIONS), BigGlobeBlocks.CHARRED_SIGN, BigGlobeBlocks.CHARRED_WALL_SIGN, DyeColor.LIGHT_GRAY)),
		CHARRED_PRESSURE_PLATE   = registerPlacer(BigGlobeBlocks.CHARRED_PRESSURE_PLATE, ItemGroup.REDSTONE),
		CHARRED_TRAPDOOR         = registerPlacer(BigGlobeBlocks.CHARRED_TRAPDOOR, ItemGroup.REDSTONE),
		CHARRED_STAIRS           = registerBlockPlacer(BigGlobeBlocks.CHARRED_STAIRS),
		CHARRED_BUTTON           = registerPlacer(BigGlobeBlocks.CHARRED_BUTTON, ItemGroup.REDSTONE),
		CHARRED_SLAB             = registerBlockPlacer(BigGlobeBlocks.CHARRED_SLAB),
		CHARRED_FENCE_GATE       = registerPlacer(BigGlobeBlocks.CHARRED_FENCE_GATE, ItemGroup.REDSTONE),
		CHARRED_FENCE            = registerDecoPlacer(BigGlobeBlocks.CHARRED_FENCE),
		CHARRED_DOOR             = register      ("charred_door", new TallBlockItem(BigGlobeBlocks.CHARRED_DOOR, new Item.Settings().group(ItemGroup.REDSTONE))),
		SOUL_MAGMA               = registerBlockPlacer(BigGlobeBlocks.SOUl_MAGMA),
		ROUGH_QUARTZ             = registerBlockPlacer(BigGlobeBlocks.ROUGH_QUARTZ),
		BUDDING_QUARTZ           = registerBlockPlacer(BigGlobeBlocks.BUDDING_QUARTZ),
		SMALL_QUARTZ_BUD         = registerDecoPlacer(BigGlobeBlocks.SMALL_QUARTZ_BUD),
		MEDIUM_QUARTZ_BUD        = registerDecoPlacer(BigGlobeBlocks.MEDIUM_QUARTZ_BUD),
		LARGE_QUARTZ_BUD         = registerDecoPlacer(BigGlobeBlocks.LARGE_QUARTZ_BUD),
		QUARTZ_CLUSTER           = registerDecoPlacer(BigGlobeBlocks.QUARTZ_CLUSTER);

	public static final TorchArrowItem TORCH_ARROW = register(
		"torch_arrow",
		new TorchArrowItem(
			new Item.Settings().group(ItemGroup.COMBAT)
		)
	);
	public static final PercussiveHammerItem PERCUSSIVE_HAMMER = register(
		"percussive_hammer",
		new PercussiveHammerItem(
			2.0F,
			-2.8F,
			ToolMaterials.IRON,
			BigGlobeBlockTags.MINEABLE_PERCUSSIVE_HAMMER,
			new Item.Settings()
			.maxDamage(166) //2/3'rds of the iron pickaxe durability, rounded down.
			.group(ItemGroup.TOOLS)
		)
	);
	public static final SlingshotItem SLINGSHOT = register(
		"slingshot",
		new SlingshotItem(
			new Item.Settings()
			.maxDamage(192)
			.group(ItemGroup.COMBAT)
		)
	);
	public static final BallOfStringItem BALL_OF_STRING = register(
		"ball_of_string",
		new BallOfStringItem(
			new Item.Settings()
			.maxCount(1)
			.group(ItemGroup.TOOLS)
		)
	);
	public static final Item ASH = register(
		"ash",
		new Item(
			new Item.Settings().group(ItemGroup.BREWING)
		)
	);
	public static final Item SULFUR = register(
		"sulfur",
		new Item(
			new Item.Settings().group(ItemGroup.MISC)
		)
	);
	public static final BucketItem SOUL_LAVA_BUCKET = register(
		"soul_lava_bucket",
		new BucketItem(
			BigGlobeFluids.SOUL_LAVA,
			new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1).group(ItemGroup.MISC)
		)
	);

	static { BigGlobeMod.LOGGER.debug("Done registering items."); }

	public static BlockItem registerBlockPlacer(Block block) {
		return registerPlacer(block, ItemGroup.BUILDING_BLOCKS);
	}

	public static BlockItem registerDecoPlacer(Block block) {
		return registerPlacer(block, ItemGroup.DECORATIONS);
	}

	public static BlockItem registerPlacer(Block block, ItemGroup group) {
		return Registry.register(
			Registry.ITEM,
			Registry.BLOCK.getId(block),
			new BlockItem(block, new Item.Settings().group(group))
		);
	}

	public static <I extends Item> I register(String name, I item) {
		return Registry.register(Registry.ITEM, BigGlobeMod.modID(name), item);
	}

	public static void init() {
		FuelRegistry.INSTANCE.add(SOUL_LAVA_BUCKET, 20000);
		FuelRegistry.INSTANCE.add(SULFUR, 1200);
		FuelRegistry.INSTANCE.add(SULFUR_BLOCK, 12000);
	}

	@Environment(EnvType.CLIENT)
	//@SuppressWarnings("UnstableApiUsage")
	public static void initClient() {
		ColorProviderRegistry.ITEM.register(
			(stack, tintIndex) -> GrassColors.getColor(0.5D, 1.0D),
			OVERGROWN_PODZOL,
			SHORT_GRASS
		);
		ModelPredicateProviderRegistry.register(SLINGSHOT, BigGlobeMod.modID("loaded"), (stack, world, entity, seed) -> {
			if (entity == null || entity.getActiveItem() != stack) return 0.0F;
			return ((float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft())) / 20.0F;
		});
		/*
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
			entries.addAfter(Items.WARPED_BUTTON, CHARRED_LOG, CHARRED_WOOD, STRIPPED_CHARRED_LOG, STRIPPED_CHARRED_WOOD, CHARRED_PLANKS, CHARRED_STAIRS, CHARRED_SLAB, CHARRED_FENCE, CHARRED_FENCE_GATE, CHARRED_DOOR, CHARRED_TRAPDOOR, CHARRED_PRESSURE_PLATE, CHARRED_BUTTON);
			entries.addAfter(Items.DARK_PRISMARINE_SLAB, SLATED_PRISMARINE, SLATED_PRISMARINE_STAIRS, SLATED_PRISMARINE_SLAB, FLOATSTONE, FLOATSTONE_STAIRS, FLOATSTONE_SLAB);
			entries.addBefore(Items.COAL_BLOCK, SULFUR_BLOCK);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
			entries.addAfter(Items.GRASS_BLOCK, OVERGROWN_PODZOL);
			entries.addBefore(Items.SAND, OVERGROWN_SAND);
			entries.addAfter(Items.PRISMARINE, CRYSTALLINE_PRISMARINE, SLATED_PRISMARINE);
			entries.addAfter(Items.MAGMA_BLOCK, SOUL_MAGMA);
			entries.addAfter(Items.WARPED_NYLIUM, ASHEN_NETHERRACK);
			entries.addAfter(Items.NETHER_QUARTZ_ORE, SULFUR_ORE);
			entries.addAfter(Items.AMETHYST_CLUSTER, ROUGH_QUARTZ, BUDDING_QUARTZ, SMALL_QUARTZ_BUD, MEDIUM_QUARTZ_BUD, LARGE_QUARTZ_BUD, QUARTZ_CLUSTER);
			entries.addAfter(Items.WARPED_STEM, CHARRED_LOG);
			entries.addAfter(Items.FLOWERING_AZALEA_LEAVES, CHARRED_LEAVES);
			entries.addAfter(Items.FLOWERING_AZALEA, CHARRED_SAPLING);
			entries.addBefore(Items.BROWN_MUSHROOM, MUSHROOM_SPORES);
			entries.addBefore(Items.GRASS, SHORT_GRASS);
			entries.addAfter(Items.DEAD_BUSH, CHARRED_GRASS);
			entries.addAfter(Items.DANDELION, ROSE);
			entries.addAfter(Items.LILY_OF_THE_VALLEY, BLAZING_BLOSSOM, GLOWING_GOLDENROD);
			entries.addBefore(Items.CRIMSON_ROOTS, WART_WEED);
			entries.addAfter(Items.STONE, ROCK);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
			entries.addAfter(Items.CHAIN, ROPE_ANCHOR, SPELUNKING_ROPE);
			entries.addAfter(Items.MAGMA_BLOCK, SOUL_MAGMA);
			entries.addAfter(Items.WARPED_SIGN, CHARRED_SIGN);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
			entries.addBefore(Items.BUCKET, PERCUSSIVE_HAMMER);
			entries.addAfter(Items.LAVA_BUCKET, SOUL_LAVA_BUCKET);
			entries.addAfter(Items.FISHING_ROD, ROPE_ANCHOR, SPELUNKING_ROPE, TORCH_ARROW);
			entries.addAfter(Items.LEAD, string(16), string(64), string(256));
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.addAfter(Items.SPECTRAL_ARROW, TORCH_ARROW);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
			entries.addAfter(Items.CHARCOAL, SULFUR);
			entries.addAfter(Items.GUNPOWDER, ASH);
			entries.addAfter(Items.FLINT, ROCK);
		});
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
			entries.addAfter(Items.CROSSBOW, SLINGSHOT);
		});
		*/
	}

	public static ItemStack string(int blocks) {
		ItemStack stack = new ItemStack(BigGlobeItems.BALL_OF_STRING);
		stack.getOrCreateNbt().putInt(BallOfStringItem.MAX_DAMAGE_KEY, blocks);
		return stack;
	}
}