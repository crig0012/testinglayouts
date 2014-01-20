package com.example.testinglayouts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Integer> mThumbIds = new ArrayList<Integer>();

	private String filePath = "/GreenButtonPrototypeGraphs/";

	public ImageAdapter(Context c, String pathFile, boolean addingItem) {
		mContext = c;

		mThumbIds.add(R.drawable.add_item);
		mThumbIds.add(R.drawable.house);
		File sdcard = Environment.getExternalStorageDirectory();

		// Get the text file
		File file = new File(sdcard, filePath);
		// File directory = new File(pathFile);

		if (addingItem == false) {
			if (file.isDirectory() == false)
				file.mkdirs();
			else {
				File[] listOfFiles = file.listFiles(); // Maybe list()?
				if (listOfFiles != null)
					for (int i = 0; i < listOfFiles.length; i++) {
						mThumbIds.add(mContext.getResources().getIdentifier(
								readFromFile(listOfFiles[i].getName()),
								"drawable", mContext.getPackageName()));
					}
			}
		} else if (addingItem == true) {
			// TODO: Use mText from additem as the name, and do a check there to
			// see if it exists already
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
	}

	boolean deleteDirectory(File path) {
		if (path.exists()) {
			if (path.isDirectory()) {
				File[] files = path.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteDirectory(files[i]);
				}
			}
			return path.delete();
		}

		return false;
	}

	private String readFromFile(String fileName) {
		final File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + filePath + fileName);
		// TODO: Add the boolean after the first pipe. Either have it be 1 or 0
		// or true or false, and read it here the same way. Make firstpart here,
		// save it, then when the seoncd pipe is hit, set the global bool, and
		// return the name. Maybe return an array? Or just call changeColour
		// here

		// Read text from file
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			// TODO: Change this to read char by char
			while ((line = br.readLine()) != null) {
				text.append(line);
				if (line.contains("|")) {
					int index = (text.toString()).indexOf("|");
					if (index != -1) {
						String firstPart = text.substring(0, index);
						return firstPart;// text.toString();
					}
				}
			}

			br.close();
		} catch (IOException e) {
			// You'll need to add proper error handling here
		}
		return text.toString();
		/*
		 * String ret = ""; // fileName = filePath + fileName;
		 * 
		 * try { InputStream inputStream = mContext.openFileInput(fileName);
		 * 
		 * if (inputStream != null) { InputStreamReader inputStreamReader = new
		 * InputStreamReader( inputStream); BufferedReader bufferedReader = new
		 * BufferedReader( inputStreamReader); String receiveString = "";
		 * StringBuilder stringBuilder = new StringBuilder();
		 * 
		 * while ((receiveString = bufferedReader.readLine()) != null) {
		 * stringBuilder.append(receiveString + '\n'); if (receiveString == "|")
		 * { ret = stringBuilder.toString(); inputStream.close(); return ret; }
		 * } ret = stringBuilder.toString(); inputStream.close(); } } catch
		 * (FileNotFoundException e) { Log.i("File not found", e.toString()); }
		 * catch (IOException e) { Log.i("Can not read file:", e.toString()); }
		 * return ret;
		 */
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
			FileOutputStream fos = null;
			try {
				final File dir = new File(Environment
						.getExternalStorageDirectory().getAbsolutePath()
						+ filePath);

				if (!dir.exists()) {
					dir.mkdirs();
				}

				final File myFile = new File(dir, mThumbIds.get(i) + ".txt");
				String body = mContext.getResources().getResourceEntryName(
						mThumbIds.get(i))
						+ "|";
				if (body.contains("add_item") || body.contains("house"))
					continue;// Maybe for(int i = 2...)?

				if (!myFile.exists()) {
					myFile.createNewFile();
				}

				fos = new FileOutputStream(myFile);

				fos.write(body.getBytes());
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * // int name = mThumbIds.get(i); String name =
			 * mContext.getResources().getResourceEntryName( mThumbIds.get(i));
			 * //name = filePath + name;
			 * 
			 * // String temp = getName(Integer.parseInt(name)); String
			 * graphName = name + "|"; // String graphName = "TEMP"; if
			 * (fileExistance(name) == false) { try { FileOutputStream
			 * outputStream = mContext.openFileOutput( name,
			 * mContext.MODE_PRIVATE); outputStream.write(graphName.getBytes());
			 * outputStream.close(); } catch (Exception e) {
			 * e.printStackTrace(); } }
			 */
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

	public void addItem(String resName) {
		// TODO: Add item name
		// TODO: Add second array, the one that will display all the images. The
		// other holds them.
		// mThumbIds[mThumbIds.length+1];
		// mThumbIds.add(null);
		Integer idImage = mContext.getResources().getIdentifier(resName,
				"drawable", mContext.getPackageName());

		mThumbIds.add(idImage);

		Save();
	}

	// house last or first, always. First is easier
	// references to our images
}