package com.bnavarro.pick5football;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.bnavarro.pick5football.GameDay;

public class GameDayParser {

	private XmlPullParser xmlParser;
	private GameDay gameDay;
	public GameDayParser (XmlPullParser xmlParser, GameDay gameDay){
		this.xmlParser=xmlParser;
		this.gameDay=gameDay;
	}
	
	public GameDay parse () throws XmlPullParserException, IOException{
		
        int eventType = xmlParser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT){
            String name = null;
            
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    name = xmlParser.getName();
                    //System.out.println("NAME = " + name);
                    if (name.equalsIgnoreCase("g")){
                    	if (gameDay.getHomeTeam().equalsIgnoreCase(xmlParser.getAttributeValue(5)) &&
                    		gameDay.getVisitingTeam().equalsIgnoreCase(xmlParser.getAttributeValue(8))	){
	                    	gameDay.setQuarter(xmlParser.getAttributeValue(4));
	                    	gameDay.setTime(xmlParser.getAttributeValue(3));
	                    	gameDay.setHomeTeamScore(Integer.valueOf(xmlParser.getAttributeValue(7)));
	                    	gameDay.setVisitingTeamScore(Integer.valueOf(xmlParser.getAttributeValue(10)));
                    	}
                   } 
                    break;
            }
            eventType = xmlParser.next();
        }
        
        return null;
	}
}
