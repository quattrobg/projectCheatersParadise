package com.fearsfx.cheatersParadise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Browse extends ListActivity {

	List<String> listTopics = new ArrayList<String>();
	List<String> listFilenames = new ArrayList<String>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			getInfo();
		} catch (Exception e) {
			Log.e("log_tag", e.getMessage());
		}
		setContentView(R.layout.activity_browse);
		setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listTopics));
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, final int position, long id) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Choose operation:");

		alertDialogBuilder
			.setCancelable(true)
			.setPositiveButton("Open",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					Intent intent = new Intent(Browse.this, Previewer.class);
					intent.putExtra("EXTRA_FILE_NAME", listFilenames.get(position));
					startActivity(intent);
				}
			  })
			.setNegativeButton("Download",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					Thread thread = new Thread() {
						public void run() {
							Downloader dw = new Downloader();
							try {
								dw.download(listFilenames.get(position));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					thread.start();
					Toast.makeText(Browse.this, "Download Completed..", Toast.LENGTH_SHORT).show();
				}
			});

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse, menu);
		return true;
	}
	
	private void getInfo() throws Exception {
		
		Thread thread = new Thread() {
			public void run() {
				try {
					String res = null;
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("http://46.238.28.50/downloadfile");

					try {
						HttpResponse response = httpclient.execute(httppost);
						res = inputStreamToString(response.getEntity().getContent()).toString();
					} catch (Exception e) {
						System.out.println("Error : " + e.getMessage());
					}
					
					String resArr[] = res.split(",");
					for(int i = 0; i < resArr.length; i++) {
						String stopic[], topic;
						String sname[], name;
						switch(i%3) {
						case 0 : 
							break;
						case 1 :
								stopic = resArr[i].split(":");
								stopic = stopic[1].split("\"");
								topic = stopic[1];
								listTopics.add(topic);
							break;
						case 2 :
								sname = resArr[i].split(":");
								sname = sname[1].split("\"");
								name  = sname[1];
								listFilenames.add(name);
							break;
						}
					}
				} catch (Exception e) {
					Log.e("Database info Exception : ", e.getMessage());
				}
			}
		};
		thread.start();
		Thread.sleep(200);
	}

	private static StringBuilder inputStreamToString(InputStream is) {
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
}
