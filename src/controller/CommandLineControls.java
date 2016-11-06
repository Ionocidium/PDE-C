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
import java.util.Arrays;
import java.util.List;
import configuration.LocalConfiguration;

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
	  	String path = local.getGccPath();
        this.gccPath = path;
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
        String s = null;
        while ((s = this.stdError.readLine()) != null)
        {
        	s = new SimplifyError(s).simplify();
            res += s + "\n";
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
