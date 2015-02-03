package com.bnavarro.pick5football;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.GameDay;
import com.bnavarro.pick5football.constants.XMLConstants;

/** XML parser to extract game day fields from nfl score xml file.
 * 
 * @author brian navarro
 *
 */
public class GameDayParser {

	private XmlPullParser xmlParser;
	public GameDayParser (XmlPullParser xmlParser){
		this.xmlParser=xmlParser;
	}
	
	/** Search and extract xml data into <code>GameDay</code> object. The search uses two parameters 
	 * to determine a match: home team and visiting team
	 * 
	 * <P>Processing occurs in a loop as follows:</P>
	 * <li>Find start of document
	 * <li>Find start tag and then match name (g)
	 * <li>Loop through each game until home team and visiting team match. *Note* Since the xml format
	 * for an in-progress game and finished/not-played game are different, we must determine the format 
	 * by looking for the clock attribute  (inefficient and may change in future)
	 * <li>In-Progress Game: We set in <code>GameDay</code> object the following values: 
	 * home team, visiting team, date, clock, quarter, play time, home team score, and visting team score
	 * <li>Finished/Non-Played Game: We set in <code>GameDay</code> object the following values: 
	 * home team, visiting team, date, quarter, play time, home team score, and visiting team score.
	 * 
	 * @param matchGameParms <code>MatchGameParms</code> obj contains two parameters to match on when searching
	 * xml for data.
	 * 
	 * @return  <code>GameDay</code> object.
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public GameDay parse (MatchGameParms matchGameParms) throws XmlPullParserException, IOException{
		
        int eventType = xmlParser.getEventType();
        GameDay gameDay = new GameDay ();
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = xmlParser.getName();
                    
                    if (XMLConstants.GAME_DAY.START_TAG_GAME.equals(name)){
                 		String homeTeamSign = matchGameParms.getHomeTeamSign();
                		String visitingTeamSign = matchGameParms.getVisitingTeamSign();
                    	if (XMLConstants.GAME_DAY.ATTR_NM_CLOCK.equals(xmlParser.getAttributeName(5))){//game currently playing
                    		if (homeTeamSign.equals(xmlParser.getAttributeValue(6)) && 
                    			visitingTeamSign.equals(xmlParser.getAttributeValue(9))	){
                    			
                    			gameDay.setHomeTeam(xmlParser.getAttributeValue(6));
                    			gameDay.setVisitingTeam(xmlParser.getAttributeValue(9));
                    			gameDay.setDate(xmlParser.getAttributeValue(0).substring(0, 8));
                    			gameDay.setClock(xmlParser.getAttributeValue(5));
                    			gameDay.setQuarter(xmlParser.getAttributeValue(4));
	                    		gameDay.setTime(xmlParser.getAttributeValue(3));
	                    		gameDay.setHomeTeamScore(Integer.valueOf(xmlParser.getAttributeValue(8)));
	                    		gameDay.setVisitingTeamScore(Integer.valueOf(xmlParser.getAttributeValue(11)));
                    		}
                    	}  
                    	else{//game not started/finished
	                    	if (homeTeamSign.equals(xmlParser.getAttributeValue(5)) &&
	                    		visitingTeamSign.equals(xmlParser.getAttributeValue(8))	){
	                    		
	                    		gameDay.setHomeTeam(xmlParser.getAttributeValue(5));
                    			gameDay.setVisitingTeam(xmlParser.getAttributeValue(8));
	                    		gameDay.setDate(xmlParser.getAttributeValue(0).substring(0, 8));
		                    	gameDay.setQuarter(xmlParser.getAttributeValue(4));
		                    	gameDay.setTime(xmlParser.getAttributeValue(3));
		                    	gameDay.setHomeTeamScore(Integer.valueOf(xmlParser.getAttributeValue(7)));
		                    	gameDay.setVisitingTeamScore(Integer.valueOf(xmlParser.getAttributeValue(10)));
	                    	}
                    	}
                   } 
                    break;
            }
            eventType = xmlParser.next();
        }
        
        return gameDay;
	}
}
