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
import javax.swing.Icon;
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

import configuration.LocalConfiguration;
import controller.fileops.FileLoad;
import controller.fileops.FileSave;
import view.CompileLog;
import view.DownloadWindow;
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
			System.out.println(ext);
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
	
	public Path getFeedbackFile(Path filePath)
	{	
		Path path = filePath;
		
		String filename = path.getFileName().toString();
		filename = filename.substring(0, filename.lastIndexOf('.'));
		filename = filename + ".pdec";
		
		
		System.out.println(filename);
//		if (loader.checkerpdec(ext))
//		{
//			//if feedback file exists
//			String pathContents = loader.loadFile(path);
//			editorPane.setText(pathContents);
//			frame.setTitle("PDE-C - " + ext);
//		}
//		else
//		{
//			filePath = null;
//		}

		return filePath;
	}
	
	public void changeSettings(JFrame frame)
	{
	  FileNameExtensionFilter exeFilter = new FileNameExtensionFilter(
			"Executable file (*.exe)", "exe");
	  JFileChooser exeFileChooser = new JFileChooser();
	  exeFileChooser.setFileFilter(exeFilter);
	  int returnVal = exeFileChooser.showOpenDialog(frame);
	  LocalConfiguration local = LocalConfiguration.getInstance();
	  if (returnVal == JFileChooser.APPROVE_OPTION)
	  {
		Path path = Paths.get(exeFileChooser.getSelectedFile().getAbsolutePath());
		local.setGccPath(path.toAbsolutePath().toString());
	  }
	  else
	  {
		System.out.println("what");
	  }
	  System.out.println(local.getGccPath());
	  exeFileChooser = new JFileChooser();
	  exeFileChooser.setFileFilter(exeFilter);
	  returnVal = exeFileChooser.showOpenDialog(frame);
	  if (returnVal == JFileChooser.APPROVE_OPTION)
	  {
		Path path = Paths.get(exeFileChooser.getSelectedFile().getAbsolutePath());
		local.setGdbPath(path.toAbsolutePath().toString());
	  }
	  else
	  {
		System.out.println("what");
	  }
	  System.out.println(local.getGdbPath());
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
	
	public void downloadActivity()
	{
	  DownloadWindow download = new DownloadWindow();
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
			  	
			  	if (!clc.getStdOut().equals(""))
			  	{
			  		compileLog.setText(clc.getStdOut() + "\n" + clc.getStdError() + "\n");
			  	}
			  	else
			  	{
			  		compileLog.setText(clc.getStdError() + "\n");
			  	}
			  	
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
						
					  	if (!clc.getStdOut().equals(""))
					  	{
					  		compileLog.setText(clc.getStdOut() + "\n" + clc.getStdError() + "\n");
					  	}
					  	else
					  	{
					  		compileLog.setText(clc.getStdError() + "\n");
					  	}
						
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
		MainWindowView.errorLog.setText(s);
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
		LocalConfiguration local = LocalConfiguration.getInstance();
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
		  	try
		  	{
		  		Process process = Runtime.getRuntime().exec(local.getGccPath() + " \"" + currentPath + "\" -o " + exePath + " -g");
		  	}
		  	catch(IOException ioe)
		  	{
				JOptionPane.showMessageDialog(null, "The process may be in use.", "Error", JOptionPane.ERROR_MESSAGE);
		  	}
			debugActual(exePath, frame, newButton, newFileItem, openButton, openFileItem, 
					saveButton, saveFileItem, saveAsFileItem, compileButton, compilerunButton, 
					compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, 
					stopButton, rsta, rtsp, addBreakItem, delBreakItem, 
					delallBreakItem, breakpointButton, delbreakpointButton, delallbreakpointButton, bp, local);
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
				  	try
				  	{
				  		Process process = Runtime.getRuntime().exec(local.getGccPath() + " \"" + currentPath + "\" -o " + exePath + " -g");
				  	}
				  	catch(IOException ioe)
				  	{
						JOptionPane.showMessageDialog(null, "The process may be in use.", "Error", JOptionPane.ERROR_MESSAGE);
				  	}
					debugActual(exePath, frame, newButton, newFileItem, openButton, 
							openFileItem, saveButton, saveFileItem, saveAsFileItem, 
							compileButton, compilerunButton, compileBuildItem, debugButton, 
							debugBuildItem, stepOverButton, resumeButton, stopButton, rsta, 
							rtsp, addBreakItem, delBreakItem, delallBreakItem, breakpointButton, 
							delbreakpointButton, delallbreakpointButton, bp, local);
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
			JButton delbreakpointButton, JButton delallbreakpointButton, ArrayList<Integer> bp, LocalConfiguration local)
	{
		writeInErrorLog("");
		Thread debug = new Thread(new Runnable(){
			public void run()
			{
				try
				{
					String line;
					StringBuilder sb = new StringBuilder();
					/*
					int breaknum = 0; // temp
					GutterIconInfo gii = null; // temp
					GutterIconInfo pointer = null;
					*/
					long nowTime = System.currentTimeMillis();
					while(System.currentTimeMillis() < nowTime + 100) ;
					Process process = Runtime.getRuntime().exec(local.getGdbPath() + " \"" + exe + "\"");
	                if (process != null){
	                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
	                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(process.getOutputStream())),true);
	                    
	                    // Capture user input through the use of continue and break buttons
	                    
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
	                    		out.println("step");
	                    	}
	                    }
	                    );
	                    
	                    resumeButton.addActionListener(new ActionListener()
	                    {
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		out.println("continue");
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
	                    	//Regex: Breakpoint \d*, ([a-zA-Z_][a-zA-Z0-9_]*) (\(()\)) at (?:[a-zA-Z]\:|\\\\[\w\.]+\\[\w.$]+)\\(?:[\w]+\\)*\w([\w.])+:
	                    	if(line.startsWith("(gdb) ")) line = line.substring("(gdb) ".length()); 
	                    	if(line.endsWith("Type \"apropos word\" to search for commands related to \"word\"..."))
	                    	{
	    	                    for(int i = 0; i < bp.size(); i++)
	    	                    {
	    	                    	out.println("break " + (bp.get(i) + 1));
	    	                    }
	    	                    out.println("start");
	                    	}
	                    	if(line.startsWith("Breakpoint"))
	                    	{
	                    		// check gutter
	                    		/*
	                    		Gutter g = rtsp.getGutter();
	                    		if(gutterIconInfoIsEmpty(pointer))
	                    		{
		                    		String breakpointStringLine = line;
		                    		breakpointStringLine = breakpointStringLine.substring(breakpointStringLine.lastIndexOf(":") + 1);
		                    		breaknum = Integer.parseInt(breakpointStringLine);
		                    		// find breakpoint
		                    		int bArrIndex = bp.indexOf(breaknum);
		                    		if(bArrIndex != -1)
		                    		{
			                    		// get guttericoninfo for that breakpoint
			                    		gii = MainWindowView.breakpoints2.get(bArrIndex);
			                    		// remove breakpoint temporarily
			                    		g.removeTrackingIcon(gii);
		                    		}
		                    		// add breakpoint afterwards
		        					pointer = g.addLineTrackingIcon(breaknum - 1, new ImageIcon("resources/images/materialsmall/pointright.png"));
	                    		}
	                    		else
	                    		{
	                    			// remove breakpoint pointer
	                    			g.removeTrackingIcon(pointer);
	                    			// add temporary breakpoint again whose line is touched
	                    			g.addLineTrackingIcon(breaknum - 1, gii.getIcon());
	                    			// follow through
		                    		String breakpointStringLine = line;
		                    		breakpointStringLine = breakpointStringLine.substring(breakpointStringLine.lastIndexOf(":") + 1);
		                    		breaknum = Integer.parseInt(breakpointStringLine);
		                    		// find breakpoint
		                    		int bArrIndex = bp.indexOf(breaknum);
		                    		if(bArrIndex != -1)
		                    		{
			                    		// get guttericoninfo for that breakpoint
			                    		gii = MainWindowView.breakpoints2.get(bArrIndex);
			                    		// remove breakpoint temporarily
			                    		g.removeTrackingIcon(gii);
		                    		}
		                    		// add breakpoint afterwards
		        					pointer = g.addLineTrackingIcon(breaknum - 1, new ImageIcon("resources/images/materialsmall/pointright.png"));
	                    		}
	                    		*/
	                    	}
	                    	if (line.startsWith("Temporary") && line.contains("c:"))
	                    	{
	                    		out.println("continue"); // prevent temporary breakpoints
	                    	}
	                    	if (line.indexOf("exited with") > 0)
	                    	{
	                    		notYet = false;
	                    	}
	                    	sb.append(line + "\n");
		                    writeInErrorLog(sb.toString());
	                    }
	                    
	                    process.destroy();
	                    //stepOverButton.removeActionListener(arg0);
	                    addBreakItem.removeActionListener(abListener2);
	                    delBreakItem.removeActionListener(dbListener2);
	                    delallBreakItem.removeActionListener(dabListener2);
	                    
	                    addBreakItem.addActionListener(abListener);
	                    delBreakItem.addActionListener(dbListener);
	                    delallBreakItem.addActionListener(dabListener);
	                    debugToggler(frame, newButton, newFileItem, openButton, openFileItem, 
	                    		saveButton, saveFileItem, saveAsFileItem, compileButton, 
	                    		compilerunButton, compileBuildItem, debugButton, debugBuildItem,
	                    		stepOverButton, resumeButton, stopButton);
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
					JOptionPane.showMessageDialog(null, "Line " + input + " added successfully.", 
							"Added!", JOptionPane.INFORMATION_MESSAGE);
					res = bpnum;
				}
				else
					JOptionPane.showMessageDialog(null, "Line " + input + " already exists.", 
							"Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (BadLocationException ble)
			{
				JOptionPane.showMessageDialog(null, "The line specified is not found. Discontinuing adding breakpoints...", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(null, "You entered a non-integer number!", 
						"Error", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "Line " + input + " does not exist.", 
								"Error", JOptionPane.ERROR_MESSAGE);
					else
					{
						g.removeTrackingIcon(gii);
						b.remove(target);
						MainWindowView.breakpoints2.remove(target);
						JOptionPane.showMessageDialog(null, "Line " + input + " removed successfully.", 
								"Removed", JOptionPane.INFORMATION_MESSAGE);
						res = bpnum;
					}
				}
				catch (NumberFormatException nfe)
				{
					JOptionPane.showMessageDialog(null, "You entered a non-integer number!", 
							"Error", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "All breakpoints removed successfully.", 
					"Removed", JOptionPane.INFORMATION_MESSAGE);
		}
		
		public void quietlydeleteallbreakpoint(Gutter g, ArrayList<Integer> b){				
			g.removeAllTrackingIcons();
			b.clear();
			MainWindowView.breakpoints2.clear();
		}
		
		public boolean gutterIconInfoIsEmpty(GutterIconInfo gii)
		{
			return gii == null?true:false;
		}
	}