package com.bnavarro.pick5football.listeners;

import java.io.IOException;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;

import android.util.Log;
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

	private MainActivity mainActivity;

	public SaveMatchesMenuItemClickListener (MainActivity mainActivity){
		this.mainActivity= mainActivity;
	}
	
	/** Collects up the user's pick selections and stores them in a <code>StringBuffer</code>.
	 *  Afterwards, the savePicks method from class <code>MainActivity</code> is called.
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		String week = mainActivity.getCurrentWeek();
		try {
			MatchDataManagementService.savePicks(week, mainActivity);
		}
		catch (IOException e) {
			Log.e("Saving Picks", e.getMessage(), e);
		}
		return false;
	}
	

	

}