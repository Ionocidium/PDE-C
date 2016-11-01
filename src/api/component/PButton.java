package api.component;

import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.JButton;

import mainwindowcomponents.ToolBar;
import service.DownloadAction;
import service.SendAction;
import view.MainWindowView;

public class PButton extends JButton
{
  public static final int DOWNLOAD_ACTION = 0;
  public static final int SEND_ACTION = 1;
  
  private static final long serialVersionUID = 1L;
  private String bName;
  private ToolBar bar = ToolBar.getToolbar();
  private MainWindowView main = MainWindowView.getInstance();
  
  public PButton(String name)
  {
	bName = name;
	this.setText(bName);
  }
  
  public void addToToolbar()
  {
	if (this != null)
	{
	  bar.addComponent(this);
	}	
  }
  
  public void remove()
  {
	bar.removeComponent(this);
  }
  
  public void addAction(int action, Path filePath)
  {
	switch (action)
	{
	  case 0: this.addActionListener(new DownloadAction("Download"));
	  case 1: this.addActionListener(new SendAction("Send", main.getErrorLog(), filePath));
	  default: System.out.println("Invalid");
	}
  }

}
