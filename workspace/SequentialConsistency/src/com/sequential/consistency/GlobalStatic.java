package com.sequential.consistency;

public class GlobalStatic {

	public static int Head = 0;
	public static int Data = 0;

	public static void print() {
		System.out.println("Head = "+ Head);
		System.out.println("Data = "+ Data);
	}
	
}
