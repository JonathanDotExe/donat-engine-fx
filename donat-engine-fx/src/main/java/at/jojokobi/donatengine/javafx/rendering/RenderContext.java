package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import javafx.scene.canvas.GraphicsContext;

public class RenderContext {
	
	private GraphicsContext ctx;
	private Camera cam;
	private Perspective perspective;
	private RessourceHandler ressourceHandler;
	private double pixelsPerMeter;
	
	public RenderContext(GraphicsContext ctx, Camera cam, Perspective perspective, RessourceHandler ressourceHandler,
			double pixelsPerMeter) {
		super();
		this.ctx = ctx;
		this.cam = cam;
		this.perspective = perspective;
		this.ressourceHandler = ressourceHandler;
		this.pixelsPerMeter = pixelsPerMeter;
	}
	public GraphicsContext getCtx() {
		return ctx;
	}
	public Camera getCam() {
		return cam;
	}
	public Perspective getPerspective() {
		return perspective;
	}
	public RessourceHandler getRessourceHandler() {
		return ressourceHandler;
	}
	public double getPixelsPerMeter() {
		return pixelsPerMeter;
	}

}
