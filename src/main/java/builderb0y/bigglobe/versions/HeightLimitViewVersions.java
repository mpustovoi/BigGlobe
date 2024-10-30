package builderb0y.bigglobe.versions;

import net.minecraft.world.HeightLimitView;

public class HeightLimitViewVersions {

	public static int getMinY(HeightLimitView view) {
		return view.getBottomY();
	}

	public static int getMaxY(HeightLimitView view) {
		return view.getBottomY() + view.getHeight();
	}

	public static int getHeight(HeightLimitView view) {
		return view.getHeight();
	}

	public static int getSectionMinY(HeightLimitView view) {
		return getMinY(view) >> 4;
	}

	public static int getSectionMaxY(HeightLimitView view) {
		return getMaxY(view) >> 4;
	}

	public static int getSectionHeight(HeightLimitView view) {
		return getHeight(view) >> 4;
	}
}