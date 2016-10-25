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
  
  private ToolBar bar = ToolBar.getToolbar();
  
  public Button(String name)
  {
	bName = name;
	this.setText(bName);
  }
  
  public void addToToolbar()
  {
	
	if (this != null)
	{
	  bar.addComponent(this);
	}	
  }
  

  
  public void remove()
  {
	bar.removeComponent(this);
  }
}
