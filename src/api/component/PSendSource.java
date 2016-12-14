package api.component;

import java.nio.file.Path;

import controller.EventController;
import view.MainWindowView;

/**
 * This class is used for the sending of source code functionality of PDE-C
 * @author Alexander John D. Jose
 *
 */
public class PSendSource
{
  private MainWindowView main;
  private EventController event;
  
  /**
   * Constructor of PSendSource
   */
  public PSendSource()
  {
	main = MainWindowView.getInstance();
	event = EventController.getEventController();
  }
  
  /**
   * This method is used for sending the source code to PDE-C Server
   * @param filePath the file path of source code other developers wish to send
   */
  public void sendSource(Path filePath)
  {
	event.sendSrcCode(main.getErrorLog(), filePath);
  }
}
