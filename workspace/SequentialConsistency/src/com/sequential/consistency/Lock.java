package com.sequential.consistency;

public class Lock {

	private boolean isLocked = false;
	private Thread lockedBy = null;
	private int lockCount = 0;

	public synchronized void lock(){
		try {
			Thread callingThread = Thread.currentThread();
			while(isLocked && lockedBy != callingThread){
				wait();
			}
			isLocked = true;
			lockCount++;
			lockedBy = callingThread;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void unlock(){
		if(Thread.currentThread() == lockedBy){
			lockCount--;
			if (lockCount==0){
				isLocked = false;
				notify();
			}
		}
	}

}

