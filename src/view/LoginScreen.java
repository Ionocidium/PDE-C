package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.fife.rsta.ac.java.rjc.parser.Main;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;

public class LoginScreen {

	private JFrame frmLogin;
	private JTextField textField;

	/**
	 * Launch the application.
	 * @param args the arguments that will be issued while running the program.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					LoginScreen window = new LoginScreen();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Shows the <code>LoginScreen</code>.
	 */
	public LoginScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setResizable(false);
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 278, 128);
		frmLogin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		URL pdecIcon = Main.class.getResource("/PDECICON.png");
		frmLogin.setIconImage(new ImageIcon(pdecIcon).getImage());
		
		JLabel lblStudentId = new JLabel("Student ID:");
		lblStudentId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudentId.setBounds(10, 14, 87, 14);
		frmLogin.getContentPane().add(lblStudentId);
		
		textField = new JTextField();
		textField.setBounds(108, 11, 153, 20);
		textField.setText("");
		frmLogin.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  EventQueue.invokeLater(new Runnable()
					{
						public void run()
						{
							try
							{
								MainWindowView window = MainWindowView.getInstance();
								
								if (!textField.getText().trim().equals(""))
								{
								  window.setStudentIdNum(textField.getText());
								}
								
								else
								{
								  window.setStudentIdNum("0");
								  window.checkIfSendable();
								}
	
								window.getMainFrame().setVisible(true);
								frmLogin.dispose();
								
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					});
			}
		});
		btnLogin.setBounds(171, 64, 90, 23);
		frmLogin.getContentPane().add(btnLogin);
		frmLogin.setLocationRelativeTo(null);
		
		frmLogin.getRootPane().setDefaultButton(btnLogin);
		frmLogin.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyPressed(KeyEvent arg0) 
			{
			  if (arg0.getKeyCode() == KeyEvent.VK_ENTER)
			  {
				EventQueue.invokeLater(new Runnable()
				{
					public void run()
					{
						try
						{
							MainWindowView window = MainWindowView.getInstance();
							
							if (!textField.getText().trim().equals(""))
							{
							  window.setStudentIdNum(textField.getText());
							}
							
							else
							{
							  window.setStudentIdNum("0");
							  window.checkIfSendable();
							}

							window.getMainFrame().setVisible(true);
							frmLogin.dispose();
							
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				});
			  }
			}
		});
	}
	

}
