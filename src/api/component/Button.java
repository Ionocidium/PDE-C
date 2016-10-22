package api.component;

import javax.swing.JButton;

import mainwindowcomponents.ToolBar;

public class Button extends JButton
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String bName;
  
  public Button(String name)
  {
	bName = name;
	this.setText(bName);
  }
  
  public Button()
  {
	
  }
  
  public void addToToolbar(int id)
  {
	ToolBar bar = ToolBar.getToolbar();
	bar.addComponent(id, this);
  }
}
