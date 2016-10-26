package mainwindowcomponents;

import java.awt.Component;

import javax.swing.JTabbedPane;

import model.APITabs;
import view.MainWindowView;

public class VerticalPane implements APITabs{

	  private static VerticalPane instance = null;
	  private MainWindowView mainWindow;
	  private JTabbedPane verticalPane;
	  
	  private VerticalPane()
	  {
		mainWindow = MainWindowView.getInstance();
		verticalPane = mainWindow.getTabbedVerticalPane();
	  }
	  
	  public static VerticalPane getVerticalPane()
	  {
		if (instance == null)
		{
		  instance = new VerticalPane();
		}
		
		return instance;
	  }
	  
	@Override
	public void addComponent(String tabName, Component comp) {
		verticalPane.add(tabName, comp);
	}

	@Override
	public void removeComponent(Component comp) {
		verticalPane.remove(comp);
		verticalPane.revalidate();
		verticalPane.repaint();
	}

	@Override
	public void setVisible(boolean isVisible) {
		verticalPane.setVisible(isVisible);
	}
}
