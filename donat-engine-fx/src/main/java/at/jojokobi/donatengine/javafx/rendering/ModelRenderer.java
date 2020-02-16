package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.ModelRenderData;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;

public class ModelRenderer implements DataRenderer<ModelRenderData>{

	@Override
	public void render(ModelRenderData data, RenderContext ctx) {
		RessourceHandler ressourceHandler = ctx.getRessourceHandler();
		Camera cam = ctx.getCam();
		GraphicsContext gc = ctx.getCtx();
		Perspective perspective = ctx.getPerspective();
		double pixelsPerMeter = ctx.getPixelsPerMeter();
		
		Vector3D pos = data.getPosition().getPosition().clone().multiply(pixelsPerMeter);
		RenderModel model = ressourceHandler.getModel(data.getTag());
		model.render(gc, cam, perspective, pos.getX(), pos.getY(), pos.getZ());
	}

	@Override
	public Class<ModelRenderData> getDataClass() {
		return ModelRenderData.class;
	}
	
}
