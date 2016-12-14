package api.component;

import javax.swing.JOptionPane;
/**
 * This class uses JOptionPane to add option boxes in PDE-C. The option box will always have a Yes or No option.
 * 
 * @author Lorenzo Miguel Monzon
 *
 */

public class POptionBox {
	
	private String title;
	private String message;
	
	  /**
	   * The constructor of PDialogBox
	   * @param title this is the name of the option box
	   * @param message this is the message of the option box
	   */
	public POptionBox(String title, String message)
	{
		this.title = title;
		this.message = message;
	}
	
	/**
	 * This method shows the option box in PDE-C
	 * @return the integer value (0 for No, 1 for Yes) of the selected option
	 */
	public int showMessage()
	{
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
	}
}
