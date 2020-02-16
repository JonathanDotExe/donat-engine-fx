package at.jojokobi.donatengine.javafx.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SceneInput implements Input{

	private Map<String, KeyCode> keyBindings = new HashMap<>();
	private Map<String, MouseButton> mouseButtonBindings = new HashMap<>();
	private Map<String, Axis> axisBindings = new HashMap<>();
	
	private Set<KeyCode> keys = new HashSet<>();
	private Set<MouseButton> buttons = new HashSet<>();
	private Set<String> pressed = new HashSet<>();
	
	private Map<String, Boolean> changedButtons = new HashMap<>();
	
	private double mouseX = 0;
	private double mouseY = 0;
	
	private List<String> typedChars = new ArrayList<>();

	public SceneInput(Map<String, KeyCode> keyBindings, Map<String, MouseButton> mouseButtonBindings,
			Map<String, Axis> axisBindings) {
		super();
		this.keyBindings = keyBindings;
		this.mouseButtonBindings = mouseButtonBindings;
		this.axisBindings = axisBindings;
		axisBindings.putIfAbsent(CURSOR_AXIS, new MouseAxis());
		mouseButtonBindings.putIfAbsent(PRIMARY_BUTTON, MouseButton.PRIMARY);
		mouseButtonBindings.putIfAbsent(SECONDARY_BUTTON, MouseButton.SECONDARY);
		keyBindings.putIfAbsent(SUBMIT_BUTTON, KeyCode.ENTER);
	}

	public void registerEvents (Scene scene) {
		scene.setOnKeyTyped(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				typedChars.add(event.getCharacter());
			}
		});
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				keys.add(event.getCode());
				for (String string : keyBindings.keySet()) {
					if (keyBindings.get(string).equals(event.getCode())) {
						changedButtons.put(string, true);
						pressed.add(string);
					}
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				keys.remove(event.getCode());
				for (String string : keyBindings.keySet()) {
					if (keyBindings.get(string).equals(event.getCode())) {
						changedButtons.put(string, false);
					}
				}
			}
		});
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				buttons.add(event.getButton());
				for (String string : mouseButtonBindings.keySet()) {
					if (mouseButtonBindings.get(string).equals(event.getButton())) {
						changedButtons.put(string, true);
					}
				}
			}
		});
		scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				buttons.remove(event.getButton());
				for (String string : mouseButtonBindings.keySet()) {
					if (mouseButtonBindings.get(string).equals(event.getButton())) {
						changedButtons.put(string, true);
					}
				}
			}
		});
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseX = event.getSceneX();
				mouseY = event.getSceneY();
			}
		});
	}
	
	public boolean getButton (String code) {
		return keys.contains(keyBindings.get(code)) || buttons.contains(mouseButtonBindings.get(code));
	}
	
//
//	@Override
//	public void handle(KeyEvent event) {
//		if (event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
//			keys.put(event.getCode(), true);
//		}
//		else if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
//			keys.put(event.getCode(), false);
//		}
//	}
	
	public void reset () {
		keys.clear();
	}

	public double getMouseX() {
		return mouseX;
	}

	public double getMouseY() {
		return mouseY;
	}

	@Override
	public Vector2D getAxis(String axis) {
		return axisBindings.containsKey(axis) ? axisBindings.get(axis).getAxis(this, mouseX, mouseY) : null;
	}

	@Override
	public boolean setButton(String code, boolean pressed) {
		return false;
	}

	@Override
	public boolean setAxis(String axis, Vector2D vector) {
		return false;
	}

	@Override
	public Map<String, Boolean> fetchChangedButtons() {
		var map = new HashMap<>(changedButtons);
		changedButtons.clear ();
		return map;
	}

	@Override
	public Map<String, Vector2D> fetchChangedAxis() {
		var map = new HashMap<String, Vector2D>();
		for (String key : axisBindings.keySet()) {
			if (axisBindings.get(key).fetchChanged(this, mouseX, mouseY)) {
				map.put(key, axisBindings.get(key).getAxis(this, mouseX, mouseY));
			}
		}
		return map;
	}

	@Override
	public List<String> getTypedChars() {
		return new ArrayList<>(typedChars);
	}

	@Override
	public void updateBuffers() {
		typedChars.clear();
		pressed.clear();
	}

	@Override
	public boolean isPressed(String button) {
		return pressed.contains(button);
	}

	public Map<String, KeyCode> getKeyBindings() {
		return keyBindings;
	}

	public Map<String, MouseButton> getMouseButtonBindings() {
		return mouseButtonBindings;
	}

	public Map<String, Axis> getAxisBindings() {
		return axisBindings;
	}
	
}
