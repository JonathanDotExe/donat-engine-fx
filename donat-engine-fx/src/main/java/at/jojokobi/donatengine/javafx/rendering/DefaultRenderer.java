package at.jojokobi.donatengine.javafx.rendering;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.level.LevelBoundsComponent;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.objects.GameObject;
import at.jojokobi.donatengine.rendering.BackgroundRenderData;
import at.jojokobi.donatengine.rendering.ModelRenderData;
import at.jojokobi.donatengine.rendering.PositionedRenderData;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.rendering.ScreenPositonedRenderData;
import javafx.scene.canvas.GraphicsContext;

/**
 * 
 * Only supports camera rotations on the X axis between -90 and 0
 * 
 * @author jojokobi
 *
 */
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
		CameraBox box = computeCameraBox(cam);
		cam.setX(cam.getX() * pixelsPerMeter);
		cam.setY(cam.getY() * pixelsPerMeter);
		cam.setZ(cam.getZ() * pixelsPerMeter);
		cam.setRotationX(modifyXRotation(cam.getRotationX()));
		//TODO: Recalculate all rendering stuff to get cleaner formulas
		Perspective perspective = getPerspective(cam);
		data.sort(perspective.getComparator(cam, ressourceHandler, pixelsPerMeter));
		RenderContext context = new RenderContext(ctx, cam, perspective, ressourceHandler, pixelsPerMeter);
		ctx.clearRect(0, 0, cam.getViewWidth(), cam.getViewHeight());
		for (RenderData r : data) {
			RenderMetaData meta = getMetaData(r, ressourceHandler, pixelsPerMeter);
			if (meta.canRender(box.getX(), box.getY(), box.getZ(), box.getWidth(), box.getHeight(), box.getLength(), cam.getArea())) {
				DataRenderer<?> renderer = renderers.get(r.getClass());
				if (renderer == null) {
					logger.log(Level.WARNING, "There is no renderer specified for the object " + r + "! Please ensure you have implemented a DataRenderer for this object.");
				}
				else {
					renderer.renderUnsafe(r, context);
				}
			}
		}
	}
	
	private double modifyXRotation (double xRotation) {
		return -180 + xRotation;
	}
	
	private static Perspective getPerspective (Camera cam) {
		Perspective perspective = null;
		for (String attribute : cam.getAttributes()) {
			switch (attribute) {
			case "retro3d":
				perspective = new Retro3DPerspective();
				break;
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
			meta = new RenderMetaData(cast.getPosition().getPosition().getX(), cast.getPosition().getPosition().getY(), cast.getPosition().getPosition().getZ(), model.getWidth()/pixelsPerMeter, model.getHeight()/pixelsPerMeter, model.getLength()/pixelsPerMeter, cast.getPosition().getArea(), 0);
		}
		else if (data instanceof PositionedRenderData) {
			PositionedRenderData cast = (PositionedRenderData) data;
			meta = new RenderMetaData(cast.getPosition().getPosition().getX(), cast.getPosition().getPosition().getY(), cast.getPosition().getPosition().getZ(), 0, 0, 0, cast.getPosition().getArea(), 0);
		}
		else if (data instanceof ScreenPositonedRenderData) {
			meta = new RenderMetaData(0, 0, 0, 0, 0, 0, "", 1);
		}
		else if (data instanceof BackgroundRenderData) {
			meta = new RenderMetaData(0, 0, 0, 0, 0, 0, "", -1);
		}
		else {
			meta = new RenderMetaData(0, 0, 0, 0, 0, 0, "", 0);
		}
		return meta;
	}
	
	public CameraBox computeCameraBox (Camera camera) {
		double width = camera.getViewWidth()/pixelsPerMeter;
		double height = camera.getViewHeight()/pixelsPerMeter * Math.cos(Math.toRadians(camera.getRotationX())) + camera.getFarClip() * Math.sin(Math.toRadians(camera.getRotationX()));
		double length = camera.getViewHeight()/pixelsPerMeter * Math.sin(Math.toRadians(camera.getRotationX())) + camera.getFarClip() * Math.cos(Math.toRadians(camera.getRotationX()));
		double x = camera.getX() - width/2;
		double y = camera.getY() - height;
		double z = camera.getZ() - length/2;
		if (height < 0) {
			y += 2 * height;
			height *= -1;
		}
		if (length < 0) {
			z += length;
			length *= -1;
		}
		return new CameraBox(x, y, z, width, height, length);
	}
	
	public CameraBox computeFollowCameraBox (Camera camera) {
		double width = camera.getViewWidth()/pixelsPerMeter;
		double height = camera.getViewHeight()/pixelsPerMeter;
		double length = camera.getViewHeight()/pixelsPerMeter;
		double x = camera.getX() - width/2;
		double y = camera.getY() - height;
		double z = camera.getZ() - length/2;
		if (height < 0) {
			y += 2 * height;
			height *= -1;
		}
		if (length < 0) {
			z += length;
			length *= -1;
		}
		return new CameraBox(x, y, z, width, height, length);
	}

	@Override
	public void doCameraFollow(GameObject follow, at.jojokobi.donatengine.level.Level level, Camera cam, double maxBorderDst) {
		CameraBox box = computeFollowCameraBox(cam);
		
		//Follow
		double x = box.getX() + box.getWidth() * maxBorderDst;
		double dx = box.getX() + box.getWidth() * (1 - maxBorderDst);
		double y = box.getY() + box.getHeight() * maxBorderDst;
		double dy = box.getY() + box.getHeight() * (1 - maxBorderDst);
		double z = box.getZ() + box.getLength() * maxBorderDst;
		double dz = box.getZ() + box.getLength() * (1 - maxBorderDst);
		// X
		if (follow.getX() < x) {
			cam.setX(follow.getX() - box.getWidth() * maxBorderDst + box.getWidth()/2);
		}
		if (follow.getX() > dx) {
			cam.setX(follow.getX() - box.getWidth() * (1 - maxBorderDst) + box.getWidth()/2);
		}
		// Y
		if (follow.getY() < y) {
			cam.setY(follow.getY() - box.getHeight() * maxBorderDst + box.getHeight());
		}
		if (follow.getY() > dy) {
			cam.setY(follow.getY() - box.getHeight() * (1 - maxBorderDst) + box.getHeight());
		}
		// Z
		if (follow.getZ() < z) {
			cam.setZ(follow.getZ() - box.getLength() * maxBorderDst + box.getLength()/2);
		}
		if (follow.getZ() > dz) {
			cam.setZ(follow.getZ() - box.getLength() * (1 - maxBorderDst) + box.getLength()/2);
		}
		cam.setArea(follow.getArea());
		
		//Bounds
		LevelBoundsComponent bounds = level.getComponent(LevelBoundsComponent.class);
		if (bounds != null) {
			if (cam.getX() - cam.getViewWidth()/pixelsPerMeter/2 < bounds.getPos().getX()) {
				cam.setX(bounds.getPos().getX() + cam.getViewWidth()/pixelsPerMeter/2);
			}
			if (cam.getX() + cam.getViewWidth()/pixelsPerMeter/2 > bounds.getPos().getX() + bounds.getSize().getX()) {
				cam.setX(bounds.getPos().getX() - cam.getViewWidth()/pixelsPerMeter/2 + bounds.getSize().getX());
			}
			if (cam.getY() - cam.getViewHeight()/pixelsPerMeter < bounds.getPos().getY()) {
				cam.setY(bounds.getPos().getY() + cam.getViewHeight()/pixelsPerMeter);
			}
			if (cam.getY() > bounds.getPos().getY() + bounds.getSize().getY()) {
				cam.setY(bounds.getPos().getY() + bounds.getSize().getY());
			}
			if (cam.getZ() - cam.getViewHeight()/pixelsPerMeter/2 < bounds.getPos().getZ()) {
				cam.setZ(bounds.getPos().getZ() + cam.getViewHeight()/pixelsPerMeter/2);
			}
			if (cam.getZ() + cam.getViewHeight()/pixelsPerMeter/2 > bounds.getPos().getZ() + bounds.getSize().getZ()) {
				cam.setZ(bounds.getPos().getZ() - cam.getViewHeight()/pixelsPerMeter/2 + bounds.getSize().getZ());
			}
		}
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
		}
		else if (z1 + length1 <= z2 || z1 >= z2 + length2) {
			if (z1 + length1 < z2 + length2) {
				compare = -1;
			} else if (z1 + length1 > z2 + length2) {
				compare = 1;
			} else if (y1 < y2) {
				compare = -1;
			} else if (y1 > y2) {
				compare = 1;
			} else if (Math.abs(x1 - camera.getX()) > Math.abs(x2 - camera.getX())) {
				compare = -1;
			} else if (Math.abs(x1 - camera.getX()) < Math.abs(x2 - camera.getX())) {
				compare = 1;
			}
		} else {
			if (y1 < y2) {
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
	private String area;
	private int priority;
	
	public RenderMetaData(double x, double y, double z, double width, double height, double length, String area,
			int priority) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.height = height;
		this.length = length;
		this.area = area;
		this.priority = priority;
	}

	public boolean canRender (double x, double y, double z, double width, double height, double length, String area) {
		return priority != 0 || (area.equals(this.area) && getX() < x + width && x < getX() + getWidth() && getY() < y + height && y < getY() + getHeight() && getZ() < z + length && z < getZ() + getLength());
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
