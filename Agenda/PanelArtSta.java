package Agenda;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PanelArtSta extends JPanel implements Panel{

	private Agenda a;
	JList artists;
	JList stages;
	Object[] dataArtist;
	Object[] dataStage;
	Window w;
	
	public PanelArtSta(Window w)
	{
		super();
		a = w.getAgenda();
		this.w = w;
		
		artists = new JList();
		artists.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		artists.setLayoutOrientation(JList.VERTICAL_WRAP);
		artists.setVisibleRowCount(-1);
		artists.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

			        if (artists.getSelectedIndex() == -1) {
			        } else {
			            new EditArtistPanel(w.getAgenda(), a.getArtists().get(artists.getSelectedIndex()), null);
			            artists.clearSelection();
			        }
			    }
			}
		});
		JScrollPane artistScroller = new JScrollPane(artists);
		artistScroller.setPreferredSize(new Dimension(250, 80));
		
		stages = new JList();
		stages.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		stages.setLayoutOrientation(JList.VERTICAL_WRAP);
		stages.setVisibleRowCount(-1);
		stages.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

			        if (stages.getSelectedIndex() == -1) {
			        } else {
			        	new EditStagePanel(w.getAgenda(), a.getStages().get(stages.getSelectedIndex()));
			            stages.clearSelection();
			        }
			    }
			}
		});
		JScrollPane stageScroller = new JScrollPane(stages);
		stageScroller.setPreferredSize(new Dimension(250, 80));
		
		add(artistScroller);
		add(stageScroller);
	}
	
	
	public void compileData()
	{
		int artistsList = a.getArtists().size();
		int stagesList = a.getStages().size();
		
		if(artistsList > 0)
		{
			dataArtist = new Object[artistsList];
			for(int i = 0; i < artistsList; i++)
			{
				dataArtist[i] = a.getArtists().get(i);
			}
			artists.setEnabled(true);
		}
		else
		{
			dataArtist = new Object[1];
			dataArtist[0] = new Artist("Geen Artists", "Geen Artists", "null", "Geen Artists", "Geen Artists");
			artists.setEnabled(false);
		}
		
		if(stagesList > 0)
		{
			dataStage = new Object[stagesList];
			
			for(int i = 0; i < stagesList; i++)
			{
				dataStage[i] = a.getStages().get(i);
			}
			stages.setEnabled(true);
		}
		else
		{
			dataStage = new Object[1];
			dataStage[0] = new Stage("Geen Stages", "Geen Stages");
			stages.setEnabled(false);
		}
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void update(ArrayList<Event> event) {
		a = w.getAgenda();
		compileData();
		AbstractListModel artistsList = new ContentList(dataArtist);
		AbstractListModel stagesList = new ContentList(dataStage);
		artists.setModel(artistsList);
		stages.setModel(stagesList);
	}

}
