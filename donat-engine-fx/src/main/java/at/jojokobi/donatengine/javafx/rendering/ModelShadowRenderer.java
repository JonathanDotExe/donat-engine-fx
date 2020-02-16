package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.ModelShadowRenderData;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ModelShadowRenderer implements DataRenderer<ModelShadowRenderData>{

	@Override
	public void render(ModelShadowRenderData data, RenderContext ctx) {
		RessourceHandler ressourceHandler = ctx.getRessourceHandler();
		Camera cam = ctx.getCam();
		GraphicsContext gc = ctx.getCtx();
		Perspective perspective = ctx.getPerspective();
		double pixelsPerMeter = ctx.getPixelsPerMeter();
		
		Vector3D pos = data.getPosition().getPosition().clone().multiply(pixelsPerMeter);
		RenderModel model = ressourceHandler.getModel(data.getTag());
		double y = 0;
		/*for (GameObject obj : level.getObjectsInArea(object.getX(), 0, object.getZ(), object.getWidth(), object.getY(), object.getLength(), object.getArea ())) {
			if (y < obj.getY() + obj.getHeight() && obj != object && obj.isSolid()) {
				y = obj.getY() + obj.getHeight();
			}
		}*/
		y = pos.getY(); //TODO Project shadow on floor
		//Render
		Vector3D startPos = new Vector3D(pos.getX(), y, pos.getZ());
		Vector3D endPos = new Vector3D(pos.getX() + model.getWidth(), y, pos.getZ() + model.getLength());
		
		Vector2D screenStartPos = perspective.toScreenPosition(cam, startPos);
		Vector2D screenEndPos = perspective.toScreenPosition(cam, endPos);
		
		gc.setFill(new Color(0.5, 0.5, 0.5, 0.5));
		gc.fillOval(screenStartPos.getX(), screenStartPos.getY(), screenEndPos.getX() - screenStartPos.getX(), screenEndPos.getY() - screenStartPos.getY());
	}

	@Override
	public Class<ModelShadowRenderData> getDataClass() {
		return ModelShadowRenderData.class;
	}

}
