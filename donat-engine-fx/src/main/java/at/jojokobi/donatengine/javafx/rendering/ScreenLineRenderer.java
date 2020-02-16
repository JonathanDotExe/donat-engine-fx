package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.JavaFXPlatform;
import at.jojokobi.donatengine.rendering.ScreenLineRenderData;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class ScreenLineRenderer implements DataRenderer<ScreenLineRenderData>{

	@Override
	public void render(ScreenLineRenderData data, RenderContext ctx) {
		GraphicsContext gc = ctx.getCtx();
		FixedStyle style = data.getStyle();
		
		Vector2D screenPos = data.getPosition();
		Vector2D endPos = data.getEndPosition();
		
		gc.setStroke(JavaFXPlatform.toFXColor(style.getBorder()));
		gc.setLineWidth(style.getBorderStrength());
		gc.strokeLine(screenPos.getX(),  screenPos.getY(), endPos.getX(), endPos.getY());
	}

	@Override
	public Class<ScreenLineRenderData> getDataClass() {
		return ScreenLineRenderData.class;
	}

}
