package view;


import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.rsta.ac.java.rjc.parser.Main;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;

import controller.EventController;
import model.Feedback;
import service.ClientService;
import service.Parsers;

import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ScrollPaneConstants;

import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.awt.event.InputEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Contains the PDE-C Client window.
 * <p>
 *  This is the frame that appears after the student has logged in.
 * </p>
 * 
 * @author Alexander John D. Jose
 * @author In Yong S. Lee
 * @author Lorenzo Miguel G. Monzon
 */
public class MainWindowView
{

	private JFrame frame;
	private RSyntaxTextArea editorPane;
	private ArrayList<Integer> breakpoints;
	/**
	 * Information about the breakpoints.
	 */
	public static ArrayList<GutterIconInfo> breakpoints2;
	private Path filePath;
	private Path feedbackFilePath;
	private boolean fileModified;
	private final String appName = "PDE-C";
	private String fileName;
	/**
	 * Contains the Error Log.
	 */
	public static JTextArea errorLog;
	/**
	 * Contains the Debug Log.
	 */
	public static JTextArea debugLog;
	/**
	 * Contains the Feedback Log.
	 */
	public static JTextArea feedbackLog;
	private JMenuItem addBreakItem, delBreakItem, delallBreakItem;
	private JButton breakpointButton, delbreakpointButton, delallbreakpointButton;
	
	private static String studentIdNum;
	
	private ArrayList<String> codeHistory = new ArrayList<String>();
	
	// for api purposes
	
	private JToolBar coreToolbar;
	private JMenuBar menuBar;
	private JSplitPane horizontalPane;
	private JSplitPane verticalPane;
	private JTabbedPane tabbedHorizontalPane;
	private JTabbedPane tabbedVerticalPane;
	private RTextScrollPane scrollPane;
	private Gutter gut;
	private FeedbackHistory feedbackHistory;
	private static JButton sendButton;
	
	// modifiers for global
	private JButton newButton, openButton, saveButton, compileButton, compilerunButton, debugButton, stepOverButton, resumeButton, stopButton;
	private JMenuItem newFileItem, openFileItem, saveFileItem, saveAsFileItem, compileBuildItem, debugBuildItem, toggleBreakItem;
	
	private int fontSize = 16;
	private int minFont = 12;
	private int maxFont = 72;
	private String fontStyle;
	private static MainWindowView instance = null;
	/**
	 * Contains the <code>DebuggingManager</code> instance.
	 */
	public static DebuggingManager debugMgrInstance = null;
	/**
	 * Contains the <code>EventController</code> instance.
	 */
	public static EventController eventController = null;

	private MainWindowView()
	{
	  	filePath = null;
		initialize();
	}
	
	/**
	 * Gets the instance of Main Window View, if it exists. Otherwise, it will create a new instance of the Main Window View.
	 * @return the <code>MainWindowView</code>'s instance
	 */
	public static MainWindowView getInstance()
	{
	  if (instance == null)
	  {
		instance = new MainWindowView();
	  }
	  
	  return instance;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		breakpoints = new ArrayList<Integer>();
		breakpoints2 = new ArrayList<GutterIconInfo>();
		fileModified = false;
		fileName = "new file";
	  	feedbackFilePath = null;
	  	
		tabbedVerticalPane = new JTabbedPane();
		feedbackHistory = new FeedbackHistory();
		studentIdNum = "1";
			
		try
		{
		  if (Files.exists(Paths.get("resources/activity.txt")))
		  {
			Files.delete(Paths.get("resources/activity.txt"));
		  }
		}
		
		catch(Exception ex)
		{
		  ex.printStackTrace();
		}
		
		
		frame = new JFrame(appName + " - new file");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) 
			{
			  int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Are you sure you want to exit the program?", "",
			        JOptionPane.YES_NO_OPTION);

			      if (confirmed == JOptionPane.YES_OPTION) 
			      {
			    	System.exit(0);
			      }
			}
		});
		frame.setBounds(100, 100, 650, 425);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		URL pdecIcon = Main.class.getResource("/PDECICON.png");
		frame.setIconImage(new ImageIcon(pdecIcon).getImage());
		
		final JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter cFilter = new FileNameExtensionFilter(
		     "C Source (*.c)", "c");
		fileChooser.setFileFilter(cFilter);
		
		eventController = EventController.getEventController();
        eventController.checkIfResourceExists();
		editorPane = new RSyntaxTextArea();
		fontStyle = editorPane.getFont().getFamily();
		editorPane.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				fileModified = true;
				if(fileModified)
				{
					frame.setTitle(appName + " - " + fileName + " *");
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				fileModified = true;
				if(fileModified)
				{
					frame.setTitle(appName + " - " + fileName + " *");
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
			}
		});
		Parsers p = new Parsers();
		
		editorPane.addParser(p);

		editorPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
		editorPane.setCodeFoldingEnabled(true);
		editorPane.setFont(new Font(fontStyle, Font.PLAIN, fontSize));
		scrollPane = new RTextScrollPane(editorPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setIconRowHeaderEnabled(true);
		JComponent.setDefaultLocale(null);
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.revalidate();
		gut = scrollPane.getGutter();
		
		Font monospace = new Font(fontStyle, Font.PLAIN, fontSize);
		for(int i = 0; i < gut.getComponentCount(); i++)
		{
			gut.getComponent(i).setFont(monospace);
		}
		gut.setBookmarkingEnabled(true);
		
		coreToolbar = new JToolBar();
		coreToolbar.setFloatable(false);
		coreToolbar.setRollover(true);
		
		newButton = new JButton("");
		newButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if (editorPane.getText().equals(""))
				{
					editorPane.setText("");
					//eventController.deleteDontTouch();
				}
				
				else
				{
					int confirmed = JOptionPane.showConfirmDialog(null, "Create new file?", "", JOptionPane.YES_NO_OPTION);
				
					if (confirmed == JOptionPane.YES_OPTION) 
					{
						editorPane.setText("");
						//eventController.deleteDontTouch();
						feedbackFilePath = null;
						filePath = null;
						fileName = "new file";
						frame.setTitle(appName + " - " + fileName);
				    	eventController.quietlydeleteallbreakpoint(gut, breakpoints);
				    	errorLog.setText("");
						feedbackHistory.getContainer().removeAll();
						feedbackHistory.getFeedback().clear();
						feedbackHistory.updateUI();
					}
				}
			}
		});
		newButton.setToolTipText("New");
		URL newfile = Main.class.getResource("/newFile.png");
		newButton.setIcon(new ImageIcon(newfile));
		newButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		openButton = new JButton("");
		openButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			  {
				int confirmed = JOptionPane.showConfirmDialog(null, "Open new file?", "", JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) 
			    {
			      filePath = eventController.openFile(frame, editorPane);
			
			      if (filePath != null)
			      {
			    	  
					  errorLog.setText("");
			    	  //Open Feedback File/////////////////////
				      feedbackFilePath = eventController.getFeedbackFile(filePath);
				      feedbackHistory.getContainer().removeAll();
				      feedbackHistory.getFeedback().clear();
					  feedbackHistory.readFile(feedbackFilePath, editorPane);
					  feedbackHistory.updateUI();
					  /////////////////////////////////////////;
			    	  fileName = filePath.getFileName().toString();
			    	  eventController.quietlydeleteallbreakpoint(gut, breakpoints);
			      }
			    }
			  }
		});
		URL openfile = Main.class.getResource("/openfile.png");
		openButton.setIcon(new ImageIcon(openfile));
		openButton.setToolTipText("Open");
		openButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		saveButton = new JButton("");
		saveButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
			  if (filePath != null)
			  {
				eventController.saveFile(frame, editorPane, filePath, fileModified);
				frame.setTitle(appName + " - " + fileName);
				fileModified = false;
			  }
			  
			  else
			  {
				filePath = eventController.saveAsFile(frame, editorPane, fileModified);
				fileName = filePath.getFileName().toString();
				frame.setTitle(appName + " - " + fileName);
				fileModified = false;
			  }	  
			}
		});
		saveButton.setBorder(null);
		
	    errorLog = new JTextArea (5,20);
	    debugLog = new JTextArea (5,20);
	    errorLog.setEditable (false); // set textArea non-editable
	    debugLog.setEditable(false);
	    JScrollPane cL = new JScrollPane (errorLog);
	    JScrollPane dL = new JScrollPane (debugLog);
		frame.setVisible(true);
		
		JButton recoverCode = new JButton("");
		recoverCode.setText("Recover Code");
		recoverCode.setToolTipText("Recovers Code Based on Selected Compile History");
		recoverCode.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		recoverCode.setEnabled(false);
		
		JButton removeHistory = new JButton("");
		removeHistory.setText("Remove History");
		removeHistory.setToolTipText("Removes Selected Compilation History");
		removeHistory.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		removeHistory.setEnabled(false);
		
		JButton clearHistory = new JButton("");
		clearHistory.setText("Clear History");
		clearHistory.setToolTipText("Clears all Compilation History");
		clearHistory.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		clearHistory.setEnabled(false);
		
		
		compileButton = new JButton("");
		compileButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
			  
			  if (filePath != null)
			  {
				filePath = eventController.compile(frame, editorPane, filePath, errorLog); 
				JOptionPane.showMessageDialog(null, "Compilation complete", "Compile Code", JOptionPane.PLAIN_MESSAGE);
			  }
			  
			  else if (editorPane.getText().trim().equals(("")))
		      {
				filePath = eventController.compile(frame, editorPane, filePath, errorLog); 
				
				if (filePath != null)
				{
				  JOptionPane.showMessageDialog(null, "Compilation complete", "Compile Code", JOptionPane.PLAIN_MESSAGE);
				  fileName = filePath.getFileName().toString();
				  frame.setTitle(appName + " - " + fileName);
				  fileModified = false;
				}
			  }
			  
			  else
			  {
				filePath = eventController.saveAsFile(frame, editorPane, fileModified);
				fileName = filePath.getFileName().toString();
				frame.setTitle(appName + " - " + fileName);
				fileModified = false;
			  }
			  	
			}
		});
		
