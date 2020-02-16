package at.jojokobi.donatengine.javafx;

import at.jojokobi.donatengine.platform.FontSystem;
import at.jojokobi.donatengine.platform.IGamePlatform;
import javafx.scene.paint.Color;

public class JavaFXPlatform implements IGamePlatform {
	
	private FontSystem fontSystem = new JavaFXFontSystem();
	
	@Override
	public FontSystem getFontSystem() {
		return fontSystem;
	}
	
	public static final Color toFXColor (at.jojokobi.donatengine.style.Color color) {
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
	}

}
