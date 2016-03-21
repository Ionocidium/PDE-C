package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Color;

public class ProfessorStudentProfile {

	private JFrame studentProfileFrame;
	private JTextField txtIDNum;
	private JTextField txtName;
	private JTextField txtRoom;
	private JTextField txtSection;
	private JTextField txtRemarks;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorStudentProfile window = new ProfessorStudentProfile();
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
	public ProfessorStudentProfile() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		studentProfileFrame = new JFrame();
		studentProfileFrame.setResizable(false);
		studentProfileFrame.setSize(720, 480);
		studentProfileFrame.setTitle("Edit Profile");
		studentProfileFrame.getContentPane().setLayout(null);
		
		JPanel generalInfoPanel = new JPanel();
		generalInfoPanel.setBounds(215, 10, 489, 187);
		studentProfileFrame.getContentPane().add(generalInfoPanel);
		generalInfoPanel.setLayout(null);
		
		JLabel lblIDNum = new JLabel("ID Number");
		lblIDNum.setBounds(10, 11, 128, 24);
		generalInfoPanel.add(lblIDNum);
		
		txtIDNum = new JTextField();
		txtIDNum.setBounds(158, 11, 320, 24);
		txtIDNum.setText("10987654");
		generalInfoPanel.add(txtIDNum);
		txtIDNum.setColumns(10);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 46, 128, 24);
		generalInfoPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(158, 46, 320, 24);
		txtName.setText("Nielson, Niels");
		txtName.setColumns(10);
		generalInfoPanel.add(txtName);
		
		JLabel lblRoomNo = new JLabel("Room No.");
		lblRoomNo.setBounds(10, 81, 64, 24);
		generalInfoPanel.add(lblRoomNo);
		
		txtRoom = new JTextField();
		txtRoom.setBounds(84, 81, 64, 24);
		txtRoom.setText("GK302A");
		txtRoom.setColumns(10);
		generalInfoPanel.add(txtRoom);
		
		JLabel lblSection = new JLabel("Section");
		lblSection.setBounds(158, 81, 48, 24);
		generalInfoPanel.add(lblSection);
		
		txtSection = new JTextField();
		txtSection.setBounds(219, 81, 48, 24);
		txtSection.setText("S11A");
		txtSection.setColumns(10);
		generalInfoPanel.add(txtSection);
		
		JLabel lblActivities = new JLabel("Activities:");
		lblActivities.setBounds(277, 81, 64, 24);
		generalInfoPanel.add(lblActivities);
		
		JLabel lblRemarks = new JLabel("Remarks");
		lblRemarks.setBounds(10, 151, 64, 24);
		generalInfoPanel.add(lblRemarks);
		
		txtRemarks = new JTextField();
		txtRemarks.setText("A very diligent student. Passes requirements on time");
		txtRemarks.setColumns(10);
		txtRemarks.setBounds(158, 151, 320, 24);
		generalInfoPanel.add(txtRemarks);
		
		JButton button = new JButton("Check Activities");
		button.setBounds(351, 82, 128, 23);
		generalInfoPanel.add(button);
		
		JPanel aboutMePanel = new JPanel();
		aboutMePanel.setBounds(10, 213, 694, 227);
		studentProfileFrame.getContentPane().add(aboutMePanel);
		aboutMePanel.setLayout(null);
		
		JLabel lblAboutMe = new JLabel("About Me");
		lblAboutMe.setBounds(10, 11, 64, 24);
		aboutMePanel.add(lblAboutMe);
		
		JTextPane txtpnProgrammerAndLearner = new JTextPane();
		txtpnProgrammerAndLearner.setText("Programmer and learner here.");
		txtpnProgrammerAndLearner.setBounds(10, 46, 674, 170);
		aboutMePanel.add(txtpnProgrammerAndLearner);
		
		JButton btnSaveProfileInformation = new JButton("Save Profile Information");
		btnSaveProfileInformation.setBounds(492, 11, 192, 24);
		aboutMePanel.add(btnSaveProfileInformation);
		
		JPanel imgPanel = new JPanel();
		imgPanel.setBounds(10, 10, 146, 187);
		studentProfileFrame.getContentPane().add(imgPanel);
		imgPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		panel.setBounds(10, 10, 128, 128);
		imgPanel.add(panel);
		panel.setLayout(null);
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(10, 139, 128, 23);
		imgPanel.add(btnBrowse);
	}
}
