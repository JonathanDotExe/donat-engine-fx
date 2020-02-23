package at.jojokobi.donatengine.javafx.rendering;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.BackgroundRenderData;
import at.jojokobi.donatengine.rendering.ModelRenderData;
import at.jojokobi.donatengine.rendering.PositionedRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.ScreenPositonedRenderData;
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
		cam.setX(cam.getX() * pixelsPerMeter);
		cam.setY(cam.getY() * pixelsPerMeter);
		cam.setZ(cam.getZ() * pixelsPerMeter);
		Perspective perspective = getPerspective(cam);
		data.sort(new DataComparator(cam, ressourceHandler, pixelsPerMeter));
		RenderContext context = new RenderContext(ctx, cam, perspective, ressourceHandler, pixelsPerMeter);
		ctx.clearRect(0, 0, cam.getViewWidth(), cam.getViewHeight());
		for (RenderData r : data) {
			DataRenderer<?> renderer = renderers.get(r.getClass());
			if (renderer == null) {
				logger.log(Level.WARNING, "There is no renderer specified for the object " + r + "! Please ensure you have implemented a DataRenderer for this object.");
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
	
	static RenderMetaData getMetaData (RenderData data, RessourceHandler handler, double pixelsPerMeter) {
		RenderMetaData meta = null;
		if (data instanceof ModelRenderData) {
			ModelRenderData cast = (ModelRenderData) data;
			RenderModel model = handler.getModel(((ModelRenderData) data).getTag());
			meta = new RenderMetaData(cast.getPosition().getPosition().getX(), cast.getPosition().getPosition().getY(), cast.getPosition().getPosition().getZ(), model.getWidth()/pixelsPerMeter, model.getHeight()/pixelsPerMeter, model.getLength()/pixelsPerMeter, 0);
		}
		else if (data instanceof PositionedRenderData) {
			PositionedRenderData cast = (PositionedRenderData) data;
			meta = new RenderMetaData(cast.getPosition().getPosition().getX(), cast.getPosition().getPosition().getY(), cast.getPosition().getPosition().getZ(), 0, 0, 0, 0);
		}
		else if (data instanceof ScreenPositonedRenderData) {
			meta = new RenderMetaData(0, 0, 0, 0, 0, 0, 1);
		}
		else if (data instanceof BackgroundRenderData) {
			meta = new RenderMetaData(0, 0, 0, 0, 0, 0, -1);
		}
		else {
			meta = new RenderMetaData(0, 0, 0, 0, 0, 0, 0);
		}
		return meta;
	}

}

class DataComparator implements Comparator<RenderData>{
	
	private Camera camera;
	private RessourceHandler ressourceHandler;
	private double pixelsPerMeter;


	public DataComparator(Camera camera, RessourceHandler ressourceHandler, double pixelsPerMeter) {
		super();
		this.camera = camera;
		this.ressourceHandler = ressourceHandler;
		this.pixelsPerMeter = pixelsPerMeter;
	}

	@Override
	public int compare(RenderData d1, RenderData d2) {
		int compare = 0;
		
		RenderMetaData o1 = DefaultRenderer.getMetaData(d1, ressourceHandler, pixelsPerMeter);
		RenderMetaData o2 = DefaultRenderer.getMetaData(d2, ressourceHandler, pixelsPerMeter);
		
		double x1 = o1.getX();
		double x2 = o2.getX();
		double y1 = o1.getY() + o1.getHeight();
		double y2 = o2.getY() + o2.getHeight();
		double z1 = o1.getZ();
		double length1 = o1.getLength();
		double z2 = o2.getZ();
		double length2 = o2.getLength();

		if (o1.getPriority() < o2.getPriority()) {
			compare = -1;
		} else if (o1.getPriority() > o2.getPriority()) {
			compare = 1;
		} else if (y1 < y2) {
			compare = -1;
		} else if (y1 > y2) {
			compare = 1;
		} else if (z1 + length1 < z2 + length2) {
			compare = -1;
		} else if (z1 + length1 > z2 + length2) {
			compare = 1;
		} else if (Math.abs(x1 - camera.getX()) > Math.abs(x2 - camera.getX())) {
			compare = -1;
		} else if (Math.abs(x1 - camera.getX()) < Math.abs(x2 - camera.getX())) {
			compare = 1;
		}
		return compare;
	}
	
}

class RenderMetaData {
	
	private double x;
	private double y;
	private double z;
	private double width;
	private double height;
	private double length;
	private int priority;
	
	public RenderMetaData(double x, double y, double z, double width, double height, double length, int priority) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.length = length;
		this.priority = priority;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public double getLength() {
		return length;
	}

	public int getPriority() {
		return priority;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
