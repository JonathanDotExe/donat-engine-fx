package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.JavaFXFontSystem;
import at.jojokobi.donatengine.javafx.JavaFXPlatform;
import at.jojokobi.donatengine.rendering.RenderText;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class TextRenderer implements ShapeRenderer<RenderText>{

	@Override
	public void render(RenderText data, RenderContext ctx, Vector2D center, double scalar) {
		GraphicsContext gc = ctx.getCtx();
		FixedStyle style = data.getStyle();

		Vector2D screenPos = center.add(data.getPosition().clone().multiply(scalar));
		
		gc.setFill(JavaFXPlatform.toFXColor(style.getFill()));
		gc.setStroke(JavaFXPlatform.toFXColor(style.getBorder()));
		gc.setLineWidth(style.getBorderStrength());
		gc.setFont(JavaFXFontSystem.toFXFont(style.getFont()));
		gc.fillText(data.getText(), screenPos.getX(),  screenPos.getY());
		gc.strokeText(data.getText(), screenPos.getX(),  screenPos.getY());
	}

	@Override
	public Class<RenderText> getDataClass() {
		return RenderText.class;
	}
	
}
