package at.jojokobi.donatengine.javafx.ressources;

import javafx.scene.image.Image;

public class NullTexture implements Texture{

	@Override
	public Image getImage(double timer) {
		return null;
	}

	@Override
	public double getWidth() {
		return 0;
	}

	@Override
	public double getHeight() {
		return 0;
	}

}
