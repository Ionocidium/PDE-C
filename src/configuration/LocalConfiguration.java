package configuration;

public class LocalConfiguration
{
  private String gccPath;
  
  public LocalConfiguration()
  {
	if(System.getProperty("os.arch").equals("amd64"))
	{
        gccPath = "C:\\cygwin64\\bin\\gcc.exe";
	}
	
	else if(System.getProperty("os.arch").equals("x86"))
	{
        gccPath = "C:\\cygwin\\bin\\gcc.exe";
	}
  }
  
  public String getGccPath()
  {
	return gccPath;
  }
}
