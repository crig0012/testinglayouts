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

	Boolean isAdding = false;

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

	Toast toast = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fullscreen);

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		
		if (extras != null) {
			temp = extras.getStringArray(GraphingActivity.EXTRA_MESSAGE_DATA);
			String tempStr = extras.getString(AddItemActivity.EXTRA_MESSAGE);
			
			if(tempStr != null)
			{
				adapter.addItem(tempStr);
				adapter.Save();
			}
			if (temp != null) {
				for (int i = 0; i < temp.length; i++) {
					if (temp[i] != "EMPTY")
						adapter.changeColour("red", temp[i]);
				}
			}
		}		

		gridview = (GridView) findViewById(R.id.gridView1);
		gridview.setAdapter(adapter);
		
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

	// TODO: Make a refresh function, that will organize the pictures in the
	// order selected
	// TODO: Make it so that if you don't select any items, the house is sent
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
		// TODO: Make the selected items into an array, this will simplify SO
		// much

		String stringToCheck = adapter.getName(position);

		adapter.changeColour("black", "add_item_white");
		isAdding = false;

		if (stringToCheck.contains("add_item")) {
			adapter.changeColour("black", selectedDeviceOne);
			selectedDeviceOne = null;
			adapter.changeColour("black", selectedDeviceTwo);
			selectedDeviceTwo = null;
			adapter.changeColour("black", selectedDeviceThree);
			selectedDeviceThree = null;
			adapter.changeColour("black", selectedDeviceFour);
			selectedDeviceFour = null;

			adapter.changeColour("white", stringToCheck);
			isAdding = true;
			// selectedDeviceOne = adapter.getName(position);
			gridview.invalidateViews();
		}

		if (stringToCheck.equals(selectedDeviceOne)) {
			adapter.changeColour("black", selectedDeviceOne);
			selectedDeviceOne = null;
			gridview.invalidateViews();
			return;
		}

		if (stringToCheck.equals(selectedDeviceTwo)) {
			adapter.changeColour("black", selectedDeviceTwo);
			selectedDeviceTwo = null;
			gridview.invalidateViews();
			return;
		}

		if (stringToCheck.equals(selectedDeviceThree)) {
			adapter.changeColour("black", selectedDeviceThree);
			selectedDeviceThree = null;
			gridview.invalidateViews();
			return;
		}

		if (stringToCheck.equals(selectedDeviceFour)) {
			adapter.changeColour("black", selectedDeviceFour);
			selectedDeviceFour = null;
			gridview.invalidateViews();
			return;
		}

		String temp = null;

		if (adapter.getName(position).contains("_white"))
			temp = adapter.getName(position).replace("_white", "");
		else if (adapter.getName(position).contains("_red"))
			temp = adapter.getName(position).replace("_red", "");
		else
			temp = adapter.getName(position);

		
		if(toast != null)
			if(toast.getView().isShown() == true)
				toast.cancel();
		
		toast = Toast.makeText(FullscreenActivity.this, temp, Toast.LENGTH_SHORT);
		toast.show();

		if (selectedDeviceFour != null)
			adapter.changeColour("black", selectedDeviceFour);

		adapter.changeColour("white", adapter.getName(position));

		if (selectedDeviceOne == null) {
			selectedDeviceOne = adapter.getName(position);
			gridview.invalidateViews();
		} else if (selectedDeviceTwo == null) {
			selectedDeviceTwo = adapter.getName(position);
			gridview.invalidateViews();
		} else if (selectedDeviceThree == null) {
			selectedDeviceThree = adapter.getName(position);
			gridview.invalidateViews();
		} else if (selectedDeviceFour == null) {
			selectedDeviceFour = adapter.getName(position);
			gridview.invalidateViews();
		}

		else {
			selectedDeviceFour = selectedDeviceThree;
			selectedDeviceThree = selectedDeviceTwo;
			selectedDeviceTwo = selectedDeviceOne;
			selectedDeviceOne = adapter.getName(position);

			gridview.invalidateViews();
		}
	}

	public void sendMessage(View view) {
		Intent intent;
		Bundle extras = new Bundle();

		if (isAdding == true) {
			intent = new Intent(this, AddItemActivity.class);
		}

		else {
			intent = new Intent(this, GraphingActivity.class);

			extras.putString(DEVICE_ONE, selectedDeviceOne);
			extras.putString(DEVICE_TWO, selectedDeviceTwo);
			extras.putString(DEVICE_THREE, selectedDeviceThree);
			extras.putString(DEVICE_FOUR, selectedDeviceFour);

			intent.putExtras(extras);
		}

		startActivity(intent);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
