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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.RTextScrollPane;

import controller.fileops.FileLoad;
import controller.fileops.FileSave;
import view.CompileLog;
import view.MainWindowView;
import view.SourceCodeUploaderView;

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
				filePath = null;
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
	
	public void sendSrcCode(JTextArea consoleLog, Path filePath)
	{
	  if (filePath == null)
	  {
		
	  }
	  
	  else
	  {
		SourceCodeUploaderView upload = new SourceCodeUploaderView(filePath, consoleLog);
	  }
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
				  // this.deleteDontTouch();
					clc.runMyCompiler();
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
						  //b this.deleteDontTouch();
							clc.runMyCompiler();
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

	/*
	@unused
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
	*/
	
	public String runProgram(Path p)
	{
		String res = null;

		try
		{
			File f = new File(p.toString());
			String dir = f.getParent();
			String filename = f.getName();
			String currentOS = System.getProperty("os.name").toLowerCase();
		  	if (currentOS.indexOf("win") >= 0)
		  	{
		  		String compiled = dir.concat("\\").concat(filename.substring(0, f.getName().lastIndexOf(".")).concat(".exe"));
	  			ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", compiled, "/b", compiled);
	  			Process proc = pb.start();
		  	}
		  	else if(currentOS.indexOf("mac") >= 0)
		  	{
		  		String compiled = dir.concat("\\").concat(filename.substring(0, f.getName().lastIndexOf(".")).concat(".out"));
	  			Runtime rt = Runtime.getRuntime();
	  			Process proc = rt.exec(compiled);
		  	}
		  	else if(currentOS.indexOf("nix") >= 0 || currentOS.indexOf("nux") >= 0)
		  	{
		  		String compiled = dir.concat("\\").concat(filename.substring(0, f.getName().lastIndexOf(".")));
	  			Runtime rt = Runtime.getRuntime();
	  			Process proc = rt.exec(compiled);
		  	}
		}

		catch(Exception ex)
		{
			System.out.println("");
		}

		return res;
	}
	
	/*
	@unused
	public void deleteDontTouch()
	{
	  File file = new File("resources/donttouch.bat");
	  
	  if (file.exists() && !file.isDirectory())
	  {
		file.delete();
	  }
	}
	*/

	public void debugToggler(JFrame frame, JButton newButton, JMenuItem newFileItem, 
			JButton openButton, JMenuItem openFileItem, JButton saveButton, 
			JMenuItem saveFileItem, JMenuItem saveAsFileItem, JButton compileButton, 
			JButton compilerunButton, JMenuItem compileBuildItem, JButton debugButton, 
			JMenuItem debugBuildItem, JButton stepOverButton, JButton resumeButton, 
			JButton stopButton)
	{
	  	newButton.setEnabled(!newButton.isEnabled());
		newFileItem.setEnabled(!newFileItem.isEnabled());
		openButton.setEnabled(!openButton.isEnabled());
		openFileItem.setEnabled(!openFileItem.isEnabled());
		saveButton.setEnabled(!saveButton.isEnabled());
		saveFileItem.setEnabled(!saveFileItem.isEnabled());
		saveAsFileItem.setEnabled(!saveAsFileItem.isEnabled());
		compileButton.setEnabled(!compileButton.isEnabled());
		compilerunButton.setEnabled(!compilerunButton.isEnabled());
		compileBuildItem.setEnabled(!compileBuildItem.isEnabled());
		debugButton.setEnabled(!debugButton.isEnabled());
		debugBuildItem.setEnabled(!debugBuildItem.isEnabled());
		stepOverButton.setEnabled(!stepOverButton.isEnabled());
		resumeButton.setEnabled(!resumeButton.isEnabled());
		stopButton.setEnabled(!stopButton.isEnabled());
	}
	
	public void writeInErrorLog(String s)
	{
		MainWindowView.consoleLog.setText(s);
	}
  
	public void debugActual2(JFrame frame, RSyntaxTextArea editorPane, Path filePath, 
			JButton newButton, JMenuItem newFileItem, JButton openButton, 
			JMenuItem openFileItem, JButton saveButton, JMenuItem saveFileItem, 
			JMenuItem saveAsFileItem, JButton compileButton, JButton compilerunButton, 
			JMenuItem compileBuildItem, JButton debugButton, JMenuItem debugBuildItem,
			JButton stepOverButton, JButton resumeButton, JButton stopButton, 
			RSyntaxTextArea rsta, RTextScrollPane rtsp, JMenuItem addBreakItem, 
			JMenuItem delBreakItem, JMenuItem delallBreakItem, JButton breakpointButton, 
			JButton delbreakpointButton, JButton delallbreakpointButton,
			ArrayList<Integer> bp)
	{
		if (filePath != null)
		{
			String currentPath = filePath.toString();
			String exePath = new String();
			String currentOS = System.getProperty("os.name").toLowerCase();
		  	if (currentOS.indexOf("win") >= 0)
		  	{
				exePath = currentPath.substring(0, currentPath.lastIndexOf(".c")) + ".exe";
		  	}
		  	else if(currentOS.indexOf("mac") >= 0)
		  	{

				exePath = currentPath.substring(0, currentPath.lastIndexOf(".c")) + ".out";
		  	}
		  	else if(currentOS.indexOf("nix") >= 0 || currentOS.indexOf("nux") >= 0)
		  	{

				exePath = currentPath.substring(0, currentPath.lastIndexOf(".c"));
		  	}
			debugActual(exePath, frame, newButton, newFileItem, openButton, openFileItem, 
					saveButton, saveFileItem, saveAsFileItem, compileButton, compilerunButton, 
					compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, 
					stopButton, rsta, rtsp, addBreakItem, delBreakItem, 
					delallBreakItem, breakpointButton, delbreakpointButton, delallbreakpointButton, bp);
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
					String currentPath = filePath.toString();
					String exePath = new String();
					String currentOS = System.getProperty("os.name").toLowerCase();
				  	if (currentOS.indexOf("win") >= 0)
				  	{
						exePath = currentPath.substring(0, currentPath.lastIndexOf(".c")) + ".exe";
				  	}
				  	else if(currentOS.indexOf("mac") >= 0)
				  	{

						exePath = currentPath.substring(0, currentPath.lastIndexOf(".c")) + ".out";
				  	}
				  	else if(currentOS.indexOf("nix") >= 0 || currentOS.indexOf("nux") >= 0)
				  	{

						exePath = currentPath.substring(0, currentPath.lastIndexOf(".c"));
				  	}
					debugActual(exePath, frame, newButton, newFileItem, openButton, 
							openFileItem, saveButton, saveFileItem, saveAsFileItem, 
							compileButton, compilerunButton, compileBuildItem, debugButton, 
							debugBuildItem, stepOverButton, resumeButton, stopButton, rsta, 
							rtsp, addBreakItem, delBreakItem, delallBreakItem, breakpointButton, delbreakpointButton, delallbreakpointButton, bp);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	

	public void debugActual(String exe, JFrame frame, JButton newButton, 
			JMenuItem newFileItem, JButton openButton, JMenuItem openFileItem, 
			JButton saveButton, JMenuItem saveFileItem, JMenuItem saveAsFileItem, 
			JButton compileButton, JButton compilerunButton, JMenuItem compileBuildItem, 
			JButton debugButton, JMenuItem debugBuildItem, JButton stepOverButton, 
			JButton resumeButton, JButton stopButton, RSyntaxTextArea rsta, 
			RTextScrollPane rtsp, JMenuItem addBreakItem, JMenuItem delBreakItem, 
			JMenuItem delallBreakItem, JButton breakpointButton, 
			JButton delbreakpointButton, JButton delallbreakpointButton, ArrayList<Integer> bp)
	{
		writeInErrorLog("");
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
	                    
	                    ActionListener abListener = addBreakItem.getActionListeners()[0];
	                    ActionListener dbListener = delBreakItem.getActionListeners()[0];
	                    ActionListener dabListener = delallBreakItem.getActionListeners()[0];
	                    addBreakItem.removeActionListener(abListener);
	                    delBreakItem.removeActionListener(dbListener);
	                    delallBreakItem.removeActionListener(dabListener);
	                    
	                    ActionListener abListener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		int answer = addbreakpoint(frame, rtsp.getGutter(), bp);
	                    		if(answer <= -1)
	                    			out.println("break " + answer);
	                    	}
                		};
	                    
                		ActionListener dbListener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		int answer = deletebreakpoint(frame, rtsp.getGutter(), bp);
	                    		if(answer <= -1)
	                    			out.println("delete " + answer);
	                    	}
                		};
	                    
                		ActionListener dabListener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		deleteallbreakpoint(rtsp.getGutter(), bp);
	                    		out.println("delete");
	                    	}
                		};

	                    addBreakItem.addActionListener(abListener2);
	                    delBreakItem.addActionListener(dbListener2);
	                    delallBreakItem.addActionListener(dabListener2);
	                    
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
	                    boolean notYet = true;
	                    while ((line = in.readLine()) != null && notYet){
	                    	if (line.indexOf("exited with") > 0)
	                    	{
	                    		notYet = false;
	                    	}
	                    	lines.add(line);
	                    }
	                    String[] lineArray = new String[lines.size()];
	                    lineArray  = lines.toArray(lineArray);
	                    
	                    /*
	                    for (int i=0; i < lineArray.length; i++) {
	                        writeInErrorLog(lineArray[i].toString());
	                    }
	                    */
	                    
	                    writeInErrorLog(lineArray[lineArray.length - 1]);
	                    
	                    process.destroy();
	                    //stepOverButton.removeActionListener(arg0);
	                    addBreakItem.removeActionListener(abListener2);
	                    delBreakItem.removeActionListener(dbListener2);
	                    delallBreakItem.removeActionListener(dabListener2);
	                    
	                    addBreakItem.addActionListener(abListener);
	                    delBreakItem.addActionListener(dbListener);
	                    delallBreakItem.addActionListener(dabListener);
	                    debugToggler(frame, newButton, newFileItem, openButton, openFileItem, saveButton, saveFileItem, saveAsFileItem, compileButton, compilerunButton, compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, stopButton);
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
	
	public int addbreakpoint(JFrame jf, Gutter g, ArrayList<Integer> b){
		int res = -1;
		String input = JOptionPane.showInputDialog(
                jf,
                "Insert a breakpoint, enter a line number (must not exceed the end of file):");
		if(input == null || input.isEmpty())
		{
			// do nothing
		}
		else
		{
			try
			{
				int bpnum = Integer.parseInt(input) - 1;
				boolean existing = false;
				for(int i = 0; i < b.size(); i++)
				{
					if(b.get(i) == bpnum)
					{
						existing = true;
					}
				}
				if(!existing)
				{
					GutterIconInfo gii = g.addLineTrackingIcon(bpnum, new ImageIcon("resources/images/materialsmall/breakpointeditor.png"));
					b.add(bpnum);
					MainWindowView.breakpoints2.add(gii);
					JOptionPane.showMessageDialog(null, "Line " + input + " added successfully.", "Added!", JOptionPane.INFORMATION_MESSAGE);
					res = bpnum;
				}
				else
					JOptionPane.showMessageDialog(null, "Line " + input + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (BadLocationException ble)
			{
				JOptionPane.showMessageDialog(null, "The line specified is not found. Discontinuing adding breakpoints...", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null, "You entered a non-integer number!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (NullPointerException npe)
			{
				
			}
		}
		return res;
	}
	
		public int deletebreakpoint(JFrame jf, Gutter g, ArrayList<Integer> b){
			int res = -1;
			String input = JOptionPane.showInputDialog(
	                jf,
	                "Remove a breakpoint, enter a line number (must not exceed the end of file):");
			if(input == null || input.isEmpty())
			{
				// do nothing
			}
			else
			{
				try
				{
					int bpnum = Integer.parseInt(input) - 1;
					int target = -1;
					GutterIconInfo gii = null;
					for(int i = 0; i < b.size(); i++)
					{
						if(b.get(i) == bpnum)
						{
							gii = MainWindowView.breakpoints2.get(i);
							target = i;
						}
					}
					if(target == -1)
						JOptionPane.showMessageDialog(null, "Line " + input + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
					else
					{
						g.removeTrackingIcon(gii);
						b.remove(target);
						MainWindowView.breakpoints2.remove(target);
						JOptionPane.showMessageDialog(null, "Line " + input + " removed successfully.", "Removed", JOptionPane.INFORMATION_MESSAGE);
						res = bpnum;
					}
				}
				catch (NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "You entered a non-integer number!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				catch (NullPointerException npe)
				{
					
				}
			}
			return res;
		}
	
		public void deleteallbreakpoint(Gutter g, ArrayList<Integer> b){				
			g.removeAllTrackingIcons();
			b.clear();
			MainWindowView.breakpoints2.clear();
			JOptionPane.showMessageDialog(null, "All breakpoints removed successfully.", "Removed", JOptionPane.INFORMATION_MESSAGE);
		}
	}