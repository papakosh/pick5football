package com.bnavarro.pick5football;


/** Stores match selection details. This includes:
 * <li>team 1 name
 * <li>team 2 name
 * <li>the spread (0.0 means pick-em)
 * <li>pick selection
 * <li>home team (city of home team)
 * <li>favored team (city of favored team)
 * <li>Formatted version of the above fields for screen display
 * </br>
 * 
 * @author brian navarro
 *
 */
public class Match {

	private Team team1;
	private Team team2;
	private Double spread;
	private String selectedTeam;
	private String homeTeam;
	private String favoredTeam;
	private String matchDate;
	private String matchTime;

	public Match (Team team1, Team team2){
		this.team1=team1;
		this.team2=team2;
	}
	
	public Team getTeam1() { return team1; }

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public String getSelectedTeam() {
		return selectedTeam;
	}

	public void setSelectedTeam(String selectedTeam) {this.selectedTeam= selectedTeam;}

	public String getHomeTeam() { return homeTeam; }

	public void setHomeTeam(String homeTeam) {	this.homeTeam = homeTeam;}

	public void setFavoredTeam (String favoredTeam, Double spread){
		this.favoredTeam=favoredTeam;
		this.spread=spread;
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}

	public String getTeamHeaderDetails (Team team){
		CommonUtils.validateNotNull(team, null);
		String teamName = team.getName();
		StringBuffer headerDetails = new StringBuffer (teamName);
		Boolean atHome = false;

		if (homeTeam != null && teamName.contains(homeTeam)){
			atHome = true;
			headerDetails.append("\n\t");
			headerDetails.append("At Home");
		}

		if (favoredTeam != null && teamName.contains(favoredTeam)){
			if (atHome)
				headerDetails.append(", ");
			else
				headerDetails.append("\n\t");
			headerDetails.append("Favored by ");
			if (spread != null && spread > 0.0)
				headerDetails.append(spread);
		}
		return headerDetails.toString();
	}
}
