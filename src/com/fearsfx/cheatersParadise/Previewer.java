package com.fearsfx.cheatersParadise;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class Previewer extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previewer);
		
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);

		Bundle extras = getIntent().getExtras();
		String filename = extras.getString("EXTRA_FILE_NAME");
		webView.loadUrl("http://46.238.28.50/uploads/" + filename);
	}

	@Override
	public void onDestroy() {
		finish();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_previewer, menu);
		return true;
	}

}
