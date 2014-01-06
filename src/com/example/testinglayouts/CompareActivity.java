package com.example.testinglayouts;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.testinglayouts.util.SystemUiHider;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class CompareActivity extends Activity {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private String message = "";
	private String messageTwo = "";
	private double[] numbersX;
	private double[] numbersY;
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

		setContentView(R.layout.activity_compare);

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
		findViewById(R.id.dummy_button).setOnTouchListener(
				mDelayHideTouchListener);
		//findViewById(R.id.graph1).setOnTouchListener(
		//		mDelayHideTouchListener);
		
		
		//LinearLayout layout = null;
		//layout = (LinearLayout) findViewById(R.id.graph1);
		//layout.setOnClickListener(this);

		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		message = extras.getString(GraphingActivity.EXTRA_MESSAGE);
		
		numbersX = extras.getDoubleArray(GraphingActivity.EXTRA_MESSAGE_DATA + "X");
		numbersY = extras.getDoubleArray(GraphingActivity.EXTRA_MESSAGE_DATA + "Y");

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

	public void sendMessage(View view) 
    {
    	finish();
    }
	
	protected void toggleLegend(View view)
	{
		toggleLegend(findViewById(R.id.graph1));
	}
	protected void whichGraph(int thisGraph)
	{
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		
		// first init data
		// sin curve
		int numSin = 150;
		GraphViewData[] data = new GraphViewData[numSin];
		double vSin=0;
		for (int i=0; i<numSin; i++) 
		{
			vSin += 0.2;
		   data[i] = new GraphViewData(i, Math.sin(vSin));
		}
		GraphViewSeriesStyle styleSin = new GraphViewSeriesStyle(Color.rgb(200, 50, 00), 5);
		GraphViewSeries seriesSin = new GraphViewSeries("Sinus curve", styleSin, data);
		 
		
		
		// cos curve
		data = new GraphViewData[numSin];
		vSin=0;
		for (int i=0; i<numSin; i++) 
		{
			vSin += 0.2;
		   data[i] = new GraphViewData(i, Math.cos(vSin));
		}

		GraphViewSeriesStyle styleCos = new GraphViewSeriesStyle(Color.rgb(90, 250, 00), 5);
		GraphViewSeries seriesCos = new GraphViewSeries("Cosinus curve", styleCos, data);
		 
		
		
		// random curve
		numSin = 1000;
		data = new GraphViewData[numSin];
		vSin=0;
		for (int i=0; i<numSin; i++)
		{
			vSin += 0.2;
		   data[i] = new GraphViewData(i, Math.sin(Math.random()*vSin));
		}
		GraphViewSeriesStyle styleRnd = new GraphViewSeriesStyle(Color.rgb(90, 90, 90), 5);
		GraphViewSeries seriesRnd = new GraphViewSeries("Random curve", styleRnd, data);
		 
		
		
		// our graph
		data = new GraphViewData[numbersX.length];
		for (int i=0; i < numbersX.length; i++) 
		{
		   data[i] = new GraphViewData(numbersX[i], numbersY[i]);
		}
		
		GraphViewSeriesStyle styleCrd = new GraphViewSeriesStyle(Color.rgb(0, 0, 0), 5);
		GraphViewSeries graphThing = new GraphViewSeries(message, styleCrd, data);
		
		/*
		 * create graph
		 */
		
		GraphView graphView = new LineGraphView(this, "Region Compare");
		// add data
		graphView.addSeries(seriesCos);
		graphView.addSeries(seriesSin);
		graphView.addSeries(seriesRnd);
		graphView.addSeries(graphThing);
		// optional - set view port, start=2, size=10
		graphView.setViewPort(2, 10);
		graphView.setScalable(true);
		// optional - legend
		graphView.setShowLegend(TOGGLE_ON_CLICK);
		graphView.setLegendAlign(LegendAlign.BOTTOM);
		graphView.getGraphViewStyle().setLegendWidth(300);
		 
		layout.addView(graphView);
	}
	
	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

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
