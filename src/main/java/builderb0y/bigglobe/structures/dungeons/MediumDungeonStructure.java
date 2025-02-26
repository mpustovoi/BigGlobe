package builderb0y.bigglobe.structures.dungeons;

import java.util.List;
import java.util.random.RandomGenerator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.structure.StructureContext;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.structure.StructureType;

import builderb0y.autocodec.annotations.VerifyNullable;
import builderb0y.bigglobe.blocks.BlockStates;
import builderb0y.bigglobe.codecs.BigGlobeAutoCodec;
import builderb0y.bigglobe.columns.scripted.ColumnScript.ColumnToIntScript;
import builderb0y.bigglobe.columns.scripted.ScriptedColumn;
import builderb0y.bigglobe.math.BigGlobeMath;
import builderb0y.bigglobe.mixins.MobSpawnerLogic_GettersAndSettersForEverything;
import builderb0y.bigglobe.randomLists.IRandomList;
import builderb0y.bigglobe.structures.BigGlobeStructures;
import builderb0y.bigglobe.structures.LabyrinthLayout;
import builderb0y.bigglobe.util.DelayedEntryList;
import builderb0y.bigglobe.util.coordinators.CoordinateFunctions.CoordinateSupplier;
import builderb0y.bigglobe.util.coordinators.Coordinator;
import builderb0y.bigglobe.versions.BlockStateVersions;

public class MediumDungeonStructure extends AbstractDungeonStructure {

	#if MC_VERSION >= MC_1_20_5
		public static final MapCodec<MediumDungeonStructure> CODEC = BigGlobeAutoCodec.AUTO_CODEC.createDFUMapCodec(MediumDungeonStructure.class);
	#else
		public static final Codec<MediumDungeonStructure> CODEC = BigGlobeAutoCodec.AUTO_CODEC.createDFUMapCodec(MediumDungeonStructure.class).codec();
	#endif

	public MediumDungeonStructure(
		Config config,
		ColumnToIntScript.@VerifyNullable Holder surface_y,
		@VerifyNullable DelayedEntryList<ConfiguredFeature<?, ?>> room_decorators,
		IRandomList<RegistryEntry<EntityType<?>>> spawner_entries,
		List<Palette> palettes
	) {
		super(config, surface_y, room_decorators, spawner_entries, palettes);
	}

	@Override
	public DungeonLayout layout(ScriptedColumn column, int y, RandomGenerator random) {
		return new Layout(column, y, random, this.room_decorators, this.spawner_entries, this.palettes);
	}

	@Override
	public StructureType<?> getType() {
		return BigGlobeStructures.MEDIUM_DUNGEON_TYPE;
	}

	public static class Layout extends DungeonLayout {

		public Layout(
			ScriptedColumn column,
			int y,
			RandomGenerator random,
			@Nullable DelayedEntryList<ConfiguredFeature<?, ?>> roomDecorators,
			IRandomList<RegistryEntry<EntityType<?>>> spawnerEntries,
			List<Palette> palettes
		) {
			super(column, y, random, (random.nextInt() & 255) + 128, roomDecorators, spawnerEntries, palettes);
		}

		@Override
		public RoomDungeonPiece newRoom() {
			return new Room(BigGlobeStructures.MEDIUM_DUNGEON_ROOM_TYPE, this.palette, this.random, this.roomDecorators);
		}

		@Override
		public HallDungeonPiece newHall(RoomPiece room1, RoomPiece room2, Direction direction) {
			return Hall.create((Room)(room1), (Room)(room2), direction, this.random);
		}

		@Override
		public int distanceBetweenRooms() {
			return 8;
		}

		@Override
		public boolean isValidPosition(RoomPiece next) {
			return (
				BigGlobeMath.squareI(
					next.x() - this.centerX,
					next.z() - this.centerZ
				)
				< (this.random.nextInt() & 2047)
			);
		}

		@Override
		public int maxHeightDifference() {
			return 2;
		}
	}

	public static class Room extends RoomDungeonPiece {

		public Room(StructurePieceType type, Palette palette, RandomGenerator random, @Nullable DelayedEntryList<ConfiguredFeature<?, ?>> decorators) {
			super(type, 0, null, palette, decorators);
			this.setPit((random.nextInt() & 15) == 0);
			this.setPos(0, 0, 0);
		}

