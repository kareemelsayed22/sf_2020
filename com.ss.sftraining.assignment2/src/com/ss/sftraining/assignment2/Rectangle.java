/**
 * 
 */
package com.ss.sftraining.assignment2;

/**
 * @author kareemelsayed
 * Find the area of a rectangle with length = 4, width = 4
 */
public class Rectangle implements Shape {

	
	 private double width, length; 

	    public Rectangle() {
	        this(4,4);
	    }
	    
	    public Rectangle(double width, double length) {
	        this.width = width;
	        this.length = length;
	    }
	    
	
	@Override
	public double calculateArea() {
		// TODO Auto-generated method stub
		return width * length;
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		 for (int i = 0; i < length; i++) 
	        { 
	            System.out.println(); 
	            for (int j = 0; j < width; j++) 
	            { 
	                // Print @ if this is first  
	                // row or last row. Or this 
	                // column is first or last. 
	                if (i == 0 || i == length-1 || 
	                    j== 0 || j == width-1) 
	                   System.out.print("*"); 
	                else
	                   System.out.print(" "); 
	            } 
	        } 
		  
	}

}
