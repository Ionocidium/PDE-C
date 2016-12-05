package debugging.model;

/**
 * This is supposed to be a future implementation for the future researchers who wishes to work on this project.<br>
 * <p>
 * The idea is to be able to compare two values whether the old and new value has been changed given the syntax and logic made by the novice programmer.
 * Each object is represented as follows:<br>
 * <code>variable</code> - the local variable declared in the said source code.<br>
 * <code>oldValue</code> - the value before the <code>newValue</code>. If it's not explicitly declared in the source code to begin with, then the value is defaulted according to the runtime. This is usually based on the current <code>LocalObject</code> <b>before</b> the debug commands have been issued.<br>
 * <code>newValue</code> - the value that has been changed after the <code>oldValue</code> and the newValue has differences. This is usually based on the current <code>LocalObject</code> <b>after</b> the debug commands have been issued.<br>
 * <br>
 * Example:<br>
 * <code>
 * a = 2<br>
 * a = 3
 * </code>
 * 
 * will be translated to the following:<br>
 * <br>
 *	<table style="border: 1px solid black;">
 *		<tr>
 *  		<td style="border: 1px solid black;"><code>variable</code></td>
 *  		<td style="border: 1px solid black;">a</td>
 *		</tr>
 *		<tr>
 *  		<td style="border: 1px solid black;"><code>oldValue</code></td>
 *  		<td style="border: 1px solid black;">2</td>
 *		</tr>
 *		<tr>
 *  		<td style="border: 1px solid black;"><code>newValue</code></td>
 *  		<td style="border: 1px solid black;">3</td>
 *		</tr>
 *	</table>
 * </p>
 * @author In Yong S. Lee
 *
 */

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
