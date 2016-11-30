package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessOutputController implements Runnable
{
  private InputStream inputStream;
  
  public ProcessOutputController(InputStream is)
  {
	inputStream = is;
  }

  @Override
  public void run()
  {
	// TODO Auto-generated method stub
	try
	{
	  InputStreamReader isr = new InputStreamReader(inputStream);
	  BufferedReader reader = new BufferedReader(isr);
	  
	  File file = new File("resources/output.txt");
	  FileWriter fw;
	  fw = new FileWriter(file);
	  BufferedWriter out = new BufferedWriter(fw);
	  
	  String line = null;
	  
	  while ((line = reader.readLine()) != null)
	  {
		out.write(line);
	  }
	  
	  out.flush();
	  out.close();
	}
	
	catch(IOException ioe)
	{
	  ioe.printStackTrace();
	}
  }

}
