package service;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import controller.EventController;

public class DownloadAction extends AbstractAction
{
  private EventController event = EventController.getEventController();
  
  public DownloadAction(String name)
  {
	super(name);
  }
  
  public DownloadAction(String name, int mnemonic)
  {
	super(name);
	putValue(MNEMONIC_KEY, mnemonic);
  }

  @Override
  public void actionPerformed(ActionEvent arg0)
  {
	event.downloadActivity();	
  }
}
