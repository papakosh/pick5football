package com.bnavarro.pick5football.listeners;


import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.pager.ViewMatchesFragmentPagerAdapter;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/** Reads the match picks from a local file and selects them from the listview. 
 * The selected file is <current week #> - picks.txt (i.e. Week 17 - picks.txt for week 17 picks).
 * 
 * @author brian navarro
 *
 */
public class LoadMatchesMenuItemClickListener implements
		OnMenuItemClickListener {

	private MainActivity mainActivity;

	public LoadMatchesMenuItemClickListener (MainActivity mainActivity){
		this.mainActivity= mainActivity;
	}
	
	/** Calls loadPicks method to read current week's picks
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {	
			String week = mainActivity.getCurrentWeek();
			MatchDataManagementService.loadSelectedPicks(week, mainActivity);
			mainActivity.setViewMatchesFragmentPagerAdapter(new ViewMatchesFragmentPagerAdapter(mainActivity.getSupportFragmentManager()));

		return false;
	}
	

	
	

}