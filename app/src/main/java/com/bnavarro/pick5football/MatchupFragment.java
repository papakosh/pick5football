package com.bnavarro.pick5football;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bnavarro.pick5football.constants.XMLConstants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by navman on 9/1/2015.
 */
public class MatchupFragment extends Fragment {

    private ListView mylist = null;
    private ListView mylist2 = null;
    private ListView mylist3 = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        View rootView = inflater.inflate(R.layout.matchup_fragment_layout, container, false);

        // Get the arguments that was supplied when
        // the fragment was instantiated in the
        // CustomPagerAdapter
        Bundle args = getArguments();

        mylist = (ListView) rootView.findViewById(R.id.listView);
        mylist2 = (ListView) rootView.findViewById(R.id.listView2);
        mylist3 = (ListView) rootView.findViewById(R.id.listView3);

        File exst = Environment.getExternalStorageDirectory();
        String exstPath = exst.getPath();
        File dataDir = new File(exstPath+"/Pick5FootballData");
        File dropBoxFile = new File(dataDir.getAbsolutePath()+"/week1.xml");

        XmlPullParser parser = Xml.newPullParser();
        Matchup[] matchups = null;
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
        List<Item> items = new ArrayList<Item>();
        matchups[0].setSpread(7.0);
        matchups[0].setFavoredTeam("Pittsburgh");
        items.add(new Header (CommonUtils.concatenate(matchups[0].getMatchDate(), " ", matchups[0].getMatchTime())));
        items.add(new ListItem (matchups[0].getTeamOneHeaderDetails(), R.drawable.pittsburgh_steelers_logo));
        items.add(new ListItem (matchups[0].getTeamTwoHeaderDetails(), R.drawable.newengland_patriots_logo));
//CommonUtils.concatenate(matchups[0].getTeam2().getTeamName(), ";",matchups[0].getHomeTeam())
        List<Item> items2 = new ArrayList<Item>();
        items2.add(new Header (CommonUtils.concatenate(matchups[1].getMatchDate(), " ", matchups[1].getMatchTime())));
        matchups[1].setSpread(1.5);
        matchups[1].setFavoredTeam("Indianapolis");
        items2.add(new ListItem(matchups[1].getTeamOneHeaderDetails(), R.drawable.indianapolis_colts_logo));
        items2.add(new ListItem(matchups[1].getTeamTwoHeaderDetails(), R.drawable.buffalo_bills_logo));

        final MatchupAdapter listadapter = new MatchupAdapter(this.getActivity().getBaseContext(), items);
        mylist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mylist.setAdapter(listadapter);

        final MatchupAdapter listadapter2 = new MatchupAdapter(this.getActivity().getBaseContext(), items2);
        mylist2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mylist2.setAdapter(listadapter2);
        return rootView;
    }

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
                            team1 = new Team (null, parser.nextText());
                        } else if (name.equalsIgnoreCase(XMLConstants.MATCHES.TAG_TEAM_2)){
                            team2 = new Team (null, parser.nextText());
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
}
