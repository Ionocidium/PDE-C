package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;

public class GradeExpanded {

	private JFrame gradesFrame;
	private JTextField txtDeadlineOfBasic;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GradeExpanded window = new GradeExpanded();
					window.gradesFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GradeExpanded() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		gradesFrame = new JFrame();
		gradesFrame.setTitle("Student Grades");
		gradesFrame.setBounds(100, 100, 760, 502);
		gradesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gradesFrame.getContentPane().setLayout(null);
		
		JLabel activityNameLabel = new JLabel("Select Activity:");
		activityNameLabel.setBounds(10, 11, 91, 14);
		gradesFrame.getContentPane().add(activityNameLabel);
		
		JPanel tabulatedSubmissionsPanel = new JPanel();
		tabulatedSubmissionsPanel.setBackground(Color.WHITE);
		tabulatedSubmissionsPanel.setBounds(10, 36, 724, 394);
		gradesFrame.getContentPane().add(tabulatedSubmissionsPanel);
		GridBagLayout gbl_tabulatedSubmissionsPanel = new GridBagLayout();
		gbl_tabulatedSubmissionsPanel.columnWidths = new int[]{76, 87, 51, 87, 0, 0, 87, 48, 0};
		gbl_tabulatedSubmissionsPanel.rowHeights = new int[]{35, 35, 0, 0, 35, 35, 35, 35, 0};
		gbl_tabulatedSubmissionsPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_tabulatedSubmissionsPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		JLabel studentSectionLabel = new JLabel("Section");
		studentSectionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_studentSectionLabel = new GridBagConstraints();
		gbc_studentSectionLabel.fill = GridBagConstraints.BOTH;
		gbc_studentSectionLabel.insets = new Insets(0, 0, 5, 5);
		gbc_studentSectionLabel.gridx = 2;
		gbc_studentSectionLabel.gridy = 0;
		tabulatedSubmissionsPanel.add(studentSectionLabel, gbc_studentSectionLabel);
		
		JLabel studentSourceCodeLabel = new JLabel("Source Code");
		studentSourceCodeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_studentSourceCodeLabel = new GridBagConstraints();
		gbc_studentSourceCodeLabel.gridwidth = 3;
		gbc_studentSourceCodeLabel.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCodeLabel.insets = new Insets(0, 0, 5, 5);
		gbc_studentSourceCodeLabel.gridx = 3;
		gbc_studentSourceCodeLabel.gridy = 0;
		tabulatedSubmissionsPanel.add(studentSourceCodeLabel, gbc_studentSourceCodeLabel);
		
		JLabel deliverableDateSubmittedLabel = new JLabel("Date Submitted");
		deliverableDateSubmittedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_deliverableDateSubmittedLabel = new GridBagConstraints();
		gbc_deliverableDateSubmittedLabel.fill = GridBagConstraints.BOTH;
		gbc_deliverableDateSubmittedLabel.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmittedLabel.gridx = 6;
		gbc_deliverableDateSubmittedLabel.gridy = 0;
		tabulatedSubmissionsPanel.add(deliverableDateSubmittedLabel, gbc_deliverableDateSubmittedLabel);
		
		JLabel deliverableGradeLabel = new JLabel("Grade");
		deliverableGradeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_deliverableGradeLabel = new GridBagConstraints();
		gbc_deliverableGradeLabel.fill = GridBagConstraints.BOTH;
		gbc_deliverableGradeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_deliverableGradeLabel.gridx = 7;
		gbc_deliverableGradeLabel.gridy = 0;
		tabulatedSubmissionsPanel.add(deliverableGradeLabel, gbc_deliverableGradeLabel);
		
		JLabel studentID4Label = new JLabel("11133360");
		GridBagConstraints gbc_studentID4Label = new GridBagConstraints();
		gbc_studentID4Label.fill = GridBagConstraints.BOTH;
		gbc_studentID4Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentID4Label.gridx = 0;
		gbc_studentID4Label.gridy = 1;
		tabulatedSubmissionsPanel.add(studentID4Label, gbc_studentID4Label);
		
		JLabel studentName4Label = new JLabel("Aguilar, Fred");
		GridBagConstraints gbc_studentName4Label = new GridBagConstraints();
		gbc_studentName4Label.fill = GridBagConstraints.BOTH;
		gbc_studentName4Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentName4Label.gridx = 1;
		gbc_studentName4Label.gridy = 1;
		tabulatedSubmissionsPanel.add(studentName4Label, gbc_studentName4Label);
		
