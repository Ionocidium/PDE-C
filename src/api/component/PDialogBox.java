package api.component;

import javax.swing.JOptionPane;
/**
 * This class uses JOptionPane to add dialog boxes in PDE-C.
 * 
 * @author Lorenzo Miguel Monzon
 *
 */
public class PDialogBox{

	/** 
	 * <p>
	 * There are four types of dialog boxes in PDE-C
	 * <ul>
	 *  <li>INFORMATION - A dialog box that only contains a message</li>
	 *  <li>WARNING - A dialog box that contains a title, message and a warning symbol</li>
	 *  <li>ERROR - A dialog box that contains a title, message and an error symbol</li>
	 *  <li>SIMPLE - A dialog box that contains a title and message</li>
	 *  </ul>
	 * </p>
	 */
	public static final int INFORMATION = 0;
	public static final int WARNING = 1;
	public static final int ERROR = 2;
	public static final int SIMPLE = 3;
	
	private String title;
	private String message;
	private int type;
	
	  /**
	   * The constructor of PDialogBox
	   * @param title this is the name of the dialog box
	   * @param message this is the message of the dialog box
	   * @param MESSAGE_TYPE this is the type of dialog box. Refer to default values for each message type.
	   */
	public PDialogBox(String title, String message, int MESSAGE_TYPE)
	{
		this.title = title;
		this.message = message;
		this.type = MESSAGE_TYPE;
	}
	
	/**
	 * This method shows the dialog box in PDE-C
	 */
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
