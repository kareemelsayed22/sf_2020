package com.ss.sftraining.assignmentone;
/** 
 * This class is for building patterns of stars and dots.
 * @author kareemelsayed
 *
 */

public class Patterns {


// TODO Auto-generated method stub
		
  public void printFullPyramids() {
//	System.out.println(patternNum); 2
     int pyramidPattern = 3;
//  System.out.println(dotFinish); 0  finishDot = 0
//  System.out.println(dotStart);  9 startDot = 9
    int startDot = 9;
    int finishDot = 0; 
	int maxDots = 12;
	//builds a full pyramid
	System.out.println(pyramidPattern + ")");

	int rows = 4, k = 0;

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
	 
//  System.out.println(dotFinish); 0
//  System.out.println(dotStart);  9
    while(startDot + 2 > finishDot) {
    	System.out.print(".");
    	startDot-=1;
    }
    System.out.println("");
    
    pyramidPattern+=1;
	System.out.println(pyramidPattern + ")");
	
  
//	System.out.println(dotFinish); 0
	
    while(maxDots > finishDot) {
    	System.out.print(".");
     	finishDot+=1;
      }
      System.out.println("");
	

    
      //inverted full pyramid
    
    for(int i = rows; i >= 1; --i) {
        for(int space = 1; space <= rows - i; ++space) {
          System.out.print(" ");
        }

        for(int j=i; j <= 2 * i - 1; ++j) {
          System.out.print("*");
        }

        for(int j = 0; j < i - 1; ++j) {
          System.out.print("*");
        }

        System.out.println();
      }
	
}
 
	
public void printHalfpyramids() {
		//local variables to build the four patterns
	    int patternNum = 1;
	    int dotFinish = 9;
		int dotStart = 0;

		
		//generates 2 half pyramids
		while(patternNum < 2) {
			
			 
			System.out.println(patternNum + ")");
			  for(int i = 0; i < 4; i++) {
				for(int j = 0; j <= i; j++) {
					System.out.print("*");
				 }
				System.out.println();
			  }
			
			
			while(dotStart < dotFinish) {
				System.out.print(".");
				dotStart+=1;
			}
			System.out.println("");
		
		patternNum += 1;
		
		
		// inverted half-pyramid
		System.out.println(patternNum + ")");
	    dotFinish+=1;
	    

	   while(dotFinish > 0) {
		   System.out.print(".");
		   dotFinish-=1;
	   }
	   System.out.println("");
//	    System.out.println(dotFinish); 10
//	    System.out.println(dotStart);  9
	    
	    
		
		for(int i = 4;  i > 0; i--) {
			for(int j = 0; j < i; j++) {
				System.out.print("*");
			 }
			System.out.println();
		   }
		
		  }
	}

}


