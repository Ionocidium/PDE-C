package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;

import java.nio.file.Path;
import java.nio.file.Paths;

import controller.fileops.FileLoad;
import controller.fileops.FileSave;

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
		frame.setBounds(100, 100, 620, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		final JFileChooser fileChooser = new JFileChooser();
		FileNameExtensionFilter cFilter = new FileNameExtensionFilter(
		     "C Source (*.c)", "c");
		fileChooser.setFileFilter(cFilter);
		
		FileSave saveFile = new FileSave();
		FileLoad loader = new FileLoad();

		RSyntaxTextArea editorPane = new RSyntaxTextArea();
		editorPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
		editorPane.setCodeFoldingEnabled(true);
		RTextScrollPane scrollPane = new RTextScrollPane(editorPane);
		scrollPane.setIconRowHeaderEnabled(true);
		scrollPane.setBounds(0, 48, 614, 326);
		Gutter g = scrollPane.getGutter();
		g.setBookmarkIcon(new ImageIcon("resources/images/Breakpoint16.png"));
		g.setBookmarkingEnabled(true);
		try
		{
			g.addLineTrackingIcon(0, new ImageIcon("resources/images/Error16.png"),
					"Error in line 1, end of file");
		}
		catch (BadLocationException ble)
		{
			ble.printStackTrace();
		}

		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem newFileItem = new JMenuItem("New", KeyEvent.VK_N);
		newFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		JMenuItem openFileItem = new JMenuItem("Open", KeyEvent.VK_O);
		openFileItem.addActionListener(new ActionListener() 
		{
		  public void actionPerformed(ActionEvent e) 
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
			  }
			  
			  else
			  {
				JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
			  }
			}
		  }
		});
		
		openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		JMenuItem saveFileItem = new JMenuItem("Save", KeyEvent.VK_S);
		saveFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		JMenuItem saveAsFileItem = new JMenuItem("Save As...", KeyEvent.VK_A);
		
		saveAsFileItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  int returnVal = fileChooser.showSaveDialog(frame);
			  
			  if (returnVal == JFileChooser.APPROVE_OPTION)
			  {
				Path path = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());			
				saveFile.writeFile(path, editorPane.getText());
			  }
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
			  try
			  {
				if (filePath != null)
				{
				  CompileLog log = new CompileLog(filePath);
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
						CompileLog log = new CompileLog(filePath);
					  }
					  
					  else
					  {
						JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
					  }
					}			
				}
			  }
			  
			  catch (IOException e1)
			  {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			  }
			  
			}
		});
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
