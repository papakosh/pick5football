package com.bnavarro.pick5football.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.async.SubmitPicksAsync;
import com.dropbox.client2.exception.DropboxException;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

public class SubmitPicksMenuItemClickListener implements OnMenuItemClickListener {

	private MainActivity activity;

	public SubmitPicksMenuItemClickListener (MainActivity activity){
		this.activity= activity;
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		StringBuffer yourPicks = new StringBuffer("");
		for (int i = 0; i < activity.getMatchups().length; i++){
			if (activity.getMatchups()[i].getPickSelection() != null){
				yourPicks.append(activity.getMatchups()[i].getPickSelection());
				yourPicks.append("\n");
			}
		}
		
		try {
				submitPicks (yourPicks.toString());
				Toast.makeText(activity.getApplicationContext(), "Picks Submission Successful", 
						   Toast.LENGTH_LONG).show();
			
		} catch (FileNotFoundException e) {
			Toast.makeText(activity.getApplicationContext(), "Picks Submission Failed", 
					   Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (DropboxException e) {
			Toast.makeText(activity.getApplicationContext(), "Picks Submission Failed", 
					   Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		catch (IOException e) {
			Toast.makeText(activity.getApplicationContext(), "Picks Submission Failed", 
					   Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
		return false;
	}
	
	public void submitPicks (String picks) throws DropboxException, IOException{
		File file = new File(activity.getDataDirectory().getAbsolutePath() + "/picks.txt");
		file.createNewFile();
	    FileWriter filewriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(picks);
        out.close();
        //new SubmitPicksAsync(activity.getApplicationContext(), activity.getDropboxAccess(), null, file).execute();
        new SubmitPicksAsync(this.activity, file, picks).execute();
	}
}
