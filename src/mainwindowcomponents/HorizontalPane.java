package mainwindowcomponents;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import model.APIInterface;
import model.APITabs;
import view.MainWindowView;

public class HorizontalPane implements APITabs{

	  private static HorizontalPane instance = null;
	  private MainWindowView mainWindow;
	  private JTabbedPane horizontalPane;
	  
	  private HorizontalPane()
	  {
		mainWindow = MainWindowView.getInstance();
		horizontalPane = mainWindow.getTabbedHorizontalPane();
	  }
	  
	  public static HorizontalPane getHorizontalPane()
	  {
		if (instance == null)
		{
		  instance = new HorizontalPane();
		}
		
		return instance;
	  }
	  
	@Override
	public void addComponent(String tabName, Component comp) {
		horizontalPane.add(tabName, comp);
	}

	@Override
	public void removeComponent(Component comp) {
		horizontalPane.remove(comp);
		horizontalPane.revalidate();
		horizontalPane.repaint();
	}

	@Override
	public void setVisible(boolean isVisible) {
		horizontalPane.setVisible(isVisible);
	}

}
