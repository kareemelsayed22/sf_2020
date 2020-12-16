/**
 * 
 */
package com.ss.sftraining.assignment2;

/**
 * @author kareemelsayed
 *
 */
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

public class Maxnum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[][] array = { {2,3}, {5,6}, {99, 100}, {53, 27} };
		
		
		
		System.out.println(getMax(array));
		System.out.println(getMaxIndex(array));

	    
		 
	}
	
	 /**
	  * 
	  * @param arr
	  * @return the max idx
	  * Same algorithm to find the value.
	  * 
	  */
     public static int getMaxIndex(int[][] arr) {
	        int maxi = 0;
	        int maxj = 0;
	        for (int j = 0; j < arr.length; j++) {
	            for (int i = 0; i < arr[j].length; i++) {
	                if (arr[j][i] > arr[maxj][maxi]) {
	                    maxi = i;
	                    maxj = j;
	                }
	            }
	        }
            // Swap values and get the greatest one 
	        if(maxj > maxi) {
	        	maxi = maxj;
	        }
	        
	        return maxj;
	        
	        
	        
	        
      }
	 /**
	  * 
	  * @param arr
	  * @return element with the greatest value
	  * Start at the beginning of the array,
	  * Check adjacent neighbors, if that element in the array
	  * is > the previous element then that element is the new
	  * max of the array. 
	  */
     
     public static int getMax(int[][] arr) {
	        int maxValue = arr[0][0];
	        for (int j = 0; j < arr.length; j++) {
	            for (int i = 0; i < arr[j].length; i++) {
	                if (arr[j][i] > maxValue) {
	                    maxValue = arr[j][i];
	                }
	            }
	        }
	        return maxValue;
     }
	
	

}
