package mainwindowcomponents;

import java.awt.Component;

import javax.swing.JTabbedPane;

import model.APITabs;
import view.MainWindowView;

/**
 * Handles the components of the <code>VerticalPane</code> found in the right of PDE-C's user interface.
 * 
 * @author Lorenzo Miguel G. Monzon
 */

public class VerticalPane implements APITabs{

	  private static VerticalPane instance = null;
	  private MainWindowView mainWindow;
	  private JTabbedPane verticalPane;
	  
		/**
		 * Creates a representation that contains the information of the <code>VerticalPane</code> of PDE-C.
		 * 
		 */
	  
	  private VerticalPane()
	  {
		mainWindow = MainWindowView.getInstance();
		verticalPane = mainWindow.getTabbedVerticalPane();
	  }
	  
	  /**
	   * Gets the <code>VerticalPane</code> property.
	   * @return the <code>VerticalPane</code>
	   */
	  
	  public static VerticalPane getVerticalPane()
	  {
		if (instance == null)
		{
		  instance = new VerticalPane();
		}
		
		return instance;
	  }
	  
	  /**
	   * Adds a new tab for the <code>VerticalPane</code> property.
	   * 
	   * @param tabName the name of the tab to add.
	   * @param comp the component to be added in the tab.
	   */
	  
	@Override
	public void addComponent(String tabName, Component comp) {
		verticalPane.add(tabName, comp);
	}
	
	  /**
	   * Removes the tab specified in the <code>VerticalPane</code> property.
	   * 
	   * @param comp the component to be removed in the <code>VerticalPane</code>.
	   */

	@Override
	public void removeComponent(Component comp) {
		verticalPane.remove(comp);
		verticalPane.revalidate();
		verticalPane.repaint();
	}
	
	  /**
	   * Sets the visibility of <code>VerticalPane</code> property.
	   * 
	   * @param isVisible the boolean value of the visibility of <code>VerticalPane</code>.
	   */

	@Override
	public void setVisible(boolean isVisible) {
		verticalPane.setVisible(isVisible);
	}
}
