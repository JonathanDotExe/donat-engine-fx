package at.jojokobi.donatengine.javafx.audio;

import at.jojokobi.donatengine.javafx.ressources.RessourceHandler;
import javafx.application.Platform;
import javafx.scene.media.MediaPlayer;

public class JavaFXAudioSystem implements at.jojokobi.donatengine.audio.AudioSystem{

//	private List<MediaPlayer> playing = new ArrayList<>();
	
	private RessourceHandler ressourceHandler;
	
	public JavaFXAudioSystem(RessourceHandler ressourceHandler) {
		super();
		this.ressourceHandler = ressourceHandler;
	}

	@Override
	public void playSound(String sound) {
		Platform.runLater(() -> {
			MediaPlayer player = new MediaPlayer(ressourceHandler.getMedia(sound));
			player.play();
		});
	}

	@Override
	public void playMusic(String music) {
		Platform.runLater(() -> {
			MediaPlayer player = new MediaPlayer(ressourceHandler.getMedia(music));
			player.setCycleCount(MediaPlayer.INDEFINITE);
			player.play();
		});
	}

}
