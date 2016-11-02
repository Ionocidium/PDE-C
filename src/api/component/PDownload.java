package api.component;

import controller.EventController;

public class PDownload
{
  private EventController event;
  
  public PDownload()
  {
	event = EventController.getEventController();
  }
  
  public void downloadActivity()
  {
	event.downloadActivity();
  }
}
