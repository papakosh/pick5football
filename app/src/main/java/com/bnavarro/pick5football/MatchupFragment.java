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
        int page_position = args.getInt("page_position");

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
        List<Item> items2 = new ArrayList<Item>();
        List<Item> items3 = new ArrayList<Item>();
        switch (page_position) {
            case 0: //page 1
                items.add(new Header(CommonUtils.concatenate(matchups[0].getMatchDate(), " ", matchups[0].getMatchTime())));
                items.add(new ListItem(matchups[0].getTeamOneHeaderDetails(), matchups[0].getTeam1().getTeamLogo()));
                items.add(new ListItem(matchups[0].getTeamTwoHeaderDetails(), matchups[0].getTeam2().getTeamLogo()));

                items2.add(new Header(CommonUtils.concatenate(matchups[1].getMatchDate(), " ", matchups[1].getMatchTime())));
                items2.add(new ListItem(matchups[1].getTeamOneHeaderDetails(), matchups[1].getTeam1().getTeamLogo()));
                items2.add(new ListItem(matchups[1].getTeamTwoHeaderDetails(), matchups[1].getTeam2().getTeamLogo()));

                items3.add(new Header(CommonUtils.concatenate(matchups[2].getMatchDate(), " ", matchups[2].getMatchTime())));
                items3.add(new ListItem(matchups[2].getTeamOneHeaderDetails(), matchups[2].getTeam1().getTeamLogo()));
                items3.add(new ListItem(matchups[2].getTeamTwoHeaderDetails(), matchups[2].getTeam2().getTeamLogo()));
                break;
            case 1: //page 2
                items.add(new Header(CommonUtils.concatenate(matchups[3].getMatchDate(), " ", matchups[3].getMatchTime())));
                items.add(new ListItem(matchups[3].getTeamOneHeaderDetails(), matchups[3].getTeam1().getTeamLogo()));
                items.add(new ListItem(matchups[3].getTeamTwoHeaderDetails(), matchups[3].getTeam2().getTeamLogo()));

                items2.add(new Header(CommonUtils.concatenate(matchups[4].getMatchDate(), " ", matchups[4].getMatchTime())));
                items2.add(new ListItem(matchups[4].getTeamOneHeaderDetails(), matchups[4].getTeam1().getTeamLogo()));
                items2.add(new ListItem(matchups[4].getTeamTwoHeaderDetails(), matchups[4].getTeam2().getTeamLogo()));

                items3.add(new Header(CommonUtils.concatenate(matchups[5].getMatchDate(), " ", matchups[5].getMatchTime())));
                items3.add(new ListItem(matchups[5].getTeamOneHeaderDetails(), matchups[5].getTeam1().getTeamLogo()));
                items3.add(new ListItem(matchups[5].getTeamTwoHeaderDetails(), matchups[5].getTeam2().getTeamLogo()));
                break;
            case 2: //page 3
                items.add(new Header(CommonUtils.concatenate(matchups[6].getMatchDate(), " ", matchups[6].getMatchTime())));
                items.add(new ListItem(matchups[6].getTeamOneHeaderDetails(), matchups[6].getTeam1().getTeamLogo()));
                items.add(new ListItem(matchups[6].getTeamTwoHeaderDetails(), matchups[6].getTeam2().getTeamLogo()));

                items2.add(new Header(CommonUtils.concatenate(matchups[7].getMatchDate(), " ", matchups[7].getMatchTime())));
                items2.add(new ListItem(matchups[7].getTeamOneHeaderDetails(), matchups[7].getTeam1().getTeamLogo()));
                items2.add(new ListItem(matchups[7].getTeamTwoHeaderDetails(), matchups[7].getTeam2().getTeamLogo()));

                items3.add(new Header(CommonUtils.concatenate(matchups[8].getMatchDate(), " ", matchups[8].getMatchTime())));
                items3.add(new ListItem(matchups[8].getTeamOneHeaderDetails(), matchups[8].getTeam1().getTeamLogo()));
                items3.add(new ListItem(matchups[8].getTeamTwoHeaderDetails(), matchups[8].getTeam2().getTeamLogo()));
                break;
            case 3: //page 4
                items.add(new Header(CommonUtils.concatenate(matchups[9].getMatchDate(), " ", matchups[9].getMatchTime())));
                items.add(new ListItem(matchups[9].getTeamOneHeaderDetails(), matchups[9].getTeam1().getTeamLogo()));
                items.add(new ListItem(matchups[9].getTeamTwoHeaderDetails(), matchups[9].getTeam2().getTeamLogo()));

                items2.add(new Header(CommonUtils.concatenate(matchups[10].getMatchDate(), " ", matchups[10].getMatchTime())));
                items2.add(new ListItem(matchups[10].getTeamOneHeaderDetails(), matchups[10].getTeam1().getTeamLogo()));
                items2.add(new ListItem(matchups[10].getTeamTwoHeaderDetails(), matchups[10].getTeam2().getTeamLogo()));

                items3.add(new Header(CommonUtils.concatenate(matchups[11].getMatchDate(), " ", matchups[11].getMatchTime())));
                items3.add(new ListItem(matchups[11].getTeamOneHeaderDetails(), matchups[11].getTeam1().getTeamLogo()));
                items3.add(new ListItem(matchups[11].getTeamTwoHeaderDetails(), matchups[11].getTeam2().getTeamLogo()));
                break;
            case 4: //page 5 (need null checks)
                items.add(new Header(CommonUtils.concatenate(matchups[12].getMatchDate(), " ", matchups[12].getMatchTime())));
                items.add(new ListItem(matchups[12].getTeamOneHeaderDetails(), matchups[12].getTeam1().getTeamLogo()));
                items.add(new ListItem(matchups[12].getTeamTwoHeaderDetails(), matchups[12].getTeam2().getTeamLogo()));

                //check length >=14
                if (matchups.length >= 14) {
                    items2.add(new Header(CommonUtils.concatenate(matchups[13].getMatchDate(), " ", matchups[13].getMatchTime())));
                    items2.add(new ListItem(matchups[13].getTeamOneHeaderDetails(), matchups[13].getTeam1().getTeamLogo()));
                    items2.add(new ListItem(matchups[13].getTeamTwoHeaderDetails(), matchups[13].getTeam2().getTeamLogo()));
                }
                //check length >=15
                if (matchups.length >= 15) {
                    items3.add(new Header(CommonUtils.concatenate(matchups[14].getMatchDate(), " ", matchups[14].getMatchTime())));
                    items3.add(new ListItem(matchups[14].getTeamOneHeaderDetails(), matchups[14].getTeam1().getTeamLogo()));
                    items3.add(new ListItem(matchups[14].getTeamTwoHeaderDetails(), matchups[14].getTeam2().getTeamLogo()));
                }
                break;
            case 5: //page 6 (need null checks)
                //check length >=16
                if (matchups.length >= 16) {
                    items.add(new Header(CommonUtils.concatenate(matchups[15].getMatchDate(), " ", matchups[15].getMatchTime())));
                    items.add(new ListItem(matchups[15].getTeamOneHeaderDetails(), matchups[15].getTeam1().getTeamLogo()));
                    items.add(new ListItem(matchups[15].getTeamTwoHeaderDetails(), matchups[15].getTeam2().getTeamLogo()));
                }
                break;
        }

        final MatchupAdapter listadapter = new MatchupAdapter(this.getActivity().getBaseContext(), items);
        mylist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mylist.setAdapter(listadapter);

        final MatchupAdapter listadapter2 = new MatchupAdapter(this.getActivity().getBaseContext(), items2);
        mylist2.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mylist2.setAdapter(listadapter2);

        final MatchupAdapter listadapter3 = new MatchupAdapter(this.getActivity().getBaseContext(), items3);
        mylist3.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mylist3.setAdapter(listadapter3);
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
}
