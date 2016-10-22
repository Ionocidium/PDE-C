package mainwindowcomponents;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.JMenuBar;
import javax.swing.JTextArea;

import model.APIInterface;
import view.MainWindowView;

public class MenuBar implements APIInterface
{
  private static MenuBar instance = null;
  private MainWindowView main;
  private JMenuBar menuBar;
  private HashMap<Integer, Component> listOfComponents;
  private JTextArea consoleLog;
  
  private MenuBar()
  {
	main = MainWindowView.getInstance();
	menuBar = main.getMenuBar();
	listOfComponents = new HashMap<Integer, Component>();
	consoleLog = main.getErrorLog();
  }
  
  public static MenuBar getMenuBar()
  {
	if (instance == null)
	{
	  instance = new MenuBar();
	}
	
	return instance;
  }
  
  public void addComponent(int id, Component comp)
  {
	menuBar.add(comp);
	listOfComponents.put(id, comp);
  }
  
  public void removeComponent(int id)
  {
	Component temp = listOfComponents.get(id);
	menuBar.remove(temp);
	listOfComponents.remove(id);
  }
  
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
