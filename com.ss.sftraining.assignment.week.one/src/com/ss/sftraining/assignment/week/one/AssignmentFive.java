/**
 * 
 */
package com.ss.sftraining.assignment.week.one;

/**
 * @author kareemelsayed
 * This class returns a a boolean if a subset exists from a set where the sum is in the range
 */
public class AssignmentFive {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] set1 = {2,4,8};
		int[] set2 = {1,2,4,8,1};

		
        System.out.println(groupSumClump(0,set1,10));
        System.out.println(groupSumClump(0,set2,14));



	     
	     
	     }
	    
	//solves this problem to find the subset recursively 
    public  static boolean groupSumClump(int start, int arr[], int target) {
            
    	  /**
    	   * Base cases. In case the set length is zero or the target number is zero
    	   */
    	
    	  if(start >= arr.length){
              return target == 0;
          }

          if(arr[start] == 6){
              if( groupSumClump(start + 1, arr, target - arr[start])) return true;
          } else {
              if( groupSumClump(start + 1, arr, target - arr[start])) return true;
              if( groupSumClump(start + 1, arr, target)) return true;
          }
          return false;
       }
}

//if (sum == 0) 
//return true;
//
//if (n == 0)
//return false;
//
//// Ignore the last element if its > the target sum then do the recursive call
//if (set[n - 1] > sum)
//return groupSumClump(set, n - 1, sum);
//
///* Otherwise checks if sum can be obtained 
//by including or excluding the last elements of the array
//*/
//return groupSumClump(set, n - 1, sum) || groupSumClump(set, n - 1, sum - set[n - 1]);

