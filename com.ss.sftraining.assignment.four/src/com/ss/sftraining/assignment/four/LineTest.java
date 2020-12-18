/**
 * 
 */
package com.ss.sftraining.assignment.four;

import static org.junit.Assert.*;

/**
 * @author kareemelsayed
 * This is a JUnit Test class on the Line class provided. 
 * Testing done using Intelliji IDE
 */
public class LineTest {
    
	

	/**
	 * Junit test on Line class which was provided
	 */
	public class LineTest {

	    @org.junit.Test
	    public void getSlope() {
	        Line testLine = new Line(1.00, 3.00 ,2.00,2.00);
	        assertEquals(-3,testLine.getSlope(),.001);
	    }

	    @org.junit.Test
	    public void getDistance() {
	        Line testLine = new Line(1.00, 4.00 ,2.00,2.00);
	        assertEquals(3.1622,testLine.getDistance(),.001);
	    }

	    @org.junit.Test
	    public void parallelTo() {
	        Line testLine0 = new Line(1.00, 5.00 ,2.00,2.00);
	        Line testLine1 = new Line(2.00,3.00,3.00,3.00);
	        assertEquals(true,testLine0.parallelTo(testLine1));
	    }
	}
}
