package Applicatie;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class Images
{

	private static Map<String, Image> images;

	public Images()
	{
		images = new HashMap<String, Image>();

		images.put("entrance", loadImage("images/entrance.png"));
		images.put("wc", loadImage("images/wc.png"));
		images.put("path", loadImage("images/path.jpg"));
		images.put("stage", loadImage("images/stage4.png"));
		images.put("wall", loadImage("images/wall.png"));
		images.put("stage1", loadImage("images/stage3.png"));
		images.put("visitor", loadImage("images/visitor.png"));
		images.put("food", loadImage("images/food.png"));
		images.put("waypoint", loadImage("images/waypoint.png"));
	}

	public static Image loadImage(String path)
	{
		return new ImageIcon(path).getImage();
	}

	public static Image getImage(String getter)
	{
		return images.get(getter);
	}
}