package view;


import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.GutterIconInfo;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;

import java.nio.file.Path;
import java.util.ArrayList;

import controller.CommandLineDebugging;
import controller.EventController;
// import model.Student;
import service.Parsers;
import service.ClientService;

import java.awt.event.KeyAdapter;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.ScrollPaneConstants;

public class MainWindowView
{

	private JFrame frame;
	private ArrayList<Integer> breakpoints;
	private ArrayList<GutterIconInfo> breakpoints2;
	private Path filePath;
	private boolean fileModified;
	private final String appName = "PDE-C";
	private String fileName;
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
					MainWindowView window = new MainWindowView();
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
	public MainWindowView()
	{
	  	filePath = null;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
	  	File file = new File("resources/donttouch.bat");
		file.delete();
		breakpoints = new ArrayList<Integer>();
		breakpoints2 = new ArrayList<GutterIconInfo>();
		fileModified = false;
		fileName = "new file";
		
		frame = new JFrame(appName + " - new file");
		frame.setBounds(100, 100, 650, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
//		Student student = new Student(11140631, "aaa", "aljon", "jose", "s18");
//		try
//		{
//		  student.sendData();
//		} catch (IOException e1)
//		{
//		  // TODO Auto-generated catch block
//		  e1.printStackTrace();
//		}
		
		final JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter cFilter = new FileNameExtensionFilter(
		     "C Source (*.c)", "c");
		fileChooser.setFileFilter(cFilter);
		
		EventController eventController = EventController.getEventController();
        
		RSyntaxTextArea editorPane = new RSyntaxTextArea();
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
		editorPane.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e) {
			  
			  if (e.isControlDown())
			  {
				if (e.getKeyCode() == e.VK_S)
				{
				  if (filePath != null)
				  {
					eventController.saveFile(frame, editorPane, filePath, fileModified);
					fileName = filePath.getFileName().toString();
				  }
				  
				  else
				  {
					filePath = eventController.saveAsFile(frame, editorPane, fileModified);
					fileName = filePath.getFileName().toString();
				  }	  
				}
			  }
			}
		});
		editorPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
		editorPane.setCodeFoldingEnabled(true);
		RTextScrollPane scrollPane = new RTextScrollPane(editorPane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setIconRowHeaderEnabled(true);
		scrollPane.setDefaultLocale(null);
		scrollPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.revalidate();
		Gutter gut = scrollPane.getGutter();
		gut.setBookmarkingEnabled(true);
		JToolBar coreToolbar = new JToolBar();
		coreToolbar.setFloatable(false);
		coreToolbar.setRollover(true);
		JButton newButton = new JButton("");
		newButton.setToolTipText("New");
		newButton.setIcon(new ImageIcon("resources/images/newFile.png"));
		JButton openButton = new JButton("");
		openButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
			  eventController.deleteDontTouch();
			  filePath = eventController.openFile(frame, editorPane);
			  fileName = filePath.getFileName().toString();
			}
		});
		openButton.setIcon(new ImageIcon("resources/images/openFile.png"));
		openButton.setToolTipText("Open");
		JButton saveButton = new JButton("");
		saveButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				 eventController.saveFile(frame, editorPane, filePath, fileModified);
				 fileModified = false;
			}
		});
		saveButton.setIcon(new ImageIcon("resources/images/saveFile.png"));
		saveButton.setToolTipText("Save");
		JButton compileButton = new JButton("");
		compileButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				eventController.compile(frame, editorPane, filePath); 
			}
		});
		compileButton.setIcon(new ImageIcon("resources/images/buildCompile.png"));
		compileButton.setToolTipText("Compile");
		JButton debugButton = new JButton("");
		debugButton.setIcon(new ImageIcon("resources/images/debugCompile.png"));
		debugButton.setToolTipText("Debug");
		JButton stepOverButton = new JButton("");
		stepOverButton.setIcon(new ImageIcon("resources/images/buildCompile.png"));
		stepOverButton.setToolTipText("Step Over");
		stepOverButton.setEnabled(false);
		JButton resumeButton = new JButton("");
		resumeButton.setIcon(new ImageIcon("resources/images/debugCompile.png"));
		resumeButton.setToolTipText("Resume");
		resumeButton.setEnabled(false);
		JButton stopButton = new JButton("");
		stopButton.setIcon(new ImageIcon("resources/images/debugCompile.png"));
		stopButton.setToolTipText("Stop");
		stopButton.setEnabled(false);
		coreToolbar.add(newButton);
		coreToolbar.add(openButton);
		coreToolbar.add(saveButton);
		coreToolbar.addSeparator();
		coreToolbar.add(compileButton);
		coreToolbar.add(debugButton);
		coreToolbar.addSeparator();
		coreToolbar.add(stepOverButton);
		coreToolbar.add(resumeButton);
		coreToolbar.add(stopButton);
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem newFileItem = new JMenuItem("New", KeyEvent.VK_N);
		newFileItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
			  filePath = null;
			  
			  if (editorPane.getText().equals(""))
			  {
				editorPane.setText("");
				eventController.deleteDontTouch();
			  }
			  
			  else
			  {
				int confirmed = JOptionPane.showConfirmDialog(null, "Create new file?", "", JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) 
			    {
			      editorPane.setText("");
				  eventController.deleteDontTouch();
			    }
			  }
			  
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
			  eventController.saveFile(frame, editorPane, filePath, fileModified);
			  frame.setTitle(appName + " - " + fileName);
			  fileModified = false;
			}
		});
		
		JMenuItem saveAsFileItem = new JMenuItem("Save As...");
		
		saveAsFileItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  eventController.saveAsFile(frame, editorPane, fileModified);
			}
		});
		
		JMenuItem exitFileItem = new JMenuItem("Exit", KeyEvent.VK_X);
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
			  filePath = eventController.compile(frame, editorPane, filePath);
			}
		});
		
		compileBuildItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
		
		JMenuItem debugBuildItem = new JMenuItem("Debug", KeyEvent.VK_D);
		
		debugBuildItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				eventController.debugToggler(frame, newButton, newFileItem, openButton, openFileItem, saveButton, saveFileItem, saveAsFileItem, compileButton, compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, stopButton);
				eventController.debugActual2(frame, editorPane, filePath, newButton, newFileItem, openButton, openFileItem, saveButton, saveFileItem, saveAsFileItem, compileButton, compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, stopButton, editorPane, scrollPane, breakpoints);
			}
		});
		
		debugBuildItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		
		JMenuItem addBreakItem = new JMenuItem("Add Breakpoint...");
		
		addBreakItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
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
							GutterIconInfo gii = gut.addLineTrackingIcon(bpnum, new ImageIcon("resources/images/Breakpoint16.png"));
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
		});
		JMenuItem delBreakItem = new JMenuItem("Remove Breakpoint...");
		delBreakItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
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
							JOptionPane.showMessageDialog(null, "Line " + input + " removed successfully.", "Added!", JOptionPane.INFORMATION_MESSAGE);
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
		mntmCompileRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
			  filePath = eventController.compile(frame, editorPane, filePath);
			  eventController.runProgram();
			}
		});
		buildMenu.add(mntmCompileRun);
		buildMenu.add(debugBuildItem);
		buildMenu.add(addBreakItem);
		buildMenu.add(delBreakItem);
		buildMenu.add(manageBreakpointItem);
		menuBar.add(helpMenu);
		helpMenu.add(helpHelpItem);
		helpMenu.add(aboutHelpItem);

		frame.setJMenuBar(menuBar);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	
		SpringLayout springLayout = new SpringLayout();
		springLayout.putConstraint(SpringLayout.NORTH, coreToolbar, 0, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, coreToolbar, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, coreToolbar, 48, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, coreToolbar, 2500, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(coreToolbar, BorderLayout.NORTH);
		frame.setVisible(true);
	}
}
