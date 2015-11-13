package com.bnavarro.pick5football.listeners;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import com.bnavarro.pick5football.pager.ViewMatchesFragmentPagerAdapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/** When a week is selected from the dropdown, the current week is 
 * set and the matches are retrieved remotely and displayed to the user in a list format.
 *  
 * @author brian navarro
 *
 */
public class MatchWeekOnItemSelectedListener implements OnItemSelectedListener {

	private MainActivity mainActivity;
	public MatchWeekOnItemSelectedListener(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}
	
	/** On selecting an item from the dropdown, create the
	 * matchup list for that week and display
	 * 
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1,
			int pos, long id) {
		String weekSelected = (String)parent.getItemAtPosition(pos);
		mainActivity.setMatchWeek(weekSelected);
		MatchDataManagementService.populateMatchMap(weekSelected, mainActivity, false);
		mainActivity.setViewMatchesFragmentPagerAdapter(new ViewMatchesFragmentPagerAdapter(mainActivity.getSupportFragmentManager()));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
