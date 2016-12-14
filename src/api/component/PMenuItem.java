package api.component;

import javax.swing.JMenuItem;

/**
 * This class extends JMenuItem and has all access to JMenuItem’s
 *	method.
 * <p>
 *  In addition, the thesis group added function that will help other developers.
 * </p>
 * @author Alexander John D. Jose
 *
 */
public class PMenuItem extends JMenuItem
{
  private String initialText;
  
  /**
   * The constructor for PMenuItem
   * @param text the name of this PMenuItem.
   */
  public PMenuItem(String text)
  {
	initialText = text;
	this.setText(initialText);
  }
  
  /**
   * This method adds the current PMenuItem to a
   * PMenu object.
   * @param menu the PMenu object which this PMenuItem will be added to.
   */
  
  public void addToMenu(PMenu menu)
  {
	if (this != null)
	{
	  menu.add(this);
	}
  }
  
  
  /**
   * This method adds a sub menu to a
   * PMenuItem. This is made possible since
   * PDE-C allows nested submenus.
   * 
   * @param menu the sub menu that will be added to PMenuItem
   */
  public void addSubMenu(PMenu menu)
  {
	this.add(menu);
  }
}
