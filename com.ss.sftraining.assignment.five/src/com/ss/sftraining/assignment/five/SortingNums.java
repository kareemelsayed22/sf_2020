/**
 * 
 */
package com.ss.sftraining.assignment.five;

import java.util.ArrayList;

/**
 * @author kareemelsayed
 * This class sorts even numbers from odd numbers
 */
public class SortingNums {

	/**
	 * @param args
	 */
	public static ArrayList<Integer> nums = new ArrayList<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i < 101; i++){
             nums.add(i);
        }
		
		isEvenOrOdd();

	}
	
	//method to distinguish the numbers 
    public static void isEvenOrOdd(){
   
        nums.stream().map(e-> {
            if (e%2 == 1){
                return ("O -> "+e);
            }
            else{
                return ("E -> "+e);
            }
        }).forEach(e-> System.out.println(e));
    }

}
