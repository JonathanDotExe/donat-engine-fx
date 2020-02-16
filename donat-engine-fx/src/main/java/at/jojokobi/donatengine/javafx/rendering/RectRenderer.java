package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.JavaFXPlatform;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RectRenderData;
import at.jojokobi.donatengine.style.FixedStyle;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;

public class RectRenderer implements DataRenderer<RectRenderData>{

	@Override
	public void render(RectRenderData data, RenderContext ctx) {
		Camera cam = ctx.getCam();
		GraphicsContext gc = ctx.getCtx();
		Perspective perspective = ctx.getPerspective();
		double pixelsPerMeter = ctx.getPixelsPerMeter();
		FixedStyle style = data.getStyle();
		
		Vector3D pos = data.getPosition().getPosition().clone().multiply(pixelsPerMeter);
		Vector2D screenPos = perspective.toScreenPosition(cam, pos).subtract(data.getWidth()/2, data.getHeight()/2);
		
		gc.setFill(JavaFXPlatform.toFXColor(style.getFill()));
		gc.setStroke(JavaFXPlatform.toFXColor(style.getBorder()));
		gc.setLineWidth(style.getBorderStrength());
		gc.fillRoundRect(screenPos.getX(),  screenPos.getY(), data.getWidth(), data.getHeight(), style.getBorderRadius(), style.getBorderRadius());
		gc.strokeRoundRect(screenPos.getX(),  screenPos.getY(), data.getWidth(), data.getHeight(), style.getBorderRadius(), style.getBorderRadius());
	}

	@Override
	public Class<RectRenderData> getDataClass() {
		return RectRenderData.class;
	}
	
}
