package service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import view.MainWindowView;

public class ClientService
{
  private static ClientService instance = null;
  private final static int port = 2021;
  private Socket clientSocket = null;
  private DataOutputStream toServer;
  private DataInputStream fromServer;
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
	  //fromServer = new DataInputStream(clientSocket.getInputStream());
	  System.out.println("From client: Connection successful." + '\n');
	  MainWindowView.connected = true;
	}
	
	catch(Exception ex)
	{
		MainWindowView.connected = false;
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
  }
  
  public void listenServer(){
	  Thread serverListener = new Thread(new Runnable() 
	  {
			@Override
			public void run() 
			{
				if(MainWindowView.connected)
				{
					
				}
				else
				{
					initSocket();
				}
			}
	  });
	  serverListener.run();
  }
  
  public void requestActivityFromServer() throws IOException
  {
	if (clientSocket == null)
	{
	  initSocket();
	}
	
	toServer.writeBytes("get,Activity,");
  }
  
  public void getActivity() throws IOException
  {
	if (clientSocket == null)
	{
	  initSocket();
	}
	
	BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
	FileDecoder decode = new FileDecoder();
	decode.convertToFile(reader.readLine(), "activity.txt");
	clientSocket.close();
  }
}
