/**
 * 
 */
package com.ss.sftraining.assignment.week.one;

import java.math.BigInteger;

/**
 * @author kareemelsayed
 * This class returns lambda expressions performing functions

 */
public class PerformOperation {
	
	public void performOperation(int condition, int value) {
		switch (condition) {
		case 1:
			Operation isOdd = result -> {
				String r = value % 2 == 1 ? "ODD" : "EVEN";
				return r;
			};
			System.out.println(operate(value, isOdd));
			break;
		case 2:
			Operation isPrime = result -> {
				BigInteger bigInt = BigInteger.valueOf(value);
				String r = bigInt.isProbablePrime(10) ? "PRIME" : "NOT PRIME";
				return r;
			};
			System.out.println(operate(value, isPrime));
			break;
		case 3:
			Operation isPalindrome = result -> {
				String numberString = Integer.toString(value);
				String reverseString = new StringBuilder(numberString).reverse().toString();
				String r = numberString.equals(reverseString) ? "PALINDROME" : "NOT PALINDROME";
				return r;
			};
			System.out.println(operate(value, isPalindrome));
			break;
		default:
			break;
		}
	}
	
	@FunctionalInterface
	interface Operation {
		String operation(int n);
	}

	public String operate(int n, Operation op) {
		return op.operation(n);
	}
}
