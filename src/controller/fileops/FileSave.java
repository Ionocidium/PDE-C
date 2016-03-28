package controller.fileops;

import java.io.File;

public class FileSave {

	public FileSave(){
		
	}
	
	public File writeMe(String path){
		return new File(path);
	}
	
}
