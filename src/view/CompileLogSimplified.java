package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.JTextPane;
import java.awt.Font;

public class CompileLogSimplified {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CompileLogSimplified window = new CompileLogSimplified();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CompileLogSimplified() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane((Component) null);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JTextPane txtpnHereIsThe = new JTextPane();
		txtpnHereIsThe.setText("Here is the standard output of the command:\r\n\r\nHere is the standard error of the command:\r\nline number 5: 'a' is not considered as an integer of x variable.\r\nline number 15: 'getch()' is not a recognized function.\r\n\r\nFeedback Level: 4\r\nThis is the correct code: \r\n\r\n#include <stdio.h>\r\nint main()\r\n{\r\n\tint x, y, z;\r\n\tx = 4;\r\n\ty = 1;\r\n\tz = x * y;\r\n\r\n\twhile (z <= 496)\r\n\t{\r\n\t\tprintf(\"%d \", z);\r\n\t\ty++;\r\n\t\tz = x*y;\r\n\t}\r\n\tgetchar();\r\n\treturn 0;\r\n}\r\n");
		txtpnHereIsThe.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtpnHereIsThe.setEditable(false);
		txtpnHereIsThe.setCaretPosition(0);
		scrollPane.setViewportView(txtpnHereIsThe);
	}

}
