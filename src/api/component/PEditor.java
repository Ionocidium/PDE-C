package api.component;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import view.MainWindowView;
/**
 * This class is used for retrieving the editor used by PDE-C.
 * @author Alexander John Jose
 *
 */
public class PEditor
{
  private MainWindowView main;
  
  /**
   * The constructor of PEditor
   */
  public PEditor()
  {
	main = MainWindowView.getInstance();
  }
  
  /**
   * The method for retrieving the editor of PDE-C
   * @return returns an object of RSyntaxTextArea which is the editor used by PDE-C
   */
  public RSyntaxTextArea getEditor()
  {
	return main.getEditor();
  }
}
