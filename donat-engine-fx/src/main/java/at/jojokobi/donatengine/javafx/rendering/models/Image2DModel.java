package at.jojokobi.donatengine.javafx.rendering.models;

import at.jojokobi.donatengine.javafx.rendering.Perspective;
import at.jojokobi.donatengine.javafx.ressources.Texture;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;


public class Image2DModel extends RenderModel{
	
	private Texture image;
	private double width;
	private double height;
	private double length;

	public Image2DModel(Texture image, double width, double height, double length) {
		super();
		this.image = image;
		this.width = width;
		this.height = height;
		this.length = length;
	}
	
	public Image2DModel(Texture image) {
		this(image, image.getWidth(), image.getHeight(), image.getHeight());
	}

	@Override
	public void render(GraphicsContext ctx, Camera cam, Perspective perspective, double x, double y, double z, double timer) {
//		double relX = x - cam.getX();
//		double relY = cam.getHeight() - (y - cam.getY());
//		double relZ = z - cam.getZ();
//		
//		ctx.drawTexture(image, relX, cam.mergeYAndZ(relY, relZ));

		
		Vector3D pos = new Vector3D(x, y, z + getLength());
		Vector2D renderPos = perspective.toScreenPosition(cam, pos).round();
		ctx.drawImage(image.getImage(timer), renderPos.getX(), renderPos.getY() - height, width, height);
	}

	public Texture getImage() {
		return image;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getLength() {
		return length;
	}

}
