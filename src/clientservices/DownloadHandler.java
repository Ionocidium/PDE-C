package clientservices;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JOptionPane;

import service.ClientService;
import service.FileDecoder;

public class DownloadHandler implements Runnable
{
  
  private int idNum;
  private ClientService client;
  private Socket clientSocket;
  private DataOutputStream toServer;
  
  public DownloadHandler(int code)
  {
	idNum = code;
	client = ClientService.getClientService();
	clientSocket = client.getClientSocket();
	toServer = client.getClientOutputStream();
  }

  @Override
  public void run()
  {
	// TODO Auto-generated method stub
	
	if (client.getClientSocket() == null)
	{
	  client.initSocket();
	}
	
	try
	{
	  int properIdNum = idNum + 1;
	  toServer.writeBytes("get,ActivityFiles," + (properIdNum) + "\n");
	  BufferedReader downloadedFile = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	  String message = downloadedFile.readLine();
	  FileDecoder decode = new FileDecoder();
	  decode.convertToFile(message, "activity.pdf");
	  downloadedFile.close();
	  client.setClientSocketNull();
	  
	  if (Desktop.isDesktopSupported()) {
	    try {
	        File myFile = new File("resources/activity.pdf");
	        Desktop.getDesktop().open(myFile);

	    } catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Nothing to open here.", "", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	}
	
	catch (Exception ex)
	{
	  ex.printStackTrace();
	  JOptionPane.showMessageDialog(null, "Connection to server failed.", "Server error", JOptionPane.ERROR_MESSAGE);
	}
	
  }

}
