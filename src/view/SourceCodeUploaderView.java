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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;


import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Deliverable;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class SourceCodeUploaderView {

	private JFrame frmActivityUpload;
	private JTextField txtCrecursionpdf;
	private FileLoad loader;
	private Path file;
	private JTextField idNumField;
	private JFileChooser fileChooser;
	private FileNameExtensionFilter cFilter;

	public SourceCodeUploaderView(Path filePath, JTextArea frame) {
	  	file = filePath;
	  	loader = new FileLoad();
	  	fileChooser= new JFileChooser();
	  	cFilter = new FileNameExtensionFilter(
			"C Source (*.c)", "c");
	  	fileChooser.setFileFilter(cFilter);
	  	
		initialize(frame);
	}

	private void initialize(JTextArea frame) {
		frmActivityUpload = new JFrame();
		frmActivityUpload.setVisible(true);
		frmActivityUpload.setTitle("Source Code Upload");
		frmActivityUpload.setBounds(100, 100, 450, 177);
		frmActivityUpload.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmActivityUpload.getContentPane().setLayout(null);
		
		JLabel lblActivityName = new JLabel("Select Activity:");
		lblActivityName.setBounds(10, 14, 100, 14);
		frmActivityUpload.getContentPane().add(lblActivityName);
		
		JLabel lblActivityFileName = new JLabel("Source Code:");
		lblActivityFileName.setBounds(10, 39, 100, 14);
		frmActivityUpload.getContentPane().add(lblActivityFileName);
		
		txtCrecursionpdf = new JTextField();
		txtCrecursionpdf.setEditable(false);
		txtCrecursionpdf.setText(file.getFileName().toString());
		txtCrecursionpdf.setBounds(120, 36, 212, 20);
		frmActivityUpload.getContentPane().add(txtCrecursionpdf);
		txtCrecursionpdf.setColumns(10);
		
		JButton btnUpload = new JButton("Browse...");
		btnUpload.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{			  
			  int returnVal = fileChooser.showOpenDialog(null);
			  if (returnVal == JFileChooser.APPROVE_OPTION)
			  {
				file = Paths.get(fileChooser.getSelectedFile().getAbsolutePath());
				txtCrecursionpdf.setText(file.getFileName().toString());
			  }
			  
			  else
			  {
				file = null;
			  }
			}
		});
		btnUpload.setBounds(335, 35, 89, 23);
		frmActivityUpload.getContentPane().add(btnUpload);
//		btnUpload.addActionListener(new ActionListener() 
//		{
//			public void actionPerformed(ActionEvent e) 
//			{
//			  chooseFile(frmActivityUpload); // Returns FilePath. Upload not yet implemented
//			}
//		});
		
		JLabel lblIdNumber = new JLabel("ID Number:");
		lblIdNumber.setBounds(10, 70, 100, 14);
		frmActivityUpload.getContentPane().add(lblIdNumber);
		
		idNumField = new JTextField();
		idNumField.setBounds(120, 67, 212, 20);
		frmActivityUpload.getContentPane().add(idNumField);
		idNumField.setColumns(10);
		
		JComboBox comboBox_6 = new JComboBox();
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] {"Basic Functions", "Conditional Satements", "Functions", "Loops", "ASCII Art I", "Recursion"}));
		comboBox_6.setSelectedIndex(5);
		comboBox_6.setBounds(120, 11, 304, 20);
		frmActivityUpload.getContentPane().add(comboBox_6);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{			  
			  Deliverable deliver = new Deliverable(1, Integer.parseInt(idNumField.getText()), 1, new File(file.toUri()), new Timestamp(System.currentTimeMillis()), file.getFileName().toString(), -1.0f);
	
			  try
			  {
			    deliver.sendData();
			    frmActivityUpload.setVisible(false);
			  }
			  catch(Exception ex)
			  {
				StringBuilder sb = new StringBuilder(ex.toString());
			    for (StackTraceElement ste : ex.getStackTrace()) {
			        sb.append("\n\tat ");
			        sb.append(ste);
			    }
			    String trace = sb.toString();
			    
			    frame.setForeground(Color.RED);
			    frame.setText(trace);
			  }
			}
		});
		btnSubmit.setBounds(335, 105, 89, 23);
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
			if (loader.checker(ext))
			{
				txtCrecursionpdf.setText(ext);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Not a C source code.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		return filePath;
	}
}
