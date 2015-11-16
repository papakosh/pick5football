package com.bnavarro.pick5football;

import android.view.View;
import android.widget.TextView;

import com.bnavarro.pick5football.pager.MatchHeaderItem;
import com.bnavarro.pick5football.pager.MatchItemAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.RoboLayoutInflater;

import static org.junit.Assert.assertEquals;

/**
 * Created by navman on 11/12/2015.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = "src/main/AndroidManifest.xml")
public class MatchHeaderItemTest {

    MainActivity mainActivity;
    @Test
    public void testCreate(){
        mainActivity =  Robolectric.setupActivity(MainActivity.class);
        Team team1 = new Team ("Chicago Bears");
        Team team2 = new Team ("Green Bay Packers");
        Match match = new Match (team1, team2);
        match.setMatchDate("09/10/2015");
        match.setMatchTime("1:00 PM ET");
        MatchHeaderItem matchHeaderItem = new MatchHeaderItem(match);

        RoboLayoutInflater inflater = new RoboLayoutInflater(mainActivity);
        View view = matchHeaderItem.getView(inflater, null);
        TextView tv = (TextView)view.findViewById(R.id.headerDetails);

        assertEquals("GetViewType", MatchItemAdapter.MATCH_HEADER_ITEM, matchHeaderItem.getViewType());
        assertEquals("GetView", "09/10/2015 1:00 PM ET", tv.getText());
    }

    @Test(expected= IllegalArgumentException.class)
    public void testCreate_MissingMatch(){
        new MatchHeaderItem(null);
    }
}
