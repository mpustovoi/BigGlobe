package builderb0y.bigglobe.dynamicRegistries;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Range;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import builderb0y.autocodec.annotations.*;
import builderb0y.autocodec.verifiers.VerifyContext;
import builderb0y.autocodec.verifiers.VerifyException;
import builderb0y.bigglobe.randomLists.IRandomList;

@SuppressWarnings("unused")
@UseVerifier(name = "verify", usage = MemberUsage.METHOD_IS_HANDLER)
public class WoodPalette {

	public final EnumMap<WoodPaletteType, @SingletonArray IRandomList<@UseName("block") RegistryEntry<Block>>> blocks;
	public final @DefaultEmpty Map<@Intern String, RegistryEntry<ConfiguredFeature<?, ?>>> features;

	public WoodPalette(
		EnumMap<WoodPaletteType, IRandomList<RegistryEntry<Block>>> blocks,
		Map<String, RegistryEntry<ConfiguredFeature<?, ?>>> features
	) {
		this.blocks = blocks;
		this.features = features;
	}

	public static <T_Encoded> void verify(VerifyContext<T_Encoded, WoodPalette> context) throws VerifyException {
		//fast check.
		WoodPalette palette = context.object;
		if (palette != null && palette.blocks.size() != WoodPaletteType.VALUES.length) {
			//slow print.
			context.logger().logErrorLazy(() -> {
				StringBuilder builder = new StringBuilder("WoodPalette is missing blocks: ");
				for (WoodPaletteType type : WoodPaletteType.VALUES) {
					if (!palette.blocks.containsKey(type)) {
						builder.append(type.lowerCaseName).append(", ");
					}
				}
				builder.setLength(builder.length() - 2);
				return builder.toString();
			});
		}
	}

	public RegistryEntry<ConfiguredFeature<?, ?>> getSaplingGrowFeature() {
		return this.features.get("sapling_grow");
	}

	//////////////////////////////// block ////////////////////////////////

	public Block getBlock(RandomGenerator random, WoodPaletteType type) {
		RegistryEntry<Block> block = this.getBlocks(type).getRandomElement(random);
		if (block != null) return block.value();
		else throw new IllegalStateException("WoodPaletteType not present: " + type);
	}

