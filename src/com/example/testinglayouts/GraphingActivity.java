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
import com.jjoe64.graphview.GraphView.LegendAlign;
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
	private boolean regionalComparison = true;
	private GraphViewData[] data;
	private GraphViewData[] dataOnPeak;
	private GraphViewData[] dataOnMid;
	private GraphViewData[] dataOffPeak;// Make a dplain data?
	private String message = "";
	private String[] excessiveItems;
	public final static String EXTRA_MESSAGE = "com.graphingactivity.gbp.MESSAGE";
	public final static String EXTRA_MESSAGE_DATA = "com.graphingactivity.gbp.MESSAGE_DATA";
	private static final boolean AUTO_HIDE = true;
	Bundle extras = null;
	private double thisTime;
	ImageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_graphing);

		adapter = new ImageAdapter(this, false);
		excessiveItems = new String[4];
		for (int i = 0; i < excessiveItems.length; i++) {
			excessiveItems[i] = "EMPTY";
		}

		Intent intent = getIntent();
		extras = intent.getExtras();

		whichGraph(extras);
		// TODO: why does this break? Try only one series again
		String wut = "";
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	protected void whichGraph(Bundle extras) {
		// TODO: Make an array of graphs, then call showGraph(thisGraph)?
		// TODO: Remove the compare button, make a menu, make a button called
		// compare, and a submenu for what to compare to. Make a button to witch
		// grpah type
		LinearLayout layout = null;
		GraphView graphView = null;
		GraphViewSeries seriesOnPeak;
		GraphViewSeries seriesOnMid;
		GraphViewSeries seriesOffPeak;
		GraphViewSeriesStyle styleOnPeak;
		GraphViewSeriesStyle styleOnMid;
		GraphViewSeriesStyle styleOffPeak;
		double vSin = 0;
		String thisGraph = "";

		for (int j = 0; j < extras.size(); j++) {
			if (j == 0) {
				thisGraph = extras.getString(FullscreenActivity.DEVICE_ONE);
				layout = (LinearLayout) findViewById(R.id.graph1);
				layout.removeAllViews();

				if (thisGraph == null)
					continue;

				if (thisGraph.contains("_white"))
					thisGraph = thisGraph.replace("_white", "");
				if (thisGraph.contains("_red"))
					thisGraph = thisGraph.replace("_red", "");
			}

			else if (j == 1) {
				thisGraph = extras.getString(FullscreenActivity.DEVICE_TWO);
				layout = (LinearLayout) findViewById(R.id.graph2);
				layout.removeAllViews();

				if (thisGraph == null)
					continue;

				if (thisGraph.contains("_white"))
					thisGraph = thisGraph.replace("_white", "");
				if (thisGraph.contains("_red"))
					thisGraph = thisGraph.replace("_red", "");
			}

			else if (j == 2) {
				thisGraph = extras.getString(FullscreenActivity.DEVICE_THREE);
				layout = (LinearLayout) findViewById(R.id.graph3);
				layout.removeAllViews();

				if (thisGraph == null)
					continue;

				if (thisGraph.contains("_white"))
					thisGraph = thisGraph.replace("_white", "");
				if (thisGraph.contains("_red"))
					thisGraph = thisGraph.replace("_red", "");
			}

			else if (j == 3) {
				thisGraph = extras.getString(FullscreenActivity.DEVICE_FOUR);
				layout = (LinearLayout) findViewById(R.id.graph4);
				layout.removeAllViews();

				if (thisGraph == null)
					continue;

				if (thisGraph.contains("_white"))
					thisGraph = thisGraph.replace("_white", "");
				if (thisGraph.contains("_red"))
					thisGraph = thisGraph.replace("_red", "");
			}

			graphView = new LineGraphView(this, thisGraph);
			
//			adapter.

			// random curve
			int numSin = 24;
			thisTime = 7;
			dataOnPeak = new GraphViewData[numSin];
			dataOnMid = new GraphViewData[numSin];
			dataOffPeak = new GraphViewData[numSin];
			vSin = 0;
			for (double i = 0; i < numSin; i++) {
				vSin += 0.2;
				// if ((i >= 7 && i < 11) || (i >= 17 && i < 19)) {
				// if (whatTimeIsIt(i) == 7 || whatTimeIsIt(i) == 17) {
				dataOnPeak[(int) i] = new GraphViewData(i, Math.abs(Math
						.sin(Math.random() * vSin)));
				// dataOnMid[(int) i] = new GraphViewData(i, 0);
				// dataOffPeak[(int) i] = new GraphViewData(i, 0);
				// } else if (i >= 11 && i < 17) {
				// else if (whatTimeIsIt(i) == 11) {
				// dataOnPeak[(int) i] = new GraphViewData(i, 0);
				// dataOnMid[(int) i] = new GraphViewData(i,
				// Math.abs(Math.sin(Math.random() * vSin)));
				// dataOffPeak[(int) i] = new GraphViewData(i, 0);
				// } else {
				// else if (whatTimeIsIt(i) == 19) {
				// dataOnPeak[(int) i] = new GraphViewData(i, 0);
				// dataOffPeak[(int) i] = new GraphViewData(i,
				// Math.abs(Math.sin(Math.random() * vSin)));
				// dataOnMid[(int) i] = new GraphViewData(i, 0);
				// }
			}//TODO: Figure this out
			styleOnPeak = new GraphViewSeriesStyle(Color.RED, 5);
			// styleOnMid = new GraphViewSeriesStyle(Color.YELLOW, 5);
			// styleOffPeak = new GraphViewSeriesStyle(Color.GREEN, 5);

			seriesOnPeak = new GraphViewSeries(thisGraph, styleOnPeak,
					dataOnPeak);
			// seriesOnMid = new GraphViewSeries(thisGraph, styleOnMid,
			// dataOnMid);
			// seriesOffPeak = new GraphViewSeries(thisGraph, styleOffPeak,
			// dataOffPeak);

			graphView.addSeries(seriesOnPeak);
			// graphView.addSeries(seriesOnMid);
			// graphView.addSeries(seriesOffPeak);

			if (regionalComparison == true) {
				data = new GraphViewData[numSin];
				vSin = 0;
				for (int i = 0; i < numSin; i++) {
					vSin += 0.2;
					data[i] = new GraphViewData(i, Math.abs(Math.cos(Math
							.random() * vSin)));
				}

				GraphViewSeriesStyle styleCos = new GraphViewSeriesStyle(
						Color.BLUE, 5);
				GraphViewSeries seriesCos = new GraphViewSeries("Regional "
						+ thisGraph, styleCos, data);

				graphView.addSeries(seriesCos);
			}

			if (regionalComparison == false) {
				data = new GraphViewData[numSin];
				vSin = 0;
				for (int i = 0; i < numSin; i++) {
					vSin += 0.2;
					data[i] = new GraphViewData(i, Math.abs(Math.cos(Math
							.random() * vSin)));
				}

				GraphViewSeriesStyle styleCos = new GraphViewSeriesStyle(
						Color.BLUE, 5);
				GraphViewSeries seriesCos = new GraphViewSeries("Industry "
						+ thisGraph, styleCos, data);

				graphView.addSeries(seriesCos);
			}

			graphView.setViewPort(0, 24);
			graphView.setScrollable(true);
			graphView.setScalable(true);
			graphView.setShowLegend(true);
			graphView.setLegendAlign(LegendAlign.BOTTOM);
			graphView.getGraphViewStyle().setLegendWidth(400);
//			graphView.setBackgroundColor(Color.BLUE);
			graphView.setBackgroundColor(Color.rgb(80, 30, 30));

			layout.addView(graphView);

			// private GraphViewData[] dataOffPeak; Make this a collection of
			// all the others
			if (tooMuchEnergy(data) == 0) {
				energyNotification(thisGraph, j);
			}
		}
	}

	protected double whatTimeIsIt(double isItThisTime) {

		if ((isItThisTime / 24) - (7 / 24) == 1)
			thisTime = 7;
		else if ((isItThisTime / 24) - (11 / 24) == 1)
			thisTime = 11;
		else if ((isItThisTime / 24) - (17 / 24) == 1)
			thisTime = 17;
		else if ((isItThisTime / 24) - (19 / 24) == 1)
			thisTime = 7;
		else
			thisTime = thisTime;

		return thisTime;
	}

	protected void energyNotification(String graphName, int graphPosition) {
		graphName = graphName.toLowerCase();
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this)
				.setSmallIcon(
						getResources().getIdentifier(graphName + "_white",
								"drawable", getPackageName()))
				.setContentTitle("Energy Usage is High")
				.setContentText(
						"Your " + graphName + " is using too much energy");

		Intent resultIntent = new Intent(this, GraphingActivity.class);
		Bundle extras = new Bundle();

		if (graphPosition == 0)
			extras.putString(FullscreenActivity.DEVICE_ONE,
					graphName.toUpperCase());
		if (graphPosition == 1)
			extras.putString(FullscreenActivity.DEVICE_TWO,
					graphName.toUpperCase());
		if (graphPosition == 2)
			extras.putString(FullscreenActivity.DEVICE_THREE,
					graphName.toUpperCase());
		if (graphPosition == 3)
			extras.putString(FullscreenActivity.DEVICE_FOUR,
					graphName.toUpperCase());

		resultIntent.putExtras(extras);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(GraphingActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotificationManager.notify(0, mBuilder.build());

		Toast.makeText(this,
				"Your " + graphName + "  is using too much energy",
				Toast.LENGTH_SHORT).show();

		for (int i = 0; i < excessiveItems.length; i++) {
			if (excessiveItems[i] == "EMPTY") {
				excessiveItems[i] = graphName;
				break;
			}
		}

		usedTooMuchEnergy = true;
	}

	protected int tooMuchEnergy(GraphViewData[] checkThis) {
		double oldNum = 0;
		double newNum = 0;
		int numberHolderPositionSmall = 0;
		int numberHolderPositionMedium = 0;
		int numberHolderPositionLarge = 0;

		for (int i = 1; i < Array.getLength(checkThis); i++) {
			oldNum = checkThis[i - 1].getY();
			newNum = checkThis[i].getY();

			if ((newNum - oldNum) >= 1) {
				if ((newNum - oldNum) >= 10) {
					if ((newNum - oldNum) >= 15) {
						numberHolderPositionLarge++;
					}

					else {
						numberHolderPositionMedium++;
					}
				}

				else {
					numberHolderPositionSmall++;
				}

				if (numberHolderPositionSmall >= 1)
				// if((numberHolderPositionSmall + (numberHolderPositionMedium *
				// 2) + (numberHolderPositionLarge * 3)) >= 15)
				{
					return 0;
				}
			}
		}
		return -1;

		// return numberHolder;
	}

	/*
	 * String appendText = newY + "|";// Stopped using new lines // for // pipes
	 * "|" // Environment.NewLine; try { outputStream =
	 * mContext.openFileOutput(name, mContext.MODE_APPEND); OutputStreamWriter
	 * osw = new OutputStreamWriter(outputStream); osw.write(GRAPHINFO);
	 * osw.flush(); osw.close(); } catch (FileNotFoundException e) {
	 * e.printStackTrace(); }
	 */

	public void sendMessage(View view) {
		Intent intent = new Intent(this, FullscreenActivity.class);
		Intent toCompare = new Intent(this, CompareActivity.class);
		// double numbersX[] = new double[data.length];
		// double numbersY[] = new double[data.length];
		Bundle extraStuff = new Bundle();

		/*
		 * for (int i = 0; i < data.length; i++) { numbersX[i] = data[i].getX();
		 * numbersY[i] = data[i].getY(); }
		 */
		switch (view.getId()) {
		case R.id.dummy_button:
			regionalComparison = !regionalComparison;
			whichGraph(extras);
			break;

		case R.id.back:
			// TODO: Add this back in, also put graphName in the intent, then in
			// FA hve it change the icon to red
			// intent.putExtra(EXTRA_MESSAGE, usedTooMuchEnergy);
			extras.putStringArray(EXTRA_MESSAGE_DATA, excessiveItems);
			intent.putExtras(extras);

			startActivity(intent);
			break;
		}
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	/*
	 * View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener()
	 * {
	 * 
	 * @Override public boolean onTouch(View view, MotionEvent motionEvent) { if
	 * (AUTO_HIDE) { delayedHide(AUTO_HIDE_DELAY_MILLIS); } return false; } };
	 */

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			// mSystemUiHider.hide();
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
