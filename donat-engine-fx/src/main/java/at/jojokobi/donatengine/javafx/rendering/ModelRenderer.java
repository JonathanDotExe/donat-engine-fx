package at.jojokobi.donatengine.javafx.rendering;

import java.util.logging.Level;
import java.util.logging.Logger;

import at.jojokobi.donatengine.javafx.rendering.models.RenderModel;
import at.jojokobi.donatengine.javafx.ressources.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.ModelRenderData;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;

public class ModelRenderer implements DataRenderer<ModelRenderData>{
	
	private static Logger logger = Logger.getGlobal();

	@Override
	public void render(ModelRenderData data, RenderContext ctx) {
		RessourceHandler ressourceHandler = ctx.getRessourceHandler();
		Camera cam = ctx.getCam();
		GraphicsContext gc = ctx.getCtx();
		Perspective perspective = ctx.getPerspective();
		double pixelsPerMeter = ctx.getPixelsPerMeter();
		Vector3D pos = data.getPosition().getPosition().clone().multiply(pixelsPerMeter);
		RenderModel model = ressourceHandler.getModel(data.getTag());
		if (model != null) {
			model.render(gc, cam, perspective, pos.getX(), pos.getY(), pos.getZ(), data.getAnimationTime());
		}
		else {
			logger.log(Level.WARNING, "No model is defined for the name " + data.getTag() +"!");
		}
	}

	@Override
	public Class<ModelRenderData> getDataClass() {
		return ModelRenderData.class;
	}
	
}
