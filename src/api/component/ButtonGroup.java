package api.component;

import java.util.ArrayList;

import mainwindowcomponents.ToolBar;

public class ButtonGroup
{
  
  private ArrayList<Button> button;
  private ToolBar bar;
  
  public ButtonGroup()
  {
	button = new ArrayList<Button>();
	bar = ToolBar.getToolbar();
  }
  
  public void addButton(Button button)
  {
	this.button.add(button);
  }
  
  public void build()
  {
	bar.addSeparator();
	
	for (int i = 0; i < this.button.size(); i++)
	{
	  Button temp = this.button.get(i);
	  bar.addComponent(this.button.get(i));
	}
	
	bar.addSeparator();
  }
}