		public Room(StructurePieceType type, StructureContext context, NbtCompound nbt) {
			super(type, context, nbt);
		}

		@Override
		public void setPos(int x, int y, int z) {
			this.boundingBox = new BlockBox(x - 3, this.hasPit() ? y - 2 : y, z - 3, x + 3, y + 5, z + 3);
		}

		@Override
		public void generate(
			StructureWorldAccess world,
			StructureAccessor structureAccessor,
			ChunkGenerator chunkGenerator,
			Random random,
			BlockBox chunkBox,
			ChunkPos chunkPos,
			BlockPos pivot
		) {
			super.generate(
				world,
				structureAccessor,
				chunkGenerator,
				random,
				chunkBox,
				chunkPos,
				pivot
			);
			if (!this.hasPit() && this.support) {
				BlockPos.Mutable pos = new BlockPos.Mutable();
				int x = this.x(), y = this.y() - 1, z = this.z();
				Coordinator rotator = this.coordinator(world, chunkBox).rotate4x90();
				rotator.setBlockStateLine(-1, -1, -3, 0, 0, 3, 3, this.palette().stairsSupplier(BlockHalf.TOP, Direction.EAST, StairShape.STRAIGHT));
				rotator.setBlockState(-2, -1, 0, this.palette().stairsSupplier(BlockHalf.TOP, Direction.WEST, StairShape.STRAIGHT));
				this.generateDown(world, pos.set(x,     y, z    ), chunkBox);
				this.generateDown(world, pos.set(x - 3, y, z    ), chunkBox);
				this.generateDown(world, pos.set(x + 3, y, z    ), chunkBox);
				this.generateDown(world, pos.set(x,     y, z - 3), chunkBox);
				this.generateDown(world, pos.set(x,     y, z + 3), chunkBox);
			}
		}

		public void generateDown(StructureWorldAccess world, BlockPos.Mutable pos, BlockBox chunkBox) {
			if (chunkBox.contains(pos)) {
				CoordinateSupplier<BlockState> mainSupplier = this.palette().mainSupplier();
				while (true) {
					if (BlockStateVersions.isReplaceable(world.getBlockState(pos))) {
						world.setBlockState(pos, mainSupplier.get(pos), Block.NOTIFY_ALL);
						if (world.isOutOfHeightLimit(pos.setY(pos.getY() - 1))) break;
					}
					else {
						break;
					}
				}
			}
		}

		@Override
		public void addDecorations(LabyrinthLayout layout) {
			super.addDecorations(layout);
			Direction deadEndDirection;
			if (this.hasPit()) {
				layout.decorations.add(new PitDungeonPiece(BigGlobeStructures.DUNGEON_PIT_TYPE, this.x(), this.y(), this.z(), this.palette, layout.random.nextInt(3), layout.random));
				this.decorators = null;
			}
			else if ((deadEndDirection = this.getDeadEndDirection()) != null) {
				if (layout.random.nextBoolean()) {
					layout.decorations.add(new ChestPiece(BigGlobeStructures.MEDIUM_DUNGEON_CHEST_TYPE, this.x(), this.y() + 1, this.z(), this.palette, deadEndDirection, layout.random.nextLong()));
					this.decorators = null;
				}
			}
			else {
				if ((layout.random.nextInt() & 15) == 0) {
					layout.decorations.add(new SpawnerPiece(BigGlobeStructures.MEDIUM_DUNGEON_SPAWNER_TYPE, this.x(), this.y() + 1, this.z(), this.palette, ((Layout)(layout)).spawnerEntries.getRandomElement(layout.random)));
					this.decorators = null;
				}
			}
		}
	}

	public static class ChestPiece extends ChestDungeonPiece {

		public ChestPiece(StructurePieceType type, int x, int y, int z, Palette palette, Direction facing, long seed) {
			super(type, 0, new BlockBox(x - 1, y, z - 1, x + 1, y + 1, z + 1), palette, facing, seed);
		}

		public ChestPiece(StructurePieceType type, StructureContext context, NbtCompound nbt) {
			super(type, context, nbt);
		}

