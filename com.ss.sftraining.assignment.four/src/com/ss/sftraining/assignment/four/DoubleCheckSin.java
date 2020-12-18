/**
 * 
 */
package com.ss.sftraining.assignment.four;

/**
 * @author kareemelsayed
 * implementation of a singleton with double checked locking 
 */
public class DoubleCheckSin {
	
	    
		// Needs to be volataile to prevent cashe incoherence issues 
		private static volatile DoubleCheckSin instance;
	    
		public static DoubleCheckSin getInstance() {
	        if (instance == null) {
	            synchronized (DoubleCheckSin .class) {
	                if (instance == null) {
	                    instance = new DoubleCheckSin();
	                }
	            }
	        }
	        return instance;
	    }
}
