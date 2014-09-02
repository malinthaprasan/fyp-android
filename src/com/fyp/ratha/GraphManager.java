package com.fyp.ratha;


import org.json.*;

import android.util.Log;

import java.util.ArrayList;
import java.util.Hashtable;

import java.util.Observable;

public class GraphManager extends Observable {

	private static GraphManager instance;
	private Hashtable<String, ArrayList<GeneralGraph>> graphtablelist;
	
	private GraphManager(){
		graphtablelist=new Hashtable<String, ArrayList<GeneralGraph>>();
	}
	
	public static GraphManager getInstance(){
		if (instance == null){
			instance=new GraphManager();
			return instance;
		}else
			return instance;
	}
	
	public Hashtable<String,  ArrayList<GeneralGraph>> getGraphTable() {
		return graphtablelist;
	}
	
	public void addGraph(String type, GeneralGraph g){
		ArrayList<GeneralGraph> list=graphtablelist.get(type);
		if(list == null){
			list=new ArrayList<GeneralGraph>();
			graphtablelist.put(type, list);
		}
		list.add(g);
	}
	
	public void addDataObj(JSONObject dataobj){
		
		try {
	
			String roottype=dataobj.names().getString(0);
			
			if(Constants.validateRootDataType(roottype)){
				for (GeneralGraph g : graphtablelist.get(roottype))
					g.addData(dataobj.getJSONObject(roottype));
			}else{
				Log.e(Constants.APPTAG,"Unsupported root data type :" + roottype);
			}
			
			setChanged();
			notifyObservers();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		
	}
	
}
