package com.sequential.consistency;



public abstract class Process implements Runnable{
	
	protected Lock L;
	
	public Process(Lock lock) {
		L = lock;
	}
	
	protected void acquireLock() {
		L.lock();
	}

	protected void releaseLock() {
		L.unlock();
	}
	
	public abstract void criticalSection();
	
	public abstract void run();
	
}
