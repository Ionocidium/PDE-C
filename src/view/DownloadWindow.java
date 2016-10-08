package view;

import java.io.BufferedReader;
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
import java.awt.event.ActionEvent;

public class DownloadWindow extends JFrame
{

  private JPanel contentPane;

  /**
   * Launch the application.
   */

  /**
   * Create the frame.
   */
  public DownloadWindow()
  {
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
  	
  	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 328, 159);
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
	actList.setSelectedIndex(0);
	contentPane.add(actList);
	
	JButton btnDownload = new JButton("Download");
	btnDownload.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
		  try
		  {
			client.getActivityFile(actList.getSelectedIndex());
			JOptionPane.showMessageDialog(null, "File download complete.", "", JOptionPane.INFORMATION_MESSAGE);
		
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
