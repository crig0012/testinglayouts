package com.example.testinglayouts;

import java.lang.reflect.Array;

import android.app.Activity;
import android.app.Notification;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.testinglayouts.util.SystemUiHider;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

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

		setContentView(R.layout.activity_graphing);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		/*mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});*/

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		//findViewById(R.id.dummy_button).setOnTouchListener(
		//	mDelayHideTouchListener);
		
		Intent intent = getIntent();
		if(intent == null)
		{
			message = "0";
		}
		else
		{
			message = intent.getStringExtra(FullscreenActivity.EXTRA_MESSAGE);
		}
		
		if(message == null)
			message = intent.getStringExtra(GraphingActivity.EXTRA_MESSAGE);;
		
		if(message == null)
			message = "0";
		
		whichGraph(Integer.parseInt(message));
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	protected void whichGraph(int thisGraph)
	{
		//TODO: Make an array of graphs, then call showGraph(thisGraph)?
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		GraphView graphView;
		GraphViewSeries seriesRnd;
		GraphViewSeriesStyle styleRnd;
		
		
		switch(thisGraph)
		{
		case 0:
			data = new GraphViewData[] {
				      new GraphViewData(1, 2.0d), new GraphViewData(2, 1.5d), new GraphViewData(3, 2.5d)
				      , new GraphViewData(4, 1.0d), new GraphViewData(5, 1.0d), new GraphViewData(6, 1.0d)
				      , new GraphViewData(7, 1.0d), new GraphViewData(8, 2.0d), new GraphViewData(9, 1.5d)
				      , new GraphViewData(10, 2.5d), new GraphViewData(11, 1.0d), new GraphViewData(12, 1.0d)
				      , new GraphViewData(13, 1.5d), new GraphViewData(14, 1.0d), new GraphViewData(15, 2.0d)
				      , new GraphViewData(16, 1.5d), new GraphViewData(17, 2.5d), new GraphViewData(18, 1.0d)
				      , new GraphViewData(19, 1.5d), new GraphViewData(20, 1.0d), new GraphViewData(21, 1.0d)
				      , new GraphViewData(22, 2.0d), new GraphViewData(23, 1.5d), new GraphViewData(24, 2.5d)
				      , new GraphViewData(25, 1.0d), new GraphViewData(26, 1.5d), new GraphViewData(27, 1.0d)
				      , new GraphViewData(28, 1.0d)
				};
			
			GraphViewSeries exampleSeries = new GraphViewSeries(data);
			
			graphView = new LineGraphView(
				      this // context
				      , "Blender" // heading
				);
			graphView.addSeries(exampleSeries); // data

			// set view port, start=2, size=40
			graphView.setViewPort(2, 40);
			// optional - activate scaling / zooming
			graphView.setScrollable(true);
			graphView.setScalable(true);
			//graphViewTV.setHorizontalLabels(new String[] {"01:00", "02:00", "03:00","04:00", "05:00", "06:00", "07:00","08:00", "09:00", "10:00", "11:00", "12:00"});
			graphView.setVerticalLabels(new String[] {"high", "middle", "low"});

			tooMuchEnergy(data);
			layout.addView(graphView);
			
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
			
		case 1:
			// draw sin curve
			int num = 150;
			data = new GraphViewData[num];
			double v=0;
			for (int i=0; i<num; i++) {
			   v += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(v) * 10);
			}
			graphView = new LineGraphView(
			      this
			      , "Cordless"
			);
			// add data
			graphView.addSeries(new GraphViewSeries(data));
			// set view port, start=2, size=40
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			// optional - activate scaling / zooming
			graphView.setScalable(true);
			//TODO: Do a for loop, where i < graphView.lengthof, an int to go into a switch that gets ticked every time, and the switch appends a 24hr clock time to the string
			//graphViewOven.setHorizontalLabels(new String[] {"01:00", "02:00", "03:00","04:00", "05:00", "06:00", "07:00","08:00", "09:00", "10:00", "11:00", "12:00"});
			graphView.setVerticalLabels(new String[] {"high", "middle", "low"});
			 
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;

		case 2:
			// draw cos curve
			int otherNum = 150;
			data = new GraphViewData[otherNum];
			double w=0;
			for (int i=0; i<otherNum; i++) {
			   w += 0.2;
			   data[i] = new GraphViewData(i, Math.cos(w));
			}
			graphView = new LineGraphView(
			      this
			      , "Dishwasher"
			);
			// add data
			graphView.addSeries(new GraphViewSeries(data));
			// set view port, start=2, size=40
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			// optional - activate scaling / zooming
			graphView.setScalable(true);
			graphView.setHorizontalLabels(new String[] {"01:00", "02:00", "03:00","04:00", "05:00", "06:00", "07:00","08:00", "09:00", "10:00", "11:00", "12:00"});
			graphView.setVerticalLabels(new String[] {"high", "middle", "low"});			
			 
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
			
		case 3:
			// random curve
			int numSin = 1000;
			data = new GraphViewData[numSin];
			int vSin =0;
			for (int i=0; i<numSin; i++)
			{
				vSin += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
			}
			styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
			seriesRnd = new GraphViewSeries("Random curve", styleRnd, data);
			 
			graphView = new LineGraphView(
				      this
				      , message
				);
				
			// add data
			graphView.addSeries(seriesRnd);
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
			
		case 4:
			// random curve
			numSin = 1000;
			data = new GraphViewData[numSin];
			vSin =0;
			for (int i=0; i<numSin; i++)
			{
				vSin += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
			}
			styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
			seriesRnd = new GraphViewSeries("Random curve", styleRnd, data);
			 
			graphView = new LineGraphView(
				      this
				      , message
				);
				
			// add data
			graphView.addSeries(seriesRnd);
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
			
		case 5:
			// random curve
			numSin = 1000;
			data = new GraphViewData[numSin];
			vSin =0;
			for (int i=0; i<numSin; i++)
			{
				vSin += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
			}
			styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
			seriesRnd = new GraphViewSeries("Random curve", styleRnd, data);
			 
			graphView = new LineGraphView(
				      this
				      , message
				);
				
			// add data
			graphView.addSeries(seriesRnd);
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
			
		case 6:
			// random curve
			numSin = 1000;
			data = new GraphViewData[numSin];
			vSin =0;
			for (int i=0; i<numSin; i++)
			{
				vSin += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
			}
			styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
			seriesRnd = new GraphViewSeries("Random curve", styleRnd, data);
			 
			graphView = new LineGraphView(
				      this
				      , message
				);
				
			// add data
			graphView.addSeries(seriesRnd);
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
			
		case 7:
			// random curve
			numSin = 1000;
			data = new GraphViewData[numSin];
			vSin =0;
			for (int i=0; i<numSin; i++)
			{
				vSin += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
			}
			styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
			seriesRnd = new GraphViewSeries("Random curve", styleRnd, data);
			 
			graphView = new LineGraphView(
				      this
				      , message
				);
				
			// add data
			graphView.addSeries(seriesRnd);
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
			
		case 8:
			// random curve
			numSin = 1000;
			data = new GraphViewData[numSin];
			vSin =0;
			for (int i=0; i<numSin; i++)
			{
				vSin += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
			}
			styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
			seriesRnd = new GraphViewSeries("Random curve", styleRnd, data);
			 
			graphView = new LineGraphView(
				      this
				      , message
				);
				
			// add data
			graphView.addSeries(seriesRnd);
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
			
		case 9:
			// random curve
			numSin = 1000;
			data = new GraphViewData[numSin];
			vSin =0;
			for (int i=0; i<numSin; i++)
			{
				vSin += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
			}
			styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
			seriesRnd = new GraphViewSeries("Random curve", styleRnd, data);
			 
			graphView = new LineGraphView(
				      this
				      , message
				);
				
			// add data
			graphView.addSeries(seriesRnd);
			graphView.setViewPort(2, 40);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			
			layout.addView(graphView);
			if(tooMuchEnergy(data) == 0)
			{
				energyNotification();
			}
			break;
		}
	}
	
	protected void energyNotification()
	{
		//TODO: Make message a string with the name of the applliance
		int resID = getResources().getIdentifier(message, "drawable", getPackageName());
		//ImageView image;
		//image.setImageResource(resID);
		
		//TODO: Use this when message is a string
		//.setSmallIcon(getResources().getIdentifier(message, "drawable", getPackageName()))
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.cordless)
                .setContentTitle("Energy Usage is High")
                .setContentText("Your " + message + " is using too much energy");

        Intent resultIntent = new Intent(this, GraphingActivity.class);

        resultIntent.putExtra(EXTRA_MESSAGE, message);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(GraphingActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        mNotificationManager.notify(0, mBuilder.build());
        
		Toast.makeText(this, "Your " + message + "  is using too much energy", Toast.LENGTH_LONG).show();
		
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
