package builderb0y.bigglobe.hyperspace;

import java.util.UUID;

import it.unimi.dsi.fastutil.Hash;

import builderb0y.autocodec.util.HashStrategies;

/**
data about a specific waypoint.
this data includes things like who owns it, where it is, and so on.
*/
public interface WaypointData {

	public static final Hash.Strategy<WaypointData> ID_STRATEGY = HashStrategies.of(
		WaypointData::id,
		(WaypointData a, WaypointData b) -> a.id() == b.id()
	);

	public abstract int id();

	public abstract UUID owner();

	public abstract PackedWorldPos destinationPosition();

	public abstract PackedWorldPos displayPosition();
}