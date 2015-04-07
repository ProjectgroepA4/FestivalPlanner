package Applicatie;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import Agenda.Agenda;
import Listeners.Mouse;
import Listeners.MouseMotion;
import Listeners.MouseWheel;
import Objects.Entrance;
import Objects.Path;
import Objects.Podium;
import Objects.Toilet;
import Objects.Wall;


@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener {

	BufferedImage background;
	BufferedImage podiumImage, toiletImage, entranceImage, pathImage,
			wallImage;

	private int panelInfox, panelInfoy, scrollfactor;

	private ArrayList<BufferedImage> panelInfo = new ArrayList<BufferedImage>();
	// private ArrayList<Object> panelTypes = new ArrayList<Object>();

	ArrayList<DrawObject> objects = new ArrayList<>();
	ArrayList<Visitor> visitors = new ArrayList<>();
	DrawObject dragObject = null;
	private DrawObject selectedObject;
	private String clickedOption = "drag";

	Point2D cameraPoint = new Point2D.Double(getWidth() / 2, getHeight() / 2);
	float cameraScale = 1;

	PropertiesPanel pp;

	Point2D lastClickPosition = new Point(0, 0);
	Point lastMousePosition = new Point(0, 0);
	Agenda agenda;
	int currentTime = 540;
	int tick = 0;

	// how to nieuwe dingen aan het panel toe te voegen:
	// maak bufferedimage global aan, voeg er een image aan toe, en voeg de
	// image aan panelInfo toe en het object aan panelTypes.
	// en maak een case statement die een new Object returnt in
	// createNewDrawObject.

	Panel(PropertiesPanel pp) {
		this.pp = pp;
		new Images();
		pp.setPanel(this);
		try {
			background = ImageIO.read(new File("images/grass.jpg"));
			podiumImage = ImageIO.read(new File("images/stageIcon.png"));
			toiletImage = ImageIO.read(new File("images/wcIcon.png"));
			entranceImage = ImageIO.read(new File("images/entranceIcon.png"));
			pathImage = ImageIO.read(new File("images/pathIcon.png"));
			wallImage = ImageIO.read(new File("images/wallIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		panelInfo.add(podiumImage);
		panelInfo.add(toiletImage);
		panelInfo.add(entranceImage);
		panelInfo.add(pathImage);
		panelInfo.add(wallImage);

		panelInfox = 0;
		panelInfoy = 0;
		scrollfactor = 0;

		// objects.add(new Podium(new Point2D.Double(100, 100)));
		// objects.add(new Podium(new Point2D.Double(500, 100)));
		// objects.add(new Podium(new Point2D.Double(300, 400)));

		addMouseListener(new Mouse(this));

		addMouseMotionListener(new MouseMotion(this));
		agenda = new Agenda();


		addMouseWheelListener(new MouseWheel(this));
		new Timer(1000/20, this).start();

	}
	
	public void addVisitors(){
		visitors.add(new Visitor("visitor", new Point(100,300), agenda, objects));
	}

	public int getPanelInfoLength() {
		int panelInfoLength = 0;
		for (BufferedImage image : panelInfo) {
			panelInfoLength += image.getWidth();
		}

		return panelInfoLength;
	}

	public DrawObject createNewDrawObject(int index) {
		switch (index) {
		case 0:
			return new Podium(null);
		case 1:
			return new Toilet(null);
		case 2:
			return new Entrance(null);
		case 3:
			return new Path(null);
		case 4:
			return new Wall(null);
		default:
			return null;
		}
	}

	public void add(DrawObject dragObject) {
		objects.add(dragObject);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setClip(new Rectangle2D.Double(0, 0, getWidth(), 300));

		// g2.fill(new Ellipse2D.Double(0,0,300,300));

		// g2.drawImage(podiumImage, 0, 0, null);

		g2.drawLine(0, 150, getWidth(), 150);
		for (BufferedImage image : panelInfo) {
			g2.drawImage(image, panelInfox + scrollfactor, panelInfoy, null);
			panelInfox += image.getWidth();
		}
		panelInfox = 0;

		g2.setClip(new Rectangle2D.Double(0, 150, getWidth(), getHeight()));
		AffineTransform oldTransform = g2.getTransform();
		g2.setTransform(getCamera());

		TexturePaint p = new TexturePaint(background, new Rectangle2D.Double(0,0, 100, 100));
		g2.setPaint(p);
		g2.fill(new Rectangle2D.Double(-1920, -1080, 3840, 2160));

		BasicStroke stroke = new BasicStroke(10);
		g2.setStroke(stroke);

		for (DrawObject o : objects) {
			o.draw(g2);
		}
		
		for (Visitor v : visitors){
			v.draw(g2);
		}

		g2.setClip(null);
		g2.setTransform(oldTransform);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		tick++;
		if ( tick >= 10 && visitors.size() > 0){
			tick = 0;
			currentTime++;
			System.out.println(currentTime);
		}
		
		for (Visitor v: visitors){
			v.update(objects, currentTime, visitors);
		}
		repaint();	
	}

	public AffineTransform getCamera() {
		AffineTransform tx = new AffineTransform();
		tx.translate(-cameraPoint.getX() + getWidth() / 2, -cameraPoint.getY() + getHeight() / 2);
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

	public BufferedImage getPodiumImage() {
		return podiumImage;
	}

	public void setPodiumImage(BufferedImage podiumImage) {
		this.podiumImage = podiumImage;
	}

	public ArrayList<BufferedImage> getPanelInfo() {
		return panelInfo;
	}

	public void setPanelInfo(ArrayList<BufferedImage> panelInfo) {
		this.panelInfo = panelInfo;
	}

	public int getScrollfactor() {
		return scrollfactor;
	}

	public void setScrollfactor(int scrollfactor) {
		this.scrollfactor = scrollfactor;
	}

	public int getPanelInfox() {
		return panelInfox;
	}

	public void setPanelInfox(int panelInfox) {
		this.panelInfox = panelInfox;
	}

	public PropertiesPanel getPP() {
		return pp;
	}

	public void update() {
		repaint();
	}

	public DrawObject getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(DrawObject selectedObject) {
		this.selectedObject = selectedObject;
	}

	public String getClickedOption() {
		return clickedOption;
	}

	public void setClickedOption(String clickedOption) {
		this.clickedOption = clickedOption;
	}
	
	public void clearObjects(){
		objects.clear();
	}
}