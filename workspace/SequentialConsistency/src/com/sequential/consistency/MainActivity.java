package com.sequential.consistency;

public class MainActivity {

	public static void main(String args[]){
		
		try {
			
			Lock lock = new Lock();
			
			Process P1 = new P1(lock);
			Process P2 = new P2(lock);
			
			Thread t1 = new Thread(P1);
			Thread t2 = new Thread(P2);
			
			t1.start();
			t2.start();
			t1.join();
			t2.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
