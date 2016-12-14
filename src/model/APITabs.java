package model;

import java.awt.Component;

/**
 * This is an interface followed by the API tab components of PDE-C panes
 * 
 * @author Lorenzo Miguel G. Monzon
 *
 */
public interface APITabs {
	/**
	 * This method is for the addition of components
	 * @param tabName the title of the tab
	 * @param comp the component to be added
	 */
	  public void addComponent(String tabName, Component comp);
	  /**
	   * This method is for the removal of components
	   * @param comp
	   */
	  public void removeComponent(Component comp);
	  /**
	   * This method is for the visibility of the component
	   * @param isVisible the boolean value of the component's visibility
	   */
	  public void setVisible(boolean isVisible);
}
