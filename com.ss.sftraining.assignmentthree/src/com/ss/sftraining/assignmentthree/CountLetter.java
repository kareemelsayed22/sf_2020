/**
 * 
 */
package com.ss.sftraining.assignmentthree;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author kareemelsayed
 *
 */
public class CountLetter {
     
	private final char targetChar;
	private FileReader file = new FileReader("/Users/kareemelsayed/git/sf_2020/sf_2020/com.ss.sftraining.assignmentthree/src/test.txt");

	
	//Constructor: Instantiate an object file with a target char
	//To count the occurrences of the targeted char
	CountLetter(FileReader file, char targetChar) throws IOException {
	    this.file = file;
	    this.targetChar = targetChar;

	}
	//Read the file using BufferReader
	private String readFile() throws IOException {

	    String url;

	    try (BufferedReader br = new BufferedReader(file)) {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            line = br.readLine();

	        }
	        url = sb.toString();
	    }
	    return url;
	}
	
	//Count the occurrences of particular letter 
	private int count(String line) {
	    int count = 0;

	    for (int i = 0; i < line.length(); i++) {
	        if (line.charAt(i) == targetChar) {
	            count++;
	        }
	    }

	    return count;
	}
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Read the file, providing the file path
		//Reads test.txt file 
		FileReader file = new FileReader("/Users/kareemelsayed/git/sf_2020/sf_2020/com.ss.sftraining.assignmentthree/src/test.txt");
	    
		//Create an Object that has a file and a char
        CountLetter cl1 = new CountLetter(file, 'e');

        //Systo the count of the char
	    System.out.println(cl1.count(cl1.readFile()));
		
	}

}
