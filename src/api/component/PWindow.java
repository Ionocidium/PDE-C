package api.component;

import javax.swing.JFrame;

/**
 * This class extends JFrame and has all access to JFrame
 *	The thesis group also added their own method to PWindow class.
 * @author Alexander John Jose
 *
 */
public class PWindow extends JFrame
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  /**
   * The constructor of PWindow
   * @param title this will be the name of the newly created window
   */
  public PWindow(String title)
  {
	this.setTitle(title);
  }
  
  public PWindow(int x, int y, int width, int height)
  {
	this.setBounds(x, y, width, height);
  }
  
  public void showWindow()
  {
	this.setLocationRelativeTo(null);
	this.setVisible(true);
  }
}
