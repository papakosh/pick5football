package com.bnavarro.pick5football;

import java.util.concurrent.ExecutionException;

import com.bnavarro.pick5football.async.GameDayAsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bnavarro.pick5football.constants.*;
public class GameDayActivity extends Activity {

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_gameday);
	      
	      TextView scoreTextView = (TextView)findViewById(R.id.scoreText);
	      TextView timeTextView = (TextView)findViewById(R.id.timeText);
	      
	      Intent intent = getIntent();
 
	    String team1 = intent.getStringExtra(IntentDataConstants.FIRST_TEAM);  
	    String team2 = intent.getStringExtra(IntentDataConstants.SECOND_TEAM);  
  		String homeTeamOrig = intent.getStringExtra(IntentDataConstants.HOME_TEAM);
  		String homeTeam;
  		String visitingTeam;
  		GameDay gameDay = new GameDay ();
  		
  		if (team1.contains(homeTeamOrig)){
  			homeTeam = TeamSign.getSignFromTeamName(team1);
  			visitingTeam=TeamSign.getSignFromTeamName(team2);
  		}else{
  			homeTeam = TeamSign.getSignFromTeamName(team2);
  			visitingTeam=TeamSign.getSignFromTeamName(team1);
  		}
  		gameDay.setHomeTeam(homeTeam);
  		gameDay.setVisitingTeam(visitingTeam);
  		
  		try {
			gameDay = new GameDayAsync(getApplicationContext(), GameDayActivity.this,gameDay).execute().get();
			
			if (gameDay.getQuarter()!= null){
    			if ((gameDay.getQuarter().contains(GameDayConstants.FINAL_SCORE)) || (gameDay.getQuarter().contains(GameDayConstants.HALF_TIME))){
    				scoreTextView.setText (gameDay.getHomeTeam() + " " + gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeam() + " " + gameDay.getVisitingTeamScore());
    				if (gameDay.getQuarter().contains(GameDayConstants.FINAL_SCORE))
    					timeTextView.setText( "Final Score");
    				else
    					timeTextView.setText( "Half-time");
    			}else if ((gameDay.getQuarter().contains(GameDayConstants.NOT_PLAYED))){
    				scoreTextView.setText (gameDay.getHomeTeam() + " and " + gameDay.getVisitingTeam() + " play at "+ gameDay.getTime() + " pm E.T.");
    				timeTextView.setText("");
    			}else {
    				scoreTextView.setText ( gameDay.getHomeTeam() + " " + gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeam() + " " + gameDay.getVisitingTeamScore());
    				String quarterText;
    				if (gameDay.getQuarter().contains(GameDayConstants.FIRST_QUARTER))
    					quarterText = "1st Quarter";
    				else if (gameDay.getQuarter().contains(GameDayConstants.SECOND_QUARTER))
    					quarterText = "2nd Quarter";
    				else if (gameDay.getQuarter().contains(GameDayConstants.THIRD_QUARTER))
    					quarterText = "3rd Quarter";
    				else if (gameDay.getQuarter().contains(GameDayConstants.FOURTH_QUARTER))
    					quarterText = "4th Quarter";
    				else
    					quarterText = "Overtime";
    				
    				timeTextView.setText(gameDay.getClock() + " left in the " + quarterText);
    			}
    		}else {
    			scoreTextView.setText ( "No Game Data Available");
    		}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	      
	      
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
