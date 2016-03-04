package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JEditorPane;

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
	Font lucida = new Font("Lucida Console", Font.PLAIN, 12);
	frame = new JFrame();
	frame.setBounds(100, 100, 620, 425);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	
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
	JEditorPane editorPane = new JEditorPane();
	editorPane.setBounds(10, 42, 584, 333);
	editorPane.setFont(lucida);
	frame.getContentPane().add(editorPane);
	frame.setJMenuBar(menuBar);
  }
}
