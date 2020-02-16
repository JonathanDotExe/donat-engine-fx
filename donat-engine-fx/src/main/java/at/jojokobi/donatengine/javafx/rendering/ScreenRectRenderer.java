package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.JavaFXPlatform;
import at.jojokobi.donatengine.rendering.ScreenRectRenderData;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class ScreenRectRenderer implements DataRenderer<ScreenRectRenderData>{

	@Override
	public void render(ScreenRectRenderData data, RenderContext ctx) {
		GraphicsContext gc = ctx.getCtx();
		FixedStyle style = data.getStyle();
		
		Vector2D screenPos = data.getPosition();
		
		gc.setStroke(JavaFXPlatform.toFXColor(style.getBorder()));
		gc.setFill(JavaFXPlatform.toFXColor(style.getFill()));
		gc.setLineWidth(style.getBorderStrength());
		gc.fillRoundRect(screenPos.getX(),  screenPos.getY(), data.getWidth(), data.getHeight(), style.getBorderRadius(), style.getBorderRadius());
		gc.strokeRoundRect(screenPos.getX(),  screenPos.getY(), data.getWidth(), data.getHeight(), style.getBorderRadius(), style.getBorderRadius());
	}

	@Override
	public Class<ScreenRectRenderData> getDataClass() {
		return ScreenRectRenderData.class;
	}

}