		@Override
		public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
			Coordinator root = this.coordinator(world, chunkBox);
			Palette palette = this.palette();
			root.setBlockStateLine(-1, 0, 0, 1, 0, 0, 3, palette.mainSupplier());
			root.setBlockStateAndBlockEntity(0, 1, 0, Blocks.CHEST.getDefaultState(), ChestBlockEntity.class, this::initChest);
			root.flip4XZ().setBlockState(1, 0, 1, palette.stairsSupplier(BlockHalf.BOTTOM, Direction.NORTH, StairShape.STRAIGHT));
			root.flip2X().setBlockState(1, 1, 0, palette.stairsSupplier(BlockHalf.BOTTOM, Direction.WEST, StairShape.STRAIGHT));
		}
	}

	public static class SpawnerPiece extends SpawnerDungeonPiece {

		public SpawnerPiece(StructurePieceType type, int x, int y, int z, Palette palette, RegistryEntry<EntityType<?>> spawnerType) {
			super(type, 0, new BlockBox(x - 1, y, z - 1, x + 1, y + 1, z + 1), palette, spawnerType);
		}

		public SpawnerPiece(StructurePieceType type, StructureContext context, NbtCompound nbt) {
			super(type, context, nbt);
		}

		@Override
		public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
			Coordinator root = this.coordinator(world, chunkBox);
			Palette palette = this.palette();
			root.setBlockState(0, 0, 0, palette.mainSupplier());
			root.setBlockStateAndBlockEntity(0, 1, 0, Blocks.SPAWNER.getDefaultState(), MobSpawnerBlockEntity.class, this::initSpawner);
			root.rotate4x90().setBlockState(1, 0, 0, palette.stairsSupplier(BlockHalf.BOTTOM, Direction.WEST, StairShape.STRAIGHT));
		}

		@Override
		public void initSpawner(BlockPos pos, MobSpawnerBlockEntity spawner) {
			super.initSpawner(pos, spawner);
			MobSpawnerLogic_GettersAndSettersForEverything logic = (MobSpawnerLogic_GettersAndSettersForEverything)(spawner.getLogic());
			logic.bigglobe_setRequiredPlayerRange(24);
			logic.bigglobe_setMaxNearbyEntities(4);
			logic.bigglobe_setSpawnCount(3);
		}
	}

	public static abstract class Hall extends HallDungeonPiece {

		public Hall(StructurePieceType type, int x, int y, int z, Palette palette, Direction direction, RandomGenerator random) {
			super(type, 0, new BlockBox(x - 3, y, z - 3, x + 3, y + 5, z + 3), palette);
			this.setOrientation(direction);
			this.setBars((random.nextInt() & 7) == 0);
			int width = random.nextInt(5) + 1;
			int position = random.nextInt(6 - width) - 2;
			this.setLeft(position);
			this.setRight(position + width - 1);
		}

		public Hall(StructurePieceType type, StructureContext context, NbtCompound nbt) {
			super(type, context, nbt);
		}

		public static Hall create(Room from, Room to, Direction direction, RandomGenerator random) {
			return create(
				(from.x() + to.x()) >> 1,
				Math.min(from.y(), to.y()),
				(from.z() + to.z()) >> 1,
				from.palette,
				direction,
				to.y() - from.y(),
				random
			);
		}

		public static Hall create(int x, int y, int z, Palette palette, Direction direction, int step, RandomGenerator random) {
			if (step != 0) y++;
			return switch (step) {
				case -2 -> new Hall2(BigGlobeStructures.MEDIUM_DUNGEON_HALL2_TYPE, x, y, z, palette, direction.getOpposite(), random);
				case -1 -> new Hall1(BigGlobeStructures.MEDIUM_DUNGEON_HALL1_TYPE, x, y, z, palette, direction.getOpposite(), random);
				case  0 -> new Hall0(BigGlobeStructures.MEDIUM_DUNGEON_HALL0_TYPE, x, y, z, palette, direction, random);
				case  1 -> new Hall1(BigGlobeStructures.MEDIUM_DUNGEON_HALL1_TYPE, x, y, z, palette, direction, random);
				case  2 -> new Hall2(BigGlobeStructures.MEDIUM_DUNGEON_HALL2_TYPE, x, y, z, palette, direction, random);
				default -> throw new IllegalArgumentException(Integer.toString(step));
			};
		}
	}

	public static class Hall0 extends Hall {

		public Hall0(StructurePieceType type, int x, int y, int z, Palette palette, Direction direction, RandomGenerator random) {
			super(type, x, y, z, palette, direction, random);
		}

		public Hall0(StructurePieceType type, StructureContext context, NbtCompound nbt) {
			super(type, context, nbt);
		}

		@Override
		public void generateRaw(Context context) {
			Coordinator root = this.coordinator(context);
			Palette palette = this.palette();
			int left = this.getLeft(), right = this.getRight();
			root.setBlockStateLine(0, 0, left - 1, 0, 1, 0, 6, palette.mainSupplier());
			root.setBlockStateLine(0, 0, right + 1, 0, 1, 0, 6, palette.mainSupplier());
			root.stack(0, 5, 0, 2).setBlockStateLine(0, 0, left, 0, 0, 1, right - left + 1, palette.mainSupplier());
			root.setBlockStateCuboid(0, 1, left, 0, 4, right, this.hasBars() ? palette.barsSupplier(true, false, true, false) : palette.air());
			root.flip2X().setBlockStateCuboid(1, 1, left, 2, 4, right, BlockStates.AIR);
		}
	}

	public static class Hall1 extends Hall {

		public Hall1(StructurePieceType type, int x, int y, int z, Palette palette, Direction direction, RandomGenerator random) {
			super(type, x, y, z, palette, direction, random);
		}

		public Hall1(StructurePieceType type, StructureContext context, NbtCompound nbt) {
			super(type, context, nbt);
		}

		@Override
		public void generateRaw(Context context) {
			Coordinator root = this.coordinator(context);
			Palette palette = this.palette();
			int left = this.getLeft(), right = this.getRight();
			root.setBlockStateLine(0, 0, left - 1, 0, 1, 0, 5, palette.mainSupplier());
			root.setBlockStateLine(0, 0, right + 1, 0, 1, 0, 5, palette.mainSupplier());
			root.setBlockStateLine(0, 0, left, 0, 0, 1, right - left + 1, palette.slabSupplier(SlabType.BOTTOM));
			root.setBlockStateLine(0, 4, left, 0, 0, 1, right - left + 1, palette.slabSupplier(SlabType.TOP));
			root.setBlockStateCuboid(0, 1, left, 0, 3, right, BlockStates.AIR);
			root.stack(3, 1, 0, 2).setBlockStateCuboid(-2, 0, left, -1, 3, right, BlockStates.AIR);
		}
	}

	public static class Hall2 extends Hall {

		public Hall2(StructurePieceType type, int x, int y, int z, Palette palette, Direction direction, RandomGenerator random) {
			super(type, x, y, z, palette, direction, random);
		}

		public Hall2(StructurePieceType type, StructureContext context, NbtCompound nbt) {
			super(type, context, nbt);
		}

		@Override
		public void generateRaw(Context context) {
			Coordinator root = this.coordinator(context);
			Palette palette = this.palette();
			int left = this.getLeft(), right = this.getRight();
			root.setBlockStateLine(0, 0, left - 1, 0, 1, 0, 6, palette.mainSupplier());
			root.setBlockStateLine(0, 0, right + 1, 0, 1, 0, 6, palette.mainSupplier());
			root.stack(0, 5, 0, 2).setBlockStateLine(0, 0, left, 0, 0, 1, right - left + 1, palette.mainSupplier());
			root.setBlockStateCuboid(0, 1, left, 0, 4, right, this.hasBars() ? palette.barsSupplier(true, false, true, false) : palette.air());
			root.setBlockStateCuboid(-1, 1, left, -1, 3, right, BlockStates.AIR);
			root.setBlockStateCuboid(-2, 0, left, -2, 3, right, BlockStates.AIR);
			root.setBlockStateCuboid(1, 2, left, 1, 4, right, BlockStates.AIR);
			root.setBlockStateCuboid(2, 2, left, 2, 5, right, BlockStates.AIR);
			root.stack(2, 1, 0, 2).setBlockStateLine(-1, 0, left, 0, 0, 1, right - left + 1, palette.slabSupplier(SlabType.BOTTOM));
			root.stack(2, 1, 0, 2).setBlockStateLine(-1, 4, left, 0, 0, 1, right - left + 1, palette.slabSupplier(SlabType.TOP));
		}
	}
}