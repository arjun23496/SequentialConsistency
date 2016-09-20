package com.protocol.ooo;

import java.util.HashMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class P2 implements Process{

	protected Map<String,Map<String, Integer>> readSet;
	protected Map<String,Map<String, Integer>> writeSet;
	
	Thread t;
	
	@Override
	public void initParam() {
		readSet = new HashMap<String,Map<String, Integer>>();
		writeSet = new HashMap<String,Map<String, Integer>>();
		
		new Thread (new Runnable() {
					
					@SuppressWarnings({ "unchecked", "static-access" })
					@Override
					public void run() {
						try{
							while(true){
								Map<String,Object> M = (Map<String, Object>) GlobalStatic.P2_in.take();
								Set<String> S = (Set<String>) M.get("writeSet");
								switch((Integer) M.get("processID")){
									case 1 : GlobalStatic.P1_ackQ.put(1); break;
									case 2 : GlobalStatic.P2_ackQ.put(1); break;
									case 3 : GlobalStatic.P3_ackQ.put(1); break;
								}
								if (intersect(S,readSet.keySet()) == true){
									abort();
									return;
								}
								Thread.currentThread().sleep(20);
							}
						}	 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
	
		t = Thread.currentThread();
		
		initParam();
		updateReadSet("A");
		if (readSet.get("A").get("val") == 1){
			updateWriteSet("B",1);
		}
		commitTransaction();
		try {
			t.sleep(500);
		} catch (InterruptedException e) {
			// do-nothing
		}
		
		if (GlobalStatic.P2_in.size()>0){
			abort();
			return;
		}
		
		if (t.interrupted()){
			System.out.println("ewf");
			return;
		}
		
		for (String S : writeSet.keySet()){
			if (S.equals("A")){
				GlobalStatic.updateA(writeSet.get(S).get("val"));
			}
			if (S.equals("B")){
				GlobalStatic.updateB(writeSet.get(S).get("val"));
			}
		}

		if (!GlobalStatic.flag_P2){
			
			System.out.println("##################  After Successful Commit of P2  #################");
			System.out.println("A = " + GlobalStatic.A);
			System.out.println("B = " + GlobalStatic.B);	

			GlobalStatic.flag_P2 = true;
		}

	}



	@Override
	public void sendInvalidate() {
		try {
			Set<String> S = new HashSet<String>();
			for (String key : writeSet.keySet()){
				if (writeSet.get(key).containsKey("dirty")){
					S.add(key);
				}
			}
			Map <String,Object> M = new HashMap<String,Object>();
			M.put("processID",new Integer(2));
			M.put("writeSet", S);
			GlobalStatic.P1_in.put(M);
			GlobalStatic.P3_in.put(M);
		} catch (InterruptedException e) {
			// do-nothing
		}
	}
	
	@Override
	public void commitTransaction() {
	
		sendInvalidate();
		
		int ackCount = 0;
		
		for (int i=0; i<GlobalStatic.P2_ackQ.size(); i++){
			if ((Integer) GlobalStatic.P2_ackQ.toArray()[i] == 1){
				ackCount++;
			}
		}
		
		
		if (ackCount==0){
			if (!GlobalStatic.flag_P1 || !GlobalStatic.flag_P3){
				abort();
				return;
			}
		}
		else if (ackCount==1){
			if (!GlobalStatic.flag_P1 && !GlobalStatic.flag_P3){
				abort();
				return;
			}
		}
		
	}
	

	@Override
	public void updateWriteSet(String S, int val) {
		Map<String,Integer> M;
		if (writeSet.containsKey(S)){
			M = writeSet.get(S);
		}
		else{
			M = new HashMap<String,Integer>();
		}
		M.put("dirty", 1);
		M.put("val", val);
		if (S.equals("A"))
			writeSet.put("A", M);
		else
			writeSet.put("B", M);
	}

	@Override
	public void updateReadSet(String S) {
		Map<String,Integer>	M = new HashMap<String,Integer>();
		M.put("valid", 1);
		if (S.equals("A")){
			M.put("val", GlobalStatic.A);
			readSet.put("A", M);
		}
		else{
			M.put("val", GlobalStatic.B);
			readSet.put("B", M);
		}
	}
	
	public boolean intersect(Set<String> A, Set<String> B){
		for (String key : A){
			if (B.contains(key)){
				return true;
			}
		}
		return false;
	}
	
	public void abort(){
		System.out.println("!!!! P2 ABORTS !!!!");
		t.interrupt();
		new Thread(new P2()).start();
	}
}