//		compileButton.setVisible(false);
		
		compilerunButton = new JButton("");
		compilerunButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				
				if (filePath != null)
				  {
					eventController.saveFile(frame, editorPane, filePath, fileModified);
					frame.setTitle(appName + " - " + fileName);
					fileModified = false;
				  }
				
				else if (editorPane.getText().trim().equals(""))
				{
					  filePath = eventController.compile(frame, editorPane, filePath, errorLog);
					  fileModified = true;
					  fileName = filePath.getFileName().toString();
					  frame.setTitle(appName + " - " + fileName);
				}
				  
				  else
				  {
					filePath = eventController.saveAsFile(frame, editorPane, fileModified);
					fileName = filePath.getFileName().toString();
					frame.setTitle(appName + " - " + fileName);
					fileModified = false;
				  }	  
				
				filePath = eventController.compile(frame, editorPane, filePath, errorLog);
				
				///////////////////////Feedback History Prototype////////////////
				Feedback feedback = new Feedback(errorLog.getText(), editorPane.getText());
				feedbackHistory.addFeedback(feedback, filePath, editorPane);
				feedbackHistory.updateUI();
				//Save Feedback File///////////////////////
				feedbackHistory.saveFile(feedbackHistory.getFeedback(), filePath);
				///////////////////////////////////////////
				//feedbackScroll.getVerticalScrollBar().setValue(feedbackScroll.getVerticalScrollBar().getMaximum());
				/////////////////////////////////////////////////////////////////
				
				if (errorLog.getText().trim().equals(""))
				{
				  eventController.runProgram(filePath);
				}
			}
		});
		
		sendButton = new JButton("Send C File");
		sendButton.setToolTipText("Send source code");
		URL send = Main.class.getResource("/send.png");
		sendButton.setIcon(new ImageIcon(send));
		sendButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		sendButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
			  eventController.checkIfResourceExists();
				eventController.sendSrcCode(errorLog, filePath);
			}
		});
		
		if (studentIdNum.equals("0"))
		{
		  sendButton.setVisible(false);
		}
		
		
		JButton downloadButton = new JButton("Download");
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
			  eventController.checkIfResourceExists();
			  eventController.downloadActivity();
			}
		});
		
		URL download = Main.class.getResource("/download.png");
		downloadButton.setToolTipText("Download Activities");
		downloadButton.setIcon(new ImageIcon(download));
		downloadButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		URL save = Main.class.getResource("/save.png");
		saveButton.setIcon(new ImageIcon(save));
		saveButton.setToolTipText("Save");
		saveButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		URL compile = Main.class.getResource("/compile.png");
		compileButton.setIcon(new ImageIcon(compile));
		compileButton.setToolTipText("Compile");
		compileButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		URL compileandrun = Main.class.getResource("/compileandrun.png");
		compilerunButton.setIcon(new ImageIcon(compileandrun));
		compilerunButton.setToolTipText("Compile and Run");
		compilerunButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		debugButton = new JButton("");
		URL debug = Main.class.getResource("/debug.png");
		debugButton.setIcon(new ImageIcon(debug));
		debugButton.setToolTipText("Debug");
		debugButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		breakpointButton = new JButton("");
		URL breakpoint = Main.class.getResource("/breakpoint.png");
		breakpointButton.setIcon(new ImageIcon(breakpoint));
		breakpointButton.setToolTipText("Add Breakpoints");
		breakpointButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			
		delbreakpointButton = new JButton("");
		URL delbreakpoint = Main.class.getResource("/delbreakpoint.png");
		delbreakpointButton.setIcon(new ImageIcon(delbreakpoint));

		
		delbreakpointButton.setToolTipText("Delete Breakpoints");
		delbreakpointButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		delbreakpointButton.setEnabled(false);
		
		delallbreakpointButton = new JButton("");
		URL delallbreakpoint = Main.class.getResource("/delallbreakpoint.png");
		delallbreakpointButton.setIcon(new ImageIcon(delallbreakpoint));
		delallbreakpointButton.setToolTipText("Delete All Breakpoints");
		delallbreakpointButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		delallbreakpointButton.setEnabled(false);
		
		breakpointButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				eventController.addbreakpoint(frame, gut, breakpoints);
				if(debugMgrInstance == null);
				else
				{
					debugMgrInstance.modifyBreakpoints();
					if(breakpoints.size() > 0) {
						debugMgrInstance.getBtnRemoveSelected().setEnabled(true);
						debugMgrInstance.getBtnRemoveAll().setEnabled(true);
					}
				}
				if(breakpoints.size() > 0) {
					delbreakpointButton.setEnabled(true);
					delallbreakpointButton.setEnabled(true);
				}
			}
		});
		
		delbreakpointButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				eventController.deletebreakpoint(frame, gut, breakpoints);
				if(debugMgrInstance == null);
				else
				{
					debugMgrInstance.modifyBreakpoints();
					if(breakpoints.size() == 0) {
						debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
						debugMgrInstance.getBtnRemoveAll().setEnabled(false);
					}
				}
				if(breakpoints.size() == 0) {
					delbreakpointButton.setEnabled(false);
					delallbreakpointButton.setEnabled(false);
				}
			}
		});
		
		delallbreakpointButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				eventController.deleteallbreakpoint(frame, gut, breakpoints);
				if(debugMgrInstance == null);
				else
				{
					debugMgrInstance.modifyBreakpoints();
					debugMgrInstance.getBtnRemoveSelected().setEnabled(true);
					debugMgrInstance.getBtnRemoveAll().setEnabled(true);
				}
				delbreakpointButton.setEnabled(false);
				delallbreakpointButton.setEnabled(false);
			}
		});
		
		stepOverButton = new JButton("");
		URL stepOver = Main.class.getResource("/stepOver.png");
		stepOverButton.setIcon(new ImageIcon(stepOver));
		stepOverButton.setToolTipText("Step Over");
		stepOverButton.setEnabled(false);
		stepOverButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		resumeButton = new JButton("");
		URL resume = Main.class.getResource("/resume.png");
		resumeButton.setIcon(new ImageIcon(resume));
		resumeButton.setToolTipText("Resume");
		resumeButton.setEnabled(false);
		resumeButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		stopButton = new JButton("");
		URL stop = Main.class.getResource("/stop.png");
		stopButton.setIcon(new ImageIcon(stop));
		stopButton.setToolTipText("Stop Debugging");
		stopButton.setEnabled(false);
		stopButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton fontUpButton = new JButton("");
		URL fontUp = Main.class.getResource("/fontUp.png");
		fontUpButton.setIcon(new ImageIcon(fontUp));
		fontUpButton.setToolTipText("Increase Font Size");
		fontUpButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				
		JButton fontDownButton = new JButton("");
		URL fontDown = Main.class.getResource("/fontDown.png");
		fontDownButton.setIcon(new ImageIcon(fontDown));
		fontDownButton.setToolTipText("Decrease Font Size");
		fontDownButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		fontUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fontSize < maxFont) {
					Font f = new Font(fontStyle, Font.PLAIN, fontSize+=4);
					scrollPane.setFont(f);
					for(int i = 0; i < gut.getComponentCount(); i++)
					{
						gut.getComponent(i).setFont(f);
					}
					editorPane.setFont(f);
					if (fontSize == maxFont) {
						fontUpButton.setEnabled(false);
					}
				}
				fontDownButton.setEnabled(true);
			}
		});
		
		fontDownButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fontSize > minFont) {
					Font f = new Font(fontStyle, Font.PLAIN, fontSize-=4);
					scrollPane.setFont(f);
					for(int i = 0; i < gut.getComponentCount(); i++)
					{
						gut.getComponent(i).setFont(f);
					}
					editorPane.setFont(f);
					if (fontSize == minFont) {
						fontDownButton.setEnabled(false);
					}
				}
				fontUpButton.setEnabled(true);
			}
		});
		
		recoverCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				int confirmed = JOptionPane.showConfirmDialog(null, "Overwrite current code in editor?", "Recover Code", JOptionPane.YES_NO_OPTION);
			    if (confirmed == JOptionPane.YES_OPTION) 
			    {
			    	editorPane.setText(codeHistory.get(tabbedVerticalPane.getSelectedIndex()));
			    }
			}
		});
		
		removeHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the selected History?", "Remove Code History", JOptionPane.YES_NO_OPTION);
			    if (confirmed == JOptionPane.YES_OPTION) 
			    {
					codeHistory.remove(tabbedVerticalPane.getSelectedIndex());
					tabbedVerticalPane.removeTabAt(tabbedVerticalPane.getSelectedIndex());
					if (tabbedVerticalPane.getTabCount() == 0)
					{
						recoverCode.setEnabled(false);
						removeHistory.setEnabled(false);
						clearHistory.setEnabled(false);
					}
			    }		
			}
		});
		
		clearHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirmed = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove all History?", "Remove All Code History", JOptionPane.YES_NO_OPTION);
			    if (confirmed == JOptionPane.YES_OPTION) 
			    {
					codeHistory.clear();
					tabbedVerticalPane.removeAll();
					recoverCode.setEnabled(false);
					removeHistory.setEnabled(false);
					clearHistory.setEnabled(false);
			    }	
			}
		});
		
		coreToolbar.add(newButton);
		coreToolbar.add(openButton);
		coreToolbar.add(saveButton);
		coreToolbar.addSeparator();
		coreToolbar.add(compileButton);
		coreToolbar.add(compilerunButton);
		coreToolbar.addSeparator();
		coreToolbar.add(debugButton);
		coreToolbar.add(stepOverButton);
		coreToolbar.add(resumeButton);
		coreToolbar.add(stopButton);
		coreToolbar.addSeparator();
		coreToolbar.add(breakpointButton);
		coreToolbar.add(delbreakpointButton);
		coreToolbar.add(delallbreakpointButton);
		coreToolbar.addSeparator();
		coreToolbar.add(fontUpButton);
		coreToolbar.add(fontDownButton);
		coreToolbar.addSeparator();
		coreToolbar.add(sendButton);
		coreToolbar.addSeparator();
		coreToolbar.add(downloadButton);
		coreToolbar.addSeparator();
		
		/////////////////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////////
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		newFileItem = new JMenuItem("New", KeyEvent.VK_N);
		newFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{ 
			  if (editorPane.getText().equals(""))
				{
					editorPane.setText("");
					//eventController.deleteDontTouch();
				}
				
				else
				{
					int confirmed = JOptionPane.showConfirmDialog(null, "Create new file?", "", JOptionPane.YES_NO_OPTION);
				
					if (confirmed == JOptionPane.YES_OPTION) 
					{
						editorPane.setText("");
						//eventController.deleteDontTouch();
						feedbackFilePath = null;
						filePath = null;
						fileName = "new file";
						frame.setTitle(appName + " - " + fileName);
				    	eventController.quietlydeleteallbreakpoint(gut, breakpoints);
				    	errorLog.setText("");
						feedbackHistory.getContainer().removeAll();
						feedbackHistory.getFeedback().clear();
						feedbackHistory.updateUI();
					}
				}
			}
		});
		newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		openFileItem = new JMenuItem("Open");
		openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openFileItem.addActionListener(new ActionListener() 
		{
		  public void actionPerformed(ActionEvent e) 
		  {
			int confirmed = JOptionPane.showConfirmDialog(null, "Open new file?", "", JOptionPane.YES_NO_OPTION);

		    if (confirmed == JOptionPane.YES_OPTION) 
		    {
		      filePath = eventController.openFile(frame, editorPane);
		
		      if (filePath != null)
		      {
		    	  
				  errorLog.setText("");
		    	  //Open Feedback File/////////////////////
			      feedbackFilePath = eventController.getFeedbackFile(filePath);
			      feedbackHistory.getContainer().removeAll();
			      feedbackHistory.getFeedback().clear();
				  feedbackHistory.readFile(feedbackFilePath, editorPane);
				  feedbackHistory.updateUI();
				  /////////////////////////////////////////;
		    	  fileName = filePath.getFileName().toString();
		    	  eventController.quietlydeleteallbreakpoint(gut, breakpoints);
		      }
		    }
		  }
		});
		
		
		saveFileItem = new JMenuItem("Save");
		saveFileItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
