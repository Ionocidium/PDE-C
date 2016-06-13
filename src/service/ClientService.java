package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientService
{
  private static ClientService instance = null;
  private final static int port = 2021;
  
  private ClientService()
  {

  }
  
  public static ClientService getClientService()
  {
	if (instance == null)
	{
	  instance = new ClientService();
	  runClient();
	}
	
	return instance;
  }
  
  private static void runClient()
  {
	try
	{
	  InetAddress addr = InetAddress.getLocalHost();
	  Socket client = new Socket(addr, port);
	  OutputStream outToServer = client.getOutputStream();
	  DataOutputStream out = new DataOutputStream(outToServer);
	  
	  out.writeUTF("Hello from" + client.getLocalSocketAddress());
	  
	  InputStream inFromServer = client.getInputStream();
	  DataInputStream in = new DataInputStream(inFromServer);
	  System.out.println("Server says " + in.readUTF());
	  client.close();
	}
	
	catch (Exception ex)
	{
	  ex.printStackTrace();
	}
  }
}
