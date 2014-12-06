package com.bnavarro.pick5football;

public class GameDay {
	private String homeTeam;
	private String visitingTeam;
	private Integer homeTeamScore;
	private Integer visitingTeamScore;
	private String quarter;
	private String time;
	private String clock;
	
	
	public String getClock() {
		return clock;
	}
	public void setClock(String clock) {
		this.clock = clock;
	}
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getVisitingTeam() {
		return visitingTeam;
	}
	public void setVisitingTeam(String visitingTeam) {
		this.visitingTeam = visitingTeam;
	}
	public Integer getHomeTeamScore() {
		return homeTeamScore;
	}
	public void setHomeTeamScore(Integer homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}
	public Integer getVisitingTeamScore() {
		return visitingTeamScore;
	}
	public void setVisitingTeamScore(Integer visitingTeamScore) {
		this.visitingTeamScore = visitingTeamScore;
	}
	public String getQuarter() {
		return quarter;
	}
	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	

}
