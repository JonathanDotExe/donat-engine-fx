package at.jojokobi.donatengine.javafx.ressources;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.javafx.rendering.models.RenderModel;
import javafx.scene.media.Media;

public class RessourceHandler {
	
	private Map<String, Media> medias = new HashMap<>();
	private Map<String, Texture> textures = new HashMap<>();
	private Map<String, RenderModel> models = new HashMap<>();
	
	public Media getMedia (String id) {
		return medias.get(id);
	}
	
	public void putMedia (String id, Media media) {
		medias.put(id, media);
	}
	
	public Texture getTexture (String id) {
		return textures.get(id);
	}
	
	public void putTexture (String id, Texture image) {
		textures.put(id, image);
	}
	
	public RenderModel getModel (String id) {
		return models.get(id);
	}
	
	public void putModel (String id, RenderModel model) {
		models.put(id, model);
	}

}
