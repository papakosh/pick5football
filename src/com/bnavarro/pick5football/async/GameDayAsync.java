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

import com.bnavarro.pick5football.GameDay;
import com.bnavarro.pick5football.GameDayParser;

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

	private GameDay gameDay;
	private Activity mainActivity; //likely used later when showing progress of downloading data in toast message
	public GameDayAsync (Activity mainActivity, GameDay gameDay){
		this.mainActivity=mainActivity;
		this.gameDay=gameDay;
	}

	@Override
	protected GameDay doInBackground(Void... params) {
		
		XmlPullParser parser = Xml.newPullParser();
    	InputStream in_s;
    	
    	 File exst = Environment.getExternalStorageDirectory();
 		String exstPath = exst.getPath();
 		File dataDir = new File(exstPath+"/Pick5FootballData");
		try {
			URL url = new URL ("http://www.nfl.com/liveupdate/scorestrip/ss.xml");
			File file = new File (dataDir.getAbsolutePath() + "/" +"ss.xml");
			FileUtils.copyURLToFile(url, file);
			in_s = new BufferedInputStream(new FileInputStream(file));
		    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	        parser.setInput(in_s, null);
	        
	        GameDayParser gdParser = new GameDayParser (parser, gameDay);
	        gdParser.parse();
	        in_s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return gameDay;
	}
	
	
//	protected void onProgressUpdate(Long result) {
//        //showDialog("Downloaded " + result + " bytes");
//		System.out.println ("in on progress update");
//		Toast.makeText(context, gameDay.getHomeTeamScore() + " - " + gameDay.getVisitingTeamScore(), Toast.LENGTH_SHORT).show();
//    }

}
