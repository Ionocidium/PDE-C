package api.component;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import mainwindowcomponents.VerticalPane;

/**
 * This class handles the vertical pane found in the bottom part of PDE-C's main window.
 * 
 * @author Lorenzo Miguel Monzon
 *
 */
public class PVerticalTabbedPane extends JTabbedPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tabName;
	private Component comp;
	
	private VerticalPane pane = VerticalPane.getVerticalPane();
	
	
	  /**
	   * Gets the instance of PVerticalTabbedPane. Adds a log component in the tab.
	   * @param tabTitle this is the name of the tab to be added on the pane
	   */
	
	public PVerticalTabbedPane (String tabTitle)
	{
		this.tabName = tabTitle;
		JTextArea log = new JTextArea (1,30);
	    log.setEditable ( false );
	    JScrollPane console = new JScrollPane ( log );
		this.comp = console;
	}
	
	/**
	 * This method adds the tab component in the pane
	 */
	public void addTab()
	{
		if (this != null)
		{
		  pane.addComponent(this.tabName, this.comp);
		}	
	}
	
	/**
	 * This method removes the tab component in the pane
	 */
	public void remove()
	{
		pane.removeComponent(this);
	}
	
}
