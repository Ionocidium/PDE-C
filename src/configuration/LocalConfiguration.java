package configuration;

public class LocalConfiguration
{
  private String gccPath;
  private String gdbPath;
  private static LocalConfiguration instance = null;
  
  
  // Edit your configuration files if you need to
  // Please do not push your own config file to Git
  
  private LocalConfiguration()
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
  
}
