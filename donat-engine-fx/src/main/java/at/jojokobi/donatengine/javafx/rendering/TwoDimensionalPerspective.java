package at.jojokobi.donatengine.javafx.rendering;

import java.util.Comparator;

import at.jojokobi.donatengine.javafx.ressources.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
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

	@Override
	public Comparator<RenderData> getComparator(Camera cam, RessourceHandler ressourceHandler, double pixelsPerMeter) {
		if (((Math.abs(cam.getRotationX()) + 45) % 180) > 90) {
			return new Comparator<RenderData>() {
				@Override
				public int compare(RenderData d1, RenderData d2) {
					RenderMetaData o1 = DefaultRenderer.getMetaData(d1, ressourceHandler, pixelsPerMeter);
					RenderMetaData o2 = DefaultRenderer.getMetaData(d2, ressourceHandler, pixelsPerMeter);
					int cmp = Double.compare(o1.getPriority(), o2.getPriority());
					if (cmp == 0) {
						cmp = Double.compare(o1.getY() + o1.getHeight(), o2.getY() + o2.getHeight());
					}
					return cmp;
				}
			};
		}
		else {
			return new Comparator<RenderData>() {
				@Override
				public int compare(RenderData d1, RenderData d2) {
					RenderMetaData o1 = DefaultRenderer.getMetaData(d1, ressourceHandler, pixelsPerMeter);
					RenderMetaData o2 = DefaultRenderer.getMetaData(d2, ressourceHandler, pixelsPerMeter);
					
					int cmp = Double.compare(o1.getPriority(), o2.getPriority());
					if (cmp == 0) {
						cmp = Double.compare(o1.getZ(), o2.getZ());
					}
					return cmp;
				}
			};
		}
	}

}
