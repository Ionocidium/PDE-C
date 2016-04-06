package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToggleButton;
import javax.swing.JTextField;

public class StudentListProfessorView {

	private JFrame activityList;
	private JTextField statusTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentListProfessorView window = new StudentListProfessorView();
					window.activityList.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentListProfessorView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		activityList = new JFrame();
		activityList.setTitle("Student List");
		activityList.setBounds(100, 100, 600, 320);
		activityList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		activityList.getContentPane().setLayout(null);
		
		JPanel tabulatedSubmissionsPanel = new JPanel();
		tabulatedSubmissionsPanel.setBackground(Color.WHITE);
		tabulatedSubmissionsPanel.setBounds(10, 36, 564, 214);
		activityList.getContentPane().add(tabulatedSubmissionsPanel);
		GridBagLayout gbl_tabulatedSubmissionsPanel = new GridBagLayout();
		gbl_tabulatedSubmissionsPanel.columnWidths = new int[]{155, 87, 0, 0, 0, 0};
		gbl_tabulatedSubmissionsPanel.rowHeights = new int[]{35, 35, 35, 35, 35, 35, 0};
		gbl_tabulatedSubmissionsPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_tabulatedSubmissionsPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		tabulatedSubmissionsPanel.setLayout(gbl_tabulatedSubmissionsPanel);
		
		JLabel studentIDLabel = new JLabel("Student ID");
		studentIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_studentIDLabel = new GridBagConstraints();
		gbc_studentIDLabel.fill = GridBagConstraints.BOTH;
		gbc_studentIDLabel.insets = new Insets(0, 0, 5, 5);
		gbc_studentIDLabel.gridx = 0;
		gbc_studentIDLabel.gridy = 0;
		tabulatedSubmissionsPanel.add(studentIDLabel, gbc_studentIDLabel);
		
		JLabel studentNameLabel = new JLabel("Student Name");
		studentNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_studentNameLabel = new GridBagConstraints();
		gbc_studentNameLabel.fill = GridBagConstraints.BOTH;
		gbc_studentNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_studentNameLabel.gridx = 1;
		gbc_studentNameLabel.gridy = 0;
		tabulatedSubmissionsPanel.add(studentNameLabel, gbc_studentNameLabel);
		
		JLabel lblActions = new JLabel("Section");
		lblActions.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblActions = new GridBagConstraints();
		gbc_lblActions.insets = new Insets(0, 0, 5, 5);
		gbc_lblActions.gridx = 2;
		gbc_lblActions.gridy = 0;
		tabulatedSubmissionsPanel.add(lblActions, gbc_lblActions);
		
		JLabel label_11 = new JLabel("10987654");
		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.anchor = GridBagConstraints.WEST;
		gbc_label_11.insets = new Insets(0, 0, 5, 5);
		gbc_label_11.gridx = 0;
		gbc_label_11.gridy = 1;
		tabulatedSubmissionsPanel.add(label_11, gbc_label_11);
		
		JLabel label_6 = new JLabel("Nielson, Niels");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.WEST;
		gbc_label_6.insets = new Insets(0, 0, 5, 5);
		gbc_label_6.gridx = 1;
		gbc_label_6.gridy = 1;
		tabulatedSubmissionsPanel.add(label_6, gbc_label_6);
		
		JLabel label_1 = new JLabel("S11A");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 1;
		tabulatedSubmissionsPanel.add(label_1, gbc_label_1);
		
		JButton btnView = new JButton("View Profile/Activities");
		GridBagConstraints gbc_btnView = new GridBagConstraints();
		gbc_btnView.insets = new Insets(0, 0, 5, 5);
		gbc_btnView.gridx = 3;
		gbc_btnView.gridy = 1;
		tabulatedSubmissionsPanel.add(btnView, gbc_btnView);
		
		JButton btnDelete = new JButton("Remove");
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(0, 0, 5, 0);
		gbc_btnDelete.gridx = 4;
		gbc_btnDelete.gridy = 1;
		tabulatedSubmissionsPanel.add(btnDelete, gbc_btnDelete);
		
