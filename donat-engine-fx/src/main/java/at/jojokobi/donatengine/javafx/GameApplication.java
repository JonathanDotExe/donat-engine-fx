package at.jojokobi.donatengine.javafx;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public abstract class GameApplication extends Application{
	
	private static Logger logger = Logger.getGlobal();
	
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
		sounds = gson.fromJson(soundsJSON(), SoundIndex.class);
		ImageIndex images = null;
		images = gson.fromJson(imagesJSON(), ImageIndex.class);
		ModelIndex models = null;
		models = gson.fromJson(modelsJSON(), ModelIndex.class);
		//Load Sounds
		for (var s : sounds.getSounds().entrySet()) {
			ressourceHandler.putMedia(s.getKey(), new Media(getRessourceRoot().getResource("/" + soundsRoot() + "/" + s.getValue().getPath()).toURI().toString()));
		}
		//Load Images
		for (var i : images.getImages().entrySet()) {
			InputStream in = getRessourceRoot().getResourceAsStream("/" + imagesRoot() + "/" + i.getValue().getPath());
			if (in != null) {
				ressourceHandler.putImage(i.getKey(), new Image(in));
			}
			else {
				logger.log(Level.WARNING, "The ressource " + i.getValue().getPath() + " for image " + i.getKey() + " does not exist!");
			}
		}
		//Load Models
		for (var m : models.getModels().entrySet()) {
			ressourceHandler.putModel(m.getKey(), m.getValue().toRenderModel(ressourceHandler));
		}
		
		//Engine Components
		AudioSystem audioSystem = new JavaFXAudioSystem(ressourceHandler);
		DoubleProperty mouseXProperty = new SimpleDoubleProperty();
		DoubleProperty mouseYProperty = new SimpleDoubleProperty();
		SceneInput input = new SceneInput(new HashMap<>(), new HashMap<>(), new HashMap<>(), mouseXProperty, mouseYProperty);
		putControls(input);
		JavaFXView gameView = new JavaFXView(stage, createRenderer(ressourceHandler), input);
		gameView.initStage();
		mouseXProperty.bind(gameView.getCanvas().scaleXProperty());
		mouseYProperty.bind(gameView.getCanvas().scaleYProperty());
		
		game = createGame(audioSystem, input, gameView);
		GameLoop loop = new GameLoop(getUpdatesPerSecond(), game);
		new Thread(loop, "Game-Loop").start();
	}
	
	protected abstract InputStream soundsInput ();
	
	protected abstract InputStream imagesInput ();
	
	protected abstract InputStream modelsInput ();
	
	protected String soundsJSON () {
		StringBuilder json = new StringBuilder();
		try (Scanner scanner = new Scanner(soundsInput())) {
			while (scanner.hasNextLine()) {
				json.append(scanner.nextLine() + System.lineSeparator());
			}
		}
		return json.toString();
	}
	
	protected String imagesJSON () {
		StringBuilder json = new StringBuilder();
		try (Scanner scanner = new Scanner(imagesInput())) {
			while (scanner.hasNextLine()) {
				json.append(scanner.nextLine() + System.lineSeparator());
			}
		}
		return json.toString();
	}
	
	protected String modelsJSON () {
		StringBuilder json = new StringBuilder();
		try (Scanner scanner = new Scanner(modelsInput())) {
			while (scanner.hasNextLine()) {
				json.append(scanner.nextLine() + System.lineSeparator());
			}
		}
		return json.toString();
	}
	
	protected abstract String imagesRoot ();
	
	protected abstract String soundsRoot ();
	
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
