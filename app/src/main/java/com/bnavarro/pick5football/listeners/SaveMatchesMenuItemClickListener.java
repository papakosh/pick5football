package com.bnavarro.pick5football.listeners;

import java.io.IOException;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;

import android.util.Log;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

//TODO - put comments in header
//TODO - put error handling in
public class SaveMatchesMenuItemClickListener implements OnMenuItemClickListener {
	private MainActivity mainActivity;

	/**
	 *
	 * @param mainActivity
	 */
	public SaveMatchesMenuItemClickListener (MainActivity mainActivity){
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
			MatchDataManagementService.savePicks(week, mainActivity);
		}
		catch (IOException e) {
			Log.e("Saving Picks", e.getMessage(), e);
		}
		return true;
	}
}