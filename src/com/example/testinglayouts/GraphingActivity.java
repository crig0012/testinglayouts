package com.example.testinglayouts;

import java.lang.reflect.Array;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.example.testinglayouts.util.SystemUiHider;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
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
	private SystemUiHider mSystemUiHider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_graphing);

		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.fullscreen_content);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
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
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		//findViewById(R.id.dummy_button).setOnTouchListener(
		//	mDelayHideTouchListener);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(FullscreenActivity.EXTRA_MESSAGE);
		whichGraph(Integer.parseInt(message));

		/**For manual data entry:
		 GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		      new GraphViewData(1, 2.0d)
		      , new GraphViewData(2, 1.5d)
		      , new GraphViewData(3, 2.5d)
		      , new GraphViewData(4, 1.0d)
		      , new GraphViewData(5, 1.0d)
		      , new GraphViewData(6, 1.0d)
		      , new GraphViewData(7, 1.0d)
		      , new GraphViewData(8, 2.0d)
		      , new GraphViewData(9, 1.5d)
		      , new GraphViewData(10, 2.5d)
		      , new GraphViewData(11, 1.0d)
		      , new GraphViewData(12, 1.0d)
		      , new GraphViewData(13, 1.5d)
		      , new GraphViewData(14, 1.0d)
		      , new GraphViewData(15, 2.0d)
		      , new GraphViewData(16, 1.5d)
		      , new GraphViewData(17, 2.5d)
		      , new GraphViewData(18, 1.0d)
		      , new GraphViewData(19, 1.5d)
		      , new GraphViewData(20, 1.0d)
		      , new GraphViewData(21, 1.0d)
		      , new GraphViewData(22, 2.0d)
		      , new GraphViewData(23, 1.5d)
		      , new GraphViewData(24, 2.5d)
		      , new GraphViewData(25, 1.0d)
		      , new GraphViewData(26, 1.5d)
		      , new GraphViewData(27, 1.0d)
		      , new GraphViewData(28, 1.0d)
		});
		 */
		/*// draw sin curve
		int num = 150;
		GraphViewData[] data = new GraphViewData[num];
		double v=0;
		for (int i=0; i<num; i++) {
		   v += 0.2;
		   data[i] = new GraphViewData(i, Math.sin(v));
		}
		GraphView graphView = new LineGraphView(
		      this
		      , message
		);
		// add data
		graphView.addSeries(new GraphViewSeries(data));
		// set view port, start=2, size=40
		graphView.setViewPort(2, 40);
		graphView.setScrollable(true);
		// optional - activate scaling / zooming
		graphView.setScalable(true);
		 
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);*/
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
		LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
		
		switch(thisGraph)
		{
		case 0:
			GraphViewData[] tempData = new GraphViewData[] {
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
			
			GraphViewSeries exampleSeries = new GraphViewSeries(tempData);
			
			GraphView graphViewTV = new LineGraphView(
				      this // context
				      , "Television" // heading
				);
			graphViewTV.addSeries(exampleSeries); // data

			// set view port, start=2, size=40
			graphViewTV.setViewPort(2, 40);
			// optional - activate scaling / zooming
			graphViewTV.setScrollable(true);
			graphViewTV.setScalable(true);

			tooMuchEnergy(tempData);
			layout.addView(graphViewTV);
			break;
			
		case 1:
			// draw sin curve
			int num = 150;
			GraphViewData[] data = new GraphViewData[num];
			double v=0;
			for (int i=0; i<num; i++) {
			   v += 0.2;
			   data[i] = new GraphViewData(i, Math.sin(v));
			}
			GraphView graphViewOven = new LineGraphView(
			      this
			      , "Oven"
			);
			// add data
			graphViewOven.addSeries(new GraphViewSeries(data));
			// set view port, start=2, size=40
			graphViewOven.setViewPort(2, 40);
			graphViewOven.setScrollable(true);
			// optional - activate scaling / zooming
			graphViewOven.setScalable(true);
			 
			layout.addView(graphViewOven);
			break;
			
		case 2:
			// draw cos curve
			int otherNum = 150;
			GraphViewData[] dataOther = new GraphViewData[otherNum];
			double w=0;
			for (int i=0; i<otherNum; i++) {
			   w += 0.2;
			   dataOther[i] = new GraphViewData(i, Math.cos(w));
			}
			GraphView graphViewCordless = new LineGraphView(
			      this
			      , "Oven"
			);
			// add data
			graphViewCordless.addSeries(new GraphViewSeries(dataOther));
			// set view port, start=2, size=40
			graphViewCordless.setViewPort(2, 40);
			graphViewCordless.setScrollable(true);
			// optional - activate scaling / zooming
			graphViewCordless.setScalable(true);
			 
			layout.addView(graphViewCordless);
			break;
		}
	}
	
	protected int tooMuchEnergy(GraphViewData[] checkThis)
	{
		double oldNum = 0;
		double newNum = 0;
		
		for(int i = 1; i < Array.getLength(checkThis); i++)
		{
			oldNum = checkThis[i-1].getY();
			newNum = checkThis[i].getY();
			
			if((newNum - oldNum) >= 10)
			{
				//TODO: Make an array in the function that calls this one, and have it record the received numbers. Then, if there are so many in the array, empty it and do something accordingly. If there are 10 0s, say your energy spiked. Five -1s, clear it and do nothing.
				return 0;
			}
		}
		return -1;
	}
	
	public void sendMessage(View view) 
    {
    	Intent intent = new Intent(this, FullscreenActivity.class);
    	startActivity(intent);
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
			mSystemUiHider.hide();
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
