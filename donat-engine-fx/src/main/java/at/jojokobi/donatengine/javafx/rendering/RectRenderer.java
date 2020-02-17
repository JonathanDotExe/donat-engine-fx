package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.JavaFXPlatform;
import at.jojokobi.donatengine.rendering.RenderRect;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class RectRenderer implements ShapeRenderer<RenderRect>{

	@Override
	public void render(RenderRect data, RenderContext ctx, Vector2D center, double scalar) {
		GraphicsContext gc = ctx.getCtx();
		FixedStyle style = data.getStyle();
		
		Vector2D screenPos = center.add(data.getPosition().clone().multiply(scalar));
		
		gc.setFill(JavaFXPlatform.toFXColor(style.getFill()));
		gc.setStroke(JavaFXPlatform.toFXColor(style.getBorder()));
		gc.setLineWidth(style.getBorderStrength());
		gc.fillRoundRect(screenPos.getX(),  screenPos.getY(), data.getWidth() * scalar, data.getHeight() * scalar, style.getBorderRadius(), style.getBorderRadius());
		gc.strokeRoundRect(screenPos.getX(),  screenPos.getY(), data.getWidth() * scalar, data.getHeight() * scalar, style.getBorderRadius(), style.getBorderRadius());

	}

	@Override
	public Class<RenderRect> getDataClass() {
		return RenderRect.class;
	}
	
}
