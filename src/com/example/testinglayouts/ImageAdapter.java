package com.example.testinglayouts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

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

	public ImageAdapter(Context c, boolean addingItem) {
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

	private int countCharsBuffer(File f) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(f)));
		int charCount = 0;
		char[] cbuf = new char[1024];
		int read = 0;
		while ((read = reader.read(cbuf)) > -1) {
			charCount += read;
		}
		reader.close();
		return charCount;
	}

	public String[] getMyGraph(String forMe) {// TODO: Bar vs line
												// TODO: GET THIS TO WORK
		final File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + filePath + getID(forMe) + ".txt");
		int howBig;
		try {
			howBig = countCharsBuffer(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			howBig = 1000;
		}
		String[] toRet = new String[howBig];
		StringBuilder text = new StringBuilder();

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line;
			// TODO: Change this to read char by char
			while ((line = br.readLine()) != null) {
				text.append(line);
				if (line.contains("|")) {
					StringTokenizer tokens = new StringTokenizer(
							text.toString(), "|");
					for (int i = 0; i < tokens.countTokens(); i++) {
						toRet[i] = tokens.nextToken();
					}
				}
			}

			br.close();
		} catch (IOException e) {
			toRet[0] = forMe + "|";
			toRet[1] = "false|";
			toRet[2] = "T20140121060300|";
			toRet[630] = "T20140121180300|";
			Random rand = new Random();

			for(int i = 3; i < 999; i++)
			{
				if(i >= 230 && i <= 630)
					continue;
				if(i == 630)
					continue;
				if(i >= 803)
					continue;
				
				int randomNum = rand.nextInt((13 - 11) + 1) + 11;
				toRet[i] = Integer.toString(randomNum);//Double.toString(Math.abs(Math.sin(Math .random() * i))*10) + "|";
			}
				//toRet"false|T20140121060300|0001|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|T20140121180300|0001|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008";
		}
		return toRet;
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
			if (mThumbIds.get(i) == 0)
				continue;
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

				if (body.contains("console"))
					body += "false|T20140121062200|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020";
				else if (body.contains("desk_fan"))
					body += "false|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|T20140121172200|0001|0009|0012|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|";
				else if (body.contains("toaster"))
					body += "false|T20140121060100|0014|0018|0018|T20140121060500|0014|0018|0018|T20140121172200|0014|0018|0018T20140121232200|0014|0018|0018";
				else if (body.contains("television"))
					body += "true|T20140121070000|0001|0009|0012|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|T20140121170000|0001|0009|0012|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0001|0009|0012|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0001|0009|0012|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0001|0009|0012|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015|0015";
				else if (body.contains("oven"))
					body += "true|T20140121170100|0014|0018|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0002|0002|0002|0002|0002|0022|0022|0022|0022|0022|0002|0012|0012|0013|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022|0022";
				else if (body.contains("iron"))
					body += "false|T20140121062300|0007|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020";
				else if (body.contains("hair_dryer"))
					body += "false|T20140121070100|0003|0023|0023";
				else if (body.contains("grill"))
					body += "false|T20140121060000|0001|0024|0024|0024|0024|0024|0024|0024|0024|0024|0020|0001|T20140121211100|0001|0024|0024|0024|0024|0024|0024|0024|0024|0024|0020|0001";
				else if (body.contains("dishwasher"))
					body += "false|T20140121083100|0009|0025|0025|0013|0013|0013|0013|0013|0013|0013|0023|0025|0025|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0013|0035|0035|";
				else if (body.contains("computer"))
					body += "false|T20140121060300|0001|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|T20140121180300|0001|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0008|0011|0008|0008|0008|0008|0008";
				else// random numbers
					body += "false|T20140121062200|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020|0020";
				fos.write(body.getBytes());
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void Save(int[] graphNumbers) {
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
				if (myFile.toString().contains("add_item")
						|| myFile.toString().contains("house"))
					continue;

				if (!myFile.exists()) {
					myFile.createNewFile();
				}

				FileWriter fw = new FileWriter(myFile, true);
				for (int j = 0; j < graphNumbers.length; j++) {
					try {
						fw.write(graphNumbers[i] + "|");
					} catch (IOException ioe) {
						System.err.println("IOException: " + ioe.getMessage());
					}
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
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
			if (mThumbIds.get(i) == 0)
				continue;
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
		Integer idImage = mContext.getResources().getIdentifier(resName,
				"drawable", mContext.getPackageName());

		// int[] randomNumbers
		mThumbIds.add(idImage);

		Save();
	}
	// house last or first, always. First is easier
	// references to our images
}