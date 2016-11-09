package configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LocalConfiguration
{
  private String gccPath;
  private String gdbPath;
  private static LocalConfiguration instance = null;
  
  
  // Edit your configuration files if you need to
  // Please do not push your own config file to Git
  
  private LocalConfiguration()
  {
	if (Files.exists(Paths.get("resources/settings.txt")))
	{
	  this.readFile();
	}
	
	else
	{
	  if(System.getProperty("os.arch").equals("amd64"))
    	{
            gccPath = "C:\\cygwin64\\bin\\gcc.exe";
            gdbPath = "C:\\cygwin64\\bin\\gdb.exe";
    	}
    	
    	else if(System.getProperty("os.arch").equals("x86"))
    	{
            gccPath = "C:\\cygwin64\\bin\\gcc.exe";
            gdbPath = "C:\\cygwin64\\bin\\gdb.exe";
    	}
	}
    	
  }
  
  public static LocalConfiguration getInstance()
  {
	if (instance == null)
	{
	  instance = new LocalConfiguration();
	}
	
	return instance;
  }
  
  public String getGccPath()
  {
	return gccPath;
  }
  
  public void setGccPath(String gcc)
  {
	this.gccPath = gcc;
  }
  
  public String getGdbPath()
  {
	return gdbPath;
  }
  
  public void setGdbPath(String gcc)
  {
	this.gdbPath = gcc;
  }
  
  private void readFile()
  {
	Charset charset = Charset.forName("UTF-8");
	String line = null;
		  
	try (BufferedReader reader = Files.newBufferedReader(Paths.get("resources/settings.txt"), charset))
	{	
	  int i = 0;
	  
	  while ((line = reader.readLine()) != null)
      {
		if (i == 0)
		{
		  gccPath = line;
		  System.out.println("Gcc path retrieved from previous settings");
		}
		
		else
		{
		  gdbPath = line;
		  System.out.println("Gdb path retrieved from previous settings");
		}
		
		i++;
	  }
	}
		  
	catch (IOException ex)
	{
	  ex.printStackTrace();
	}
  }
  
}
