package debugging.controls;

import debugging.model.LocalObject;

/**
 * Extracts the local variables, including its values. <br>
 * <p>
 * Every time A Step Over/Continue is called by the Debugging Manager, gdb's command line interface will let you view the list of local variables. <br>
 * <br>
 * Example:
 * <code>
 * a = 2
 * b = 3
 * c = 4
 * </code>
 * 
 * will be translated to the following:<br>
 * <br>
 *	<table style="border: 1px solid black;" summary="Local Variable Lists Object Representation List">
 *		<tr>
 *  		<th style="border: 1px solid black;">Variable Name</th>
 *  		<th style="border: 1px solid black;">Value</th>
 *		</tr>
 *		<tr>
 *  		<td style="border: 1px solid black;">a</td>
 *  		<td style="border: 1px solid black;">2</td>
 *		</tr>
 *		<tr>
 *  		<td style="border: 1px solid black;">b</td>
 *  		<td style="border: 1px solid black;">3</td>
 *		</tr>
 *		<tr>
 *  		<td style="border: 1px solid black;">c</td>
 *  		<td style="border: 1px solid black;">4</td>
 *		</tr>
 *	</table>
 * </p>
 * @author In Yong S. Lee
 *
 */

public class LocalVariableListExtractor {

   /**
	* Gets the local variable and its partner value of the said local variable given the <code>line</code>.
	* @param line the line to capture.
	* @return Local Object represented as a whole to be placed in the Debugging Manager/Variable List during the debugging.
	*/
	public LocalObject getLocal(String line)
	{
		return new LocalObject(extractVars(line), extractVals(line));
	}
	
	/**
	* Gets the local variable of the said <code>line</code>.
	* @param line the line to capture.
	* @return The Local Variable.
	*/
	public String extractVars(String line)
    {
        return line.substring(0, line.indexOf("=")).trim();
    }
    
	/**
	* Gets the value of the said <code>line</code>.
	* @param line the line to capture.
	* @return The value of the local variable.
	*/
    public String extractVals(String line)
    {
        return line.substring(line.indexOf("=") + 1).trim();
    }
}
