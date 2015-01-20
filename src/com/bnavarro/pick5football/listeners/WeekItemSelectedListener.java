package com.bnavarro.pick5football.listeners;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.MainActivity;
import com.dropbox.client2.exception.DropboxException;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

/** When a week is selected from the dropdown, the current week is 
 * set and the matches are retrieved remotely and displayed to the user in a list format.
 *  
 * @author navman
 *
 */
public class WeekItemSelectedListener implements OnItemSelectedListener {

	private MainActivity activity;
	public WeekItemSelectedListener (MainActivity activity){
		this.activity = activity;
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1,
			int pos, long id) {
		String item = (String)parent.getItemAtPosition(pos);
		 try {
			activity.setCurrentMatchWeek(item);
			activity.createMatchups(false);
			activity.setMatchupList(activity.createList(activity.getMatchups()));
			
			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(parent.getContext(),
			android.R.layout.simple_list_item_activated_1, activity.getMatchupList());
			activity.setListAdapter(adapter1);
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (DropboxException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}
