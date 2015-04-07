package Agenda;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class StagePanel extends JPanel 
{
	private BufferedImage image;
	private int width, height, posX;
	private double widthD, posXD;
	private Event event;
	public StagePanel(int width, int height, int posX, Event event, double widthD, double posXD) 
	{
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.event = event;
		this.widthD = widthD;
		this.posXD = posXD;
		image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = image.createGraphics();
		g2.setColor(Color.blue);
		g2.fillRect(0, 0, image.getWidth(), image.getHeight());
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		((Graphics2D)g).drawImage(image, this.posX, 0, this);
	}

	public StagePanel(LayoutManager arg0) 
	{
		super(arg0);

	}

	public StagePanel(boolean arg0) 
	{
		super(arg0);

	}
	
	public void update(int posx, double posXD)
	{
		this.posX = posx;
		this.posXD = posXD;
		repaint();
	}
	

	public StagePanel(LayoutManager arg0, boolean arg1) 
	{
		super(arg0, arg1);

	}
	
	public double getPosX()
	{
		return this.posXD;
	}
	
	public void setLength(int length, double widthD)
	{
		this.width = length;
		this.widthD = widthD;
	}
	
	public void setStageStartTime(int hours, int minutes)
	{
		event.setStartTime(hours, minutes);
	}
	
	public void setStageEndTime(int hours, int minutes)
	{
		event.setEndTime(hours, minutes);
	}
	
	public int getStageStartTime()
	{
		return event.getStartTime();
	}
	
	public double getImageWidth()
	{
		return this.widthD;
	}

}
