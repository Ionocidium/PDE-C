package api.component;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import view.MainWindowView;

public class PEditor
{
  private MainWindowView main;
  
  public PEditor()
  {
	main = MainWindowView.getInstance();
  }
  
  public RSyntaxTextArea getEditor()
  {
	return main.getEditor();
  }
}
