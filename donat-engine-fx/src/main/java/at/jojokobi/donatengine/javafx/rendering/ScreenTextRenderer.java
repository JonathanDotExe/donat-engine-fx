package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.JavaFXFontSystem;
import at.jojokobi.donatengine.javafx.JavaFXPlatform;
import at.jojokobi.donatengine.rendering.ScreenTextRenderData;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class ScreenTextRenderer implements DataRenderer<ScreenTextRenderData>{

	@Override
	public void render(ScreenTextRenderData data, RenderContext ctx) {
		GraphicsContext gc = ctx.getCtx();
		FixedStyle style = data.getStyle();
		
		Vector2D screenPos = data.getPosition();
		
		gc.setStroke(JavaFXPlatform.toFXColor(style.getFontBorder()));
		gc.setFill(JavaFXPlatform.toFXColor(style.getFontColor()));
		gc.setFont(JavaFXFontSystem.toFXFont(style.getFont()));
		gc.setLineWidth(style.getBorderStrength());
		gc.fillText(data.getText(), screenPos.getX(),  screenPos.getY());
		gc.strokeText(data.getText(), screenPos.getX(),  screenPos.getY());
	}

	@Override
	public Class<ScreenTextRenderData> getDataClass() {
		return ScreenTextRenderData.class;
	}

}
