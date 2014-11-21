package com.bnavarro.pick5football.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.bnavarro.pick5football.MainActivity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class LoadMatchesMenuItemClickListener implements
		OnMenuItemClickListener {

	private MainActivity activity;

	public LoadMatchesMenuItemClickListener (MainActivity activity){
		this.activity= activity;
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
//		StringBuffer yourPicks = new StringBuffer("");
//		try {
//			for (int i = 0; i < activity.getMatchups().length; i++){
//				if (activity.getMatchups()[i].getPickSelection() != null){
//					yourPicks.append(activity.getMatchups()[i].getPickSelection());
//					yourPicks.append("\n");
//				}
//			}
//			activity.savePicks(yourPicks.toString());
//			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		} 
		
		try {
			activity.loadPicks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	

}