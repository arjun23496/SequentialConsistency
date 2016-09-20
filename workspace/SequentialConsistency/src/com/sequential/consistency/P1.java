package com.sequential.consistency;

public class P1 extends Process {

	public P1(Lock lock) {
		super(lock);
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		acquireLock();
		criticalSection();
		System.out.println("########## After P1 commits #########");
		GlobalStatic.print();
		releaseLock();
	}

	@Override
	public void criticalSection() {
		GlobalStatic.Data = 2000;
		GlobalStatic.Head = 1;
	}
}
