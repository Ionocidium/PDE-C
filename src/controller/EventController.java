package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;

import configuration.LocalConfiguration;
import controller.fileops.FileLoad;
import controller.fileops.FileSave;
import debugging.controls.LocalVariableListExtractor;
import service.ClientService;
import view.CompileLog;
import view.DebuggingManager;
import view.DownloadWindow;
import view.MainWindowView;
import view.SetIPAddress;
import view.SourceCodeUploaderView;

/**
 * Lists all of the event invocations being called by various buttons like New, Open, Save.
 * 
 * <p>
 * This includes, but not limited to the following:
 * <ul>
 *  <li>Compiling</li>
 *  <li>Debugging</li>
 *  <li>Setting the IP Address</li>
 *  <li>Managing the breakpoints</li>
 *  <li>Creating a new file</li>
 *  <li>Opening the file</li>
 *  <li>Saving the file</li>
 *  <li>Sending the source code</li>
 *  <li>Checking if the program exists</li>
 *  <li>Running the program</li>
 *  </ul>
 * </p>
 * 
 *  
 * 
 * @author In Yong S. Lee
 * @author Alexander John D. Jose
 * @author Lorenzo Miguel G. Monzon
 *
 */
public class EventController
{	
	private static EventController instance = null;
	private final JFileChooser fileChooser;
	private FileLoad loader;
	private FileSave saveFile;
	private FileNameExtensionFilter cFilter;
	private String command = "";

	private EventController()
	{
		saveFile = new FileSave();
		loader = new FileLoad();
		fileChooser = new JFileChooser();

		cFilter = new FileNameExtensionFilter(
					"C Source (*.c)", "c");
		fileChooser.setFileFilter(cFilter);
	}
  
	/**
	 * Gets the instance of Event Controller, if it exists. Otherwise, it will create a new instance of the event controller.
	 * @return the <code>EventController</code>'s instance
	 */
	public static EventController getEventController()
	{
		if (instance == null)
		{
			instance = new EventController();
		}

		return instance;
  	}
	
	/**
	 * Opens the Open File Frame.
	 * 
	 * @param frame The target frame to modify.
	 * @param editorPane The <code>editorPane</code> to modify.
	 * @return the <code>Path</code> selected by the student.
	 */
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

	/**
	 * Gets the feedback history file (*.pdec).
	 * 
	 * @param feedbackFilePath The original path to convert
	 * @return the feedback file <code>Path</code>.
	 */
	public Path getFeedbackFile(Path feedbackFilePath)
	{	
			String pdecFile = "";
			pdecFile = feedbackFilePath.toString();
			pdecFile = pdecFile.replaceAll("(\\.c)", ".pdec");
			pdecFile = pdecFile.replaceAll("(\\\\)", "\\\\");
			//System.out.println(pdecFile);
			feedbackFilePath = Paths.get(pdecFile);
	
			return feedbackFilePath;
		
	}

	/**
	 * Gets the source code (*.pdec).
	 * 
	 * @param feedbackFilePath The original path to convert
	 * @return the source code C file <code>Path</code>.
	 */
	public Path getCFile(Path feedbackFilePath)
	{	
		//System.out.println(feedbackFilePath.toString());
		String cFile = "";
		cFile = feedbackFilePath.toString();
		cFile = cFile.replaceAll("(\\.pdec)", ".c");
		cFile = cFile.replaceAll("(\\\\)", "\\\\");
		//System.out.println(pdecFile);
		feedbackFilePath = Paths.get(cFile);
		//System.out.println(feedbackFilePath.toString());
		
		return feedbackFilePath;
	}
	
