package at.jojokobi.donatengine.javafx.ressources;

import java.util.HashMap;
import java.util.Map;

public class ModelIndex {
	
	private Map<String, ModelEntry> models = new HashMap<>();

	public Map<String, ModelEntry> getModels() {
		return models;
	}

	public void setModels(Map<String, ModelEntry> models) {
		this.models = models;
	}

}
