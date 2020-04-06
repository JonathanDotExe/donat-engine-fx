package at.jojokobi.donatengine.javafx.ressources;

import java.util.HashMap;
import java.util.Map;

import at.jojokobi.donatengine.javafx.rendering.models.BoxModel;
import at.jojokobi.donatengine.javafx.rendering.models.Image2DModel;
import at.jojokobi.donatengine.javafx.rendering.models.RenderModel;

public class ModelEntry {
	
	private ModelType type;
	private Map<String, String> faces = new HashMap<>();
	private Double width;
	private Double height;
	private Double length;
	
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
	
	
	
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getLength() {
		return length;
	}
	public void setLength(Double length) {
		this.length = length;
	}
	public RenderModel toRenderModel (RessourceHandler handler) {
		RenderModel model = null;
		
		switch (type) {
		case BOX:
			Texture front = handler.getTexture(faces.get("front"));
			Texture right = handler.getTexture(faces.get("right"));
			Texture left = handler.getTexture(faces.get("left"));
			Texture top = handler.getTexture(faces.get("top"));
			if (width != null && height != null && length != null) {
				model = new BoxModel(front, right, left, top, width, height, length);
			}
			else {
				model = new BoxModel(front, right, left, top);
			}
			break;
		case IMAGE:
			front = handler.getTexture(faces.get("front"));
			if (front == null) {
				throw new RuntimeException("Image " + faces.get("front") + " not found!");
			}
			if (width != null && height != null && length != null) {
				model = new Image2DModel(front, width, height, length);
			}
			else {
				model = new Image2DModel(front);
			}
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