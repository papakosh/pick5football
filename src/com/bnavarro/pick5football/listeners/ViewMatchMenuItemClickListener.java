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
			if (MenuConstants.MATCH_MENU.NONE.equalsIgnoreCase(item.getTitle().toString())){
				matchups[position].makePick(item.getTitle().toString());
				matchupList.set(position, matchups[position].displayMatchupDetails());
				listview.setItemChecked(position, false);
			}else if (MenuConstants.MATCH_MENU.VIEW_GAME.equalsIgnoreCase(item.getTitle().toString())){
				Intent intent = new Intent(activity, GameDayActivity.class);
	        	intent.putExtra(IntentDataConstants.FIRST_TEAM, matchups[position].getTeam1());
	        	intent.putExtra(IntentDataConstants.SECOND_TEAM, matchups[position].getTeam2());
	        	intent.putExtra(IntentDataConstants.HOME_TEAM, matchups[position].getHomeTeam());
	        	intent.putExtra(IntentDataConstants.WEEK,  activity.getCurrentWeek());
	        	
	        	if (!CommonUtils.hasText(matchups[position].getPickSelection()))
	        		listview.setItemChecked(position, false);
	        	else
	        		listview.setItemChecked(position, true);
	        	adapter1.notifyDataSetChanged();
	        	activity.startActivity(intent);
			}else{
				matchups[position].makePick(item.getTitle().toString());
				matchupList.set(position, matchups[position].displayMatchupDetails());
			
				listview.setItemChecked(position, true);
			}
			adapter1.notifyDataSetChanged();
			
			return false;
		} 
		});
		popupMenu.getMenu().add(MenuConstants.MATCH_MENU.NONE);
		popupMenu.getMenu().add(MenuConstants.MATCH_MENU.PICK + matchups[position].getTeam1());
		popupMenu.getMenu().add(MenuConstants.MATCH_MENU.PICK + matchups[position].getTeam2());
		popupMenu.getMenu().add(MenuConstants.MATCH_MENU.VIEW_GAME);
		popupMenu.show();
    } 

}
