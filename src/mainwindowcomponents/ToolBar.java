package mainwindowcomponents;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JTextArea;
import javax.swing.JToolBar;

import model.APIInterface;
import view.MainWindowView;

/**
 * This class resembles the tool bar of PDE-C
 * @author Alexander John Jose
 *
 */
public class ToolBar implements APIInterface
{
  private static ToolBar instance = null;
  private MainWindowView mainWindow;
  private JToolBar toolBar;
  private JTextArea consoleLog;
  
  /**
   * The constructor of PDE-C
   */
  private ToolBar()
  {
	mainWindow = MainWindowView.getInstance();
	toolBar = mainWindow.getCoreToolbar();
	consoleLog = mainWindow.getErrorLog();
  }
  
  /**
   * 
   * @return the toolbar of PDE-C
   */
  public static ToolBar getToolbar()
  {
	if (instance == null)
	{
	  instance = new ToolBar();
	}
	
	return instance;
  }
  
  /**
   * Adds the <code>comp</code> to PDE-C
   */
  public void addComponent(Component comp)
  {
	toolBar.add(comp);
  }
  
  /**
   * Removes the component <code>comp</code> to PDE-C
   */
  public void removeComponent(Component comp)
  {
	toolBar.remove(comp);
	toolBar.revalidate();
	toolBar.repaint();
  }
  
  /**
   * Adds a separator for the components in the toolbar
   */
  public void addSeparator()
  {
	toolBar.addSeparator();
  }
  
  /**
   * Sets the console text through <code>message</code>
   */
  public void setConsoleText(String message)
  {
	if (consoleLog.getText().toString().equals(""))
	{
	  consoleLog.setText(message);
	}
	
	else
	{
	  String lastMessage = consoleLog.getText().toString();
	  consoleLog.setText(lastMessage + "\n" + message);
	}
  }
  
  /**
   * Sets the tool bar as visible or not
   */
  public void setVisible(boolean isVisible)
  {
	toolBar.setVisible(isVisible);
  }
}
