package view;


import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.io.IOException;


import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;

import java.nio.file.Path;
import controller.EventController;
import model.Student;
// import model.Student;
import service.Parsers;

import java.awt.event.KeyAdapter;
import javax.swing.SpringLayout;
import javax.swing.ScrollPaneConstants;

public class MainWindowView
{

	private JFrame frame;
	private Path filePath;
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
		frame = new JFrame();
		frame.setBounds(100, 100, 650, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);

		Student student = new Student(11140631, "aaa", "yong", "yongers", "s18");

		final JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter cFilter = new FileNameExtensionFilter(
		     "C Source (*.c)", "c");
		fileChooser.setFileFilter(cFilter);
		
		EventController eventController = EventController.getEventController();
        
		RSyntaxTextArea editorPane = new RSyntaxTextArea();
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
					eventController.saveFile(frame, editorPane, filePath);
				  }
				  
				  else
				  {
					filePath = eventController.saveAsFile(frame, editorPane);
				  }	  
				}
				
				else if (e.getKeyCode() == e.VK_O)
				{
				  filePath = eventController.openFile(frame, editorPane);
				}
				
				else if (e.getKeyCode() == e.VK_N)
				{
				  filePath = null;
				  editorPane.setText("");
				}
			  }
			  
			  else if (e.getKeyCode() == e.VK_F6)
			  {
				eventController.compile(frame, editorPane, filePath);
			  }
			  
			  else if (e.getKeyCode() == e.VK_F8)
			  {
				eventController.compile(frame, editorPane, filePath);
				eventController.runProgram();				
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
		gut.setBookmarkIcon(new ImageIcon("resources/images/Breakpoint16.png"));

		JToolBar coreToolbar = new JToolBar();
		coreToolbar.setFloatable(false);
		coreToolbar.setRollover(true);
		JButton newButton = new JButton("");
		newButton.setToolTipText("New");
		newButton.setIcon(new ImageIcon("resources/images/new/newfile.png"));
		JButton openButton = new JButton("");
		openButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				filePath = eventController.openFile(frame, editorPane);
			}
		});
	
		openButton.setIcon(new ImageIcon("resources/images/new/openfile.png"));
		openButton.setToolTipText("Open");
		JButton saveButton = new JButton("");
		saveButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) {
				 eventController.saveFile(frame, editorPane, filePath);
			}
		});
		saveButton.setIcon(new ImageIcon("resources/images/new/save.png"));
		saveButton.setToolTipText("Save");
		JButton compileButton = new JButton("");
		compileButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				eventController.compile(frame, editorPane, filePath); 
			}
		});
		compileButton.setIcon(new ImageIcon("resources/images/new/compile.png"));
		compileButton.setToolTipText("Compile");
		JButton debugButton = new JButton("");
		debugButton.setIcon(new ImageIcon("resources/images/new/debug.png"));
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
		newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		JMenuItem openFileItem = new JMenuItem("Open");
		openFileItem.addActionListener(new ActionListener() 
		{
		  public void actionPerformed(ActionEvent e) 
		  {
			filePath = eventController.openFile(frame, editorPane);
		  }
		});
		
		
		JMenuItem saveFileItem = new JMenuItem("Save");
		saveFileItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
			  eventController.saveFile(frame, editorPane, filePath);
			}
		});
		
		JMenuItem saveAsFileItem = new JMenuItem("Save As...");
		
		saveAsFileItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  eventController.saveAsFile(frame, editorPane);
			}
		});
		
		JMenuItem exitFileItem = new JMenuItem("Exit", KeyEvent.VK_X);
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
		JMenuItem compileBuildItem = new JMenuItem("Compile", KeyEvent.VK_C);
		
		compileBuildItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  eventController.compile(frame, editorPane, filePath); 
			}
		});
		
		JMenuItem debugBuildItem = new JMenuItem("Debug", KeyEvent.VK_D);
		
		debugBuildItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				eventController.debugToggler(frame, newButton, newFileItem, openButton, openFileItem, saveButton, saveFileItem, saveAsFileItem, compileButton, compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, stopButton);
				eventController.debugActual2(frame, editorPane, filePath, newButton, newFileItem, openButton, openFileItem, saveButton, saveFileItem, saveAsFileItem, compileButton, compileBuildItem, debugButton, debugBuildItem, stepOverButton, resumeButton, stopButton, editorPane, scrollPane);
			}
		});
		
		debugBuildItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
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
		buildMenu.add(debugBuildItem);
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
