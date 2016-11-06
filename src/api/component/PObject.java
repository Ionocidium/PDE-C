package api.component;

import java.io.Serializable;

import service.ClientService;

public class PObject extends Object implements Serializable
{
  public void sendThisObject()
  {
	ClientService clientService = ClientService.getClientService();
	
	clientService.sendObject(this);
  }
}
