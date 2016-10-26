package api.component;

import java.awt.Component;

import javax.swing.JMenu;

import mainwindowcomponents.MenuBar;

public class PMenu extends JMenu
{
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private MenuBar bar = MenuBar.getMenuBar();

  public PMenu(String name)
  {
	this.setText(name);
  }
  
  public PMenu()
  {
	
  }
  
  public void addToMenuBar()
  {
	if (this != null)
	{
	  bar.addComponent(this);
	}
  }
  
  public void addMenuItem(PMenuItem menuItem)
  {
	this.add(menuItem);
  }
  
  public void addSubMenuItem(PMenu menu)
  {
	this.add(menu);
  }
  
  public void remove()
  {
	bar.removeComponent(this);
  }
  
  public void removeSubMenu(Component comp)
  {
	this.remove(comp);
  }
}
