package api.component;

import java.nio.file.Path;

import configuration.LocalConfiguration;
import controller.EventController;
import view.MainWindowView;
/**
 * This class is used for editing the file path of PDE-C’s compiler since the default compiler can be changed by other developers. Another use of this class is
 * for compiling a source code given a file path.
 * @author Alexander John D. Jose
 *
 */
public class PCompiler
{
  private LocalConfiguration config;
  private MainWindowView main;
  private EventController event;
  
  /**
   * Initialized PCompiler
   */
  public PCompiler()
  {
	config = LocalConfiguration.getInstance();
	main = MainWindowView.getInstance();
	event = EventController.getEventController();
  }
  
  /**
   * Sets the compiler to be used.
   * @param path location of the compiler to be used, e.g., C:/cygwin64/bin/gcc.exe
   */
  public void setCompiler(String path)
  {
	config.setGccPath(path);
  }
  
  /**
   * Retrieves the current path of the compiler
   * @return returns the path of the current compiler as a String
   */
  public String getCurrentCompiler()
  {
	return config.getGccPath();
  }
  
  /**
   * Compiles a source code
   * @param filePath path of the source code to be compiled
   * @return returns the path of the compiled source code
   */
  public Path compile(Path filePath)
  {
	return event.compile(main.getMainFrame(), main.getEditor(), filePath, main.getErrorLog());
  }
  
  /**
   * Compiles and run a source code
   * @param filePath the source code to be compiled and run
   * @return returns the error message of compilation if present
   */
  public String compileRun(Path filePath)
  {
	Path next = compile(filePath);
	String errors = "";
	
	if (!main.getErrorLog().getText().trim().equals(""))
	{
	  
	}
	
	else
	{
	  event.runProgram(next);	 
	  errors = MainWindowView.getInstance().getErrorLog().toString();
	}

	return errors;
  }
}
