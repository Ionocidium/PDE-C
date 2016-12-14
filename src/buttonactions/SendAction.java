package buttonactions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;

import controller.EventController;

/**
 * 
 * @author Alexander John Jose
 *
 */
public class SendAction extends AbstractAction
{
  private EventController event = EventController.getEventController();
  private JTextArea consoleLog;
  private Path filePath;
  
  /**
   * Constructor for SendAction
   * @param name name of the action
   * @param console console area of PDE-C
   * @param filePath location of the file to be sent
   */
  public SendAction(String name, JTextArea console, Path filePath)
  {
	super(name);
	this.consoleLog = console;
	this.filePath = filePath;
  }
  
  /**
   * Another constructor for SendAction
   * @param name name of the action
   * @param mnemonic mnemonic to be used
   * @param console console area of PDE-C
   * @param filePath location of the file to be sent
   */
  public SendAction(String name, int mnemonic, JTextArea console, Path filePath)
  {
	super(name);
	putValue(MNEMONIC_KEY, mnemonic);
	this.consoleLog = console;
	this.filePath = filePath;
  }

  @Override
  /**
   * Method that handles the send action
   */
  public void actionPerformed(ActionEvent arg0)
  {
	event.sendSrcCode(consoleLog, filePath);	
  }
}
