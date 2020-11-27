package com.bnavarro.pick5football;

import java.io.File;
import java.util.LinkedHashMap;

import com.bnavarro.pick5football.async.RetrieveMatchDataAsyncService;
import com.bnavarro.pick5football.constants.MenuConstants;
import com.bnavarro.pick5football.gameday.GameDay;
import com.bnavarro.pick5football.listeners.GameDayMenuItemClickListener;
import com.bnavarro.pick5football.listeners.LoadMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.RefreshMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.SaveMatchesMenuItemClickListener;
import com.bnavarro.pick5football.listeners.SubmitPicksMenuItemClickListener;
import com.bnavarro.pick5football.listeners.MatchWeekOnItemSelectedListener;
import com.bnavarro.pick5football.pager.ViewMatchesFragmentPagerAdapter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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
	private Spinner spnMatchWeek;
	private ListView listview;

	//Data Access fields
	private String exstPath;
	private File dataDir;

	//Interaction fields
	private ArrayAdapter<String> adapter1;
	private String currentWeek;

	//Aynschronous tasks
	private RetrieveMatchDataAsyncService retrieval;
	private ViewMatchesFragmentPagerAdapter viewMatchesFragmentPagerAdapter;
	private ViewPager mViewPager;
	private LinkedHashMap<String, Match> matchMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
        intializeComponents();
        
		initializeDataDirectory();

		mViewPager = (ViewPager) findViewById(R.id.pager);
		spnMatchWeek.setOnItemSelectedListener(new MatchWeekOnItemSelectedListener(this));
    }

	
	public ViewMatchesFragmentPagerAdapter getCustomPagerAdapter(){
		return viewMatchesFragmentPagerAdapter;
	}

	public ViewPager getPager (){
		return mViewPager;
	}

	public void refreshPagerAdapter (){
		this.viewMatchesFragmentPagerAdapter=new ViewMatchesFragmentPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(viewMatchesFragmentPagerAdapter);
		mViewPager.setVisibility(View.VISIBLE);
	}

	public void showScoreDialog(String msg){
    	//Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

		AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
		alertDialog.setTitle(currentWeek + " Scores");
		alertDialog.setMessage(msg);
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		alertDialog.show();
	}

	public void setViewMatchesFragmentPagerAdapter (ViewMatchesFragmentPagerAdapter viewMatchesFragmentPagerAdapter){
		this.viewMatchesFragmentPagerAdapter=viewMatchesFragmentPagerAdapter;
		mViewPager.setAdapter(viewMatchesFragmentPagerAdapter);
		mViewPager.setVisibility(View.VISIBLE);
	}

    /** Initialize screen and data components
     * 
     */
    private void intializeComponents(){
    	//Initialize
    	// data for current week selection dropdown
		spnMatchWeek = (Spinner)findViewById(R.id.spnMatchWeek);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
    										this.getBaseContext(), R.array.weeks_array,
    										android.R.layout.simple_spinner_dropdown_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnMatchWeek.setAdapter(adapter);
    }

    /** Initialize local data directory for storing retrieved and user-saved files
     * 
     */
    private void initializeDataDirectory (){
		ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
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


    /** Main file menu options - submit picks, refresh matches, save picks, and load picks
     * 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(MenuConstants.FILE_MENU.SUBMIT_PICKS);
    	SubmitPicksMenuItemClickListener submitPicksListener = new SubmitPicksMenuItemClickListener (this);
    	menu.getItem(0).setOnMenuItemClickListener(submitPicksListener);
    	//menu.add(MenuConstants.FILE_MENU.REFRESH_MATCHES);
    	//menu.getItem(1).setOnMenuItemClickListener(new RefreshMatchesMenuItemClickListener(this));
		menu.add(MenuConstants.FILE_MENU.SHOW_SCORES);
		menu.getItem(1).setOnMenuItemClickListener(new GameDayMenuItemClickListener(this));

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
    	doExit();
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

	protected void onResume() {
	    super.onResume();

	}

    public File getDataDirectory (){
    	return  dataDir;
    }

    public void setMatchWeek(String week){
    	this.currentWeek=week;
    }

    public String getCurrentWeek (){
    	return currentWeek;
    }

    public void setMatchMapForReference(LinkedHashMap<String, Match> matchMap){
		this.matchMap = matchMap;
	}
}
