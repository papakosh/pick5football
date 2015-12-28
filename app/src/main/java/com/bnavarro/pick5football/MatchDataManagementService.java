package com.bnavarro.pick5football;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import com.bnavarro.pick5football.async.RetrieveMatchDataAsyncService;
import com.bnavarro.pick5football.async.SelectedPicksAsyncService;
import com.bnavarro.pick5football.constants.FileConstants;
import com.bnavarro.pick5football.constants.XMLConstants;
import com.bnavarro.pick5football.pager.ViewMatchesFragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

//TODO - Throw exceptions to caller so they can display error messages to user
//TODO - Put header comments
public class MatchDataManagementService {
    public static LinkedHashMap<String, Match> matchMap;

    /**
     *
     * @param week
     * @param context
     * @param isUpdate
     */
    public static void populateMatchMap ( String week, Context context, Boolean isUpdate ){
        String matchWeek = week.replace (" ","").toLowerCase(Locale.ENGLISH);
        File exstDir = Environment.getExternalStorageDirectory();
        File matchFile = new File(exstDir.getPath() +"/Pick5FootballData/" + matchWeek + ".xml");
        String urlString = getURLString(matchWeek);

        try {
            if (!matchFile.exists() || isUpdate) {
                RetrieveMatchDataAsyncService retrieval = new RetrieveMatchDataAsyncService(matchFile, urlString, context);
                retrieval.execute();
                retrieval.get();
            }
        }catch (InterruptedException e){
            Log.e("Downloading File", e.getMessage(),e);
        }catch (ExecutionException e){
            Log.e("Downloading File", e.getMessage(),e);
        }

        parseXMLAndPopulateMatchMap(matchFile);
    }

