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

import android.app.ListActivity;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getInfo();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browse);
//		for(int i = 0; i < 15; i++) listTopics.add("asd");
		setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, listTopics));
	}
	
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String selection = listFilenames.get(position);
		Toast.makeText(this, selection, Toast.LENGTH_LONG).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browse, menu);
		return true;
	}
	
	private void getInfo() {
		
		Thread thread = new Thread() {
			public void run() {
				try {
					String res = null;
					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("http://46.238.28.50/down2.php");

					// http post
					try {
						HttpResponse response = httpclient.execute(httppost);
						res = inputStreamToString(response.getEntity().getContent()).toString();
					} catch (Exception e) {
						System.out.println("Error : " + e.getMessage());
					}
					
//					System.out.println("Res : " + res);
					String resArr[] = res.split(",");
//					System.out.println("ResArr : " + resArr.length);
					for(int i = 0; i < resArr.length; i++) {
						String sid[];
						int id;
						String stopic[], topic;
						String sname[], name;
						switch(i%3) {
						case 0 : 
								sid = resArr[i].split(":");
								id = Integer.parseInt(sid[1]);
//								System.out.print("ID : " + id);
							break;
						case 1 :
								stopic = resArr[i].split(":");
								stopic = stopic[1].split("\"");
								topic = stopic[1];
//								System.out.print("  Topic : " + topic);
								listTopics.add(topic);
							break;
						case 2 :
								sname = resArr[i].split(":");
								sname = sname[1].split("\"");
								name  = sname[1];
								listFilenames.add(name);
//								System.out.println("  Name : " + name);
							break;
						}
					}
				} catch (Exception e) {
					Log.e("Database info Exception : ", e.getMessage());
				}
			}
		};
		thread.start();
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
