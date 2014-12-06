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
import android.widget.Toast;

public class GameDayActivity extends Activity {

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_gameday);
	      
	      TextView textView = (TextView)findViewById(R.id.gameDayText);
	      
	      Intent intent = getIntent();
 
	    String team1 = intent.getStringExtra("TEAM_1");  
	    String team2 = intent.getStringExtra("TEAM_2");  
  		String homeTeamOrig = intent.getStringExtra("HOME_TEAM");
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
    			if ((gameDay.getQuarter().contains("F")) || (gameDay.getQuarter().contains("H"))){
    				textView.setText (gameDay.getHomeTeam() + " " + gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeam() + " " + gameDay.getVisitingTeamScore() + " in the " + gameDay.getQuarter() + " quarter");
    			}else if ((gameDay.getQuarter().contains("P"))){
    				textView.setText (gameDay.getHomeTeam() + " and " + gameDay.getVisitingTeam() + " play at "+ gameDay.getTime() + " pm E.T.");
    			}else {
    				textView.setText ( gameDay.getHomeTeam() + " " + gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeam() + " " + gameDay.getVisitingTeamScore() + " in the " + gameDay.getQuarter() + " quarter with " + gameDay.getClock());
    			}
    		}else {
    			textView.setText ( "No game data available");
    		}
			
			//textView.setText("No Data Available for the game between " + intent.getStringExtra("HOME_TEAM") + " and " +
		    //		  intent.getStringExtra("VISITING_TEAM"));
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
