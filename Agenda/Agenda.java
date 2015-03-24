package Agenda;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

public class Agenda implements Serializable {

	private static final long serialVersionUID = 1;

	private ArrayList<Stage> stages;
	private ArrayList<Event> events;
	private ArrayList<Artist> artists;

	public Agenda() {
		stages = new ArrayList<Stage>();
		events = new ArrayList<Event>();
		artists = new ArrayList<Artist>();
	}
	
	public void addStage(Stage stage){
		stages.add(stage);
	}

	public void addEvent(Event event) {
		events.add(event);
	}

	public void addArtist(Artist artist) {
		artists.add(artist);
	}
	
	public ArrayList<Event> getEvents()
	{
		return events;
	}
	public ArrayList<Artist> getArtists()
	{
		return artists;
	}
	public ArrayList<Stage> getStages()
	{
		return stages;
	}
	
	public void saveAgenda()
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
					saveAgenda(file);
				}
			}
			else
			{
				saveAgenda(file);
			}
		}
	}
	
	public void loadAgenda()
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
				clearAgenda();
				fillAgenda(file);
				JOptionPane.showMessageDialog(null, "Loaded succesfully");
			}
		}
		}

	public void fillAgenda(File file) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			Object object;
			object = ois.readObject();
			Agenda a = (Agenda) object; 
			events = a.getEvents();
			stages = a.getStages();
			artists = a.getArtists();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void fillArtists()
	{
		for (Event e : events)
		{
			boolean exists = false;
			String name = e.getArtist().getName();
			for (Artist a : artists)
			{
				if ( a.getName().equals(name) ) {
					exists = true;
				}
			}
			if ( exists == false) {
				addArtist(e.getArtist());
			}
		}
	}
	
	public void fillStages()
	{
		for (Event e : events)
		{
			boolean exists = false;
			String name = e.getStage().getName();
			for (Stage s : stages)
			{
				if ( s.getName().equals(name) ) {
					exists = true;
				}
			}
			if ( exists == false) {
				addStage(e.getStage());
			}
		}
	}

	public void saveAgenda(File file) {

		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			JOptionPane.showMessageDialog(null, "Saved succesfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
	
	public void clearAgenda()
	{
		events.clear();
		stages.clear();
		artists.clear();
	}	
	
	public void fillAllLists()
	{
		loadAgenda();
		fillStages();
		fillArtists();
	}
}
