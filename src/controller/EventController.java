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

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
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
import debugging.controls.LocalVariableListExtractor;
import debugging.model.LocalObject;
import debugging.model.RowLocalObject;
import service.ClientService;
import view.CompileLog;
import view.DebuggingManager;
import view.DownloadWindow;
import view.MainWindowView;
import view.SetIPAddress;
import view.SourceCodeUploaderView;

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
	
	public Path getFeedbackFile(Path feedbackFilePath)
	{	
		
			System.out.println(feedbackFilePath.toString());
			String pdecFile = "";
			pdecFile = feedbackFilePath.toString();
			pdecFile = pdecFile.replaceAll("(\\.c)", ".pdec");
			pdecFile = pdecFile.replaceAll("(\\\\)", "\\\\");
			//System.out.println(pdecFile);
			feedbackFilePath = Paths.get(pdecFile);
			System.out.println(feedbackFilePath.toString());
	
			return feedbackFilePath;
		
	}
	
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
	  else
	  {
		System.out.println("what");
	  }
	}
	
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
	  
	  else
	  {
		System.out.println("what");
	  }
	}
	
	public void changeIPSettings()
	{
	  SetIPAddress set = new SetIPAddress();
	}
	
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
	  ClientService client = ClientService.getClientService();
	  
	  if (filePath == null)
	  {
		
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
		mwv.getDebugButton().setEnabled(!mwv.getDebugButton().isEnabled());
		mwv.getDebugBuildItem().setEnabled(!mwv.getDebugBuildItem().isEnabled());
		mwv.getStepOverButton().setEnabled(!mwv.getStepOverButton().isEnabled());
		mwv.getResumeButton().setEnabled(!mwv.getResumeButton().isEnabled());
		mwv.getStopButton().setEnabled(!mwv.getStopButton().isEnabled());
		MainWindowView.debugMgrInstance.getBtnContinue().setEnabled(!MainWindowView.debugMgrInstance.getBtnContinue().isEnabled());
		MainWindowView.debugMgrInstance.getBtnStepOver().setEnabled(!MainWindowView.debugMgrInstance.getBtnStepOver().isEnabled());
		MainWindowView.debugMgrInstance.getBtnStop().setEnabled(!MainWindowView.debugMgrInstance.getBtnStop().isEnabled());
		MainWindowView.debugMgrInstance.getBtnTrackVars().setEnabled(!MainWindowView.debugMgrInstance.getBtnTrackVars().isEnabled());
	}
	
	public void writeInErrorLog(String s)
	{
		MainWindowView.debugLog.setText(s);
	}
  
	public void debugInit(Path filePath, JButton breakpointButton, 
			JButton delbreakpointButton, JButton delallbreakpointButton)
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
//					ArrayList<LocalObject> locals = new ArrayList<LocalObject>();
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
	                    breakpointButton.removeActionListener(abListener);
	                    delbreakpointButton.removeActionListener(dbListener);
	                    delallbreakpointButton.removeActionListener(dabListener);
	                    toggleBreakpointMenuItem.removeActionListener(tbListener);
	                    MainWindowView.debugMgrInstance.getBtnAddABreakpoint().removeActionListener(ab_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnRemoveSelected().removeActionListener(db_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnRemoveAll().removeActionListener(dab_debug_Listener);
	                    
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
        						}
	                    	}
                		};
	                    
                		ActionListener dabListener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		deleteallbreakpoint(mwv.getGut(), mwv.getBreakpoints());
                				mwv.getDelbreakpointButton().setEnabled(false);
                				mwv.getDelallbreakpointButton().setEnabled(false);
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
                				}
                				else
                				{
                					delbreakpointButton.setEnabled(false);
                					delallbreakpointButton.setEnabled(false);
                				}
                			}
                		};
                		
                		ActionListener ab_debug_Listener2 = new ActionListener()
                		{
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		int answer = addbreakpoint(mwv.getMainFrame(), mwv.getGut(), mwv.getBreakpoints());
	                    		if(answer > 0)
	            				{
	                    			MainWindowView.debugMgrInstance.getLmbp().addElement(answer);
	                    			MainWindowView.debugMgrInstance.getBpList().setModel(MainWindowView.debugMgrInstance.getLmbp());
	            					if(mwv.getBreakpoints().size() > 0) {
	            						mwv.getDelbreakpointButton().setEnabled(true);
	            						mwv.getDelallbreakpointButton().setEnabled(true);
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
	            						}
	                        			out.println("delete " + answer);
	            					}
	            					MainWindowView.debugMgrInstance.getBpList().setModel(MainWindowView.debugMgrInstance.getLmbp());
	            				}
	                    	}
                		};
	                    
                		ActionListener dab_debug_Listener2 = new ActionListener() {
                			public void actionPerformed(ActionEvent e) {
                				MainWindowView.debugMgrInstance.setLmbp(new DefaultListModel<Integer>());
                				MainWindowView.debugMgrInstance.getBpList().setModel(MainWindowView.debugMgrInstance.getLmbp());
                				deleteallbreakpoint(mwv.getGut(), mwv.getBreakpoints());
                				mwv.getDelbreakpointButton().setEnabled(false);
                				mwv.getDelallbreakpointButton().setEnabled(false);
	                    		out.println("delete");
                			}
                		};
                		
                		ActionListener tv_debug_Listener = new ActionListener() {
                			public void actionPerformed(ActionEvent e) {
                        		int r = MainWindowView.debugMgrInstance.getVarTable().getSelectedRow();
                        		boolean existing = false;
                        		if(r > -1)
                        		{
                        			ArrayList<String> aWatch = MainWindowView.debugMgrInstance.getWatchList();
                        			ArrayList<RowLocalObject> aWatch2 = MainWindowView.debugMgrInstance.getWatchList2();
                        			for(int i = 0; i < aWatch.size(); i++)
                        			{
                        				if(aWatch.get(i).equals(Integer.toString(r)))
                        				{
                        					existing = true;
                        				}
                        			}
                        			if(!existing)
                        			{
                        				aWatch.add(Integer.toString(r));
                        				LocalObject lo = new LocalObject("", "");
                        				for(int i = 0; i < locals.size(); i++)
                        				{
                        					if(locals.get(i).getVariable().equals(MainWindowView.debugMgrInstance.getVarTable().getValueAt(r, 0)))
                        					{
                        						lo.setVariable(locals.get(i).getVariable());
                        						lo.setValue(locals.get(i).getValue());
                                				aWatch2.add(new RowLocalObject(r, lo));
                        					}
                        				}
                            			System.out.println("Added a watch.");
                        			}
                        			else
                        			{
                        				aWatch.remove(Integer.toString(r));
                        				LocalObject lo = new LocalObject("", "");
                        				for(int i = 0; i < aWatch2.size(); i++)
                        				{
                        					if(aWatch2.get(i).getLocalVarVal().getVariable().equals(MainWindowView.debugMgrInstance.getVarTable().getValueAt(r, 0)))
                        					{
                        						lo.setVariable(locals.get(i).getVariable());
                        						lo.setValue(locals.get(i).getValue());
                                				aWatch2.remove(i);
                        					}
                        				}
                            			System.out.println("Removed a watch.");
                        			}
                        			System.out.print("Current Watches: ");
//                        			for(int i = 0; i < aWatch.size(); i++)
//                        			{
//                        				System.out.print(aWatch.get(i) + " ");
//                        			}
                        			for(int i = 0; i < aWatch2.size(); i++)
                        			{
                        				System.out.print("Row " + aWatch2.get(i).getRow() + ": " + 
                        						aWatch2.get(i).getLocalVarVal().getVariable() + " = " + aWatch2.get(i).getLocalVarVal().getValue());
                            			System.out.println();
                        			}
                        		}
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
	                    	}
	                    };
	                    ActionListener stopListener = new ActionListener()
	                    {
	                    	public void actionPerformed(ActionEvent e)
	                    	{
	                    		command = "";
			                    out.close();
	                    		locals.clear();
	                    	}
	                    };
	                    mwv.getStepOverButton().addActionListener(stepOverListener);
	                    mwv.getResumeButton().addActionListener(resumeListener);
	                    mwv.getStopButton().addActionListener(stopListener);
	                    MainWindowView.debugMgrInstance.getBtnStepOver().addActionListener(stepOverListener);
	                    MainWindowView.debugMgrInstance.getBtnContinue().addActionListener(resumeListener);
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
                    			System.out.println("Refreshing local variables");
                    			if(command.equals("step") || command.equals("continue"))
                    			{
                        			System.out.println("Step/Continue has been pressed.");
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
	                    
	                    breakpointButton.addActionListener(abListener);
	                    delbreakpointButton.addActionListener(dbListener);
	                    delallbreakpointButton.addActionListener(dabListener);
	                    toggleBreakpointMenuItem.addActionListener(tbListener);
	                    MainWindowView.debugMgrInstance.getBtnAddABreakpoint().addActionListener(ab_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnRemoveSelected().addActionListener(db_debug_Listener);
	                    MainWindowView.debugMgrInstance.getBtnRemoveAll().addActionListener(dab_debug_Listener);
	                    MainWindowView.debugMgrInstance.getWatchList().clear();
	                    MainWindowView.debugMgrInstance.getWatchList2().clear();
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
		
		public boolean checkIfResourceExists()
		{
		  boolean exists = false;
		  
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
		  
		  return exists;
		}
		
		public void delayMe(long ms)
		{
			long nowTime = System.currentTimeMillis();
			while(System.currentTimeMillis() < nowTime + ms) ;
		}
	}