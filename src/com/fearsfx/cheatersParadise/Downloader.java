package com.fearsfx.cheatersParadise;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;

public class Downloader {
	
	public void download(String filename) throws Exception {
		URL url = new URL("http://46.238.28.50/uploads/" + filename);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoOutput(true);
		urlConnection.connect();

		File folder = new File(Environment.getExternalStorageDirectory() + "/CheatersParadise");
		if( !folder.exists() ) {
			folder.mkdir();
		}
		File file = new File(folder, filename);

		FileOutputStream fileOutput = new FileOutputStream(file);

		InputStream inputStream = urlConnection.getInputStream();

		byte[] buffer = new byte[1024];
		int bufferLength = 0;

		while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
			fileOutput.write(buffer, 0, bufferLength);
		}
	}
	
}
