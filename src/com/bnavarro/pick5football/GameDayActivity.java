package com.bnavarro.pick5football;

import java.util.concurrent.ExecutionException;

import com.bnavarro.pick5football.async.GameDayAsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.bnavarro.pick5football.constants.*;
public class GameDayActivity extends Activity {

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_gameday);
	      
	      TextView dateTextView = (TextView)findViewById(R.id.dateText);
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
				String date = gameDay.getDate().substring(4, 6) + "/" + gameDay.getDate().substring(6, 8) + "/"+gameDay.getDate().substring(0, 4);
				dateTextView.setText(date);
    			if ((gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_FINAL_SCORE)) || (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_HALF_TIME))){
    				scoreTextView.setText (gameDay.getHomeTeam() + " " + gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeam() + " " + gameDay.getVisitingTeamScore());
    				if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_FINAL_SCORE))
    					timeTextView.setText( "Final Score");
    				else
    					timeTextView.setText( "Half-time");
    			}else if ((gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_NOT_PLAYED))){
    				scoreTextView.setText (gameDay.getHomeTeam() + " and " + gameDay.getVisitingTeam() + " play at "+ gameDay.getTime() + " pm E.T.");
    				timeTextView.setText("");
    			}else {
    				scoreTextView.setText ( gameDay.getHomeTeam() + " " + gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeam() + " " + gameDay.getVisitingTeamScore());
    				String quarterText;
    				if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_FIRST_QUARTER))
    					quarterText = "1st Quarter";
    				else if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_SECOND_QUARTER))
    					quarterText = "2nd Quarter";
    				else if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_THIRD_QUARTER))
    					quarterText = "3rd Quarter";
    				else if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_FOURTH_QUARTER))
    					quarterText = "4th Quarter";
    				else
    					quarterText = "Overtime";
    				
    				timeTextView.setText(gameDay.getClock() + " left in the " + quarterText);
    			}
    		}else {
    			scoreTextView.setText ( "No Game Data Available");
    			timeTextView.setText("");
    			dateTextView.setText("");
    		}
		} catch (InterruptedException | ExecutionException e) {
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
