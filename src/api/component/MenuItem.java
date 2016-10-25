package api.component;

import javax.swing.JMenuItem;


public class MenuItem extends JMenuItem
{
  private String initialText;
  
  public MenuItem(String text)
  {
	initialText = text;
	this.setText(initialText);
  }
  
  public void addToMenu(Menu menu)
  {
	if (this != null)
	{
	  menu.add(this);
	}
  }
  
  public void addSubMenu(Menu menu)
  {
	this.add(menu);
  }
}
