package at.jojokobi.donatengine.javafx.ressources;

public class ImageEntry {

	private int frames;
	private String path;
	private double frameDuration = 1/24.0;

	public String getPath() {
		return path;
	}

	public void setPath(String image) {
		this.path = image;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public double getFrameDuration() {
		return frameDuration;
	}

	public void setFrameDuration(double frameDuration) {
		this.frameDuration = frameDuration;
	}
	
}
