package api.editor.main.toolbar;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JToolBar;

import view.MainWindowView;

public class ToolBar
{
  private static ToolBar instance = null;
  private MainWindowView mainWindow;
  private JToolBar toolBar;
  private ArrayList<Component> listOfComponents;
  
  private ToolBar()
  {
	mainWindow = MainWindowView.getInstance();
	listOfComponents = new ArrayList<Component>();
	toolBar = mainWindow.getCoreToolbar();
  }
  
  public static ToolBar getToolbar()
  {
	if (instance == null)
	{
	  instance = new ToolBar();
	}
	
	return instance;
  }
  
  public void addComponentToToolbar(Component comp)
  {
	toolBar.add(comp);
	listOfComponents.add(comp);
  }
}
