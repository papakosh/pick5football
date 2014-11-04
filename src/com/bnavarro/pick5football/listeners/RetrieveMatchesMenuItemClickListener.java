package com.bnavarro.pick5football.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.MainActivity;
import com.dropbox.client2.exception.DropboxException;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public class RetrieveMatchesMenuItemClickListener implements
		OnMenuItemClickListener {

	private MainActivity activity;

	public RetrieveMatchesMenuItemClickListener (MainActivity activity){
		this.activity= activity;
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		try {
			activity.updateMatchups ();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DropboxException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

}
