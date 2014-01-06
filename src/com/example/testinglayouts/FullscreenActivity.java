package com.example.testinglayouts;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
	public final static String EXTRA_MESSAGE = "com.fullscreenactivity.gbp.MESSAGE";
	
	public final static String DEVICE_ONE = "com.fullscreenactivity.gbp.DEVICE_ONE";
	public final static String DEVICE_TWO= "com.fullscreenactivity.gbp.DEVICE_TWO";
	public final static String DEVICE_THREE = "com.fullscreenactivity.gbp.DEVICE_THREE";
	public final static String DEVICE_FOUR = "com.fullscreenactivity.gbp.DEVICE_FOUR";

	String selectedDeviceOne = "";
	String selectedDeviceTwo = "";
	String selectedDeviceThree = "";
	String selectedDeviceFour = "";
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
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
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
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
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
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        
        setContentView(R.layout.activity_fullscreen);

        GridView gridview = (GridView) findViewById(R.id.gridView1);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() 
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
            {
            	switch(position)
            	{
            	case 0:
            		Toast.makeText(FullscreenActivity.this, "Blender", Toast.LENGTH_SHORT).show();
            		break;
            		
            	case 1:
            		Toast.makeText(FullscreenActivity.this, "Cordless", Toast.LENGTH_SHORT).show();
            		break;
            		
            	case 2:
            		Toast.makeText(FullscreenActivity.this, "Dishwasher", Toast.LENGTH_SHORT).show();
            		break;
            		
            	case 3:
            		Toast.makeText(FullscreenActivity.this, "Dryer", Toast.LENGTH_SHORT).show();
            		break;
            	
            	case 4:
            		Toast.makeText(FullscreenActivity.this, "Exhaust Fan", Toast.LENGTH_SHORT).show();
            		break;
            	
            	case 5:
            		Toast.makeText(FullscreenActivity.this, "Kettle", Toast.LENGTH_SHORT).show();
            		break;
            	
            	case 6:
            		Toast.makeText(FullscreenActivity.this, "Microwave", Toast.LENGTH_SHORT).show();
            		break;
            	
            	case 7:
            		Toast.makeText(FullscreenActivity.this, "Oven", Toast.LENGTH_SHORT).show();
            		break;
            	
            	case 8:
            		Toast.makeText(FullscreenActivity.this, "Refrigerator", Toast.LENGTH_SHORT).show();
            		break;
            	
            	case 9:
            		Toast.makeText(FullscreenActivity.this, "Toaster", Toast.LENGTH_SHORT).show();
            		break;
            	
            	case 10:
            		Toast.makeText(FullscreenActivity.this, "Television", Toast.LENGTH_SHORT).show();
            		break;
            	
            	case 11:
            		Toast.makeText(FullscreenActivity.this, "Washer", Toast.LENGTH_SHORT).show();
            		break;
            		
            	case 12:
            		Toast.makeText(FullscreenActivity.this, "Other Blender", Toast.LENGTH_SHORT).show();
            		break;
            		
            		default:
            			Toast.makeText(FullscreenActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            			break;
            	}
                //Toast.makeText(FullscreenActivity.this, "" + position, Toast.LENGTH_SHORT).show();                
            }
        });
        
        Intent intent = getIntent();
		String message = intent.getStringExtra(FullscreenActivity.EXTRA_MESSAGE);
		
		if(message != "Good usage")
		{
			
		}
    }

    private void setSelectedDevice(int position)
    {
    	switch(position)
    	{
    	case 0:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "BLENDER";
    		break;
    	case 1:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "CORDLESS";
    		break;
    	case 2:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "DISHWASHER";
    		break;
    	case 3:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "DRYER";
    		break;
    	case 4:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "EXHAUST_FAN";
    		break;
    	case 5:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "KETTLE";
    		break;
    	case 6:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "MICROWAVE";
    		break;
    	case 7:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "OVEN";
    		break;
    	case 8:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "REFRIGERATOR";
    		break;
    	case 9:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "TOASTER";
    		break;
    	case 10:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "TELEVISION";
    		break;
    	case 11:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "WASHER";
    		break;
    	case 12:
    		selectedDeviceFour = selectedDeviceThree;
    		selectedDeviceThree = selectedDeviceTwo;
    		selectedDeviceTwo = selectedDeviceOne;
    		selectedDeviceOne = "OTHER_BLENDER";
    		break;
    	}
    }
    public void sendMessage(View view) 
    {
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
