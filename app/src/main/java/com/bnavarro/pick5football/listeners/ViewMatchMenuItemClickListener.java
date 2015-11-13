package com.bnavarro.pick5football.listeners;

import java.util.ArrayList;

import com.bnavarro.pick5football.MainActivity;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/** Displays a popup menu when a match is clicked on.
 * The following options are shown:
 * <li>Pick None  (select no team)
 * <li>Pick Team 1 (select team 1, team 1 value is dynamic) 
 * <li>Pick Team 2 (select team 2, team 2 value is dynamic) 
 * <li>View Game Score (view the live details of the match on GameDay screen)
 * 
 * @author brian navarro
 *
 */
public class ViewMatchMenuItemClickListener implements AdapterView.OnItemClickListener{

	MainActivity activity;
	public ViewMatchMenuItemClickListener (MainActivity activity){
		this.activity = activity;
	}
	
	/** Show popup menu and then handle menu item selection
	 * <li>None - deselects pick selection
	 * <li>View Game - shows game day details screen
	 * <li>Pick Team 1 or Team 2 - makes pick selection of team 1 or team 2
	 *  
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, final View view, final int position, long i) {		

    }

}
