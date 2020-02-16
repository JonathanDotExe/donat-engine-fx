package at.jojokobi.donatengine.javafx.audio;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.media.Media;

public class SoundEvent {

	private Media[] sounds;

	public SoundEvent(Media... sounds) {
		super();
		this.sounds = sounds;
	}

	public SoundEvent(String... uris) {
		this(Arrays.stream(uris).map((uri) -> new Media(uri)).toArray(Media[]::new));
	}

	public SoundEvent(Class<?> clazz, String... paths) {
		this((String[]) Arrays.stream(paths).map((path) -> {
			try {
				return clazz.getResource(path).toURI().toString();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			return "";
		}).toArray(String[]::new));
	}

	public Media chooseSound() {
		Random random = new Random();
		return sounds[random.nextInt(sounds.length)];
	}

}
