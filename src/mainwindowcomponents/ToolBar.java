package mainwindowcomponents;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JTextArea;
import javax.swing.JToolBar;

import model.APIInterface;
import view.MainWindowView;

public class ToolBar implements APIInterface
{
  private static ToolBar instance = null;
  private MainWindowView mainWindow;
  private JToolBar toolBar;
  private JTextArea consoleLog;
  
  private ToolBar()
  {
	mainWindow = MainWindowView.getInstance();
	toolBar = mainWindow.getCoreToolbar();
	consoleLog = mainWindow.getErrorLog();
  }
  
  public static ToolBar getToolbar()
  {
	if (instance == null)
	{
	  instance = new ToolBar();
	}
	
	return instance;
  }
  
  public void addComponent(Component comp)
  {
	toolBar.add(comp);
  }
  
  public void removeComponent(Component comp)
  {
	toolBar.remove(comp);
	toolBar.revalidate();
	toolBar.repaint();
  }
  
  public void addSeparator()
  {
	toolBar.addSeparator();
  }
  
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
  
  public void setVisible(boolean isVisible)
  {
	toolBar.setVisible(isVisible);
  }
}
