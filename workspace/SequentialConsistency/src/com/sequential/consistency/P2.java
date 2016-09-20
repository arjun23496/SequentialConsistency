package com.sequential.consistency;

public class P2 extends Process {

	public P2(Lock lock) {
		super(lock);
	}

	@Override
	public void run() {
		acquireLock();
		criticalSection();
		System.out.println("########## After P2 commits #########");
		GlobalStatic.print();
		releaseLock();
	}

	@Override
	public void criticalSection() {
		int k = 5;
		System.out.println("While loop in P2 runs 5 times without affecting value of Head\n\n");
		while (GlobalStatic.Head == 0){
			System.out.println("k = " + k-- + " Head = " + GlobalStatic.Head);
			if (k==0) break;
		}
		GlobalStatic.Data = 3000;
	}
}
