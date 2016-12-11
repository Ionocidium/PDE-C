package model;

/**
 * Handles the encapsulation and representation of <code>Feedback</code> model.
 * 
 * @author Lorenzo Miguel G. Monzon
 */
public class Feedback {

	private String error;
	private String code;
	
	/**
	 * Creates a representation that contains the information for <code>Feedback</code> model.
	 * @param error the error message
	 * @param code the source code
	 */
	public Feedback(String error, String code) {
		this.error = error;
		this.code = code;
	}

	/**
	 * Gets the <code>error</code> property.
	 * @return the <code>error</code>
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the <code>error</code> to its preferred value.
	 * @param error the <code>error</code> to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * Gets the <code>code</code> property.
	 * @return the <code>code</code>
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the <code>code</code> to its preferred value.
	 * @param code the <code>code</code> to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	

}
