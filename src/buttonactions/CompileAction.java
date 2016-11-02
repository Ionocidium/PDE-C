package buttonactions;

import java.awt.event.ActionEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;

import controller.EventController;
import view.MainWindowView;

public class CompileAction extends AbstractAction
{
  public static final int COMPILE = 0;
  public static final int COMPILE_RUN = 1;
  public static final int NO_ACTION = 2;
  
  private EventController event = EventController.getEventController();
  private Path filePath;
  private MainWindowView main = MainWindowView.getInstance();
  private int code;
  
  public CompileAction(String name, Path filePath, int code)
  {
	super(name);
	this.filePath = filePath;
	this.code = code;
  }
  
  public CompileAction(String name, int mnemonic, Path filePath, int code)
  {
	super(name);
	putValue(MNEMONIC_KEY, mnemonic);
	this.filePath = filePath;
	this.code = code;
  }

  @Override
  public void actionPerformed(ActionEvent arg0)
  {
	switch (code)
	{
	  case 0: event.compile(main.getMainFrame(), main.getEditor(), filePath, main.getErrorLog());
	  case 1: {
		        filePath = event.compile(main.getMainFrame(), main.getEditor(), filePath, main.getErrorLog());
		        event.runProgram(filePath);
	  		  }
	  default: event.compile(main.getMainFrame(), main.getEditor(), filePath, main.getErrorLog());
	}
  }
}
