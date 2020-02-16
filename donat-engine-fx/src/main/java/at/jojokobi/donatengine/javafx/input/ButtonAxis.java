package at.jojokobi.donatengine.javafx.input;

import at.jojokobi.donatengine.input.Input;
import at.jojokobi.donatengine.util.Vector2D;

public class ButtonAxis implements Axis{
	
	private String up;
	private String down;
	private String left;
	private String right;
	
	private Vector2D lastValue = null;
	
	public ButtonAxis(String up, String down, String left, String right) {
		super();
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
	}

	@Override
	public Vector2D getAxis(Input input, double mouseX, double mouseY) {
		double x = 0;
		double y = 0;
		if (input.getButton(up)) {
			y -= 1;
		}
		if (input.getButton(down)) {
			y += 1;
		}
		if (input.getButton(left)) {
			x -= 1;
		}
		if (input.getButton(right)) {
			x += 1;
		}
		Vector2D vector = new Vector2D(x, y);
		if (x != 0 || y != 0) {
			vector.normalize();
		}
		return vector;
	}

	@Override
	public boolean fetchChanged(Input input, double mouseX, double mouseY) {
		Vector2D axis = getAxis(input, mouseX, mouseY);
		boolean changed = !axis.equals(lastValue);
		lastValue = axis;
		return changed;
	}
	
}
