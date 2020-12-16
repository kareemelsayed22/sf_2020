/**
 * 
 */
package com.ss.sftraining.assignmentthree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/**
 * @author kareemelsayed
 * Append text to an exisiting file
 */
public class AppendText {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
		String path = System.getProperty("user.dir") + "/src/test.txt";
        String text = "Successfully working!";

        try {
            Files.write(Paths.get(path), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
        }
	}
	
	
	
		
}

