package com.bnavarro.pick5football.listeners;

import java.io.IOException;
import android.util.Log;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

//TODO Put header comments
//TODO - put error handling in
public class SubmitPicksMenuItemClickListener implements OnMenuItemClickListener {
	private MainActivity mainActivity;

	/**
	 *
	 * @param mainActivity
	 */
	public SubmitPicksMenuItemClickListener (MainActivity mainActivity){
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
		try {
			MatchDataManagementService.submitPicks(week, mainActivity);
		}
		catch (IOException e) {
			Log.e("Saving Picks", e.getMessage(), e);
		}
		return true;
	}
}
