package api.component;

import java.nio.file.Path;

import configuration.LocalConfiguration;
import controller.EventController;
import view.MainWindowView;

public class PCompiler
{
  private LocalConfiguration config;
  private MainWindowView main;
  private EventController event;
  
  public PCompiler()
  {
	config = LocalConfiguration.getInstance();
	main = MainWindowView.getInstance();
	event = EventController.getEventController();
  }
  
  public void setCompiler(String path)
  {
	config.setGccPath(path);
  }
  
  public String getCurrentCompiler()
  {
	return config.getGccPath();
  }
  
  public Path compile(Path filePath)
  {
	return event.compile(main.getMainFrame(), main.getEditor(), filePath, main.getErrorLog());
  }
  
  public void compileRun(Path filePath)
  {
	Path next = compile(filePath);
	
	if (!main.getErrorLog().getText().trim().equals(""))
	{
	  
	}
	
	else
	{
	  event.runProgram(next);	  
	}

  }
}
