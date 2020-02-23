package at.jojokobi.donatengine.javafx;

import java.util.List;

import at.jojokobi.donatengine.javafx.input.SceneInput;
import at.jojokobi.donatengine.javafx.rendering.Renderer;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.rendering.GameView;
import at.jojokobi.donatengine.rendering.RenderData;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class JavaFXView implements GameView{
	
	private Stage stage;
	private Renderer renderer;
	private SceneInput input;
	private Canvas canvas;
	
	
	public JavaFXView(Stage stage, Renderer renderer, SceneInput input) {
		super();
		this.stage = stage;
		this.renderer = renderer;
		this.input = input;
	}

	/**
	 * May only be called from the JavaFX Application Thread
	 */
	public void initStage () {
		canvas = new Canvas(1280, 768);
		Scene scene = new Scene(new BorderPane(canvas));
		input.registerEvents(scene);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void render(List<RenderData> data, Camera cam) {
		Camera camera = cam.clone();
		Platform.runLater(() -> {
			canvas.setWidth(camera.getViewWidth());
			canvas.setHeight(camera.getViewHeight());
			renderer.render(data, camera, canvas.getGraphicsContext2D());
		});
	}

	@Override
	public void setTitle(String title) {
		Platform.runLater(() -> {
			stage.setTitle(title);
		}); 
	}

	@Override
	public String getTitle() {
		return stage.getTitle();
	}

	@Override
	public Vector2D getSize() {
		return new Vector2D(stage.getWidth(), stage.getHeight());
	}

	@Override
	public void setSize(Vector2D size) {
		Platform.runLater(() -> {
			stage.setWidth(size.getX());
			stage.setHeight(size.getY());
		}); 
	}

}
