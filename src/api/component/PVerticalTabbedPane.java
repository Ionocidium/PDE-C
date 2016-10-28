package api.component;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import mainwindowcomponents.VerticalPane;

public class PVerticalTabbedPane extends JTabbedPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tabName;
	private Component comp;
	
	private VerticalPane pane = VerticalPane.getVerticalPane();
	
	public PVerticalTabbedPane (String tabTitle)
	{
		this.tabName = tabTitle;
		JTextArea log = new JTextArea (1,30);
	    log.setEditable ( false );
	    JScrollPane console = new JScrollPane ( log );
		this.comp = console;
	}
	
	public void addTab()
	{
		if (this != null)
		{
		  pane.addComponent(this.tabName, this.comp);
		}	
	}
	
	public void remove()
	{
		pane.removeComponent(this);
	}
	
}
