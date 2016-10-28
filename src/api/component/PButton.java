package api.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import mainwindowcomponents.ToolBar;

public class PButton extends JButton implements ActionListener
{
  // extendable
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String bName;
  
  private ToolBar bar = ToolBar.getToolbar();
  
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

  @Override
  public void actionPerformed(ActionEvent arg0)
  {
	// TODO Auto-generated method stub
	
  }
}
