/**
 * 
 */
package com.ss.sftraining.assignment.week.one;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author kareemelsayed
 * This class returns the rightmost digit given a list of non negative integers
 */
public class AssignmentTwo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 ArrayList<Integer> nums = new ArrayList();
	        for (int i = 0; i < 10; i++) {
	            nums.add((int) (Math.random() * 10000));
	        }
	        nums.forEach(e -> System.out.println(e));
	        nums.replaceAll(e -> {
	            while (e > 10) {
	                e = e % 10;
	            }
	            return e;
	        });
	        System.out.println("---- Right most digits are ----");
	        nums.forEach(e -> System.out.println(e));
	}
	
	

}
