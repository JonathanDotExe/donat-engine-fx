package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.rendering.RenderData;


public interface DataRenderer<T extends RenderData> {
	
	public void render (T data, RenderContext ctx);
	
	public Class<T> getDataClass ();
	
	public default void renderUnsafe (RenderData data, RenderContext ctx) {
		render(getDataClass().cast(data), ctx);
	}
	
}
