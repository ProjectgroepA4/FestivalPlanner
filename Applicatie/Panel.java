package Applicatie;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Listneners.Mouse;
import Listneners.MouseMotion;
import Listneners.MouseWheel;
import Objects.Podium;

public class Panel extends JPanel {
	
	BufferedImage background;
	
	ArrayList<DrawObject> objects = new ArrayList<>();
	DrawObject dragObject = null;
	
	Point2D cameraPoint = new Point2D.Double(getWidth()/2,getHeight()/2);
	float cameraScale = 1;
	
	
	Point2D lastClickPosition;
	Point lastMousePosition;
	
	Panel()
	{
		try {
			background = ImageIO.read(new File("images/grass.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		objects.add(new Podium(new Point2D.Double(100, 100)));
		objects.add(new Podium(new Point2D.Double(500, 100)));
		objects.add(new Podium(new Point2D.Double(300, 400)));
	
		addMouseListener(new Mouse(this));
		
		addMouseMotionListener(new MouseMotion(this));
		
		addMouseWheelListener(new MouseWheel(this));
		
	}
	
	
	public void add(DrawObject dragObject)
	{
		objects.add(dragObject);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setClip(new Rectangle2D.Double(0,0, 200, getHeight()));

		g2.fill(new Ellipse2D.Double(0,0,300,300));
		
		
		g2.setClip(new Rectangle2D.Double(200,0, getWidth()-200, getHeight()));
		AffineTransform oldTransform = g2.getTransform();
		g2.setTransform(getCamera());
		
		TexturePaint p = new TexturePaint(background, new Rectangle2D.Double(0, 0, 100, 100));
		g2.setPaint(p);
		g2.fill(new Rectangle2D.Double(0,0,1920,1080));
		
		for(DrawObject o : objects)
			o.draw(g2);
		
		
		g2.setClip(null);
		g2.setTransform(oldTransform);
	}

	public AffineTransform getCamera() {
		AffineTransform tx = new AffineTransform();
		tx.translate(-cameraPoint.getX() + getWidth()/2, -cameraPoint.getY() + getHeight()/2);
		tx.scale(cameraScale, cameraScale);
		
		return tx;
	}
	
	public Point2D getClickPoint(Point point) {
		try {
			return getCamera().inverseTransform(point, null);
		} catch (NoninvertibleTransformException e1) {
			e1.printStackTrace();
		}
		return null;
	}



	public void setBackground(BufferedImage background) {
		this.background = background;
	}



	public ArrayList<DrawObject> getObjects() {
		return objects;
	}


	public void setObjects(ArrayList<DrawObject> objects) {
		this.objects = objects;
	}


	public DrawObject getDragObject() {
		return dragObject;
	}


	public void setDragObject(DrawObject dragObject) {
		this.dragObject = dragObject;
	}


	public Point2D getCameraPoint() {
		return cameraPoint;
	}


	public void setCameraPoint(Point2D cameraPoint) {
		this.cameraPoint = cameraPoint;
	}


	public float getCameraScale() {
		return cameraScale;
	}


	public void setCameraScale(float cameraScale) {
		this.cameraScale = cameraScale;
	}


	public Point2D getLastClickPosition() {
		return lastClickPosition;
	}


	public void setLastClickPosition(Point2D lastClickPosition) {
		this.lastClickPosition = lastClickPosition;
	}


	public Point getLastMousePosition() {
		return lastMousePosition;
	}


	public void setLastMousePosition(Point lastMousePosition) {
		this.lastMousePosition = lastMousePosition;
	}
	

}
