package com.bnavarro.pick5football.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.bnavarro.pick5football.MainActivity;

import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Toast;

/** Reads the match picks from a local file and selects them from the listview. 
 * The selected file is <current week #> - picks.txt (i.e. Week 17 - picks.txt for week 17 picks).
 * 
 * @author brian navarro
 *
 */
public class LoadMatchesMenuItemClickListener implements
		OnMenuItemClickListener {

	private MainActivity activity;

	public LoadMatchesMenuItemClickListener (MainActivity activity){
		this.activity= activity;
	}
	
	/** Calls loadPicks method to read current week's picks
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {	
		try {
			loadPicks();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/** Reads local file for current week's picks and then selects them from the listview
	 * 
	 * @throws IOException
	 */
	private void loadPicks() throws IOException {
		
		//Access local file (if exist) and buffer read its contents
		File file = new File(activity.getDataDirectory().getAbsolutePath() + "/" + activity.getCurrentWeek() + "-saved-picks.txt");
		ArrayList<String> currentPicks = new ArrayList<String>();
		if (file.canRead()){
		    FileReader filereader = new FileReader(file);
	        BufferedReader in = new BufferedReader(filereader);
	        String line;
	        int num = 0;
	        while ((line=in.readLine()) != null){
	        	currentPicks.add(line.trim());
	        }

	        in.close();
	        int index = 0;
	        //Loop through each pick and make selection by matching the pick to team 1 or team 2
	        while (index < currentPicks.size()){
				Log.w("Pick 5 Football", currentPicks.get(index));
				index++;
//	        	for (int i =0; i <activity.getMatchups().length; i++){
//	        		if (activity.getMatchups()[i].getTeam1().getTeamName().contains(currentPicks.get(num)) || activity.getMatchups()[i].getTeam2().getTeamName().contains(currentPicks.get(num))  ){
//	        			activity.getMatchups()[i].makePick(currentPicks.get(num));
//	        			activity.getMatchupList().set(i, activity.getMatchups()[i].displayMatchupDetails());
//	        			activity.getListView().setItemChecked(i, true);
//	        		   num++;
//	        		   break;
//	        		}
//	        	}
	        }
			activity.getCustomPagerAdapter().notifyDataSetChanged();
	        //activity.getMatchArrayAdapter().notifyDataSetChanged();
	        
	        
		}else {
			Toast.makeText(activity.getApplicationContext(), "No previous pick selections found", Toast.LENGTH_LONG).show();
		}
	}
	
	

}