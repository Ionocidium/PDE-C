package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JScrollPane;

public class StudentProfile {

	private JFrame studentProfileFrame;
	private JTextField txtIDNum;
	private JTextField txtName;
	private JTextField txtSection;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentProfile window = new StudentProfile();
					window.studentProfileFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentProfile() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		studentProfileFrame = new JFrame();
		studentProfileFrame.setResizable(false);
		studentProfileFrame.setSize(360, 360);
		studentProfileFrame.setTitle("Student Profile");
		studentProfileFrame.getContentPane().setLayout(null);
		
		JPanel generalInfoPanel = new JPanel();
		generalInfoPanel.setBounds(10, 11, 334, 112);
		studentProfileFrame.getContentPane().add(generalInfoPanel);
		generalInfoPanel.setLayout(null);
		
		JLabel lblIDNum = new JLabel("ID Number");
		lblIDNum.setBounds(10, 11, 86, 24);
		generalInfoPanel.add(lblIDNum);
		
		txtIDNum = new JTextField();
		txtIDNum.setEditable(false);
		txtIDNum.setBounds(106, 11, 86, 24);
		txtIDNum.setText("10987654");
		generalInfoPanel.add(txtIDNum);
		txtIDNum.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 46, 64, 24);
		generalInfoPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setBounds(84, 46, 240, 24);
		txtName.setText("Nielson, Niels");
		txtName.setColumns(10);
		generalInfoPanel.add(txtName);
		
		JLabel lblSection = new JLabel("Section");
		lblSection.setBounds(202, 11, 48, 24);
		generalInfoPanel.add(lblSection);
		
		txtSection = new JTextField();
		txtSection.setEditable(false);
		txtSection.setBounds(276, 11, 48, 24);
		txtSection.setText("S11A");
		txtSection.setColumns(10);
		generalInfoPanel.add(txtSection);
		
		JLabel lblActivities = new JLabel("Activities:");
		lblActivities.setBounds(10, 81, 64, 24);
		generalInfoPanel.add(lblActivities);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 134, 334, 186);
		studentProfileFrame.getContentPane().add(scrollPane);
	}
}
