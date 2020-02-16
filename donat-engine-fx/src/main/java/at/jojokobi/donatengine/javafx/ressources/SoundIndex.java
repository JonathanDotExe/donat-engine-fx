package at.jojokobi.donatengine.javafx.ressources;

import java.util.HashMap;
import java.util.Map;

public class SoundIndex {
	
	private Map<String, SoundEntry> sounds = new HashMap<>();

	public Map<String, SoundEntry> getSounds() {
		return sounds;
	}

	public void setSounds(Map<String, SoundEntry> sounds) {
		this.sounds = sounds;
	}

}
