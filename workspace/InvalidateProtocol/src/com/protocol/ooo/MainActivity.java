package com.protocol.ooo;

public class MainActivity {

	static Thread[] threads;
	
	public static void main(String args[]){
		try{
			
			new GlobalStatic();
			
			threads = new Thread[3];
			threads[0] = new Thread(new P1());
			threads[1] = new Thread(new P2());
			threads[2] = new Thread(new P3());
			
			
			for (int i=0; i<3; i++){
				threads[i].start();
				Thread.currentThread().sleep(100);
			}
			
			while (!GlobalStatic.flag_P1 || !GlobalStatic.flag_P2 || !GlobalStatic.flag_P3);
			
			System.out.println("##################  All Processes have joined onto main thread  #################");
			
			System.out.flush();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
