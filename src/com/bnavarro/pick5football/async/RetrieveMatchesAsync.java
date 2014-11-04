package com.bnavarro.pick5football.async;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.exception.DropboxException;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class RetrieveMatchesAsync extends AsyncTask<Void, Long, Boolean> {

	//private Context mContext;
	private DropboxAPI<?> mDBApi;
	//private String mPath;
	private File mFile;
	public RetrieveMatchesAsync(Context context, DropboxAPI<?> api, String dropboxPath,
	        File file) {
		//mContext = context.getApplicationContext();
		mDBApi=api;
		mFile=file;
	}
	
	
	@Override
	protected Boolean doInBackground(Void... params) {
		FileInputStream inputStream = null;
		try {
			FileOutputStream outputStream = new FileOutputStream(mFile);
    		DropboxFileInfo info = mDBApi.getFile("/"+mFile.getName(), null, outputStream, null);
			 
    		Log.i("DbExampleLog", "The downloaded file's rev is: " + info.getMetadata().rev);
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DropboxException e) {
			e.printStackTrace();
		}finally{
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
		return true;
	}

}
