package com.protocol.ooo;

public class ParallelThread {

	public Process P;
	public Thread t;
	
	public ParallelThread(Process P) {
		try {
			this.P = P;
			t = new Thread(P,P.getClass().getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
