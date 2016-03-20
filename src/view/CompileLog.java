package view;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTextPane;

import controller.CommandLineControls;

import java.awt.Font;
import java.io.IOException;

public class CompileLog {

	private JFrame frmCompileLog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompileLog window = new CompileLog();
					window.frmCompileLog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public CompileLog() throws IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws IOException{
		frmCompileLog = new JFrame();
		frmCompileLog.setTitle("Compile Log");
		frmCompileLog.setBounds(100, 100, 718, 394);
		frmCompileLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompileLog.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JTextPane txtpnTest = new JTextPane();
		txtpnTest.setFont(new Font("Monospaced", Font.PLAIN, 12));
		CommandLineControls clc = new CommandLineControls("C:\\Users\\InYong\\Documents\\test.c");
		txtpnTest.setText(clc.getStdOut() + "\n" + clc.getStdError());
		frmCompileLog.getContentPane().add(txtpnTest, BorderLayout.CENTER);
	}

}
