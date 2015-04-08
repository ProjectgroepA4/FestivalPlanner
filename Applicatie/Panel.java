package Applicatie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Agenda.Agenda;
import Agenda.AgendaStage;
import Listeners.Mouse;
import Listeners.MouseMotion;
import Listeners.MouseWheel;
import Listeners.WindowFocusListener;
import Objects.DrawObject;
import Objects.Entrance;
import Objects.Food;
import Objects.Path;
import Objects.Stage;
import Objects.Toilet;
import Objects.Wall;

@SuppressWarnings("serial")
public class Panel extends JPanel implements ActionListener
{

	BufferedImage grass, grass2, sand, sand2, stone;
	BufferedImage background;
	BufferedImage podiumImage, toiletImage, entranceImage, pathImage, wallImage, foodImage, waypointImage;

	private int panelInfox, panelInfoy, scrollfactor;

	private ArrayList<BufferedImage> panelInfo = new ArrayList<BufferedImage>();
	// private ArrayList<Object> panelTypes = new ArrayList<Object>();
	ArrayList<Visitor> visitors = new ArrayList<>();
	ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
	ArrayList<DrawObject> objects = new ArrayList<>();
	ArrayList<Path> paths = new ArrayList<>();
	DrawObject dragObject = null;
	private DrawObject selectedObject;
	private String clickedOption = "drag";
	private Path currentPath;
	private static int width = 1920;
	private static int height = 1080;

	Point2D cameraPoint = new Point2D.Double(getWidth() / 2, getHeight() / 2);
	float cameraScale = 1;
	PropertiesPanel pp;
	ControlPanel cp;
	Agenda agenda;
	Point2D lastClickPosition = new Point(0, 0);
	Point lastMousePosition = new Point(0, 0);
	Point2D selectionPosition = new Point(0, 0);
	Images images = new Images();
	Timer t;
	
	SimpleDateFormat formatter;
	GregorianCalendar date;

	int currentTime = 540;
	int tick = 0;

	// how to nieuwe dingen aan het panel toe te voegen:
	// maak bufferedimage global aan, voeg er een image aan toe, en voeg de
	// image aan panelInfo toe en het object aan panelTypes.
	// en maak een case statement die een new Object returnt in
	// createNewDrawObject.

	public Point2D getSelectionPosition()
	{
		return selectionPosition;
	}

	public void setSelectionPosition(Point2D selectionPosition)
	{
		this.selectionPosition = selectionPosition;
	}

