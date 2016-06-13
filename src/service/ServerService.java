package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService extends Thread
{
  private ServerSocket serverSocket;
  
  public ServerService(int port) throws IOException
  {
	serverSocket = new ServerSocket(port);
  }
  
  public void run()
  {
	while(true)
	{
	  try
	  {
		System.out.println("Waiting for client ");
		Socket server = serverSocket.accept();
		System.out.println("Just connected to " + server.getRemoteSocketAddress());
		
		DataInputStream in = new DataInputStream(server.getInputStream());
		System.out.println(in.readUTF());
		
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
		out.writeUTF("You're connected to " + server.getLocalAddress());
		server.close();
	  }
	  
	  catch(Exception ex)
	  {
		ex.printStackTrace();
	  }
	}
  }
  
  public static void main(String[] args)
  {
	int port = 2021;
	
	try
	{
	  Thread t = new ServerService(port);
	  t.start();
	}
	
	catch(IOException ex)
	{
	  ex.printStackTrace();
	}
  }
}
