package com.fearsfx.cheatersParadise;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
    	finish();
		super.onDestroy();
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_about, menu);
        return true;
    }
    
}
