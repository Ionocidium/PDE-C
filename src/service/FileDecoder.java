package service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

public class FileDecoder {
	
	public byte[] convertToFile(String content, String filename){
        byte[] contentByteArray = null;  
       try{
             //Converting a Base64 String into Content byte array 
			
			contentByteArray = decodeContent(content);
			
			
			// Write a content byte array into file system  
			 
			FileOutputStream contentOutFile = new FileOutputStream(System.getProperty("user.dir")+"/src/"+filename,true);
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
	
	public byte[] decodeContent(String contentDataString){		
        return Base64.decodeBase64(contentDataString);
}

}
