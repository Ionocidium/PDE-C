package api.component;

import javax.swing.JOptionPane;

public class PDialogBox{

	public static final int INFORMATION = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;
	public static final int SIMPLE = 3;
	
	private String title;
	private String message;
	private int type;
	
	public PDialogBox(String title, String message, int MESSAGE_TYPE)
	{
		this.title = title;
		this.message = message;
		this.type = MESSAGE_TYPE;
	}
	
	public void showMessage()
	{
		switch (type)
		{
		case 0:
			JOptionPane.showMessageDialog(null, message);
			break;
		case 1:
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
			break;
		case 2:
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
			break;
		case 3:
			JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
			break;
		}
	}
	
}
