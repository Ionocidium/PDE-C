package service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import api.component.PObject;
import clientservices.DownloadHandler;

public class ClientService
{
  private static ClientService instance = null;
  private final static int port = 2021;
  private Socket clientSocket = null;
  private DataOutputStream toServer;
  private BufferedReader reader;
  private String currIpAddr;
  private InetAddress addr;
  
  private ClientService()
  {
	try
	{
	  currIpAddr = "0.0.0.0";
	  addr = InetAddress.getByName(currIpAddr);
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
  
  public void setIPAddress(String Ip)
  {
	
	try
	{
	  this.currIpAddr = Ip;
	  addr = InetAddress.getByName(this.currIpAddr);
	}
	
	catch(Exception ex)
	{
	  ex.printStackTrace();
	}
	
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
  
  public void sendObject(Object obj)
  {
	try
	{
	  Socket objectSender = new Socket(addr, 2022);
	  ObjectOutputStream oos = new ObjectOutputStream(objectSender.getOutputStream());
	  Object ob = (java.lang.Object) obj;
	  oos.writeObject(ob);
	  oos.close();
	  System.out.println(obj.hashCode());
	  objectSender.close();
	} 
	
	catch (IOException e)
	{
	  // TODO Auto-generated catch block
	  e.printStackTrace();
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
  
  public void getActivityFile(int idNum) throws IOException
  {
	if (clientSocket == null)
	{
	  initSocket();
	}
	
/*	int properIdNum = idNum + 1;
	toServer.writeBytes("get,ActivityFiles," + (properIdNum) + "\n");
	BufferedReader downloadedFile = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	String message = downloadedFile.readLine();
	FileDecoder decode = new FileDecoder();
	decode.convertToFile(message, "activity.pdf");
	downloadedFile.close();
	clientSocket = null;*/
	
	Thread download = new Thread(new DownloadHandler(idNum));
	download.start();
  }

  public String getCurrIpAddr()
  {
    return currIpAddr;
  }
  
  public Socket getClientSocket()
  {
	return clientSocket;
  }
  
  public DataOutputStream getClientOutputStream()
  {
	return toServer;
  }
  
  public void setClientSocketNull()
  {
	clientSocket = null;
  }
}
