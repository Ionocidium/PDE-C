package controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import controller.fileops.FileLoad;
import controller.fileops.FileSave;
import view.CompileLog;

public class EventController
{
  private static EventController instance = null;
  private final JFileChooser fileChooser;
  private FileLoad loader;
  private FileSave saveFile;
  
  private EventController()
  {
	saveFile = new FileSave();
	loader = new FileLoad();
	fileChooser = new JFileChooser();
  }
  
  public static EventController getEventController()
  {
	if (instance == null)
	{
	  instance = new EventController();
	}
	
	return instance;
  }
  
  public Path openFile(JFrame frame, RSyntaxTextArea editorPane)
  {
	int returnVal = fileChooser.showOpenDialog(frame);
	Path filePath = null;
	
	if (returnVal == JFileChooser.APPROVE_OPTION)
	{
	  Path path = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
	  filePath = path;
	  String ext = path.getFileName().toString();
	  
	  if (loader.checker(ext))
	  {
		String pathContents = loader.loadFile(path);
		editorPane.setText(pathContents);
	  }
	  
	  else
	  {
		JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
	  }
	}
	
	return filePath;
  }
  
  public void saveAsFile(JFrame frame, RSyntaxTextArea editorPane)
  {
	int returnVal = fileChooser.showSaveDialog(frame);
	  
	if (returnVal == JFileChooser.APPROVE_OPTION)
	{
	  Path path = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());			
	  saveFile.writeFile(path, editorPane.getText());
	}
  }
  
  public void saveFile(JFrame frame, RSyntaxTextArea editorPane, Path filePath)
  {
	saveFile.writeFile(filePath, editorPane.getText());
  }
  
  public void compile(JFrame frame, RSyntaxTextArea editorPane, Path filePath)
  {
	try
	  {
		if (filePath != null)
		{
		  CompileLog log = new CompileLog(filePath);
		}
		
		else
		{
		  int returnVal = fileChooser.showOpenDialog(frame);
			
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
			  Path path = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
			  filePath = path;
			  String ext = path.getFileName().toString();
			  
			  if (loader.checker(ext))
			  {
				String pathContents = loader.loadFile(path);
				editorPane.setText(pathContents);
				CompileLog log = new CompileLog(filePath);
			  }
			  
			  else
			  {
				JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
			  }
			}			
		}
	  }
	  
	  catch (IOException e1)
	  {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	  }
  }
}