	/**
	 * Sets the current compiler for compilation of the source code.
	 * 
	 * @param frame The target frame to use for location relativity.
	 */
	public void changeSettingsGcc(JFrame frame)
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
	}

	/**
	 * Sets the current debugger for debugging of the source code.
	 * 
	 * @param frame The target frame to use for location relativity.
	 */
	public void changeSettingsGdb(JFrame frame)
	{
	  FileNameExtensionFilter exeFilter = new FileNameExtensionFilter(
			"Executable file (*.exe)", "exe");
	  JFileChooser exeFileChooser = new JFileChooser();
	  LocalConfiguration local = LocalConfiguration.getInstance();
	  
	  exeFileChooser = new JFileChooser();
	  exeFileChooser.setFileFilter(exeFilter);
	  int returnVal = exeFileChooser.showOpenDialog(frame);
	  
	  if (returnVal == JFileChooser.APPROVE_OPTION)
	  {
		Path path = Paths.get(exeFileChooser.getSelectedFile().getAbsolutePath());
		local.setGdbPath(path.toAbsolutePath().toString());
	  }
	}

	/**
	 * Sets the IP Address to connect to the server side to retrieve the activity and send the files.
	 */
	public void changeIPSettings()
	{
		ClientService client = ClientService.getClientService();
		String ipAddress = JOptionPane.showInputDialog(MainWindowView.getInstance().getMainFrame(), "Please enter the IP Address you would want to connect:");
		if(ipAddress == null);
		else
		{
			if (!ipAddress.trim().equals(""))
			{
				client.setIPAddress(ipAddress);
			}
		}
	}
	
	/**
	 * Saves the path settings to a file for persistent changes.
	 */
	public void savePathSettings()
	{
	  LocalConfiguration local = LocalConfiguration.getInstance();
	  
	  try
	  {
		FileWriter fw = new FileWriter(new File("resources/settings.txt"));
		BufferedWriter writer = new BufferedWriter(fw);
		  
		writer.write(local.getGccPath() + "\n" + local.getGdbPath());
		writer.flush();
		writer.close();
	  }
		
	  catch(Exception ex)
	  {
	    ex.printStackTrace();
	  }
	}

	/**
	 * Saves the source code to the said <code>Path</code>.
	 * 
	 * @param frame The target frame to modify.
	 * @param editorPane The <code>editorPane</code> to use.
	 * @param state The modification state to change.
	 * @return the <code>Path</code> selected by the student.
	 */
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
			  
			  Path newPath = Paths.get(pathsss);
			  
			  saveFile.writeFile(newPath, editorPane.getText());
			  returnedPath = newPath;
			  frame.setTitle("PDE-C - " + newPath);
			}
			state = false;
		}
		
		return returnedPath;
	}

	/**
	 * Sends the source code to the server.
	 * <p>
	 * 	For failsafe reasons, a student should set the IP Address first before sending the source code to the server.
	 * </p>
	 * 
	 * @param consoleLog The logger to use.
	 * @param filePath The source code to send.
	 */
	public void sendSrcCode(JTextArea consoleLog, Path filePath)
	{
	  ClientService client = ClientService.getClientService();
		  
	  
	  if (filePath == null)
	  {
		JOptionPane.showMessageDialog(null, "Cannot send empty an source code.", "Warning", JOptionPane.INFORMATION_MESSAGE);
	  }
	  
	  else
	  {
		if (client.getCurrIpAddr().equals("0.0.0.0"))
		{
		  this.changeIPSettings();
		}
			
		else
		{
	      SourceCodeUploaderView upload = new SourceCodeUploaderView(filePath, consoleLog);
		}
	  }
	}

	/**
	 * Downloads the activity from the server.
	 * <p>
	 * 	For failsafe reasons, a student should set the IP Address first before downloading the activity files from the server.
	 * </p>
	 * 
	 */
	public void downloadActivity()
	{
	  ClientService client = ClientService.getClientService();
	  if (client.getCurrIpAddr().equals("0.0.0.0"))
	  {
		  this.changeIPSettings();
	  }
	  
	  else
	  {
        DownloadWindow download = new DownloadWindow();
	  }
	}
	
	/**
	 * Saves the source code to the said <code>Path</code>.
	 * 
	 * @param frame The target frame to modify.
	 * @param editorPane The <code>editorPane</code> to use.
	 * @param filePath The absolute path to write.
	 * @param state The modification state to change.
	 */
	public void saveFile(JFrame frame, RSyntaxTextArea editorPane, Path filePath, boolean state)
	{
		saveFile.writeFile(filePath, editorPane.getText());
		frame.setTitle("PDE-C - " + filePath.toString());
		state = false;
	}
	
	/**
	 * Compiles the source code.
	 * <p>
	 *  It checks if the compiler exists. Otherwise, compilation of the source code will not work. As a result, it is imperative to open a source code first before compiling. Otherwise, opening the file will be done by the time a student presses Compile button.
	 * </p>
	 * 
	 * @param frame The target frame to modify.
	 * @param editorPane The <code>editorPane</code> to use.
	 * @param filePath The source code to compile.
	 * @param compileLog The <code>textArea</code> to use for logging errors.
	 * @returns the <code>filePath</code>
	 */
	public Path compile(JFrame frame, RSyntaxTextArea editorPane, Path filePath, JTextArea compileLog)
	{
	  
	  Path path = null;
	  
	  if (this.checkIfGccExists())
	  {
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
	  }
	  
	  return path;
	}
	
	/**
	 * Prototype Version of compile method.
	 * <p>
	 *  This should pop up a <code>CompileLog</code> in a separate window.
	 * </p>
	 * 
	 * @param filePath The source code to compile.
	 */
	@Deprecated
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

	/**
	 * Prototype Version of compile method.
	 * <p>
	 *  This should pop up a <code>CompileLog</code> in a separate window.
	 * </p>
	 * 
	 * @param p The source code to use.
	 */
	public void runProgram(Path p)
	{
		try
		{
			File f = new File(p.toString());
			String dir = f.getParent();
			String filename = f.getName();
			String currentOS = System.getProperty("os.name").toLowerCase();
			
		  	if (currentOS.indexOf("win") >= 0)
		  	{
		  		String compiled = dir.concat("\\").concat(filename.substring(0, f.getName().lastIndexOf(".")).concat(".exe"));
		  		
		  		if (this.programIfExists(compiled))
		  		{
		  		  ProcessBuilder pb = new ProcessBuilder("cmd", "/k", "start", compiled);
		  		  Process proc = pb.start();
		  		}
		  		
		  		else
		  		{
		  		  JOptionPane.showMessageDialog(null, "Some compilation error occured", "Error", JOptionPane.ERROR_MESSAGE);
		  		}
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
	}
	
	/**
	 * Sets the said functions to enabled/disabled.
	 */
	public void debugToggler()
	{
		MainWindowView mwv = MainWindowView.getInstance();
		MainWindowView.debugMgrInstance = DebuggingManager.getInstance();
		mwv.getNewButton().setEnabled(!mwv.getNewButton().isEnabled());
		mwv.getNewFileItem().setEnabled(!mwv.getNewFileItem().isEnabled());
		mwv.getOpenButton().setEnabled(!mwv.getOpenButton().isEnabled());
		mwv.getOpenFileItem().setEnabled(!mwv.getOpenFileItem().isEnabled());
		mwv.getSaveButton().setEnabled(!mwv.getSaveButton().isEnabled());
		mwv.getSaveFileItem().setEnabled(!mwv.getSaveFileItem().isEnabled());
		mwv.getSaveAsFileItem().setEnabled(!mwv.getSaveAsFileItem().isEnabled());
		mwv.getCompileButton().setEnabled(!mwv.getCompileButton().isEnabled());
		mwv.getCompilerunButton().setEnabled(!mwv.getCompilerunButton().isEnabled());
		mwv.getCompileBuildItem().setEnabled(!mwv.getCompileBuildItem().isEnabled());
		mwv.getStepOverButton().setEnabled(!mwv.getStepOverButton().isEnabled());
		mwv.getResumeButton().setEnabled(!mwv.getResumeButton().isEnabled());
		mwv.getStopButton().setEnabled(!mwv.getStopButton().isEnabled());
		MainWindowView.debugMgrInstance.getBtnStepOver().setEnabled(!MainWindowView.debugMgrInstance.getBtnStepOver().isEnabled());
		MainWindowView.debugMgrInstance.getBtnTrackVars().setEnabled(!MainWindowView.debugMgrInstance.getBtnTrackVars().isEnabled());
		MainWindowView.debugMgrInstance.getBtnStop().setEnabled(!MainWindowView.debugMgrInstance.getBtnStop().isEnabled());
	}

	/**
	 * Writes the debug log.
	 * @param s The output of the debugger.
	 */
	public void writeInErrorLog(String s)
	{
		MainWindowView.debugLog.setText(s);
		MainWindowView.debugLog.setCaretPosition(MainWindowView.debugLog.getDocument().getLength());
	}

	/**
	 * Initializes a debugging session.
	 * @param filePath the source code to compile for debugging purposes.
	 */
	public void debugInit(Path filePath)
	{
		LocalConfiguration local = LocalConfiguration.getInstance();
		MainWindowView mwv = MainWindowView.getInstance();
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
			debugActual(exePath, local, mwv);
		}
		else
		{
			int returnVal = fileChooser.showOpenDialog(mwv.getMainFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				Path path = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
				filePath = path;
				String ext = path.getFileName().toString();
				if (loader.checker(ext))
				{
					String pathContents = loader.loadFile(path);
					mwv.getEditor().setText(pathContents);
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
					debugActual(exePath, local, mwv);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	/**
	 * Debugs the program through the use of the current debugger.
	 * 
	 * <p>
	 *  What it actually does is to get the <code>ActionListener</code> of each <code>JButton</code>, and stores it in a separate variable. These said buttons will replace the current function of the buttons involved and replace it with a different function.
	 *  Function modification will include adding a breakpoint, removing a breakpoint, removing all breakpoints from the command line interface through the use of output streams without the need to type commands to the current debugger.
	 *  Step Over and Continue will have a function in addition to invoking the actual debugger, while Start will be changed to Stop, and its functions related to the said button will be changed as well.
	 *  
	 *  Button modifications are included as follows:
	 *  <ul>
	 *   <li>Add A Breakpoint</li>
	 *   <li>Remove a Breakpoint/Remove selected breakpoint(s)</li>
	 *   <li>Remove all breakpoints</li>
	 *   <li>Start/Stop debugging</li>
	 *  </ul>
	 * </p>
	 * @param exe the compiled source code for debugging
	 * @param local the <code>LocalConfiguration</code> to use
	 * @param mwv the Main Window View to use
	 */
	public void debugActual(String exe, LocalConfiguration local, MainWindowView mwv)
	{
		JButton breakpointButton = mwv.getBreakpointButton(), 
				delbreakpointButton = mwv.getDelbreakpointButton(), 
				delallbreakpointButton = mwv.getDelallbreakpointButton();
		JMenuItem toggleBreakpointMenuItem = mwv.getToggleBreakItem();
		writeInErrorLog("");
		Thread debug = new Thread(new Runnable(){
			public void run()
			{
				try
				{
					MainWindowView.debugMgrInstance.openMe();
					MainWindowView.debugMgrInstance.modifyBreakpoints();
					LocalVariableListExtractor lvle = new LocalVariableListExtractor();
					HashMap<String, String> locals = new HashMap<String, String>();
					String line, prevLine;
					StringBuilder sb = new StringBuilder();
					/*
					int breaknum = 0; // temp
					GutterIconInfo gii = null; // temp
					GutterIconInfo pointer = null;
					*/
					delayMe(100);
					Process process = Runtime.getRuntime().exec(local.getGdbPath() + " \"" + exe + "\"");
	                if (process != null){
	                    BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
	                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(process.getOutputStream())),true);
	                    
	                    // Capture user input through the use of continue and break buttons
	                    
	                    ActionListener abListener = breakpointButton.getActionListeners()[0];
	                    ActionListener dbListener = delbreakpointButton.getActionListeners()[0];
	                    ActionListener dabListener = delallbreakpointButton.getActionListeners()[0];
	                    ActionListener tbListener = toggleBreakpointMenuItem.getActionListeners()[0];
	                    ActionListener ab_debug_Listener = MainWindowView.debugMgrInstance.getBtnAddABreakpoint().getActionListeners()[0];
	                    ActionListener db_debug_Listener = MainWindowView.debugMgrInstance.getBtnRemoveSelected().getActionListeners()[0];
	                    ActionListener dab_debug_Listener = MainWindowView.debugMgrInstance.getBtnRemoveAll().getActionListeners()[0];
	                    ActionListener cntListener = MainWindowView.debugMgrInstance.getBtnContinue().getActionListeners()[0];
	                    breakpointButton.removeActionListener(abListener);
	                    delbreakpointButton.removeActionListener(dbListener);
	                    delallbreakpointButton.removeActionListener(dabListener);
	                    toggleBreakpointMenuItem.removeActionListener(tbListener);
	                    MainWindowView.debugMgrInstance.getBtnAddABreakpoint().removeActionListener(ab_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnRemoveSelected().removeActionListener(db_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnRemoveAll().removeActionListener(dab_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnContinue().removeActionListener(cntListener);
	                    
	                    ActionListener abListener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		int answer = addbreakpoint(mwv.getMainFrame(), mwv.getGut(), mwv.getBreakpoints());
                    			out.println("break " + answer);
	                    	}
                		};
	                    
                		ActionListener dbListener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		int answer = deletebreakpoint(mwv.getMainFrame(), mwv.getGut(), mwv.getBreakpoints());
                    			out.println("delete " + answer);
        						if(mwv.getBreakpoints().size() == 0) {
        							mwv.getDelbreakpointButton().setEnabled(false);
        							mwv.getDelallbreakpointButton().setEnabled(false);
        							MainWindowView.debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
        							MainWindowView.debugMgrInstance.getBtnRemoveAll().setEnabled(false);
        						}
	                    	}
                		};
	                    
                		ActionListener dabListener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		deleteallbreakpoint(mwv.getMainFrame(), mwv.getGut(), mwv.getBreakpoints());
                				mwv.getDelbreakpointButton().setEnabled(false);
                				mwv.getDelallbreakpointButton().setEnabled(false);
    							MainWindowView.debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
    							MainWindowView.debugMgrInstance.getBtnRemoveAll().setEnabled(false);
	                    		out.println("delete");
	                    	}
                		};
                		
                		ActionListener tbListener2 = new ActionListener() {
                			public void actionPerformed(ActionEvent e) {
                				int lineNum = mwv.getEditor().getCaretLineNumber();
                				if(!mwv.getBreakpoints().contains(lineNum))
                				{
                					silentAddBreakpoint(mwv.getGut(), mwv.getBreakpoints(), lineNum + 1);
                        			out.println("break " + (lineNum + 1));
                				}
                				else
                				{
                					silentDeleteBreakpoint(mwv.getGut(), mwv.getBreakpoints(), lineNum + 1);
                        			out.println("delete " + (lineNum + 1));
                				}
            					MainWindowView.debugMgrInstance.modifyBreakpoints();
                				if(mwv.getBreakpoints().size() > 0) {
                					delbreakpointButton.setEnabled(true);
                					delallbreakpointButton.setEnabled(true);
        							MainWindowView.debugMgrInstance.getBtnRemoveSelected().setEnabled(true);
        							MainWindowView.debugMgrInstance.getBtnRemoveAll().setEnabled(true);
                				}
                				else
                				{
                					delbreakpointButton.setEnabled(false);
                					delallbreakpointButton.setEnabled(false);
        							MainWindowView.debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
        							MainWindowView.debugMgrInstance.getBtnRemoveAll().setEnabled(false);
                				}
                			}
                		};
                		
                		ActionListener ab_debug_Listener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		int answer = addbreakpoint(MainWindowView.debugMgrInstance.getDebuggingFrame(), mwv.getGut(), mwv.getBreakpoints()) + 1;
	                    		if(answer > 0)
	            				{
	                    			MainWindowView.debugMgrInstance.modifyBreakpoints();
	            					if(mwv.getBreakpoints().size() > 0) {
	            						mwv.getDelbreakpointButton().setEnabled(true);
	            						mwv.getDelallbreakpointButton().setEnabled(true);
	        							MainWindowView.debugMgrInstance.getBtnRemoveSelected().setEnabled(true);
	        							MainWindowView.debugMgrInstance.getBtnRemoveAll().setEnabled(true);
	            					}
	            				}
                    			out.println("break " + answer);
	                    	}
                		};
	                    
                		ActionListener db_debug_Listener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	            				List<Integer> selected = MainWindowView.debugMgrInstance.getBpList().getSelectedValuesList();
	            				if(selected.size() > 0)
	            				{
	            					for(int i = 0; i < selected.size(); i++)
	            					{
	            						int answer = selected.get(i);
	            						MainWindowView.debugMgrInstance.getLmbp().removeElement(answer);
	            						silentDeleteBreakpoint(mwv.getGut(), mwv.getBreakpoints(), selected.get(i));
	            						if(mwv.getBreakpoints().size() == 0) {
	            							mwv.getDelbreakpointButton().setEnabled(false);
	            							mwv.getDelallbreakpointButton().setEnabled(false);
	            							MainWindowView.debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
	            							MainWindowView.debugMgrInstance.getBtnRemoveAll().setEnabled(false);
	            						}
	                        			out.println("delete " + answer);
	            					}
	            					MainWindowView.debugMgrInstance.modifyBreakpoints();
	            				}
	                    	}
                		};
	                    
                		ActionListener dab_debug_Listener2 = new ActionListener() {
                			public void actionPerformed(ActionEvent e) {
                				MainWindowView.debugMgrInstance.setLmbp(new DefaultListModel<Integer>());
                				MainWindowView.debugMgrInstance.getBpList().setModel(MainWindowView.debugMgrInstance.getLmbp());
                				deleteallbreakpoint(MainWindowView.debugMgrInstance.getDebuggingFrame(), mwv.getGut(), mwv.getBreakpoints());
                				mwv.getDelbreakpointButton().setEnabled(false);
                				mwv.getDelallbreakpointButton().setEnabled(false);
    							MainWindowView.debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
    							MainWindowView.debugMgrInstance.getBtnRemoveAll().setEnabled(false);
	                    		out.println("delete");
                			}
                		};
                		
                		ActionListener tv_debug_Listener = new ActionListener() {
                			public void actionPerformed(ActionEvent e) {
                				JTable jt = MainWindowView.debugMgrInstance.getVarTable();
                        		int r = jt.getSelectedRow();
                        		boolean existing = false;
                        		if(r > -1)
                        		{
                        			HashMap<String, String> aWatch2 = MainWindowView.debugMgrInstance.getWatchList2();
                        			Map<String, String> map = new TreeMap<String, String>(aWatch2); // sorts keys in ascending order ref: http://stackoverflow.com/questions/7860822/sorting-hashmap-based-on-keys
                        			for(Map.Entry<String, String> entry : map.entrySet())
                        			{
                        				if(entry.getKey().equals(jt.getValueAt(r, 0)))
                        				{
                        					existing = true;
                        				}
                        			}
                        			if(!existing)
                        			{
                        				aWatch2.put(jt.getValueAt(r, 0).toString(), jt.getValueAt(r, 1).toString());
                        			}
                        			else
                        			{
                        				String theVariableToRemove = "";
                        				for(Map.Entry<String, String> entry : map.entrySet())
                        				{
                            				if(entry.getKey().equals(jt.getValueAt(r, 0)))
                            				{
                            					theVariableToRemove = entry.getKey();
                            				}
                        				}
                        				aWatch2.remove(theVariableToRemove);
                        			}
                        		}
                        		jt.repaint();
                			}
                		};

                		breakpointButton.addActionListener(abListener2);
                		delbreakpointButton.addActionListener(dbListener2);
                		delallbreakpointButton.addActionListener(dabListener2);
                		toggleBreakpointMenuItem.addActionListener(tbListener2);
                		MainWindowView.debugMgrInstance.getBtnAddABreakpoint().addActionListener(ab_debug_Listener2);
	                    MainWindowView.debugMgrInstance.getBtnRemoveSelected().addActionListener(db_debug_Listener2);
	                    MainWindowView.debugMgrInstance.getBtnRemoveAll().addActionListener(dab_debug_Listener2);
	                    MainWindowView.debugMgrInstance.getBtnTrackVars().addActionListener(tv_debug_Listener);
	                    
	                    ActionListener stepOverListener = new ActionListener()
	                    {
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	    	                    command = "info locals";
	    	                    out.println(command);
	                    		out.println("step");
	                    		locals.clear();
	        					delayMe(50);
	                    	}
	                    };
	                    ActionListener resumeListener = new ActionListener()
	                    {
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	    	                    command = "info locals";
	    	                    out.println(command);
	                    		out.println("continue");
	                    		locals.clear();
	        					delayMe(50);
	                    	}
	                    };
	                    ActionListener stopListener = new ActionListener()
	                    {
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		command = "";
			                    out.close();
	                    		locals.clear();
	        					delayMe(50);
	                    	}
	                    };
	                    mwv.getStepOverButton().addActionListener(stepOverListener);
	                    mwv.getResumeButton().addActionListener(resumeListener);
	                    mwv.getStopButton().addActionListener(stopListener);
	                    MainWindowView.debugMgrInstance.getBtnStepOver().addActionListener(stepOverListener);
	                    MainWindowView.debugMgrInstance.getBtnContinue().addActionListener(resumeListener);
	                    MainWindowView.debugMgrInstance.getBtnContinue().setText("Continue");
	                    MainWindowView.debugMgrInstance.getBtnStop().addActionListener(stopListener);
	                    boolean notYet = true;
	                    line = in.readLine();
	                    while (line != null && notYet){
	                    	//Regex: Breakpoint \d*, ([a-zA-Z_][a-zA-Z0-9_]*) (\(()\)) at (?:[a-zA-Z]\:|\\\\[\w\.]+\\[\w.$]+)\\(?:[\w]+\\)*\w([\w.])+:
	                    	if(line.startsWith("(gdb) ")) line = line.substring("(gdb) ".length()); 
	                    	if(command.equals("info locals"))
	                    	{
	                    		if(line.contains(" = "))
	                    		{
		                    		if(!(line.startsWith("0") || line.startsWith("1") || line.startsWith("2") || line.startsWith("3") || line.startsWith("4") ||
		                    				line.startsWith("5") || line.startsWith("6") || line.startsWith("7") || line.startsWith("8") || line.startsWith("9")))
		                    		{
		                    			locals.put(lvle.extractVars(line), lvle.extractVals(line));
		                    		}
//		                    		else
//		                    		{
//		                    			command = "";
//		                    		}
	                    		}
//	                    		MainWindowView.debugMgrInstance.modifyDebugging(locals);
	                    	}
	                    	else if (!command.equals("info locals"))
	                    	{
//                    			System.out.println("Refreshing local variables");
                    			if(command.equals("step") || command.equals("continue"))
                    			{
//                        			System.out.println("Step/Continue has been pressed.");
                    			}
	                    	}
	                    	if(line.endsWith("Type \"apropos word\" to search for commands related to \"word\"..."))
	                    	{
	    	                    for(int i = 0; i < mwv.getBreakpoints().size(); i++)
	    	                    {
	    	                    	out.println("break " + (mwv.getBreakpoints().get(i) + 1));
	    	                    }
	    	                    out.println("start");
	    	                    command = "info locals";
	    	                    out.println(command);
	                    	}
	                    	if(line.startsWith("Breakpoint"))
	                    	{
	                    		MainWindowView.debugMgrInstance.modifyDebugging(locals);
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
		            		MainWindowView.debugMgrInstance.modifyDebugging(locals);
		            		prevLine = line;
		            		line = in.readLine();
	                    }
	                    
	                    process.destroy();
	                    //stepOverButton.removeActionListener(arg0);
	                    breakpointButton.removeActionListener(abListener2);
	                    delbreakpointButton.removeActionListener(dbListener2);
	                    delallbreakpointButton.removeActionListener(dabListener2);
	                    toggleBreakpointMenuItem.removeActionListener(tbListener2);
                		MainWindowView.debugMgrInstance.getBtnAddABreakpoint().removeActionListener(ab_debug_Listener2);
	                    MainWindowView.debugMgrInstance.getBtnRemoveSelected().removeActionListener(db_debug_Listener2);
	                    MainWindowView.debugMgrInstance.getBtnRemoveAll().removeActionListener(dab_debug_Listener2);
	                    MainWindowView.debugMgrInstance.getBtnTrackVars().removeActionListener(tv_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnContinue().removeActionListener(resumeListener);
	                    
	                    breakpointButton.addActionListener(abListener);
	                    delbreakpointButton.addActionListener(dbListener);
	                    delallbreakpointButton.addActionListener(dabListener);
	                    toggleBreakpointMenuItem.addActionListener(tbListener);
	                    MainWindowView.debugMgrInstance.getBtnAddABreakpoint().addActionListener(ab_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnRemoveSelected().addActionListener(db_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnRemoveAll().addActionListener(dab_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnContinue().addActionListener(cntListener);
	                    MainWindowView.debugMgrInstance.getBtnContinue().setText("Start");
	                    MainWindowView.debugMgrInstance.getWatchList2().clear();
	                    MainWindowView.debugMgrInstance.getVarVals().clear();
	                    MainWindowView.debugMgrInstance.resetDebuggingTable();
	                    debugToggler();
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
	
	/**
	 * Adds a breakpoint through the use of a dialog box.
	 * @param jf the target frame to use for location relativity.
	 * @param g the current <code>Gutter</code>.
	 * @param b the list of all breakpoints.
	 * @return the line number to add.
	 */
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
					JOptionPane.showMessageDialog(jf, "Line " + input + " added successfully.", 
							"Added!", JOptionPane.INFORMATION_MESSAGE);
					res = bpnum;
				}
				else
					JOptionPane.showMessageDialog(jf, "Line " + input + " already exists.", 
							"Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (BadLocationException ble)
			{
				JOptionPane.showMessageDialog(jf, "The line specified is not found. Discontinuing adding breakpoints...", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (NumberFormatException nfe)
			{
				JOptionPane.showMessageDialog(jf, "You entered a non-integer number!", 
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (NullPointerException npe)
			{
				
			}
		}
		return res;
	}

	/**
	 * Silently adds a breakpoint.
	 * @param g the current <code>Gutter</code>.
	 * @param b the list of all breakpoints.
	 * @param num the breakpoint to add.
	 * @return the line number to add.
	 */
	public int silentAddBreakpoint(Gutter g, ArrayList<Integer> b, int num){
		int res = -1;
		try
		{
			int bpnum = num - 1;
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
				res = bpnum;
			}
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
		return res;
	}
	
	/**
	 * Removes a breakpoint through the use of a dialog box.
	 * @param jf the target frame to use for location relativity.
	 * @param g the current <code>Gutter</code>.
	 * @param b the list of all breakpoints.
	 * @return the line number to delete.
	 */
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
						JOptionPane.showMessageDialog(jf, "Line " + input + " does not exist.", 
								"Error", JOptionPane.ERROR_MESSAGE);
					else
					{
						g.removeTrackingIcon(gii);
						b.remove(target);
						MainWindowView.breakpoints2.remove(target);
						JOptionPane.showMessageDialog(jf, "Line " + input + " removed successfully.", 
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
		
		/**
		 * Silently removes a breakpoint.
		 * @param g the current <code>Gutter</code>.
		 * @param b the list of all breakpoints.
		 * @param num the breakpoint to remove.
		 * @return the line number to remove.
		 */
		public int silentDeleteBreakpoint(Gutter g, ArrayList<Integer> b, int bnum){
			int res = -1;
			try
			{
				int bpnum = bnum - 1;
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
				g.removeTrackingIcon(gii);
				b.remove(target);
				MainWindowView.breakpoints2.remove(target);
				res = bpnum;
			}
			catch (NullPointerException npe)
			{
				
			}
			return res;
		}

		/**
		 * Removes all breakpoints and notifies the user of the changes.
		 * @param jf the target frame to use for location relativity.
		 * @param g the current <code>Gutter</code>.
		 * @param b the list of all breakpoints.
		 * @return the line number to delete.
		 */
		public void deleteallbreakpoint(JFrame jf, Gutter g, ArrayList<Integer> b){				
			g.removeAllTrackingIcons();
			b.clear();
			MainWindowView.breakpoints2.clear();
			JOptionPane.showMessageDialog(jf, "All breakpoints removed successfully.", 
					"Removed", JOptionPane.INFORMATION_MESSAGE);
		}
		
		/**
		 * Silently removes all breakpoints.
		 * @param g the current <code>Gutter</code>.
		 * @param b the list of all breakpoints.
		 */
		public void quietlydeleteallbreakpoint(Gutter g, ArrayList<Integer> b){				
			g.removeAllTrackingIcons();
			b.clear();
			MainWindowView.breakpoints2.clear();
		}
		
		/**
		 * Checks if <code>GutterIconInfo</code> has information.
		 * @param g the current <code>Gutter</code>.
		 * @return <code>true</code> if it contains no values for <code>GutterIconInfo</code>'s attribute, <code>false</code> if otherwise.
		 */
		public boolean gutterIconInfoIsEmpty(GutterIconInfo gii)
		{
			return gii == null?true:false;
		}

		/**
		 * Checks if <code>resources</code> folder exists.
		 */
		public void checkIfResourceExists()
		{
		  if (!Files.exists(Paths.get("resources/")))
		  {
			try
			{
			  Files.createDirectories(Paths.get("resources/"));
			} catch (IOException e)
			{
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			}
		  }
		}
		
		/**
		 * Delays the current code for a said <code>ms</code>.
		 * @param ms milliseconds.
		 */
		public static void delayMe(long ms)
		{
			long nowTime = System.currentTimeMillis();
			while(System.currentTimeMillis() < nowTime + ms) ;
		}
		
		
		private boolean checkIfGccExists()
		{
		  boolean itExists = false;
		  
		  LocalConfiguration local = LocalConfiguration.getInstance();
		  
		  if (Files.exists(Paths.get(local.getGccPath())))
		  {
			itExists = true;
		  }
		  
		  else
		  {
			JOptionPane.showMessageDialog(null, "PDE-C cannot find the gcc.exe specified. Please use the settings to fix the path.", "Error", JOptionPane.ERROR_MESSAGE);
		  }
		  
		  return itExists;
		}
		
		private boolean programIfExists(String compiledPath)
		{
		  boolean itExists = false;
		  
		  if (Files.exists(Paths.get(compiledPath)))
		  {
			itExists = true;
		  }
		  
		  return itExists;
		}
		
		
		//work in progress
		private boolean testForGccInSysPath()
		{
		  boolean itExists = false;
		  
		  Runtime rt = Runtime.getRuntime();
		  String[] commands = {"gcc.exe", "--version"};
		  
		  
		  int lineCtr = 0;
		  
		  try
		  {
			//proc = rt.exec(commands);
			ProcessBuilder pb = new ProcessBuilder("cmd", "/k", "start", "gcc.exe", "--version");
	  		Process proc = pb.start();
//			BufferedReader stdInput = new BufferedReader(new 
//			     InputStreamReader(proc.getInputStream()));
//			
//			BufferedReader stdError = new BufferedReader(new 
//			     InputStreamReader(proc.getErrorStream()));
//			
//			String s = null;
//			
//			while ((s = stdInput.readLine()) != null) 
//			{
//			    lineCtr++;
//			}
//			
//			while ((s = stdError.readLine()) != null) {
//			    System.out.println(s);
//			}
//			
//			if (lineCtr > 0)
//			{
//			  itExists = true;
//			}
//			
//			else
//			{
//			  itExists = false;
//			  JOptionPane.showMessageDialog(null, "No gcc found in the machine's environment variables. Exit PDE-C and set gcc in the environment variables.", "Error", JOptionPane.ERROR_MESSAGE);
//			}
			
			Thread t = new Thread(new ProcessOutputController(proc.getInputStream()));
			t.start();
		  } 
		  
		  catch (Exception e)
		  {
			// TODO Auto-generated catch block
			itExists = false;
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No gcc found in the machine's environment variables. Exit PDE-C and set gcc in the environment variables.", "Error", JOptionPane.ERROR_MESSAGE);
		  }
		  
		  return itExists;
		}
	}