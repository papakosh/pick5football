package com.bnavarro.pick5football;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.R;
import com.bnavarro.pick5football.async.RetrieveMatchesAsync;
import com.bnavarro.pick5football.async.SubmitPicksAsync;
import com.bnavarro.pick5football.constants.MenuConstants;
import com.bnavarro.pick5football.constants.XMLConstants;
import com.bnavarro.pick5football.listeners.LoadMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.MatchItemListener;
import com.bnavarro.pick5football.listeners.RetrieveMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.SaveMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.SubmitPicksMenuItemClickListener;
import com.bnavarro.pick5football.listeners.WeekItemListener;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 
 * @author navman
 *
 */
public class MainActivity extends Activity {
	
	//Screen fields
	private Spinner spnGameWeeks;	
	private ListView listview;
	
	//Data Access fields
	private DropboxAPI<AndroidAuthSession> mDBApi;
	final static private String APP_KEY = "1t3c5oggvr0hnhe";
	final static private String APP_SECRET = "1zh1mvowilxj04d";
	//final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER; //May be used in the future, not sure.
	private String exstPath;
	private File dataDir;
	
	//Interaction fields
	private Matchup[] matchups;
	private ArrayAdapter<String> adapter1;
	private String currentWeek;
	private ArrayList<String> matchupList;
	private ArrayList<String> currentPicks;
	
	//Aynschronous tasks
	private RetrieveMatchesAsync retrieval;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        intializeComponents ();
        
		initializeDataDirectory();
        