		JLabel studentSection1Label = new JLabel("S11A");
		GridBagConstraints gbc_studentSection1Label = new GridBagConstraints();
		gbc_studentSection1Label.fill = GridBagConstraints.BOTH;
		gbc_studentSection1Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSection1Label.gridx = 2;
		gbc_studentSection1Label.gridy = 1;
		tabulatedSubmissionsPanel.add(studentSection1Label, gbc_studentSection1Label);
		
		JLabel studentSourceCode4Label = new JLabel("Aguilar_Fred_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode4Label = new GridBagConstraints();
		gbc_studentSourceCode4Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode4Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSourceCode4Label.gridx = 3;
		gbc_studentSourceCode4Label.gridy = 1;
		tabulatedSubmissionsPanel.add(studentSourceCode4Label, gbc_studentSourceCode4Label);
		
		JToggleButton showSourceCode1 = new JToggleButton("-");
		showSourceCode1.setSelected(true);
		GridBagConstraints gbc_showSourceCode1 = new GridBagConstraints();
		gbc_showSourceCode1.insets = new Insets(0, 0, 5, 5);
		gbc_showSourceCode1.gridx = 4;
		gbc_showSourceCode1.gridy = 1;
		tabulatedSubmissionsPanel.add(showSourceCode1, gbc_showSourceCode1);
		
		JButton button = new JButton("Test");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 5;
		gbc_button.gridy = 1;
		tabulatedSubmissionsPanel.add(button, gbc_button);
		
		JLabel deliverableDateSubmitted1Label = new JLabel("1/4/2016 1:56:29 PM UTC+0800");
		GridBagConstraints gbc_deliverableDateSubmitted1Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted1Label.anchor = GridBagConstraints.EAST;
		gbc_deliverableDateSubmitted1Label.fill = GridBagConstraints.VERTICAL;
		gbc_deliverableDateSubmitted1Label.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmitted1Label.gridx = 6;
		gbc_deliverableDateSubmitted1Label.gridy = 1;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted1Label, gbc_deliverableDateSubmitted1Label);
		
		/*
		RSyntaxTextArea deliverableEditorPane1 = new RSyntaxTextArea();
		deliverableEditorPane1.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
		deliverableEditorPane1.setCodeFoldingEnabled(true);
		RTextScrollPane deliverableSourceCode1 = new RTextScrollPane(deliverableEditorPane1);
		GridBagConstraints gbc_deliverableSourceCode1 = new GridBagConstraints();
		gbc_deliverableSourceCode1.gridwidth = 7;
		gbc_deliverableSourceCode1.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableSourceCode1.fill = GridBagConstraints.BOTH;
		gbc_deliverableSourceCode1.gridx = 0;
		gbc_deliverableSourceCode1.gridy = 2;
		tabulatedSubmissionsPanel.add(deliverableSourceCode1, gbc_deliverableSourceCode1);
		*/
		
		textField_1 = new JTextField();
		textField_1.setText("80");
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 7;
		gbc_textField_1.gridy = 1;
		tabulatedSubmissionsPanel.add(textField_1, gbc_textField_1);
		
		JPanel deliverableSourceCode1Panel = new JPanel();
		GridBagConstraints gbc_deliverableSourceCode1Panel = new GridBagConstraints();
		gbc_deliverableSourceCode1Panel.gridwidth = 8;
		gbc_deliverableSourceCode1Panel.gridheight = 2;
		gbc_deliverableSourceCode1Panel.insets = new Insets(0, 0, 5, 0);
		gbc_deliverableSourceCode1Panel.fill = GridBagConstraints.BOTH;
		gbc_deliverableSourceCode1Panel.gridx = 0;
		gbc_deliverableSourceCode1Panel.gridy = 2;
		tabulatedSubmissionsPanel.add(deliverableSourceCode1Panel, gbc_deliverableSourceCode1Panel);
		
