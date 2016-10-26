package view;


import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
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

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;

import controller.EventController;
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


public class MainWindowView
{

	private JFrame frame;
	private ArrayList<Integer> breakpoints;
	public static ArrayList<GutterIconInfo> breakpoints2;
	private Path filePath;
	private boolean fileModified;
	private final String appName = "PDE-C";
	private String fileName;
	public static JTextArea errorLog;
	public static JTextArea feedbackLog;
	private JMenuItem addBreakItem, delBreakItem, delallBreakItem;
	private JButton breakpointButton, delbreakpointButton, delallbreakpointButton;
	
	// for api purposes
	
	private JToolBar coreToolbar;
	private JMenuBar menuBar;
	private JSplitPane horizontalPane;
	private JSplitPane verticalPane;
	private JTabbedPane tabbedHorizontalPane;
	private JTabbedPane tabbedVerticalPane;
	
	private int fontSize = 16;
	private int minFont = 12;
	private int maxFont = 72;
	private String fontStyle;
	private static MainWindowView instance = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindowView window = getInstance();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MainWindowView()
	{
	  	filePath = null;
		initialize();
	}
	
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
			        frame.dispose();
			      }
			}
		});
		frame.setBounds(100, 100, 650, 425);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		final JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter cFilter = new FileNameExtensionFilter(
		     "C Source (*.c)", "c");
		fileChooser.setFileFilter(cFilter);
		
		EventController eventController = EventController.getEventController();
        
		RSyntaxTextArea editorPane = new RSyntaxTextArea();
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
		RTextScrollPane scrollPane = new RTextScrollPane(editorPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setIconRowHeaderEnabled(true);
		JComponent.setDefaultLocale(null);
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.revalidate();
		Gutter gut = scrollPane.getGutter();
		Font monospace = new Font(fontStyle, Font.PLAIN, fontSize);
		for(int i = 0; i < gut.getComponentCount(); i++)
		{
			gut.getComponent(i).setFont(monospace);
		}
		gut.setBookmarkingEnabled(true);
		
		coreToolbar = new JToolBar();
		coreToolbar.setFloatable(false);
		coreToolbar.setRollover(true);
		JButton newButton = new JButton("");
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
					}
				}
				filePath = null;
				fileName = "new file";
				frame.setTitle(appName + " - " + fileName);
			}
		});
		newButton.setToolTipText("New");
		newButton.setIcon(new ImageIcon("resources/images/materialSmall/newfile.png"));
		newButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton openButton = new JButton("");
		openButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			  {
				int confirmed = JOptionPane.showConfirmDialog(null, "Open new file?", "", JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) 
			    {
			      filePath = eventController.openFile(frame, editorPane);
			      if (filePath != null)
			    	  fileName = filePath.getFileName().toString();
			    }
			  }
		});
		openButton.setIcon(new ImageIcon("resources/images/materialSmall/openfile.png"));
		openButton.setToolTipText("Open");
		openButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton saveButton = new JButton("");
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
	    errorLog.setEditable ( false ); // set textArea non-editable
	    JScrollPane cL = new JScrollPane ( errorLog );
		frame.setVisible(true);
		
		
		JButton compileButton = new JButton("");
		compileButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
			  filePath = eventController.compile(frame, editorPane, filePath, errorLog); 
			}
		});
		
		JButton compilerunButton = new JButton("");
		compilerunButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				filePath = eventController.compile(frame, editorPane, filePath, errorLog);
				eventController.runProgram(filePath);
			}
		});
		
		JButton sendButton = new JButton("Send C File");
		sendButton.setToolTipText("Send source code");
		sendButton.setIcon(new ImageIcon("resources/images/materialSmall/send.png"));
		sendButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		sendButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				eventController.sendSrcCode(errorLog, filePath);
			}
		});
		
		JButton downloadButton = new JButton("Download");
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
			  eventController.downloadActivity();
			}
		});
		downloadButton.setToolTipText("Download Activities");
		downloadButton.setIcon(new ImageIcon("resources/images/materialSmall/download.png"));
		downloadButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		saveButton.setIcon(new ImageIcon("resources/images/materialSmall/save.png"));
		saveButton.setToolTipText("Save");
		saveButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		compileButton.setIcon(new ImageIcon("resources/images/materialSmall/compile.png"));
		compileButton.setToolTipText("Compile");
		compileButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		compilerunButton.setIcon(new ImageIcon("resources/images/materialSmall/compileandrun.png"));
		compilerunButton.setToolTipText("Compile and Run");
		compilerunButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		JButton debugButton = new JButton("");
		debugButton.setIcon(new ImageIcon("resources/images/materialSmall/debug.png"));
		debugButton.setToolTipText("Debug");
		debugButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		breakpointButton = new JButton("");
		breakpointButton.setIcon(new ImageIcon("resources/images/materialSmall/breakpoint.png"));
		breakpointButton.setToolTipText("Add Breakpoints");
		breakpointButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			
		delbreakpointButton = new JButton("");
		delbreakpointButton.setIcon(new ImageIcon("resources/images/materialSmall/delbreakpoint.png"));
		delbreakpointButton.setToolTipText("Delete Breakpoints");
		delbreakpointButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		delbreakpointButton.setEnabled(false);
		
		delallbreakpointButton = new JButton("");
		delallbreakpointButton.setIcon(new ImageIcon("resources/images/materialSmall/delallbreakpoint.png"));
		delallbreakpointButton.setToolTipText("Delete All Breakpoints");
		delallbreakpointButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		delallbreakpointButton.setEnabled(false);
		
		breakpointButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				eventController.addbreakpoint(frame, gut, breakpoints);
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
				eventController.deleteallbreakpoint(gut, breakpoints);
				if(breakpoints.size() == 0) {
					delbreakpointButton.setEnabled(false);
					delallbreakpointButton.setEnabled(false);
				}
			}
		});
		
		JButton stepOverButton = new JButton("");
		stepOverButton.setIcon(new ImageIcon("resources/images/materialSmall/stepOver.png"));
		stepOverButton.setToolTipText("Step Over");
		stepOverButton.setEnabled(false);
		stepOverButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton resumeButton = new JButton("");
		resumeButton.setIcon(new ImageIcon("resources/images/materialSmall/resume.png"));
		resumeButton.setToolTipText("Resume");
		resumeButton.setEnabled(false);
		resumeButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton stopButton = new JButton("");
		stopButton.setIcon(new ImageIcon("resources/images/materialSmall/stop.png"));
		stopButton.setToolTipText("Stop Debugging");
		stopButton.setEnabled(false);
		stopButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton fontUpButton = new JButton("");
		fontUpButton.setIcon(new ImageIcon("resources/images/materialSmall/fontUp.png"));
		fontUpButton.setToolTipText("Increase Font Size");
		fontUpButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
				
		JButton fontDownButton = new JButton("");
		fontDownButton.setIcon(new ImageIcon("resources/images/materialSmall/fontDown.png"));
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
		
		coreToolbar.add(newButton);
		coreToolbar.add(openButton);
		coreToolbar.add(saveButton);
		coreToolbar.addSeparator();
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
		coreToolbar.addSeparator();
		coreToolbar.add(fontUpButton);
		coreToolbar.add(fontDownButton);
		coreToolbar.addSeparator();
		coreToolbar.addSeparator();
		coreToolbar.addSeparator();
		coreToolbar.add(sendButton);
		coreToolbar.addSeparator();
		coreToolbar.add(downloadButton);
		coreToolbar.addSeparator();
		
		
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem newFileItem = new JMenuItem("New", KeyEvent.VK_N);
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
					}
				}
				filePath = null;
				fileName = "new file";
				frame.setTitle(appName + " - " + fileName);
			  
			}
		});
		newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		JMenuItem openFileItem = new JMenuItem("Open");
		openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openFileItem.addActionListener(new ActionListener() 
		{
		  public void actionPerformed(ActionEvent e) 
		  {
			int confirmed = JOptionPane.showConfirmDialog(null, "Open new file?", "", JOptionPane.YES_NO_OPTION);

		    if (confirmed == JOptionPane.YES_OPTION) 
		    {
		      filePath = eventController.openFile(frame, editorPane);
			  fileName = filePath.getFileName().toString();
		    }
		  }
		});
		
		
		JMenuItem saveFileItem = new JMenuItem("Save");
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
		
		JMenuItem saveAsFileItem = new JMenuItem("Save As...");
		
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
		exitFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		JMenuItem undoEditItem = new JMenuItem("Undo", KeyEvent.VK_U);
		undoEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		JMenuItem redoEditItem = new JMenuItem("Redo", KeyEvent.VK_R);
		redoEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		JMenuItem cutEditItem = new JMenuItem("Cut", KeyEvent.VK_T);
		cutEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		JMenuItem copyEditItem = new JMenuItem("Copy", KeyEvent.VK_C);
		copyEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		JMenuItem pasteEditItem = new JMenuItem("Paste", KeyEvent.VK_P);
		pasteEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		JMenuItem findEditItem = new JMenuItem("Find...", KeyEvent.VK_F);
		findEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		JMenuItem selectAllEditItem = new JMenuItem("Select All", KeyEvent.VK_A);
		selectAllEditItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		JMenu buildMenu = new JMenu("Build");
		buildMenu.setMnemonic(KeyEvent.VK_B);
		
		JMenuItem compileBuildItem = new JMenuItem("Compile");
		
		compileBuildItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  filePath = eventController.compile(frame, editorPane, filePath, errorLog);
			}
		});
		
		compileBuildItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		
		JMenuItem debugBuildItem = new JMenuItem("Debug", KeyEvent.VK_D);
		
		debugBuildItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				eventController.debugToggler(frame, newButton, newFileItem, openButton, 
						openFileItem, saveButton, saveFileItem, saveAsFileItem, 
						compileButton, compilerunButton, compileBuildItem, debugButton, 
						debugBuildItem, stepOverButton, resumeButton, stopButton);
				eventController.debugActual2(frame, editorPane, filePath, newButton, 
						newFileItem, openButton, openFileItem, saveButton, saveFileItem, 
						saveAsFileItem, compileButton, compilerunButton, compileBuildItem, 
						debugButton, debugBuildItem, stepOverButton, resumeButton, 
						stopButton, editorPane, scrollPane, addBreakItem, delBreakItem, 
						delallBreakItem, breakpointButton, delbreakpointButton, delallbreakpointButton, breakpoints);
			}
		});
		
		debugButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				eventController.debugToggler(frame, newButton, newFileItem, openButton, 
						openFileItem, saveButton, saveFileItem, saveAsFileItem, 
						compileButton, compilerunButton, compileBuildItem, debugButton,
						debugBuildItem, stepOverButton, resumeButton, stopButton);
				eventController.debugActual2(frame, editorPane, filePath, newButton, 
						newFileItem, openButton, openFileItem, saveButton, saveFileItem, 
						saveAsFileItem, compileButton, compilerunButton, compileBuildItem, 
						debugButton, debugBuildItem, stepOverButton, resumeButton, 
						stopButton, editorPane, scrollPane, addBreakItem, delBreakItem, 
						delallBreakItem, breakpointButton, delbreakpointButton, delallbreakpointButton, breakpoints);
			}
		});
		
		debugBuildItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		
		addBreakItem = new JMenuItem("Add Breakpoint...");
		
		addBreakItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				eventController.addbreakpoint(frame, gut, breakpoints);	
				if(breakpoints.size() > 0) {
					delbreakpointButton.setEnabled(true);
					delallbreakpointButton.setEnabled(true);
				}
			}
		});
		delBreakItem = new JMenuItem("Remove Breakpoint...");
		delBreakItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				eventController.deletebreakpoint(frame, gut, breakpoints);
				if(breakpoints.size() == 0) {
					delbreakpointButton.setEnabled(false);
					delallbreakpointButton.setEnabled(false);
				}
			}
		});
		delallBreakItem = new JMenuItem("Remove all Breakpoint...");
		delallBreakItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				eventController.deleteallbreakpoint(gut, breakpoints);
				if(breakpoints.size() == 0) {
					delbreakpointButton.setEnabled(false);
					delallbreakpointButton.setEnabled(false);
				}
			}
		});
		
		
		JMenuItem manageBreakpointItem = new JMenuItem("Manage Breakpoints...");
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuItem helpHelpItem = new JMenuItem("Help Contents", KeyEvent.VK_H);
		helpHelpItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		JMenuItem aboutHelpItem = new JMenuItem("About this System", KeyEvent.VK_A);
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
		editMenu.add(findEditItem);
		editMenu.addSeparator();
		editMenu.add(selectAllEditItem);
		menuBar.add(buildMenu);
		buildMenu.add(compileBuildItem);
		
		JMenuItem mntmCompileRun = new JMenuItem("Compile & run");
		mntmCompileRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
		mntmCompileRun.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0)
			{
			  filePath = eventController.compile(frame, editorPane, filePath, errorLog);
			  eventController.runProgram(filePath);
			}
		});
		buildMenu.add(mntmCompileRun);
		buildMenu.add(debugBuildItem);
		buildMenu.add(addBreakItem);
		buildMenu.add(delBreakItem);
		buildMenu.add(delallBreakItem);
		buildMenu.add(manageBreakpointItem);
		menuBar.add(helpMenu);
		helpMenu.add(helpHelpItem);
		helpMenu.add(aboutHelpItem);
		
		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(new BorderLayout());
		//frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		///////////////////////////////////////////////////////////////////LOGS
		
	    feedbackLog = new JTextArea (1,30);
	    feedbackLog.setEditable ( false ); // set textArea non-editable
	    feedbackLog.setText("");
	    JScrollPane CBRC = new JScrollPane ( feedbackLog );
		//frame.getContentPane().add(CBRC, BorderLayout.EAST);
		frame.setVisible(true);
		
		tabbedVerticalPane = new JTabbedPane();
		tabbedVerticalPane.add("Feedback History", CBRC);
		
		horizontalPane = new JSplitPane();
		horizontalPane.setOrientation(JSplitPane.VERTICAL_SPLIT);		
		frame.getContentPane().add(horizontalPane, BorderLayout.CENTER);
		horizontalPane.setOneTouchExpandable(true);	
		
		//for editor text and cbrc
		verticalPane = new JSplitPane();
		verticalPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		verticalPane.setResizeWeight(1);
		verticalPane.setLeftComponent(scrollPane);
		verticalPane.setRightComponent(tabbedVerticalPane);
		verticalPane.setOneTouchExpandable(true);
		
		horizontalPane.setTopComponent(verticalPane);
		
		tabbedHorizontalPane = new JTabbedPane();
		tabbedHorizontalPane.add("Error Log", cL);
		//tabbedHorizontalPane.add("Test Log", cL);
		
		horizontalPane.setBottomComponent(tabbedHorizontalPane);
		horizontalPane.setResizeWeight(1);
	
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, coreToolbar, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, coreToolbar, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, coreToolbar, 48, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, coreToolbar, 2500, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(coreToolbar, BorderLayout.NORTH);


	}
	
	/*
	
	public void addbreakpoint(Gutter gut){
		String input = JOptionPane.showInputDialog(
                frame,
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
				for(int i = 0; i < breakpoints.size(); i++)
				{
					if(breakpoints.get(i) == bpnum)
					{
						existing = true;
					}
				}
				if(!existing)
				{
					GutterIconInfo gii = gut.addLineTrackingIcon(bpnum, new ImageIcon("resources/images/materialsmall/breakpointeditor.png"));
					breakpoints.add(bpnum);
					breakpoints2.add(gii);
					JOptionPane.showMessageDialog(null, "Line " + input + " added successfully.", "Added!", JOptionPane.INFORMATION_MESSAGE);
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
	}
	
	public void deletebreakpoint(Gutter gut){
		String input = JOptionPane.showInputDialog(
                frame,
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
				for(int i = 0; i < breakpoints.size(); i++)
				{
					if(breakpoints.get(i) == bpnum)
					{
						gii = breakpoints2.get(i);
						target = i;
					}
				}
				if(target == -1)
					JOptionPane.showMessageDialog(null, "Line " + input + " does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
				else
				{
					gut.removeTrackingIcon(gii);
					breakpoints.remove(target);
					breakpoints2.remove(target);
					JOptionPane.showMessageDialog(null, "Line " + input + " removed successfully.", "Removed", JOptionPane.INFORMATION_MESSAGE);
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
	}
	
	public void deleteallbreakpoint(Gutter gut){				
			gut.removeAllTrackingIcons();
			breakpoints.clear();
			breakpoints2.clear();
			JOptionPane.showMessageDialog(null, "All breakpoints removed successfully.", "Removed", JOptionPane.INFORMATION_MESSAGE);
	}
	*/
	
	public JSplitPane getHorizontalPane()
	{
		return horizontalPane;
	}
	
	public JSplitPane getVerticalPane()
	{
		return verticalPane;
	}
	
	public JTabbedPane getTabbedHorizontalPane()
	{
		return tabbedHorizontalPane;
	}
	
	public JTabbedPane getTabbedVerticalPane()
	{
		return tabbedVerticalPane;
	}
	
	public JToolBar getCoreToolbar()
	{
	  return coreToolbar;
	}

	public JMenuBar getMenuBar()
	{
	  return menuBar;
	}

	public JTextArea getErrorLog()
	{
	  return errorLog;
	}
	
	public JTextArea getfeedbackLog()
	{
	  return feedbackLog;
	}
}
