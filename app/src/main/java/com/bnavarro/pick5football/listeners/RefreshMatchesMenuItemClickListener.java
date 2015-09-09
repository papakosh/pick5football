package com.bnavarro.pick5football.listeners;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.CommonUtils;
import com.bnavarro.pick5football.CustomPagerAdapter;
import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.Matchup;
import com.dropbox.client2.exception.DropboxException;

import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/** Refreshes the matches list by pulling the latest xml from a private repository,
 * and then updates the current list with new match details. This is typically the spread and who is favored.
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
	
	/** Calls refreshMatchups method
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		try {
			refreshMatchups ();
			
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
	
	/** Creates new matchup list by retrieving latest file from dropbox repository.
	 * 
	 * @throws DropboxException
	 * @throws IOException
	 * @throws XmlPullParserException
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws TimeoutException
	 */
	private void refreshMatchups () throws DropboxException, IOException, XmlPullParserException, InterruptedException, ExecutionException, TimeoutException{
		 //Call create matchups with update true flag in order to retrieve fresh
		activity.pullData(activity.getCurrentWeek(),true);

		activity.setCustomPagerAdapterAdapter(new CustomPagerAdapter(activity.getSupportFragmentManager(), activity, activity.getCurrentWeek()));
//		 activity.createMatchups(true);
//		 Matchup[] matchups = activity.getMatchups();
//
//		 //Check to see if matchups array is empty
//		 if (CommonUtils.isArrayEmpty(matchups)){
//			 System.out.println ("matchups is null"); //replace with Exception
//		 	return;
//		 }else{ //loop through matchups and set new details for each
////			 activity.setMatchupList(activity.createList(activity.getMatchups()));
//			 for (int i = 0; i < matchups.length; i++){
//				 activity.getMatchupList().set(i,  matchups[i].displayMatchupDetails());
//			 }
//		 }
//
//		 //refresh data for list
//		 activity.getMatchArrayAdapter().notifyDataSetChanged();
	}
	
}
