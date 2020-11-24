package com.bnavarro.pick5football.listeners;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.gameday.GameDay;

//TODO - put header comments
//TODO - put error handling in
public class GameDayMenuItemClickListener implements	OnMenuItemClickListener {
	private MainActivity mainActivity;

	/**
	 *
	 * @param mainActivity
	 */
	public GameDayMenuItemClickListener(MainActivity mainActivity){
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
		String msg = MatchDataManagementService.showNFLScores();

		mainActivity.showScoreToast(msg);
		return true;
	}
}
