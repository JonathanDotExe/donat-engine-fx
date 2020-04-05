package at.jojokobi.donatengine.javafx.rendering;

import java.util.Comparator;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;

public class ThreeDimensionalPerspective implements Perspective {

	private double xScalar = 32 * 20;

	//		return new Vector2D(pos.getX() * (2 - Math.pow(Math.cos(Math.toRadians(rotation.getX())), pos.getZ() / xScalar)),
	@Override
	public Vector2D toScreenPosition(Vector3D pos, Vector3D rotation) {
		return new Vector2D(pos.getX() * (1 - (rotation.getX() * -pos.getZ()/xScalar)/90.0),
				pos.getY() * Math.cos(Math.toRadians(rotation.getX()))
						+ pos.getZ() * Math.sin(Math.toRadians(rotation.getX())));
		/*
		 return new Vector2D(pos.getX() * (1 - Math.sin(Math.toRadians(rotation.getX()) * -pos.getZ()/xScalar)),
				pos.getY() * Math.cos(Math.toRadians(rotation.getX()))
						+ pos.getZ() * Math.sin(Math.toRadians(rotation.getX())));
		 */
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	public static double interpolate (double d1, double d2, double prog) {
		return d1 * (1-prog) + d2 * prog;
	}
	
	@Override
	public OptimizationLevel getOptimizationLevel() {
		return OptimizationLevel.NONE;
	}

	@Override
	public Comparator<RenderData> getComparator(Camera cam, RessourceHandler ressourceHandler, double pixelsPerMeter) {
		return new DataComparator(cam, ressourceHandler, pixelsPerMeter);
	}
	
}
