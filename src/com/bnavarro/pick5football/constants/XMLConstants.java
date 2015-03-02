package com.bnavarro.pick5football.constants;

/** Constants for when referring to xml data.
 * 
 * @author brian navarro
 *
 */
public class XMLConstants {

	public static class GAME_DAY{
		public static String ATTR_VAL_FINAL_SCORE = "F";
		public static String ATTR_VAL_HALF_TIME = "H";
		public static String ATTR_VAL_NOT_PLAYED = "P";
		public static String ATTR_VAL_FIRST_QUARTER = "1";
		public static String ATTR_VAL_SECOND_QUARTER = "2";
		public static String ATTR_VAL_THIRD_QUARTER = "3";
		public static String ATTR_VAL_FOURTH_QUARTER = "4";
		public static String ATTR_NM_CLOCK = "K";
		public static String START_TAG_GAME = "g";
	}
	
	public static class MATCHES{
		public static String TAG_MATCHUP = "MATCHUP";
		public static String TAG_TEAM_1 = "TEAM1";
		public static String TAG_TEAM_2 = "TEAM2";
		public static String TAG_HOME = "HOME";
		public static String TAG_SPREAD = "SPREAD";
		public static String TAG_FAVORED = "FAVORED";
	}
}
