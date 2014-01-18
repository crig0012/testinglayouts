package com.example.testinglayouts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.example.testinglayouts.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class FullscreenActivity extends Activity {
	ImageAdapter adapter = new ImageAdapter(this);

	public final static String EXTRA_MESSAGE = "com.fullscreenactivity.gbp.MESSAGE";

	public final static String DEVICE_ONE = "com.fullscreenactivity.gbp.DEVICE_ONE";
	public final static String DEVICE_TWO = "com.fullscreenactivity.gbp.DEVICE_TWO";
	public final static String DEVICE_THREE = "com.fullscreenactivity.gbp.DEVICE_THREE";
	public final static String DEVICE_FOUR = "com.fullscreenactivity.gbp.DEVICE_FOUR";

	String selectedDeviceOne = null;
	String selectedDeviceTwo = null;
	String selectedDeviceThree = null;
	String selectedDeviceFour = null;
	
	GridView gridview;

	String[] temp;
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

		setContentView(R.layout.activity_fullscreen);

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
		findViewById(R.id.dummy_button).setOnTouchListener(
				mDelayHideTouchListener);

		setContentView(R.layout.activity_fullscreen);

		gridview = (GridView) findViewById(R.id.gridView1);
		gridview.setAdapter(adapter);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		// TODO: Get the app to save what is over and what isn't
		// Maybe send the names of items over, and read them here. Send a
		// different string array that is large enough to hold everything
		// Maybe save them to a file, then read it? That was I can still have
		// them there if the app gets closed
		// Save as a key-value set? That way I can read the first part for the
		// appliance name,and the second part for the colour. 1st part is key,
		// 2nd is value
		if (extras != null) {
			temp = extras.getStringArray(GraphingActivity.EXTRA_MESSAGE_DATA);

			if (temp != null) {
				for (int i = 0; i < temp.length; i++) {
					if (temp[i] != "EMPTY")
						adapter.changeColour("red", temp[i]);
				}
			}
		}
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO: Change colour of images. Red, white, green(?). Use the
				// tooMuchEnergy var to discern which colour to use
				// TODO: Have an ID and a name, that way name can be whatever,
				// ID will be unique and what gets passed around

				setSelectedDevice(position);
			}
		});
	}

	public ImageAdapter getAdapter() {
		return adapter;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sortmenu, menu);
		return true;
	}

	//TODO: Make a refresh function, that will organize the pictures in the order selected
	//TODO: Make it so that if you don't select any items, the house is sent
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.sortByGroup:
			if (item.isChecked())
				item.setChecked(false);
			else
				item.setChecked(true);
			return true;
		case R.id.sortByName:
			if (item.isChecked())
				item.setChecked(false);
			else
				item.setChecked(true);
			return true;
		case R.id.sortByType:
			if (item.isChecked())
				item.setChecked(false);
			else
				item.setChecked(true);
			return true;
		case R.id.sortByUsage:
			if (item.isChecked())
				item.setChecked(false);
			else
				item.setChecked(true);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setSelectedDevice(int position) {
		//TODO: Make the selected items into an array, this will simplify SO much
		
		String stringToCheck = adapter.getName(position);
				
		if(stringToCheck.equals(selectedDeviceOne))
		{
			adapter.changeColour("black", selectedDeviceOne);
			selectedDeviceOne = null;
			gridview.invalidateViews();
			return;
		}
		
		if(stringToCheck.equals(selectedDeviceTwo))
		{
			adapter.changeColour("black", selectedDeviceTwo);
			selectedDeviceTwo = null;
			gridview.invalidateViews();
			return;
		}
		
		if(stringToCheck.equals(selectedDeviceThree))
		{
			adapter.changeColour("black", selectedDeviceThree);
			selectedDeviceThree = null;
			gridview.invalidateViews();
			return;
		}
		
		if(stringToCheck.equals(selectedDeviceFour))
		{
			adapter.changeColour("black", selectedDeviceFour);
			selectedDeviceFour = null;
			gridview.invalidateViews();
			return;
		}
		
		String temp = null;
		
		if(adapter.getName(position).contains("_white"))
			temp = adapter.getName(position).replace("_white", "");
		else if(adapter.getName(position).contains("_red"))
			temp = adapter.getName(position).replace("_red", "");
		else
			temp = adapter.getName(position);
		
		Toast.makeText(FullscreenActivity.this,
				temp, Toast.LENGTH_SHORT).show();

		if (selectedDeviceFour != null)
			adapter.changeColour("black", selectedDeviceFour);

		adapter.changeColour("white", adapter.getName(position));

		if(selectedDeviceOne == null)
		{
			selectedDeviceOne = adapter.getName(position);
			gridview.invalidateViews();
		}
		else if(selectedDeviceTwo == null)
		{
			selectedDeviceTwo = adapter.getName(position);
			gridview.invalidateViews();
		}
		else if(selectedDeviceThree == null)
		{
			selectedDeviceThree = adapter.getName(position);
			gridview.invalidateViews();
		}
		else if(selectedDeviceFour == null)
		{
			selectedDeviceFour = adapter.getName(position);
			gridview.invalidateViews();
		}
		
		else
		{
			selectedDeviceFour = selectedDeviceThree;
			selectedDeviceThree = selectedDeviceTwo;
			selectedDeviceTwo = selectedDeviceOne;
			selectedDeviceOne = adapter.getName(position);
			
			gridview.invalidateViews();
		}
	}

	public void sendMessage(View view) {
		Intent intent = new Intent(this, GraphingActivity.class);
		Bundle extras = new Bundle();

		extras.putString(DEVICE_ONE, selectedDeviceOne);
		extras.putString(DEVICE_TWO, selectedDeviceTwo);
		extras.putString(DEVICE_THREE, selectedDeviceThree);
		extras.putString(DEVICE_FOUR, selectedDeviceFour);

		intent.putExtras(extras);
		startActivity(intent);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
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
