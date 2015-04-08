package Applicatie;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import Agenda.Agenda;
import Objects.DrawObject;

public class SaveLoad {

	
	public static void save(Panel panel)
	{
		JFileChooser fileChooser = new JFileChooser();
		
		fileChooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(pathname.isFile() && pathname.getName().endsWith(".agn"))
					{
						return true;
					}else if(pathname.isDirectory()){return true;}else{return false;}
				}

				@Override
				public String getDescription() {
					return ".agn";	
				}
			
		});
		fileChooser.setDialogTitle("Choose save location");
		int userSelection = fileChooser.showSaveDialog(null);
		
		if(userSelection == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			if(!file.getName().endsWith(".agn"))
			{
				file = new File(file.getAbsolutePath() + ".agn");
			}
			
			if(file.exists())
			{
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite: " + file.getName(), "Overwrite", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
				{
					saveTerrain(file, panel);
				}
			}
			else
			{
				saveTerrain(file, panel);
			}
		}
	}
	
	
	public static void saveTerrain(File file, Panel panel) {

		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(panel.getAgenda());
			for (DrawObject object : panel.objects) {
				oos.writeObject(object);
			}
			oos.close();
			JOptionPane.showMessageDialog(null, "Saved succesfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	
	public static void load(Panel panel)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				if(pathname.isFile() && pathname.getName().endsWith(".agn"))
				{
					return true;
				}else if(pathname.isDirectory()){return true;}else{return false;}
			}

			@Override
			public String getDescription() {
				return ".agn";
			}
		
		});
		
		fileChooser.setDialogTitle("Choose file");
		int userSelection = fileChooser.showOpenDialog(null);
		
		if(userSelection == JFileChooser.APPROVE_OPTION)
		{
			File file = fileChooser.getSelectedFile();
			if(!file.exists())
			{
				JOptionPane.showMessageDialog(null, "This file does not exist: " + file.getName());
			}
			else
			{
				fillTerrain(file, panel);
				JOptionPane.showMessageDialog(null, "Loaded succesfully");
			}
		}
	}
	
	
	public static void fillTerrain(File file, Panel panel) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			Object object;
			Agenda a = (Agenda) ois.readObject();
			panel.setAgenda(a);
			try {
				object = ois.readObject();
				panel.clearObjects();
				while (object != null) {
					panel.objects.add((DrawObject) object); 					
					object = ois.readObject();
				}
			} catch (EOFException e) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		panel.update();
	}
	
	
	
}