package com.bnavarro.pick5football;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.io.FileNotFoundException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.R;
import com.bnavarro.pick5football.async.RetrieveMatchesAsync;
import com.bnavarro.pick5football.constants.MenuConstants;
import com.bnavarro.pick5football.constants.XMLConstants;
import com.bnavarro.pick5football.listeners.LoadMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.ViewMatchMenuItemClickListener;
import com.bnavarro.pick5football.listeners.RefreshMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.SaveMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.SubmitPicksMenuItemClickListener;
import com.bnavarro.pick5football.listeners.WeekItemSelectedListener;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

/** <P>Screen component for displaying the week selection which drives the list of matches displayed. </P>
 *  <P>The upper right hand corner menu displays four options. 
 * <li>submit picks
 * <li>refresh matches
 * <li>save picks
 * <li>load picks
 * </P> 
 * </br>
 * <P>After clicking on a match, a menu will display four options.
 *  <li>Pick None - Unselects pick selection
 *  <li>Pick Team 1 (dynamic) - Make team 1 pick selection
 *  <li>Pick Team 2 (dynamic) - Make team 2 pick selection
 *  <li>View Game Score - Check score of the game
 * </P>
 * 
 * @author brian navarro
 *
 */
public class MainActivity extends FragmentActivity {
	
	//Screen fields
	private Spinner spnGameWeeks;	
	private ListView listview;
	
	//Data Access fields
	private DropboxAPI<AndroidAuthSession> mDBApi;
	final static private String APP_KEY = "1t3c5oggvr0hnhe";
	final static private String APP_SECRET = "1zh1mvowilxj04d";
	private String exstPath;
	private File dataDir;
	//final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER; //May be used in the future, not sure.
	
	//Interaction fields
	private Matchup[] matchups;
	private ArrayAdapter<String> adapter1;
	private String currentWeek;
	private ArrayList<String> matchupList;

	//Aynschronous tasks
	private RetrieveMatchesAsync retrieval;
	private CustomPagerAdapter mCustomPagerAdapter;
	private ViewPager mViewPager;
	//private MatchParcelable matchParceable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        intializeComponents();
        
		initializeDataDirectory();

