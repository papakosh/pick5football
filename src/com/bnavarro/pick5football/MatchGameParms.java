package com.bnavarro.pick5football;

/** Search parameters for matching on a game
 * <li>Home team sign
 * <li<Visiting team sign
 * 
 * @author brian navarro
 *
 */
public class MatchGameParms {

	private String homeTeamSign;
	private String visitingTeamSign;
	
	public MatchGameParms (String homeTeamSign, String visitingTeamSign){
		this.homeTeamSign=homeTeamSign;
		this.visitingTeamSign=visitingTeamSign;
	}

	public String getHomeTeamSign() {
		return homeTeamSign;
	}

	public void setHomeTeamSign(String homeTeamSign) {
		this.homeTeamSign = homeTeamSign;
	}

	public String getVisitingTeamSign() {
		return visitingTeamSign;
	}

	public void setVisitingTeamSign(String visitingTeamSign) {
		this.visitingTeamSign = visitingTeamSign;
	}
}
