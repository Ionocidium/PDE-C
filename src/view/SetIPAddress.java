package view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import service.ClientService;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SetIPAddress extends JFrame
{

  private JPanel contentPane;

  /**
   * Launch the application.
   */

  /**
   * Create the frame.
   */
  public SetIPAddress()
  {
	this.initialize();
  }
  
  private void initialize()
  {
	setVisible(true);
	setTitle("Change IP Address");
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setBounds(100, 100, 320, 120);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
	
	JTextField ipAddr = new JTextField();
	ipAddr.setBounds(10, 11, 284, 22);
	contentPane.add(ipAddr);
	
	JButton btnSubmit = new JButton("Submit");
	btnSubmit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
		  String ipAddress = ipAddr.getText();
		  if (!ipAddress.trim().equals(""))
		  {
			ClientService client = ClientService.getClientService();
			client.setIPAddress(ipAddress);
			dispose();
		  }
		}
	});
	btnSubmit.setBounds(205, 47, 89, 23);
	contentPane.add(btnSubmit);
	

	this.setLocationRelativeTo(null);
	
	this.getRootPane().setDefaultButton(btnSubmit);
	this.addKeyListener(new KeyAdapter() 
	{
		@Override
		public void keyPressed(KeyEvent arg0) 
		{
		  String ipAddress = ipAddr.getText();
		  if (!ipAddress.trim().equals(""))
		  {
			ClientService client = ClientService.getClientService();
			client.setIPAddress(ipAddress);
			dispose();
		  }
		}
	});
  }
}
