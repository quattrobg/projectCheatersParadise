package com.fearsfx.cheatersParadise;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private static final int REQUEST_PICK_FILE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView bg = (ImageView) findViewById(R.id.background);
		bg.setBackgroundResource(R.drawable.background);
	}

	public void browse(View view) {
		Toast.makeText(MainActivity.this, "Not yet implemented", Toast.LENGTH_SHORT).show();
	}

	public void post(View view) {
		Intent intent = new Intent(this, PickFile.class);
		startActivityForResult(intent, REQUEST_PICK_FILE);
	}

	public void request(View view) {
		Toast.makeText(MainActivity.this, "Not yet implemented", Toast.LENGTH_SHORT).show();
	}

	public void about(View view) {
		Intent intent = new Intent(this, About.class);
		startActivity(intent);
	}

	public void die(View view) {
		finish();
		System.exit(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			final Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_PICK_FILE:
				Thread thread = new Thread() {

					public void run() {
						try {
							if (data.hasExtra(PickFile.EXTRA_FILE_PATH)) {
								// Get the file path
								File f = new File(
										data.getStringExtra(PickFile.EXTRA_FILE_PATH));

								Uploader up = new Uploader();
								up.upload(f.getPath());
							}
						} catch (Exception e) {
							Log.e("Upload file Exception : ", e.getMessage());
						}
					}
				};
				thread.start();
			}
		}
	}

}
