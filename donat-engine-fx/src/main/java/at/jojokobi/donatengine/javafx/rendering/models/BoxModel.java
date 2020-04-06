package at.jojokobi.donatengine.javafx.rendering.models;


import at.jojokobi.donatengine.javafx.rendering.Perspective;
import at.jojokobi.donatengine.javafx.ressources.Texture;
import at.jojokobi.donatengine.objects.Camera;
import at.jojokobi.donatengine.util.Vector2D;
import at.jojokobi.donatengine.util.Vector3D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.paint.Color;

public class BoxModel extends RenderModel {
	
	private boolean border = false;
	private Texture front;
	private Texture top;
	private Texture left;
	private Texture right;
	private double width;
	private double height;
	private double length;

	
	
	public BoxModel(Texture front, Texture right, Texture left, Texture top, double width, double height,
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
	
	public BoxModel(Texture front, Texture right, Texture left, Texture top) {
		this(front, right, left, top, front != null ? front.getWidth() : 0, front != null ? front.getHeight() : 0,  top != null ? top.getHeight() : 0);
	}

	public BoxModel(Texture front, Texture top) {
		this (front, front, front, top);
	}

	@Override
	public void render(GraphicsContext ctx, Camera cam, Perspective perspective, double x, double y, double z, double timer) {
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
				ctx.drawImage(getLeft().getImage(timer), ul.getX(), ul.getY());
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
				ctx.drawImage(getLeft().getImage(timer), ul.getX(), ul.getY());
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
				ctx.drawImage(getFront().getImage(timer), ul.getX(), ul.getY());
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
				ctx.drawImage(getTop().getImage(timer), ul.getX(), ul.getY());
				if (border) {
					ctx.setEffect(null);
					ctx.strokePolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
				}
			}
			break;
		case FULL:
			if ((Math.abs(cam.getRotationX()) + 45) % 180 > 90) {
				//Top
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), 0)).round();
				ctx.drawImage(top.getImage(timer), ul.getX(), ul.getY());
			}
			else {
				//Front
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), getLength())).round();
				ctx.drawImage(front.getImage(timer), ul.getX(), ul.getY());
			}
			break;
		case ONLY_YZ:
			//Top
			{
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), 0)).round();
				Vector2D lr = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), getHeight(), getLength())).round();
				ctx.drawImage(top.getImage(timer), ul.getX(), ul.getY(), lr.getX() - ul.getX(), lr.getY() - ul.getY());
			}
			//Front
			{
				Vector2D ul = perspective.toScreenPosition(cam, pos.clone().add(0, getHeight(), getLength())).round();
				Vector2D lr = perspective.toScreenPosition(cam, pos.clone().add(getWidth(), 0, getLength())).round();
				ctx.drawImage(front.getImage(timer), ul.getX(), ul.getY(), lr.getX() - ul.getX(), lr.getY() - ul.getY());
			}
			break;
		}
	}

	public Texture getFront() {
		return front;
	}

	public Texture getTop() {
		return top;
	}

	public Texture getLeft() {
		return left;
	}

	public Texture getRight() {
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
