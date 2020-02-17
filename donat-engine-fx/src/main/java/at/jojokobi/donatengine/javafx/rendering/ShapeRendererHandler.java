package at.jojokobi.donatengine.javafx.rendering;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.util.Vector2D;

public class ShapeRendererHandler {
	
	private Map<Class<? extends RenderShape>, ShapeRenderer<?>> renderers = new HashMap<> ();
	
	public ShapeRendererHandler() {
		putRenderer(new RectRenderer());
		putRenderer(new TextRenderer());
		putRenderer(new LineRenderer());
	}
	
	public void render (RenderShape shape, RenderContext ctx, Vector2D center, double scalar) {
		renderers.get(shape.getClass()).renderUnsafe(shape, ctx, center, scalar);
	}
	
	public void putRenderer (ShapeRenderer<?> renderer) {
		renderers.put(renderer.getDataClass(), renderer);
	}

}
