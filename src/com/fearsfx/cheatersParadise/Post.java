package com.fearsfx.cheatersParadise;

import java.io.File;

import org.apache.http.entity.mime.content.StringBody;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Post extends Activity {
	
	private static final int REQUEST_PICK_FILE = 1;
	private Intent keepIt = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		ImageView bg = (ImageView) findViewById(R.id.background_post);
		bg.setBackgroundResource(R.drawable.background);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.post, menu);
		return true;
	}
	
	public void pickFile(View view) {
		Intent intent = new Intent(this, PickFile.class);
		startActivityForResult(intent, REQUEST_PICK_FILE);
	}
	
	@Override
	public void onDestroy() {
		finish();
		super.onDestroy();
	}

	boolean rightFormat = false;
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_PICK_FILE:

				if (data.hasExtra(PickFile.EXTRA_FILE_PATH)) {
					File f = new File(data.getStringExtra(PickFile.EXTRA_FILE_PATH));

					String imgOnly = f.getPath().substring(f.getPath().lastIndexOf('.'));
					System.out.println(imgOnly);
					
					if (imgOnly.equals(".jpg")
							|| imgOnly.equals(".png")
							|| imgOnly.equals(".bmp")
							|| imgOnly.equals(".gif")
							|| imgOnly.equals(".jpeg")
							|| imgOnly.equals(".txt")
							|| imgOnly.equals(".doc")
							|| imgOnly.equals(".docx")) {
						
						rightFormat = true;
						TextView tv = (TextView) findViewById(R.id.fileName);
						tv.setText(f.getName());
						keepIt = data;
					}else{
						Toast.makeText(Post.this, "File not image or text", Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
		}
	}

	public void submit(View view) {
		final EditText et = (EditText) findViewById(R.id.topic);
		
		if( keepIt != null && !et.getText().toString().equals("")){
			Thread thread = new Thread() {
	
				public void run() {
					try {
						if (keepIt.hasExtra(PickFile.EXTRA_FILE_PATH)) {
							File f = new File(keepIt.getStringExtra(PickFile.EXTRA_FILE_PATH));
							
							Uploader up = new Uploader();
							up.upload(f.getPath(), new StringBody(et.getText().toString()));
						}
					} catch (Exception e) {
						Log.e("Upload file Exception : ", e.getMessage());
					}
				}
			};
			thread.start();
			Toast.makeText(Post.this, "Upload Completed..", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			Toast.makeText(Post.this, "Topic must be entered and only img/text file could be chosen.. ", Toast.LENGTH_SHORT).show();
		}
	}
	
}
