package api.component;

import java.io.Serializable;

import service.ClientService;


/**
 * Used for sending an <code>Object</code> to PDE-C Server.
 * @author Alexander John Jose
 *
 */
public class PObject extends Object implements Serializable
{
  /**
   * 
   */
  private static final long serialVersionUID = 3L;

  
  /**
   * The method for sending this <code>Object</code> to PDE-C Server
   */
  public void sendThisObject()
  {
	ClientService clientService = ClientService.getClientService();
	
	clientService.sendObject(this);
  }
}
