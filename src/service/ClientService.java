package service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class ClientService
{
  private static ClientService instance = null;
  private final static int port = 2021;
  private Socket clientSocket = null;
  private DataOutputStream toServer;
  private BufferedReader reader;
  private InetAddress addr;
  
  private ClientService()
  {
	try
	{
	  addr = InetAddress.getByName("127.0.0.1");
	}
	
	catch(Exception ex)
	{
	  ex.printStackTrace();
	}
	
  }
  
  public static ClientService getClientService()
  {
	if (instance == null)
	{
	  instance = new ClientService();
	}
	
	return instance;
  }
  
  public void initSocket()
  {
	try
	{
	  clientSocket = new Socket(addr, port);
	  toServer = new DataOutputStream(clientSocket.getOutputStream());
	  reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//	  DataInputStream test = new DataInputStream(clientSocket.getInputStream());
//	  System.out.println("Server says: " + test.readUTF());
	  //reader = new DataInputStream(clientSocket.getInputStream());
	  //fromServer = new DataInputStream(clientSocket.getInputStream());
	  System.out.println("From client: Connection successful." + '\n');
	}
	
	catch(Exception ex)
	{
	  ex.printStackTrace();
	}
	
  }
  
  public void sendDataToServer(String data) throws IOException
  {
	
	if (clientSocket == null)
	{
	  initSocket();
	}
	 
	 toServer.writeBytes(data);
	 toServer.close();
  }
  
  public void getActivity() throws IOException
  {
	if (clientSocket == null)
	{
	  initSocket();
	}
	
	toServer.writeBytes("get,Activity\n");
	
	DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
	String message = dis.readUTF();
	
	FileDecoder decode = new FileDecoder();
	decode.convertToFile(message, "activity.txt");
	
	dis.close();
	clientSocket = null;
  }
  
  public void getActivityFiles() throws IOException
  {
	if (clientSocket == null)
	{
	  initSocket();
	}
	
	toServer.writeBytes("get,ActivityFiles");
  }
}
