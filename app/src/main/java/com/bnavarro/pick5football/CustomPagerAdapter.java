package com.bnavarro.pick5football;

/**
 * Created by navman on 9/1/2015.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
//import android.app.Activity;
import com.bnavarro.pick5football.MainActivity;

public class CustomPagerAdapter extends FragmentStatePagerAdapter {

   // protected Context mContext;
    private String matchWeek;
	private MatchupFragment fragment;
	private String text = "hello";
	private MainActivity mainActivity;
	
    public CustomPagerAdapter(FragmentManager fm, MainActivity mainActivity, String matchWeek) {
        super(fm);
        //mContext = context;
		this.mainActivity=mainActivity;
        this.matchWeek=matchWeek;
    }
	

    @Override
    // This method returns the fragment associated with
    // the specified position.
    //
    // It is called when the Adapter needs a fragment
    // and it does not exists.
    public Fragment getItem(int position) {

        // Create fragment object
        fragment = new MatchupFragment(matchWeek, mainActivity);

        // Attach some data to it that we'll
        // use to populate our fragment layouts
        Bundle args = new Bundle();
        args.putInt("page_position", position);
      //  args.putString("match_week", matchWeek);

        // Set the arguments on the fragment
        // that will be fetched in MatchupFragment@onCreateView
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

	public void setData (String text){
		//this.text = text;
		fragment.setData(text);
	}
	
	public String getData (){
		return fragment.getData();
	}
}
