package api.component;

import java.nio.file.Path;

import javax.swing.JButton;

import buttonactions.CompileAction;
import buttonactions.DownloadAction;
import buttonactions.SendAction;
import mainwindowcomponents.ToolBar;
import view.MainWindowView;

/**
 * A PDE-C specific button class that extends the JButton class. It contains all of
   JButton methods. In addition, some methods are also added by the researchers
   for the ease of use of other developers.
 * 
 * @author Alexander John D. Jose
 *
 */

public class PButton extends JButton
{
  public static final int DOWNLOAD_ACTION = 0;
  public static final int SEND_ACTION = 1;
  public static final int COMPILE_ACTION = 2;
  
  private static final long serialVersionUID = 1L;
  private String bName;
  private ToolBar bar = ToolBar.getToolbar();
  private MainWindowView main = MainWindowView.getInstance();
  
  /**
   * 
   * @param name the desired name of PButton
   */
  public PButton(String name)
  {
	bName = name;
	this.setText(bName);
  }
  
  /**
   * The main function of this
	method is to add the PButton to PDE-C’s toolbar.
   */
  public void addToToolbar()
  {
	if (this != null)
	{
	  bar.addComponent(this);
	}	
  }
  
  /**
   * A method that remove the said component from PDE-C. This
	 does not need any parameter since it instantly remove
	 the component.
   */
  public void remove()
  {
	bar.removeComponent(this);
  }
  
  /**
   * This method adds a pre-made action to PButton.
   * @param action Integer value that determines if the buttons is to be
                   made to a download button, send source code button, or
				   compile button. pecific codes are:
				   PButton.DOWNLOAD ACTION,
				   PButton.SEND ACTION,
	               PButton.COMPILE ACTION
				   
   * @param filePath This is an optional parameter for send and compile action. If
					 this parameter is not null, the button will send or compile a file
					 that is passed inside the filePath.
					 Download action should
					 always have a null value for this

   * @param code This is a required parameter that must be with the Compile
				 action input. The specific codes are:
			 	 CompileAction.COMPILE,
				 CompileAction.COMPILE RUN,
				 CompileAction.NO ACTION.
				 No Action code must be used when the button is not to be
				 programmed as a compile button.
   */
  public void addAction(int action, Path filePath, int code)
  {
	switch (action)
	{
	  case 0: this.addActionListener(new DownloadAction("Download")); break;
	  case 1: this.addActionListener(new SendAction("Send", main.getErrorLog(), filePath)); break;
	  case 2: this.addActionListener(new CompileAction("Compile", filePath, code)); break;
	  default: System.out.println("Invalid"); break;
	}
  }

}
