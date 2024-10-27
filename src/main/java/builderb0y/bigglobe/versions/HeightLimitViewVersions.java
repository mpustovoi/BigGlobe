package builderb0y.bigglobe.versions;

import net.minecraft.world.HeightLimitView;

public class HeightLimitViewVersions {

	public static int getTopY(HeightLimitView view) {
		return view.getBottomY() + view.getHeight();
	}
}