		JLabel label_12 = new JLabel("10864213");
		GridBagConstraints gbc_label_12 = new GridBagConstraints();
		gbc_label_12.anchor = GridBagConstraints.WEST;
		gbc_label_12.insets = new Insets(0, 0, 5, 5);
		gbc_label_12.gridx = 0;
		gbc_label_12.gridy = 2;
		tabulatedSubmissionsPanel.add(label_12, gbc_label_12);
		
		JLabel label_7 = new JLabel("Rizal, Jose");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.anchor = GridBagConstraints.WEST;
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 1;
		gbc_label_7.gridy = 2;
		tabulatedSubmissionsPanel.add(label_7, gbc_label_7);
		
		JLabel label_2 = new JLabel("S11A");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 2;
		tabulatedSubmissionsPanel.add(label_2, gbc_label_2);
		
		JButton btnViewProfileactivities = new JButton("View Profile/Activities");
		GridBagConstraints gbc_btnViewProfileactivities = new GridBagConstraints();
		gbc_btnViewProfileactivities.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewProfileactivities.gridx = 3;
		gbc_btnViewProfileactivities.gridy = 2;
		tabulatedSubmissionsPanel.add(btnViewProfileactivities, gbc_btnViewProfileactivities);
		
		JButton btnRemove = new JButton("Remove");
		GridBagConstraints gbc_btnRemove = new GridBagConstraints();
		gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove.gridx = 4;
		gbc_btnRemove.gridy = 2;
		tabulatedSubmissionsPanel.add(btnRemove, gbc_btnRemove);
		
		JLabel label_13 = new JLabel("11021333");
		GridBagConstraints gbc_label_13 = new GridBagConstraints();
		gbc_label_13.anchor = GridBagConstraints.WEST;
		gbc_label_13.insets = new Insets(0, 0, 5, 5);
		gbc_label_13.gridx = 0;
		gbc_label_13.gridy = 3;
		tabulatedSubmissionsPanel.add(label_13, gbc_label_13);
		
		JLabel label_8 = new JLabel("Jobs, Steve");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.anchor = GridBagConstraints.WEST;
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 1;
		gbc_label_8.gridy = 3;
		tabulatedSubmissionsPanel.add(label_8, gbc_label_8);
		
		JLabel label_3 = new JLabel("S11A");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 3;
		tabulatedSubmissionsPanel.add(label_3, gbc_label_3);
		
		JButton btnViewProfileactivities_1 = new JButton("View Profile/Activities");
		GridBagConstraints gbc_btnViewProfileactivities_1 = new GridBagConstraints();
		gbc_btnViewProfileactivities_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewProfileactivities_1.gridx = 3;
		gbc_btnViewProfileactivities_1.gridy = 3;
		tabulatedSubmissionsPanel.add(btnViewProfileactivities_1, gbc_btnViewProfileactivities_1);
		
		JButton btnRemove_1 = new JButton("Remove");
		GridBagConstraints gbc_btnRemove_1 = new GridBagConstraints();
		gbc_btnRemove_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove_1.gridx = 4;
		gbc_btnRemove_1.gridy = 3;
		tabulatedSubmissionsPanel.add(btnRemove_1, gbc_btnRemove_1);
		
		JLabel label_14 = new JLabel("11133360");
		GridBagConstraints gbc_label_14 = new GridBagConstraints();
		gbc_label_14.anchor = GridBagConstraints.WEST;
		gbc_label_14.insets = new Insets(0, 0, 5, 5);
		gbc_label_14.gridx = 0;
		gbc_label_14.gridy = 4;
		tabulatedSubmissionsPanel.add(label_14, gbc_label_14);
		
		JLabel label_9 = new JLabel("Aguilar, Fred");
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.anchor = GridBagConstraints.WEST;
		gbc_label_9.insets = new Insets(0, 0, 5, 5);
		gbc_label_9.gridx = 1;
		gbc_label_9.gridy = 4;
		tabulatedSubmissionsPanel.add(label_9, gbc_label_9);
		
