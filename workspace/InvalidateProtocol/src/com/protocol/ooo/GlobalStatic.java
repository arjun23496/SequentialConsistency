package com.protocol.ooo;

import java.util.Map;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GlobalStatic {
	
	public static int A;
	public static int B;
	public static int C;
	
	public static boolean flag_P1;
	public static boolean flag_P2;
	public static boolean flag_P3;
	

	public static BlockingQueue<Map<String, Object>> P1_in;
	public static BlockingQueue<Integer> P1_ackQ;
	
	public static BlockingQueue<Map<String, Object>> P2_in;
	public static BlockingQueue<Integer> P2_ackQ;
	
	public static BlockingQueue<Map<String, Object>> P3_in;
	public static BlockingQueue<Integer> P3_ackQ;

	public GlobalStatic() {

		A = 0;
		B = 0;
		C = 0;
		
		flag_P1 = false;
		flag_P2 = false;
		flag_P3 = false;
		
		P1_in = new ArrayBlockingQueue<Map<String, Object>>(5);
		P1_ackQ = new ArrayBlockingQueue<Integer>(5);
	
		P2_in = new ArrayBlockingQueue<Map<String, Object>>(5);
		P2_ackQ = new ArrayBlockingQueue<Integer>(5);
	
		P3_in = new ArrayBlockingQueue<Map<String, Object>>(5);
		P3_ackQ = new ArrayBlockingQueue<Integer>(5);
	
	}
	
	public static synchronized void updateA(int val){
		A = val;
	}
	
	public static synchronized void updateB(int val){
		B = val;
	}
	
	public static synchronized void updateC(int val){
		C = val;
	}
	
}
