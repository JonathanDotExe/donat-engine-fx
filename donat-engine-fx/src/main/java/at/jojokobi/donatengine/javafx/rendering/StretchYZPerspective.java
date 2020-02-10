package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public class StretchYZPerspective implements Perspective {

	@Override
	public Vector2D toScreenPosition(Vector3D pos, Vector3D rotation) {
		return new Vector2D(pos.getX(), pos.getY() * Math.cos(Math.toRadians(rotation.getX())) + pos.getZ() * Math.sin(Math.toRadians(rotation.getX())));
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	@Override
	public OptimizationLevel getOptimizationLevel() {
		return OptimizationLevel.ONLY_YZ;
	}

}
