package com.fearsfx.cheatersParadise;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class About extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        
        TextView tv = (TextView) findViewById(R.id.about);
        tv.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
	protected void onDestroy() {
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
