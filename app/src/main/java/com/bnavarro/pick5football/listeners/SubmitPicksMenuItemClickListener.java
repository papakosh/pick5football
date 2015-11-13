package com.bnavarro.pick5football.listeners;

import java.io.IOException;
import android.util.Log;

import com.bnavarro.pick5football.MainActivity;
import com.bnavarro.pick5football.MatchDataManagementService;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

/** <li>Gathers each of the user's pick selections
 * <li>validates that exactly five have been made 
 * <li>saves them in a local file
 * <li>pastes them into an email to be sent. 
 * 
 * <br></br>
 * 
 * <p>*Note* The file is named <Current Week #> - picks.txt (i.e. Week 17 - picks.txt for Week 17). The 
 * user is prompted to choose the email provider (gmail or other) to be used to send the picks.</p>
 * 
 * @author brian navarro
 *
 */
public class SubmitPicksMenuItemClickListener implements OnMenuItemClickListener {

	private MainActivity mainActivity;

	public SubmitPicksMenuItemClickListener (MainActivity mainActivity){
		this.mainActivity= mainActivity;
	}
	
	/** Collects up the user's pick selections and stores them in a <code>StringBuffer</code>.
	 *  Then the picks are validated to be exactly five. Error message displayed otherwise.
	 *  <li>More than five Error: Too few picks entered. Please select five picks.
	 *  <li>Less than five Error: Too many picks entered. Please, select five picks.
	 *  
	 *  If exactly five then the execute method from class <code>SubmitPicksAsync</code> is called.
	 * 
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {

		String week = mainActivity.getCurrentWeek();
		try {
			MatchDataManagementService.submitPicks(week, mainActivity);
		}
		catch (IOException e) {
			Log.e("Saving Picks", e.getMessage(), e);
		}
		return false;
	}
	

}
