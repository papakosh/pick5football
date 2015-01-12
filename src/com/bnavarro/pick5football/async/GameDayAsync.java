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
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Xml;
import android.widget.Toast;

public class GameDayAsync extends AsyncTask<Void, Long, GameDay> {

	private Context context;
	private GameDay gameDay;
	private Activity mainActivity;
	public GameDayAsync (Context context, Activity mainActivity, GameDay gameDay){
		this.context=context;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
