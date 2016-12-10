package api.component;

import controller.EventController;

/**
 * This class is used for downloading activities from PDE-C Server.
 * @author Alexander John Jose
 *
 */
public class PDownload
{
  private EventController event;
  
  public PDownload()
  {
	event = EventController.getEventController();
  }
  
  /**
   * This is used for downloading activities from PDE-C Server
   */
  public void downloadActivity()
  {
	event.downloadActivity();
  }
}
