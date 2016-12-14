package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import configuration.LocalConfiguration;

/**
 * Allows the debugging using <code>gdb</code> after compiling it in <code>gcc</code> through the use of <code>Runtime</code> class.<br>
 * <p>
 * It executes sets of commands to further run the program.<br>
 * <code>CommandLineDebugging</code> is run through the following commands shown below:<br>
 * <br>
 *	<table style="border: 1px solid black;" summary="Command Line for debugging C Source Code">
 *		<tr>
 *			<th colspan="2" style="border: 1px solid black;">
 *				<center>
 *					<code>
 *						commands
 *					</code>
 *				</center>
 *			</th>
 *		</tr>
 *		<tr>
 *			<td style="border: 1px solid black;">
 *				<center>
 *					<code>
 *						[0]
 *					</code>
 *				</center>
 *			</td>
 *			<td style="border: 1px solid black;">
 *				<center>
 *					<code>
 *						[1]
 *					</code>
 *				</center>
 *			</td>
 *		</tr>
 *		<tr>
 *			<td style="border: 1px solid black;">
 *				<code>
 *					gdb
 *				</code>
 *			</td>
 *			<td style="border: 1px solid black;">
 *				<code>
 *					compiled.exe
 *				</code>
 *			</td>
 *		</tr>
 *	</table>
 * <br>
 * It will be run through the use of the default runtime which is dependent on the Operating System used by the client operator.<br>
 * <b>Note:</b> This does not support <b>file names that contain whitespace(s), including directories that have whitespace(s)</b> when compiling/running their source codes.
 * </p>
 * @author In Yong S. Lee
 * @author Alexander John D. Jose
 *
 */
public class CommandLineDebugging
{
	private String gdbPath;
	private String fileToDebug;
	private final String[] commands = {"", ""};
    private Runtime rt = Runtime.getRuntime();
    private Process proc;
	private BufferedReader stdInput;
	private BufferedReader stdError;
	private LocalConfiguration local;
	
	public CommandLineDebugging(){
		
	}

	public CommandLineDebugging(String cFile)
	{
		
		local = LocalConfiguration.getInstance();
		String path = "";
		if(System.getProperty("os.arch").equals("amd64"))
		{
	        path = "/usr/bin/";
		}
		else if(System.getProperty("os.arch").equals("x86"))
		{
	        path = "C:\\cygwin\\bin\\";
		}
        this.gdbPath = local.getGdbPath();
        this.fileToDebug = cFile;
        this.rt = Runtime.getRuntime();
        this.commands[0] = this.gdbPath;
        this.commands[1] = this.fileToDebug;
	}
	
	public String getStdOut() throws IOException{
        this.proc = rt.exec(this.commands);
        this.stdInput = new BufferedReader(new InputStreamReader(this.proc.getInputStream()));
		String res = "";
        String s = null;
        while ((s = this.stdInput.readLine()) != null)
        {
            res += s + "\n";
        }
        return "Here is the standard output of the command:\n" + res;
	}
	
	public String getStdError() throws IOException{
        this.proc = rt.exec(this.commands);
        this.stdError = new BufferedReader(new InputStreamReader(this.proc.getErrorStream()));
		String res = "";
        String s = null;
        while ((s = this.stdError.readLine()) != null)
        {
            res += s + "\n";
        }
        return "Here is the standard error of the command:\n" + res;
	}

	public void sampler() throws IOException
	{
        Runtime rt = Runtime.getRuntime();
        Process proc = rt.exec(this.commands);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

        // read the output from the command
        System.out.println("Here is the standard output of the command:\n");
        String s = null;
        while ((s = stdInput.readLine()) != null)
        {
            System.out.println(s);
        }

        // read any errors from the attempted command
        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null)
        {
            System.out.println(s);
        }
    }
	
}
