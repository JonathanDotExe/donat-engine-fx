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

	public BoxModel(Image front, Image right, Image left, Image top) {
		super();
		this.front = front;
		this.right = right;
		this.left = left;
		this.top = top;
	}
	
	public BoxModel(Image front, Image top) {
		this (front, front, front, top);
	}

	@Override
	public void render(GraphicsContext ctx, Camera cam, double x, double y, double z) {
		Vector3D pos = new Vector3D(x, y, z);
		ctx.setStroke(Color.BLACK);
		switch (cam.getPerspective().getOptimizationLevel()) {
		case NONE:
			//Left side
			{
				Vector2D ul = cam.toScreenPosition(pos.clone().add(0, getHeight(), 0)).round();
				Vector2D ur = cam.toScreenPosition(pos.clone().add(0, getHeight(), getLength())).round();
				Vector2D lr = cam.toScreenPosition(pos.clone().add(0, 0, getLength())).round();
				Vector2D ll = cam.toScreenPosition(pos.clone().add(0, 0, 0)).round();
				
				ctx.setEffect(new PerspectiveTransform(ul.getX(), ul.getY(), ur.getX(), ur.getY(), lr.getX(), lr.getY(), ll.getX(), ll.getY()));
				ctx.drawImage(getLeft(), ul.getX(), ul.getY());
				if (border) {
					ctx.setEffect(null);
					ctx.strokePolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
				}
			}
			//Left side
			{
				Vector2D ul = cam.toScreenPosition(pos.clone().add(getWidth(), getHeight(), 0)).round();
				Vector2D ur = cam.toScreenPosition(pos.clone().add(getWidth(), getHeight(), getLength())).round();
				Vector2D lr = cam.toScreenPosition(pos.clone().add(getWidth(), 0, getLength())).round();
				Vector2D ll = cam.toScreenPosition(pos.clone().add(getWidth(), 0, 0)).round();
				
				ctx.setEffect(new PerspectiveTransform(ul.getX(), ul.getY(), ur.getX(), ur.getY(), lr.getX(), lr.getY(), ll.getX(), ll.getY()));
				ctx.drawImage(getLeft(), ul.getX(), ul.getY());
				if (border) {
					ctx.setEffect(null);
					ctx.strokePolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
				}
			}
			//Front side
			{
				Vector2D ul = cam.toScreenPosition(pos.clone().add(0, getHeight(), getLength())).round();
				Vector2D ur = cam.toScreenPosition(pos.clone().add(getWidth(), getHeight(), getLength())).round();
				Vector2D lr = cam.toScreenPosition(pos.clone().add(getWidth(), 0, getLength())).round();
				Vector2D ll = cam.toScreenPosition(pos.clone().add(0, 0, getLength())).round();
				
				ctx.setEffect(new PerspectiveTransform(ul.getX(), ul.getY(), ur.getX(), ur.getY(), lr.getX(), lr.getY(), ll.getX(), ll.getY()));
				ctx.drawImage(getFront(), ul.getX(), ul.getY());
				if (border) {
					ctx.setEffect(null);
					ctx.strokePolygon(new double[] {ul.getX(), ur.getX(), lr.getX(), ll.getX()}, new double[] {ul.getY(), ur.getY(), lr.getY(), ll.getY()}, 4);
				}
			}
			//Top side
			{
				Vector2D ul = cam.toScreenPosition(pos.clone().add(0, getHeight(), 0)).round();
				Vector2D ur = cam.toScreenPosition(pos.clone().add(getWidth(), getHeight(), 0)).round();
				Vector2D lr = cam.toScreenPosition(pos.clone().add(getWidth(), getHeight(), getLength())).round();
				Vector2D ll = cam.toScreenPosition(pos.clone().add(0, getHeight(), getLength())).round();
				
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
				Vector2D ul = cam.toScreenPosition(pos.clone().add(0, getHeight(), 0)).round();
				ctx.drawImage(top, ul.getX(), ul.getY());
			}
			else {
				//Front
				Vector2D ul = cam.toScreenPosition(pos.clone().add(0, getHeight(), getLength())).round();
				ctx.drawImage(top, ul.getX(), ul.getY());
			}
			break;
		case ONLY_YZ:
			//Top
			{
				Vector2D ul = cam.toScreenPosition(pos.clone().add(0, getHeight(), 0)).round();
				Vector2D lr = cam.toScreenPosition(pos.clone().add(getWidth(), getHeight(), getLength())).round();
				ctx.drawImage(top, ul.getX(), ul.getY(), lr.getX() - ul.getX(), lr.getY() - lr.getY());
			}
			//Front
			{
				Vector2D ul = cam.toScreenPosition(pos.clone().add(0, getHeight(), getLength())).round();
				Vector2D lr = cam.toScreenPosition(pos.clone().add(getWidth(), 0, getLength())).round();
				ctx.drawImage(top, ul.getX(), ul.getY(), lr.getX() - ul.getX(), lr.getY() - lr.getY());
			}
			break;
		}
		
//		double relX = x - cam.getX();
//		double relY = cam.getHeight() - (y - cam.getY());
//		double relZ = z - cam.getZ();
//		
//		double startY = Math.round(cam.mergeYAndZ(relY, relZ));
//		double topHeight = Math.round(getLength() * cam.getZMultiplier());
//		double frontHeight = Math.round(getHeight() * cam.getYMultiplier());
		
//		if (frontHeight >= 0) {
//			ctx.drawImage(top, relX, startY, getWidth(), topHeight);
//			ctx.drawImage(front, relX, startY + topHeight, getWidth(), frontHeight);
//		}
//		else {
//			ctx.drawImage(front, relX, startY, getWidth(), frontHeight);
//			ctx.drawImage(top, relX, startY + frontHeight, getWidth(), topHeight);
//		}
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
		return front != null ? front.getWidth() : 0;
	}

	@Override
	public double getHeight() {
		return front != null ? front.getHeight() : 0;
	}

	@Override
	public double getLength() {
		return top != null ? top.getHeight() : 0;
	}

	public boolean isBorder() {
		return border;
	}

	public BoxModel setBorder(boolean border) {
		this.border = border;
		return this;
	}

}
