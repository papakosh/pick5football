package com.bnavarro.pick5football;

import java.util.concurrent.ExecutionException;

import com.bnavarro.pick5football.async.GameDayAsync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.bnavarro.pick5football.constants.*;

/**
 * Screen component for displaying game day information. The information displayed varies by 
 * the status of the game. The status is stored in the q (game quarter) field.
 * <ul> 
 *  <li>When status = P (Playable): Home team and visiting team play at specified time E.T. 
 *  	(CHI and NE play at 1:00 pm ET)</li>
 *  <li>When status = F (Final): Home team and home score - visiting team and visiting score 
 *  	(CHI 21 - NE 42 Final)</li>
 *  <li>Otherwise: Home team and home score - visiting team and visiting score, Time left in Quarter
 *  	(CHI 7 - NE 21 13:05 left in 2nd quarter)
 * </ul>
 * 
 * @author brian navarro
 *
 */
public class GameDayActivity extends Activity {

	private Intent intent;
	private GameDay gameDay;
	
	private TextView dateTextView;
	private TextView scoreTextView;
	private TextView timeTextView;
	
	private String team1Data;
	private String team2Data;
	private String homeTeamData;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_gameday);
	      
	    initializeComponents();
	    initializeData ();
	    
	  	String homeTeam;
	  	String visitingTeam;
	
  		if (team1Data.contains(homeTeamData)){
  			homeTeam = TeamSign.getSignFromTeamName(team1Data);
  			visitingTeam=TeamSign.getSignFromTeamName(team2Data);
  		}else{
  			homeTeam = TeamSign.getSignFromTeamName(team2Data);
  			visitingTeam=TeamSign.getSignFromTeamName(team1Data);
  		}
  		gameDay.setHomeTeam(homeTeam);
  		gameDay.setVisitingTeam(visitingTeam);
  		
  		try {
			gameDay = new GameDayAsync(GameDayActivity.this,gameDay).execute().get();
			
			if (gameDay.getQuarter()!= null){
				String date = gameDay.getDate().substring(4, 6) + "/" + gameDay.getDate().substring(6, 8) + "/"+gameDay.getDate().substring(0, 4);
				dateTextView.setText(date);
    			if ((gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_FINAL_SCORE)) || (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_HALF_TIME))){
    				scoreTextView.setText (gameDay.getHomeTeam() + " " + gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeam() + " " + gameDay.getVisitingTeamScore());
    				if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_FINAL_SCORE))
    					timeTextView.setText( "Final Score");
    				else
    					timeTextView.setText( "Half-time");
    			}else if ((gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_NOT_PLAYED))){
    				scoreTextView.setText (gameDay.getHomeTeam() + " and " + gameDay.getVisitingTeam() + " play at "+ gameDay.getTime() + " pm E.T.");
    				timeTextView.setText("");
    			}else {
    				scoreTextView.setText ( gameDay.getHomeTeam() + " " + gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeam() + " " + gameDay.getVisitingTeamScore());
    				String quarterText;
    				if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_FIRST_QUARTER))
    					quarterText = "1st Quarter";
    				else if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_SECOND_QUARTER))
    					quarterText = "2nd Quarter";
    				else if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_THIRD_QUARTER))
    					quarterText = "3rd Quarter";
    				else if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_FOURTH_QUARTER))
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
	
	private void initializeComponents (){
	      dateTextView = (TextView)findViewById(R.id.dateText);
	      scoreTextView = (TextView)findViewById(R.id.scoreText);
	      timeTextView = (TextView)findViewById(R.id.timeText);
	      
	      intent = getIntent();
	      gameDay = new GameDay ();
	}

	private void initializeData (){
		team1Data = intent.getStringExtra(IntentDataConstants.FIRST_TEAM);  
	    team2Data = intent.getStringExtra(IntentDataConstants.SECOND_TEAM);  
  		homeTeamData = intent.getStringExtra(IntentDataConstants.HOME_TEAM);
	}
}
