package com.bnavarro.pick5football.listeners;

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
		try {
			activity.loadPicks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	

}