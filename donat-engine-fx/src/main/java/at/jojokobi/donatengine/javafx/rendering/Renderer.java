package at.jojokobi.donatengine.javafx.rendering;

import java.util.List;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.RenderData;
import javafx.scene.canvas.GraphicsContext;

public interface Renderer {

	public void render (List<RenderData> data, Camera cam, GraphicsContext ctx);
	
}
