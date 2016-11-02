package api.component;

import configuration.LocalConfiguration;

public class PCompiler
{
  private LocalConfiguration config;
  
  public PCompiler()
  {
	config = LocalConfiguration.getInstance();
  }
  
  public void setCompiler(String path)
  {
	config.setGccPath(path);
  }
  
  public String getCurrentCompiler()
  {
	return config.getGccPath();
  }
}
