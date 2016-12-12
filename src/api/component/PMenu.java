package api.component;

import java.awt.Component;

import javax.swing.JMenu;

import mainwindowcomponents.MenuBar;
/**
 * This class extends JMenu and has all access to JMenu’s methods.
 * <p>
 * The thesis group also added some of their own methods that will help other developer implement the PMenu class
 * </p>
 * @author Alexander John Jose
 *
 */
public class PMenu extends JMenu
{
  private static final long serialVersionUID = 1L;
  private MenuBar bar = MenuBar.getMenuBar();

  /**
   * The constructor of PMenu
   * @param name the name of this PMenu. This will reflect when added to menu bar
   */
  public PMenu(String name)
  {
	this.setText(name);
  }
  
  /**
   * Another constructor of PMenu without parameters.
   */
  public PMenu()
  {
	
  }
  
  /**
   * This method adds the current PMenu to PDE-C’s menu bar.
   */
  public void addToMenuBar()
  {
	if (this != null)
	{
	  bar.addComponent(this);
	}
  }
  
  /**
   * This method adds a PMenuItem menuItem to the PMenu.
   * @param menuItem will be added to the PMenu
   */
  public void addMenuItem(PMenuItem menuItem)
  {
	this.add(menuItem);
  }
  
  /**
   * This method adds a sub menu to the currentPMenu class.
   * <p>
   *  It accepts another PMenuclass.  
   *  The newly added PMenu class will be the sub menu.
   * </p>
   * @param menu the sub menu that should be added to this PMenu
   */
  public void addSubMenuItem(PMenu menu)
  {
	this.add(menu);
  }
  
  /**
   * This method removes the PMenu.
   */
  public void remove()
  {
	bar.removeComponent(this);
  }
  
  /**
   * This method removes a sub menu from the PMenu class
   * @param comp the sub-menu to be removed
   */
  public void removeSubMenu(Component comp)
  {
	this.remove(comp);
  }
}
