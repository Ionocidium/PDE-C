package api.component;

import javax.swing.JFrame;

public class PWindow extends JFrame
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
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
