package builderb0y.bigglobe.versions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import builderb0y.bigglobe.math.BigGlobeMath;

public class EntityVersions {

	public static World getWorld(Entity entity) {
		return entity.getWorld();
	}

	public static ServerWorld getServerWorld(ServerPlayerEntity player) {
		return player.getServerWorld();
	}

	public static boolean isOnGround(Entity entity) {
		return entity.isOnGround();
	}

	public static ItemStack getAmmunition(PlayerEntity player, ItemStack weapon) {
		return player.getProjectileType(weapon);
	}

	public static double getBlockReachDistance(PlayerEntity player) {
		#if MC_VERSION >= MC_1_20_5
			return player.getAttributeValue(EntityAttributes.PLAYER_BLOCK_INTERACTION_RANGE);
		#else
			return 8.0D;
		#endif
	}

	public static double getBlockReachDistanceSquared(PlayerEntity player) {
		return BigGlobeMath.squareD(getBlockReachDistance(player));
	}

	public static double getEntityReachDistance(PlayerEntity player) {
		#if MC_VERSION >= MC_1_20_5
			return player.getAttributeValue(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE);
		#else
			return 8.0D;
		#endif
	}

	public static double getEntityReachDistanceSquared(PlayerEntity player) {
		return BigGlobeMath.squareD(getEntityReachDistance(player));
	}

	public static void setPortalCooldown(Entity entity, int cooldown) {
		entity.setPortalCooldown(cooldown);
	}

	public static Box getBoundingBox(EntityType<?> type, double x, double y, double z) {
		return type. #if MC_VERSION >= MC_1_20_5 getSpawnBox #else createSimpleBoundingBox #endif (x, y, z);
	}
}