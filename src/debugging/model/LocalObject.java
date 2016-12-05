package debugging.model;

/**
 * The model representation of Local Variables and its partner value for the said variable.<br>
 * <p>
 * Each object is represented as follows:<br>
 * <code>variable</code> - the local variable declared in the said source code.<br>
 * <code>value</code> - the default value declared in the said source code. If it's not explicitly declared in the source code, then the value is defaulted according to the runtime.<br>
 * <br>
 * Example:<br>
 * <code>
 * a = 2
 * </code>
 * <br>
 * will be translated to the following:<br>
 * <br>
 *	<table style="border: 1px solid black;">
 *		<tr>
 *  		<td style="border: 1px solid black;"><code>variable</code></td>
 *  		<td style="border: 1px solid black;"><code>a</code></td>
 *		</tr>
 *		<tr>
 *  		<td style="border: 1px solid black;"><code>value</code></td>
 *  		<td style="border: 1px solid black;"><code>2</code></td>
 *		</tr>
 *	</table>
 * </p>
 * @author In Yong S. Lee
 *
 */

public class LocalObject {

	private String variable;
	private String value;
	
	/**
	 * @param variable The local variable declared by the novice programmer.
	 * @param value The local value in the local variable explicitly defined by the novice programmer. If it's not explicitly declared in the source code, then the value is defaulted according to the runtime.
	 */
	
	public LocalObject(String variable, String value) {
		this.variable = variable;
		this.value = value;
	}

	/**
	 * @return The local variable declared by the novice programmer.
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * @param variable The local variable to be set.
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}

	/**
	 * @return The local value in the local variable explicitly defined by the novice programmer. If it's not explicitly declared in the source code, then the value is defaulted according to the runtime.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value The value to be set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
