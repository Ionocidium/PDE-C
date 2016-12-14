package api.component;

import javax.swing.JFrame;

/**
 * This class extends JFrame and has all access to JFrame.
 * <p>
 *  The thesis group also added their own method to PWindow class.
 * </p>
 * @author Alexander John D. Jose
 *
 */
public class PWindow extends JFrame
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  /**
   * The constructor of P<code>PWindow</code> with the given title.
   * @param title the name of the newly created window
   */
  public PWindow(String title)
  {
	this.setTitle(title);
  }
  
  
  /**
   * The constructor of <code>PWindow</code> with the given dimensions and its coordinates.
   * @param x the x-axis of <code>PWindow</code>
   * @param y the y-axis of <code>PWindow</code>
   * @param width the width of <code>PWindow</code>
   * @param height the height of <code>PWindow</code>
   */
  public PWindow(int x, int y, int width, int height)
  {
	this.setBounds(x, y, width, height);
  }
  
  /**
   * Shows the <code>PWindow</code>.
   */
  public void showWindow()
  {
	this.setLocationRelativeTo(null);
	this.setVisible(true);
  }
}