//			  eventController.saveFile(frame, editorPane, filePath, fileModified);
//			  frame.setTitle(appName + " - " + fileName);
//			  fileModified = false;
			  
			  if (filePath != null)
			  {
				eventController.saveFile(frame, editorPane, filePath, fileModified);
				frame.setTitle(appName + " - " + fileName);
				fileModified = false;
			  }
			  
			  else
			  {
				filePath = eventController.saveAsFile(frame, editorPane, fileModified);
				if (filePath != null)
				{
					fileName = filePath.getFileName().toString();
					frame.setTitle(appName + " - " + fileName);
					fileModified = false;
				}
			  }	  
			}
		});
		
		saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		
		saveAsFileItem = new JMenuItem("Save As...");
		
		saveAsFileItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  filePath = eventController.saveAsFile(frame, editorPane, fileModified);
			}
		});
		
		JMenuItem exitFileItem = new JMenuItem("Exit");
		exitFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
			  int confirmed = JOptionPane.showConfirmDialog(null, 
		        "Are you sure you want to exit the program?", "",
		        JOptionPane.YES_NO_OPTION);

		      if (confirmed == JOptionPane.YES_OPTION) 
		      {
		        frame.dispose();
		      }
			}
		});
		
		JMenu paths = new JMenu("Settings");
		
		JMenuItem settingsGccFileItem = new JMenuItem("GCC path");
		
		settingsGccFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{ 
			  eventController.changeSettingsGcc(frame);
			  eventController.savePathSettings();
			}
		});
		
		paths.add(settingsGccFileItem);
		
		JMenuItem settingsGdbFileItem = new JMenuItem("GDB path");
		
		settingsGdbFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{ 
			  eventController.changeSettingsGdb(frame);
			  eventController.savePathSettings();
			}
		});
		
		paths.add(settingsGdbFileItem);
		
		JMenuItem settingsIpFileItem = new JMenuItem("IP Address of server");
		
		settingsIpFileItem.addActionListener(new ActionListener() {
		  public void actionPerformed(ActionEvent e)
		  {
			eventController.changeIPSettings();
		  }
		});
		
		paths.add(settingsIpFileItem);
		exitFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		JMenuItem undoEditItem = new JMenuItem("Undo", KeyEvent.VK_U);
		undoEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		undoEditItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				editorPane.requestFocus();
				try
				{
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_Z);
					r.keyRelease(KeyEvent.VK_Z);
					r.keyRelease(KeyEvent.VK_CONTROL);
				}
				catch (Exception e)
				{
					
				}
			}
		});
		JMenuItem redoEditItem = new JMenuItem("Redo", KeyEvent.VK_R);
		redoEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		redoEditItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				editorPane.requestFocus();
				try
				{
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_Y);
					r.keyRelease(KeyEvent.VK_Y);
					r.keyRelease(KeyEvent.VK_CONTROL);
				}
				catch (Exception e)
				{
					
				}
			}
		});
		JMenuItem cutEditItem = new JMenuItem("Cut", KeyEvent.VK_T);
		cutEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		cutEditItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				editorPane.requestFocus();
				try
				{
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_X);
					r.keyRelease(KeyEvent.VK_X);
					r.keyRelease(KeyEvent.VK_CONTROL);
				}
				catch (Exception e)
				{
					
				}
			}
		});
		JMenuItem copyEditItem = new JMenuItem("Copy", KeyEvent.VK_C);
		copyEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copyEditItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				editorPane.requestFocus();
				try
				{
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_C);
					r.keyRelease(KeyEvent.VK_C);
					r.keyRelease(KeyEvent.VK_CONTROL);
				}
				catch (Exception e)
				{
					
				}
			}
		});
		JMenuItem pasteEditItem = new JMenuItem("Paste", KeyEvent.VK_P);
		pasteEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		pasteEditItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				editorPane.requestFocus();
				try
				{
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_V);
					r.keyRelease(KeyEvent.VK_V);
					r.keyRelease(KeyEvent.VK_CONTROL);
				}
				catch (Exception e)
				{
					
				}
			}
		});
		JMenuItem selectAllEditItem = new JMenuItem("Select All", KeyEvent.VK_A);
		selectAllEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		selectAllEditItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				editorPane.requestFocus();
				try
				{
					Robot r = new Robot();
					r.keyPress(KeyEvent.VK_CONTROL);
					r.keyPress(KeyEvent.VK_A);
					r.keyRelease(KeyEvent.VK_A);
					r.keyRelease(KeyEvent.VK_CONTROL);
				}
				catch (Exception e)
				{
					
				}
			}
		});
		JMenu buildMenu = new JMenu("Build");
		buildMenu.setMnemonic(KeyEvent.VK_B);
		
		compileBuildItem = new JMenuItem("Compile");
		
		compileBuildItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  if (filePath != null)
			  {
				filePath = eventController.compile(frame, editorPane, filePath, errorLog); 
				JOptionPane.showMessageDialog(null, "Compilation complete", "Compile Code", JOptionPane.PLAIN_MESSAGE);
			  }
			  
			  else if (editorPane.getText().trim().equals(("")))
		      {
				filePath = eventController.compile(frame, editorPane, filePath, errorLog); 
				
				if (filePath != null)
				{
				  JOptionPane.showMessageDialog(null, "Compilation complete", "Compile Code", JOptionPane.PLAIN_MESSAGE);
				  fileName = filePath.getFileName().toString();
				  frame.setTitle(appName + " - " + fileName);
				  fileModified = false;
				}
			  }
			  
			  else
			  {
				filePath = eventController.saveAsFile(frame, editorPane, fileModified);
				fileName = filePath.getFileName().toString();
				frame.setTitle(appName + " - " + fileName);
				fileModified = false;
			  }
			  	
			}
		});
		
		compileBuildItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		
		debugBuildItem = new JMenuItem("Debug", KeyEvent.VK_D);
		
		debugBuildItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				debugMgrInstance = DebuggingManager.getInstance();
				debugMgrInstance.openMe();
				debugMgrInstance.modifyBreakpoints();
			}
		});
		
		debugButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				debugMgrInstance = DebuggingManager.getInstance();
				debugMgrInstance.openMe();
				debugMgrInstance.modifyBreakpoints();
			}
		});
		
		debugBuildItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0));
		
		addBreakItem = new JMenuItem("Add Breakpoint...");
		addBreakItem.setEnabled(false);
		
		addBreakItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				eventController.addbreakpoint(frame, gut, breakpoints);	
				if(breakpoints.size() > 0) {
					delbreakpointButton.setEnabled(true);
					delallbreakpointButton.setEnabled(true);
					if(debugMgrInstance == null);
					else
					{
						debugMgrInstance.getBtnRemoveSelected().setEnabled(true);
						debugMgrInstance.getBtnRemoveAll().setEnabled(true);
					}
				}
			}
		});
		delBreakItem = new JMenuItem("Remove Breakpoint...");
		delBreakItem.setEnabled(false);
		delBreakItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				eventController.deletebreakpoint(frame, gut, breakpoints);
				if(breakpoints.size() == 0) {
					delbreakpointButton.setEnabled(false);
					delallbreakpointButton.setEnabled(false);
					if(debugMgrInstance == null);
					else
					{
						debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
						debugMgrInstance.getBtnRemoveAll().setEnabled(false);
					}
				}
			}
		});
		delallBreakItem = new JMenuItem("Remove all Breakpoints...");
		delallBreakItem.setEnabled(false);
		delallBreakItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				eventController.deleteallbreakpoint(frame, gut, breakpoints);
				if(breakpoints.size() == 0) {
					delbreakpointButton.setEnabled(false);
					delallbreakpointButton.setEnabled(false);
					if(debugMgrInstance == null);
					else
					{
						debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
						debugMgrInstance.getBtnRemoveAll().setEnabled(false);
					}
				}
			}
		});
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem helpHelpItem = new JMenuItem("Help Contents", KeyEvent.VK_H);
		helpHelpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		helpHelpItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
				    try {
				        File myFile = new File("resources/Help.pdf");
				        Desktop.getDesktop().open(myFile);
				    } catch (IOException ioe) {
						JOptionPane.showMessageDialog(null, "Nothing to open here.", "", JOptionPane.INFORMATION_MESSAGE);
				    } catch (Exception ex) {
				    	
				    }
				}
			}
		});
		JMenuItem aboutHelpItem = new JMenuItem("About this System", KeyEvent.VK_A);
		JDialog aboutDialog = new JDialog(frame, "About PDE-C");
		aboutDialog.getContentPane().setLayout(null);
		aboutHelpItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(aboutDialog.isVisible())
				{
					aboutDialog.requestFocus();
				}
				else
				{
					aboutDialog.setSize(450, 240);
					aboutDialog.setLocationRelativeTo(null);
					aboutDialog.setResizable(false);
					aboutDialog.setVisible(true);

					JLabel lblPdec = new JLabel("PDE-C Version 1.0\r");
					lblPdec.setHorizontalAlignment(SwingConstants.LEFT);
					lblPdec.setBounds(148, 11, 286, 14);
					aboutDialog.add(lblPdec);
					
					JLabel lblAllRightsReserved = new JLabel("All rights reserved.");
					lblAllRightsReserved.setHorizontalAlignment(SwingConstants.LEFT);
					lblAllRightsReserved.setBounds(148, 36, 286, 14);
					aboutDialog.add(lblAllRightsReserved);
					
					JLabel lblThirdPartyUsed = new JLabel("Third party used by the proponents are the property of their respective owners.");
					lblThirdPartyUsed.setHorizontalAlignment(SwingConstants.CENTER);
					lblThirdPartyUsed.setBounds(10, 150, 414, 14);
					aboutDialog.add(lblThirdPartyUsed);
					
					JLabel lblIcon = new JLabel("");
					URL pdecicon = Main.class.getResource("/PDECICON.png");
					lblIcon.setIcon(new ImageIcon(pdecicon));
					lblIcon.setBounds(10, 11, 128, 128);
					aboutDialog.add(lblIcon);
					
					JButton btnLicenses = new JButton("Licenses");
					btnLicenses.setBounds(10, 175, 89, 23);
					JDialog licensesDialog = new JDialog(frame, "Licenses");
					btnLicenses.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							if(licensesDialog.isVisible())
							{
								licensesDialog.requestFocus();
							}
							else
							{
								JScrollPane scrollPane = new JScrollPane();
								licensesDialog.add(scrollPane, BorderLayout.CENTER);
								
								JTextArea txtrCopyrightc = new JTextArea();
								txtrCopyrightc.setFont(new Font("Consolas", Font.PLAIN, 13));
								txtrCopyrightc.setText("RSyntaxTextArea\r\n\r\nCopyright (c) 2012, Robert Futrell\r\nAll rights reserved.\r\n\r\nRedistribution and use in source and binary forms, with or without\r\nmodification, are permitted provided that the following conditions are met:\r\n    * Redistributions of source code must retain the above copyright\r\n      notice, this list of conditions and the following disclaimer.\r\n    * Redistributions in binary form must reproduce the above copyright\r\n      notice, this list of conditions and the following disclaimer in the\r\n      documentation and/or other materials provided with the distribution.\r\n    * Neither the name of the author nor the names of its contributors may\r\n      be used to endorse or promote products derived from this software\r\n      without specific prior written permission.\r\n\r\nTHIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS IS\" AND\r\nANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED\r\nWARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE\r\nDISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY\r\nDIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES\r\n(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;\r\nLOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND\r\nON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\r\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS\r\nSOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.");
								scrollPane.setViewportView(txtrCopyrightc);
								licensesDialog.setSize(640, 480);
								licensesDialog.setLocationRelativeTo(null);
								licensesDialog.setResizable(false);
								txtrCopyrightc.setEditable(false);
								licensesDialog.setVisible(true);
							}
						}
					});
					aboutDialog.add(btnLicenses);
				}
			}
		});
		menuBar.add(fileMenu);
		fileMenu.add(newFileItem);
		fileMenu.add(openFileItem);
		fileMenu.add(saveFileItem);
		fileMenu.add(saveAsFileItem);
		fileMenu.addSeparator();
		fileMenu.add(exitFileItem);
		menuBar.add(editMenu);
		editMenu.add(undoEditItem);
		editMenu.add(redoEditItem);
		editMenu.addSeparator();
		editMenu.add(cutEditItem);
		editMenu.add(copyEditItem);
		editMenu.add(pasteEditItem);
		editMenu.addSeparator();
		editMenu.add(selectAllEditItem);
		menuBar.add(buildMenu);
		buildMenu.add(compileBuildItem);
		menuBar.add(paths);
		
		JMenuItem mntmCompileRun = new JMenuItem("Compile & run");
		mntmCompileRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
		mntmCompileRun.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
			  if (filePath != null)
			  {
				eventController.saveFile(frame, editorPane, filePath, fileModified);
				frame.setTitle(appName + " - " + fileName);
				fileModified = false;
				filePath = eventController.compile(frame, editorPane, filePath, errorLog);
			  }
			
			else if (editorPane.getText().trim().equals(""))
			{
			  filePath = eventController.compile(frame, editorPane, filePath, errorLog);
			  fileModified = true;
			  fileName = filePath.getFileName().toString();
			  frame.setTitle(appName + " - " + fileName);
			}
			  
			  else
			  {
				filePath = eventController.saveAsFile(frame, editorPane, fileModified);
				fileName = filePath.getFileName().toString();
				frame.setTitle(appName + " - " + fileName);
				fileModified = false;
				filePath = eventController.compile(frame, editorPane, filePath, errorLog);
			  }	  
			
			
			
			///////////////////////Feedback History Prototype////////////////
			Feedback feedback = new Feedback(errorLog.getText(), editorPane.getText());
			feedbackHistory.addFeedback(feedback, filePath, editorPane);
			feedbackHistory.updateUI();
			//Save Feedback File///////////////////////
			feedbackHistory.saveFile(feedbackHistory.getFeedback(), filePath);
			///////////////////////////////////////////
			//feedbackScroll.getVerticalScrollBar().setValue(feedbackScroll.getVerticalScrollBar().getMaximum());
			/////////////////////////////////////////////////////////////////
			
			if (errorLog.getText().trim().equals(""))
			{
			  eventController.runProgram(filePath);
			}
			}
		});
		buildMenu.add(mntmCompileRun);
		buildMenu.add(debugBuildItem);
