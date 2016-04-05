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
	private JTextField textField;

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
		gradesFrame.setBounds(100, 100, 720, 502);
		gradesFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gradesFrame.getContentPane().setLayout(null);
		
		JLabel activityNameLabel = new JLabel("Select Activity:");
		activityNameLabel.setBounds(10, 11, 91, 14);
		gradesFrame.getContentPane().add(activityNameLabel);
		
		JPanel tabulatedSubmissionsPanel = new JPanel();
		tabulatedSubmissionsPanel.setBackground(Color.WHITE);
		tabulatedSubmissionsPanel.setBounds(10, 36, 684, 394);
		gradesFrame.getContentPane().add(tabulatedSubmissionsPanel);
		GridBagLayout gbl_tabulatedSubmissionsPanel = new GridBagLayout();
		gbl_tabulatedSubmissionsPanel.columnWidths = new int[]{76, 87, 51, 87, 0, 87, 48, 0};
		gbl_tabulatedSubmissionsPanel.rowHeights = new int[]{35, 35, 0, 0, 35, 35, 35, 35, 0};
		gbl_tabulatedSubmissionsPanel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		gbc_studentSourceCodeLabel.gridwidth = 2;
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
		gbc_deliverableDateSubmittedLabel.gridx = 5;
		gbc_deliverableDateSubmittedLabel.gridy = 0;
		tabulatedSubmissionsPanel.add(deliverableDateSubmittedLabel, gbc_deliverableDateSubmittedLabel);
		
		JLabel deliverableGradeLabel = new JLabel("Grade");
		deliverableGradeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_deliverableGradeLabel = new GridBagConstraints();
		gbc_deliverableGradeLabel.fill = GridBagConstraints.BOTH;
		gbc_deliverableGradeLabel.insets = new Insets(0, 0, 5, 0);
		gbc_deliverableGradeLabel.gridx = 6;
		gbc_deliverableGradeLabel.gridy = 0;
		tabulatedSubmissionsPanel.add(deliverableGradeLabel, gbc_deliverableGradeLabel);
		
		JLabel studentID1Label = new JLabel("10987654");
		GridBagConstraints gbc_studentID1Label = new GridBagConstraints();
		gbc_studentID1Label.fill = GridBagConstraints.BOTH;
		gbc_studentID1Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentID1Label.gridx = 0;
		gbc_studentID1Label.gridy = 1;
		tabulatedSubmissionsPanel.add(studentID1Label, gbc_studentID1Label);
		
		JLabel studentName1Label = new JLabel("Nielson, Niels");
		GridBagConstraints gbc_studentName1Label = new GridBagConstraints();
		gbc_studentName1Label.fill = GridBagConstraints.BOTH;
		gbc_studentName1Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentName1Label.gridx = 1;
		gbc_studentName1Label.gridy = 1;
		tabulatedSubmissionsPanel.add(studentName1Label, gbc_studentName1Label);
		
		JLabel studentSection1Label = new JLabel("S11A");
		GridBagConstraints gbc_studentSection1Label = new GridBagConstraints();
		gbc_studentSection1Label.fill = GridBagConstraints.BOTH;
		gbc_studentSection1Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSection1Label.gridx = 2;
		gbc_studentSection1Label.gridy = 1;
		tabulatedSubmissionsPanel.add(studentSection1Label, gbc_studentSection1Label);
		
		JLabel studentSourceCode1Label = new JLabel("Nielson_Niels_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode1Label = new GridBagConstraints();
		gbc_studentSourceCode1Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode1Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSourceCode1Label.gridx = 3;
		gbc_studentSourceCode1Label.gridy = 1;
		tabulatedSubmissionsPanel.add(studentSourceCode1Label, gbc_studentSourceCode1Label);
		
		JToggleButton showSourceCode1 = new JToggleButton("-");
		showSourceCode1.setSelected(true);
		GridBagConstraints gbc_showSourceCode1 = new GridBagConstraints();
		gbc_showSourceCode1.insets = new Insets(0, 0, 5, 5);
		gbc_showSourceCode1.gridx = 4;
		gbc_showSourceCode1.gridy = 1;
		tabulatedSubmissionsPanel.add(showSourceCode1, gbc_showSourceCode1);
		
		JLabel deliverableDateSubmitted1Label = new JLabel("1/4/2016 1:56:29 PM UTC+0800");
		GridBagConstraints gbc_deliverableDateSubmitted1Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted1Label.fill = GridBagConstraints.BOTH;
		gbc_deliverableDateSubmitted1Label.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmitted1Label.gridx = 5;
		gbc_deliverableDateSubmitted1Label.gridy = 1;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted1Label, gbc_deliverableDateSubmitted1Label);
		
		JComboBox deliverableGrade1ComboBox = new JComboBox();
		deliverableGrade1ComboBox.setModel(new DefaultComboBoxModel(new String[] {"4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0.0", "NGS"}));
		deliverableGrade1ComboBox.setSelectedIndex(0);
		GridBagConstraints gbc_deliverableGrade1ComboBox = new GridBagConstraints();
		gbc_deliverableGrade1ComboBox.fill = GridBagConstraints.BOTH;
		gbc_deliverableGrade1ComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_deliverableGrade1ComboBox.gridx = 6;
		gbc_deliverableGrade1ComboBox.gridy = 1;
		tabulatedSubmissionsPanel.add(deliverableGrade1ComboBox, gbc_deliverableGrade1ComboBox);
		
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
		
		JPanel deliverableSourceCode1Panel = new JPanel();
		GridBagConstraints gbc_deliverableSourceCode1Panel = new GridBagConstraints();
		gbc_deliverableSourceCode1Panel.gridwidth = 7;
		gbc_deliverableSourceCode1Panel.gridheight = 2;
		gbc_deliverableSourceCode1Panel.insets = new Insets(0, 0, 5, 5);
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
		
		JLabel studentID2Label = new JLabel("10864213");
		GridBagConstraints gbc_studentID2Label = new GridBagConstraints();
		gbc_studentID2Label.fill = GridBagConstraints.BOTH;
		gbc_studentID2Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentID2Label.gridx = 0;
		gbc_studentID2Label.gridy = 4;
		tabulatedSubmissionsPanel.add(studentID2Label, gbc_studentID2Label);
		
		JLabel studentName2Label = new JLabel("Rizal, Jose");
		GridBagConstraints gbc_studentName2Label = new GridBagConstraints();
		gbc_studentName2Label.fill = GridBagConstraints.BOTH;
		gbc_studentName2Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentName2Label.gridx = 1;
		gbc_studentName2Label.gridy = 4;
		tabulatedSubmissionsPanel.add(studentName2Label, gbc_studentName2Label);
		
		JLabel studentSection2Label = new JLabel("S11B");
		GridBagConstraints gbc_studentSection2Label = new GridBagConstraints();
		gbc_studentSection2Label.fill = GridBagConstraints.BOTH;
		gbc_studentSection2Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSection2Label.gridx = 2;
		gbc_studentSection2Label.gridy = 4;
		tabulatedSubmissionsPanel.add(studentSection2Label, gbc_studentSection2Label);
		
		JLabel studentSourceCode2Label = new JLabel("Rizal_Jose_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode2Label = new GridBagConstraints();
		gbc_studentSourceCode2Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode2Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSourceCode2Label.gridx = 3;
		gbc_studentSourceCode2Label.gridy = 4;
		tabulatedSubmissionsPanel.add(studentSourceCode2Label, gbc_studentSourceCode2Label);
		
		JToggleButton showSourceCode2 = new JToggleButton("+");
		GridBagConstraints gbc_showSourceCode2 = new GridBagConstraints();
		gbc_showSourceCode2.insets = new Insets(0, 0, 5, 5);
		gbc_showSourceCode2.gridx = 4;
		gbc_showSourceCode2.gridy = 4;
		tabulatedSubmissionsPanel.add(showSourceCode2, gbc_showSourceCode2);
		
		JLabel deliverableDateSubmitted2Label = new JLabel("1/3/2016 2:06:57 PM UTC+0800");
		GridBagConstraints gbc_deliverableDateSubmitted2Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted2Label.fill = GridBagConstraints.BOTH;
		gbc_deliverableDateSubmitted2Label.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmitted2Label.gridx = 5;
		gbc_deliverableDateSubmitted2Label.gridy = 4;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted2Label, gbc_deliverableDateSubmitted2Label);
		
		JComboBox deliverableGrade2ComboBox = new JComboBox();
		deliverableGrade2ComboBox.setModel(new DefaultComboBoxModel(new String[] {"4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0.0", "NGS"}));
		deliverableGrade2ComboBox.setSelectedIndex(1);
		GridBagConstraints gbc_deliverableGrade2ComboBox = new GridBagConstraints();
		gbc_deliverableGrade2ComboBox.fill = GridBagConstraints.BOTH;
		gbc_deliverableGrade2ComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_deliverableGrade2ComboBox.gridx = 6;
		gbc_deliverableGrade2ComboBox.gridy = 4;
		tabulatedSubmissionsPanel.add(deliverableGrade2ComboBox, gbc_deliverableGrade2ComboBox);
		
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
		
		JLabel studentSection3Label = new JLabel("S12A");
		GridBagConstraints gbc_studentSection3Label = new GridBagConstraints();
		gbc_studentSection3Label.fill = GridBagConstraints.BOTH;
		gbc_studentSection3Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSection3Label.gridx = 2;
		gbc_studentSection3Label.gridy = 5;
		tabulatedSubmissionsPanel.add(studentSection3Label, gbc_studentSection3Label);
		
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
		
		JLabel deliverableDateSubmitted3Label = new JLabel("1/5/2016 3:22:18 PM UTC+0800");
		deliverableDateSubmitted3Label.setForeground(Color.RED);
		GridBagConstraints gbc_deliverableDateSubmitted3Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted3Label.fill = GridBagConstraints.BOTH;
		gbc_deliverableDateSubmitted3Label.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmitted3Label.gridx = 5;
		gbc_deliverableDateSubmitted3Label.gridy = 5;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted3Label, gbc_deliverableDateSubmitted3Label);
		
		JComboBox deliverableGrade3ComboBox = new JComboBox();
		deliverableGrade3ComboBox.setModel(new DefaultComboBoxModel(new String[] {"4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0.0", "NGS"}));
		deliverableGrade3ComboBox.setSelectedIndex(4);
		GridBagConstraints gbc_deliverableGrade3ComboBox = new GridBagConstraints();
		gbc_deliverableGrade3ComboBox.fill = GridBagConstraints.BOTH;
		gbc_deliverableGrade3ComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_deliverableGrade3ComboBox.gridx = 6;
		gbc_deliverableGrade3ComboBox.gridy = 5;
		tabulatedSubmissionsPanel.add(deliverableGrade3ComboBox, gbc_deliverableGrade3ComboBox);
		
		JLabel studentID4Label = new JLabel("11133360");
		GridBagConstraints gbc_studentID4Label = new GridBagConstraints();
		gbc_studentID4Label.fill = GridBagConstraints.BOTH;
		gbc_studentID4Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentID4Label.gridx = 0;
		gbc_studentID4Label.gridy = 6;
		tabulatedSubmissionsPanel.add(studentID4Label, gbc_studentID4Label);
		
		JLabel studentName4Label = new JLabel("Aguilar, Fred");
		GridBagConstraints gbc_studentName4Label = new GridBagConstraints();
		gbc_studentName4Label.fill = GridBagConstraints.BOTH;
		gbc_studentName4Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentName4Label.gridx = 1;
		gbc_studentName4Label.gridy = 6;
		tabulatedSubmissionsPanel.add(studentName4Label, gbc_studentName4Label);
		
		JLabel studentSection4Label = new JLabel("S11B");
		GridBagConstraints gbc_studentSection4Label = new GridBagConstraints();
		gbc_studentSection4Label.fill = GridBagConstraints.BOTH;
		gbc_studentSection4Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSection4Label.gridx = 2;
		gbc_studentSection4Label.gridy = 6;
		tabulatedSubmissionsPanel.add(studentSection4Label, gbc_studentSection4Label);
		
		JLabel studentSourceCode4Label = new JLabel("Aguilar_Fred_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode4Label = new GridBagConstraints();
		gbc_studentSourceCode4Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode4Label.insets = new Insets(0, 0, 5, 5);
		gbc_studentSourceCode4Label.gridx = 3;
		gbc_studentSourceCode4Label.gridy = 6;
		tabulatedSubmissionsPanel.add(studentSourceCode4Label, gbc_studentSourceCode4Label);
		
		JToggleButton showSourceCode4 = new JToggleButton("+");
		GridBagConstraints gbc_showSourceCode4 = new GridBagConstraints();
		gbc_showSourceCode4.insets = new Insets(0, 0, 5, 5);
		gbc_showSourceCode4.gridx = 4;
		gbc_showSourceCode4.gridy = 6;
		tabulatedSubmissionsPanel.add(showSourceCode4, gbc_showSourceCode4);
		
		JLabel deliverableDateSubmitted4Label = new JLabel("1/3/2016 1:20:05 PM UTC+0800");
		GridBagConstraints gbc_deliverableDateSubmitted4Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted4Label.fill = GridBagConstraints.BOTH;
		gbc_deliverableDateSubmitted4Label.insets = new Insets(0, 0, 5, 5);
		gbc_deliverableDateSubmitted4Label.gridx = 5;
		gbc_deliverableDateSubmitted4Label.gridy = 6;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted4Label, gbc_deliverableDateSubmitted4Label);
		
		JComboBox deliverableGrade4ComboBox = new JComboBox();
		deliverableGrade4ComboBox.setModel(new DefaultComboBoxModel(new String[] {"4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0.0", "NGS"}));
		deliverableGrade4ComboBox.setSelectedIndex(6);
		GridBagConstraints gbc_deliverableGrade4ComboBox = new GridBagConstraints();
		gbc_deliverableGrade4ComboBox.fill = GridBagConstraints.BOTH;
		gbc_deliverableGrade4ComboBox.insets = new Insets(0, 0, 5, 0);
		gbc_deliverableGrade4ComboBox.gridx = 6;
		gbc_deliverableGrade4ComboBox.gridy = 6;
		tabulatedSubmissionsPanel.add(deliverableGrade4ComboBox, gbc_deliverableGrade4ComboBox);
		
		JLabel studentID5Label = new JLabel("11280960");
		GridBagConstraints gbc_studentID5Label = new GridBagConstraints();
		gbc_studentID5Label.fill = GridBagConstraints.BOTH;
		gbc_studentID5Label.insets = new Insets(0, 0, 0, 5);
		gbc_studentID5Label.gridx = 0;
		gbc_studentID5Label.gridy = 7;
		tabulatedSubmissionsPanel.add(studentID5Label, gbc_studentID5Label);
		
		JLabel studentName5Label = new JLabel("Gates, Bill");
		GridBagConstraints gbc_studentName5Label = new GridBagConstraints();
		gbc_studentName5Label.fill = GridBagConstraints.BOTH;
		gbc_studentName5Label.insets = new Insets(0, 0, 0, 5);
		gbc_studentName5Label.gridx = 1;
		gbc_studentName5Label.gridy = 7;
		tabulatedSubmissionsPanel.add(studentName5Label, gbc_studentName5Label);
		
		JLabel studentSection5Label = new JLabel("S12A");
		GridBagConstraints gbc_studentSection5Label = new GridBagConstraints();
		gbc_studentSection5Label.fill = GridBagConstraints.BOTH;
		gbc_studentSection5Label.insets = new Insets(0, 0, 0, 5);
		gbc_studentSection5Label.gridx = 2;
		gbc_studentSection5Label.gridy = 7;
		tabulatedSubmissionsPanel.add(studentSection5Label, gbc_studentSection5Label);
		
		JLabel studentSourceCode5Label = new JLabel("Gates_Bill_BasicFunctions.c");
		GridBagConstraints gbc_studentSourceCode5Label = new GridBagConstraints();
		gbc_studentSourceCode5Label.fill = GridBagConstraints.BOTH;
		gbc_studentSourceCode5Label.insets = new Insets(0, 0, 0, 5);
		gbc_studentSourceCode5Label.gridx = 3;
		gbc_studentSourceCode5Label.gridy = 7;
		tabulatedSubmissionsPanel.add(studentSourceCode5Label, gbc_studentSourceCode5Label);
		
		JToggleButton showSourceCode5 = new JToggleButton("+");
		GridBagConstraints gbc_showSourceCode5 = new GridBagConstraints();
		gbc_showSourceCode5.insets = new Insets(0, 0, 0, 5);
		gbc_showSourceCode5.gridx = 4;
		gbc_showSourceCode5.gridy = 7;
		tabulatedSubmissionsPanel.add(showSourceCode5, gbc_showSourceCode5);
		
		JLabel deliverableDateSubmitted5Label = new JLabel("1/5/2016 3:16:41 PM UTC+0800");
		deliverableDateSubmitted5Label.setForeground(Color.RED);
		GridBagConstraints gbc_deliverableDateSubmitted5Label = new GridBagConstraints();
		gbc_deliverableDateSubmitted5Label.fill = GridBagConstraints.BOTH;
		gbc_deliverableDateSubmitted5Label.insets = new Insets(0, 0, 0, 5);
		gbc_deliverableDateSubmitted5Label.gridx = 5;
		gbc_deliverableDateSubmitted5Label.gridy = 7;
		tabulatedSubmissionsPanel.add(deliverableDateSubmitted5Label, gbc_deliverableDateSubmitted5Label);
		
		JComboBox deliverableGrade5ComboBox = new JComboBox();
		deliverableGrade5ComboBox.setModel(new DefaultComboBoxModel(new String[] {"4.0", "3.5", "3.0", "2.5", "2.0", "1.5", "1.0", "0.0", "NGS"}));
		deliverableGrade5ComboBox.setSelectedIndex(8);
		GridBagConstraints gbc_deliverableGrade5ComboBox = new GridBagConstraints();
		gbc_deliverableGrade5ComboBox.fill = GridBagConstraints.BOTH;
		gbc_deliverableGrade5ComboBox.gridx = 6;
		gbc_deliverableGrade5ComboBox.gridy = 7;
		tabulatedSubmissionsPanel.add(deliverableGrade5ComboBox, gbc_deliverableGrade5ComboBox);
		
		JComboBox activitySelectionCBox = new JComboBox();
		activitySelectionCBox.setModel(new DefaultComboBoxModel(new String[] {"Basic Functions", "Conditional Statements", "Loops", "Functions", "ASCII Art I"}));
		activitySelectionCBox.setBounds(111, 8, 170, 20);
		gradesFrame.getContentPane().add(activitySelectionCBox);
		
		JButton submitButton = new JButton("Submit Grades");
		submitButton.setBounds(575, 7, 119, 23);
		gradesFrame.getContentPane().add(submitButton);
		
		JLabel sectionNameLabel = new JLabel("Select Section:");
		sectionNameLabel.setBounds(291, 11, 91, 14);
		gradesFrame.getContentPane().add(sectionNameLabel);
		
		JComboBox sectionSelectionCBox = new JComboBox();
		sectionSelectionCBox.setModel(new DefaultComboBoxModel(new String[] {"S11A", "S11B", "S12A"}));
		sectionSelectionCBox.setBounds(395, 8, 60, 20);
		gradesFrame.getContentPane().add(sectionSelectionCBox);
		
		textField = new JTextField();
		textField.setText("Deadline of Basic Functions: 1/4/2016 11:59:59 PM UTC +0800");
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(0, 443, 704, 20);
		gradesFrame.getContentPane().add(textField);
	}
}
