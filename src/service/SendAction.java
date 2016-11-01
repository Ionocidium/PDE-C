package service;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;

import controller.EventController;

public class SendAction extends AbstractAction
{
  private EventController event = EventController.getEventController();
  private JTextArea consoleLog;
  private Path filePath;
  
  public SendAction(String name, JTextArea console, Path filePath)
  {
	super(name);
	this.consoleLog = console;
	this.filePath = filePath;
  }
  
  public SendAction(String name, int mnemonic, JTextArea console, Path filePath)
  {
	super(name);
	putValue(MNEMONIC_KEY, mnemonic);
	this.consoleLog = console;
	this.filePath = filePath;
  }

  @Override
  public void actionPerformed(ActionEvent arg0)
  {
	event.sendSrcCode(consoleLog, filePath);	
  }
}
