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
  
  public void addToMenuBar()
  {
	MenuBar bar = MenuBar.getMenuBar();
	
	if (this != null)
	{
	  bar.addComponent(this);
	}
  }
  
  public void addMenuItem(MenuItem menuItem)
  {
	this.add(menuItem);
  }
  
  public void addSubMenuItem(Menu menu)
  {
	this.add(menu);
  }
}
