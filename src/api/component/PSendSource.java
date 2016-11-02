package api.component;

import java.nio.file.Path;

import controller.EventController;
import view.MainWindowView;

public class PSendSource
{
  private MainWindowView main;
  private EventController event;
  
  public PSendSource()
  {
	main = MainWindowView.getInstance();
	event = EventController.getEventController();
  }
  
  public void sendSource(Path filePath)
  {
	event.sendSrcCode(main.getErrorLog(), filePath);
  }
}
