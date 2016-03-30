package controller.fileops;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;

public class FileSave {

  public FileSave()
  {
		
  }
	
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
