package com.bnavarro.pick5football.listeners;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.pager.ViewMatchesFragmentPagerAdapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

//TODO - Put header comments
//TODO - put error handling in
public class MatchWeekOnItemSelectedListener implements OnItemSelectedListener {
	private MainActivity mainActivity;

	/**
	 *
	 * @param mainActivity
	 */
	public MatchWeekOnItemSelectedListener(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}

	/**
	 *
	 * @param parent
	 * @param arg1
	 * @param pos
	 * @param id
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1,
			int pos, long id) {
		String weekSelected = (String)parent.getItemAtPosition(pos);
		MatchDataManagementService.populateMatchMap(weekSelected, mainActivity, false);
		mainActivity.setMatchWeek(weekSelected);
		mainActivity.refreshPagerAdapter();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
