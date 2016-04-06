package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

public class ServerScreen {

	private JFrame frmServerMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerScreen window = new ServerScreen();
					window.frmServerMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServerMenu = new JFrame();
		frmServerMenu.setTitle("Server Menu");
		frmServerMenu.setBounds(100, 100, 270, 186);
		frmServerMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServerMenu.getContentPane().setLayout(null);
		
		JButton btnUploadActivity = new JButton("Upload Activity");
		btnUploadActivity.setBounds(10, 11, 233, 23);
		frmServerMenu.getContentPane().add(btnUploadActivity);
		
		JButton btnUploadedActivityList = new JButton("Uploaded Activity List");
		btnUploadedActivityList.setBounds(10, 45, 233, 23);
		frmServerMenu.getContentPane().add(btnUploadedActivityList);
		
		JButton btnStudentProfiles = new JButton("Student Lists");
		btnStudentProfiles.setBounds(10, 79, 233, 23);
		frmServerMenu.getContentPane().add(btnStudentProfiles);
		
		JButton btnStudentDeliverableList = new JButton("Students Deliverable List");
		btnStudentDeliverableList.setBounds(10, 113, 233, 23);
		frmServerMenu.getContentPane().add(btnStudentDeliverableList);
	}
}
