package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.rendering.BackgroundRenderData;

public class BackgroundRenderer implements DataRenderer<BackgroundRenderData>{

	@Override
	public void render(BackgroundRenderData data, RenderContext ctx) {
		ctx.getCtx().drawImage(ctx.getRessourceHandler().getImage(data.getTag()), 0, 0, ctx.getCam().getViewWidth(), ctx.getCam().getViewHeight());
	}

	@Override
	public Class<BackgroundRenderData> getDataClass() {
		return BackgroundRenderData.class;
	}

}
