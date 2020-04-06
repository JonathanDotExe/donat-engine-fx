package at.jojokobi.donatengine.javafx.ressources;

import javafx.scene.image.Image;

public class StaticTexture implements Texture{

	private Image image;

	public StaticTexture(Image image) {
		super();
		this.image = image;
	}

	@Override
	public Image getImage(double timer) {
		return image;
	}

	@Override
	public double getWidth() {
		return image.getWidth();
	}

	@Override
	public double getHeight() {
		return image.getHeight();
	}
		
}
