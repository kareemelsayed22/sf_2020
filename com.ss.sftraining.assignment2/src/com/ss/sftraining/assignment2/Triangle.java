package com.ss.sftraining.assignment2;

public class Triangle implements Shape{
     
	
	 private double a, b, c; 

	    public Triangle() {
	        this(4,4,4);
	    }
	    public Triangle(double a, double b, double c) {
	        this.a = a;
	        this.b = b;
	        this.c = c;
	    }
	@Override
	public double calculateArea() {
		// TODO Auto-generated method stub
		double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
	}

	@Override
	public void display() {
		// TODO Auto-generated method stub
		int rows = (int) a, k = 0;

	    for (int i = 1; i <= rows; ++i, k = 0) {
	      for (int space = 1; space <= rows - i; ++space) {
	        System.out.print(" ");
	      }

	      while (k != 2 * i - 1) {
	        System.out.print("*");
	        ++k;
	      }

	      System.out.println();
	    }
	}

}
