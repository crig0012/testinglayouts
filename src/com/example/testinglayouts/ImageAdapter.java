package com.example.testinglayouts;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	public ImageAdapter(Context c) {
		mContext = c;

		mThumbIds.add(R.drawable.air_conditioner);
		mThumbIds.add(R.drawable.blender);
		mThumbIds.add(R.drawable.car_charger);
		mThumbIds.add(R.drawable.computer);
		mThumbIds.add(R.drawable.console);
		mThumbIds.add(R.drawable.desk_fan);
		mThumbIds.add(R.drawable.dishwasher);
		mThumbIds.add(R.drawable.exhaust_fan);
		mThumbIds.add(R.drawable.fridge);
		mThumbIds.add(R.drawable.grill);
		mThumbIds.add(R.drawable.hair_dryer);
		mThumbIds.add(R.drawable.iron);
		mThumbIds.add(R.drawable.microwave);
		mThumbIds.add(R.drawable.oven);
		mThumbIds.add(R.drawable.phone);
		mThumbIds.add(R.drawable.printer);
		mThumbIds.add(R.drawable.television);
		mThumbIds.add(R.drawable.toaster);
		mThumbIds.add(R.drawable.washer);
		mThumbIds.add(R.drawable.house);
	}

	public int getCount() {
		return mThumbIds.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new ImageView for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(mThumbIds.get(position));
		return imageView;
	}

	public String getName(int position) {
		String temp = null;
		temp = mContext.getResources().getResourceEntryName(mThumbIds.get(position));
		return temp;
	}
	
	public int getPosition(String resName)
	{
		if(resName == null)
			return -1;
		//TODO: Does this work? It looks like it just gets the ID
		return mContext.getResources().getIdentifier(resName,
				"drawable", mContext.getPackageName());
	}

	public void changeColour(String colour, String resName) {
		if (resName == null)
			return;
		
		if(resName.contains("_white")) 
			resName = resName.replace("_white", "");
			
		if(resName.contains("_red")) 
			resName = resName.replace("_red", "");
		
		int idImage;

		if (colour == "black") {
			idImage = mContext.getResources().getIdentifier(resName,
					"drawable", mContext.getPackageName());
			//TODO: Add a if(tempString.contains(_white)) tempString -= _white
			//TODO: Fix the colours for the images
		}

		else {
			idImage = mContext.getResources().getIdentifier(
					resName + "_" + colour, "drawable",
					mContext.getPackageName());
		}
		
		//int index = mThumbIds.indexOf(idImage);
		for (int i = 0; i < mThumbIds.size(); i++) {
			String temp = mContext.getResources().getResourceEntryName(mThumbIds.get(i));
			if (temp.contains(resName)) {
				mThumbIds.set(i, idImage);
			}
		}
		//mThumbIds.set(index, idImage);
	}

	public void addItem(String itemName) {
		// TODO: add to the array
		// mThumbIds[mThumbIds.length+1];
	}

	// house last or first, always. First is easier
	// references to our images
	private ArrayList<Integer> mThumbIds = new ArrayList<Integer>();
}