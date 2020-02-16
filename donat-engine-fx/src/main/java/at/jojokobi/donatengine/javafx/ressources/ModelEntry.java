package at.jojokobi.donatengine.javafx.ressources;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.javafx.rendering.BoxModel;
import at.jojokobi.donatengine.javafx.rendering.Image2DModel;
import at.jojokobi.donatengine.javafx.rendering.RenderModel;

public class ModelEntry {
	
	private ModelType type;
	private Map<String, String> faces = new HashMap<>();
	
	public ModelType getType() {
		return type;
	}
	public Map<String, String> getFaces() {
		return faces;
	}
	public void setType(ModelType type) {
		this.type = type;
	}
	public void setFaces(Map<String, String> faces) {
		this.faces = faces;
	}
	
	public RenderModel toRenderModel (RessourceHandler handler) {
		RenderModel model = null;
		
		switch (type) {
		case BOX:
			model = new BoxModel(handler.getImage(faces.get("front")), handler.getImage(faces.get("right")), handler.getImage(faces.get("left")), handler.getImage(faces.get("top")));
			break;
		case IMAGE:
			model = new Image2DModel(handler.getImage(faces.get("image")));
			break;
		}
		
		return model;
	}

}

enum ModelType {
	IMAGE, BOX;
}


class ModelFace {
	
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}