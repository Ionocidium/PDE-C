package api.component;

import java.util.ArrayList;

import mainwindowcomponents.ToolBar;

public class PButtonGroup
{
  
  private ArrayList<PButton> button;
  private ToolBar bar;
  
  public PButtonGroup()
  {
	button = new ArrayList<PButton>();
	bar = ToolBar.getToolbar();
  }
  
  public void addButton(PButton button)
  {
	this.button.add(button);
  }
  
  public void build()
  {
	bar.addSeparator();
	
	for (int i = 0; i < this.button.size(); i++)
	{
	  PButton temp = this.button.get(i);
	  bar.addComponent(this.button.get(i));
	}
	
	bar.addSeparator();
  }
}
