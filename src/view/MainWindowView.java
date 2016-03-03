package view;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
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
	
	JEditorPane editorPane = new JEditorPane();
	editorPane.setBounds(10, 42, 584, 333);
	editorPane.setFont(lucida);
	frame.getContentPane().add(editorPane);
  }
}
