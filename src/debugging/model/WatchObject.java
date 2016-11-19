package debugging.model;

public class WatchObject {
	
	private String variable;
	private String oldValue;
	private String newValue;
	
	/**
	 * @param variable The local variable being watched
	 * @param oldValue The old value of the local variable
	 * @param newValue The new value of the local variable
	 */
	public WatchObject(String variable, String oldValue, String newValue) {
		this.variable = variable;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	/**
	 * @return The local variable being watched
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
	 * @return the The old value of the local variable
	 */
	public String getOldValue() {
		return oldValue;
	}

	/**
	 * @param oldValue the oldValue to set
	 */
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	/**
	 * @return the The new value of the local variable
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
}
