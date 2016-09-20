package com.protocol.ooo;


public interface Process extends Runnable{

	public void initParam();
	
	public void run();
	
	public void updateReadSet(String S);
	
	public void updateWriteSet(String S, int val);

	public void sendInvalidate();
	
	public void commitTransaction();
}
