package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class MainWindowView
{

  private JFrame frame;

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
	initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize()
  {
	frame = new JFrame();
	frame.setBounds(100, 100, 620, 425);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	frame.setResizable(false);
	frame.setLocationRelativeTo(null);
	
	RSyntaxTextArea editorPane = new RSyntaxTextArea();
	editorPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
	editorPane.setCodeFoldingEnabled(true);
	RTextScrollPane scrollPane = new RTextScrollPane(editorPane);
	scrollPane.setIconRowHeaderEnabled(true);
	scrollPane.setBounds(0, 48, 614, 326);
	
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	fileMenu.setMnemonic(KeyEvent.VK_F);
	JMenuItem newFileItem = new JMenuItem("New", KeyEvent.VK_N);
	newFileItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	JMenuItem openFileItem = new JMenuItem("Open", KeyEvent.VK_O);
	openFileItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	JMenuItem saveFileItem = new JMenuItem("Save", KeyEvent.VK_S);
	saveFileItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	JMenuItem saveAsFileItem = new JMenuItem("Save As...", KeyEvent.VK_A);
	JMenuItem exitFileItem = new JMenuItem("Exit", KeyEvent.VK_X);
	exitFileItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_F4, ActionEvent.ALT_MASK));
	JMenu editMenu = new JMenu("Edit");
	editMenu.setMnemonic(KeyEvent.VK_E);
	JMenuItem cutEditItem = new JMenuItem("Cut", KeyEvent.VK_T);
	cutEditItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_X, ActionEvent.CTRL_MASK));
	JMenuItem copyEditItem = new JMenuItem("Copy", KeyEvent.VK_C);
	copyEditItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
	JMenuItem pasteEditItem = new JMenuItem("Paste", KeyEvent.VK_P);
	pasteEditItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_V, ActionEvent.CTRL_MASK));
	JMenu buildMenu = new JMenu("Build");
	buildMenu.setMnemonic(KeyEvent.VK_B);
	JMenuItem compileBuildItem = new JMenuItem("Compile", KeyEvent.VK_C);
	compileBuildItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
	JMenuItem debugBuildItem = new JMenuItem("Debug", KeyEvent.VK_D);
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
	editMenu.add(cutEditItem);
	editMenu.add(copyEditItem);
	editMenu.add(pasteEditItem);
	menuBar.add(buildMenu);
	buildMenu.add(compileBuildItem);
	buildMenu.add(debugBuildItem);
	menuBar.add(helpMenu);
	helpMenu.add(helpHelpItem);
	helpMenu.add(aboutHelpItem);
	JToolBar coreToolbar = new JToolBar();
	coreToolbar.setFloatable(false);
	coreToolbar.setRollover(true);
	JButton newButton = new JButton("");
	newButton.setToolTipText("New");
	newButton.setIcon(new ImageIcon("resources/images/newFile.png"));
	JButton openButton = new JButton("");
	openButton.setIcon(new ImageIcon("resources/images/openFile.png"));
	openButton.setToolTipText("Open");
	JButton saveButton = new JButton("");
	saveButton.setIcon(new ImageIcon("resources/images/saveFile.png"));
	saveButton.setToolTipText("Save");
	JButton compileButton = new JButton("");
	compileButton.setIcon(new ImageIcon("resources/images/buildCompile.png"));
	compileButton.setToolTipText("Compile");
	JButton debugButton = new JButton("");
	debugButton.setIcon(new ImageIcon("resources/images/debugCompile.png"));
	debugButton.setToolTipText("Debug");
	coreToolbar.setBounds(0, 0, 620, 48);
	coreToolbar.add(newButton);
	coreToolbar.add(openButton);
	coreToolbar.add(saveButton);
	coreToolbar.addSeparator();
	coreToolbar.add(compileButton);
	coreToolbar.add(debugButton);
	frame.setJMenuBar(menuBar);
	frame.getContentPane().add(coreToolbar);
	frame.getContentPane().add(scrollPane);
  }
}
