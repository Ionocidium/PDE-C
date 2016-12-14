package model;

import java.awt.Component;

/**
 * This is an interface followed by the API components of PDE-C
 * 
 * @author Alexander John D. Jose
 * @author Lorenzo Miguel G. Monzon
 *
 */

public interface APIInterface
{
	/**
	 * This method is for the addition of components
	 * @param comp the component to be added
	 */
  public void addComponent(Component comp);
  /**
   * This method is for the removal of components
   * @param comp the component to remove
   */
  public void removeComponent(Component comp);
  /**
   * This method is for the input of messages in the console log
   * @param message the message to be added
   */
  public void setConsoleText(String message);
  /**
   * This method is for the visibility of the component
   * @param isVisible the boolean value of the component's visibility
   */
  public void setVisible(boolean isVisible);
}
