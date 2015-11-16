package com.bnavarro.pick5football.async;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

public class RetrieveMatchDataAsyncService extends AsyncTask<Void, Integer, Boolean> {

	private File matchFile;
	private String urlString;
	private AlertDialog alertDialog;
	private Exception exceptionCaught;

    /**
     *
     * @param matchFile
     * @param urlString
     * @param context
     */
	public RetrieveMatchDataAsyncService (File matchFile, String urlString, Context context) {
		this.urlString = urlString;
		this.matchFile=matchFile;

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Downloading");
		builder.setMessage("Download Complete");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog = builder.create();
	}

    /**
     *
     * @param params
     * @return
     */
	@Override
	protected Boolean doInBackground(Void... params){
		InputStream inputStream = null;
		HttpURLConnection connection;
		FileOutputStream outputStream;
		try {
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();

			// download the file
			inputStream = connection.getInputStream();
			outputStream = new FileOutputStream(matchFile);
			byte data[] = new byte[4096];
			long total = 0;
			int count;

			while ((count = inputStream.read(data)) != -1) {
				total += count;
				publishProgress((int) (total * 100));
				outputStream.write(data, 0, count);
			}

		}catch (MalformedURLException e){
			Log.e("Downloading File", e.getMessage(), e);
			exceptionCaught =  e;
		}catch (IOException e){
			Log.e("Downloading File", e.getMessage(), e);
			exceptionCaught =  e;
		}
		finally{
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					Log.e("Closing Input Stream", e.getMessage(), e);
					exceptionCaught =  e;
				}
		}
		return true;
	}

    /**
     *
     * @param progress
     */
	protected void onProgressUpdate (Integer... progress){
		Log.i("DOWNLOAD PROGRESS", "PROGRESS => " + progress[0]);
	}

    /**
     *
     * @param result
     */
	protected void onPostExecute(Boolean result){
		String week = matchFile.getName().split(".xml")[0];
		if (exceptionCaught != null)
				throw new RuntimeException("Unable to retrieve match data for " + week + ". Contact support at brian.navarro@gmail.com");
		else {
			if (result)
				alertDialog.show();
			else {
				alertDialog.setMessage("Download Failed");
				alertDialog.show();
			}
		}
	}
}
