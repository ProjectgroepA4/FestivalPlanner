package Listeners;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import Applicatie.Panel;

public class WindowFocusListener implements FocusListener
{
	Panel p;
	
	public WindowFocusListener(Panel p)
	{
		this.p = p;
	}

	@Override
	public void focusGained(FocusEvent e){}

	@Override
	public void focusLost(FocusEvent e)
	{
		p.requestFocus();
	}
}
