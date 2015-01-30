package com.bnavarro.pick5football;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.GameDay;
import com.bnavarro.pick5football.constants.XMLConstants;

/** XML parser to extract game day fields from nfl score xml file.
 * 
 * @author navman
 *
 */
public class GameDayParser {

	private XmlPullParser xmlParser;
	public GameDayParser (XmlPullParser xmlParser){
		this.xmlParser=xmlParser;
	}
	
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
                    if (name.equalsIgnoreCase(XMLConstants.GAME_DAY.ROOT)){
                    	if (xmlParser.getAttributeName(5).equalsIgnoreCase(XMLConstants.GAME_DAY.ATTR_NM_CLOCK)){//game currently playing
                    		if (matchGameParms.getHomeTeamSign().equalsIgnoreCase(xmlParser.getAttributeValue(6)) &&
                    				matchGameParms.getVisitingTeamSign().equalsIgnoreCase(xmlParser.getAttributeValue(9))	){
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
	                    	if (matchGameParms.getHomeTeamSign().equalsIgnoreCase(xmlParser.getAttributeValue(5)) &&
	                    			matchGameParms.getVisitingTeamSign().equalsIgnoreCase(xmlParser.getAttributeValue(8))	){
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