		JLabel label_4 = new JLabel("S11A");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 2;
		gbc_label_4.gridy = 4;
		tabulatedSubmissionsPanel.add(label_4, gbc_label_4);
		
		JButton btnViewProfileactivities_2 = new JButton("View Profile/Activities");
		GridBagConstraints gbc_btnViewProfileactivities_2 = new GridBagConstraints();
		gbc_btnViewProfileactivities_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnViewProfileactivities_2.gridx = 3;
		gbc_btnViewProfileactivities_2.gridy = 4;
		tabulatedSubmissionsPanel.add(btnViewProfileactivities_2, gbc_btnViewProfileactivities_2);
		
		JButton btnRemove_2 = new JButton("Remove");
		GridBagConstraints gbc_btnRemove_2 = new GridBagConstraints();
		gbc_btnRemove_2.insets = new Insets(0, 0, 5, 0);
		gbc_btnRemove_2.gridx = 4;
		gbc_btnRemove_2.gridy = 4;
		tabulatedSubmissionsPanel.add(btnRemove_2, gbc_btnRemove_2);
		
		JLabel label_15 = new JLabel("11280960");
		GridBagConstraints gbc_label_15 = new GridBagConstraints();
		gbc_label_15.anchor = GridBagConstraints.WEST;
		gbc_label_15.insets = new Insets(0, 0, 0, 5);
		gbc_label_15.gridx = 0;
		gbc_label_15.gridy = 5;
		tabulatedSubmissionsPanel.add(label_15, gbc_label_15);
		
		JLabel label_10 = new JLabel("Gates, Bill");
		GridBagConstraints gbc_label_10 = new GridBagConstraints();
		gbc_label_10.anchor = GridBagConstraints.WEST;
		gbc_label_10.insets = new Insets(0, 0, 0, 5);
		gbc_label_10.gridx = 1;
		gbc_label_10.gridy = 5;
		tabulatedSubmissionsPanel.add(label_10, gbc_label_10);
		
		JLabel label_5 = new JLabel("S11A");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 0, 5);
		gbc_label_5.gridx = 2;
		gbc_label_5.gridy = 5;
		tabulatedSubmissionsPanel.add(label_5, gbc_label_5);
		
		JButton btnViewProfileactivities_3 = new JButton("View Profile/Activities");
		GridBagConstraints gbc_btnViewProfileactivities_3 = new GridBagConstraints();
		gbc_btnViewProfileactivities_3.insets = new Insets(0, 0, 0, 5);
		gbc_btnViewProfileactivities_3.gridx = 3;
		gbc_btnViewProfileactivities_3.gridy = 5;
		tabulatedSubmissionsPanel.add(btnViewProfileactivities_3, gbc_btnViewProfileactivities_3);
		
		JButton btnRemove_3 = new JButton("Remove");
		GridBagConstraints gbc_btnRemove_3 = new GridBagConstraints();
		gbc_btnRemove_3.gridx = 4;
		gbc_btnRemove_3.gridy = 5;
		tabulatedSubmissionsPanel.add(btnRemove_3, gbc_btnRemove_3);
		
		statusTextField = new JTextField();
		statusTextField.setEditable(false);
		statusTextField.setBounds(0, 261, 584, 20);
		activityList.getContentPane().add(statusTextField);
		statusTextField.setColumns(10);
		
		JButton btnDeleteAll = new JButton("Remove All");
		btnDeleteAll.setBounds(455, 6, 119, 23);
		activityList.getContentPane().add(btnDeleteAll);
		
		JLabel label = new JLabel("Select Section:");
		label.setBounds(10, 10, 91, 14);
		activityList.getContentPane().add(label);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"S11A", "S11B", "S12A"}));
		comboBox.setBounds(114, 7, 60, 20);
		activityList.getContentPane().add(comboBox);
	}
}
