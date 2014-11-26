package com.bnavarro.pick5football;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.R;
import com.bnavarro.pick5football.async.GameDayAsync;
import com.bnavarro.pick5football.async.RetrieveMatchesAsync;
import com.bnavarro.pick5football.async.SubmitPicksAsync;
import com.bnavarro.pick5football.listeners.LoadMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.RetrieveMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.SaveMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.SubmitPicksMenuItemClickListener;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Spinner;


public class MainActivity extends Activity {
	
	private Matchup[] matchups;
	

	private PopupMenu popupMenu;
	private Integer option;
	
	private DropboxAPI<AndroidAuthSession> mDBApi;
	
	final static private String APP_KEY = "1t3c5oggvr0hnhe";
	final static private String APP_SECRET = "1zh1mvowilxj04d";
	final static private AccessType ACCESS_TYPE = AccessType.APP_FOLDER;
	private String exstPath;
	private File dataDir;
	private ArrayAdapter<String> adapter1;
	private ListView listview;
	private String currentWeek;
	private ArrayList<String> matchupList;
	private ArrayList<String> currentPicks;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidAuthSession session = buildSession();
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
        //StrictMode.setThreadPolicy(policy);
        
       // matchups = new Matchup[16];
        
		Spinner spinner = (Spinner)findViewById(R.id.spinnerDevOptions);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getBaseContext(), R.array.dev_options_array, android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		 File exst = Environment.getExternalStorageDirectory();
		 exstPath = exst.getPath();
		 dataDir = new File(exstPath+"/Pick5FootballData");

		if (!dataDir.exists()){
			boolean success= dataDir.mkdir();
			System.out.println("directory created is " + success);
		}

		  listview = (ListView) findViewById(R.id.listview);
		  listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		  listview.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(final AdapterView<?> parent,
						 View view, int position, long id) {
					option = position;
					
					popupMenu = new PopupMenu(parent.getContext(), view);
					popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){
					
						@Override
						public boolean onMenuItemClick(MenuItem item) {
							matchups[option].makePick(item.getTitle().toString());
							matchupList.set(option, matchups[option].displayMatchupDetails());
							if ("Pick None".equalsIgnoreCase(item.getTitle().toString()))
								listview.setItemChecked(option, false);
							else
								listview.setItemChecked(option, true);
							adapter1.notifyDataSetChanged();
							
							return false;
						}
						
					});
					popupMenu.getMenu().add("Pick None");
					popupMenu.getMenu().add("Pick " + matchups[position].getTeam1());
					popupMenu.getMenu().add("Pick " + matchups[position].getTeam2());
					popupMenu.show();
					return false;
				}

		        

		      });
		    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		        @Override
		        public void onItemClick(AdapterView<?> parent, final View view,
		            int position, long id) {
		        		new GameDayAsync(getApplicationContext(), MainActivity.this).execute();
		        	
		        	}

		      });
		    
		    
			spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

	            public void onNothingSelected(AdapterView<?> arg0) {

	            }

				@Override
				public void onItemSelected(AdapterView<?> parent, View arg1,
						int pos, long id) {
					 String item = (String)parent.getItemAtPosition(pos);
					 try {
						 currentWeek = item;
						createMatchups(false);
						matchupList = createList(matchups);
						 adapter1 = new ArrayAdapter<String>(parent.getContext(),
						android.R.layout.simple_list_item_activated_1, matchupList);
						listview.setAdapter(adapter1);
						listview.setVisibility(View.VISIBLE);
					} catch (XmlPullParserException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}catch (DropboxException e) {
						e.printStackTrace();
					}

				}
	        });
		    
    }
    
    

    
    private void createMatchups (boolean isUpdate) throws XmlPullParserException, IOException, DropboxException{
    	String week = currentWeek.replace(" ", "").toLowerCase(Locale.ENGLISH);
    	
    	File dropBoxFile = new File(dataDir.getAbsolutePath()+"/"+week+ ".xml");
    	if (!dropBoxFile.exists() || isUpdate){
    		  new RetrieveMatchesAsync(getApplicationContext(), mDBApi, null, dropBoxFile).execute();
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
    	menu.add("Submit Your Picks");
    	SubmitPicksMenuItemClickListener submitPicksListener = new SubmitPicksMenuItemClickListener (this);
    	menu.getItem(0).setOnMenuItemClickListener(submitPicksListener);

    	menu.add("Update Matchups");
    	menu.getItem(1).setOnMenuItemClickListener(new RetrieveMatchesMenuItemClickListener(this));
    	
    	menu.add("Save Picks");
    	menu.getItem(2).setOnMenuItemClickListener(new SaveMatchesMenuItemClickListener(this));
    	
    	menu.add("Load Picks");
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
	
	private ArrayList<String> createList (Matchup[] matchups){
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
	        	//System.out.println("line " + num + " is " + line);
	        	//num++;
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
			System.out.println("no such file");
		}
	}
	
	public void updateMatchups () throws DropboxException, IOException, XmlPullParserException{
		 createMatchups(true);
		 adapter1.clear();
		 adapter1.addAll(createList(matchups));
		 listview.setAdapter(adapter1);
		 listview.requestFocusFromTouch();
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
                    //System.out.println("NAME = " + name);
                    if (name.equalsIgnoreCase("MATCHUP")){
                    	currentMatchup = new Matchup();
                    } else if (currentMatchup != null){
                        if (name.equalsIgnoreCase("TEAM1")){
                        	currentMatchup.setTeam1(parser.nextText());
                        } else if (name.equalsIgnoreCase("TEAM2")){
                        	currentMatchup.setTeam2(parser.nextText());
                        } else if (name.equalsIgnoreCase("HOME")){
                        	currentMatchup.setHomeTeam(parser.nextText());
                        } else if (name.equalsIgnoreCase("SPREAD")){
                        	currentMatchup.setSpread(Double.valueOf(parser.nextText()));
                        } else if (name.equalsIgnoreCase("FAVORED")){
                        	currentMatchup.setFavoredTeam(parser.nextText());
                        } 
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("MATCHUP") && currentMatchup != null){
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
}
