package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginScreen {

	private JFrame frmLogin;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen window = new LoginScreen();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
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
							  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								MainWindowView window = MainWindowView.getInstance();
								
								if (!textField.getText().trim().equals(""))
								{
								  window.setStudentIdNum(textField.getText());
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
	}

}
