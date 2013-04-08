package com.fearsfx.cheatersParadise;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
		Thread thread = new Thread() {
			
			public void run() {
				try {
					HttpURLConnection conn =    null;
					DataOutputStream dos = null;
					DataInputStream inStream = null;    
					String exsistingFileName = "/sdcard/lqlq.jpg";
			
					// Is this the place are you doing something wrong.
					String lineEnd = "rn";
					String twoHyphens = "--";
					String boundary =  "*****";
			
					int bytesRead, bytesAvailable, bufferSize;
					byte[] buffer;
					int maxBufferSize = 1*1024*1024;
					String responseFromServer = "";
					String urlString = "http://46.238.28.50/uploadfile";
			
					try {
				        //------------------ CLIENT REQUEST 
				        Log.e("MediaPlayer","Inside second Method");
				        FileInputStream fileInputStream = new FileInputStream(new    File(exsistingFileName) );
			            // open a URL connection to the Servlet
			            URL url = new URL(urlString);
			            // Open a HTTP connection to the URL
			            conn = (HttpURLConnection) url.openConnection();
			            // Allow Inputs
			            conn.setDoInput(true);
			            // Allow Outputs
			            conn.setDoOutput(true);
			            // Don't use a cached copy.
			            conn.setUseCaches(false);
			            // Use a post method.
			            conn.setRequestMethod("POST");
			            conn.setRequestProperty("Connection", "Keep-Alive");
			            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			
			            dos = new DataOutputStream( conn.getOutputStream() );
			            dos.writeBytes(twoHyphens + boundary + lineEnd);
			            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
			                                + exsistingFileName + "\"" + lineEnd);
			            dos.writeBytes(lineEnd);
			            Log.e("MediaPlayer","Headers are written");
			
			            // create a buffer of maximum size
			            bytesAvailable = fileInputStream.available();
			            bufferSize = Math.min(bytesAvailable, maxBufferSize);
			            buffer = new byte[bufferSize];
			
			            // read file and write it into form...
			            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			
			            while (bytesRead > 0){
                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = fileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = fileInputStream.read(buffer, 0, bufferSize);                                                
			            }
			
			            // send multipart form data necesssary after file data...
			            dos.writeBytes(lineEnd);
			            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			
			            // close streams
			            Log.e("MediaPlayer","File is written");
			            fileInputStream.close();
			            dos.flush();
			            dos.close();
			
				    } catch (MalformedURLException ex) {
					    Log.e("MediaPlayer", "error1: " + ex.getMessage(), ex);
				    }
					catch (IOException ioe){
						Log.e("MediaPlayer", "error2: " + ioe.getMessage(), ioe);
					}
				    //------------------ read the SERVER RESPONSE
				    try {
				          inStream = new DataInputStream ( conn.getInputStream() );
				          String str;
				
				          while (( str = inStream.readLine()) != null)
				          {
				               Log.e("MediaPlayer","Server Response"+str);
				          }
				          inStream.close();
				    }
				    catch (IOException ioex){
				         Log.e("MediaPlayer", "error3: " + ioex.getMessage(), ioex);
				    }
				} catch(Exception e) {
					System.out.println("SomeE");
				}
			}
		};
		thread.start();			
	}
	
	public void post(View view) {
		Intent intent = new Intent(this, PickFile.class);
		startActivityForResult(intent, REQUEST_PICK_FILE);
	}
	
	public void request(View view) {
		Thread thread = new Thread() {
			
			public void run() {
				try {
					HttpURLConnection connection = null;
				    DataOutputStream outputStream = null;
				    DataInputStream inputStream = null;
			
				    String pathToOurFile = "/sdcard/lqlq.jpg";
				    String urlServer = "http://46.238.28.50/uploadfile";
				    String lineEnd = "\r\n";
				    String twoHyphens = "--";
				    String boundary =  "*****";
			
				    int bytesRead, bytesAvailable, bufferSize;
				    byte[] buffer;
				    int maxBufferSize = 1*1024*1024;
			
				    try
				    {
				        FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile) );
			
				        URL url = new URL(urlServer);
				        connection = (HttpURLConnection) url.openConnection();
			
				        // Allow Inputs & Outputs
				        connection.setDoInput(true);
				        connection.setDoOutput(false);
				        connection.setUseCaches(true);
			
				        // Enable POST method
				        connection.setRequestMethod("POST");
			
				        connection.setRequestProperty("Connection", "Keep-Alive");
				        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			
				        outputStream = new DataOutputStream( connection.getOutputStream() );
				        outputStream.writeBytes(twoHyphens + boundary + lineEnd);
				        outputStream.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + pathToOurFile +"\"" + lineEnd);
				        outputStream.writeBytes(lineEnd);
			
				        bytesAvailable = fileInputStream.available();
				        bufferSize = Math.min(bytesAvailable, maxBufferSize);
				        buffer = new byte[bufferSize];
			
				        // Read file
				        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			
				        while (bytesRead > 0)
				        {
				            outputStream.write(buffer, 0, bufferSize);
				            bytesAvailable = fileInputStream.available();
				            bufferSize = Math.min(bytesAvailable, maxBufferSize);
				            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
				        }
			
				        outputStream.writeBytes(lineEnd);
				        outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			
				        // Responses from the server (code and message)
				        int serverResponseCode = connection.getResponseCode();
				        String serverResponseMessage = connection.getResponseMessage();
			
				        fileInputStream.close();
				        outputStream.flush();
				        outputStream.close();
			
				        Log.i("ODPOWIEDZ", serverResponseMessage);
				        System.out.println("JA JA");
				    }
				    catch (Exception ex)
				    {
				        Log.i("WYJATEK", ex.getMessage(), ex);
				    	System.out.println("NO NO NO");
				    }
				} catch(Exception e) {
					
				}
			}
		};
		thread.start();
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			switch(requestCode) {
			case REQUEST_PICK_FILE:
				if(data.hasExtra(PickFile.EXTRA_FILE_PATH)) {
					// Get the file path
					File f = new File(data.getStringExtra(PickFile.EXTRA_FILE_PATH));

					Toast.makeText(this, f.getName()+"\n"+f.getPath(), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
}
