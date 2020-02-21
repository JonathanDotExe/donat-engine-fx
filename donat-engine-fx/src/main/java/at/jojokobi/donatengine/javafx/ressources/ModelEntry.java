package at.jojokobi.donatengine.javafx.ressources;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.javafx.RessourceHandler;
import at.jojokobi.donatengine.javafx.rendering.BoxModel;
import at.jojokobi.donatengine.javafx.rendering.Image2DModel;
import at.jojokobi.donatengine.javafx.rendering.RenderModel;
import javafx.scene.image.Image;

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
			Image front = handler.getImage(faces.get("front"));
			Image right = handler.getImage(faces.get("right"));
			Image left = handler.getImage(faces.get("left"));
			Image top = handler.getImage(faces.get("top"));
			model = new BoxModel(front, right, left, top);
			break;
		case IMAGE:
			front = handler.getImage(faces.get("front"));
			if (front == null) {
				throw new RuntimeException("Image " + faces.get("front") + " not found!");
			}
			model = new Image2DModel(front);
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