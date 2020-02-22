package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.rendering.CanvasRenderData;
import at.jojokobi.donatengine.rendering.RenderShape;
import at.jojokobi.donatengine.util.Vector2D;

public class CanvasRenderer implements DataRenderer<CanvasRenderData>{

	private ShapeRendererHandler handler = new ShapeRendererHandler();
	
	@Override
	public void render(CanvasRenderData data, RenderContext ctx) {
		Vector2D center = ctx.getPerspective().toScreenPosition(ctx.getCam(), data.getPosition().getPosition().clone().multiply(ctx.getPixelsPerMeter()));
		for (RenderShape shape : data.getShapes()) {
			handler.render(shape, ctx, center.clone(), ctx.getPixelsPerMeter());
		}
	}

	@Override
	public Class<CanvasRenderData> getDataClass() {
		return CanvasRenderData.class;
	}
	
}