//		buildMenu.add(addBreakItem);
//		buildMenu.add(delBreakItem);
//		buildMenu.add(delallBreakItem);
		
		JMenu questMenu = new JMenu("QUEST");
		JMenuItem questLaunch = new JMenuItem("Launch QUEST");
		questLaunch.addActionListener(new ActionListener(){
			
			@Override
			
			public void actionPerformed(ActionEvent e){
				
				eventController.launchQuest();
			}
		});
		
		JMenuItem questAbout = new JMenuItem("About QUEST");
		JDialog questDialog = new JDialog(frame, "About QUEST");
		questDialog.getContentPane().setLayout(null);
		questAbout.addActionListener(new ActionListener(){
			
			
			@Override
			
			public void actionPerformed(ActionEvent e){
				
				
				if(questDialog.isVisible())
					questDialog.requestFocus();
				else{
					//setup about dialog for quest
					//JOptionPane.showMessageDialog("test!");
					questDialog.setSize(450, 240);
					questDialog.setLocationRelativeTo(null);
					questDialog.setResizable(false);
					questDialog.setVisible(true);
					
					JLabel lblQDec = new JLabel("QUEST for PDE-C Ver 1.0\r");
					lblQDec.setHorizontalAlignment(SwingConstants.LEFT);
					lblQDec.setBounds(148, 11, 286, 14);
					questDialog.add(lblQDec);
					
					JLabel lblQInf = new JLabel("A gamification plugin for PDE-C");
					lblQInf.setHorizontalAlignment(SwingConstants.LEFT);
					lblQInf.setBounds(148, 36, 286, 14);
					questDialog.add(lblQInf);
					
					JLabel lblQPUsed = new JLabel("PDE-C and its components are properties of its respective owners.");
					lblQPUsed.setHorizontalAlignment(SwingConstants.CENTER);
					lblQPUsed.setBounds(10, 150, 414, 14);
					questDialog.add(lblQPUsed);
					
				}
			}
		});
		
		JMenuItem questTerminate = new JMenuItem("Close QUEST");
		questTerminate.addActionListener(new ActionListener(){
			
			@Override
			
			public void actionPerformed(ActionEvent e){
				
				eventController.terminateQuest();
			}
			
		});
		
		questMenu.add(questLaunch);
		questMenu.add(questTerminate);
		questMenu.add(questAbout);
		
		
		
		toggleBreakItem = new JMenuItem("Toggle Breakpoint");
		toggleBreakItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lineNum = editorPane.getCaretLineNumber();
				if(!breakpoints.contains(lineNum))
				{
					eventController.silentAddBreakpoint(gut, breakpoints, lineNum + 1);
				}
				else
				{
					eventController.silentDeleteBreakpoint(gut, breakpoints, lineNum + 1);
				}
				if(debugMgrInstance == null);
				else
				{
					debugMgrInstance.modifyBreakpoints();
				}
				if(breakpoints.size() > 0) {
					delbreakpointButton.setEnabled(true);
					delallbreakpointButton.setEnabled(true);
					if(debugMgrInstance == null);
					else
					{
						debugMgrInstance.getBtnRemoveSelected().setEnabled(true);
						debugMgrInstance.getBtnRemoveAll().setEnabled(true);
					}
				}
				else
				{
					delbreakpointButton.setEnabled(false);
					delallbreakpointButton.setEnabled(false);
					if(debugMgrInstance == null);
					else
					{
						debugMgrInstance.getBtnRemoveSelected().setEnabled(false);
						debugMgrInstance.getBtnRemoveAll().setEnabled(false);
					}
				}
			}
		});
		toggleBreakItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, InputEvent.CTRL_MASK));
		buildMenu.add(toggleBreakItem);
		menuBar.add(helpMenu);
		helpMenu.add(helpHelpItem);
		helpMenu.add(aboutHelpItem);
		
		menuBar.add(questMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(new BorderLayout());
		//frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		///////////////////////////////////////////////////////////////////LOGS
		frame.setVisible(true);
		horizontalPane = new JSplitPane();
		horizontalPane.setOrientation(JSplitPane.VERTICAL_SPLIT);		
		horizontalPane.setDividerLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.7));
		frame.getContentPane().add(horizontalPane, BorderLayout.CENTER);
		horizontalPane.setOneTouchExpandable(true);	
		
		verticalPane = new JSplitPane();
		verticalPane.setDividerLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1.5));
		verticalPane.setLeftComponent(scrollPane);
		verticalPane.setRightComponent(tabbedVerticalPane);
		verticalPane.setOneTouchExpandable(true);	
		
		horizontalPane.setTopComponent(verticalPane);
		
		tabbedHorizontalPane = new JTabbedPane();
		tabbedHorizontalPane.add("Error Log", cL);
		URL errorlogIcon = Main.class.getResource("/errorlog.png");
		tabbedHorizontalPane.setIconAt(0, new ImageIcon(errorlogIcon) );
		
		tabbedHorizontalPane.add("Debug Log", dL);
		tabbedHorizontalPane.setIconAt(1, new ImageIcon(debug) );
		
		tabbedVerticalPane.addTab("Feedback History", new JScrollPane(feedbackHistory));
		URL feedbacklogIcon = Main.class.getResource("/feedbacklog.png");
		tabbedVerticalPane.setIconAt(0, new ImageIcon(feedbacklogIcon) );
		
		horizontalPane.setBottomComponent(tabbedHorizontalPane);
		horizontalPane.setResizeWeight(1);
	
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, coreToolbar, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, coreToolbar, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, coreToolbar, 48, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, coreToolbar, 2500, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(coreToolbar, BorderLayout.NORTH);


	}

	/**
	 * Gets the <code>horizontalPane</code> property, which contains the Horizontal Pane component.
	 * @return the Horizontal Pane component.
	 */
	public JSplitPane getHorizontalPane()
	{
		return horizontalPane;
	}

	/**
	 * Gets the <code>verticalPane</code> property, which contains the Vertical Pane component.
	 * @return the Vertical Pane component.
	 */
	public JSplitPane getVerticalPane()
	{
		return verticalPane;
	}

	/**
	 * Gets the <code>tabbedHorizontalPane</code> property, which contains the Tabbed Horizontal Pane component.
	 * @return the Tabbed Horizontal Pane component.
	 */
	public JTabbedPane getTabbedHorizontalPane()
	{
		return tabbedHorizontalPane;
	}

	/**
	 * Gets the <code>tabbedVerticalPane</code> property, which contains the Tabbed Vertical Pane component.
	 * @return the Tabbed Vertical Pane component.
	 */
	public JTabbedPane getTabbedVerticalPane()
	{
		return tabbedVerticalPane;
	}

	/**
	 * Gets the <code>coreToolbar</code> property, which contains the Core Tool Bar for PDE-C.
	 * @return the Core Tool Bar for PDE-C.
	 */
	public JToolBar getCoreToolbar()
	{
	  return coreToolbar;
	}

	/**
	 * Gets the <code>menuBar</code> property, which contains the Menu Bar for PDE-C.
	 * @return the Menu Bar for PDE-C.
	 */
	public JMenuBar getMenuBar()
	{
	  return menuBar;
	}

	/**
	 * Gets the <code>errorLog</code> property, which contains the Error Log.
	 * @return the Error Log
	 */
	public JTextArea getErrorLog()
	{
	  return errorLog;
	}

	/**
	 * Gets the <code>feedbackLog</code> property, which contains the Feedback Log.
	 * @return the Feedback Log
	 */
	public JTextArea getfeedbackLog()
	{
	  return feedbackLog;
	}

	/**
	 * Gets the <code>frame</code> property, which contains the PDE-C Window.
	 * @return the PDE-C Window
	 */
	public JFrame getMainFrame()
	{
	  return frame;
	}

	/**
	 * Gets the <code>editorPane</code> property, which contains the editor itself.
	 * @return the Editor Pane
	 */
	public RSyntaxTextArea getEditor()
	{
	  return editorPane;
	}

	/**
	 * Gets the <code>studentIdNum</code> property, which contains the Student ID Number.
	 * @return the Student ID Number
	 */
	public String getStudentIdNum()
	{
	  return studentIdNum;
	}

	/**
	 * Sets the <code>studentIdNum</code> to its preferred value.
	 * @param studentIdNum the studentIdNum to set
	 */
	public void setStudentIdNum(String studentIdNum)
	{
	  this.studentIdNum = studentIdNum;
	}
	
	/**
	 * Check if the C Source Code is sendable given the Student ID Number.
	 * <p>
	 *  The sending feature will be disabled given that the student did not provide his/her ID Number.
	 * </p>
	 */
	public void checkIfSendable()
	{
	  if (studentIdNum.equals("0"))
		{
		  sendButton.setVisible(false);
		}
	}

	/**
	 * Gets the <code>gut</code> property, which contains the gutter for <code>RTextScrollPane</code>.
	 * @return the gut
	 */
	public Gutter getGut() {
		return gut;
	}

	/**
	 * Sets the <code>gut</code> to its preferred value.
	 * @param gut the gut to set
	 */
	public void setGut(Gutter gut) {
		this.gut = gut;
	}

	/**
	 * Gets the <code>scrollPane</code> property, which contains the <code>RSyntaxScrollPane</code>.
	 * @return the scrollPane
	 */
	public RTextScrollPane getScrollPane() {
		return scrollPane;
	}

	/**
	 * Sets the <code>scrollPane</code> to its preferred value.
	 * @param scrollPane the scrollPane to set
	 */
	public void setScrollPane(RTextScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	/**
	 * Gets the <code>breakpoints</code> property, which contains the list of breakpoints, in the form of <code>ArrayList&lt;Integer&gt;</code>.
	 * @return the breakpoints
	 */
	public ArrayList<Integer> getBreakpoints() {
		return breakpoints;
	}

	/**
	 * Sets the <code>breakpoints</code> to its preferred value.
	 * @param breakpoints the breakpoints to set
	 */
	public void setBreakpoints(ArrayList<Integer> breakpoints) {
		this.breakpoints = breakpoints;
	}

	/**
	 * Gets the <code>breakpointButton</code> property, which contains the button for Add A Breakpoint.
	 * @return the breakpointButton
	 */
	public JButton getBreakpointButton() {
		return breakpointButton;
	}

	/**
	 * Sets the <code>breakpointButton</code> to its preferred value.
	 * @param breakpointButton the breakpointButton to set
	 */
	public void setBreakpointButton(JButton breakpointButton) {
		this.breakpointButton = breakpointButton;
	}

	/**
	 * Gets the <code>delbreakpointButton</code> property, which contains the button for Remove A Breakpoint.
	 * @return the delbreakpointButton
	 */
	public JButton getDelbreakpointButton() {
		return delbreakpointButton;
	}

	/**
	 * Sets the <code>delbreakpointButton</code> to its preferred value.
	 * @param delbreakpointButton the delbreakpointButton to set
	 */
	public void setDelbreakpointButton(JButton delbreakpointButton) {
		this.delbreakpointButton = delbreakpointButton;
	}

	/**
	 * Gets the <code>delallbreakpointButton</code> property, which contains the button for Remove All Breakpoints.
	 * @return the delallbreakpointButton
	 */
	public JButton getDelallbreakpointButton() {
		return delallbreakpointButton;
	}

	/**
	 * Sets the <code>delallbreakpointButton</code> to its preferred value.
	 * @param delallbreakpointButton the delallbreakpointButton to set
	 */
	public void setDelallbreakpointButton(JButton delallbreakpointButton) {
		this.delallbreakpointButton = delallbreakpointButton;
	}

	/**
	 * Gets the <code>newButton</code> property, which contains the button for New File.
	 * @return the newButton
	 */
	public JButton getNewButton() {
		return newButton;
	}

	/**
	 * Sets the <code>newButton</code> to its preferred value.
	 * @param newButton the newButton to set
	 */
	public void setNewButton(JButton newButton) {
		this.newButton = newButton;
	}

	/**
	 * Gets the <code>openButton</code> property, which contains the button for Open File.
	 * @return the openButton
	 */
	public JButton getOpenButton() {
		return openButton;
	}

	/**
	 * Sets the <code>openButton</code> to its preferred value.
	 * @param openButton the openButton to set
	 */
	public void setOpenButton(JButton openButton) {
		this.openButton = openButton;
	}

	/**
	 * Gets the <code>saveButton</code> property, which contains the button for Save File.
	 * @return the saveButton
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * Sets the <code>saveButton</code> to its preferred value.
	 * @param saveButton the saveButton to set
	 */
	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	/**
	 * Gets the <code>compileButton</code> property, which contains the button for Compile.
	 * @return the compileButton
	 */
	public JButton getCompileButton() {
		return compileButton;
	}

	/**
	 * Sets the <code>compileButton</code> to its preferred value.
	 * @param compileButton the compileButton to set
	 */
	public void setCompileButton(JButton compileButton) {
		this.compileButton = compileButton;
	}

	/**
	 * Gets the <code>compilerunButton</code> property, which contains the button for Compile and Run.
	 * @return the compilerunButton
	 */
	public JButton getCompilerunButton() {
		return compilerunButton;
	}

	/**
	 * Sets the <code>compilerunButton</code> to its preferred value.
	 * @param compilerunButton the compilerunButton to set
	 */
	public void setCompilerunButton(JButton compilerunButton) {
		this.compilerunButton = compilerunButton;
	}

	/**
	 * Gets the <code>debugButton</code> property, which contains the button for Debug.
	 * @return the debugButton
	 */
	public JButton getDebugButton() {
		return debugButton;
	}

	/**
	 * Sets the <code>debugButton</code> to its preferred value.
	 * @param debugButton the debugButton to set
	 */
	public void setDebugButton(JButton debugButton) {
		this.debugButton = debugButton;
	}

	/**
	 * Gets the <code>stepOverButton</code> property, which contains the button for Step Over.
	 * @return the stepOverButton
	 */
	public JButton getStepOverButton() {
		return stepOverButton;
	}

	/**
	 * Sets the <code>stepOverButton</code> to its preferred value.
	 * @param stepOverButton the stepOverButton to set
	 */
	public void setStepOverButton(JButton stepOverButton) {
		this.stepOverButton = stepOverButton;
	}

	/**
	 * Gets the <code>resumeButton</code> property, which contains the button for Continue.
	 * @return the resumeButton
	 */
	public JButton getResumeButton() {
		return resumeButton;
	}

	/**
	 * Sets the <code>resumeButton</code> to its preferred value.
	 * @param resumeButton the resumeButton to set
	 */
	public void setResumeButton(JButton resumeButton) {
		this.resumeButton = resumeButton;
	}

	/**
	 * Gets the <code>stopButton</code> property, which contains the button for Stop.
	 * @return the stopButton
	 */
	public JButton getStopButton() {
		return stopButton;
	}

	/**
	 * Sets the <code>stopButton</code> to its preferred value.
	 * @param stopButton the stopButton to set
	 */
	public void setStopButton(JButton stopButton) {
		this.stopButton = stopButton;
	}

	/**
	 * Gets the <code>newFileItem</code> property, which contains the menu item for New File.
	 * @return the newFileItem
	 */
	public JMenuItem getNewFileItem() {
		return newFileItem;
	}

	/**
	 * Sets the <code>newFileItem</code> to its preferred value.
	 * @param newFileItem the newFileItem to set
	 */
	public void setNewFileItem(JMenuItem newFileItem) {
		this.newFileItem = newFileItem;
	}

	/**
	 * Gets the <code>openFileItem</code> property, which contains the menu item for Open File.
	 * @return the openFileItem
	 */
	public JMenuItem getOpenFileItem() {
		return openFileItem;
	}

	/**
	 * Sets the <code>openFileItem</code> to its preferred value.
	 * @param openFileItem the openFileItem to set
	 */
	public void setOpenFileItem(JMenuItem openFileItem) {
		this.openFileItem = openFileItem;
	}

	/**
	 * Gets the <code>saveFileItem</code> property, which contains the menu item for Save File.
	 * @return the saveFileItem
	 */
	public JMenuItem getSaveFileItem() {
		return saveFileItem;
	}

	/**
	 * Sets the <code>saveFileItem</code> to its preferred value.
	 * @param saveFileItem the saveFileItem to set
	 */
	public void setSaveFileItem(JMenuItem saveFileItem) {
		this.saveFileItem = saveFileItem;
	}

	/**
	 * Gets the <code>saveAsFileItem</code> property, which contains the menu item for Save As.
	 * @return the saveAsFileItem
	 */
	public JMenuItem getSaveAsFileItem() {
		return saveAsFileItem;
	}

	/**
	 * Sets the <code>saveAsFileItem</code> to its preferred value.
	 * @param saveAsFileItem the saveAsFileItem to set
	 */
	public void setSaveAsFileItem(JMenuItem saveAsFileItem) {
		this.saveAsFileItem = saveAsFileItem;
	}

	/**
	 * Gets the <code>compileBuildItem</code> property, which contains the menu item for Compile.
	 * @return the compileBuildItem
	 */
	public JMenuItem getCompileBuildItem() {
		return compileBuildItem;
	}

	/**
	 * Sets the <code>compileBuildItem</code> to its preferred value.
	 * @param compileBuildItem the compileBuildItem to set
	 */
	public void setCompileBuildItem(JMenuItem compileBuildItem) {
		this.compileBuildItem = compileBuildItem;
	}

	/**
	 * Gets the <code>debugBuildItem</code> property, which contains the menu item for Debug.
	 * @return the debugBuildItem
	 */
	public JMenuItem getDebugBuildItem() {
		return debugBuildItem;
	}

	/**
	 * Sets the <code>debugBuildItem</code> to its preferred value.
	 * @param debugBuildItem the debugBuildItem to set
	 */
	public void setDebugBuildItem(JMenuItem debugBuildItem) {
		this.debugBuildItem = debugBuildItem;
	}

	/**
	 * Gets the <code>toggleBreakItem</code> property, which contains the menu item for Toggle Breakpoint.
	 * @return the toggleBreakItem
	 */
	public JMenuItem getToggleBreakItem() {
		return toggleBreakItem;
	}

	/**
	 * Sets the <code>toggleBreakItem</code> to its preferred value.
	 * @param toggleBreakItem the toggleBreakItem to set
	 */
	public void setToggleBreakItem(JMenuItem toggleBreakItem) {
		this.toggleBreakItem = toggleBreakItem;
	}

	/**
	 * Gets the <code>filePath</code> property, which contains the source code of the current file being edited.
	 * @return the source code of the current file being edited.
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Sets the <code>filePath</code> to its preferred value.
	 * @param filePath the filePath to set
	 */
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
	}
}
