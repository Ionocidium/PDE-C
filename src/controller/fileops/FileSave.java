package controller.fileops;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;

/**
 * Handles the actual writing of C Files in PDE-C.
 * 
 * @author Alexander John D. Jose
 *
 */

public class FileSave {
	  
  /**
   * Creates an instance of FileSave.
   */
  public FileSave()
  {
		
  }
  
  /**
   * Saves a file using the said <code>path</code>. <br>
   * Writes the source code of the <code>path</code> specified.
   * @param path The Path Specified
   * @param contents The contents of the source code from the editor.
   */
  public void writeFile(Path path, String contents)
  {
	Charset charset = Charset.forName("UTF-8");
	String s = contents;
	
	try (BufferedWriter writer = Files.newBufferedWriter(path, charset))
	{
	    writer.write(s, 0, s.length());
	} 
	catch (IOException x) 
	{
	    System.err.format("IOException: %s%n", x);
	}
  }
}
