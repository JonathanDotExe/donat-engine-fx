package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.rendering.ScreenCanvasRenderData;
import at.jojokobi.donatengine.util.Vector2D;

public class ScreenCanvasRenderer implements DataRenderer<ScreenCanvasRenderData>{

	private ShapeRendererHandler handler = new ShapeRendererHandler();;
	
	@Override
	public void render(ScreenCanvasRenderData data, RenderContext ctx) {
		Vector2D center = data.getPosition();
		for (RenderShape shape : data.getShapes()) {
			handler.render(shape, ctx, center.clone(), 1);
		}
	}

	@Override
	public Class<ScreenCanvasRenderData> getDataClass() {
		return ScreenCanvasRenderData.class;
	}
	
}
