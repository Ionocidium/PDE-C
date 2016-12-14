package buttonactions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;

import controller.EventController;
import view.MainWindowView;

/**
 * A compile action for PDE-C API
 * 
 * @author Alexander John Jose
 *
 */
public class CompileAction extends AbstractAction
{
  public static final int COMPILE = 0;
  public static final int COMPILE_RUN = 1;
  public static final int NO_ACTION = 2;
  
  private EventController event = EventController.getEventController();
  private Path filePath;
  private MainWindowView main = MainWindowView.getInstance();
  private int code;
  
  /**
   * CompileAction constructor
   * @param name the name of the action
   * @param filePath the location of the C source code
   * @param code code for compiling, compiling and running, or no action
   */
  public CompileAction(String name, Path filePath, int code)
  {
	super(name);
	this.filePath = filePath;
	this.code = code;
  }
  
  /**
   * Another CompileAction constructor
   * @param name the name of the action
   * @param mnemonic mnemonic for the action
   * @param filePath the location of the C source code
   * @param code <code>int</code> for compiling, compiling and running, or no action. Values are 0 to 2. 
   */
  public CompileAction(String name, int mnemonic, Path filePath, int code)
  {
	super(name);
	putValue(MNEMONIC_KEY, mnemonic);
	this.filePath = filePath;
	this.code = code;
  }

  @Override
  /**
   * Action performed that is made for compiling event
   */
  public void actionPerformed(ActionEvent arg0)
  {
	switch (code)
	{
	  case 0: event.compile(main.getMainFrame(), main.getEditor(), filePath, main.getErrorLog()); break;
	  case 1: {
		        filePath = event.compile(main.getMainFrame(), main.getEditor(), filePath, main.getErrorLog());
		        event.runProgram(filePath);
		        break;
	  		  }
	  default: event.compile(main.getMainFrame(), main.getEditor(), filePath, main.getErrorLog()); break;
	}
  }
}
