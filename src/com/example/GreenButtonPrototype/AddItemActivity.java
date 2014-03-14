
package com.example.GreenButtonPrototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.GreenButtonPrototype.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class AddItemActivity extends Activity {
	private String m_Text = "";
	String selectedDevice = null;
	ImageAdapter adapter = null;
	GridView gridview;
	public final static String EXTRA_MESSAGE = "com.additemactivity.gbp.MESSAGE";
	public final static String EXTRA_MESSAGE_NAME = "com.additemactivity.gbp.NAME";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_item);

		Intent intent = getIntent();
		adapter = (ImageAdapter)intent.getSerializableExtra("adapter");
		//Send context instead?
		if(adapter == null)
			adapter = new ImageAdapter(this, true);
		
		gridview = (GridView) findViewById(R.id.gridView1);
		gridview.setAdapter(adapter);

		/*
		//TODO: Test, comment out, then bring back when it can do something
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Title");

		// Set up the input
		final EditText input = new EditText(this);
		// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
		input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		builder.setView(input);

		// Set up the buttons
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        m_Text = input.getText().toString();
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});

		builder.show();
		*/
		
		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				setDevicePicture(position);
			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
	
	public void addItem(View v)
	{//TODO: Send m_Text as well, so when the user taps on the pic it pops up
		Intent intent = new Intent(this, FullscreenActivity.class);
		Bundle extras = new Bundle();
		extras.putString(EXTRA_MESSAGE, selectedDevice);
		extras.putString(EXTRA_MESSAGE_NAME, m_Text);
		intent.putExtras(extras);
		
		startActivity(intent);
	}

	protected void setDevicePicture(int position)
	{
		adapter.changeColour("black", selectedDevice);
		selectedDevice = null;
		
		selectedDevice = adapter.getName(position);
		adapter.changeColour("white", selectedDevice);
		gridview.invalidateViews();
	}
}
