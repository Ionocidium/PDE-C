package mainwindowcomponents;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;

import model.APIInterface;
import view.MainWindowView;

/**
 * This class represents the Menu bar of PDE-C
 * @author Alexander John Jose
 *
 */
public class MenuBar implements APIInterface
{
  private static MenuBar instance = null;
  private MainWindowView main;
  private JMenuBar menuBar;
  private JTextArea consoleLog;
  
  /**
   * Constructor for MenuBar
   */
  private MenuBar()
  {
	main = MainWindowView.getInstance();
	menuBar = main.getMenuBar();
	consoleLog = main.getErrorLog();
  }
  
  /**
   * This method retrieves the menu bar of PDE-C
   * @return the menu bar of PDE-C
   */
  public static MenuBar getMenuBar()
  {
	if (instance == null)
	{
	  instance = new MenuBar();
	}
	
	return instance;
  }
  
  /**
   * Adds a <code>Component</code> to PDE-C
   */
  public void addComponent(Component comp)
  {
	menuBar.add(comp);
  }
  
  /**
   * Removes the component <code>temp</code> from PDE-C's menu bar
   */
  public void removeComponent(Component temp)
  {
	menuBar.remove(temp);
	menuBar.revalidate();
	menuBar.repaint();
  }
  
  /**
   * Sets the console text
   */
  public void setConsoleText(String message)
  {
	if (consoleLog.getText().equals(""))
	{
	  consoleLog.setText(message);
	}
	
	else
	{
	  String lastMessage = consoleLog.getText().toString();
	  consoleLog.setText(lastMessage + "\n" + message);
	}
  }
  
  public void setVisible(boolean isVisible)
  {
	menuBar.setVisible(isVisible);
  }
}
