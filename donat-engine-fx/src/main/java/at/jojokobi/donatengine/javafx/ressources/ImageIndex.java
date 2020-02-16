package at.jojokobi.donatengine.javafx.ressources;

import java.util.HashMap;
import java.util.Map;

public class ImageIndex {

	private Map<String, ImageEntry> images = new HashMap<>();

	public Map<String, ImageEntry> getImages() {
		return images;
	}

	public void setImages(Map<String, ImageEntry> images) {
		this.images = images;
	}
	
}
