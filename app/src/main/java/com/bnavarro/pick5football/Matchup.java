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
	
	/** Return match details text in the format of: 
	 * <li>team 1 vs team 2 (Chicago Bears vs New England Patriots)
	 * <li>newline and indent home team at home ( Chicago at home)
	 * <li>if favorite exists, newline and indent favored team favored by spread points ( New England favored by 7.0 points)
	 * <li>else no favorite, new line and indent No Favorite ( No Favorite)
	 * 
	 * @return formatted <code>String</code>
	 */
	public String displayMatchupDetails (){
		if (pickSelection == null)
			headlineDetails = CommonUtils.concatenate(team1, " vs ", team2);
		String favoredDetails = "";
		String homeDetails =  CommonUtils.concatenate("\n\t", homeTeam, " at home ");
		if (!"none".equals(favoredTeam))
			favoredDetails = CommonUtils.concatenate ("\n\t",favoredTeam," favored by ",spread.toString(), " points");
		else{
			favoredDetails = CommonUtils.concatenate("\n\t","No favorite");
		}
		return CommonUtils.concatenate(headlineDetails,homeDetails,favoredDetails);
	}

	/** Return modified match details to include pick selection (team 1 or team 2)
	 * <li>If team 1, then '>' is appended to team 1 to indicate team 1 chosen
	 * <li>If team 2, then '<' is appended to team 2 to indicate team 2 chosen
	 * <li>If none, then pick selection removed ('<' and '>' replaced with '')
	 * 
	 * @param pickInformation
	 */
	public void makePick (String pickInformation){
		headlineDetails = CommonUtils.concatenate(team1," vs ",team2);
		if (pickInformation != null){
			if (pickInformation.contains(team1)){
				headlineDetails=CommonUtils.replace(headlineDetails, ">", "");
				headlineDetails=CommonUtils.replace(headlineDetails, "<","");
				pickSelection = team1;
				headlineDetails=CommonUtils.concatenate(">",headlineDetails);
			}
			else if (pickInformation.contains(team2)){
				headlineDetails=CommonUtils.replace(headlineDetails,">", "");
				headlineDetails=CommonUtils.replace(headlineDetails,"<","");
				pickSelection = team2;
				headlineDetails=CommonUtils.concatenate(headlineDetails,"<");
			}else{
				headlineDetails=CommonUtils.replace(headlineDetails,">", "");
				headlineDetails=CommonUtils.replace(headlineDetails,"<","");
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
