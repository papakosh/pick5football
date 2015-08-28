package com.bnavarro.pick5football.listeners;

import java.util.ArrayList;

import com.bnavarro.pick5football.CommonUtils;
import com.bnavarro.pick5football.GameDayActivity;
import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.Matchup;
import com.bnavarro.pick5football.constants.IntentDataConstants;
import com.bnavarro.pick5football.constants.MenuConstants;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

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
		final Matchup[] matchups = activity.getMatchups();
		final ArrayList<String> matchupList = activity.getMatchupList();
		final ListView listview = activity.getListView();
		final ArrayAdapter<String> adapter1 = activity.getMatchArrayAdapter();
		
		PopupMenu popupMenu = new PopupMenu(parent.getContext(), view);
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){
			
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			//If "none" item selection, deselect pick selection and refresh matchup
			if (MenuConstants.MATCH_MENU.NONE.equalsIgnoreCase(item.getTitle().toString())){
				matchups[position].makePick(item.getTitle().toString());
				matchupList.set(position, matchups[position].displayMatchupDetails());
				listview.setItemChecked(position, false);
				
			//If "view game" item selection,  prepare intent data and then start Game Day activity
			}else if (MenuConstants.MATCH_MENU.VIEW_GAME.equalsIgnoreCase(item.getTitle().toString())){
				
				//Pass through data - team 1 and team 2, home team name, and current week
				Intent intent = new Intent(activity, GameDayActivity.class);
	        	intent.putExtra(IntentDataConstants.FIRST_TEAM, matchups[position].getTeam1());
	        	intent.putExtra(IntentDataConstants.SECOND_TEAM, matchups[position].getTeam2());
	        	intent.putExtra(IntentDataConstants.HOME_TEAM, matchups[position].getHomeTeam());
	        	intent.putExtra(IntentDataConstants.WEEK,  activity.getCurrentWeek());
	        	
	        	//Retaining pick selection to either to stay deselected or remain selected 
	        	//after clicking on menu item choice
	        	if (!CommonUtils.hasText(matchups[position].getPickSelection()))
	        		listview.setItemChecked(position, false);
	        	else
	        		listview.setItemChecked(position, true);
	        	
	        	//refresh data for listview and then start activity
	        	adapter1.notifyDataSetChanged();
	        	activity.startActivity(intent);
	        	
			}else{ //If "team 1 or team 2" item selection, make pick selection
				matchups[position].makePick(item.getTitle().toString());
				matchupList.set(position, matchups[position].displayMatchupDetails());
			
				listview.setItemChecked(position, true);
			}
			
			//refresh data for listview 
			adapter1.notifyDataSetChanged();
			
			return false;
		} 
		});
		
		//Add menu items to popup menu and then show
		popupMenu.getMenu().add(MenuConstants.MATCH_MENU.NONE);
		popupMenu.getMenu().add(MenuConstants.MATCH_MENU.PICK + matchups[position].getTeam1());
		popupMenu.getMenu().add(MenuConstants.MATCH_MENU.PICK + matchups[position].getTeam2());
		popupMenu.getMenu().add(MenuConstants.MATCH_MENU.VIEW_GAME);
		popupMenu.show();
    } 

}
