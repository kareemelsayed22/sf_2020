package com.ss.sftraining.assignment2;

public class HierarchyCheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        // Instantiate objects and use their functions here 
	
		int h = 0;
        
		do {
			
			checkRectangle();
			    System.out.println();
		        System.out.println(".................");
			checkCircle();
			    System.out.println();
		        System.out.println(".................");
			checkTriangle();
			h+=2;

		}while(h < 2);

        
        
        
        
	}

	public static void checkRectangle() {
		
		Rectangle rect = new Rectangle();
		System.out.println("The area of the rectangle is " + rect.calculateArea());
		System.out.println("The rectangle displayed ");
        rect.display();
		
	}
	
	public static void checkCircle() {
		
		Circle circ = new Circle();
        System.out.println("The area of the circle is " + circ.calculateArea());
		System.out.println("The circle displayed ");
        circ.display();
        
	}
	
	public static void checkTriangle() {
		
		 Triangle tri = new Triangle();
	     System.out.println("The area of the triangle is " + tri.calculateArea());
		 System.out.println("The triangle displayed ");
	     tri.display();
	}
	
	
}
