package at.jojokobi.donatengine.javafx.input;

import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.util.Vector2D;

public interface Axis {
	
	public Vector2D getAxis (Input input, double mouseX, double mouseY);
	
	public boolean fetchChanged (Input input, double mouseX, double mouseY);

}
