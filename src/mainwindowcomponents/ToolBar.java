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
  private HashMap<Integer, Component> listOfComponents;
  private JTextArea consoleLog;
  
  private ToolBar()
  {
	mainWindow = MainWindowView.getInstance();
	listOfComponents = new HashMap<Integer, Component>();
	toolBar = mainWindow.getCoreToolbar();
	consoleLog = mainWindow.getConsoleLog();
  }
  
  public static ToolBar getToolbar()
  {
	if (instance == null)
	{
	  instance = new ToolBar();
	}
	
	return instance;
  }
  
  public void addComponent(int id, Component comp)
  {
	toolBar.add(comp);
	listOfComponents.put(id, comp);
  }
  
  public void removeComponent(int id)
  {
	Component comp = listOfComponents.get(id);
	toolBar.remove(comp);
	listOfComponents.remove(id);
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
