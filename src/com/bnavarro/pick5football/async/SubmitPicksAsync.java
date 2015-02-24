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
	
	private static String EMAIL_MESSAGE ="message/rfc822";
	private static String EMAIL_SUBJECT = "NFL Picks";
	
	public SubmitPicksAsync(Activity mainActivity, String picks) {
		this.mainActivity=mainActivity;
		mPicks=picks;
	}

	/**
	 * Background send mail task to submit picks using user's mail client. Picks are added
	 * automatically, but the user will need to enter the recipient's email address.
	 */
	@Override
	protected Boolean doInBackground(Void... params) {	
		 Intent i = new Intent(Intent.ACTION_SEND);
		 i.setType(EMAIL_MESSAGE);
		 i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
		 i.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
		 i.putExtra(Intent.EXTRA_TEXT   , mPicks);
		 
		 mainActivity.startActivity(Intent.createChooser(i, "Send mail..."));
		

		return true;
	}
	

}
