package at.jojokobi.donatengine.javafx;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.GameLoop;
import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.javafx.audio.JavaFXAudioSystem;
import at.jojokobi.donatengine.javafx.input.SceneInput;
import at.jojokobi.donatengine.javafx.rendering.DefaultRenderer;
import at.jojokobi.donatengine.javafx.rendering.Renderer;
import at.jojokobi.donatengine.javafx.ressources.ImageIndex;
import at.jojokobi.donatengine.javafx.ressources.ModelIndex;
import at.jojokobi.donatengine.javafx.ressources.SoundIndex;
import at.jojokobi.donatengine.platform.GamePlatform;
import at.jojokobi.donatengine.rendering.GameView;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public abstract class GameApplication extends Application{
	
	private Game game;

	@Override
	public void start(Stage stage) throws Exception {
		initApplication();
		//Init Platform
		JavaFXPlatform platform = new JavaFXPlatform();
		GamePlatform.initialize(platform);
		//Load ressources
		RessourceHandler ressourceHandler = new RessourceHandler();
		GsonBuilder builder = new GsonBuilder();
		builder.setLenient();
		Gson gson = builder.create();
		//Load indexes
		SoundIndex sounds = null;
		try (Reader r = new InputStreamReader(soundsInput())) {
			sounds = gson.fromJson(r, SoundIndex.class);
		}
		ImageIndex images = null;
		try (Reader r = new InputStreamReader(soundsInput())) {
			images = gson.fromJson(r, ImageIndex.class);
		}
		ModelIndex models = null;
		try (Reader r = new InputStreamReader(soundsInput())) {
			models = gson.fromJson(r, ModelIndex.class);
		}
		//Load Sounds
		for (var s : sounds.getSounds().entrySet()) {
			ressourceHandler.putMedia(s.getKey(), new Media(getRessourceRoot().getResource("/" + s.getValue().getPath()).toURI().toString()));
		}
		//Load Images
		for (var i : images.getImages().entrySet()) {
			ressourceHandler.putImage(i.getKey(), new Image(getRessourceRoot().getResourceAsStream("/" + i.getValue().getPath())));
		}
		//Load Models
		for (var m : models.getModels().entrySet()) {
			ressourceHandler.putModel(m.getKey(), m.getValue().toRenderModel(ressourceHandler));
		}
		
		//Engine Components
		AudioSystem audioSystem = new JavaFXAudioSystem(ressourceHandler);
		SceneInput input = new SceneInput(new HashMap<>(), new HashMap<>(), new HashMap<>());
		putControls(input);
		GameView gameView = new JavaFXView(stage, createRenderer(ressourceHandler), input);
		
		Game game = createGame(audioSystem, input, gameView);
		GameLoop loop = new GameLoop(getUpdatesPerSecond(), game);
		new Thread(loop, "Game-Loop").start();
	}
	
	protected abstract InputStream soundsInput ();
	
	protected abstract InputStream imagesInput ();
	
	protected abstract InputStream modelsInput ();
	
	protected abstract void putControls (SceneInput input);
	
	protected abstract Class<?> getRessourceRoot();
	
	protected abstract Game createGame (AudioSystem system, Input input, GameView view);
	
	protected abstract int getUpdatesPerSecond ();
	
	protected abstract void initApplication();
	
	protected Renderer createRenderer (RessourceHandler ressourceHandler) {
		return new DefaultRenderer(ressourceHandler, 32);
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		game.stopGame();
	}

}
