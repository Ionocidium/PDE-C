package api.component;

import javax.swing.JFrame;

public class Window extends JFrame
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  public Window(String title)
  {
	this.setTitle(title);
  }
  
  public Window(int x, int y, int width, int height)
  {
	this.setBounds(x, y, width, height);
  }
  
  public void showWindow()
  {
	this.setLocationRelativeTo(null);
	this.setVisible(true);
  }
}