    /**
     *
     * @param matchFile
     */
    private static void parseXMLAndPopulateMatchMap(File matchFile) {
        XmlPullParser parser = Xml.newPullParser();

        boolean matchFound = false;
        Team team1 = null;
        Team team2 = null;
        String homeTeam = null;
        String favoredTeam = null;
        Double matchSpread = null;
        String matchDate = null;
        String matchTime = null;

        try {
            InputStream in_s = new BufferedInputStream(new FileInputStream(matchFile));
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String name;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        matchMap = new LinkedHashMap<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (XMLConstants.MATCH_TAG.equals(name)) {
                            matchFound = true;
                        } else {
                            if (XMLConstants.TEAM1_TAG.equals(name))
                                team1 = new Team(parser.nextText());
                             else if (XMLConstants.TEAM2_TAG.equals(name))
                                team2 = new Team(parser.nextText());
                             else if (XMLConstants.HOME_TEAM_TAG.equals(name))
                                homeTeam = parser.nextText();
                             else if (XMLConstants.MATCH_SPREAD_TAG.equals(name))
                                matchSpread = Double.valueOf(parser.nextText());
                            else if (XMLConstants.FAVORED_TEAM_TAG.equals(name))
                                favoredTeam = parser.nextText();
                             else if (XMLConstants.MATCH_DATE_TAG.equals(name))
                                matchDate = parser.nextText();
                             else if (XMLConstants.MATCH_TIME_TAG.equals(name))
                               matchTime = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (matchFound) {
                            //Set xml data into current match
                            Match currentMatch = new Match(team1, team2);
                            currentMatch.setFavoredTeam(favoredTeam, matchSpread);
                            currentMatch.setHomeTeam(homeTeam);
                            currentMatch.setMatchDate(matchDate);
                            currentMatch.setMatchTime(matchTime);
                            matchMap.put(createKey(currentMatch), currentMatch);

                            //reset local variables before next match
                            matchFound = false;
                            team1 = null;
                            team2 = null;
                            homeTeam = null;
                            favoredTeam = null;
                            matchSpread = null;
                            matchDate = null;
                            matchTime = null;
                        }
                }
                eventType = parser.next();
            }
        }catch (XmlPullParserException e){
            Log.e("Parse Match XML", e.getMessage(), e);
        }catch (IOException e){
            Log.e("Parse Match XML", e.getMessage(), e);
        }
    }

    /**
     *
     * @param matchWeek
     * @return
     */
    private static String getURLString(String matchWeek) {
        String urlString;
        switch (matchWeek){
            case "week1":
                urlString= FileConstants.WEEK_1;
                break;
            case "week2":
                urlString =FileConstants.WEEK_2;
                break;
            case "week3":
                urlString =FileConstants.WEEK_3;
                break;
            case "week4":
                urlString =FileConstants.WEEK_4;
                break;
            case "week5":
                urlString =FileConstants.WEEK_5;
                break;
            case "week6":
                urlString =FileConstants.WEEK_6;
                break;
            case "week7":
                urlString =FileConstants.WEEK_7;
                break;
            case "week8":
                urlString=FileConstants.WEEK_8;
                break;
            case "week9":
                urlString=FileConstants.WEEK_9;
                break;
            case "week10":
                urlString=FileConstants.WEEK_10;
                break;
            case "week11":
                urlString=FileConstants.WEEK_11;
                break;
            case "week12":
                urlString=FileConstants.WEEK_12;
                break;
            case "week13":
                urlString=FileConstants.WEEK_13;
                break;
            case "week14":
                urlString=FileConstants.WEEK_14;
                break;
            case "week15":
                urlString=FileConstants.WEEK_15;
                break;
            case "week16":
                urlString=FileConstants.WEEK_16;
                break;
            case "week17":
                urlString=FileConstants.WEEK_17;
                break;
            default:
                throw new IllegalArgumentException("Invalid Week");
        }
        return urlString;
    }

    /**
     *
     * @param match
     * @return
     */
    public static String createKey (Match match) {
        CommonUtils.validateNotNull(match, "Match is missing");
        String team1 = match.getTeam1().getNFLCode();
        String team2 = match.getTeam2().getNFLCode();
        return team1 + "-" + team2;
    }

    /**
     *
     * @return
     */
    public static Match [] matchMapToArray ( )
    {
        return matchMap.values().toArray(new Match[matchMap.values().size()]);
    }

    /**
     *
     * @param key
     * @param teamSelected
     */
    public static void makePickSelection (String key, String teamSelected) {
        Match targetMatch = matchMap.get(key);
        if (targetMatch != null)
            targetMatch.setSelectedTeam (teamSelected);
    }

    /**
     *
     * @param week
     * @param context
     * @return
     * @throws IOException
     */
    public static Boolean savePicks (String week, Context context ) throws IOException{
        Iterator<Match> matchIterator = matchMap.values().iterator();
        StringBuffer savedSelections = new StringBuffer ( );

        while (matchIterator.hasNext()) {
            Match tempMatch = matchIterator.next();
            if (tempMatch.getSelectedTeam() != null) {
                savedSelections.append(tempMatch.getSelectedTeam());
                savedSelections.append(",");
                savedSelections.append(createKey(tempMatch));
                savedSelections.append("\n");
            }
        }

        // Saves the picks locally
        try {
            SelectedPicksAsyncService selectedPicksAsyncService = new SelectedPicksAsyncService(
                            context, "Save Picks", savedSelections.toString(), null, week);
            selectedPicksAsyncService.execute();
            selectedPicksAsyncService.get();
        }catch (InterruptedException e){
            Log.e("Saving Picks", e.getMessage(),e);
        }catch (ExecutionException e){
            Log.e("Saving Picks", e.getMessage(),e);
        }
        return true;
    }

    /**
     *
     * @param week
     * @param context
     */
    @SuppressWarnings("unchecked")
    public static void loadSelectedPicks (String week, Context context){
        String savedSelections = null;
        try {
            SelectedPicksAsyncService selectedPicksAsyncService = new SelectedPicksAsyncService
                    (context, "Load Picks", null, null, week);
            selectedPicksAsyncService.execute();
            savedSelections = selectedPicksAsyncService.get();
        }
        catch (Exception e){
            Log.e("Loading Picks", e.getMessage(), e);
        }

        if (savedSelections == null || savedSelections.isEmpty())
            Log.e("Loading Picks", "No Picks Found");
        else {
            // Clone Map before setting selections in case of need to rollback
            LinkedHashMap<String,Match> clonedMatchMap = (LinkedHashMap<String, Match>)matchMap.clone();

            String[] temp = savedSelections.split(";");
            for (String selections: temp) {
                String[] tempPick = selections.split(",");
                Match tempMatch = matchMap.get(tempPick[1]);
                if (tempMatch != null)
                    tempMatch.setSelectedTeam(tempPick[0]);
                else {
                    matchMap = (LinkedHashMap) clonedMatchMap.clone();
                }
            }

//            for (int i = 0; i < temp.length; i++) {
//                String[] tempPick = temp[i].split(",");
//                Match tempMatch = matchMap.get(tempPick[1]);
//                if (tempMatch != null)
//                    tempMatch.setSelectedTeam(tempPick[0]);
//                else {
//                    matchMap = (LinkedHashMap) clonedMatchMap.clone();
//                }
//            }
        }

        // notify the UI
        ViewMatchesFragment.notifyPreviousSelectionsExist();
    }

    /**
     *
     * @param week
     * @param context
     * @throws IOException
     */
    public static void submitPicks ( String week, Context context ) throws IOException {
        Iterator<Match> matchIterator = matchMap.values().iterator();
        StringBuffer savedSelections = new StringBuffer();
        StringBuffer submitSelections = new StringBuffer();

        int pickCount = 0;
        while (matchIterator.hasNext()) {
            Match tempMatch = matchIterator.next();
            if (tempMatch.getSelectedTeam() != null) {
                savedSelections.append(tempMatch.getSelectedTeam());
                savedSelections.append(",");
                savedSelections.append(createKey(tempMatch));
                savedSelections.append("\n");

                submitSelections.append(tempMatch.getSelectedTeam());
                submitSelections.append("\n");
                pickCount++;
            }
        }

        if (pickCount < 5)
            Log.e("Submit Picks", "Too Few Picks");
        else if (pickCount > 5)
            Log.e("Submit Picks", "Too Many Picks");
        else {
            try {
                SelectedPicksAsyncService selectedPicksAsyncService = new SelectedPicksAsyncService(context, "Submit Picks",
                        savedSelections.toString(), submitSelections.toString(), week);
                selectedPicksAsyncService.execute();
                selectedPicksAsyncService.get();
            } catch (InterruptedException e) {
                Log.e("Submitting Picks", e.getMessage(), e);
            } catch (ExecutionException e) {
                Log.e("Submitting Picks", e.getMessage(), e);
            }
        }
    }
}
