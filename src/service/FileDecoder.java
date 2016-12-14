package service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

/**
 * FileDecoder class
 * @author Alexander John Jose
 *
 */
public class FileDecoder {
	
  /**
   * Converts a file into <code>byte[]</code>
   * @param content the content of the file
   * @param filename file name
   * @return a converted <code>byte[]</code> of the file
   */
	public byte[] convertToFile(String content, String filename){
        byte[] contentByteArray = null;  
       try{
             //Converting a Base64 String into Content byte array 
			
			contentByteArray = decodeContent(content);
			
			
			// Write a content byte array into file system  
			 
			FileOutputStream contentOutFile = new FileOutputStream("resources/" + filename, false);
			contentOutFile.write(contentByteArray);
			
			
			contentOutFile.close();
			
			System.out.println("Content Successfully Manipulated!");
		} catch (FileNotFoundException e) {
			System.out.println("Content not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the content" + ioe);
		}
         
         return contentByteArray;
     }
	
	/**
	 * This is the main decoder of file to <code>byte[]</code>
	 * @param contentDataString the content of file as a <code>String</code> object
	 * @return a converted <code>byte[]</code> of the file
	 */
	public byte[] decodeContent(String contentDataString){		
        return Base64.decodeBase64(contentDataString);
}

}
