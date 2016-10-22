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
  private int id;
  
  public Button(String name, int id)
  {
	bName = name;
	this.setText(bName);
	this.id = id;
  }
  
  public void addToToolbar()
  {
	ToolBar bar = ToolBar.getToolbar();
	
	if (this != null)
	{
	  bar.addComponent(this.id, this);
	}	
  }
  
  public int getId()
  {
	return this.id;
  }
}
