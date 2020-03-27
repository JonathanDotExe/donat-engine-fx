package at.jojokobi.donatengine.javafx.rendering;

import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Image2DModel extends RenderModel{
	
	private Image image;
	private double width;
	private double height;
	private double length;

	public Image2DModel(Image image, double width, double height, double length) {
		super();
		this.image = image;
		this.width = width;
		this.height = height;
	}
	
	public Image2DModel(Image image) {
		this(image, image.getWidth(), image.getHeight(), image.getHeight());
	}

	@Override
	public void render(GraphicsContext ctx, Camera cam, Perspective perspective, double x, double y, double z) {
//		double relX = x - cam.getX();
//		double relY = cam.getHeight() - (y - cam.getY());
//		double relZ = z - cam.getZ();
//		
//		ctx.drawImage(image, relX, cam.mergeYAndZ(relY, relZ));

		
		Vector3D pos = new Vector3D(x, y + getHeight(), z);
		Vector2D renderPos = perspective.toScreenPosition(cam, pos).round();
		ctx.drawImage(image, renderPos.getX(), renderPos.getY(), width, height);
	}

	public Image getImage() {
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
