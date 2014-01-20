package com.example.testinglayouts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Integer> mThumbIds = new ArrayList<Integer>();

	// private String filePath;// = "/graphs/";

	public ImageAdapter(Context c) {
		mContext = c;

		mThumbIds.add(R.drawable.add_item);
		mThumbIds.add(R.drawable.house);

		File folder = mContext.getFilesDir();
		// filePath = folder.toString() + filePath;
		// File directory = new File(filePath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			mThumbIds.add(mContext.getResources()
					.getIdentifier(readFromFile(listOfFiles[i].getName()), "drawable",
							mContext.getPackageName()));
		}

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
	}

	private String readFromFile(String fileName) {
		String ret = "";
		// fileName = filePath + fileName;

		try {
			InputStream inputStream = mContext.openFileInput(fileName);

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";
				StringBuilder stringBuilder = new StringBuilder();

				while ((receiveString = bufferedReader.readLine()) != null) {
					stringBuilder.append(receiveString + '\n');
					if (receiveString == "|") {
						ret = stringBuilder.toString();
						inputStream.close();
						return ret;
					}
				}
				ret = stringBuilder.toString();
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
			Log.i("File not found", e.toString());
		} catch (IOException e) {
			Log.i("Can not read file:", e.toString());
		}
		return ret;
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

	public void Save() {
		for (int i = 0; i < mThumbIds.size(); i++) {
			// int name = mThumbIds.get(i);
			String name = mContext.getResources().getResourceEntryName(
					mThumbIds.get(i));
			// name = filePath + name;

			// String temp = getName(Integer.parseInt(name));
			String graphName = name + "|";
			// String graphName = "TEMP";
			if (fileExistance(name) == false) {
				try {
					FileOutputStream outputStream = mContext.openFileOutput(
							name, mContext.MODE_PRIVATE);
					outputStream.write(graphName.getBytes());
					outputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean fileExistance(String fname) {
		try {
			File file = mContext.getFileStreamPath(fname);
			return file.exists();
		} catch (Exception e) {
			return false;
		}
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
		temp = mContext.getResources().getResourceEntryName(
				mThumbIds.get(position));
		return temp;
	}

	public int getPosition(String resName) {
		if (resName == null)
			return -1;
		// TODO: Does this work? It looks like it just gets the ID
		return mContext.getResources().getIdentifier(resName, "drawable",
				mContext.getPackageName());
	}

	public int getID(String resName) {
		return mContext.getResources().getIdentifier(resName, "drawable",
				mContext.getPackageName());
	}

	public void changeColour(String colour, String resName) {
		if (resName == null)
			return;

		if (resName.contains("_white"))
			resName = resName.replace("_white", "");

		if (resName.contains("_red"))
			resName = resName.replace("_red", "");

		int idImage;

		if (colour == "black") {
			idImage = mContext.getResources().getIdentifier(resName,
					"drawable", mContext.getPackageName());
			// TODO: Add a if(tempString.contains(_white)) tempString -= _white
			// TODO: Fix the colours for the images
		}

		else {
			idImage = mContext.getResources().getIdentifier(
					resName + "_" + colour, "drawable",
					mContext.getPackageName());
		}

		// int index = mThumbIds.indexOf(idImage);
		for (int i = 0; i < mThumbIds.size(); i++) {
			String temp = mContext.getResources().getResourceEntryName(
					mThumbIds.get(i));
			if (temp.contains(resName)) {
				mThumbIds.set(i, idImage);
			}
		}
		// mThumbIds.set(index, idImage);
	}

	public ArrayList<Integer> getArray() {
		return mThumbIds;
	}

	public void addItem(String itemName) {
		// TODO: Add item name
		// TODO: Add second array, the one that will display all the images. The
		// other holds them.
		// mThumbIds[mThumbIds.length+1];
		// mThumbIds.add(null);
		Integer idImage = mContext.getResources().getIdentifier(itemName,
				"drawable", mContext.getPackageName());

		mThumbIds.add(idImage);

		// Save();
	}

	// house last or first, always. First is easier
	// references to our images
}