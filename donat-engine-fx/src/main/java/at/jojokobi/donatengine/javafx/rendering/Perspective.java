package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public interface Perspective {
	
	public enum OptimizationLevel {
		NONE, ONLY_YZ, FULL;
	}

	public Vector2D toScreenPosition (Vector3D pos, Vector3D rotation);
	
	public OptimizationLevel getOptimizationLevel ();

}
