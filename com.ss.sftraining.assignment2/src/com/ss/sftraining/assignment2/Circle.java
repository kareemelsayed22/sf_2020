/**
 * 
 */
package com.ss.sftraining.assignment2;

/**
 * @author kareemelsayed
 * Find the area of a circle and print a circle with a radius of 4.
 *
 */
public class Circle implements Shape{
	
	private static double radius;
    double pi = Math.PI;
    
    public Circle() {
        this(4);
    }   
    public Circle(double radius) {
        this.radius = radius;
    }

	@Override
	public double calculateArea() {
		// TODO Auto-generated method stub
		//Circle area formula 
		return pi * Math.pow(radius, 2);
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		// dist represents distance to the center 
	    double dist; 
	  
	    // for horizontal movement 
	    for (int i = 0; i <= 2 * radius; i++) { 
	  
	    // for vertical movement 
	    for (int j = 0; j <= 2 * radius; j++) { 
	        dist = Math.sqrt((i - radius) * (i - radius) + 
	                         (j - radius) * (j - radius)); 
	  
	        // dist should be in the range (radius - 0.5) 
	        // and (radius + 0.5) to print stars(*) 
	        if (dist > radius - 0.5 && dist < radius + 0.5) 
	        System.out.print("*"); 
	        else
	        System.out.print(" "); 
	    } 
	  
	    System.out.print("\n"); 
	    } 
		
	}
    
	
}
