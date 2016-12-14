package buttonactions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import controller.EventController;

/**
 * Class for download action of PDE-C API
 * @author Alexander John D. Jose
 *
 */
public class DownloadAction extends AbstractAction
{
  private EventController event = EventController.getEventController();
  
  /**
   * The constructor of <code>DownloadAction</code>
   * @param name this would be the name of the action
   */
  public DownloadAction(String name)
  {
	super(name);
  }
  
  /**
   * Another possible constructor of <code>DownloadAction</code>
   * @param name name of the action
   * @param mnemonic mnemonic to be used
   */
  public DownloadAction(String name, int mnemonic)
  {
	super(name);
	putValue(MNEMONIC_KEY, mnemonic);
  }

  /**
   * The action event of downloading activity
   */
  @Override
  public void actionPerformed(ActionEvent arg0)
  {
	event.downloadActivity();	
  }
}
