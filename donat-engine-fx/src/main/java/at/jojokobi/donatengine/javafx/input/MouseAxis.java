package at.jojokobi.donatengine.javafx.input;

import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.util.Vector2D;

public class MouseAxis implements Axis{
	
	private Vector2D lastValue = null;

	@Override
	public Vector2D getAxis(Input input, double mouseX, double mouseY) {
		return new Vector2D(mouseX, mouseY);
	}

	@Override
	public boolean fetchChanged(Input input, double mouseX, double mouseY) {
		Vector2D axis = getAxis(input, mouseX, mouseY);
		boolean changed = !axis.equals(lastValue);
		lastValue = axis;
		return changed;
	}
	
}
