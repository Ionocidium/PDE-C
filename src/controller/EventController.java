package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

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
  private FileNameExtensionFilter cFilter;
  
  private EventController()
  {
	saveFile = new FileSave();
	loader = new FileLoad();
	fileChooser = new JFileChooser();
	
	cFilter = new FileNameExtensionFilter(
	     "C Source (*.c)", "c");
	fileChooser.setFileFilter(cFilter);
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
  

	public String runProgram()
	{
	  String res = null;
	  
	  try
	  {
	    Process process = new ProcessBuilder("resources/donttouch.bat").start();
	  }
	  
	  catch(Exception ex)
	  {
		ex.printStackTrace();
	  }
	  
	  return res;
	}

  public void debugToggler(JFrame frame, JButton newButton, JMenuItem newFileItem, JButton openButton, JMenuItem openFileItem, JButton saveButton, JMenuItem saveFileItem, JMenuItem saveAsFileItem, JButton compileButton, JMenuItem compileBuildItem, JButton debugButton, JMenuItem debugBuildItem, JButton stepOverButton, JButton resumeButton, JButton stopButton){
	  	newButton.setEnabled(!newButton.isEnabled());
		newFileItem.setEnabled(!newFileItem.isEnabled());
		openButton.setEnabled(!openButton.isEnabled());
		openFileItem.setEnabled(!openFileItem.isEnabled());
		saveButton.setEnabled(!saveButton.isEnabled());
		saveFileItem.setEnabled(!saveFileItem.isEnabled());
		saveAsFileItem.setEnabled(!saveAsFileItem.isEnabled());
		compileButton.setEnabled(!compileButton.isEnabled());
		compileBuildItem.setEnabled(!compileBuildItem.isEnabled());
		debugButton.setEnabled(!debugButton.isEnabled());
		debugBuildItem.setEnabled(!debugBuildItem.isEnabled());
		stepOverButton.setEnabled(!stepOverButton.isEnabled());
		resumeButton.setEnabled(!resumeButton.isEnabled());
		stopButton.setEnabled(!stopButton.isEnabled());
  }
}