	Panel(PropertiesPanel pp, ControlPanel cp)
	{
		this.pp = pp;
		this.cp = cp;
		cp.setPanel(this);
		pp.setPanel(this);
		date = new GregorianCalendar();
		formatter = new SimpleDateFormat("H:s dd-MM-yyyy");
		try
		{
			grass = ImageIO.read(new File("images/grass.jpg"));
			grass2 = ImageIO.read(new File("images/grass2.png"));
			sand = ImageIO.read(new File("images/sand.jpg"));
			sand2 = ImageIO.read(new File("images/sand2.jpg"));
			stone = ImageIO.read(new File("images/stone.jpg"));
			podiumImage = ImageIO.read(new File("images/stageIcon.png"));
			toiletImage = ImageIO.read(new File("images/wcIcon.png"));
			entranceImage = ImageIO.read(new File("images/entranceIcon.png"));
			wallImage = ImageIO.read(new File("images/wallIcon.png"));
			foodImage = ImageIO.read(new File("images/foodIcon.png"));
			waypointImage = ImageIO.read(new File("images/waypointIcon.png"));
			background = grass;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		panelInfo.add(podiumImage);
		panelInfo.add(toiletImage);
		panelInfo.add(entranceImage);
		panelInfo.add(wallImage);
		panelInfo.add(foodImage);
		panelInfo.add(waypointImage);

		agenda = new Agenda();

		panelInfox = 0;
		panelInfoy = 0;
		scrollfactor = 0;

		// objects.add(new Podium(new Point2D.Double(100, 100)));
		// objects.add(new Podium(new Point2D.Double(500, 100)));
		// objects.add(new Podium(new Point2D.Double(300, 400)));

		addMouseListener(new Mouse(this));

		addMouseMotionListener(new MouseMotion(this));

		addMouseWheelListener(new MouseWheel(this));

		addFocusListener(new WindowFocusListener(this));
		t = new Timer(1000 / 10, this);
	}

	public int getPanelInfoLength()
	{
		int panelInfoLength = 0;
		for (BufferedImage image : panelInfo)
		{
			panelInfoLength += image.getWidth();
		}

		return panelInfoLength;
	}

	public DrawObject createNewDrawObject(int index)
	{
		switch (index)
		{
			case 0:
				ArrayList<AgendaStage> stages = agenda.getStages();
				Object[] s = new Object[stages.size()];
				AgendaStage stage = null;
				if (stages.size() != 0)
				{
					for (int i = 0; i < stages.size(); i++)
					{
						s[i] = stages.get(i);
					}

					stage = (AgendaStage) JOptionPane.showInputDialog(null, "Select the right Stage", "Select Stage", JOptionPane.PLAIN_MESSAGE, null, s, "stage");
				}
				if (stage != null)
				{
					return new Stage(null, stage);
				}
				else
				{
					return null;
				}
			case 1:
				return new Toilet(null);
			case 2:
				return new Entrance(null);
			case 3:
				return new Wall(null);
			case 4:
				return new Food(null);
			case 5:
				return new Waypoint(null);
			default:
				return null;
		}
	}

	public void add(DrawObject dragObject)
	{
		objects.add(dragObject);
	}

	public void addWaypoint(Waypoint w)
	{
		waypoints.add(w);
	}
	
	public Waypoint getWaypoint(int i)
	{
		for(Waypoint w : waypoints)
		{
			if(w.getSelf() == i)
			{
				return w;
			}
		}
		return null;
	}

	public ArrayList<Waypoint> getWaypoints()
	{
		return waypoints;
	}

	public void addVisitors()
	{
		ArrayList<DrawObject> entrances = new ArrayList<>();
		for(DrawObject object : objects) {
			if(object instanceof Entrance) {
				entrances.add(object);
			}
		}
		if(!entrances.isEmpty()) {
			DrawObject entrance = entrances.get((int) Math.floor(Math.random()*entrances.size()));
			Point point = new Point((int)entrance.getPosition().getX(),(int)entrance.getPosition().getY());
			visitors.add(new Visitor("visitor",point, agenda, objects));
		}
		else {
			JOptionPane.showMessageDialog(this, "You don't have an entrance");
		}
	}

	public void addVisitors(int count)
	{
		for (int i = 0; i < count; i++)
		{
			addVisitors();
		}
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setClip(new Rectangle2D.Double(0, 0, getWidth(), 150));
		Stroke old = g2.getStroke();
		g2.setStroke(new BasicStroke(10));
		g2.drawLine(0, 150, getWidth(), 150);
		g2.setStroke(old);
		for (BufferedImage image : panelInfo)
		{
			g2.drawImage(image, panelInfox + scrollfactor, panelInfoy, null);
			panelInfox += image.getWidth();
		}
		panelInfox = 0;

		g2.setClip(new Rectangle2D.Double(0, 150, getWidth(), getHeight()));
		AffineTransform oldTransform = g2.getTransform();
		g2.setTransform(getCamera());

		TexturePaint p = new TexturePaint(background, new Rectangle2D.Double(0, 0, 100, 100));
		g2.setPaint(p);

		g2.fill(new Rectangle2D.Double(-width / 2, -height / 2, width, height));

		BasicStroke stroke = new BasicStroke(10);
		g2.setStroke(stroke);

		for (Path path : paths)
		{
			path.draw(g2);
		}
		for (DrawObject o : objects)
		{
			o.draw(g2);
		}

		for (Visitor v : visitors)
		{
			v.draw(g2);
		}

		g2.setTransform(oldTransform);
		if (currentPath != null)
		{ // Display text while in path making modes.
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Verdana", Font.ITALIC, 25));
			g2.drawString("Press enter to finish the path.", 20, 185);
		}
		g2.setClip(null);

	}

	public AffineTransform getCamera()
	{
		AffineTransform tx = new AffineTransform();
		tx.translate(-cameraPoint.getX() + getWidth() / 2, -cameraPoint.getY() + getHeight() / 2);
		tx.scale(cameraScale, cameraScale);
		return tx;
	}

	public Point2D getClickPoint(Point point)
	{
		try
		{
			return getCamera().inverseTransform(point, null);
		}
		catch (NoninvertibleTransformException e1)
		{
			e1.printStackTrace();
		}
		return null;
	}

	public void setBackground(BufferedImage background)
	{
		this.background = background;
	}

	public ArrayList<DrawObject> getObjects()
	{
		return objects;
	}

	public void setObjects(ArrayList<DrawObject> objects)
	{
		this.objects = objects;
	}

	public DrawObject getDragObject()
	{
		return dragObject;
	}

	public void setDragObject(DrawObject dragObject)
	{
		this.dragObject = dragObject;
	}

	public Point2D getCameraPoint()
	{
		return cameraPoint;
	}

	public void setCameraPoint(Point2D cameraPoint)
	{
		this.cameraPoint = cameraPoint;
	}

	public float getCameraScale()
	{
		return cameraScale;
	}

	public void setCameraScale(float cameraScale)
	{
		this.cameraScale = cameraScale;
	}

	public Point2D getLastClickPosition()
	{
		return lastClickPosition;
	}

	public void setLastClickPosition(Point2D lastClickPosition)
	{
		this.lastClickPosition = lastClickPosition;
	}

	public void clearObjects()
	{
		objects.clear();
	}

	public Point getLastMousePosition()
	{
		return lastMousePosition;
	}

	public void setLastMousePosition(Point lastMousePosition)
	{
		this.lastMousePosition = lastMousePosition;
	}

	public BufferedImage getPodiumImage()
	{
		return podiumImage;
	}

	public void setPodiumImage(BufferedImage podiumImage)
	{
		this.podiumImage = podiumImage;
	}

	public ArrayList<BufferedImage> getPanelInfo()
	{
		return panelInfo;
	}

	public void setPanelInfo(ArrayList<BufferedImage> panelInfo)
	{
		this.panelInfo = panelInfo;
	}

	public int getScrollfactor()
	{
		return scrollfactor;
	}

	public void setScrollfactor(int scrollfactor)
	{
		this.scrollfactor = scrollfactor;
	}

	public int getPanelInfox()
	{
		return panelInfox;
	}

	public void setPanelInfox(int panelInfox)
	{
		this.panelInfox = panelInfox;
	}

	public PropertiesPanel getPP()
	{
		return pp;
	}

	public void update()
	{
		repaint();
	}

	public DrawObject getSelectedObject()
	{
		return selectedObject;
	}

	public void setSelectedObject(DrawObject selectedObject)
	{
		this.selectedObject = selectedObject;
	}

	public String getClickedOption()
	{
		return clickedOption;
	}

	public void setClickedOption(String clickedOption)
	{
		this.clickedOption = clickedOption;
	}

	/**
	 * Remove a DrawObject from the list.
	 * 
	 * @param o
	 *            - the DrawObject you want to remove from the list.
	 */
	public void removeObject(DrawObject o)
	{
		Iterator<DrawObject> itr = objects.iterator();
		while (itr.hasNext())
		{
			DrawObject nextObject = (DrawObject) itr.next();
			if (nextObject.equals(o))
			{
				pp.clearSelected();
				itr.remove();
			}
			repaint();
		}
	}

	public void clearObjectSelection()
	{
		for (DrawObject o : objects)
		{
			o.setSelected(false);
		}
	}

	public void checkCollision()
	{
		boolean collision = false;
		for (DrawObject o : getObjects())
		{
			if (getSelectedObject().collision(o) && o != getSelectedObject())
			{
				collision = true;
				getSelectedObject().setRectangleColor(Color.RED);
				break;
			}
			if (!collision)
				getSelectedObject().setRectangleColor(Color.BLACK);
		}
	}

	/**
	 * Begin making a new path.
	 */
	public void startPath()
	{
		pp.clearSelected();
		setSelectedObject(null);
		clearObjectSelection();
		setClickedOption("Path");
		currentPath = new Path();
		paths.add(currentPath);
		pp.setSelectedPath(currentPath);
	}

	/**
	 * Get the current selected Path.
	 * 
	 * @return - the selected Path.
	 */
	public Path getCurrentPath()
	{
		return currentPath;
	}

	/**
	 * Set the selected Path object.
	 * 
	 * @param path
	 *            - the selected Path object.
	 */
	public void setcurrentPath(Path path)
	{
		currentPath = path;
		pp.setSelectedPath(currentPath);
	}

	/**
	 * Checks if the point is contained in one of the path's.
	 * 
	 * @param point
	 *            - The point to check for.
	 * @return if the point is contained in one of the path's.
	 */
	public boolean checkPath(Point2D point)
	{
		for (Path path : paths)
		{
			if (path.containsPoint(point))
			{

				setcurrentPath(path);
				setClickedOption("Path");

				return true;
			}
		}
		return false;
	}
	
	public Path getClickedPath(Point2D point)
	{
		for (Path path : paths)
		{
			if (path.containsPoint(point))
			{
				return path;
			}
		}
		return null;
	}

	/**
	 * Remove a path from the list.
	 * 
	 * @param path
	 *            - the path you want to remove from the list.
	 */
	public void removePath(Path path)
	{
		Iterator<Path> it = paths.iterator();
		while (it.hasNext())
		{
			if (it.next().equals(path))
			{
				pp.clearSelected();
				setClickedOption("drag");
				currentPath = null;
				it.remove();
			}
			repaint();
		}
	}

	public Agenda getAgenda()
	{
		return agenda;
	}

	public void setAgenda(Agenda agenda)
	{
		this.agenda = agenda;
	}

	public static int getFieldWidth()
	{
		return width;
	}

	public static int getFieldHeight()
	{
		return height;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		tick++;
		if (tick >= 10 && visitors.size() > 0)
		{
			tick = 0;
			currentTime++;
			date.setTimeInMillis(date.getTimeInMillis() + 1000);
			cp.setTime(formatter.format(date.getTime()));
			System.out.println(currentTime);
		}

		for (Visitor v : visitors)
		{
			v.update(objects, currentTime, visitors, paths, this);
		}
		repaint();

	}

	public Timer getT()
	{
		return t;
	}

	public ArrayList<Path> getPaths()
	{
		return paths;
	}

	public void setT(Timer t)
	{
		this.t = t;
	}

	public void newWorld(int width, int height, int terrainIndex)
	{
		this.width = width;
		this.height = height;
		paths.clear();
		objects.clear();
		cameraScale = 1;
		switch (terrainIndex)
		{
			case 0:
				background = grass;
				break;
			case 1:
				background = grass2;
				break;
			case 2:
				background = sand;
				break;
			case 3:
				background = sand2;
				break;
			case 4:
				background = stone;
				break;

		}
		repaint();
	}
}
