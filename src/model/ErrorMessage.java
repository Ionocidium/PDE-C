package model;
/**
 * Handles the encapsulation and representation of <code>ErrorMessage</code> model.
 * 
 * @author Lorenzo Miguel G. Monzon
 */
public class ErrorMessage {

	private String type;
	private String message;
	private String line;
	
	/**
	 * Creates a representation that contains the information for <code>ErrorMessage</code> model.
	 * @param type the type of error
	 * @param message the message contained in the error
	 * @param line the line number of the error message 
	 */
	public ErrorMessage(String type, String message, String line) {
		this.type = type;
		this.message = message;
		this.line = line;
	}
	
	/**
	 * Gets the <code>type</code> property.
	 * @return the <code>type</code>
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the <code>type</code> to its preferred value.
	 * @param type the <code>type</code> to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Gets the <code>message</code> property.
	 * @return the <code>message</code>
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the <code>message</code> to its preferred value.
	 * @param message the <code>message</code> to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * Gets the <code>line</code> property.
	 * @return the <code>line</code>
	 */
	public String getLine() {
		return line;
	}

	/**
	 * Sets the <code>line</code> to its preferred value.
	 * @param line the <code>line</code> to set
	 */
	public void setLine(String line) {
		this.line = line;
	}
	
	/**
	 * Gets the error message of the said line.
	 * @return the error message of the said line
	 */
	public String getErrorMessage() {
		return this.getType() + this.getMessage() + this.getLine();
	}
	
}
