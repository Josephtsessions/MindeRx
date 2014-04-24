package com.example.minderx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class LineGraph{

	public Intent getIntent(Context context, ArrayList<int[]> data) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		
		for(int i=0; i<data.size();i=i+2){
			TimeSeries series3 = new TimeSeries("Line"+i);
			int[] a = data.get(i);
			int[] b = data.get(i+1);
			for(int j = 0; j< a.length;j++){
				series3.add(a[j], b[j]);
			}
			XYSeriesRenderer renderer3 = new XYSeriesRenderer();
			dataset.addSeries(series3);
			mRenderer.addSeriesRenderer(renderer3);
			
			if(i == 0){
			renderer3.setColor(Color.BLUE);
			}else if( i == 2){
				renderer3.setColor(Color.RED);
			}else if( i== 4){
				renderer3.setColor(Color.YELLOW);
			}else if( i== 4){
				renderer3.setColor(Color.WHITE);
			}
			else{
				renderer3.setColor(Color.GREEN);
			}
			renderer3.setPointStyle(PointStyle.DIAMOND);
			renderer3.setFillPoints(true);
			
			
		}
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "MindeRx");
		return intent;
		
	}

}
