/**
 * 
 */
package com.ss.sftraining.assignment.week.one;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kareemelsayed
 * This class returns a list doubled
 */
public class AssignmentThree {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		
		List<Integer> result = numbers.stream()
				.map(number -> number * 2)
				.collect(Collectors.toList());
		
		System.out.println(numbers + "\n -> " + result);

	}

}