		RSyntaxTextArea editorPane = new RSyntaxTextArea();
		editorPane.setText("#include <stdio.h>\r\nint main()\r\n{\r\n\tprintf(\"Hello World!\");\r\n\tgetch();\r\n\treturn 0;\r\n}");
		editorPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C);
		editorPane.setCodeFoldingEnabled(true);
		deliverableSourceCode1Panel.setLayout(new BorderLayout(0, 0));
		RTextScrollPane scrollPane = new RTextScrollPane(editorPane);
		scrollPane.setIconRowHeaderEnabled(true);
		deliverableSourceCode1Panel.add(scrollPane);
		
		JLabel studentID5Label = new JLabel("11280960");
		GridBagConstraints gbc_studentID5Label = new GridBagConstraints();
		gbc_studentID5Label.fill = GridBagConstraints.BOTH;
		gbc_studentID5Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentID5Label.gridx = 0;
		gbc_studentID5Label.gridy = 4;
		tabulatedSubmissionsPanel.add(studentID5Label, gbc_studentID5Label);
		
		JLabel studentName5Label = new JLabel("Gates, Bill");
		GridBagConstraints gbc_studentName5Label = new GridBagConstraints();
		gbc_studentName5Label.fill = GridBagConstraints.BOTH;
		gbc_studentName5Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentName5Label.gridx = 1;
		gbc_studentName5Label.gridy = 4;
		tabulatedSubmissionsPanel.add(studentName5Label, gbc_studentName5Label);
		
		JLabel label = new JLabel("S11A");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 4;
		tabulatedSubmissionsPanel.add(label, gbc_label);
		
		JLabel studentSourceCode5Label = new JLabel("Gates_Bill_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode5Label = new GridBagConstraints();
		gbc_studentSourceCode5Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode5Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSourceCode5Label.gridx = 3;
		gbc_studentSourceCode5Label.gridy = 4;
		tabulatedSubmissionsPanel.add(studentSourceCode5Label, gbc_studentSourceCode5Label);
		
		JToggleButton showSourceCode2 = new JToggleButton("+");
		GridBagConstraints gbc_showSourceCode2 = new GridBagConstraints();
		gbc_showSourceCode2.insets = new Insets(0, 0, 5, 5);
		gbc_showSourceCode2.gridx = 4;
		gbc_showSourceCode2.gridy = 4;
		tabulatedSubmissionsPanel.add(showSourceCode2, gbc_showSourceCode2);
		
		JButton button_1 = new JButton("Test");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 5;
		gbc_button_1.gridy = 4;
		tabulatedSubmissionsPanel.add(button_1, gbc_button_1);
		
		JLabel deliverableDateSubmitted2Label = new JLabel("1/3/2016 2:06:57 PM UTC+0800");
		GridBagConstraints gbc_deliverableDateSubmitted2Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted2Label.anchor = GridBagConstraints.EAST;
		gbc_deliverableDateSubmitted2Label.fill = GridBagConstraints.VERTICAL;
		gbc_deliverableDateSubmitted2Label.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmitted2Label.gridx = 6;
		gbc_deliverableDateSubmitted2Label.gridy = 4;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted2Label, gbc_deliverableDateSubmitted2Label);
		
		textField_2 = new JTextField();
		textField_2.setText("92");
		textField_2.setColumns(10);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 7;
		gbc_textField_2.gridy = 4;
		tabulatedSubmissionsPanel.add(textField_2, gbc_textField_2);
		
		JLabel studentID3Label = new JLabel("11021333");
		GridBagConstraints gbc_studentID3Label = new GridBagConstraints();
		gbc_studentID3Label.fill = GridBagConstraints.BOTH;
		gbc_studentID3Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentID3Label.gridx = 0;
		gbc_studentID3Label.gridy = 5;
		tabulatedSubmissionsPanel.add(studentID3Label, gbc_studentID3Label);
		
		JLabel studentName3Label = new JLabel("Jobs, Steve");
		GridBagConstraints gbc_studentName3Label = new GridBagConstraints();
		gbc_studentName3Label.fill = GridBagConstraints.BOTH;
		gbc_studentName3Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentName3Label.gridx = 1;
		gbc_studentName3Label.gridy = 5;
		tabulatedSubmissionsPanel.add(studentName3Label, gbc_studentName3Label);
		
		JLabel label_1 = new JLabel("S11A");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 2;
		gbc_label_1.gridy = 5;
		tabulatedSubmissionsPanel.add(label_1, gbc_label_1);
		
		JLabel studentSourceCode3Label = new JLabel("Jobs_Steve_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode3Label = new GridBagConstraints();
		gbc_studentSourceCode3Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode3Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSourceCode3Label.gridx = 3;
		gbc_studentSourceCode3Label.gridy = 5;
		tabulatedSubmissionsPanel.add(studentSourceCode3Label, gbc_studentSourceCode3Label);
		
		JToggleButton showSourceCode3 = new JToggleButton("+");
		GridBagConstraints gbc_showSourceCode3 = new GridBagConstraints();
		gbc_showSourceCode3.insets = new Insets(0, 0, 5, 5);
		gbc_showSourceCode3.gridx = 4;
		gbc_showSourceCode3.gridy = 5;
		tabulatedSubmissionsPanel.add(showSourceCode3, gbc_showSourceCode3);
		
		JButton button_2 = new JButton("Test");
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 5, 5);
		gbc_button_2.gridx = 5;
		gbc_button_2.gridy = 5;
		tabulatedSubmissionsPanel.add(button_2, gbc_button_2);
		
		JLabel deliverableDateSubmitted3Label = new JLabel("1/5/2016 3:22:18 PM UTC+0800");
		deliverableDateSubmitted3Label.setForeground(Color.RED);
		GridBagConstraints gbc_deliverableDateSubmitted3Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted3Label.anchor = GridBagConstraints.EAST;
		gbc_deliverableDateSubmitted3Label.fill = GridBagConstraints.VERTICAL;
		gbc_deliverableDateSubmitted3Label.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmitted3Label.gridx = 6;
		gbc_deliverableDateSubmitted3Label.gridy = 5;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted3Label, gbc_deliverableDateSubmitted3Label);
		
		textField_3 = new JTextField();
		textField_3.setText("75");
		textField_3.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 7;
		gbc_textField_3.gridy = 5;
		tabulatedSubmissionsPanel.add(textField_3, gbc_textField_3);
		
		JLabel studentID1Label = new JLabel("10987654");
		GridBagConstraints gbc_studentID1Label = new GridBagConstraints();
		gbc_studentID1Label.fill = GridBagConstraints.BOTH;
		gbc_studentID1Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentID1Label.gridx = 0;
		gbc_studentID1Label.gridy = 6;
		tabulatedSubmissionsPanel.add(studentID1Label, gbc_studentID1Label);
		
		JLabel studentName1Label = new JLabel("Nielson, Niels");
		GridBagConstraints gbc_studentName1Label = new GridBagConstraints();
		gbc_studentName1Label.fill = GridBagConstraints.BOTH;
		gbc_studentName1Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentName1Label.gridx = 1;
		gbc_studentName1Label.gridy = 6;
		tabulatedSubmissionsPanel.add(studentName1Label, gbc_studentName1Label);
		
		JLabel label_2 = new JLabel("S11A");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.WEST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 2;
		gbc_label_2.gridy = 6;
		tabulatedSubmissionsPanel.add(label_2, gbc_label_2);
		
		JLabel studentSourceCode1Label = new JLabel("Nielson_Niels_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode1Label = new GridBagConstraints();
		gbc_studentSourceCode1Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode1Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSourceCode1Label.gridx = 3;
		gbc_studentSourceCode1Label.gridy = 6;
		tabulatedSubmissionsPanel.add(studentSourceCode1Label, gbc_studentSourceCode1Label);
		
		JToggleButton showSourceCode4 = new JToggleButton("+");
		GridBagConstraints gbc_showSourceCode4 = new GridBagConstraints();
		gbc_showSourceCode4.insets = new Insets(0, 0, 5, 5);
		gbc_showSourceCode4.gridx = 4;
		gbc_showSourceCode4.gridy = 6;
		tabulatedSubmissionsPanel.add(showSourceCode4, gbc_showSourceCode4);
		
		JButton button_3 = new JButton("Test");
		GridBagConstraints gbc_button_3 = new GridBagConstraints();
		gbc_button_3.insets = new Insets(0, 0, 5, 5);
		gbc_button_3.gridx = 5;
		gbc_button_3.gridy = 6;
		tabulatedSubmissionsPanel.add(button_3, gbc_button_3);
		
		JLabel deliverableDateSubmitted4Label = new JLabel("1/3/2016 1:20:05 PM UTC+0800");
		GridBagConstraints gbc_deliverableDateSubmitted4Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted4Label.anchor = GridBagConstraints.EAST;
		gbc_deliverableDateSubmitted4Label.fill = GridBagConstraints.VERTICAL;
		gbc_deliverableDateSubmitted4Label.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmitted4Label.gridx = 6;
		gbc_deliverableDateSubmitted4Label.gridy = 6;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted4Label, gbc_deliverableDateSubmitted4Label);
		
		textField_4 = new JTextField();
		textField_4.setText("65");
		textField_4.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 7;
		gbc_textField_4.gridy = 6;
		tabulatedSubmissionsPanel.add(textField_4, gbc_textField_4);
		
		JLabel studentID2Label = new JLabel("10864213");
		GridBagConstraints gbc_studentID2Label = new GridBagConstraints();
		gbc_studentID2Label.fill = GridBagConstraints.BOTH;
		gbc_studentID2Label.insets = new Insets(0, 0, 0, 5);
		gbc_studentID2Label.gridx = 0;
		gbc_studentID2Label.gridy = 7;
		tabulatedSubmissionsPanel.add(studentID2Label, gbc_studentID2Label);
		
		JLabel studentName2Label = new JLabel("Rizal, Jose");
		GridBagConstraints gbc_studentName2Label = new GridBagConstraints();
		gbc_studentName2Label.fill = GridBagConstraints.BOTH;
		gbc_studentName2Label.insets = new Insets(0, 0, 0, 5);
		gbc_studentName2Label.gridx = 1;
		gbc_studentName2Label.gridy = 7;
		tabulatedSubmissionsPanel.add(studentName2Label, gbc_studentName2Label);
		
		JLabel label_3 = new JLabel("S11A");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.WEST;
		gbc_label_3.insets = new Insets(0, 0, 0, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 7;
		tabulatedSubmissionsPanel.add(label_3, gbc_label_3);
		
		JLabel studentSourceCode2Label = new JLabel("Rizal_Jose_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode2Label = new GridBagConstraints();
		gbc_studentSourceCode2Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode2Label.insets = new Insets(0, 0, 0, 5);
		gbc_studentSourceCode2Label.gridx = 3;
		gbc_studentSourceCode2Label.gridy = 7;
		tabulatedSubmissionsPanel.add(studentSourceCode2Label, gbc_studentSourceCode2Label);
		
		JToggleButton showSourceCode5 = new JToggleButton("+");
		GridBagConstraints gbc_showSourceCode5 = new GridBagConstraints();
		gbc_showSourceCode5.insets = new Insets(0, 0, 0, 5);
		gbc_showSourceCode5.gridx = 4;
		gbc_showSourceCode5.gridy = 7;
		tabulatedSubmissionsPanel.add(showSourceCode5, gbc_showSourceCode5);
		
		JButton button_4 = new JButton("Test");
		GridBagConstraints gbc_button_4 = new GridBagConstraints();
		gbc_button_4.insets = new Insets(0, 0, 0, 5);
		gbc_button_4.gridx = 5;
		gbc_button_4.gridy = 7;
		tabulatedSubmissionsPanel.add(button_4, gbc_button_4);
		
		JLabel deliverableDateSubmitted5Label = new JLabel("1/5/2016 3:16:41 PM UTC+0800");
		deliverableDateSubmitted5Label.setForeground(Color.RED);
		GridBagConstraints gbc_deliverableDateSubmitted5Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted5Label.anchor = GridBagConstraints.EAST;
		gbc_deliverableDateSubmitted5Label.fill = GridBagConstraints.VERTICAL;
		gbc_deliverableDateSubmitted5Label.insets = new Insets(0, 0, 0, 5);
		gbc_deliverableDateSubmitted5Label.gridx = 6;
		gbc_deliverableDateSubmitted5Label.gridy = 7;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted5Label, gbc_deliverableDateSubmitted5Label);
		
		textField_5 = new JTextField();
		textField_5.setText("-1");
		textField_5.setColumns(10);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 7;
		gbc_textField_5.gridy = 7;
		tabulatedSubmissionsPanel.add(textField_5, gbc_textField_5);
		
		JComboBox activitySelectionCBox = new JComboBox();
		activitySelectionCBox.setModel(new DefaultComboBoxModel(new String[] {"Basic Functions", "Conditional Statements", "Loops", "Functions", "ASCII Art I"}));
		activitySelectionCBox.setBounds(111, 8, 170, 20);
		gradesFrame.getContentPane().add(activitySelectionCBox);
		
		JButton submitButton = new JButton("Submit Grades");
		submitButton.setBounds(615, 7, 119, 23);
		gradesFrame.getContentPane().add(submitButton);
		
		JLabel sectionNameLabel = new JLabel("Select Section:");
		sectionNameLabel.setBounds(291, 11, 91, 14);
		gradesFrame.getContentPane().add(sectionNameLabel);
		
		JComboBox sectionSelectionCBox = new JComboBox();
		sectionSelectionCBox.setModel(new DefaultComboBoxModel(new String[] {"S11A", "S11B", "S12A"}));
		sectionSelectionCBox.setBounds(395, 8, 60, 20);
		gradesFrame.getContentPane().add(sectionSelectionCBox);
		
		txtDeadlineOfBasic = new JTextField();
		txtDeadlineOfBasic.setText("Deadline of Basic Functions: 1/4/2016 2:30 PM UTC +0800");
		txtDeadlineOfBasic.setEditable(false);
		txtDeadlineOfBasic.setColumns(10);
		txtDeadlineOfBasic.setBounds(0, 443, 744, 20);
		gradesFrame.getContentPane().add(txtDeadlineOfBasic);
	}
}
