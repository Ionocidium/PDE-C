package mainwindowcomponents;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.APIInterface;
import model.APITabs;
import view.MainWindowView;

/**
 * Handles the components of the <code>Horizontal Pane</code> found in the bottom of PDE-C's user interface.
 * 
 * @author Lorenzo Miguel G. Monzon
 */

public class HorizontalPane implements APITabs{

	  private static HorizontalPane instance = null;
	  private MainWindowView mainWindow;
	  private JTabbedPane horizontalPane;
	  
		/**
		 * Creates a representation that contains the information of the <code>Horizontal Pane</code> of PDE-C.
		 * 
		 */
	  private HorizontalPane()
	  {
		mainWindow = MainWindowView.getInstance();
		horizontalPane = mainWindow.getTabbedHorizontalPane();
	  }
	  
	  /**
	   * Gets the <code>Horizontal Pane</code> property.
	   * @return the <code>Horizontal Pane</code>
	   */
	  
	  public static HorizontalPane getHorizontalPane()
	  {
		if (instance == null)
		{
		  instance = new HorizontalPane();
		}
		
		return instance;
	  }
	
	  /**
	   * Adds a new tab for the <code>Horizontal Pane</code> property.
	   * 
	   * @param tabName the name of the tab to add.
	   * @param comp the component to be added in the tab.
	   */
	  
	@Override
	public void addComponent(String tabName, Component comp) {
		horizontalPane.add(tabName, comp);
	}
	
	  /**
	   * Removes the tab specified in the <code>Horizontal Pane</code> property.
	   * 
	   * @param comp the component to be removed in the <code>Horizontal Pane</code>.
	   */

	@Override
	public void removeComponent(Component comp) {
		horizontalPane.remove(comp);
		horizontalPane.revalidate();
		horizontalPane.repaint();
	}
	
	  /**
	   * Sets the visibility of <code>Horizontal Pane</code> property.
	   * 
	   * @param isVisible the boolean value of the visibility of <code>Horizontal Pane</code>.
	   */

	@Override
	public void setVisible(boolean isVisible) {
		horizontalPane.setVisible(isVisible);
	}

}
