/**
 * 
 */
package com.ss.sftraining.assignment2;

/**
 * @author kareemelsayed
 *
 */
public class Addition {

	/**
	 * @param args
	 * 
	 * 
	 */
	// method to add two inputs 
	public static int Add(int a, int b) {
		return a + b;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// take as many arguments and keep adding 
        for(int i = 0; i < args.length; i++) {
        	System.out.println(Add(i, i - 1));
        }
	}
	
	

}
