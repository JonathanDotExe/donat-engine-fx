package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.JavaFXPlatform;
import at.jojokobi.donatengine.rendering.RenderLine;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class LineRenderer implements ShapeRenderer<RenderLine>{

	@Override
	public void render(RenderLine data, RenderContext ctx, Vector2D center, double scalar) {
		GraphicsContext gc = ctx.getCtx();
		FixedStyle style = data.getStyle();
		
		Vector2D screenPos = center.clone().add(data.getPosition().clone().multiply(scalar));
		Vector2D endPos = center.clone().add(data.getEndPosition().clone().multiply(scalar));
		
		gc.setStroke(JavaFXPlatform.toFXColor(style.getBorder()));
		gc.setLineWidth(style.getBorderStrength());
		gc.strokeLine(screenPos.getX(),  screenPos.getY(), endPos.getX(), endPos.getY());
	}

	@Override
	public Class<RenderLine> getDataClass() {
		return RenderLine.class;
	}

}
