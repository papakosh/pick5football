package com.bnavarro.pick5football.listeners;

import java.io.IOException;
import com.bnavarro.pick5football.MainActivity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/** Reads the match selections from a local file and displays them to the user in the original list format. 
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
	
	/** Calls loadPicks method from class <code>MainActivity</code>
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {	
		try {
			activity.loadPicks();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

}