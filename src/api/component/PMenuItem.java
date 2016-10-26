package api.component;

import javax.swing.JMenuItem;


public class PMenuItem extends JMenuItem
{
  private String initialText;
  
  public PMenuItem(String text)
  {
	initialText = text;
	this.setText(initialText);
  }
  
  public void addToMenu(PMenu menu)
  {
	if (this != null)
	{
	  menu.add(this);
	}
  }
  
  public void addSubMenu(PMenu menu)
  {
	this.add(menu);
  }
}
