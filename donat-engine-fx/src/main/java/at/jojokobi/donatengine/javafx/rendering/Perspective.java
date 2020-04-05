package at.jojokobi.donatengine.javafx.rendering;

import java.util.Comparator;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public interface Perspective {
	
	public enum OptimizationLevel {
		NONE, ONLY_YZ, FULL;
	}

	public Vector2D toScreenPosition (Vector3D pos, Vector3D rotation);
	
	public OptimizationLevel getOptimizationLevel ();
	
	public Comparator<RenderData> getComparator (Camera cam, RessourceHandler ressourceHandler, double pixelsPerMeter);
	
	public default Vector3D getCenterRelative(Vector3D pos, Camera cam) {
		return pos.clone().subtract(cam.getX(), cam.getY(), cam.getZ());
	}

	public default Vector2D centerRelativeToEdgeRelative(Vector2D pos, Camera cam) {
		return new Vector2D(pos.getX() + cam.getViewWidth() / 2, pos.getY() + cam.getViewHeight() / 2);
	}

	public default Vector2D toScreenPosition(Camera cam, Vector3D pos) {
		return centerRelativeToEdgeRelative(toScreenPosition(getCenterRelative(pos, cam), cam.getRotation()), cam);
	}

}
