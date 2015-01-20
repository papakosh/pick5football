package com.bnavarro.pick5football.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.MainActivity;
import com.dropbox.client2.exception.DropboxException;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/** Refreshes the matches list by pulling the latest xml from private repository,
 * and then updating the current list with new match details (typically spread and who is favored).
 * 
 * @author brian navarro
 *
 */
public class RefreshMatchesMenuItemClickListener implements
		OnMenuItemClickListener {

	private MainActivity activity;

	public RefreshMatchesMenuItemClickListener (MainActivity activity){
		this.activity= activity;
	}
	
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		try {
			activity.refreshMatchups ();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DropboxException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	

}
