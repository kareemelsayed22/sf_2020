/**
 * 
 */
package com.ss.sftraining.assignmentthree;
import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
import java.io.IOException;



/**
 * @author kareemelsayed
 * 
 *
 */
public class ListFiles {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		  //Creating a File object for directory
		  //Type targeted path in the File object
	      File directoryPath = new File("/Users/kareemelsayed/");
	      
	      //Lists all files and directories
	      String contents[] = directoryPath.list();
	      System.out.println("List of files under the given directory:");
	      for(int i=0; i<contents.length; i++) {
	         System.out.println(contents[i]);
	      }
		
	}

}
	


