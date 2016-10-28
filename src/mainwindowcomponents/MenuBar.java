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
  private JTextArea consoleLog;
  
  private MenuBar()
  {
	main = MainWindowView.getInstance();
	menuBar = main.getMenuBar();
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
  
  public void addComponent(Component comp)
  {
	menuBar.add(comp);
  }
  
  public void removeComponent(Component temp)
  {
	menuBar.remove(temp);
	menuBar.revalidate();
	menuBar.repaint();
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
