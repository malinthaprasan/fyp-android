package com.fyp.ratha;

import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class MockCommunicator extends Thread{
	
	private static MockCommunicator instance;
	private GraphManager gm=GraphManager.getInstance();
	private MockCommunicator(){}
	
	public static MockCommunicator getInstance(){
		if(instance==null){
			instance=new MockCommunicator();
		}
		return instance;
	}	
	
	
	public JSONObject createTimeValuePair(int time,int value){
		JSONObject obj=new JSONObject();		
		try {
			obj.put(Constants.TIME, time);
			obj.put(Constants.VALUE, value);
			return obj;
			
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	@Override
	public void run(){		
		for (int i = 0; i < 100; i++) {			
			JSONObject datapairs =new JSONObject();
			JSONObject dataobj =new JSONObject();
			
			try {
				datapairs.put(Constants.OBDII_SPEED,
						createTimeValuePair(i, new Random().nextInt(10)));
				datapairs.put(Constants.OBDII_RPM, 
						createTimeValuePair(i, new Random().nextInt(2500)));				
				dataobj.put(Constants.OBDII_REAL_TIME_DATA, datapairs);
				
				gm.addDataObj(dataobj);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}			
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

}
