package controller.fileops;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles the actual reading of C Files, PDE-C files (Feedback History), Portable Document Format file types in PDE-C.
 * 
 * @author Alexander John D. Jose
 * @author Lorenzo Miguel G. Monzon
 *
 */

public class FileLoad 
{
  private Matcher matcher;
  private Pattern pattern;
  
  /**
   * Creates an instance of FileLoad.
   */
  public FileLoad()
  {
		
  }

  /**
   * Loads a file using the said <code>path</code>. <br>
   * Returns the source code of the <code>path</code> loaded.
   * @param path The Path Specified
   * @return The C Source Code
   */
  public String loadFile(Path path)
  {
	Charset charset = Charset.forName("UTF-8");
	String line = null;
	String cCode = new String();
	try (BufferedReader reader = Files.newBufferedReader(path, charset))
	{		
	  while ((line = reader.readLine()) != null)
	  {
		if (cCode.isEmpty())
		{
	      cCode += line;
		}
		  
		else
		{
		  cCode = cCode + "\n" + line;
		}	  
	  }
	}
	  
	catch (IOException ex)
	{
	  ex.printStackTrace();
	}
	  
	return cCode;
  }
  

  /**
   * Checks if the fileName has a <code>.c</code> extension.
   * @param fileName The Absolute File to check.
   * @return <code>true</code> if the <code>fileName</code> specified is a C Source Code, <code>false</code> otherwise.
   */
  public boolean checker(String fileName)
  {
	String codePattern = "(^.*\\.(c)$)";
	pattern = Pattern.compile(codePattern);
	matcher = pattern.matcher(fileName);
	
	return matcher.matches();
  }

  /**
   * Checks if the fileName has a <code>.fdbk</code> extension.
   * @param fileName The Absolute File to check.
   * @return <code>true</code> if the <code>fileName</code> specified is a PDE-C Extension, <code>false</code> otherwise.
   */
  public boolean checkerpdec(String fileName)
  {
	String codePattern = "(^.*\\.(fdbk)$)";
	pattern = Pattern.compile(codePattern);
	matcher = pattern.matcher(fileName);
	
	return matcher.matches();
  }

  /**
   * Checks if the fileName has a <code>.pdf</code> extension.
   * @param fileName The Absolute File to check.
   * @return <code>true</code> if the <code>fileName</code> specified is a Portable Document Format, <code>false</code> otherwise.
   */
  public boolean checkerpdf(String fileName)
  {
	String codePattern = "([^\\s]+(\\.(?i)(pdf))$)";
	pattern = Pattern.compile(codePattern);
	matcher = pattern.matcher(fileName);
	
	return matcher.matches();
  }
}
