package com.bnavarro.pick5football;

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
