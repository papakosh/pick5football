package com.bnavarro.pick5football.listeners;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.pager.ViewMatchesFragmentPagerAdapter;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

//TODO - put header comments
//TODO - put error handling in
public class RefreshMatchesMenuItemClickListener implements	OnMenuItemClickListener {
	private MainActivity mainActivity;

	/**
	 *
	 * @param mainActivity
	 */
	public RefreshMatchesMenuItemClickListener (MainActivity mainActivity){
		this.mainActivity= mainActivity;
	}

	/**
	 *
	 * @param item
	 * @return
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		String currentWeek = mainActivity.getCurrentWeek();
		MatchDataManagementService.populateMatchMap(currentWeek, mainActivity, true);
		mainActivity.refreshPagerAdapter();
		return true;
	}
}
