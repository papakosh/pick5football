package com.bnavarro.pick5football.listeners;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.pager.ViewMatchesFragmentPagerAdapter;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/** Refreshes the matches list by pulling the latest xml from a private repository,
 * and then updates the current list with new match details. This is typically the spread and who is favored.
 * 
 * @author brian navarro
 *
 */
public class RefreshMatchesMenuItemClickListener implements
		OnMenuItemClickListener {

	private MainActivity mainActivity;

	public RefreshMatchesMenuItemClickListener (MainActivity mainActivity){
		this.mainActivity= mainActivity;
	}
	
	/** Calls refreshMatchups method
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		String currentWeek = mainActivity.getCurrentWeek();
		MatchDataManagementService.populateMatchMap(currentWeek, mainActivity, true);
		mainActivity.setViewMatchesFragmentPagerAdapter(new ViewMatchesFragmentPagerAdapter(mainActivity.getSupportFragmentManager()));

		return false;
	}

}
