package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.ClientService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Desktop;
import java.awt.event.ActionEvent;

/**
 * Handles the Download Window.
 * <p>
 *  Allows the students to download the activity uploaded by the professor.
 * </p>
 * @author Alexander John D. Jose
 */
public class DownloadWindow extends JFrame
{

  private JPanel contentPane;

  /**
   * Creates the DownloadWindow constructor.
   */
  public DownloadWindow()
  {
  	setResizable(false);
	initialize();
	this.setVisible(true);
  }
  
  private void initialize()
  {
	ArrayList<String> actListArray = new ArrayList<String>();
  	ClientService client = ClientService.getClientService();
  	
  	try
  	{ 
  	  if (!Files.exists(Paths.get("resources/activity.txt")));
  	  {
  		client.initSocket();
	  	client.getActivity();	  	
  	  }
  	  
  	  actListArray = this.readFile(Paths.get("resources/activity.txt"));

  	}
  	
  	catch(Exception ex)
  	{
  	  ex.printStackTrace();
  	}
  	
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 300, 159);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JLabel lblActivityFile = new JLabel("Activity File:");
	lblActivityFile.setBounds(10, 25, 98, 14);
	contentPane.add(lblActivityFile);
	
	JComboBox actList = new JComboBox();
	actList.setBounds(135, 22, 152, 20);
	actList.setModel(new DefaultComboBoxModel(actListArray.toArray()));
	
	try
	{
	  actList.setSelectedIndex(0);
	}
	
	catch (Exception ex)
	{
	  JOptionPane.showMessageDialog(null, "Connection to server failed.", "Server error", JOptionPane.ERROR_MESSAGE);
	  dispose();
	}
	
	contentPane.add(actList);
	
	JButton btnDownload = new JButton("Download");
	btnDownload.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
		  try
		  {
			client.getActivityFile(actList.getSelectedIndex());
			dispose();
		  }
		  catch (Exception e)
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		}
	});
	btnDownload.setBounds(198, 86, 89, 23);
	contentPane.add(btnDownload);
	setLocationRelativeTo(null);
  }
  
  private ArrayList<String> readFile(Path path)
	{
	  Charset charset = Charset.forName("UTF-8");
	  String line = null;
	  ArrayList<String> res = new ArrayList<String>();
		  
	  try (BufferedReader reader = Files.newBufferedReader(path, charset))
	  {		
	    while ((line = reader.readLine()) != null)
		{
		  res.add(line);
		  System.out.println(line);
		}
	  }
		  
	  catch (IOException ex)
	  {
		ex.printStackTrace();
	  }
		  
	  return res;
	}
}
