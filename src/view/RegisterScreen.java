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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class RegisterScreen {

	private JFrame frmLogin;
	private JTextField textField;
	private JPasswordField pwdAbcdfj;
	private JButton btnRegister;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterScreen window = new RegisterScreen();
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
	public RegisterScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.setResizable(false);
		frmLogin.setTitle("Register");
		frmLogin.setBounds(100, 100, 278, 176);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		
		JLabel lblStudentId = new JLabel("Student ID:");
		lblStudentId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblStudentId.setBounds(10, 14, 87, 14);
		frmLogin.getContentPane().add(lblStudentId);
		
		textField = new JTextField();
		textField.setBounds(108, 11, 153, 20);
		textField.setText("10987654");
		frmLogin.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(10, 39, 87, 14);
		frmLogin.getContentPane().add(lblPassword);
		
		pwdAbcdfj = new JPasswordField();
		pwdAbcdfj.setBounds(108, 36, 153, 20);
		pwdAbcdfj.setText("AbCd3F6#!J<1");
		frmLogin.getContentPane().add(pwdAbcdfj);
		
		btnRegister = new JButton("Back");
		btnRegister.setBounds(10, 117, 90, 23);
		frmLogin.getContentPane().add(btnRegister);
		
		JButton btnLogin = new JButton("Confirm");
		btnLogin.setBounds(171, 117, 90, 23);
		frmLogin.getContentPane().add(btnLogin);
		
		JLabel lblSection = new JLabel("Section:");
		lblSection.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSection.setBounds(10, 89, 87, 14);
		frmLogin.getContentPane().add(lblSection);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"S11A", "S11B", "S12A"}));
		comboBox.setBounds(108, 86, 153, 20);
		frmLogin.getContentPane().add(comboBox);
		
		JLabel lblConfirmPassword = new JLabel("Confirm:");
		lblConfirmPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblConfirmPassword.setBounds(10, 64, 87, 14);
		frmLogin.getContentPane().add(lblConfirmPassword);
		
		passwordField = new JPasswordField();
		passwordField.setText("AbCd3F6#!J<1");
		passwordField.setBounds(108, 61, 153, 20);
		frmLogin.getContentPane().add(passwordField);
	}
}
