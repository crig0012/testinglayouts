package com.example.testinglayouts;

import java.lang.reflect.Array;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.testinglayouts.util.SystemUiHider;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;
//import android.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class GraphingActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private boolean usedTooMuchEnergy = false;
	private GraphViewData[] data;
	private String message = "";
	public final static String EXTRA_MESSAGE = "com.graphingactivity.gbp.MESSAGE";
	public final static String EXTRA_MESSAGE_DATA = "com.graphingactivity.gbp.MESSAGE_DATA";
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	//private SystemUiHider mSystemUiHider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = null;
		
		setContentView(R.layout.activity_graphing);
		
		Intent intent = getIntent();
		extras = intent.getExtras();
		
		whichGraph(extras);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	protected void whichGraph(Bundle extras)
	{
		//TODO: Make an array of graphs, then call showGraph(thisGraph)?
		LinearLayout layout = null;
		GraphView graphView;
		GraphViewSeries seriesRnd;
		GraphViewSeriesStyle styleRnd;
		double vSin = 0;
		String thisGraph = "";
		
		for(int j = 0; j < extras.size(); j++)
		{
			if(j == 0)
			{
				thisGraph = extras.getString(FullscreenActivity.DEVICE_ONE);
				layout = (LinearLayout) findViewById(R.id.graph1);
				if(thisGraph == null)
					continue;
			}
			
			else if(j == 1)
			{
				thisGraph = extras.getString(FullscreenActivity.DEVICE_TWO);
				layout = (LinearLayout) findViewById(R.id.graph2);
					continue;
			}
			
			else if(j == 2)
			{
				thisGraph = extras.getString(FullscreenActivity.DEVICE_THREE);
				layout = (LinearLayout) findViewById(R.id.graph3);
				if(thisGraph == null)
					continue;
			}
			
			else if(j == 3)
			{
				thisGraph = extras.getString(FullscreenActivity.DEVICE_FOUR);
				layout = (LinearLayout) findViewById(R.id.graph4);
				if(thisGraph == null)
					continue;
			}
			
			// random curve
			int numSin = 1000;
			data = new GraphViewData[numSin];
			vSin =0;
			for (int i=0; i<numSin; i++)
			{
				vSin += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
			}
			styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
			seriesRnd = new GraphViewSeries(thisGraph, styleRnd, data);
			 
			graphView = new LineGraphView(this, thisGraph);
				
			// add data
			graphView.addSeries(seriesRnd);
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			
			layout.addView(graphView);
			
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification(thisGraph, j);
			}
		}
	}
	
	protected void energyNotification(String graphName, int graphPosition)
	{
		graphName = graphName.toLowerCase();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
        		.setSmallIcon(getResources().getIdentifier(graphName, "drawable", getPackageName()))
                .setContentTitle("Energy Usage is High")
                .setContentText("Your " + graphName + " is using too much energy");

        Intent resultIntent = new Intent(this, GraphingActivity.class);
        Bundle extras = new Bundle();
        
        if(graphPosition == 0)
        	extras.putString(FullscreenActivity.DEVICE_ONE, graphName.toUpperCase());
        if(graphPosition == 1)
        	extras.putString(FullscreenActivity.DEVICE_TWO, graphName.toUpperCase());
        if(graphPosition == 2)
        	extras.putString(FullscreenActivity.DEVICE_THREE, graphName.toUpperCase());
        if(graphPosition == 3)
        	extras.putString(FullscreenActivity.DEVICE_FOUR, graphName.toUpperCase());
        
        resultIntent.putExtras(extras);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(GraphingActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        mNotificationManager.notify(0, mBuilder.build());
        
		Toast.makeText(this, "Your " + graphName + "  is using too much energy", Toast.LENGTH_LONG).show();
		
		usedTooMuchEnergy = true;
	}
	
	protected int tooMuchEnergy(GraphViewData[] checkThis)
	{
		double oldNum = 0;
		double newNum = 0;
		int numberHolderPositionSmall = 0;
		int numberHolderPositionMedium = 0;
		int numberHolderPositionLarge = 0;
		
		for(int i = 1; i < Array.getLength(checkThis); i++)
		{
			oldNum = checkThis[i-1].getY();
			newNum = checkThis[i].getY();
			
			if((newNum - oldNum) >= 1)
			{
				if((newNum - oldNum) >= 10)
				{
					if((newNum - oldNum) >= 15)
					{
						numberHolderPositionLarge++;
					}
					
					else
					{
						numberHolderPositionMedium++;
					}
				}

				else
				{
					numberHolderPositionSmall++;
				}
				
				if(numberHolderPositionSmall >= 1)
				//if((numberHolderPositionSmall + (numberHolderPositionMedium * 2) + (numberHolderPositionLarge * 3)) >= 15)
				{
					return 0;
				}
			}
		}
		return -1;
		
		//return numberHolder;
	}
	
	public void sendMessage(View view) 
    {
    	Intent intent = new Intent(this, FullscreenActivity.class);
    	Intent toCompare = new Intent(this, CompareActivity.class);
    	double numbersX[] = new double[data.length];
    	double numbersY[] = new double[data.length];
		Bundle extras = new Bundle();

		for(int i = 0; i < data.length; i++)
		{
			numbersX[i] = data[i].getX();
			numbersY[i] = data[i].getY();
		}
		switch(view.getId())
		{
		case R.id.dummy_button:
			extras.putString(EXTRA_MESSAGE, message);
	    		
	    	//TODO: Make this modular. Make everything modular
	    	extras.putSerializable(EXTRA_MESSAGE_DATA + "X", numbersX);
	    	extras.putSerializable(EXTRA_MESSAGE_DATA + "Y", numbersY);
	    	toCompare.putExtras(extras);

	        startActivity(toCompare);

		  break;

		  case R.id.back:
			if(usedTooMuchEnergy == true)
		    {
				//TODO: Get rid of compare button
		       	intent.putExtra(EXTRA_MESSAGE, message);
		       	startActivity(intent);
		    }
		    	
		    else
		    {
		       	intent.putExtra(EXTRA_MESSAGE, "Good usage");
		       	startActivity(intent);
		    }
			break;
    	}
    }
	
	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	/*View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};*/

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			//mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
}
