package com.bnavarro.pick5football.listeners;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.bnavarro.pick5football.MainActivity;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/** Gathers each of the user's pick selections and saves them to a file. 
 * The file is named <Current Week #> - picks.txt (i.e. Week 17 - picks.txt for Week 17)
 * 
 * @author brian navarro
 *
 */
public class SaveMatchesMenuItemClickListener implements
		OnMenuItemClickListener {

	private MainActivity activity;

	public SaveMatchesMenuItemClickListener (MainActivity activity){
		this.activity= activity;
	}
	
	/** Collects up the user's pick selections and stores them in a <code>StringBuffer</code>.
	 *  Afterwards, the savePicks method from class <code>MainActivity</code> is called.
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		StringBuffer yourPicks = new StringBuffer("");
		try {
			for (int i = 0; i < activity.getMatchups().length; i++){
				if (activity.getMatchups()[i].getPickSelection() != null){
					yourPicks.append(i + "," + activity.getMatchups()[i].getPickSelection());
					yourPicks.append("\n");
				}
			}
			savePicks(yourPicks.toString());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	/** Creates a local text file with football picks
	 * 
	 * @param picks
	 * @return
	 * @throws IOException
	 */
	public File savePicks(String picks) throws IOException {
		//creates local file 
		File file = new File(activity.getDataDirectory().getAbsolutePath() + "/" + activity.getCurrentWeek() + "-saved-picks.txt");
		file.createNewFile();
	    FileWriter filewriter = new FileWriter(file);
	    
	    //writes to local file
	    BufferedWriter out = new BufferedWriter(filewriter);
	    out.write(picks);
	    out.close();
	return file;
}
	

}