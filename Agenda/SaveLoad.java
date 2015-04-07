package Agenda;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class SaveLoad {
	
	public static void saveFile(Object obj)
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
					return "Agenda (.agn)";
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
					save(obj, file);
				}
			}
			else
			{
				save(obj, file);
			}
		}
	}
	
	public static Object loadFile()
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
				return "Agenda (.agn)";
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
				return load(file);
			}
		}
		
		return null;
		}
		
		private static void save(Object obj, File file)
		{
			ObjectOutputStream oostr = null;
			try {
				oostr = new ObjectOutputStream(new FileOutputStream(file));
				oostr.writeObject(obj);
				oostr.flush();
				oostr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private static Object load(File file)
		{
			ObjectInputStream oistr = null;
			try {
				oistr = new ObjectInputStream(new FileInputStream(file));
				Object obj = oistr.readObject();
				oistr.close();
				return obj;
			} catch (IOException | ClassNotFoundException e) {
				return null;
			}
		}
}
