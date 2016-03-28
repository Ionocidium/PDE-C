package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class FindWindow {

	private JFrame findAndReplaceFrame;
	private JTextField findContentTextField2;
	private JTextField replaceContentTextField;
	private JTextField findContentTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FindWindow window = new FindWindow();
					window.findAndReplaceFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FindWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		findAndReplaceFrame = new JFrame();
		findAndReplaceFrame.setTitle("Find and Replace...");
		findAndReplaceFrame.setResizable(false);
		findAndReplaceFrame.setBounds(100, 100, 640, 240);
		findAndReplaceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		findAndReplaceFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		findAndReplaceFrame.getContentPane().add(tabbedPane);
		
		JPanel findPanel = new JPanel();
		tabbedPane.addTab("Find", null, findPanel, null);
		findPanel.setLayout(null);
		
		JLabel findWhatLabel = new JLabel("Find what:");
		findWhatLabel.setHorizontalAlignment(SwingConstants.LEFT);
		findWhatLabel.setBounds(10, 15, 128, 14);
		findPanel.add(findWhatLabel);
		
		findContentTextField = new JTextField();
		findContentTextField.setColumns(10);
		findContentTextField.setBounds(141, 12, 343, 20);
		findPanel.add(findContentTextField);
		
		JButton findNextButton = new JButton("Find Next");
		findNextButton.setBounds(494, 11, 125, 23);
		findPanel.add(findNextButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(494, 39, 125, 23);
		findPanel.add(cancelButton);
		
		JCheckBox matchCaseCheckBox = new JCheckBox("Match case");
		matchCaseCheckBox.setBounds(141, 39, 128, 23);
		findPanel.add(matchCaseCheckBox);
		
		JPanel replacePanel = new JPanel();
		tabbedPane.addTab("Replace", null, replacePanel, null);
		replacePanel.setLayout(null);
		
		JLabel findWhatLabel2 = new JLabel("Find what:");
		findWhatLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		findWhatLabel2.setBounds(10, 15, 128, 14);
		replacePanel.add(findWhatLabel2);
		
		findContentTextField2 = new JTextField();
		findContentTextField2.setColumns(10);
		findContentTextField2.setBounds(141, 12, 343, 20);
		replacePanel.add(findContentTextField2);
		
		JLabel replaceWithLabel = new JLabel("Replace with:");
		replaceWithLabel.setHorizontalAlignment(SwingConstants.LEFT);
		replaceWithLabel.setBounds(10, 72, 128, 14);
		replacePanel.add(replaceWithLabel);
		
		replaceContentTextField = new JTextField();
		replaceContentTextField.setColumns(10);
		replaceContentTextField.setBounds(141, 69, 343, 20);
		replacePanel.add(replaceContentTextField);
		
		JButton findNextButton2 = new JButton("Find Next");
		findNextButton2.setBounds(494, 11, 125, 23);
		replacePanel.add(findNextButton2);
		
		JButton replaceButton = new JButton("Replace");
		replaceButton.setBounds(494, 39, 125, 23);
		replacePanel.add(replaceButton);
		
		JButton replaceAllButton = new JButton("Replace All");
		replaceAllButton.setBounds(494, 68, 125, 23);
		replacePanel.add(replaceAllButton);
		
		JButton cancelButton2 = new JButton("Cancel");
		cancelButton2.setBounds(494, 97, 125, 23);
		replacePanel.add(cancelButton2);
		
		JCheckBox matchCaseCheckBox2 = new JCheckBox("Match case");
		matchCaseCheckBox2.setBounds(141, 39, 128, 23);
		replacePanel.add(matchCaseCheckBox2);
	}
}
