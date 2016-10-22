package api.component;

import javax.swing.JMenu;

import mainwindowcomponents.MenuBar;

public class Menu extends JMenu
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public Menu(String name)
  {
	this.setText(name);
  }
  
  public Menu()
  {
	
  }
  
  public void addToMenuBar(int id)
  {
	MenuBar bar = MenuBar.getMenuBar();
	
	if (this != null)
	{
	  bar.addComponent(id, this);
	}
  }
  
  public void addMenuItem(MenuItem menuItem)
  {
	this.add(menuItem);
  }
}
