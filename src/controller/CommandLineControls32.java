package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandLineControls32
{
	private String gccPath;
	private String fileToCompile;
	private final String[] commands = {"", "", "", ""};
    private Runtime rt = Runtime.getRuntime();
    private Process proc;
	private BufferedReader stdInput;
	private BufferedReader stdError;

	public CommandLineControls32(String cFile) throws IOException
	{
        this.gccPath = "C:\\cygwin\\bin\\";
        this.fileToCompile = cFile;
        this.rt = Runtime.getRuntime();
        this.commands[0] = this.gccPath + "gcc.exe";
        this.commands[1] = this.fileToCompile;
        this.commands[2] = "-o";
        this.commands[3] = this.fileToCompile.substring(0, this.fileToCompile.lastIndexOf(".c"));
	}
	
	public CommandLineControls32(String gccPath, String cFile) throws IOException
	{
        this.gccPath = gccPath;
        this.fileToCompile = cFile;
        this.rt = Runtime.getRuntime();
        this.commands[0] = this.gccPath + "gcc.exe";
        this.commands[1] = this.fileToCompile;
        this.commands[2] = "-o";
        this.commands[3] = this.fileToCompile.substring(0, this.fileToCompile.lastIndexOf(".c"));
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
