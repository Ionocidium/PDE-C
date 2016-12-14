package api.component;

import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import mainwindowcomponents.HorizontalPane;

/**
 * This class handles the horizontal pane found in the bottom part of PDE-C's main window.
 * 
 * @author Lorenzo Miguel Monzon
 *
 */
public class PHorizontalTabbedPane extends JTabbedPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tabName;
	private Component comp;
	
	private HorizontalPane pane = HorizontalPane.getHorizontalPane();
	
	  /**
	   * Gets the instance of PHorizontalTabbedPane. Adds a log component in the tab.
	   * @param tabTitle this is the name of the tab to be added on the pane
	   */
	public PHorizontalTabbedPane (String tabTitle)
	{
		this.tabName = tabTitle;
		JTextArea log = new JTextArea (5,20);
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
	 * This methods removes the tab component in the pane
	 */
	
	public void remove()
	{
		pane.removeComponent(this);
	}
	
}
