package com.example.testinglayouts;

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
    }

    public int getCount() {
        return mThumbIds.length;
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
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
    
    public void changeColour(String colour, String resName)
    {
    	if(resName == "")
    		return;
    	
    	int idImage = mContext.getResources().getIdentifier(resName + "_" + colour, "drawable", mContext.getPackageName());
    	String temp = null;
    	
    	for(int i = 0; i < mThumbIds.length; i++)
    	{
    		temp = mContext.getResources().getResourceEntryName(mThumbIds[i]);
    		if(temp.contains(resName))
    		{
    			mThumbIds[i] = idImage;
    		}
    	}
    }

    //house last
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.air_conditioner, R.drawable.blender,
            R.drawable.car_charger, R.drawable.computer,
            R.drawable.console, R.drawable.desk_fan,
            R.drawable.dishwasher, R.drawable.exhaust_fan,
            R.drawable.fridge, R.drawable.grill,
            R.drawable.hair_dryer, R.drawable.iron,
            R.drawable.microwave, R.drawable.oven,
            R.drawable.phone, R.drawable.printer,
            R.drawable.television, R.drawable.toaster,
            R.drawable.washer, R.drawable.house
    };
}