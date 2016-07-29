package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.fileops.FileLoad;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;

public class ActivityUploaderView {

	private JFrame frmActivityUpload;
	private JTextField txtRecursion;
	private JTextField txtCrecursionpdf;
	private final JFileChooser fileChooser;
	private FileLoad loader;
	private FileNameExtensionFilter pdfFilter;

	public ActivityUploaderView() {
		loader = new FileLoad();
		fileChooser = new JFileChooser();
		pdfFilter = new FileNameExtensionFilter(
					"PDF Documents", "pdf");
		fileChooser.setFileFilter(pdfFilter);
		initialize();
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActivityUploaderView window = new ActivityUploaderView();
					window.frmActivityUpload.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmActivityUpload = new JFrame();
		frmActivityUpload.setTitle("Activity Upload");
		frmActivityUpload.setBounds(100, 100, 450, 172);
		frmActivityUpload.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmActivityUpload.getContentPane().setLayout(null);
		
		JLabel lblActivityName = new JLabel("Activity Name:");
		lblActivityName.setBounds(10, 14, 100, 14);
		frmActivityUpload.getContentPane().add(lblActivityName);
		
		JLabel lblActivityFileName = new JLabel("Activity File Name:");
		lblActivityFileName.setBounds(10, 39, 100, 14);
		frmActivityUpload.getContentPane().add(lblActivityFileName);
		
		txtRecursion = new JTextField();
		txtRecursion.setText("Recursion");
		txtRecursion.setBounds(120, 11, 304, 20);
		frmActivityUpload.getContentPane().add(txtRecursion);
		txtRecursion.setColumns(10);
		
		txtCrecursionpdf = new JTextField();
		txtCrecursionpdf.setEditable(false);
		txtCrecursionpdf.setText("C:\\Recursion.pdf");
		txtCrecursionpdf.setBounds(120, 36, 212, 20);
		frmActivityUpload.getContentPane().add(txtCrecursionpdf);
		txtCrecursionpdf.setColumns(10);
		
		JButton btnUpload = new JButton("Browse...");
		btnUpload.setBounds(335, 35, 89, 23);
		frmActivityUpload.getContentPane().add(btnUpload);
		btnUpload.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			  chooseFile(frmActivityUpload); // Returns FilePath. Upload not yet implemented
			}
		});
		
		JLabel lblActivityDeadline = new JLabel("Activity Deadline:");
		lblActivityDeadline.setBounds(10, 70, 100, 14);
		frmActivityUpload.getContentPane().add(lblActivityDeadline);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"hh", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		comboBox.setSelectedIndex(2);
		comboBox.setBounds(120, 98, 64, 20);
		frmActivityUpload.getContentPane().add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"mm", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "00"}));
		comboBox_1.setSelectedIndex(30);
		comboBox_1.setBounds(194, 98, 64, 20);
		frmActivityUpload.getContentPane().add(comboBox_1);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"AM/PM", "AM", "PM"}));
		comboBox_2.setSelectedIndex(2);
		comboBox_2.setBounds(268, 98, 64, 20);
		frmActivityUpload.getContentPane().add(comboBox_2);
		
		JComboBox comboBox_3 = new JComboBox();
		comboBox_3.setModel(new DefaultComboBoxModel(new String[] {"day", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"}));
		comboBox_3.setSelectedIndex(8);
		comboBox_3.setBounds(120, 67, 64, 20);
		frmActivityUpload.getContentPane().add(comboBox_3);
		
		JComboBox comboBox_4 = new JComboBox();
		comboBox_4.setModel(new DefaultComboBoxModel(new String[] {"month", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}));
		comboBox_4.setSelectedIndex(4);
		comboBox_4.setBounds(194, 67, 64, 20);
		frmActivityUpload.getContentPane().add(comboBox_4);
		
		JComboBox comboBox_5 = new JComboBox();
		comboBox_5.setModel(new DefaultComboBoxModel(new String[] {"year", "2016", "2017", "2018"}));
		comboBox_5.setSelectedIndex(1);
		comboBox_5.setBounds(268, 67, 64, 20);
		frmActivityUpload.getContentPane().add(comboBox_5);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(335, 66, 89, 23);
		frmActivityUpload.getContentPane().add(btnSubmit);
	}
	
	public Path chooseFile(JFrame frame)
	{
		int returnVal = fileChooser.showOpenDialog(frame);
		Path filePath = null;
	
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			Path path = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
			filePath = path;
			String ext = path.toString();
			if (loader.checkerpdf(ext))
			{
				txtCrecursionpdf.setText(ext);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Not a PDF File.", "Error", JOptionPane.ERROR_MESSAGE);
				filePath = null;
			}
		}
		return filePath;
	}
	
}
