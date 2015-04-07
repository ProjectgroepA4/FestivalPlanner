package Agenda;
import javax.swing.AbstractListModel;


public class ContentList extends AbstractListModel {

	private static final long serialVersionUID = 9110016552017561529L;
	
	Object[] data;
	
	public ContentList(Object[] data)
	{
		this.data = data;
	}
	
	@Override
	public Object getElementAt(int arg0) {
		return data[arg0];
	}

	@Override
	public int getSize() {
		return data.length;
	}

}
