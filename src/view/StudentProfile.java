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

public class StudentProfile {

	private JFrame studentProfileFrame;
	private JTextField txtIDNum;
	private JTextField txtName;
	private JTextField txtSection;
	private JTable activityList;

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
		
		JScrollPane activityPane = new JScrollPane();
		activityPane.setBounds(10, 83, 614, 237);
		studentProfileFrame.getContentPane().add(activityPane);
		
		activityList = new JTable();
		activityList.setModel(new DefaultTableModel(
			new Object[][] {
				{"Basic Functions", "Nielson_BasicFunctions.c", "1/4/2016", "4.0"},
				{"Conditional Statements", "Nielson_ConditionalStatements.c", "1/11/2016", "3.0"},
				{"Loops", "Nielson_Loops.c", "1/18/2016", "2.0"},
				{"Functions", "Nielson_Functions.c", "1/25/2016", "1.0"},
				{"ASCII Art I", "Nielson_ASCIIArtI.c", "2/1/2016", "NGS"},
				{null, null, null, null},
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
		activityList.getColumnModel().getColumn(0).setPreferredWidth(191);
		activityList.getColumnModel().getColumn(1).setPreferredWidth(166);
		activityList.getColumnModel().getColumn(2).setPreferredWidth(55);
		activityList.getColumnModel().getColumn(3).setPreferredWidth(23);
		activityPane.setViewportView(activityList);
	}
}