		listview.setOnItemClickListener(new MatchItemListener(this));
		spnGameWeeks.setOnItemSelectedListener(new WeekItemListener(this));
    
    }
    
    private void intializeComponents(){
    	spnGameWeeks = (Spinner)findViewById(R.id.spnGameWeeks);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
    										this.getBaseContext(), R.array.weeks_array, 
    										android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnGameWeeks.setAdapter(adapter);
		
    	mDBApi = new DropboxAPI<AndroidAuthSession>(buildSession());
    	
    	listview = (ListView) findViewById(R.id.listview);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    private void initializeDataDirectory (){
    	File exst = Environment.getExternalStorageDirectory();
		exstPath = exst.getPath();
		dataDir = new File(exstPath+"/Pick5FootballData");
		if (!dataDir.exists()){
			boolean success= dataDir.mkdir();
			System.out.println("Pick5FootballData directory created is " + success + " at"+ exstPath +"/Pick5FootballData");
		}else{
			System.out.println("No action taken. Pick5FootballData directory already exists " + " at"+ exstPath +"/Pick5FootballData");
		}
    }

    
    public void createMatchups (boolean isUpdate) throws XmlPullParserException, IOException, DropboxException, InterruptedException, ExecutionException, TimeoutException{
    	String week = currentWeek.replace(" ", "").toLowerCase(Locale.ENGLISH);
    	File dropBoxFile = new File(dataDir.getAbsolutePath()+"/"+week+ ".xml");
    	if (!dropBoxFile.exists() || isUpdate){
    		retrieval =  new RetrieveMatchesAsync(getApplicationContext(), mDBApi, null, dropBoxFile);
    		retrieval.execute();
        	retrieval.get();
    	}

    	XmlPullParser parser = Xml.newPullParser();
    	InputStream in_s = new BufferedInputStream(new FileInputStream(dropBoxFile));
	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in_s, null);
        matchups= parseXML(parser);
        in_s.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(MenuConstants.FILE_MENU.SUBMIT_PICKS);
    	SubmitPicksMenuItemClickListener submitPicksListener = new SubmitPicksMenuItemClickListener (this);
    	menu.getItem(0).setOnMenuItemClickListener(submitPicksListener);

    	menu.add(MenuConstants.FILE_MENU.REFRESH_MATCHES);
    	menu.getItem(1).setOnMenuItemClickListener(new RetrieveMatchesMenuItemClickListener(this));
    	
    	menu.add(MenuConstants.FILE_MENU.SAVE_PICKS);
    	menu.getItem(2).setOnMenuItemClickListener(new SaveMatchesMenuItemClickListener(this));
    	
    	menu.add(MenuConstants.FILE_MENU.LOAD_PICKS);
    	menu.getItem(3).setOnMenuItemClickListener(new LoadMatchesMenuItemClickListener(this));
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	return true;
    }
    
    @Override
    public void onBackPressed() {
    	doExit ();
    }

    private void doExit() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
        		MainActivity.this);

        alertDialog.setPositiveButton("Yes", exitApplication_OnClickListener);
        alertDialog.setNegativeButton("No", null);

        alertDialog.setMessage("Do you want to exit?");
        alertDialog.setTitle("Pick 5 Football");
        alertDialog.show();
    }
    
    final OnClickListener exitApplication_OnClickListener = new OnClickListener() {
	    public void onClick(DialogInterface dialog, int which) {
	    	finish();
      		
	    }
	};    
	
	@Override
	public void onDestroy (){
		
		super.onDestroy();
    	SharedPreferences myPrefs = getSharedPreferences("pref",0);	
    	myPrefs.edit().clear().commit();
	}
	
	public ArrayList<String> createList (Matchup[] matchups){
			 final ArrayList<String> list = new ArrayList<String>();
		    for (int i = 0; i < matchups.length; ++i) {
		      list.add(matchups[i].displayMatchupDetails());
		    }
		    
		 return list;
	}
	
	protected void onResume() {
	    super.onResume();

	    if (mDBApi != null && mDBApi.getSession().authenticationSuccessful()) {
	        try {
	            // Required to complete auth, sets the access token on the session
	            mDBApi.getSession().finishAuthentication();

	            mDBApi.getSession().getOAuth2AccessToken();
	        } catch (IllegalStateException e) {
	            Log.i("DbAuthLog", "Error authenticating", e);
	        }
	    }
	}
	
	public void submitPicks (String picks) throws DropboxException, IOException{
		File file = savePicks(picks);
        new SubmitPicksAsync(getApplicationContext(), mDBApi, null, file).execute();
	}

	public File savePicks(String picks) throws IOException {
		File file = new File(dataDir.getAbsolutePath() + "/" + currentWeek + "-picks.txt");
		file.createNewFile();
	    FileWriter filewriter = new FileWriter(file);
        BufferedWriter out = new BufferedWriter(filewriter);
        out.write(picks);
        out.close();
		return file;
	}
	
	public void loadPicks() throws IOException {
		File file = new File(dataDir.getAbsolutePath() + "/" + currentWeek + "-picks.txt");
		currentPicks = new ArrayList<String>();
		if (file.canRead()){
		    FileReader filereader = new FileReader(file);
	        BufferedReader in = new BufferedReader(filereader);
	        String line;
	        int num = 0;
	        while ((line=in.readLine()) != null){
	        	currentPicks.add(line.trim());
	        }

	        in.close();
	        
	        while (num < currentPicks.size()){
	        	for (int i =0; i <matchups.length; i++){
	        		if (matchups[i].getTeam1().contains(currentPicks.get(num)) || matchups[i].getTeam2().contains(currentPicks.get(num))  ){
	        		   matchups[i].makePick(currentPicks.get(num));
	        		   matchupList.set(i, matchups[i].displayMatchupDetails());
	        		   listview.setItemChecked(i, true);
	        		   num++;
	        		   break;
	        		}
	        	}
	        }
	       	adapter1.notifyDataSetChanged();
	        
	        
		}else {
			Toast.makeText(getApplicationContext(), "No previous pick selections found", Toast.LENGTH_LONG).show();
		}
	}
	
	public void updateMatchups () throws DropboxException, IOException, XmlPullParserException, InterruptedException, ExecutionException, TimeoutException{
		 createMatchups(true);
		 if (matchups == null){
			 System.out.println ("matchups is null");
		 	return;
		 }else{
			 for (int i = 0; i < matchups.length; i++){
				 matchupList.set(i,  matchups[i].displayMatchupDetails());
			 }
		 }
		 adapter1.notifyDataSetChanged();
	}

    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);

        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
		
		mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
        return session;
    }
    
    private Matchup[] parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
	{
		ArrayList<Matchup> week = null;
        int eventType = parser.getEventType();
        Matchup currentMatchup = null;
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                	week = new ArrayList<Matchup>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_MATCHUP)){
                    	currentMatchup = new Matchup();
                    } else if (currentMatchup != null){
                        if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_TEAM_1)){
                        	currentMatchup.setTeam1(parser.nextText());
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_TEAM_2)){
                        	currentMatchup.setTeam2(parser.nextText());
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_HOME)){
                        	currentMatchup.setHomeTeam(parser.nextText());
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_SPREAD)){
                        	currentMatchup.setSpread(Double.valueOf(parser.nextText()));
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_FAVORED)){
                        	currentMatchup.setFavoredTeam(parser.nextText());
                        } 
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_MATCHUP) && currentMatchup != null){
                    	week.add(currentMatchup);
                    } 
            }
            eventType = parser.next();
        }
        return week.toArray(new Matchup[week.size()]);
	}
	
    public Matchup[] getMatchups (){
    	return matchups;
    }
	
    public File getDataDirectory (){
    	return  dataDir;
    }
    
    public DropboxAPI<AndroidAuthSession> getDropboxAccess(){
    	return mDBApi;
    }
    
    public ArrayList<String> getMatchupList(){
    	return  matchupList;
    }
    
    public ListView getListView(){
    	return listview;
    }
    
    public ArrayAdapter<String> getMatchArrayAdapter (){
    	return adapter1;
    }
    
    public void setCurrentMatchWeek(String week){
    	this.currentWeek=week;
    }
    
    public void setMatchupList (ArrayList<String> matchupList){
    	this.matchupList = matchupList;
    	
    }
    
    public void setListAdapter (ArrayAdapter<String> adapter){
    	this.adapter1 = adapter;
    	listview.setAdapter(adapter1);
		listview.setVisibility(View.VISIBLE);
    }
    
}
