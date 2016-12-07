package api.component;

import java.util.ArrayList;

import mainwindowcomponents.ToolBar;
/**
 * Groups the buttons before adding it to PDE-C’s
   toolbar.
 * 
 * @author Alexander John D. Jose
 *
 */
public class PButtonGroup
{
  
  private ArrayList<PButton> button;
  private ToolBar bar;
  
  /**
   * 
   */
  public PButtonGroup()
  {
	button = new ArrayList<PButton>();
	bar = ToolBar.getToolbar();
  }
  
  /**
   * Adds a button to the group.
   * @param button this is a PButton that will be added to the PButtonGroup.
   */
  public void addButton(PButton button)
  {
	this.button.add(button);
  }
  
  /**
   * This method is used after adding button
 	 objects to the ButtonGroup. It builds the
	 group and add it to PDE-C’s toolbar.
   */
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
