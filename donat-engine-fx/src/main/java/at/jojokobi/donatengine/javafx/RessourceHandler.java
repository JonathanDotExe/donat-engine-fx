package at.jojokobi.donatengine.javafx;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.javafx.rendering.RenderModel;
import javafx.scene.image.Image;
import javafx.scene.media.Media;

public class RessourceHandler {
	
	private Map<String, Media> medias = new HashMap<>();
	private Map<String, Image> images = new HashMap<>();
	private Map<String, RenderModel> models = new HashMap<>();
	
	public Media getMedia (String id) {
		return medias.get(id);
	}
	
	public void putMedia (String id, Media media) {
		medias.put(id, media);
	}
	
	public Image getImage (String id) {
		return images.get(id);
	}
	
	public void putImage (String id, Image image) {
		images.put(id, image);
	}
	
	public RenderModel getModel (String id) {
		return models.get(id);
	}
	
	public void putModel (String id, RenderModel model) {
		models.put(id, model);
	}

}
