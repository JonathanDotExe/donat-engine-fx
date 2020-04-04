package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public class TwoDimensionalPerspective implements Perspective{

	@Override
	public Vector2D toScreenPosition(Vector3D pos, Vector3D rotation) {
		return ((Math.abs(rotation.getX()) + 45) % 180) > 90 ? new Vector2D(pos.getX(), pos.getZ()) : new Vector2D(pos.getX(), pos.getY());
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	@Override
	public OptimizationLevel getOptimizationLevel() {
		return OptimizationLevel.FULL;
	}

}
