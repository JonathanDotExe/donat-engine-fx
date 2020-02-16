package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.objects.Camera;
import javafx.scene.canvas.GraphicsContext;

public abstract class RenderModel {
	
	public abstract void render (GraphicsContext ctx, Camera cam, Perspective perspective, double x, double y, double z);
	
	public abstract double getWidth ();
	
	public abstract double getHeight ();
	
	public abstract double getLength ();
	
//	@Deprecated
//	public double getRenderingZ (Camera cam, double z) {
//		double relZ = z - cam.getZ();
//		return relZ * cam.getZMultiplier();
//	}
//	
//	@Deprecated
//	public double getRenderingY (Camera cam, double y) {
//		double relY = cam.getHeight() - (y - cam.getY());
//		return relY * cam.getYMultiplier();
//	}

}
