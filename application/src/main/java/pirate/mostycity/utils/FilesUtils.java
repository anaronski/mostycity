package pirate.mostycity.utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;

public class FilesUtils implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static List<String> getFilesNames(String pathName) throws IOException{
		
		 File path = new File(pathName);
		 if (!path.exists()) {
		 	throw new IOException("Cannot access " + pathName + ": No such file or directory");
		 }
		 File[] files = null; 
		 if (path.isFile()) {
			 files = new File[]{path};           
		 } else {
			  files = path.listFiles();
		 }
		 
		 List<String> filesNames = new ArrayList<String>();
		 
		 if(files!=null){
			 
			 for (File f : files){
				 if(f.isFile())
					 filesNames.add(f.getName());
			 }
		 }
		 return filesNames;
	}

	
	protected File getFileToWrite(String parentFilePath, String fileName) throws IOException{
		
		File dir = new File("webapps/mostycity/images/"+parentFilePath);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		File fileToWrite = new File(dir, fileName);
		fileToWrite.deleteOnExit();
		fileToWrite.createNewFile();
		
		return fileToWrite;
	}
	
	protected File saveFileFromUpload(FileUpload upload, String parentPath, String fileName ) throws IOException{
		
		File file = getFileToWrite(parentPath, fileName);
		
		upload.writeTo(file);
		
		return file;
	}
}
