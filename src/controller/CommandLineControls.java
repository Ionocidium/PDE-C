package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import configuration.LocalConfiguration;
import model.ErrorMessage;

/**
 * Allows the compilation of <code>gcc</code> through the use of <code>Runtime</code> class.<br>
 * <p>
 * It executes sets of commands to further run the program.<br>
 * <code>CommandLineControls</code> is run through the following commands shown below:<br>
 * <br>
 *	<table style="border: 1px solid black;">
 *		<tr>
 *			<th colspan="4" style="border: 1px solid black;">
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
 *			<td style="border: 1px solid black;">
 *				<center>
 *					<code>
 *						[2]
 *					</code>
 *				</center>
 *			</td>
 *			<td style="border: 1px solid black;">
 *				<center>
 *					<code>
 *						[3]
 *					</code>
 *				</center>
 *			</td>
 *		</tr>
 *		<tr>
 *			<td style="border: 1px solid black;">
 *				<code>
 *					gcc
 *				</code>
 *			</td>
 *			<td style="border: 1px solid black;">
 *				<code>
 *					code.c
 *				</code>
 *			</td>
 *			<td style="border: 1px solid black;">
 *				<code>
 *					-o
 *				</code>
 *			</td>
 *			<td style="border: 1px solid black;">
 *				<code>
 *					code.exe
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
public class CommandLineControls
{
	private String gccPath;
	private String fileToCompile;
	private final String[] commands = {"", "", "", ""};
    private Runtime rt = Runtime.getRuntime();
    private Process proc;
	private BufferedReader stdInput;
	private BufferedReader stdError;
	private LocalConfiguration local;

	public CommandLineControls(String cFile) throws IOException
	{
	  	local = LocalConfiguration.getInstance();
	  	this.gccPath = local.getGccPath();
        this.fileToCompile = cFile;
        this.rt = Runtime.getRuntime();
        this.commands[0] = this.gccPath;
        this.commands[1] = this.fileToCompile;
        this.commands[2] = "-o";
        this.commands[3] = this.fileToCompile.substring(0, this.fileToCompile.lastIndexOf(".c"));

	}
	
	public void runMyCompiler() throws IOException{
		rt.exec(this.commands);
	}
	
	
	public String getStdOut() throws IOException{
        this.proc = rt.exec(this.commands);
        this.stdInput = new BufferedReader(new InputStreamReader(this.proc.getInputStream()));
		String res = "";
        String s = null;
        while ((s = this.stdInput.readLine()) != null)
        {
        	System.out.println(this.proc.getInputStream().toString());
            res += s + "\n";
        }
        return res;
	}
	
	public String getStdError() throws IOException{
        this.proc = rt.exec(this.commands);
        this.stdError = new BufferedReader(new InputStreamReader(this.proc.getErrorStream()));
		String res = "";
        String s = "";
        while ((s = this.stdError.readLine()) != null)
        {
        	s = new SimplifyError(s).simplify();
        	if (!s.trim().isEmpty()) 
        	{
        		res += s + "\n";
        	}
        }
        return res;
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
