package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextPane;

import controller.CommandLineControls;
import service.CBRCIntegration;

import java.awt.Font;
import java.io.IOException;
import java.nio.file.Path;

import javax.swing.JScrollPane;

public class CompileLog {

	private JFrame frmCompileLog;
	private Path cPath;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			/* (non-Javadoc)
//			 * @see java.lang.Runnable#run()
//			 */
//			public void run() {
//				try {
//					CompileLog window = new CompileLog();
//					window.frmCompileLog.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public CompileLog(Path path) throws IOException {
	  	this.cPath = path;
		initialize();
	}
	
	public JFrame getFrame()
	{
	  return frmCompileLog;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws IOException{
		frmCompileLog = new JFrame();
		frmCompileLog.setTitle("Compile Log");
		frmCompileLog.setBounds(100, 100, 718, 394);
		frmCompileLog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmCompileLog.getContentPane().setLayout(new BorderLayout(0, 0));
		frmCompileLog.setLocationRelativeTo(null);
		frmCompileLog.setVisible(true);
		CommandLineControls clc = new CommandLineControls(cPath.toString());
		//CBRCIntegration cbrc = new CBRCIntegration();
		
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setEditable(false);
		txtpnTest.setFont(new Font("Monospaced", Font.PLAIN, 12));
		//txtpnTest.setText(clc.getStdOut() + "\n" + clc.getStdError() + "\n" + cbrc.feedback0());
		txtpnTest.setCaretPosition(0);
		// frmCompileLog.getContentPane().add(txtpnTest, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(txtpnTest);
		frmCompileLog.getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

}
