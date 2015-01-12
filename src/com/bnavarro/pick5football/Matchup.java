package com.bnavarro.pick5football;

public class Matchup {

	private String team1;
	private String team2;
	private Double spread;
	private String pickSelection;
	private String homeTeam;
	private String favoredTeam;
	private String headlineDetails;
	
	public Matchup (){
		
	}
	
	public String getTeam1() {
		return team1;
	}

	public void setTeam1(String team1) {
		this.team1 = team1;
	}

	public String getTeam2() {
		return team2;
	}

	public void setTeam2(String team2) {
		this.team2 = team2;
	}


	public Double getSpread() {
		return spread;
	}

	public void setSpread(Double spread) {
		this.spread = spread;
	}

	public String getPickSelection() {
		return pickSelection;
	}

	public void setPickSelection(String pickSelection) {
		this.pickSelection = pickSelection;
	}
	
	public String displayMatchupDetails (){
		if (pickSelection == null)
			headlineDetails = team1.concat(" vs ").concat(team2);
		String favoredDetails = "";
		String homeDetails = "\n\t".concat(homeTeam).concat(" at home ");
		if (!favoredTeam.equalsIgnoreCase("none"))
			favoredDetails = "\n\t".concat(favoredTeam).concat(" favored by ").concat(spread.toString()).concat(" points");
		else{
			favoredDetails = "\n\t".concat("No favorite");
		}
		return headlineDetails.concat(homeDetails).concat(favoredDetails);
	}

	public void makePick (String pickInformation){
		System.out.println("your pick is " + pickInformation);
		headlineDetails = team1.concat(" vs ").concat(team2);
		if (pickInformation != null){
			if (pickInformation.contains(team1)){
				headlineDetails=headlineDetails.replace(">", "");
				headlineDetails=headlineDetails.replace("<","");
				pickSelection = team1;
				headlineDetails=">".concat(headlineDetails);
			}
			else if (pickInformation.contains(team2)){
				headlineDetails=headlineDetails.replace(">", "");
				headlineDetails=headlineDetails.replace("<","");
				pickSelection = team2;
				headlineDetails=headlineDetails.concat("<");
			}else{
				headlineDetails=headlineDetails.replace(">", "");
				headlineDetails=headlineDetails.replace("<","");
				pickSelection = null;
			}
		}
	}
	
	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getFavoredTeam() {
		return favoredTeam;
	}

	public void setFavoredTeam(String favoredTeam) {
		this.favoredTeam = favoredTeam;
	}
}
