package at.jojokobi.donatengine.javafx.rendering;


import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class BoxModel extends RenderModel {
	
	private boolean border = false;
	private Image front;
	private Image top;
	private Image left;
	private Image right;
	private double width;
	private double height;
	private double length;

	
	
	public BoxModel(Image front, Image top, Image left, Image right, double width, double height,
			double length) {
		super();
		this.front = front;
		this.top = top;
		this.left = left;
		this.right = right;
		this.width = width;
		this.height = height;
		this.length = length;
	}
	
	public BoxModel(Image front, Image top, Image left, Image right) {
		this(front, top, left, right, front != null ? front.getWidth() : 0, front != null ? front.getHeight() : 0,  top != null ? top.getHeight() : 0);
	}

	public BoxModel(Image front, Image top) {
		this (front, front, front, top);
	}

	@Override
	public void render(GraphicsContext ctx, Camera cam, Perspective perspective, double x, double y, double z) {
		Vector3D pos = new Vector3D(x, y, z);
		ctx.setStroke(Color.BLACK);
		switch (perspective.getOptimizationLevel()) {
		case NONE:
			//Left side
			{
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), 0)).round();
				Vector2D ur = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), getLength())).round();
				Vector2D lr = perspective.toScreenPosition(cam, pos.clone().add(0, 0, getLength())).round();
				Vector2D ll = perspective.toScreenPosition(cam, pos.clone().add(0, 0, 0)).round();
				
				ctx.setEffect(new PerspectiveTransform(ul.getX(), ul.getY(), ur.getX(), ur.getY(), lr.getX(), lr.getY(), ll.getX(), ll.getY()));
				ctx.drawImage(getLeft(), ul.getX(), ul.getY());
				if (border) {
					ctx.setEffect(null);
					ctx.strokePolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
				}
			}
			//Left side
			{
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), getHeight(), 0)).round();
				Vector2D ur = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), getHeight(), getLength())).round();
				Vector2D lr = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), 0, getLength())).round();
				Vector2D ll = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), 0, 0)).round();
				
				ctx.setEffect(new PerspectiveTransform(ul.getX(), ul.getY(), ur.getX(), ur.getY(), lr.getX(), lr.getY(), ll.getX(), ll.getY()));
				ctx.drawImage(getLeft(), ul.getX(), ul.getY());
				if (border) {
					ctx.setEffect(null);
					ctx.strokePolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
				}
			}
			//Front side
			{
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), getLength())).round();
				Vector2D ur = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), getHeight(), getLength())).round();
				Vector2D lr = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), 0, getLength())).round();
				Vector2D ll = perspective.toScreenPosition(cam, pos.clone().add(0, 0, getLength())).round();
				
				ctx.setEffect(new PerspectiveTransform(ul.getX(), ul.getY(), ur.getX(), ur.getY(), lr.getX(), lr.getY(), ll.getX(), ll.getY()));
				ctx.drawImage(getFront(), ul.getX(), ul.getY());
				if (border) {
					ctx.setEffect(null);
					ctx.strokePolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
				}
			}
			//Top side
			{
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), 0)).round();
				Vector2D ur = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), getHeight(), 0)).round();
				Vector2D lr = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), getHeight(), getLength())).round();
				Vector2D ll = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), getLength())).round();
				
				ctx.setEffect(new PerspectiveTransform(ul.getX(), ul.getY(), ur.getX(), ur.getY(), lr.getX(), lr.getY(), ll.getX(), ll.getY()));
				ctx.drawImage(getTop(), ul.getX(), ul.getY());
				if (border) {
					ctx.setEffect(null);
					ctx.strokePolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
				}
			}
			break;
		case FULL:
			if ((cam.getRotationX() + 45) % 180 > 90) {
				//Top
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), 0)).round();
				ctx.drawImage(top, ul.getX(), ul.getY());
			}
			else {
				//Front
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), getLength())).round();
				ctx.drawImage(top, ul.getX(), ul.getY());
			}
			break;
		case ONLY_YZ:
			//Top
			{
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), 0)).round();
				Vector2D lr = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), getHeight(), getLength())).round();
				ctx.drawImage(top, ul.getX(), ul.getY(), lr.getX() - ul.getX(), lr.getY() - ul.getY());
			}
			//Front
			{
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), getLength())).round();
				Vector2D lr = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), 0, getLength())).round();
				ctx.drawImage(front, ul.getX(), ul.getY(), lr.getX() - ul.getX(), lr.getY() - ul.getY());
			}
			break;
		}
	}

	public Image getFront() {
		return front;
	}

	public Image getTop() {
		return top;
	}

	public Image getLeft() {
		return left;
	}

	public Image getRight() {
		return right;
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

	public boolean isBorder() {
		return border;
	}

	public BoxModel setBorder(boolean border) {
		this.border = border;
		return this;
	}

}
