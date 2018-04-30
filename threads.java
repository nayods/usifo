/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threads;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;



public class Threads {

	/**
	 * @param args
	 */
	 public static void main(String[] args){
	 
		try{
		
			List <String> evenNumbers = new ArrayList<>();
			List <String> oddNumbers = new ArrayList<>();
			
			ThreadA a = new ThreadA();
			
			a.start();
			oddNumbers = a.getOddNumbers();
			evenNumbers = a.getEvenNumbers();
			a.join();
			
			ThreadB b = new ThreadB(evenNumbers);
			ThreadC c = new ThreadC(oddNumbers);
			
			b.start();
			c.start();
			b.join();
			c.join();
			
			System.out.println("Finished");
		}
		
		catch(InterruptedException ie){
			System.out.println("Error " + ie.getMessage());
		}
	 
	 } 
	 
}

class ThreadA extends Thread{
	final List <String> evenNumbers = new ArrayList<>();
	final List <String> oddNumbers = new ArrayList<>();
	
	@Override
	public void run(){
		for(int i=0; i<400; i++){
			final int rand = ThreadLocalRandom.current().nextInt(0,100);
			
			if(rand % 2 == 0)
				evenNumbers.add(rand + ",");
			else
				oddNumbers.add(rand + ",");
		}
		System.out.println("Thread: " + ThreadA.currentThread().getId());
	}
	
	public List<String> getEvenNumbers(){
		return this.evenNumbers;
	}
	
	public List<String> getOddNumbers(){
		return this.oddNumbers;
	}
}

class ThreadB extends Thread {
	List<String> num = new ArrayList<>();
	
	ThreadB(List<String> num){
		this.num = num;
	}
	
	@Override
	public void run(){
		try{
			
			File gpxfile = new File("c:/oddFile.txt");
                    try (FileWriter writer = new FileWriter(gpxfile)) {
                        writer.append(this.num.toString());
                        writer.flush();
                    }
			
			System.out.println("Thread: " + ThreadB.currentThread().getId());
		}
		catch(IOException e){
			System.out.println("Error printing file " + e.getMessage());
		}
	}
}

class ThreadC extends Thread{

	List<String> num = new ArrayList<String>();
	
	ThreadC(List<String> num){
		this.num = num;
	}
	
	@Override
	public void run(){
		try{
			
			File gpxfile = new File("c:/evenFile.txt");
                    try (FileWriter writer = new FileWriter(gpxfile)) {
                            Writer Writer; /*append*/
                            Writer = writer.append(this.num.toString());
                        writer.flush();
                    }
			
			System.out.println("Thread: " + ThreadC.currentThread().getId());
		}
		catch(IOException e){
			System.out.println("Error printing file " + e.getMessage());
		
                }
        }
}