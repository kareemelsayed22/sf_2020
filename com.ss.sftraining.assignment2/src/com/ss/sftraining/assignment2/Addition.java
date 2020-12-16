/**
 * 
 */
package com.ss.sftraining.assignment2;
import java.util.Scanner;  // Import the Scanner class

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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	 Scanner scan = new Scanner(System.in);
     
	 //get the size of array to store nums being summed
	 //how many numbers are we adding?
	 System.out.println("How many numbers are you adding?");
     int size = scan.nextInt();
       //System.out.println("Size is " + size);
     int[] nums = new int[size];
       //System.out.println("Size of array is " + nums.length);
      
     //promt the users for numbers to add to array
     System.out.println("What numbers would you like to add: ");
     for(int i=0;i<nums.length;i++) {
        nums[i] = scan.nextInt();
        System.out.println("Please enter your next number: ");
      }
   //print array sum
       int sum = 0;
       for(int j = 0; j < nums.length; j++) {
	   sum += nums[j];
       }
   
       System.out.println("Sum is " + sum);


   }
   
}  

