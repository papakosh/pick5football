package com.bnavarro.pick5football.async;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.CommonUtils;
import com.bnavarro.pick5football.gameday.GameDay;
import com.bnavarro.pick5football.gameday.GameDayParser;
import com.bnavarro.pick5football.gameday.MatchGameParms;
import com.bnavarro.pick5football.constants.AsyncDataConstants;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Xml;

/** Retrieve game day information from NFL.com 
 * and parse it in a background task
 * 
 * @author brian navarro
 *
 */
public class GameDayAsync extends AsyncTask<Void, Long, GameDay> {

	//private GameDay gameDay;
	private MatchGameParms matchGameParms;
	private Activity mainActivity; //likely used later when showing progress of downloading data in toast message
		
	public GameDayAsync (Activity mainActivity, MatchGameParms matchGameParms){
		this.mainActivity=mainActivity;
		this.matchGameParms=matchGameParms;
	}

	/** Background download of nfl live score xml into local directory (Pick5FootballData)
	 * 
	 */
	@Override
	protected GameDay doInBackground(Void... params) {
		
		//Setup data reference variables
    	File exst = Environment.getExternalStorageDirectory();
 		String exstPath = exst.getPath();
 		File dataDir = new File(CommonUtils.concat(exstPath, "/", AsyncDataConstants.DATA_DIR));
		
 		//Setup data extraction variables
		XmlPullParser parser = Xml.newPullParser();
    	InputStream in_s = null;
		
    	//Setup data storage variables
 		GameDay gameDay = null;

		try {
			//Download data from internet and  copy to local file
			URL url = new URL ("http://www.nfl.com/liveupdate/scorestrip/ss.xml");
			File file = new File (dataDir.getAbsolutePath() + "/" +"ss.xml");
			FileUtils.copyURLToFile(url, file);
			in_s = new BufferedInputStream(new FileInputStream(file));
			
			//Setup parser and parse GameDay details out of the downloaded xml
		    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	        parser.setInput(in_s, null); 
	        GameDayParser gdParser = new GameDayParser (parser);
	        gameDay = gdParser.parse(matchGameParms);
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (in_s != null)
				try {
				     //Close input
					in_s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
		return gameDay;
	}
	
	
//	protected void onProgressUpdate(Long result) {
//        //showDialog("Downloaded " + result + " bytes");
//		System.out.println ("in on progress update");
//		Toast.makeText(context, gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeamScore(), Toast.LENGTH_SHORT).show();
//    }

}
