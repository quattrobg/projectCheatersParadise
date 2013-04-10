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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ImageView bg = (ImageView) findViewById(R.id.background_main);
		bg.setBackgroundResource(R.drawable.background);
	}

	public void browse(View view) {
		Toast.makeText(MainActivity.this, "Not yet implemented", Toast.LENGTH_SHORT).show();
	}

	public void post(View view) {
		Intent intent = new Intent(this, Post.class);
		startActivity(intent);
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

}
