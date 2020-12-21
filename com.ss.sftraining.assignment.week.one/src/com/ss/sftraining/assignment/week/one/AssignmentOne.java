/**
 * 
 */
package com.ss.sftraining.assignment.week.one;
import java.util.*;
import java.util.stream.*;
import java.math.BigInteger;

/**
 * @author kareemelsayed
 * This class performs the functions on cases
 */
public class AssignmentOne {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	     LambdaOperations();
		

	}
	public static void LambdaOperations() {
		System.out.println("Performing operations on your input");
		PerformOperation operator = new PerformOperation();
		Scanner scan = new Scanner(System.in);
		int lineCount = Integer.parseInt(scan.nextLine());
		
		ArrayList<String> lines = new ArrayList<>();
		IntStream.range(0, lineCount).forEach(addLine -> lines.add(scan.nextLine()));
		scan.close();
		
		lines.forEach(line -> {
			String[] lineArray = line.split(" ");
			int condition = Integer.parseInt(lineArray[0]);
			int value = Integer.parseInt(lineArray[1]);
			operator.performOperation(condition, value);
		});
		System.out.println("Finished!");
	}

}

