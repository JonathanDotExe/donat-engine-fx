package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.javafx.ressources.Texture;
import at.jojokobi.donatengine.rendering.BackgroundRenderData;

public class BackgroundRenderer implements DataRenderer<BackgroundRenderData>{

	@Override
	public void render(BackgroundRenderData data, RenderContext ctx) {
		Texture texture = ctx.getRessourceHandler().getTexture(data.getTag());
		if (texture != null) {
			ctx.getCtx().drawImage(texture.getImage(0)/*TODO animated backgrounds*/, 0, 0, ctx.getCam().getViewWidth(), ctx.getCam().getViewHeight());
		}
	}

	@Override
	public Class<BackgroundRenderData> getDataClass() {
		return BackgroundRenderData.class;
	}

}
