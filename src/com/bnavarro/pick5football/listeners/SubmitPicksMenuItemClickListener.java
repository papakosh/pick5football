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

/** Gathers each of the user's pick selections,  validates that exactly five have been made, 
 * saves them in a local file, and then pastes them into an email to be sent. 
 * The file is named <Current Week #> - picks.txt (i.e. Week 17 - picks.txt for Week 17). The 
 * user is prompted to choose the email provider (gmail or other) to be used to send the picks.
 * 
 * @author navman
 *
 */
public class SubmitPicksMenuItemClickListener implements OnMenuItemClickListener {

	private MainActivity activity;

	public SubmitPicksMenuItemClickListener (MainActivity activity){
		this.activity= activity;
	}
	
	/** Collects up the user's pick selections and stores them in a <code>StringBuffer</code>.
	 *  Then the picks are validated to be exactly five. Error message displayed otherwise.
	 *  <li>More than five Error: Too few picks entered. Please select five picks.
	 *  <li>Less than five Error: Too many picks entered. Please, select five picks.
	 *  
	 *  If exactly five then the execute method from class <code>SubmitPicksAsync</code> is called.
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {	
		Integer count = 0;
		StringBuffer yourPicks = new StringBuffer("");
		
		//Gather each pick selection and append it to StringBuffer. Also count them.
		for (int i = 0; i < activity.getMatchups().length; i++){
			if (activity.getMatchups()[i].getPickSelection() != null){
				yourPicks.append(activity.getMatchups()[i].getPickSelection());
				yourPicks.append("\n");
				count++;
			}
		}
		
		//Validate the number of picks to be exactly five, else throw errors.
		try {
			if (count.intValue() == 5){
				submitPicks (yourPicks.toString());
				Toast.makeText(activity.getApplicationContext(), "Picks Submission Successful", 
						   Toast.LENGTH_LONG).show();
			}else{
				if (count.intValue()  < 5){
					Toast.makeText(activity.getApplicationContext(), "Error: Too few picks entered. Please select five picks.", 
							   Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(activity.getApplicationContext(), "Error: Too many picks entered. Please, select five picks.", 
							   Toast.LENGTH_LONG).show();
				}
			}
			
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
	
	/**
	 * 
	 * @param picks
	 * @throws DropboxException
	 * @throws IOException
	 */
	public void submitPicks (String picks) throws DropboxException, IOException{
		File file = new File(activity.getDataDirectory().getAbsolutePath() + "/picks.txt");
		file.createNewFile();
	    FileWriter filewriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(picks);
        out.close();
        new SubmitPicksAsync(this.activity, file, picks).execute();
	}
}
