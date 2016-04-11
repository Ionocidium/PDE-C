package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class TestCasesProfessorForm {

	private JFrame frmAddTestCase;
	private JTextField textField;
	private JTextField textField_1;
	private JButton btnNewButton;
	private JButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestCasesProfessorForm window = new TestCasesProfessorForm();
					window.frmAddTestCase.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestCasesProfessorForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddTestCase = new JFrame();
		frmAddTestCase.setResizable(false);
		frmAddTestCase.setTitle("Add Test Case");
		frmAddTestCase.setBounds(100, 100, 267, 119);
		frmAddTestCase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAddTestCase.getContentPane().setLayout(null);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setHorizontalAlignment(SwingConstants.RIGHT);
		lblInput.setBounds(10, 11, 46, 14);
		frmAddTestCase.getContentPane().add(lblInput);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOutput.setBounds(10, 36, 46, 14);
		frmAddTestCase.getContentPane().add(lblOutput);
		
		textField = new JTextField();
		textField.setText("1 2 3");
		textField.setBounds(66, 8, 179, 20);
		frmAddTestCase.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("6 ");
		textField_1.setColumns(10);
		textField_1.setBounds(66, 33, 179, 20);
		frmAddTestCase.getContentPane().add(textField_1);
		
		btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(10, 61, 89, 23);
		frmAddTestCase.getContentPane().add(btnNewButton);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(162, 61, 89, 23);
		frmAddTestCase.getContentPane().add(btnCancel);
	}
}