		mViewPager = (ViewPager) findViewById(R.id.pager);
		spnGameWeeks.setOnItemSelectedListener(new WeekItemSelectedListener(this));
		//matchParceable = new MatchParcelable (matchups);
		//mCustomPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), this, "week2");
		//mViewPager.setAdapter(mCustomPagerAdapter);
		//listview.setOnItemClickListener(new ViewMatchMenuItemClickListener(this));
    }
	
	public CustomPagerAdapter getCustomPagerAdapter(){
		return mCustomPagerAdapter;
	}

	public ViewPager getPager (){
		return mViewPager;
	}

	public void setCustomPagerAdapterAdapter (CustomPagerAdapter mCustomPagerAdapter){
		this.mCustomPagerAdapter=mCustomPagerAdapter;
		mViewPager.setAdapter(mCustomPagerAdapter);
		mViewPager.setVisibility(View.VISIBLE);
	}
	
	public void setMatchups (Matchup[] matchups){
		this.matchups = matchups;
	}
    
	public void updateMatchups (Matchup[] matchups, int index){
		this.matchups[index] = matchups[index];
	}
	
    /** Initialize screen and data components
     * 
     */
    private void intializeComponents(){
    	//Initialize data for current week selection dropdown
    	spnGameWeeks = (Spinner)findViewById(R.id.spnGameWeeks);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
    										this.getBaseContext(), R.array.weeks_array,
    										android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnGameWeeks.setAdapter(adapter);
		
		//Initialize dropbox connection
    	mDBApi = new DropboxAPI<AndroidAuthSession>(buildSession());
    	
    	//Initalize listview component
    	//listview = (ListView) findViewById(R.id.listview);
		//listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    
    /** Initialize local data directory for storing retrieved and user-saved files
     * 
     */
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

	public void pullData(String week, boolean isUpdate) throws InterruptedException, ExecutionException{
		String matchweek = week.replace(" ", "").toLowerCase(Locale.ENGLISH);
		File exst = Environment.getExternalStorageDirectory();
        String exstPath = exst.getPath();
        File dataDir = new File(exstPath+"/Pick5FootballData");
		
		File dropBoxFile = new File(dataDir.getAbsolutePath()+"/"+matchweek+ ".xml");
		//Retrieve list of matches for current week if file does not exist or is an update
		if (!dropBoxFile.exists() || isUpdate){
			retrieval =  new RetrieveMatchesAsync(getApplicationContext(), mDBApi, null, dropBoxFile);
			retrieval.execute();
			retrieval.get();
		}
		
     //   File dropBoxFile = new File(dataDir.getAbsolutePath()+"/" + matchWeek + ".xml");

        XmlPullParser parser = Xml.newPullParser();

        try {
            InputStream in_s = new BufferedInputStream(new FileInputStream(dropBoxFile));
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            matchups= parseXML(parser);

            in_s.close();
        }catch(FileNotFoundException ex) {

        }catch (XmlPullParserException ex2){

        }catch (IOException ex3){

        }
	
	}

    /** Create a new array of matchups based on retrieved data, or if data exists and not an update, just
     * refresh the list from the local directory.
     * 
     * @param isUpdate <code>Boolean</code> value to determine if new list needs to be retrieved
     * @throws XmlPullParserException
     * @throws IOException
     * @throws DropboxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public void createMatchups (boolean isUpdate) throws XmlPullParserException, IOException, DropboxException, InterruptedException, ExecutionException, TimeoutException{
    	String week = currentWeek.replace(" ", "").toLowerCase(Locale.ENGLISH);
    	File dropBoxFile = new File(dataDir.getAbsolutePath()+"/"+week+ ".xml");
    	
    	//Retrieve list of matches for current week if file does not exist or is an update
    	if (!dropBoxFile.exists() || isUpdate){
    		retrieval =  new RetrieveMatchesAsync(getApplicationContext(), mDBApi, null, dropBoxFile);
    		retrieval.execute();
        	retrieval.get();
    	}

    	XmlPullParser parser = Xml.newPullParser();
    	InputStream in_s = new BufferedInputStream(new FileInputStream(dropBoxFile));
	    parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in_s, null);
        
    	//Parse matchup details from xml into Matchup objects
        matchups= parseXML(parser);
        
        in_s.close();
    }

    /** Main file menu options - submit picks, refresh matches, save picks, and load picks
     * 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(MenuConstants.FILE_MENU.SUBMIT_PICKS);
    	SubmitPicksMenuItemClickListener submitPicksListener = new SubmitPicksMenuItemClickListener (this);
    	menu.getItem(0).setOnMenuItemClickListener(submitPicksListener);

    	menu.add(MenuConstants.FILE_MENU.REFRESH_MATCHES);
    	menu.getItem(1).setOnMenuItemClickListener(new RefreshMatchesMenuItemClickListener(this));
    	
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

    /** Prompt user when they try to exit app.  Yes or no option available.
     * 
     */
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
		
//	public void loadPicks() throws IOException {
//		File file = new File(dataDir.getAbsolutePath() + "/" + currentWeek + "-picks.txt");
//		currentPicks = new ArrayList<String>();
//		if (file.canRead()){
//		    FileReader filereader = new FileReader(file);
//	        BufferedReader in = new BufferedReader(filereader);
//	        String line;
//	        int num = 0;
//	        while ((line=in.readLine()) != null){
//	        	currentPicks.add(line.trim());
//	        }
//
//	        in.close();
//	        
//	        while (num < currentPicks.size()){
//	        	for (int i =0; i <matchups.length; i++){
//	        		if (matchups[i].getTeam1().contains(currentPicks.get(num)) || matchups[i].getTeam2().contains(currentPicks.get(num))  ){
//	        		   matchups[i].makePick(currentPicks.get(num));
//	        		   matchupList.set(i, matchups[i].displayMatchupDetails());
//	        		   listview.setItemChecked(i, true);
//	        		   num++;
//	        		   break;
//	        		}
//	        	}
//	        }
//	       	adapter1.notifyDataSetChanged();
//	        
//	        
//		}else {
//			Toast.makeText(getApplicationContext(), "No previous pick selections found", Toast.LENGTH_LONG).show();
//		}
//	}
	
//	public void refreshMatchups () throws DropboxException, IOException, XmlPullParserException, InterruptedException, ExecutionException, TimeoutException{
//		 createMatchups(true);
//		 if (matchups == null){
//			 System.out.println ("matchups is null");
//		 	return;
//		 }else{
//			 for (int i = 0; i < matchups.length; i++){
//				 matchupList.set(i,  matchups[i].displayMatchupDetails());
//			 }
//		 }
//		 adapter1.notifyDataSetChanged();
//	}

	/** Create Dropbox authenticated session 
	 * 
	 * @return <code>AndroidAuthSession</code> object
	 */
    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);

        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
		mDBApi = new DropboxAPI<AndroidAuthSession>(session);
		
		mDBApi.getSession().startOAuth2Authentication(MainActivity.this);
        return session;
    }
    
    /** Parse through the xml file looking for matchup details - team 1, team 2, home team, 
     *  spread value, and favored team - and then return in <code>Matchup</code> array
     * 
     * @param parser <code>XmlPullParser</code> object configured to find and return xml nodes
     * 
     * @return <code>Matchup</code> array containing matchup details for a given week
     * @throws XmlPullParserException
     * @throws IOException
     */

    private Matchup[] parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        ArrayList<Matchup> week = null;
        int eventType = parser.getEventType();
        boolean matchupFound = false;
        Team team1 = null;
        Team team2 = null;
        String homeTeamName = null;
        Double matchSpread = null;
        String favoredTeamName = null;
        String matchDate = null;
        String matchTime = null;
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;

            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    week = new ArrayList<Matchup>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_MATCHUP)){
                        //currentMatchup = new Matchup();
                        matchupFound = true;
                    } else{
                        if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_TEAM_1)){
                            team1 = new Team (parser.nextText());
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_TEAM_2)){
                            team2 = new Team (parser.nextText());
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_HOME)){
                            homeTeamName = parser.nextText();
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_SPREAD)){
                            matchSpread = Double.valueOf(parser.nextText());
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_FAVORED)){
                            favoredTeamName = parser.nextText();
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_DATE)){
                            matchDate = parser.nextText();
                        }else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_TIME)){
                            matchTime = parser.nextText();
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (matchupFound) {
                        //Set xml data into current matchup
                        Matchup currentMatchup = new Matchup(team1,team2);
                        currentMatchup.setFavoredTeam(favoredTeamName);
                        currentMatchup.setHomeTeam(homeTeamName);
                        currentMatchup.setSpread(matchSpread);
                        currentMatchup.setMatchDate(matchDate);
                        currentMatchup.setMatchTime(matchTime);
                        week.add(currentMatchup);

                        //reset local variables before next matchup
                        matchupFound = false;
                        team1 = null;
                        team2 = null;
                        homeTeamName = null;
                        favoredTeamName=null;
                        matchSpread=null;
                        matchDate=null;
                        matchTime=null;
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
    
    public String getCurrentWeek (){
    	return currentWeek;
    }
}
