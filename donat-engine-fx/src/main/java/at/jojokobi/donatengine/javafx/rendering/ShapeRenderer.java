package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.util.Vector2D;

public interface ShapeRenderer<T extends RenderShape> {
	
	public void render (T data, RenderContext ctx, Vector2D center, double scalar);
	
	public Class<T> getDataClass ();
	
	public default void renderUnsafe (RenderShape data, RenderContext ctx, Vector2D center, double scalar) {
		render(getDataClass().cast(data), ctx, center, scalar);
	}
	
}
