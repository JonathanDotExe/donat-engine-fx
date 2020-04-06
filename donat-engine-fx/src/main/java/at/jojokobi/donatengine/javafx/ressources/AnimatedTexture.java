package at.jojokobi.donatengine.javafx.ressources;

import java.util.List;

import javafx.scene.image.Image;

public class AnimatedTexture implements Texture {

	private double frameDuration;
	private List<Image> frames;
	
	public AnimatedTexture(double frameDuration, List<Image> frames) {
		super();
		this.frameDuration = frameDuration;
		this.frames = frames;
	}

	@Override
	public Image getImage(double timer) {
		int index = (int)(timer/frameDuration)%frames.size();
		return frames.get(index);
	}

	@Override
	public double getWidth() {
		return frames.get(0).getWidth();
	}

	@Override
	public double getHeight() {
		return frames.get(0).getHeight();
	}
	
}
