package com.fyp.ratha;

import java.util.ArrayList;
import java.util.Hashtable;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

public class MultiLineGraph implements GeneralGraph {
	
	private Hashtable<String, TimeSeries> seriestable;
	private Hashtable<String, XYSeriesRenderer> renderertable;
	private Hashtable<String, Integer> multipliertable;
	
	private ArrayList<String> addedlines;
	
	private XYMultipleSeriesDataset xyseries;
	private XYMultipleSeriesRenderer multirenderer;
	
	String datatype;
	
	public MultiLineGraph(String datatype) {
		
		seriestable=new Hashtable<String, TimeSeries>();
		renderertable =new Hashtable<String, XYSeriesRenderer>();
		multipliertable=new Hashtable<String, Integer>();
		addedlines =new ArrayList<String>();
		
		xyseries=new XYMultipleSeriesDataset();
		
		multirenderer =new XYMultipleSeriesRenderer();
		
		multirenderer.setBackgroundColor(Color.BLACK);
		multirenderer.setMarginsColor(Color.GRAY);
		multirenderer.setApplyBackgroundColor(true);

		multirenderer.setGridColor(Color.WHITE);
		multirenderer.setShowGrid(true);
		
		multirenderer.setAxesColor(Color.BLACK);
		multirenderer.setLabelsColor(Color.BLACK);
		multirenderer.setXLabelsColor(Color.BLACK);
		multirenderer.setYLabelsColor(0, Color.BLACK);
		
		this.datatype=datatype;
		
	}
	
	public void addLine(String type,String title,int color,int multiplier){
		
		TimeSeries series=new TimeSeries(title);				
		XYSeriesRenderer renderer =new XYSeriesRenderer();
		
		seriestable.put(type, series);
		renderertable.put(type, renderer);
		multipliertable.put(type, multiplier);
		
		addedlines.add(type);
		
		renderer.setColor(color);
		
		multirenderer.addSeriesRenderer(renderer);
		xyseries.addSeries(series);
	}
	
	public GraphicalView getView(Context context) {
		GraphicalView chartView=  ChartFactory.getLineChartView(context, xyseries, multirenderer);
		return chartView;		
	}
	
	
	
	@Override
	public void addData(JSONObject data){
		
		String subtype;
		for(int i=0; i<data.names().length();i++){
			try {
				subtype=data.names().getString(i);
				if(Constants.validateOBD2DataType(subtype)){
					if(seriestable.get(subtype)!=null){				
							JSONObject subobj=data.getJSONObject(subtype);
							int time = subobj.getInt(Constants.TIME);
							int value = subobj.getInt(Constants.VALUE);
							seriestable.get(subtype).add(time, value * multipliertable.get(subtype) );					
					}
				}else{
					Log.e(Constants.APPTAG,"Unsupported obd2 data type");
				}	
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	
}
