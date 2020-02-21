package at.jojokobi.donatengine.javafx.rendering;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
import javafx.scene.canvas.GraphicsContext;

public class DefaultRenderer implements Renderer {
	
	private static Logger logger = Logger.getGlobal();
	
	private Map<Class<? extends RenderData>, DataRenderer<?>> renderers = new HashMap<>();
	private RessourceHandler ressourceHandler;
	private double pixelsPerMeter;
	
	

	public DefaultRenderer(RessourceHandler ressourceHandler, double pixelsPerMeter) {
		super();
		this.ressourceHandler = ressourceHandler;
		this.pixelsPerMeter = pixelsPerMeter;
		
		putRenderer(new BackgroundRenderer());
		putRenderer(new ImageRenderer());
		putRenderer(new ModelRenderer());
		putRenderer(new ModelShadowRenderer());
		putRenderer(new CanvasRenderer());
		putRenderer(new ScreenCanvasRenderer());
	}

	@Override
	public void render(List<RenderData> data, Camera cam, GraphicsContext ctx) {
		Perspective perspective = getPerspective(cam);
		RenderContext context = new RenderContext(ctx, cam, perspective, ressourceHandler, pixelsPerMeter);
		ctx.clearRect(0, 0, cam.getViewWidth(), cam.getViewHeight());
		for (RenderData r : data) {
			DataRenderer<?> renderer = renderers.get(r.getClass());
			if (renderer == null) {
				logger.log(Level.WARNING, "There is no render specified for the object " + r + "! Please ensure you have implemented a DataRenderer for this object.");
			}
			else {
				renderer.renderUnsafe(r, context);
			}
		}
	}
	
	private static Perspective getPerspective (Camera cam) {
		Perspective perspective = null;
		for (String attribute : cam.getAttributes()) {
			switch (attribute) {
			case "orthographic":
				perspective = new StretchYZPerspective();
				break;
			case "perspective":
				perspective = new ThreeDimensionalPerspective();
				break;
			}
			if (perspective != null) {
				break;
			}
		}
		if (perspective == null) {
			perspective = new TwoDimensionalPerspective();
		}
		return perspective;
	}

	public void putRenderer (DataRenderer<?> renderer) {
		renderers.put(renderer.getDataClass(), renderer);
	}

}
