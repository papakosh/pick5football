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
	
	private static String FIRST_QUARTER = "1st Quarter";
	private static String SECOND_QUARTER = "2nd Quarter";
	private static String THIRD_QUARTER = "3rd Quarter";
	private static String FOURTH_QUARTER = "4th Quarter";
	private static String OVERTIME_QUARTER="Overtime";
	private static String HALF_TIME = "H";
	private static String FINAL_SCORE = "F";
	
	/** Display screen components with values populated. 
	 * 
	 */
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_gameday);
	      
	    initializeComponents();
	    setDataValues ();
	    
	  	String homeTeamSign;
	  	String visitingTeamSign;
	
  		if (team1Data.contains(homeTeamData)){
  			homeTeamSign = TeamSign.getSignFromTeamName(team1Data);
  			visitingTeamSign=TeamSign.getSignFromTeamName(team2Data);
  		}else{
  			homeTeamSign = TeamSign.getSignFromTeamName(team2Data);
  			visitingTeamSign=TeamSign.getSignFromTeamName(team1Data);
  		}
  		MatchGameParms parms = new MatchGameParms (homeTeamSign,visitingTeamSign);
  		try {
			gameDay = new GameDayAsync(GameDayActivity.this,parms).execute().get();
			
			if (gameDay.getQuarter()!= null){
				displayGameDayDetailsForAvailableGame ();
    		}else {
    			displayGameDayDetailsForUnavailableGame();
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
	
	/**
	 * Initiliaze screen and data components
	 * 
	 */
	private void initializeComponents (){
	      dateTextView = (TextView)findViewById(R.id.dateText);
	      scoreTextView = (TextView)findViewById(R.id.scoreText);
	      timeTextView = (TextView)findViewById(R.id.timeText);
	      
	      intent = getIntent();
	      gameDay = new GameDay ();
	}

	/** Set global data values
	 * 
	 */
	private void setDataValues (){
		team1Data = intent.getStringExtra(IntentDataConstants.FIRST_TEAM);  
	    team2Data = intent.getStringExtra(IntentDataConstants.SECOND_TEAM);  
  		homeTeamData = intent.getStringExtra(IntentDataConstants.HOME_TEAM);
	}
	
	/** Display game day details for games to be played and being played. For both types,
	 * the date of the game is always displayed. Refer to the individual methods called below for the additional details 
	 * displayed for each type.
	 * 
	 * <li>Game Date text formatted: Month / Day / Year (12/07/2014)
	 * 
	 */
	private void displayGameDayDetailsForAvailableGame(){
			String date = CommonUtils.concatenate(gameDay.getDate().substring(4, 6), 
												  "/", gameDay.getDate().substring(6, 8), 
												  "/", gameDay.getDate().substring(0, 4));
			dateTextView.setText(date);
//			if ((gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_FINAL_SCORE)) 
//					|| (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_HALF_TIME))){
//				displayGameDetailsForScoredGame();
//			}else
			
			if ((gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_NOT_PLAYED))){
				displayGameDetailsForUnplayedGame();
			}else {
				displayGameDetailsForInProgressGame();
		}
	}

	/** Display game day details for games being played.
	 *  <li>Current score formatted: Home Team Name and Home Team Score - Visiting Team Name and Visiting Team Score (CHI 21 - TB 7)
	 *  <li>Quarter details formatted: Time left on the clock in quarter (14:50 left in the 4th quarter)
	 */
	private void displayGameDetailsForInProgressGame() {
		scoreTextView.setText ( CommonUtils.concatenate(
								gameDay.getHomeTeam(), " " , gameDay.getHomeTeamScore().toString() , 
								" - " ,
								gameDay.getVisitingTeam() , " " , gameDay.getVisitingTeamScore().toString()));
		if (HALF_TIME.equals(gameDay.getQuarter())){
			timeTextView.setText( "Half-time");
		}else if (FINAL_SCORE.equals(gameDay.getQuarter())){
			timeTextView.setText( "Final Score");
		}else{
			String quarterText;
			if (XMLConstants.GAME_DAY.ATTR_VAL_FIRST_QUARTER.equals(gameDay.getQuarter()))
				quarterText = FIRST_QUARTER;
			else if (XMLConstants.GAME_DAY.ATTR_VAL_SECOND_QUARTER.equals(gameDay.getQuarter()))
				quarterText = SECOND_QUARTER;
			else if (XMLConstants.GAME_DAY.ATTR_VAL_THIRD_QUARTER.equals(gameDay.getQuarter()))
				quarterText = THIRD_QUARTER;
			else if (XMLConstants.GAME_DAY.ATTR_VAL_FOURTH_QUARTER.equals(gameDay.getQuarter()))
				quarterText = FOURTH_QUARTER;
			else
				quarterText = OVERTIME_QUARTER;
			
			timeTextView.setText(gameDay.getClock() + " left in the " + quarterText);
		}
	}

	
	/** Display game details for games to be played.
	 * <li>Game details text formatted: Home Team Name and Visting Team Name play at game time E.T. (CHI and TB play at 1:00 PM ET)
	 * 
	 */
	private void displayGameDetailsForUnplayedGame() {
		scoreTextView.setText (gameDay.getHomeTeam() + " and " + gameDay.getVisitingTeam() + " play at "+ gameDay.getTime() + " pm E.T.");
		timeTextView.setText("");
	}

	/** Display game details for games that are at half-time or are final.
	 * <li>Current score formatted: Home Team Name and Home Team Score - Visiting Team Name and Visiting Team Score (CHI 21 - TB 7)
	 * <li><li>Quarter details formatted: Time left on the clock in quarter (14:50 left in the 4th quarter)
	 */
//	private void displayGameDetailsForScoredGame() {
//		scoreTextView.setText (CommonUtils.concatenate(
//								   gameDay.getHomeTeam(), " ",gameDay.getHomeTeamScore().toString(), 
//								   " - ",  
//								   gameDay.getVisitingTeam() , " " , gameDay.getVisitingTeamScore().toString()));
//		if (gameDay.getQuarter().contains(XMLConstants.GAME_DAY.ATTR_VAL_FINAL_SCORE))
//			timeTextView.setText( "Final Score");
//		else
//			timeTextView.setText( "Half-time");
//	}
	
	private void displayGameDayDetailsForUnavailableGame() {
		scoreTextView.setText ( "No Game Data Available");
		timeTextView.setText("");
		dateTextView.setText("");
	}
}
