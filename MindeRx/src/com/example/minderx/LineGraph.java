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
		
//		String dateInString = "1982-08-31 10:20:56";
//		Date date = sdf.parse(dateInString);
//		int x1 = date.getDay()*100+date.getHours();
//		
//		
//		String dateInString2 = "1982-08-31 12:20:56";
//		Date date2 = sdf.parse(dateInString2);
//		int x2 = date2.getDay()*100+date2.getHours();
//		
//		
//		String dateInString3 = "1982-08-31 14:20:56";
//		Date date3 = sdf.parse(dateInString3);
//		int x3 = date3.getDay()*100+date3.getHours();
//		
//		String dateInString4 = "1982-08-31 16:20:56";
//		Date date4 = sdf.parse(dateInString4);
//		int x4 = date4.getDay()*100+date4.getHours();
//		
//		String dateInString5 = "1982-08-31 18:20:56";
//		Date date5 = sdf.parse(dateInString5);
//		int x5 = date5.getDay()*100+date5.getHours();
//		
//		String dateInString6 = "1982-08-31 20:20:56";
//		Date date6 = sdf.parse(dateInString6);
//		int x6 = date6.getDay()*100+date6.getHours();
//		
//		String dateInString7 = "1982-08-31 22:20:56";
//		Date date7 = sdf.parse(dateInString7);
//		int x7 = date7.getDay()*100+date7.getHours();
//
//		
//		double[] x = { x1, x2, x3, x4, x5, x6, x7}; // x values!
//		double[] y =  { 30.5, 34.3, 45.4, 57.3, 77.4, 89.5, 100.3}; // y values!
//		TimeSeries series = new TimeSeries("Line1"); 
//		for( int i = 0; i < x.length; i++)
//		{
//			series.add(x[i], y[i]);
//		}
		
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		
//		
//		int[] x2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }; // x values!
//		int[] y2 =  { 145, 123, 111, 100, 89, 77, 57, 45, 34, 30}; // y values!
//		TimeSeries series2 = new TimeSeries("Line2"); 
//		for( int i = 0; i < x2.length; i++)
//		{
//			series2.add(x2[i], y2[i]);
//		}
//		
//		int[] x3 = { 12, 23, 31, 64, 54, 65, 76, 83, 98, 10 }; // x values!
//		int[] y3 =  { 145, 13, 111, 10, 89, 7, 57, 57, 34, 30};
//		TimeSeries series3 = new TimeSeries("Line3"); 
//		for( int i = 0; i < x2.length; i++)
//		{
//			series3.add(x3[i], y3[i]);
//		}
		
		
		
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
		
	
		
//		dataset.addSeries(series);
//		dataset.addSeries(series2);
//		dataset.addSeries(series3);
//		
//
//		XYSeriesRenderer renderer = new XYSeriesRenderer(); // This will be used to customize line 1
//		XYSeriesRenderer renderer2 = new XYSeriesRenderer();
//		XYSeriesRenderer renderer3 = new XYSeriesRenderer();// This will be used to customize line 2
//		mRenderer.addSeriesRenderer(renderer);
//		mRenderer.addSeriesRenderer(renderer2);
//		mRenderer.addSeriesRenderer(renderer3);
//		
//		// Customization time for line 1!
//		renderer.setColor(Color.WHITE);
//		renderer.setPointStyle(PointStyle.SQUARE);
//		renderer.setFillPoints(true);
//		// Customization time for line 2!
//		renderer2.setColor(Color.YELLOW);
//		renderer2.setPointStyle(PointStyle.DIAMOND);
//		renderer2.setFillPoints(true);
//		
//		renderer3.setColor(Color.RED);
//		renderer3.setPointStyle(PointStyle.DIAMOND);
//		renderer3.setFillPoints(true);
		
		Intent intent = ChartFactory.getLineChartIntent(context, dataset, mRenderer, "Patient Vital Chart");
		return intent;
		
	}

}
