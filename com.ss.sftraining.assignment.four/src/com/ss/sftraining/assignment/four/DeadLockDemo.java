/**
 * 
 */
package com.ss.sftraining.assignment.four;

/**
 * @author kareemelsayed
 * This class demonstrates an example of deadlock 
 * Thread 1 will try to lock both resources. Thread 2 will start by locking resource 2
 * Then tries to lock resource1. Thread 1 locked resource1, and won't release it till it gets a lock on resource2.
 * This thread holds the lock on resource2, and won't release it till it gets resource1. Process halts.
 */
public class DeadLockDemo {
	 
	public static void main(String[] args) {
 
		
		
        final Object resource1 = "resource1";
        final Object resource2 = "resource2";
        
        
        //This thread tries to lock resource1 then resource2
        Thread t1 = new Thread() {
            public void run() {
                
                synchronized(resource1){
                    System.out.println("Thread One locking resource 1");
                   
                    try{
                        Thread.sleep(50);
                    } catch (InterruptedException e) {}

                    
                    synchronized(resource2){
                        System.out.println("Thread 1 locking resource 2");
                    }
                }
            }
        };

       //This thread tries to lock resource2 then resource1
        Thread t2 = new Thread(){
            public void run(){
                
                synchronized(resource2){
                    System.out.println("Thread 2 locking resource 2");
                  
                    try{
                        Thread.sleep(50);
                    } catch (InterruptedException e){}

                    
                    synchronized(resource1){
                        System.out.println("Thread 2 locking resource 1");
                    }
                }
            }
        };

        
        t1.start();
        t2.start();
	
	}
	    
}


	 

    



