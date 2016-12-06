package configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Handles the path for compiling and debugging configuration.
 *
 * @author Alexander John D. Jose
 *
 */

public class LocalConfiguration
{
  private String gccPath;
  private String gdbPath;
  private static LocalConfiguration instance = null;
  
  
  // Edit your configuration files if you need to
  // Please do not push your own config file to Git
  
  /**
	* Creates an instance of LocalConfiguration.
	* <p>
	* Checks if <code>resources/settings.txt</code> exists from where PDE-C.jar is located. Otherwise, it will be based from the current instance, which requires setting up from the Settings Menu.
	* 
	* Otherwise, this will recreate a new settings file based from its initialization.
	* </p>
	*/
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
  

  /**
	* Gets the instance of <code>LocalConfiguration</code>. If this is not yet invoked, it will create an instance of <code>LocalConfiguration</code>.
	* @return The instance of <code>LocalConfiguration</code>.
	*/
  public static LocalConfiguration getInstance()
  {
	if (instance == null)
	{
	  instance = new LocalConfiguration();
	}
	
	return instance;
  }
  
  /**
	* Gets the executable file that allows you to compile C code to its executable equivalent from the <code>LocalConfiguration</code>.
	* @return The absolute path of C Compiler.
	*/
  public String getGccPath()
  {
	return gccPath;
  }

  /**
	* Sets the executable file that allows you to compile C code to its executable equivalent from the <code>LocalConfiguration</code>.
	* @param gcc The absolute path of C Compiler to set.
	*/
  public void setGccPath(String gcc)
  {
	this.gccPath = gcc;
  }

  /**
	* Gets the executable file that allows you to debug C code to its executable equivalent from the <code>LocalConfiguration</code>.
	* @return The absolute path of C Debugger.
	*/
  public String getGdbPath()
  {
	return gdbPath;
  }

  /**
	* Gets the executable file that allows you to debug C code to its executable equivalent from the <code>LocalConfiguration</code>.
	* @param gdb The absolute path of C Debugger to set.
	*/
  public void setGdbPath(String gdb)
  {
	this.gdbPath = gdb;
  }

  /**
	* Reads the current settings from the <code>resources/settings.txt</code> where PDE-C.jar is located.
	* <p>
	* This is assumed that <code>settings.txt</code> is present in the <code>resources</code> folder where PDE-C.jar is located.
	* </p> 
	*/
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
