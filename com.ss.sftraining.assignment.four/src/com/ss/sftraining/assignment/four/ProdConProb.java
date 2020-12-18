/**
 * 
 */
package com.ss.sftraining.assignment.four;
import java.util.Random;

/**
 * @author kareemelsayed
 * 
 * This class demonstrates an example of threads running 
 * Without interruptions. Using a try and catch block
 * Notifying the other running threads when
 * the current thread stops running, unlocking the deadlock

 */
public class ProdConProb {

	/**
	 * @param args
	 * Start running the threads
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Message message = new Message();
        (new Thread(new Writer(message))).start();
        (new Thread(new Reader(message))).start();
	}
}

class Message {
    private String message;
    private boolean empty = true;
    
    public synchronized String read() {
        while(empty) {
        	try{
                
        		wait();
            
        	}catch (InterruptedException e){
                e.printStackTrace();
            }
          
        }
        empty = true;
        notifyAll();
        return message;
    }
  //deadlock will occur without the Try and catch block to halt other thread

    public synchronized void write(String message) {
        while(!empty) {
        	try{
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        empty = false;
        this.message = message;
        notifyAll();


    }
}
//This class will write the threads
class Writer implements Runnable {
    private Message message;

    public Writer(Message message) {
        this.message = message;
    }

    public void run() {
        String messages[] = {
                "Twinkle, Twinkle, little star",
                "How i wonder what you are",
                "Up above, you are in the sky",
                "Beautiful, beautiful, little star"
        };

        Random random = new Random();

        for(int i=0; i<messages.length; i++) {
            message.write(messages[i]);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch(InterruptedException e) {

            }
        }
        message.write("Finished");
    }
}

class Reader implements Runnable {
    private Message message;

    public Reader(Message message) {
        this.message = message;
    }

    public void run() {
        Random random = new Random();
        for(String latestMessage = message.read(); !latestMessage.equals("Finished");
            latestMessage = message.read()) {
            System.out.println(latestMessage);
            try {
                Thread.sleep(random.nextInt(2000));
            } catch(InterruptedException e) {

            }
        }
    }
}
