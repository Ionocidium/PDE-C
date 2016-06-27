package service;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;


public class FileManipulation {
        
        public String convertToBinary(File file){
            try{
                
			/*
			 * Reading a Image file from file system
			 */
			FileInputStream imageInFile = new FileInputStream(file);
			byte imageData[] = new byte[(int)file.length()];
			imageInFile.read(imageData);
			
			/*
			 * Converting Image byte array into Base64 String 
			 */
			String imageDataString = encodeImage(imageData);
                        
                        return imageDataString;
			
            } catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
	    } catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
            }
            
            return null;
        }
        
        public byte[] convertToImage(String imageDataString, String filename){
           byte[] imageByteArray = null;  
          try{
                //Converting a Base64 String into Image byte array 
			
			imageByteArray = decodeImage(imageDataString);
			
			
			// Write a image byte array into file system  
			 
			FileOutputStream imageOutFile = new FileOutputStream(System.getProperty("user.dir")+"/src/"+filename.concat(".jpg"),true);
			imageOutFile.write(imageByteArray);
			
			
			imageOutFile.close();
			
			System.out.println("Image Successfully Manipulated!");
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
            
            return imageByteArray;
            }
        

            public String encodeImage(byte[] imageByteArray){		
                        return Base64.encodeBase64URLSafeString(imageByteArray);		
                }

                /**
                 * Decodes the base64 string into byte array
                 * @param imageDataString - a {@link java.lang.String} 
                 * @return byte array
                 */
                public byte[] decodeImage(String imageDataString){		
                        return Base64.decodeBase64(imageDataString);
                }
        /*
	public static void main(String[] args) {
		File file = new File("C:/Users/cinnamon/Desktop/jelli.jpg");
		
		try {
			
			 // Reading a Image file from file system
			 
                         
			FileInputStream imageInFile = new FileInputStream(file);
			byte imageData[] = new byte[(int)file.length()];
			imageInFile.read(imageData);
			
			
			 //Converting Image byte array into Base64 String 
			
			String imageDataString = encodeImage(imageData);
			
			
			 //Converting a Base64 String into Image byte array 
			
			byte[] imageByteArray = decodeImage(imageDataString);
			
			
			// Write a image byte array into file system  
			 
			FileOutputStream imageOutFile = new FileOutputStream("C:/Users/cinnamon/Desktop/jelli-after-convert.jpg");
			imageOutFile.write(imageByteArray);
			
			imageInFile.close();
			imageOutFile.close();
			
			System.out.println("Image Successfully Manipulated!");
		} catch (FileNotFoundException e) {
			System.out.println("Image not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while reading the Image " + ioe);
		}
	}*/
        
        
	
	/**
	 * Encodes the byte array into base64 string
	 * @param imageByteArray - byte array
	 * @return String a {@link java.lang.String}
	 */
	

}