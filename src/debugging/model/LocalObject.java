package debugging.model;

public class LocalObject {

	private String variable;
	private String value;
	
	/**
	 * @param variable The local variable declared by the novice programmer
	 * @param value The local value in the local variable
	 */
	
	public LocalObject(String variable, String value) {
		this.variable = variable;
		this.value = value;
	}

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * @param variable the variable to set
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
