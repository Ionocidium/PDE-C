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
/**
 * This is class is responsible for sending and receiving information from PDE-C Server
 * 
 * @author Alexander John Jose
 *
 */
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
  
  /**
   * This method returns the single instantiated ClientService
   * 
   * @return a single instantiated ClientService
   */
  public static ClientService getClientService()
  {
	if (instance == null)
	{
	  instance = new ClientService();
	}
	
	return instance;
  }
  
  /**
   * Sets the IP Address of where PDE-C Server is
   * @param Ip the IP Address of PDE-C Server
   */
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
  
  /**
   * 
   * This method initializes the socket that PDE-C use.
   */
  
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
  
  /**
   * Facilitates the sending of <code>PObject</code> from PDE-C to PDE-C Server
   * @param obj the Object that will be sent.
   */
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
  
  /**
   * Sends an actual data to PDE-C Server
   * @param data the information, in <code>String</code>, to be sent to PDE-C Server
   * @throws IOException
   */
  public void sendDataToServer(String data) throws IOException
  {
	
	if (clientSocket == null)
	{
	  initSocket();
	}
	 
	 toServer.writeBytes(data);
	 toServer.close();
  }
  
  
  /**
   * Retrieves the activity list from PDE-C Server
   * @throws IOException
   */
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
  
  /**
   * Retrieves a particular activity file from PDE-C Server
   * @param idNum ID Number, from the database, of the activity file to be downloaded
   * @throws IOException
   */
  public void getActivityFile(int idNum) throws IOException
  {
	if (clientSocket == null)
	{
	  initSocket();
	}
	
	Thread download = new Thread(new DownloadHandler(idNum));
	download.start();
  }

  /**
   * Retrieves the current IP Address in use for connecting to PDE-C Server
   * @return the IP Address in <code>String</code> of PDE-C Server
   */
  public String getCurrIpAddr()
  {
    return currIpAddr;
  }
  
  /**
   * Retrieves the socket in use by the client
   * @return a <code>Socket</code> object in use by the client
   */
  public Socket getClientSocket()
  {
	return clientSocket;
  }
  
  /**
   * Retrieves the output stream of the client
   * @return the output stream of the client
   */
  public DataOutputStream getClientOutputStream()
  {
	return toServer;
  }
  
  /**
   * Sets the <code>Socket</code> of PDE-C client as null
   */
  public void setClientSocketNull()
  {
	clientSocket = null;
  }
}
