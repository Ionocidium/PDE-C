package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

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
				frame.setTitle("PDE-C - " + ext);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return filePath;
	}
  
	public Path saveAsFile(JFrame frame, RSyntaxTextArea editorPane, boolean state)
	{
		int returnVal = fileChooser.showSaveDialog(frame);
		
		Path returnedPath = null;
	  
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			Path path = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());	
			
			if (path.toString().contains(".c"))
			{
			  saveFile.writeFile(path, editorPane.getText());
			  returnedPath = path;
			  frame.setTitle("PDE-C - " + path);
			}
			
			else
			{
			  String pathsss = path.toString().concat(".c");
			  
			  System.out.println(pathsss);
			  Path newPath = Paths.get(pathsss);
			  
			  saveFile.writeFile(newPath, editorPane.getText());
			  returnedPath = newPath;
			  frame.setTitle("PDE-C - " + newPath);
			}
			state = false;
		}
		
		return returnedPath;
	}
  
	public void saveFile(JFrame frame, RSyntaxTextArea editorPane, Path filePath, boolean state)
	{
		saveFile.writeFile(filePath, editorPane.getText());
		frame.setTitle("PDE-C - " + filePath.toString());
		state = false;
	}
  
	public Path compile(JFrame frame, RSyntaxTextArea editorPane, Path filePath, JTextArea compileLog)
	{
	  
	  Path path = null;
		try
		{
			if (filePath != null)
			{
			  	path = filePath;
			  	CommandLineControls clc = new CommandLineControls(filePath.toString());
				
				compileLog.setText(clc.getStdOut() + "\n" + clc.getStdError() + "\n");
				
				if (!clc.getStdError().equals(""))
				{
				  this.deleteDontTouch();
				}
			}
			
			else
			{
				int returnVal = fileChooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					path = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
					filePath = path;
					String ext = path.getFileName().toString();
					  
					if (loader.checker(ext))
					{
						String pathContents = loader.loadFile(path);
						editorPane.setText(pathContents);
						CommandLineControls clc = new CommandLineControls(filePath.toString());
						
						compileLog.setText(clc.getStdOut() + "\n" + clc.getStdError() + "\n");
						
						if (!clc.getStdError().equals(""))
						{
						  this.deleteDontTouch();
						}
					}
					  
					else
					{
						JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				
				else if (returnVal == JFileChooser.CANCEL_OPTION)
				{
				  
				}
			}
		}
		catch (IOException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return path;
	}
	
	public Path compile(Path filePath)
	{
	  try
	  {
		CompileLog log = new CompileLog(filePath);
	  }
	  
	  catch (Exception ex)
	  {
		ex.printStackTrace();
	  }
	  
	  return filePath;
	}

	public String runProgram()
	{
		String res = null;

		try
		{
		  	File donttouchFile = new File("resources/donttouch.bat");
		  	
		  	if (donttouchFile.exists() && !donttouchFile.isDirectory())
		  	{
		  	  Process process = new ProcessBuilder("resources/donttouch.bat").start();
		  	}
		  	
		  	else
		  	{
		  	  
		  	}
		}

		catch(Exception ex)
		{
			System.out.println("");
		}

		return res;
	}
	
	public void deleteDontTouch()
	{
	  File file = new File("resources/donttouch.bat");
	  
	  if (file.exists() && !file.isDirectory())
	  {
		file.delete();
	  }
	}

	public void debugToggler(JFrame frame, JButton newButton, JMenuItem newFileItem, JButton openButton, JMenuItem openFileItem, JButton saveButton, JMenuItem saveFileItem, JMenuItem saveAsFileItem, JButton compileButton, JMenuItem compileBuildItem, JButton debugButton, JMenuItem debugBuildItem, JButton stepOverButton, JButton resumeButton, JButton stopButton)
	{
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
  
	public void debugActual2(JFrame frame, RSyntaxTextArea editorPane, Path filePath, JButton newButton, JMenuItem newFileItem, JButton openButton, JMenuItem openFileItem, JButton saveButton, JMenuItem saveFileItem, JMenuItem saveAsFileItem, JButton compileButton, JMenuItem compileBuildItem, JButton debugButton, JMenuItem debugBuildItem, JButton stepOverButton, JButton resumeButton, JButton stopButton, RSyntaxTextArea rsta, RTextScrollPane rtsp, ArrayList<Integer> bp)
	{
		try
		{
			if (filePath != null)
	  		{
				CompileLog log = new CompileLog(filePath);
				String currentPath = filePath.toString();
				String exePath = currentPath.substring(0, currentPath.lastIndexOf(".c")) + ".exe";
				debugActual(exePath, frame, newButton, newFileItem, openButton, openFileItem, saveButton, saveFileItem, saveAsFileItem, compileButton, compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, stopButton, rsta, rtsp, bp);
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
						String currentPath = filePath.toString();
						String exePath = currentPath.substring(0, currentPath.lastIndexOf(".c")) + ".exe";
						debugActual(exePath, frame, newButton, newFileItem, openButton, openFileItem, saveButton, saveFileItem, saveAsFileItem, compileButton, compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, stopButton, rsta, rtsp, bp);
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

	public void debugActual(String exe, JFrame frame, JButton newButton, JMenuItem newFileItem, JButton openButton, JMenuItem openFileItem, JButton saveButton, JMenuItem saveFileItem, JMenuItem saveAsFileItem, JButton compileButton, JMenuItem compileBuildItem, JButton debugButton, JMenuItem debugBuildItem, JButton stepOverButton, JButton resumeButton, JButton stopButton, RSyntaxTextArea rsta, RTextScrollPane rtsp, ArrayList<Integer> bp)
	{
		Thread debug = new Thread(new Runnable(){
			public void run()
			{
				try
				{
					String line;
					ArrayList<String> lines = new ArrayList<String>();
					Process process = Runtime.getRuntime().exec("gdb \"" + exe + "\"");
	
	                if (process != null){
	                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
	                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(process.getOutputStream())),true);
	
	                    out.flush();
	
	                    out.flush();
	                    
	                    // get the gutter
	                    // Gutter gut = rtsp.getGutter();
	                    // GutterIconInfo[] gii = gut.getBookmarks();
	                    
	                    // get breakpoint numbers from RScrollPane or RSyntaxTextArea, make a for loop out of it (store as arraylist, we will call it as al)
	                    // ArrayList<Integer> alBreakPt = new ArrayList<Integer>(); // initialise breakpoint numbers based on the user inputted breakpoints
	                    /*
	                    for(int i = 0; i < gii.length; i++)
	                    {
	                    	alBreakPt.add(gii[i].getMarkedOffset());
	                    }
	                    */
	                    
	                    for(int i = 0; i < bp.size(); i++)
	                    {
	                    	out.println("break " + bp.get(i));
	                    }
	                    
	                    
	                    
	                    
	                    
	                    // out.println("break 12"); // forget hardcoding.
	                    
	                    /*
	                    // Run the program to be debugged
	                     */
	                    
	                    out.println("start");
	                    
	                    /*
	                    // Capture user input through the use of continue and break buttons
	                     */
	                    stepOverButton.addActionListener(new ActionListener()
	                    {
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		out.println("s");
	                    	}
	                    }
	                    );
	                    
	                    resumeButton.addActionListener(new ActionListener()
	                    {
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		out.println("c");
	                    	}
	                    }
	                    );
	                    
	                    stopButton.addActionListener(new ActionListener()
	                    {
	                    	public void actionPerformed(ActionEvent e)
	                    	{
			                    out.close();
	                    	}
	                    }
	                    );
	
	                    while ((line = in.readLine()) != null){
	                        lines.add(line);
	                    }
	                    String[] lineArray = new String[lines.size()];
	                    lineArray  = lines.toArray(lineArray);
	
	                    for (int i=0; i < lineArray.length; i++) {
	                        System.out.println(lineArray[i].toString());
	                    }
	                    
	                    process.destroy();
	                    debugToggler(frame, newButton, newFileItem, openButton, openFileItem, saveButton, saveFileItem, saveAsFileItem, compileButton, compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, stopButton);
	                }
	            }
				catch (Exception ex)
				{
	                ex.printStackTrace();
	            }
			}
		});
		debug.start();
		}
	}