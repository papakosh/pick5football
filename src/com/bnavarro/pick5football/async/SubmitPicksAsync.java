package com.bnavarro.pick5football.async;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

/** Send email with match pick selections to user specified email address 
 * using mail system (gmail or otherwise) in a background task
 * 
 * @author brian navarro
 *
 */
public class SubmitPicksAsync extends AsyncTask<Void, Long, Boolean> {

	private Activity mainActivity;
	private String mPicks;
	public SubmitPicksAsync(Activity mainActivity, File file, String picks) {
		this.mainActivity=mainActivity;
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
		

		return true;
	}
	

}
