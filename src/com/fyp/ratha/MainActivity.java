package com.fyp.ratha;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.achartengine.GraphicalView;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity implements Observer  {

	GraphManager gm=GraphManager.getInstance();
		
	LinearLayout graphView1,graphView2;	
	
	MultiLineGraph linegraph,linegraph2;
	GraphicalView graph,graph2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		graphView1 = (LinearLayout)findViewById(R.id.layout1);
		graphView2 = (LinearLayout)findViewById(R.id.layout2);
		
		/*
		ListView list = (ListView)findViewById(R.id.listView1);
		ArrayList<MultiLineGraph> graphlist=new ArrayList<MultiLineGraph>();
		GraphViewAdaptor adapter=new GraphViewAdaptor(this, R.layout.graphlistitem, graphlist);
		list.setAdapter(adapter);
		graphlist.add(linegraph);
		*/  
		
		linegraph=new MultiLineGraph(Constants.OBDII_REAL_TIME_DATA);
		linegraph2=new MultiLineGraph(Constants.OBDII_REAL_TIME_DATA);
		
		linegraph.addLine(Constants.OBDII_SPEED, Constants.OBDII_SPEED_LABEL,Color.GREEN,200);
		linegraph2.addLine(Constants.OBDII_RPM, Constants.OBDII_RPM_LABEL,Color.RED,1);
		
		gm.addGraph(Constants.OBDII_REAL_TIME_DATA, linegraph);
		gm.addGraph(Constants.OBDII_REAL_TIME_DATA, linegraph2);
		
		graph = linegraph.getView(this);
		graph2 = linegraph2.getView(this);
		
		graphView1.addView(graph);
		graphView2.addView(graph2);
	
		MockCommunicator.getInstance().start();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void showSpeed(View view){	
		gm.addObserver(this);	
	}

	@Override
	public void update(Observable observable, Object data) {
		graph.repaint();
		graph2.repaint();
	}
	
	
}
