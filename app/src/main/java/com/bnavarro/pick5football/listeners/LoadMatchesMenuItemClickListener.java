package com.bnavarro.pick5football.listeners;


import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.pager.ViewMatchesFragmentPagerAdapter;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

//TODO - put error handling in
//TODO - Put header comments
public class LoadMatchesMenuItemClickListener implements OnMenuItemClickListener {

	private MainActivity mainActivity;

	/**
	 *
	 * @param mainActivity
	 */
	public LoadMatchesMenuItemClickListener (MainActivity mainActivity){
		this.mainActivity= mainActivity;
	}

	/**
	 *
	 * @param item
	 * @return
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {	
			String week = mainActivity.getCurrentWeek();
			MatchDataManagementService.loadSelectedPicks(week, mainActivity);
			mainActivity.refreshPagerAdapter();
		return true;
	}
}