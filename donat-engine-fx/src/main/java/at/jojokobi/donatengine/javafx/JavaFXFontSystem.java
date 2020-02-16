package at.jojokobi.donatengine.javafx;

import at.jojokobi.donatengine.platform.FontSystem;
import at.jojokobi.donatengine.style.Font;
import at.jojokobi.donatengine.util.Vector2D;
import javafx.scene.text.Text;

public class JavaFXFontSystem implements FontSystem {

	@Override
	public Vector2D calculateTextDimensions(String text, Font font) {
		Text fxtext = new Text(text);
		fxtext.setFont(toFXFont(font));
		return new Vector2D(fxtext.getLayoutBounds().getWidth(), fxtext.getLayoutBounds().getHeight());
	}
	
	public static final javafx.scene.text.Font toFXFont (Font font) {
		return new javafx.scene.text.Font(font.getFamily(), font.getSize());
	}
	
}
