package api.component;

import javax.swing.JOptionPane;

public class POptionBox {
	
	private String title;
	private String message;
	
	public POptionBox(String title, String message)
	{
		this.title = title;
		this.message = message;
	}
	
	public int showMessage()
	{
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
	}
}