	public Block logBlock            (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.LOG              ); }
	public Block woodBlock           (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.WOOD             ); }
	public Block strippedLogBlock    (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.STRIPPED_LOG     ); }
	public Block strippedWoodBlock   (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.STRIPPED_WOOD    ); }
	public Block planksBlock         (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.PLANKS           ); }
	public Block stairsBlock         (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.STAIRS           ); }
	public Block slabBlock           (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.SLAB             ); }
	public Block fenceBlock          (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.FENCE            ); }
	public Block fenceGateBlock      (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.FENCE_GATE       ); }
	public Block doorBlock           (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.DOOR             ); }
	public Block trapdoorBlock       (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.TRAPDOOR         ); }
	public Block pressurePlateBlock  (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.PRESSURE_PLATE   ); }
	public Block buttonBlock         (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.BUTTON           ); }
	public Block leavesBlock         (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.LEAVES           ); }
	public Block saplingBlock        (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.SAPLING          ); }
	public Block pottedSaplingBlock  (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.POTTED_SAPLING   ); }
	public Block standingSignBlock   (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.STANDING_SIGN    ); }
	public Block wallSignBlock       (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.WALL_SIGN        ); }
	public Block hangingSignBlock    (RandomGenerator random) { return this.getBlock(random, WoodPaletteType.HANGING_SIGN     ); }
	public Block wallHangingSignBlock(RandomGenerator random) { return this.getBlock(random, WoodPaletteType.WALL_HANGING_SIGN); }

	//////////////////////////////// blocks ////////////////////////////////

	public IRandomList<RegistryEntry<Block>> getBlocks(WoodPaletteType type) {
		IRandomList<RegistryEntry<Block>> block = this.blocks.get(type);
		if (block != null) return block;
		else throw new IllegalStateException("WoodPaletteType not present: " + type);
	}

	public IRandomList<RegistryEntry<Block>> logBlocks            () { return this.getBlocks(WoodPaletteType.LOG              ); }
	public IRandomList<RegistryEntry<Block>> woodBlocks           () { return this.getBlocks(WoodPaletteType.WOOD             ); }
	public IRandomList<RegistryEntry<Block>> strippedLogBlocks    () { return this.getBlocks(WoodPaletteType.STRIPPED_LOG     ); }
	public IRandomList<RegistryEntry<Block>> strippedWoodBlocks   () { return this.getBlocks(WoodPaletteType.STRIPPED_WOOD    ); }
	public IRandomList<RegistryEntry<Block>> planksBlocks         () { return this.getBlocks(WoodPaletteType.PLANKS           ); }
	public IRandomList<RegistryEntry<Block>> stairsBlocks         () { return this.getBlocks(WoodPaletteType.STAIRS           ); }
	public IRandomList<RegistryEntry<Block>> slabBlocks           () { return this.getBlocks(WoodPaletteType.SLAB             ); }
	public IRandomList<RegistryEntry<Block>> fenceBlocks          () { return this.getBlocks(WoodPaletteType.FENCE            ); }
	public IRandomList<RegistryEntry<Block>> fenceGateBlocks      () { return this.getBlocks(WoodPaletteType.FENCE_GATE       ); }
	public IRandomList<RegistryEntry<Block>> doorBlocks           () { return this.getBlocks(WoodPaletteType.DOOR             ); }
	public IRandomList<RegistryEntry<Block>> trapdoorBlocks       () { return this.getBlocks(WoodPaletteType.TRAPDOOR         ); }
	public IRandomList<RegistryEntry<Block>> pressurePlateBlocks  () { return this.getBlocks(WoodPaletteType.PRESSURE_PLATE   ); }
	public IRandomList<RegistryEntry<Block>> buttonBlocks         () { return this.getBlocks(WoodPaletteType.BUTTON           ); }
	public IRandomList<RegistryEntry<Block>> leavesBlocks         () { return this.getBlocks(WoodPaletteType.LEAVES           ); }
	public IRandomList<RegistryEntry<Block>> saplingBlocks        () { return this.getBlocks(WoodPaletteType.SAPLING          ); }
	public IRandomList<RegistryEntry<Block>> pottedSaplingBlocks  () { return this.getBlocks(WoodPaletteType.POTTED_SAPLING   ); }
	public IRandomList<RegistryEntry<Block>> standingSignBlocks   () { return this.getBlocks(WoodPaletteType.STANDING_SIGN    ); }
	public IRandomList<RegistryEntry<Block>> wallSignBlocks       () { return this.getBlocks(WoodPaletteType.WALL_SIGN        ); }
	public IRandomList<RegistryEntry<Block>> hangingSignBlocks    () { return this.getBlocks(WoodPaletteType.HANGING_SIGN     ); }
	public IRandomList<RegistryEntry<Block>> wallHangingSignBlocks() { return this.getBlocks(WoodPaletteType.WALL_HANGING_SIGN); }

	//////////////////////////////// states ////////////////////////////////

	public BlockState getState(RandomGenerator random, WoodPaletteType type) {
		return this.getBlock(random, type).getDefaultState();
	}

	public BlockState logState(RandomGenerator random, Axis axis) {
		BlockState state = this.getState(random, WoodPaletteType.LOG);
		state = state.withIfExists(Properties.AXIS, axis);
		return state;
	}

	public BlockState woodState(RandomGenerator random, Axis axis) {
		BlockState state = this.getState(random, WoodPaletteType.WOOD);
		state = state.withIfExists(Properties.AXIS, axis);
		return state;
	}

	public BlockState strippedLogState(RandomGenerator random, Axis axis) {
		BlockState state = this.getState(random, WoodPaletteType.STRIPPED_LOG);
		state = state.withIfExists(Properties.AXIS, axis);
		return state;
	}

	public BlockState strippedWoodState(RandomGenerator random, Axis axis) {
		BlockState state = this.getState(random, WoodPaletteType.STRIPPED_WOOD);
		state = state.withIfExists(Properties.AXIS, axis);
		return state;
	}

	public BlockState planksState(RandomGenerator random) {
		return this.getState(random, WoodPaletteType.PLANKS);
	}

	public BlockState stairsState(RandomGenerator random, Direction facing, BlockHalf half, StairShape shape, boolean waterlogged) {
		BlockState state = this.getState(random, WoodPaletteType.STAIRS);
		state = state.withIfExists(Properties.HORIZONTAL_FACING, facing);
		state = state.withIfExists(Properties.BLOCK_HALF, half);
		state = state.withIfExists(Properties.STAIR_SHAPE, shape);
		state = state.withIfExists(Properties.WATERLOGGED, waterlogged);
		return state;
	}

	public BlockState slabState(RandomGenerator random, BlockHalf half, boolean waterlogged) {
		BlockState state = this.getState(random, WoodPaletteType.SLAB);
		state = state.withIfExists(Properties.BLOCK_HALF, half);
		state = state.withIfExists(Properties.WATERLOGGED, waterlogged);
		return state;
	}

	public BlockState fenceState(RandomGenerator random, boolean north, boolean east, boolean south, boolean west, boolean waterlogged) {
		BlockState state = this.getState(random, WoodPaletteType.FENCE);
		state = state.withIfExists(Properties.NORTH, north);
		state = state.withIfExists(Properties.EAST, east);
		state = state.withIfExists(Properties.SOUTH, south);
		state = state.withIfExists(Properties.WEST, west);
		state = state.withIfExists(Properties.WATERLOGGED, waterlogged);
		return state;
	}

	public BlockState fenceGateState(RandomGenerator random, Direction facing, boolean open, boolean in_wall, boolean powered) {
		BlockState state = this.getState(random, WoodPaletteType.FENCE_GATE);
		state = state.withIfExists(Properties.HORIZONTAL_FACING, facing);
		state = state.withIfExists(Properties.OPEN, open);
		state = state.withIfExists(Properties.IN_WALL, in_wall);
		state = state.withIfExists(Properties.POWERED, powered);
		return state;
	}

	public BlockState doorState(RandomGenerator random, Direction facing, DoubleBlockHalf half, DoorHinge hinge, boolean open, boolean powered) {
		BlockState state = this.getState(random, WoodPaletteType.DOOR);
		state = state.withIfExists(Properties.HORIZONTAL_FACING, facing);
		state = state.withIfExists(Properties.DOUBLE_BLOCK_HALF, half);
		state = state.withIfExists(Properties.DOOR_HINGE, hinge);
		state = state.withIfExists(Properties.OPEN, open);
		state = state.withIfExists(Properties.POWERED, powered);
		return state;
	}

	public BlockState trapdoorState(RandomGenerator random, Direction facing, BlockHalf half, boolean open, boolean powered, boolean waterlogged) {
		BlockState state = this.getState(random, WoodPaletteType.TRAPDOOR);
		state = state.withIfExists(Properties.HORIZONTAL_FACING, facing);
		state = state.withIfExists(Properties.BLOCK_HALF, half);
		state = state.withIfExists(Properties.OPEN, open);
		state = state.withIfExists(Properties.POWERED, powered);
		state = state.withIfExists(Properties.WATERLOGGED, waterlogged);
		return state;
	}

	public BlockState pressurePlateState(RandomGenerator random, boolean powered) {
		BlockState state = this.getState(random, WoodPaletteType.PRESSURE_PLATE);
		state = state.withIfExists(Properties.POWERED, powered);
		return state;
	}

	public BlockState leavesState(RandomGenerator random, @Range(from = 1, to = 7) int distance, boolean persistent, boolean waterlogged) {
		BlockState state = this.getState(random, WoodPaletteType.LEAVES);
		state = state.withIfExists(Properties.DISTANCE_1_7, distance);
		state = state.withIfExists(Properties.PERSISTENT, persistent);
		state = state.withIfExists(Properties.WATERLOGGED, waterlogged);
		return state;
	}

	public BlockState saplingState(RandomGenerator random, @Range(from = 0, to = 1) int stage) {
		BlockState state = this.getState(random, WoodPaletteType.SAPLING);
		state = state.withIfExists(Properties.STAGE, stage);
		return state;
	}

	public BlockState pottedSaplingState(RandomGenerator random) {
		return this.getState(random, WoodPaletteType.POTTED_SAPLING);
	}

	//////////////////////////////// types ////////////////////////////////

	public static enum WoodPaletteType implements StringIdentifiable {
		LOG,
		WOOD,
		STRIPPED_LOG,
		STRIPPED_WOOD,
		PLANKS,
		STAIRS,
		SLAB,
		FENCE,
		FENCE_GATE,
		DOOR,
		TRAPDOOR,
		PRESSURE_PLATE,
		BUTTON,
		LEAVES,
		SAPLING,
		POTTED_SAPLING,
		STANDING_SIGN,
		WALL_SIGN,
		HANGING_SIGN,
		WALL_HANGING_SIGN,
		;

		public static final WoodPaletteType[] VALUES = values();
		public static final Map<String, WoodPaletteType> LOWER_CASE_LOOKUP = (
			Arrays
			.stream(VALUES)
			.collect(
				Collectors.toMap(
					(WoodPaletteType type) -> type.lowerCaseName,
					Function.identity()
				)
			)
		);

		public final String lowerCaseName = this.name().toLowerCase(Locale.ROOT);

		@Override
		public String asString() {
			return this.lowerCaseName;
		}
	}
}