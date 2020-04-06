package at.jojokobi.donatengine.javafx.rendering;

import java.util.Comparator;

import at.jojokobi.donatengine.javafx.ressources.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public class Retro3DPerspective implements Perspective {

	@Override
	public Vector2D toScreenPosition(Vector3D pos, Vector3D rotation) {
		return new Vector2D(pos.getX(), pos.getZ() - pos.getY());
	}

	@Override
	public OptimizationLevel getOptimizationLevel() {
		return OptimizationLevel.FULL;
	}

	@Override
	public Comparator<RenderData> getComparator(Camera cam, RessourceHandler ressourceHandler, double pixelsPerMeter) {
		return new DataComparator(cam, ressourceHandler, pixelsPerMeter);
	}

}
