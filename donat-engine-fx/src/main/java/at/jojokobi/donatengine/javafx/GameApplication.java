package at.jojokobi.donatengine.javafx;

import java.io.InputStream;
import java.util.HashMap;

import at.jojokobi.donatengine.Game;
import at.jojokobi.donatengine.GameLoop;
import at.jojokobi.donatengine.audio.AudioSystem;
import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.javafx.audio.JavaFXAudioSystem;
import at.jojokobi.donatengine.javafx.input.SceneInput;
import at.jojokobi.donatengine.javafx.rendering.DefaultRenderer;
import at.jojokobi.donatengine.javafx.rendering.Renderer;
import at.jojokobi.donatengine.platform.GamePlatform;
import at.jojokobi.donatengine.rendering.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public abstract class GameApplication extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		//Init Platform
		JavaFXPlatform platform = new JavaFXPlatform();
		GamePlatform.initialize(platform);
		//Load ressources
		RessourceHandler ressourceHandler = new RessourceHandler();
		//TODO Load ressources
		
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
	
	protected abstract Game createGame (AudioSystem system, Input input, GameView view);
	
	protected abstract int getUpdatesPerSecond ();
	
	protected Renderer createRenderer (RessourceHandler ressourceHandler) {
		return new DefaultRenderer(ressourceHandler, 32);
	}

}
