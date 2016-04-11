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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;

public class StudentProfile {

	private JFrame studentProfileFrame;
	private JTextField txtIDNum;
	private JTextField txtName;
	private JTextField txtSection;
	private JTable activityList;
	private JTextField textField;
	private JTextField textField_1;

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
		studentProfileFrame.setSize(640, 360);
		studentProfileFrame.setTitle("Student Profile");
		studentProfileFrame.getContentPane().setLayout(null);
		
		JPanel generalInfoPanel = new JPanel();
		generalInfoPanel.setBounds(0, 0, 634, 72);
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
		lblName.setBounds(202, 11, 36, 24);
		generalInfoPanel.add(lblName);
		
		txtName = new JTextField();
		txtName.setEditable(false);
		txtName.setBounds(248, 11, 240, 24);
		txtName.setText("Nielson, Niels");
		txtName.setColumns(10);
		generalInfoPanel.add(txtName);
		
		JLabel lblSection = new JLabel("Section");
		lblSection.setBounds(498, 11, 48, 24);
		generalInfoPanel.add(lblSection);
		
		txtSection = new JTextField();
		txtSection.setEditable(false);
		txtSection.setBounds(556, 11, 48, 24);
		txtSection.setText("S11A");
		txtSection.setColumns(10);
		generalInfoPanel.add(txtSection);
		
		JLabel lblActivities = new JLabel("Activities:");
		lblActivities.setBounds(10, 46, 64, 24);
		generalInfoPanel.add(lblActivities);
		
		textField = new JTextField();
		textField.setToolTipText("Activities Done");
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setText("5");
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(106, 46, 26, 24);
		generalInfoPanel.add(textField);
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Total Activities");
		textField_1.setText("10");
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(156, 46, 26, 24);
		generalInfoPanel.add(textField_1);
		
		JLabel label = new JLabel("/");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(106, 46, 76, 24);
		generalInfoPanel.add(label);
		
		JScrollPane activityPane = new JScrollPane();
		activityPane.setBounds(10, 83, 614, 237);
		studentProfileFrame.getContentPane().add(activityPane);
		
		activityList = new JTable();
		activityList.setModel(new DefaultTableModel(
			new Object[][] {
				{"Basic Functions", "Nielson_Niels_BasicFunctions.c", "1/4/2016 1:56:29 PM UTC+0800", "NGS"},
				{"Conditional Statements", "Nielson_Niels_ConditionalStatements.c", "1/11/2016 2:29:56 PM UTC+0800", "83"},
				{"Loops", "Nielson_Niels_Loops.c", "1/18/2016 1:37:48 PM UTC+0800", "91"},
				{"Functions", "Nielson_Niels_Functions.c", "1/25/2016 1:08:35 PM UTC+0800", "82"},
				{"ASCII Art I", "Nielson_Niels_ASCIIArtI.c", "2/1/2016 2:10:10 PM UTC+0800", "60"},
				{null, null, "", null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"Activity Name", "Source Code", "Date Submitted", "Grade"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		activityList.getColumnModel().getColumn(0).setPreferredWidth(129);
		activityList.getColumnModel().getColumn(1).setPreferredWidth(226);
		activityList.getColumnModel().getColumn(2).setPreferredWidth(188);
		activityList.getColumnModel().getColumn(3).setPreferredWidth(40);
		activityPane.setViewportView(activityList);
	}
}
