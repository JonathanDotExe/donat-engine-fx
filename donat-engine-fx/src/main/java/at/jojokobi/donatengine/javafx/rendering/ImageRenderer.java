package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.rendering.ImageRenderData;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.image.Image;

public class ImageRenderer implements DataRenderer<ImageRenderData>{

	@Override
	public void render(ImageRenderData data, RenderContext ctx) {
		Image image = ctx.getRessourceHandler().getTexture(data.getTag()).getImage(0);//TDO animated images
		Vector2D renderPos = ctx.getPerspective().toScreenPosition(ctx.getCam(), data.getPosition().getPosition().clone().multiply(ctx.getPixelsPerMeter())).subtract(image.getWidth()/2, image.getHeight()/2).round();
		ctx.getCtx().drawImage(image, renderPos.getX(), renderPos.getY());
	}

	@Override
	public Class<ImageRenderData> getDataClass() {
		return ImageRenderData.class;
	}

}
