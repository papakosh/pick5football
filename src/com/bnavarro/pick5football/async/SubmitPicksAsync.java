package com.bnavarro.pick5football.async;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class SubmitPicksAsync extends AsyncTask<Void, Long, Boolean> {

	//private Context mContext;
	private DropboxAPI<?> mDBApi;
	//private String mPath;
	private File mFile;
	private Activity mainActivity;
	private String mPicks;
	public SubmitPicksAsync(Context context, DropboxAPI<?> api, String dropboxPath,
	        File file) {
		//mContext = context.getApplicationContext();
		mDBApi=api;
		mFile=file;
	}
	public SubmitPicksAsync(Activity mainActivity, File file, String picks) {
		//mContext = context.getApplicationContext();
		this.mainActivity=mainActivity;
		mFile=file;
		mPicks=picks;
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
				
		 Intent i = new Intent(Intent.ACTION_SEND);
		 i.setType("message/rfc822");
		 i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
		 i.putExtra(Intent.EXTRA_SUBJECT, "NFL Picks");
		 i.putExtra(Intent.EXTRA_TEXT   , mPicks);
		 
		 mainActivity.startActivity(Intent.createChooser(i, "Send mail..."));
		
//		FileInputStream inputStream = null;
//		Entry response;
//		try {
//			inputStream = new FileInputStream(mFile);
//			 response = mDBApi.putFile("/"+mFile.getName(), inputStream,
//                    mFile.length(), null, null);
//			 
//			 Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
//			 
//			 Intent i = new Intent(Intent.ACTION_SEND);
//			 i.setType("message/rfc822");
//			 i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
//			 i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
//			 i.putExtra(Intent.EXTRA_TEXT   , "body of email");
//			 try {
//				 mainActivity.startActivity(Intent.createChooser(i, "Send mail..."));
//			 } catch (android.content.ActivityNotFoundException ex) {
//			     Toast.makeText(mainActivity, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//			 }
//			 
//			 
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (DropboxException e) {
//			e.printStackTrace();
//		}finally{
//			if (inputStream != null)
//				try {
//					inputStream.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			
//			mFile.delete();
//		}
		return true;
	}
	

}
