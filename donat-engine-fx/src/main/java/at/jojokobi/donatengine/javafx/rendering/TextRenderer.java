package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.JavaFXPlatform;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.platform.GamePlatform;
import at.jojokobi.donatengine.rendering.TextRenderData;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;

public class TextRenderer implements DataRenderer<TextRenderData>{

	@Override
	public void render(TextRenderData data, RenderContext ctx) {
		Camera cam = ctx.getCam();
		GraphicsContext gc = ctx.getCtx();
		Perspective perspective = ctx.getPerspective();
		double pixelsPerMeter = ctx.getPixelsPerMeter();
		FixedStyle style = data.getStyle();
		Vector2D dim = GamePlatform.getFontSystem().calculateTextDimensions(data.getText(), style.getFont());
		
		Vector3D pos = data.getPosition().getPosition().clone().multiply(pixelsPerMeter);
		Vector2D screenPos = perspective.toScreenPosition(cam, pos).subtract(dim.getX()/2, dim.getY()/2);
		
		gc.setFill(JavaFXPlatform.toFXColor(style.getFill()));
		gc.setStroke(JavaFXPlatform.toFXColor(style.getBorder()));
		gc.setLineWidth(style.getBorderStrength());
		gc.fillText(data.getText(), screenPos.getX(),  screenPos.getY());
		gc.strokeText(data.getText(), screenPos.getX(),  screenPos.getY());
	}

	@Override
	public Class<TextRenderData> getDataClass() {
		return TextRenderData.class;
	}
	
}
